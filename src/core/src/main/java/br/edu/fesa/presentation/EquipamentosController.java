package br.edu.fesa.presentation;


import br.edu.fesa.infra.dao.EquipamentoDAO;
import br.edu.fesa.infra.models.Equipamento;
import br.edu.fesa.infra.models.TipoEquipamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class EquipamentosController extends MenuController implements Initializable {

    @FXML
    private TextField nome;
    @FXML
    private TextField marca;
    @FXML
    private ComboBox tipo;
    @FXML
    private ListView<Equipamento> listView;
    @FXML
    private VBox detailContainer;
    @FXML
    private Label labelMarca;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelTipo;
    @FXML
    private Button btnNovoEquipamento;

    private ObservableList<Equipamento> equipamentosObservableList;
    private ObservableList<TipoEquipamento> tiposEquipamentoObservableList;
    private EquipamentoDAO equipamentoDAO = new EquipamentoDAO();


    @FXML
    private void sendToNovoEquipamentoView() throws IOException {
        MainApplication.setRoot("novo-equipamento-view");
    }
    @FXML
    protected void onClickEnviarButton()  throws IOException {
        Equipamento equipamento = new Equipamento();
        equipamento.setNome(nome.getText());
        equipamento.setTipo(TipoEquipamento.valueOf(tipo.getSelectionModel().getSelectedItem().toString()));
        equipamento.setMarca(marca.getText());

        equipamentoDAO.salvar(equipamento);
        MainApplication.setRoot("equipamentos-view");

    }

    @FXML
    protected void onClickEditarButton()  throws IOException {
        Equipamento equipamento = listView.getSelectionModel().getSelectedItem();
        equipamento.setNome(nome.getText());
        equipamento.setTipo(TipoEquipamento.valueOf(tipo.getSelectionModel().getSelectedItem().toString()));
        equipamento.setMarca(marca.getText());

        equipamentoDAO.atualizar(equipamento);
        toggleDetail();
        listView.refresh();
        listView.getSelectionModel().clearSelection();
    }

    @FXML
    protected void onClickExcluirButton()  throws IOException {
        Equipamento equipamento = listView.getSelectionModel().getSelectedItem();
        equipamentoDAO.deletar(equipamento);
        listView.getItems().remove(equipamento);
        toggleDetail();
        listView.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(listView != null){
            loadEquipamentos();
            listView.setItems(equipamentosObservableList);
            listView.setCellFactory(ingredienteListView -> new EquipamentoListViewCell());
        }

        if(tipo != null){
            tiposEquipamentoObservableList = FXCollections.observableArrayList();
            tiposEquipamentoObservableList.addAll(TipoEquipamento.values());
            tipo.setItems(tiposEquipamentoObservableList);
        }
    }

    private static UnaryOperator<TextFormatter.Change> validaNumeroDecimalOuInteiro() {
        return c -> {
            if (!c.getControlNewText().matches("-?\\d*\\.?\\d*"))
                return null;
            return c;
        };
    }

    protected void loadEquipamentos(){
        List<Equipamento> equipamentos = equipamentoDAO.obterTodos();

        equipamentosObservableList = FXCollections.observableArrayList();
        equipamentosObservableList.addAll(equipamentos);
    }

    @FXML
    protected void onClickItemListIngredientes(MouseEvent arg0) {
        Equipamento equipamentoSelecionado = listView.getSelectionModel().getSelectedItem();
        toggleDetail();

        labelNome.setText(equipamentoSelecionado.getNome());
        nome.setText(equipamentoSelecionado.getNome());

        labelMarca.setText("Marca: " +equipamentoSelecionado.getMarca());
        marca.setText(equipamentoSelecionado.getMarca());

        tipo.getSelectionModel().select(equipamentoSelecionado.getTipo().ordinal());
        labelTipo.setText(equipamentoSelecionado.getTipo().toString().toLowerCase());
    }
    protected void toggleDetail(){
        if(detailContainer.isVisible()){
            detailContainer.setVisible(false);
            GridPane.setConstraints(listView, 3, 2, 9,9);
            GridPane.setConstraints(btnNovoEquipamento, 10, 1, 2,1);
            return;
        }
        GridPane.setConstraints(listView, 3, 2, 4,9);
        GridPane.setConstraints(btnNovoEquipamento, 5, 1, 2,1);
        detailContainer.setVisible(true);
    }


}