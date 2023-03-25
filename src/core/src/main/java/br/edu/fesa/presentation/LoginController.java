package br.edu.fesa.presentation;

import br.edu.fesa.infra.dao.UsuarioDAO;
import br.edu.fesa.infra.models.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField email;
    @FXML
    private TextField senha;
    @FXML
    protected void sendToHome()  throws IOException {
        UsuarioDAO usuarioDao = new UsuarioDAO();

        Usuario usuario = new Usuario();
        usuario.setEmail(email.getText());
        usuario.setSenha(senha.getText());
        usuario = usuarioDao.buscar(usuario);

        if(usuario != null){
            System.out.printf("%s é um usuario valido%n", usuario.getNome());
            MainApplication.setRoot("hello-view");
        }
        else{
            System.out.println("Usuario não encontrado");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Usuário não foi encontrado!");
            alert.setContentText("Verifique se email e senha estão corretos!");

            alert.showAndWait();
        }

    }
}