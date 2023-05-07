package br.edu.fesa.infra.dao;

import br.edu.fesa.infra.models.Equipamento;
import br.edu.fesa.infra.models.Produto;
import br.edu.fesa.infra.models.TipoEquipamento;
import br.edu.fesa.infra.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoDAO implements Dao<Produto> {

    private Connection databaseConnection;
    private Usuario usuarioLogado = new Usuario(1, "dayane", "day@gmail.com", "Dayane@08642ts");
    public ProdutoDAO() {
        this.databaseConnection = DatabaseConnection.getConexao();
    }

    @Override
    public List<Produto> obterTodos() {

        List<Produto> produtos = new ArrayList<>();
        String query = "SELECT ID_PRODUTO, ID_USUARIO, NOME, PRECO_UNITARIO FROM PRODUTOS WHERE id_usuario = 1";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {

                produtos.add(
                        new Produto(
                                result.getInt("ID_PRODUTO"),
                                usuarioLogado,
                                result.getString("NOME"),
                                result.getDouble("PRECO_UNITARIO")
                        ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produtos;
    }

    @Override
    public Produto buscar(Produto produto) {
        try {
            String query = "SELECT * FROM PRODUTOS where id_produto = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);

            statement.setInt(1, produto.getId());

            ResultSet resultSet = statement.executeQuery();

            boolean isEmpty = !resultSet.isBeforeFirst();

            if(isEmpty){
                return null;
            }

            while (resultSet.next()) {
                produto.setNome(resultSet.getString("NOME"));
                produto.setUsuario(usuarioLogado);
                produto.setPrecoUnitario(resultSet.getDouble("PRECO_UNITARIO"));
            }

            return produto;

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
            return null;
        }
    }

    @Override
    public void salvar(Produto produto) {
        try {
            String query = "Insert into PRODUTOS (id_usuario, nome, preco_unitario) values (?,?,?)";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getUsuario().getId());
            statement.setString(2, produto.getNome());
            statement.setDouble(3, produto.getPrecoUnitario());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    @Override
    public void atualizar(Produto produto) {
        try {
            String query = "UPDATE PRODUTOS SET id_usuario=?, nome=?, preco_unitario=? WHERE id_produto = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getUsuario().getId());
            statement.setString(2, produto.getNome());
            statement.setDouble(3, produto.getPrecoUnitario());
            statement.setInt(4, produto.getId());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    @Override
    public void deletar(Produto produto) {
        String query = "DELETE FROM PRODUTOS WHERE id_produto = ?";

        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getId());
            statement.execute();
        } catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }


}
