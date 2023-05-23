package br.edu.fesa.infra.dao;

import br.edu.fesa.infra.models.*;
import br.edu.fesa.presentation.AppContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IngredienteDAO implements Dao<Ingrediente> {

    private Connection databaseConnection;

    public IngredienteDAO() {
        this.databaseConnection = DatabaseConnection.getConexao();
    }

    @Override
    public List<Ingrediente> obterTodos() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        String query = "SELECT * FROM INGREDIENTES where id_usuario = ?";
        try {

            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, AppContext.usuarioLogado.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ingredientes.add(
                        new Ingrediente(
                                result.getInt("ID_INGREDIENTE"),
                                AppContext.usuarioLogado,
                                result.getString("NOME"),
                                UnidadeDeMedidaIngrediente.valueOf(
                                        result.getString("UNIDADE_DE_MEDIDA")),
                                result.getDouble("PRECO"),
                                result.getDouble("PESO"),
                                result.getDouble("CUSTO_UNITARIO")
                        ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngredienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ingredientes;
    }

    @Override
    public Ingrediente buscar(Ingrediente ingrediente) {
        try {
            String query = "SELECT * FROM INGREDIENTES where id_ingrediente = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, ingrediente.getId());
            ResultSet resultSet = statement.executeQuery();

            boolean isEmpty = !resultSet.isBeforeFirst();

            if(isEmpty){
                return null;
            }

            while (resultSet.next()) {
                ingrediente.setNome(resultSet.getString("nome"));
                ingrediente.setPreco(resultSet.getDouble("preco"));
                ingrediente.setPeso(resultSet.getDouble("peso"));
            }

            return ingrediente;

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
            return null;
        }
    }

    @Override
    public Ingrediente salvar(Ingrediente ingrediente) {
        try {
            ingrediente.setCustoUnitario(ingrediente.getPreco() / ingrediente.getPeso());

            String query = "Insert into INGREDIENTES (nome, preco, peso, unidade_de_medida, custo_unitario, id_usuario) values (?,?,?,?,?,?)";
            PreparedStatement statement = databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, ingrediente.getNome());
            statement.setDouble(2, ingrediente.getPreco());
            statement.setDouble(3, ingrediente.getPeso());
            statement.setString(4, ingrediente.getUnidadeDeMedida().toString());
            statement.setDouble(5, ingrediente.getCustoUnitario());
            statement.setInt(6, AppContext.usuarioLogado.getId());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                ingrediente.setId(rs.getInt(1));
            }

            return ingrediente;

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
            return null;
        }
    }

    @Override
    public void atualizar(Ingrediente ingrediente) {
        ingrediente.setCustoUnitario(ingrediente.getPreco() / ingrediente.getPeso());
        try {
            String query = "UPDATE INGREDIENTES SET nome=?, preco=?, peso=?, custo_unitario=? WHERE id_ingrediente = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, ingrediente.getNome());
            statement.setDouble(2, ingrediente.getPreco());
            statement.setDouble(3, ingrediente.getPeso());
            statement.setDouble(4, ingrediente.getCustoUnitario());
            statement.setInt(5, ingrediente.getId());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    @Override
    public void deletar(Ingrediente ingrediente) {
        String query = "DELETE FROM INGREDIENTES WHERE id_ingrediente = ?";

        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, ingrediente.getId());
            statement.execute();
        } catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }



}
