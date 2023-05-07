package br.edu.fesa.infra.dao;

import br.edu.fesa.infra.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoXEquipamentoDAO {

    private Connection databaseConnection;
    public ProdutoXEquipamentoDAO() {
        this.databaseConnection = DatabaseConnection.getConexao();
    }

    public void salvar(Produto produto, Equipamento equipamento) {
        try {
            String query = "insert into PRODUTOS_EQUIPAMENTOS (id_produto, id_equipamento) values (?,?)";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getId());
            statement.setInt(2, equipamento.getId());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    public void deletar(Produto produto) {
        String query = "DELETE FROM PRODUTOS_EQUIPAMENTOS WHERE id_produto = ?";

        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getId());
            statement.execute();
        } catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    public List<Equipamento> obterEquipamentosDeUmProduto(Produto produto){
        List<Equipamento> equipamentos = new ArrayList<>();
        String query = "select * from EQUIPAMENTOS where id_equipamento in (SELECT id_equipamento from PRODUTOS_EQUIPAMENTOS WHERE id_produto = ?);";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getId());

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                equipamentos.add(
                        new Equipamento(
                                result.getInt("ID_EQUIPAMENTO"),
                                result.getString("NOME"),
                                TipoEquipamento.valueOf(result.getString("TIPO")),
                                result.getString("MARCA"),
                                result.getDouble("CONSUMOWATT")
                        ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return equipamentos;
    }
}
