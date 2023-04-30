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
    private ScrollPane listaIngredientes;
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

        if(listaIngredientes != null)
            loadIngredientes();

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

    private void createList(List<Ingrediente> ingredientes){

        VBox container = new VBox();
        container.setSpacing(10);
        container.setStyle("-fx-background-color: FFFFFF;");

        for (Ingrediente ingrediente: ingredientes) {
            container.getChildren().add(createItem(ingrediente));
        }

        listaIngredientes.setContent(container);
    }

    private static VBox createItem(Ingrediente ingrediente) {
        Label nome = new Label(ingrediente.getNome());
        nome.setFont(Font.font("System", FontWeight.BOLD, 16));

        Label labelText = new Label("Ultimo preço de aquisição:");
        labelText.setFont(Font.font(14));

        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance();
        Label preco = new Label(formatoMoeda.format(ingrediente.getPreco()));
        preco.setFont(Font.font("System", FontWeight.BOLD, 24));
        preco.setTextFill(Paint.valueOf("#009f53"));

        VBox item = new VBox();
        item.getChildren().addAll(nome, labelText, preco);
        item.setSpacing(4);
        item.setStyle("-fx-border-color: E6E6E7;");
        item.setPadding(new Insets(10));

        return item;
    }

    protected void loadIngredientes(){
        List<Ingrediente> ingredientes = ingredienteDAO.obterTodos();
        createList(ingredientes);
    }

}