package br.edu.fesa.presentation;


import br.edu.fesa.infra.models.Ingrediente;
import br.edu.fesa.infra.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.NumberFormat;

public class UsuarioListViewCell extends ListCell<Usuario> {
    @FXML
    private Label nome;
    @FXML
    private Label email;
    @FXML
    private VBox box;
    @FXML
    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Usuario usuario, boolean empty) {
        super.updateItem(usuario, empty);

        setStyle("-fx-background-color: #ffffff");
        if(empty || usuario == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(MainApplication.class.getResource("usuario-cell-list-view.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            nome.setText(usuario.getNome());
            email.setText(usuario.getEmail());


            setText(null);
            setGraphic(box);
        }

    }

}