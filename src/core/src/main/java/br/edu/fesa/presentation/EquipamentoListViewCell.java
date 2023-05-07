package br.edu.fesa.presentation;


import br.edu.fesa.infra.models.Equipamento;
import br.edu.fesa.infra.models.Ingrediente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.NumberFormat;

public class EquipamentoListViewCell extends ListCell<Equipamento> {
    @FXML
    private Label nome;
    @FXML
    private Label consumoWatt;
    @FXML
    private VBox box;
    @FXML
    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Equipamento equipamento, boolean empty) {
        super.updateItem(equipamento, empty);

        setStyle("-fx-background-color: #ffffff");
        if(empty || equipamento == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(MainApplication.class.getResource("equipamento-cell-list-view.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            nome.setText(equipamento.getNome());
            consumoWatt.setText(equipamento.getConsumoWatt() + "kWh");

            setText(null);
            setGraphic(box);
        }

    }

}