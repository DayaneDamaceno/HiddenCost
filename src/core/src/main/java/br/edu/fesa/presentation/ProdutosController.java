package br.edu.fesa.presentation;


import javafx.fxml.FXML;

import java.io.IOException;

public class ProdutosController extends MenuController {

    @FXML
    private void sendToNovoProdutoView() throws IOException {
        MainApplication.setRoot("novo-produto-view");
    }

}