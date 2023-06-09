package br.edu.fesa.infra.dao;

import br.edu.fesa.infra.models.Equipamento;
import br.edu.fesa.infra.models.Produto;
import br.edu.fesa.infra.models.TipoEquipamento;
import br.edu.fesa.presentation.AppContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EquipamentoDAO implements Dao<Equipamento> {

    private Connection databaseConnection;

    public EquipamentoDAO() {
        this.databaseConnection = DatabaseConnection.getConexao();
    }

    @Override
    public List<Equipamento> obterTodos() {
        List<Equipamento> equipamentos = new ArrayList<>();
        String query = "SELECT * FROM EQUIPAMENTOS where id_usuario = ?";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, AppContext.usuarioLogado.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                equipamentos.add(
                        new Equipamento(
                                result.getInt("ID_EQUIPAMENTO"),
                                AppContext.usuarioLogado,
                                TipoEquipamento.valueOf(result.getString("TIPO")),
                                result.getString("MARCA"),
                                result.getString("NOME")
                        ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return equipamentos;
    }

    @Override
    public Equipamento buscar(Equipamento equipamento) {
        try {
            String query = "SELECT * FROM EQUIPAMENTOS where id_equipamento = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);

            statement.setInt(1, equipamento.getId());

            ResultSet resultSet = statement.executeQuery();

            boolean isEmpty = !resultSet.isBeforeFirst();

            if(isEmpty){
                return null;
            }

            while (resultSet.next()) {
                equipamento.setMarca(resultSet.getString("NOME"));
                equipamento.setMarca(resultSet.getString("MARCA"));
                equipamento.setTipo(TipoEquipamento.valueOf(resultSet.getString("TIPO")));
            }

            return equipamento;

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
            return null;
        }
    }

    @Override
    public Equipamento salvar(Equipamento equipamento) {
        try {
            String query = "Insert into EQUIPAMENTOS (nome, tipo, marca, id_usuario) values (?,?,?,?)";
            PreparedStatement statement = databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, equipamento.getNome());
            statement.setString(2, equipamento.getTipo().toString());
            statement.setString(3, equipamento.getMarca());
            statement.setInt(4, AppContext.usuarioLogado.getId());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                equipamento.setId(rs.getInt(1));
            }

            return equipamento;

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
            return null;
        }
    }

    @Override
    public void atualizar(Equipamento equipamento) {
        try {
            String query = "UPDATE EQUIPAMENTOS SET marca=?, tipo=?, nome=? WHERE id_Equipamento = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, equipamento.getMarca());
            statement.setString(2, equipamento.getTipo().toString());
            statement.setString(3, equipamento.getNome());
            statement.setInt(4, equipamento.getId());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    @Override
    public void deletar(Equipamento equipamento) {
        String query = "DELETE FROM EQUIPAMENTOS WHERE id_equipamento = ?";

        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, equipamento.getId());
            statement.execute();
        } catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }




}
