package br.edu.fesa.presentation;

import br.edu.fesa.infra.dao.UsuarioDAO;
import br.edu.fesa.infra.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable {

    @FXML
    private TextField nome;
    @FXML
    private TextField email;
    @FXML
    private TextField senha;
    @FXML
    private ListView<String> usuariosListView;

    private UsuarioDAO usuarioDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDao = new UsuarioDAO();
        loadUsuarios();
    }
    @FXML
    protected void sendToLogin()  throws IOException {
        MainApplication.setRoot("login-view");
    }
    @FXML
    protected void onClickCadastroButton()  throws IOException {
        Usuario usuario = new Usuario();
        usuario.setNome(nome.getText());
        usuario.setEmail(email.getText());
        usuario.setSenha(senha.getText());
        usuarioDao.salvar(usuario);

        MainApplication.setRoot("login-view");

    }
    protected void loadUsuarios(){
        List<Usuario> users = usuarioDao.obterTodos();
        ObservableList<String> names = FXCollections.observableArrayList(users.stream().map(Usuario::getNome).toList());

        ObservableList<Usuario> teste = FXCollections.observableArrayList(users);
        usuariosListView.setItems(names);

    }

}