package br.edu.fesa.presentation;


import br.edu.fesa.infra.models.Ingrediente;
import br.edu.fesa.infra.models.Produto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.NumberFormat;

public class ProdutoListViewCell extends ListCell<Produto> {
    @FXML
    private Label nome;
    @FXML
    private Label peso;
    @FXML
    private Label preco;
    @FXML
    private VBox box;
    @FXML
    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Produto produto, boolean empty) {
        super.updateItem(produto, empty);

        setStyle("-fx-background-color: #ffffff");
        if(empty || produto == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(MainApplication.class.getResource("produto-cell-list-view.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            nome.setText(produto.getNome());
            peso.setText(produto.getPeso() + "g");

            NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance();
            preco.setText(formatoMoeda.format(produto.getPrecoUnitario()));

            setText(null);
            setGraphic(box);
        }

    }

}