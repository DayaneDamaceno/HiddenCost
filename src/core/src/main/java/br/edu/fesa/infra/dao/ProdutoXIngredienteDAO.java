package br.edu.fesa.infra.dao;

import br.edu.fesa.infra.models.Ingrediente;
import br.edu.fesa.infra.models.Produto;
import br.edu.fesa.infra.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoXIngredienteDAO {

    private Connection databaseConnection;
    public ProdutoXIngredienteDAO() {
        this.databaseConnection = DatabaseConnection.getConexao();
    }

    public void salvar(Produto produto, Ingrediente ingrediente) {
        try {
            String query = "insert into PRODUTOS_INGREDIENTES (id_produto, id_ingrediente) values (?,?)";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getId());
            statement.setInt(2, ingrediente.getId());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    public void deletar(Produto produto) {
        String query = "DELETE FROM PRODUTOS_INGREDIENTES WHERE id_produto = ?";

        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getId());
            statement.execute();
        } catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    public List<Ingrediente> obterIngredientesDeUmProduto(Produto produto){
        List<Ingrediente> ingredientes = new ArrayList<>();
        String query = "select * from INGREDIENTES where id_ingrediente in (SELECT id_ingrediente from PRODUTOS_INGREDIENTES WHERE id_produto = ?)";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ingredientes.add(
                        new Ingrediente(
                                result.getInt("ID_INGREDIENTE"),
                                result.getString("NOME"),
                                result.getDouble("PRECO"),
                                result.getDouble("PESO")
                        ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngredienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ingredientes;
    }
}
