package br.edu.fesa.presentation;


import br.edu.fesa.infra.dao.ProdutoDAO;
import br.edu.fesa.infra.models.Produto;
import br.edu.fesa.service.ProdutoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class ProdutosController extends MenuController implements Initializable {



    @FXML
    private ListView<Produto> listView;
    @FXML
    private VBox detailContainer;

    @FXML
    private Button btnNovoProduto;

    private ObservableList<Produto> produtosObservableList;
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    private ProdutoService produtoService = new ProdutoService();
    @FXML
    private void sendToNovoProdutoView() throws IOException {
        MainApplication.setRoot("novo-produto-view");
    }

    @FXML
    protected void onClickEnviarButton()  throws IOException {
//        Ingrediente ingrediente = new Ingrediente();
//        ingrediente.setNome(nome.getText());
//        ingrediente.setPreco(Double.parseDouble(preco.getText()));
//        ingrediente.setPeso(Double.parseDouble(peso.getText()));
//
//        ingredienteDAO.salvar(ingrediente);
//        MainApplication.setRoot("ingredientes-view");

    }

    @FXML
    protected void onClickEditarButton()  throws IOException {
//        Ingrediente ingrediente = listView.getSelectionModel().getSelectedItem();
//        ingrediente.setNome(nome.getText());
//        ingrediente.setPreco(Double.parseDouble(preco.getText()));
//        ingrediente.setPeso(Double.parseDouble(peso.getText()));
//
//        ingredienteDAO.atualizar(ingrediente);
//        toggleDetail();
//        listView.refresh();
//        listView.getSelectionModel().clearSelection();
    }

    @FXML
    protected void onClickExcluirButton()  throws IOException {
//        Ingrediente ingrediente = listView.getSelectionModel().getSelectedItem();
//        ingredienteDAO.deletar(ingrediente);
//        listView.getItems().remove(ingrediente);
//        toggleDetail();
//        listView.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(listView != null){
            loadProdutos();
            listView.setItems(produtosObservableList);
            listView.setCellFactory(ingredienteListView -> new ProdutoListViewCell());
        }

//        if(preco != null)
//            preco.setTextFormatter(new TextFormatter<>(validaNumeroDecimalOuInteiro()));
//
//        if(peso != null)
//            peso.setTextFormatter(new TextFormatter<>(validaNumeroDecimalOuInteiro()));

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
        toggleDetail();

//        labelNome.setText(produtoSelecionado.getNome());
//        nome.setText(produtoSelecionado.getNome());
//        labelPeso.setText("Peso: " + produtoSelecionado.getPeso() + "g");
//        peso.setText(Double.toString(produtoSelecionado.getPeso()));
//
//        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance();
//        labelPreco.setText(formatoMoeda.format(produtoSelecionado.getPreco()));
//        preco.setText(Double.toString(produtoSelecionado.getPreco()));
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
}