package br.edu.fesa.presentation;

import br.edu.fesa.infra.dao.IngredienteDAO;
import br.edu.fesa.infra.dao.UsuarioDAO;
import br.edu.fesa.infra.models.Ingrediente;
import br.edu.fesa.infra.models.UnidadeDeMedidaIngrediente;
import br.edu.fesa.infra.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class UsuariosController extends MenuController implements Initializable {

    @FXML
    private TextField nome;
    @FXML
    private Label labelNome;
    @FXML
    private Text txtUsuarioSelecionado;
    @FXML
    private TextField email;
    @FXML
    private TextField senha;
    @FXML
    private ListView<Usuario> listView;

    private UsuarioDAO usuarioDao = new UsuarioDAO();
    @FXML
    private VBox detailContainer;
    private ObservableList<Usuario> usuarioObservableList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if(listView != null){
            loadUsuarios();
            listView.setItems(usuarioObservableList);
            listView.setCellFactory(usuarioListView ->  new UsuarioListViewCell());
        }
    }
    @FXML
    protected void sendToLogin()  throws IOException {
        MainApplication.setRoot("login-view");
    }
    @FXML
    protected void onClickEditarButton()  throws IOException {
        Usuario usuarioSelecionado = listView.getSelectionModel().getSelectedItem();
        usuarioSelecionado.setNome(nome.getText());
        usuarioSelecionado.setEmail(email.getText());

        if(!senha.getText().isEmpty()){
            usuarioSelecionado.setSenha(senha.getText());
        }

        usuarioDao.atualizar(usuarioSelecionado);
        toggleDetail();
        listView.refresh();
        listView.getSelectionModel().clearSelection();
    }

    @FXML
    protected void onClickExcluirButton()  throws IOException {
        Usuario usuario = listView.getSelectionModel().getSelectedItem();
        usuarioDao.deletar(usuario);
        listView.getItems().remove(usuario);
        toggleDetail();
        listView.getSelectionModel().clearSelection();
    }

    protected void loadUsuarios(){
        List<Usuario> usuarios = usuarioDao.obterTodos();

        usuarioObservableList = FXCollections.observableArrayList();
        usuarioObservableList.addAll(usuarios);
    }
    @FXML
    protected void onClickItemListUsuarios(MouseEvent arg0) {
        Usuario usuarioSelecionado = listView.getSelectionModel().getSelectedItem();
        toggleDetail();

        labelNome.setText(usuarioSelecionado.getNome());
        nome.setText(usuarioSelecionado.getNome());
        email.setText(usuarioSelecionado.getEmail());
    }

    protected void toggleDetail(){
        if(detailContainer.isVisible()){
            detailContainer.setVisible(false);
            GridPane.setConstraints(listView, 3, 2, 9,9);
            return;
        }
        GridPane.setConstraints(listView, 3, 2, 4,9);
        detailContainer.setVisible(true);
    }

}