package br.edu.fesa.infra.dao;

import br.edu.fesa.infra.models.Ingrediente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "SELECT * FROM INGREDIENTES";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ingredientes.add(new Ingrediente(result.getInt("ID_INGREDIENTE"), result.getString("NOME"), result.getDouble("PRECO"), result.getDouble("PESO")));
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
    public void salvar(Ingrediente ingrediente) {
        try {
            String query = "Insert into INGREDIENTES (nome, preco, peso) values (?,?,?)";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, ingrediente.getNome());
            statement.setDouble(2, ingrediente.getPreco());
            statement.setDouble(3, ingrediente.getPeso());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    @Override
    public void atualizar(Ingrediente ingrediente) {
        try {
            String query = "UPDATE INGREDIENTES SET nome=?, preco=?, peso=? WHERE id_ingrediente = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, ingrediente.getNome());
            statement.setDouble(2, ingrediente.getPreco());
            statement.setDouble(3, ingrediente.getPeso());
            statement.setInt(4, ingrediente.getId());
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
