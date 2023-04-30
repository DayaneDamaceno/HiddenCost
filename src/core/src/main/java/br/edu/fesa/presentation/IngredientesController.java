package br.edu.fesa.presentation;


import br.edu.fesa.infra.dao.IngredienteDAO;
import br.edu.fesa.infra.models.Ingrediente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class IngredientesController extends MenuController implements Initializable {
    @FXML
    private TextField nome;
    @FXML
    private TextField preco;
    @FXML
    private TextField peso;
    private IngredienteDAO ingredienteDAO = new IngredienteDAO();

    @FXML
    private void sendToNovoIngredienteView() throws IOException {
        MainApplication.setRoot("novo-ingrediente-view");
    }


    @FXML
    protected void onClickEnviarButton()  throws IOException {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome(nome.getText());
        ingrediente.setPreso(Double.parseDouble(preco.getText()));
        ingrediente.setPeso(Double.parseDouble(peso.getText()));

        ingredienteDAO.salvar(ingrediente);
        MainApplication.setRoot("ingredientes-view");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

}