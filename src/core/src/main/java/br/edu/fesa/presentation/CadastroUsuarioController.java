package br.edu.fesa.presentation;

import br.edu.fesa.infra.dao.UsuarioDAO;
import br.edu.fesa.infra.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CadastroUsuarioController {

    @FXML
    private TextField nome;
    @FXML
    private TextField email;
    @FXML
    private TextField senha;
    private UsuarioDAO usuarioDao = new UsuarioDAO();

    @FXML
    protected void sendToLogin()  throws IOException {
        MainApplication.setRoot("log-in-view");
    }
    @FXML
    protected void onClickCadastroButton()  throws IOException {
        Usuario usuario = new Usuario();
        usuario.setNome(nome.getText());
        usuario.setEmail(email.getText());
        usuario.setSenha(senha.getText());
        if(usuario.validate()){
            usuarioDao.salvar(usuario);
            MainApplication.setRoot("log-in-view");
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Email ou senha invalidos!");
            alert.setContentText("Lembre-se que a senha deve conter no minimo 8 caracteres, incluindo números, um caractere especial, letras minúsculas e maiúsculas.");

            alert.showAndWait();
        }


    }


}