package br.edu.fesa.presentation;


import br.edu.fesa.infra.dao.IngredienteDAO;
import br.edu.fesa.infra.models.Ingrediente;
import br.edu.fesa.infra.models.TipoEquipamento;
import br.edu.fesa.infra.models.UnidadeDeMedidaIngrediente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class IngredientesController extends MenuController implements Initializable  {


    @FXML
    private TextField nome;
    @FXML
    private TextField preco;
    @FXML
    private TextField peso;
    @FXML
    private Label labelMedida;
    @FXML
    private ComboBox unidadesDeMedida;
    @FXML
    private ListView<Ingrediente> listView;
    @FXML
    private VBox detailContainer;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelPreco;
    @FXML
    private Label labelPeso;
    @FXML
    private Button btnNovoIngrediente;

    private ObservableList<Ingrediente> ingredienteObservableList;

    private ObservableList<UnidadeDeMedidaIngrediente> unidadesDeMedidaOptions;
    private IngredienteDAO ingredienteDAO = new IngredienteDAO();

    @FXML
    private void sendToNovoIngredienteView() throws IOException {
        MainApplication.setRoot("novo-ingrediente-view");
    }

    @FXML
    protected void onClickEnviarButton()  throws IOException {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome(nome.getText());
        ingrediente.setUnidadeDeMedida(UnidadeDeMedidaIngrediente.valueOf(unidadesDeMedida.getSelectionModel().getSelectedItem().toString()));
        ingrediente.setPreco(Double.parseDouble(preco.getText()));
        ingrediente.setPeso(Double.parseDouble(peso.getText()));

        ingredienteDAO.salvar(ingrediente);
        MainApplication.setRoot("ingredientes-view");

    }

    @FXML
    protected void onClickEditarButton()  throws IOException {
        Ingrediente ingrediente = listView.getSelectionModel().getSelectedItem();
        ingrediente.setNome(nome.getText());
        ingrediente.setUnidadeDeMedida(UnidadeDeMedidaIngrediente.valueOf(unidadesDeMedida.getSelectionModel().getSelectedItem().toString()));
        ingrediente.setPreco(Double.parseDouble(preco.getText()));
        ingrediente.setPeso(Double.parseDouble(peso.getText()));

        ingredienteDAO.atualizar(ingrediente);
        toggleDetail();
        listView.refresh();
        listView.getSelectionModel().clearSelection();
    }

    @FXML
    protected void onClickExcluirButton()  throws IOException {
        Ingrediente ingrediente = listView.getSelectionModel().getSelectedItem();
        ingredienteDAO.deletar(ingrediente);
        listView.getItems().remove(ingrediente);
        toggleDetail();
        listView.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(listView != null){
            loadIngredientes();
            listView.setItems(ingredienteObservableList);
            listView.setCellFactory(ingredienteListView -> new IngredienteListViewCell());
        }

        if(preco != null)
            preco.setTextFormatter(new TextFormatter<>(validaNumeroDecimalOuInteiro()));

        if(peso != null)
            peso.setTextFormatter(new TextFormatter<>(validaNumeroDecimalOuInteiro()));

        if(unidadesDeMedida != null){
            unidadesDeMedidaOptions = FXCollections.observableArrayList();
            unidadesDeMedidaOptions.addAll(UnidadeDeMedidaIngrediente.values());
            unidadesDeMedida.setItems(unidadesDeMedidaOptions);
        }
        if(unidadesDeMedida != null){
            unidadesDeMedida.getSelectionModel().selectedItemProperty().addListener((observableValue, ingrediente, itemSelecionado) -> {
                if(itemSelecionado != null){
                    labelMedida.setText("Medida em " + itemSelecionado.toString().toLowerCase());
                }
            });
        }
    }

    private static UnaryOperator<TextFormatter.Change> validaNumeroDecimalOuInteiro() {
        return c -> {
            if (!c.getControlNewText().matches("-?\\d*\\.?\\d*"))
                return null;
            return c;
        };
    }

    protected void loadIngredientes(){
        List<Ingrediente> ingredientes = ingredienteDAO.obterTodos();

        ingredienteObservableList = FXCollections.observableArrayList();
        ingredienteObservableList.addAll(ingredientes);
    }

    @FXML
    protected void onClickItemListIngredientes(MouseEvent arg0) {
        Ingrediente ingredienteSelecionado = listView.getSelectionModel().getSelectedItem();
        toggleDetail();

        labelNome.setText(ingredienteSelecionado.getNome());
        nome.setText(ingredienteSelecionado.getNome());
        labelPeso.setText("Peso: " + ingredienteSelecionado.getPeso() + "g");
        peso.setText(Double.toString(ingredienteSelecionado.getPeso()));
        unidadesDeMedida.getSelectionModel().select(ingredienteSelecionado.getUnidadeDeMedida());
        labelMedida.setText("Medida em " + ingredienteSelecionado.getUnidadeDeMedida().toString().toLowerCase());
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance();
        labelPreco.setText(formatoMoeda.format(ingredienteSelecionado.getPreco()));
        preco.setText(Double.toString(ingredienteSelecionado.getPreco()));
    }
     protected void toggleDetail(){
        if(detailContainer.isVisible()){
            detailContainer.setVisible(false);
            GridPane.setConstraints(listView, 3, 2, 9,9);
            GridPane.setConstraints(btnNovoIngrediente, 10, 1, 2,1);
            return;
        }
         GridPane.setConstraints(listView, 3, 2, 4,9);
         GridPane.setConstraints(btnNovoIngrediente, 5, 1, 2,1);
         detailContainer.setVisible(true);
     }

}