package br.edu.fesa.presentation;

import br.edu.fesa.infra.dao.UsuarioDAO;
import br.edu.fesa.infra.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable {

    @FXML
    private TextField nome;

    @FXML
    private Text txtUsuarioSelecionado;
    @FXML
    private TextField email;
    @FXML
    private TextField senha;
    @FXML
    private ListView<Usuario> usuariosListView;

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
    protected void onClickEditarButton()  throws IOException {
        Usuario usuarioSelecionado = usuariosListView.getSelectionModel().getSelectedItem();
        usuarioSelecionado.setNome(nome.getText());
        usuarioSelecionado.setEmail(email.getText());
        if(!senha.getText().isEmpty()){
            usuarioSelecionado.setSenha(senha.getText());
        }

        usuarioDao.atualizar(usuarioSelecionado);
        senha.setText("");
        txtUsuarioSelecionado.setText(nome.getText());
        usuariosListView.refresh();
    }

    @FXML
    protected void onClickExcluirButton()  throws IOException {
        Usuario usuarioSelecionado = usuariosListView.getSelectionModel().getSelectedItem();
        usuarioDao.deletar(usuarioSelecionado);
        usuariosListView.getItems().remove(usuarioSelecionado);
        nome.setText("");
        email.setText("");
        senha.setText("");

    }

    protected void loadUsuarios(){
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList(usuarioDao.obterTodos());
        usuariosListView.setItems(usuarios);
        usuariosListView.setCellFactory(param -> new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNome() == null) {
                    setText(null);
                } else {
                    setText(item.getNome());
                }
            }
        });


    }
    @FXML
    protected void onClickItemListUsuarios(MouseEvent arg0) {
        Usuario usuarioSelecionado = usuariosListView.getSelectionModel().getSelectedItem();
        txtUsuarioSelecionado.setText(usuarioSelecionado.getNome());
        nome.setText(usuarioSelecionado.getNome());
        email.setText(usuarioSelecionado.getEmail());
    }



}