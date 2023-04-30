package br.edu.fesa.presentation;


import javafx.fxml.FXML;

import java.io.IOException;

public class EquipamentosController extends MenuController {

    @FXML
    private void sendToNovoEquipamentoView() throws IOException {
        MainApplication.setRoot("novo-equipamento-view");
    }

}