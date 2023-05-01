package br.edu.fesa.presentation;


import br.edu.fesa.infra.dao.IngredienteDAO;
import br.edu.fesa.infra.models.Ingrediente;
import br.edu.fesa.infra.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class IngredientesController extends MenuController implements Initializable {
    @FXML
    private TextField nome;
    @FXML
    private TextField preco;
    @FXML
    private TextField peso;

    @FXML
    private ListView<Ingrediente> listView;

    private ObservableList<Ingrediente> ingredienteObservableList;
    private IngredienteDAO ingredienteDAO = new IngredienteDAO();

    @FXML
    private void sendToNovoIngredienteView() throws IOException {
        MainApplication.setRoot("novo-ingrediente-view");
    }

    @FXML
    protected void onClickEnviarButton()  throws IOException {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome(nome.getText());
        ingrediente.setPreco(Double.parseDouble(preco.getText()));
        ingrediente.setPeso(Double.parseDouble(peso.getText()));

        ingredienteDAO.salvar(ingrediente);
        MainApplication.setRoot("ingredientes-view");

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
        System.out.println(ingredienteSelecionado.getNome());
    }

}