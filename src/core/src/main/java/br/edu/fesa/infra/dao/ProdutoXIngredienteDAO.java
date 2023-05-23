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

public class ProdutoXIngredienteDAO {

    private Connection databaseConnection;
    public ProdutoXIngredienteDAO() {
        this.databaseConnection = DatabaseConnection.getConexao();
    }

    public void salvar(Produto produto, ProdutoXIngrediente ingrediente) {
        try {
            String query = "insert into PRODUTOS_INGREDIENTES (id_produto, id_ingrediente, medida, custo_unitario) values (?,?,?,?)";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getId());
            statement.setInt(2, ingrediente.getIngrediente().getId());
            statement.setDouble(3, ingrediente.getMedida());
            statement.setDouble(4, ingrediente.getCustoUnitario());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }
    public void atualizar(Produto produto, ProdutoXIngrediente ingrediente) {
        try {
            String query = "UPDATE PRODUTOS_INGREDIENTES SET medida = ?, custo_unitario = ? WHERE id_produto = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setDouble(1, ingrediente.getMedida());
            statement.setDouble(2, ingrediente.getCustoUnitario());
            statement.setInt(3, produto.getId());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    public void deletar(ProdutoXIngrediente ingrediente) {
        String query = "DELETE FROM PRODUTOS_INGREDIENTES WHERE id_produto_ingrediente = ?";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, ingrediente.getId());
            statement.execute();
        } catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    public List<ProdutoXIngrediente> obterIngredientesDeUmProduto(Produto produto){
        List<ProdutoXIngrediente> ingredientes = new ArrayList<>();
        String query = """
                select I.*, PI.id_produto_ingrediente, PI.medida, PI.custo_unitario
                from INGREDIENTES I
                         inner join PRODUTOS_INGREDIENTES PI
                                    on I.id_ingrediente = PI.id_ingrediente
                where PI.id_produto = ?""";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Ingrediente ingrediente = new Ingrediente(
                        result.getInt("ID_INGREDIENTE"),
                        result.getString("NOME"),
                        UnidadeDeMedidaIngrediente.valueOf(
                                result.getString("UNIDADE_DE_MEDIDA")),
                        result.getDouble("PRECO"),
                        result.getDouble("PESO"),
                        result.getDouble("CUSTO_UNITARIO")
                );
                ingredientes.add(new ProdutoXIngrediente(
                        result.getInt("id_produto_ingrediente"),
                        ingrediente,
                        result.getInt("medida"),
                        result.getDouble("custo_unitario")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngredienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ingredientes;
    }
}
