package br.edu.fesa.presentation;


import br.edu.fesa.infra.models.Ingrediente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.text.NumberFormat;

public class IngredienteListViewCell extends ListCell<Ingrediente> {
    @FXML
    private Label lb;
    @FXML
    private Label preco;
    @FXML
    private VBox box;
    @FXML
    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Ingrediente ingrediente, boolean empty) {
        super.updateItem(ingrediente, empty);

        setStyle("-fx-background-color: #ffffff");
        if(empty || ingrediente == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(MainApplication.class.getResource("ingrediente-cell-list-view.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            lb.setText(ingrediente.getNome());

            NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance();
            preco.setText(formatoMoeda.format(ingrediente.getPreco()));

            setText(null);
            setGraphic(box);
        }

    }

}