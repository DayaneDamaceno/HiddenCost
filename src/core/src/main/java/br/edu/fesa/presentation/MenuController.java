package br.edu.fesa.presentation;


import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController {

    @FXML
    private void sendToProdutosView() throws IOException {
        MainApplication.setRoot("produtos-view");
    }

    @FXML
    private void sendToIngredientesView() throws IOException {
        MainApplication.setRoot("ingredientes-view");
    }

    @FXML
    private void sendToEquipamentosView() throws IOException {
        MainApplication.setRoot("equipamentos-view");
    }


}