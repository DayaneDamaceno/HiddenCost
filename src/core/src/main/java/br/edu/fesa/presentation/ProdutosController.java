package br.edu.fesa.presentation;


import br.edu.fesa.infra.dao.*;
import br.edu.fesa.infra.models.*;
import br.edu.fesa.service.ProdutoService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class ProdutosController extends MenuController implements Initializable {

    @FXML
    private Label lbNome;
    @FXML
    private Label lbPeso;
    @FXML
    private Label lbPreco;
    @FXML
    private TextField nome;
    @FXML
    private TextField medida;
    @FXML
    private TextField peso;
    @FXML
    private TextField tempoDeUsoEmMinutos;
    @FXML
    private ComboBox<Ingrediente> ingredientes;
    @FXML
    private ComboBox<Equipamento> equipamentos;
    @FXML
    private ListView<Produto> listView;
    @FXML
    private TableView<ProdutoXIngrediente> tableIngredientes;
    @FXML
    private TableView<ProdutoXEquipamento> tableEquipamentos;
    @FXML
    private TableColumn<ProdutoXEquipamento, String> colunaNomeEquipamento;
    @FXML
    private TableColumn<ProdutoXEquipamento, Integer> colunaTempoDeUsoEmMinutos;
    @FXML
    private TableColumn<ProdutoXIngrediente, String> colunaNomeIngrediente;
    @FXML
    private TableColumn<ProdutoXIngrediente, Double> colunaMedida;
    @FXML
    private VBox detailContainer;
    @FXML
    private Button btnNovoProduto;

    private ObservableList<Produto> produtosObservableList;
    private ObservableList<Ingrediente> ingredientesOptions = FXCollections.observableArrayList();
    private ObservableList<Equipamento> equipamentosOptions = FXCollections.observableArrayList();
    private ObservableList<ProdutoXIngrediente> ingredientesSelecionados = FXCollections.observableArrayList();
    private ObservableList<ProdutoXEquipamento> equipamentosSelecionados = FXCollections.observableArrayList();
    private EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    private IngredienteDAO ingredienteDAO = new IngredienteDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ProdutoXIngredienteDAO produtoXIngredienteDAO = new ProdutoXIngredienteDAO();
    private ProdutoXEquipamentoDAO produtoXEquipamentoDAO = new ProdutoXEquipamentoDAO();
    private ProdutoService produtoService = new ProdutoService();
    @FXML
    private void sendToNovoProdutoView() throws IOException {
        MainApplication.setRoot("novo-produto-view");
    }

    @FXML
    protected void onClickEnviarButton()  throws IOException {
        Produto produto = new Produto();
        produto.setNome(nome.getText());
        produto.setPeso(Double.parseDouble(peso.getText()));
        produto.setIngredientes(ingredientesSelecionados);
        produto.setEquipamentos(equipamentosSelecionados);

        produtoService.salvarProduto(produto);
        MainApplication.setRoot("produtos-view");

    }

    @FXML
    protected void onClickEditarButton()  throws IOException {
        Produto produto = listView.getSelectionModel().getSelectedItem();
        produto.setNome(nome.getText());
        produto.setPeso(Double.parseDouble(peso.getText()));
        produto.setIngredientes(ingredientesSelecionados);
        produto.setEquipamentos(equipamentosSelecionados);

        produtoService.editarProduto(produto);
        toggleDetail();
        listView.refresh();
        listView.getSelectionModel().clearSelection();
    }

    @FXML
    protected void onClickExcluirButton()  throws IOException {
        Produto produto = listView.getSelectionModel().getSelectedItem();
        produtoDAO.deletar(produto);
        listView.getItems().remove(produto);
        toggleDetail();
        listView.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(listView != null){
            loadProdutos();
            listView.setItems(produtosObservableList);
            listView.setCellFactory(ingredienteListView -> new ProdutoListViewCell());
        }
        initCombos();
        initTableViews();
        onSelectIngredienteComboBox();

        if(medida != null)
            medida.setTextFormatter(new TextFormatter<>(validaNumeroDecimalOuInteiro()));

        if(tempoDeUsoEmMinutos != null)
            tempoDeUsoEmMinutos.setTextFormatter(new TextFormatter<>(validaNumeroDecimalOuInteiro()));

        if(peso != null)
            peso.setTextFormatter(new TextFormatter<>(validaNumeroDecimalOuInteiro()));

    }

    private void initTableViews() {

        if(tableIngredientes != null){
            tableIngredientes.getColumns().add(createBtnExcluirIngrediente());

            colunaNomeIngrediente.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIngrediente().getNome()));
            colunaMedida.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMedida()));
            colunaMedida.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            tableIngredientes.setItems(ingredientesSelecionados);
        }
        if(tableEquipamentos != null){
            tableEquipamentos.getColumns().add(createBtnExcluirEquipamento());

            colunaNomeEquipamento.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEquipamento().getNome()));
            colunaTempoDeUsoEmMinutos.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTempoDeUsoEmMinutos()));
            colunaTempoDeUsoEmMinutos.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            tableEquipamentos.setItems(equipamentosSelecionados);
        }

    }

    protected TableColumn createBtnExcluirIngrediente(){
        TableColumn<ProdutoXIngrediente, Void> deleteColumn = new TableColumn<>("");
        Callback<TableColumn<ProdutoXIngrediente, Void>, TableCell<ProdutoXIngrediente, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ProdutoXIngrediente, Void> call(TableColumn<ProdutoXIngrediente, Void> column) {
                final TableCell<ProdutoXIngrediente, Void> cell = new TableCell<>() {
                    private final Button deleteButton = new Button("Excluir");
                    {
                        deleteButton.setOnAction(event -> {
                            ProdutoXIngrediente ingrediente = getTableRow().getItem();
                            System.out.println(ingrediente.getIngrediente().getNome());
                            tableIngredientes.getItems().remove(ingrediente);
                            if(listView != null)
                                produtoXIngredienteDAO.deletar(ingrediente);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
                return cell;
            }
        };

        deleteColumn.setCellFactory(cellFactory);
        return deleteColumn;
    }

    protected TableColumn createBtnExcluirEquipamento(){
        TableColumn<ProdutoXEquipamento, Void> deleteColumn = new TableColumn<>("");
        Callback<TableColumn<ProdutoXEquipamento, Void>, TableCell<ProdutoXEquipamento, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ProdutoXEquipamento, Void> call(TableColumn<ProdutoXEquipamento, Void> column) {
                final TableCell<ProdutoXEquipamento, Void> cell = new TableCell<>() {
                    private final Button deleteButton = new Button("Excluir");
                    {
                        deleteButton.setOnAction(event -> {
                            ProdutoXEquipamento equipamento = getTableRow().getItem();
                            System.out.println(equipamento.getEquipamento().getNome());
                            tableEquipamentos.getItems().remove(equipamento);
                            if(listView != null)
                                produtoXEquipamentoDAO.deletar(equipamento);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
                return cell;
            }
        };

        deleteColumn.setCellFactory(cellFactory);
        return deleteColumn;
    }

    private void initCombos() {
        if(ingredientes != null){
            ingredientesOptions.addAll(ingredienteDAO.obterTodos());
            ingredientes.setConverter(new StringConverter<>() {
                @Override
                public String toString(Ingrediente item) {
                    return item.getNome();
                }

                @Override
                public Ingrediente fromString(String string) {
                    return null;
                }
            });
            ingredientes.setItems(ingredientesOptions);
        }
        if(equipamentos != null){
            equipamentosOptions.addAll(equipamentoDAO.obterTodos());
            equipamentos.setConverter(new StringConverter<>() {
                @Override
                public String toString(Equipamento item) {
                    return item.getNome();
                }

                @Override
                public Equipamento fromString(String string) {
                    return null;
                }
            });
            equipamentos.setItems(equipamentosOptions);
        }
    }

    private static UnaryOperator<TextFormatter.Change> validaNumeroDecimalOuInteiro() {
        return c -> {
            if (!c.getControlNewText().matches("-?\\d*\\.?\\d*"))
                return null;
            return c;
        };
    }

    protected void loadProdutos(){
        List<Produto> produtos = produtoService.obterTodosProdutos();

        produtosObservableList = FXCollections.observableArrayList();
        produtosObservableList.addAll(produtos);
    }

    @FXML
    protected void onClickItemListProdutos(MouseEvent arg0) {
        Produto produtoSelecionado = listView.getSelectionModel().getSelectedItem();
        List<ProdutoXIngrediente> ingredientes = produtoSelecionado.getIngredientes().stream().toList();
        List<ProdutoXEquipamento> equipamentos = produtoSelecionado.getEquipamentos().stream().toList();
        toggleDetail();
        equipamentosSelecionados.clear();
        ingredientesSelecionados.clear();
        equipamentosSelecionados.addAll(equipamentos);
        ingredientesSelecionados.addAll(ingredientes);


        lbNome.setText(produtoSelecionado.getNome());
        nome.setText(produtoSelecionado.getNome());
        lbPeso.setText("Peso: " + produtoSelecionado.getPeso() + "g");
        peso.setText(Double.toString(produtoSelecionado.getPeso()));

        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance();
        lbPreco.setText(formatoMoeda.format(10.99));
    }
    @FXML
    protected void onClickAddIngrediente() {
        Ingrediente ingredienteSelecionado = ingredientes.getSelectionModel().getSelectedItem();
        ProdutoXIngrediente novoIngrediente = new ProdutoXIngrediente(
                ingredienteSelecionado,
                Double.parseDouble(medida.getText()));

        ingredientesSelecionados.add(novoIngrediente);
        if(listView != null){
            produtoXIngredienteDAO.salvar(
                    listView.getSelectionModel().getSelectedItem(),
                    novoIngrediente);
        }
        ingredientes.getSelectionModel().clearSelection();
        medida.clear();
    }
    @FXML
    protected void onClickAddEquipamento() {
        Equipamento equipamentoSelecionado = equipamentos.getSelectionModel().getSelectedItem();
        ProdutoXEquipamento novoEquipamento = new ProdutoXEquipamento(equipamentoSelecionado, Integer.parseInt(tempoDeUsoEmMinutos.getText()));
        equipamentosSelecionados.add(novoEquipamento);
        equipamentos.getSelectionModel().clearSelection();
        tempoDeUsoEmMinutos.clear();
    }

    protected void toggleDetail(){
        if(detailContainer.isVisible()){
            detailContainer.setVisible(false);
            GridPane.setConstraints(listView, 3, 2, 9,9);
            GridPane.setConstraints(btnNovoProduto, 10, 1, 2,1);
            return;
        }
        GridPane.setConstraints(listView, 3, 2, 4,9);
        GridPane.setConstraints(btnNovoProduto, 5, 1, 2,1);
        detailContainer.setVisible(true);
    }

    protected void onSelectIngredienteComboBox(){
        if(ingredientes != null){
            ingredientes.getSelectionModel().selectedItemProperty().addListener((observableValue, ingrediente, itemSelecionado) -> {
                if(itemSelecionado != null){
                    medida.setPromptText("Medida em " + itemSelecionado.getUnidadeDeMedida().toString().toLowerCase());
                }
            });
        }
    }


}