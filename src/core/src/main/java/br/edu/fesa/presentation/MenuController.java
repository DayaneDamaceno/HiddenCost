package br.edu.fesa.presentation;


import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class MenuController {

    @FXML
    void sendToProdutosView() throws IOException {
        AppContext.stage.setTitle("Produtos");
        MainApplication.setRoot("produtos-view");
    }

    @FXML
    private void sendToIngredientesView() throws IOException {
        AppContext.stage.setTitle("Ingredientes");
        MainApplication.setRoot("ingredientes-view");
    }

    @FXML
    private void sendToEquipamentosView() throws IOException {
        AppContext.stage.setTitle("Equipamentos");
        MainApplication.setRoot("equipamentos-view");
    }

    @FXML
    private void sendToLoginView() throws IOException {
        AppContext.stage.setTitle("Login");
        AppContext.usuarioLogado = null;
        MainApplication.setRoot("log-in-view");
    }

}