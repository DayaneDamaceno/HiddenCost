package br.edu.fesa.infra.dao;

import br.edu.fesa.infra.models.Equipamento;
import br.edu.fesa.infra.models.Produto;
import br.edu.fesa.presentation.AppContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoDAO implements Dao<Produto> {

    private Connection databaseConnection;
    public ProdutoDAO() {
        this.databaseConnection = DatabaseConnection.getConexao();
    }

    @Override
    public List<Produto> obterTodos() {

        List<Produto> produtos = new ArrayList<>();
        String query = "SELECT * FROM PRODUTOS WHERE id_usuario = ?";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, AppContext.usuarioLogado.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {

                produtos.add(
                        new Produto(
                                result.getInt("ID_PRODUTO"),
                                AppContext.usuarioLogado,
                                result.getString("NOME"),
                                result.getDouble("PESO"),
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
                produto.setUsuario(AppContext.usuarioLogado);
                produto.setPrecoUnitario(resultSet.getDouble("PRECO_UNITARIO"));
                produto.setPeso(resultSet.getDouble("PESO"));
            }

            return produto;

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
            return null;
        }
    }

    @Override
    public Produto salvar(Produto produto) {
        try {
            String query = "Insert into PRODUTOS (id_usuario, nome, peso, preco_unitario) values (?,?,?,?)";
            PreparedStatement statement = databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, AppContext.usuarioLogado.getId());
            statement.setString(2, produto.getNome());
            statement.setDouble(3, produto.getPeso());
            statement.setDouble(4, produto.getPrecoUnitario());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                produto.setId(rs.getInt(1));
            }

            return produto;

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
            return null;
        }
    }

    @Override
    public void atualizar(Produto produto) {
        try {
            String query = "UPDATE PRODUTOS SET id_usuario=?, nome=?, preco_unitario=?, peso=? WHERE id_produto = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getUsuario().getId());
            statement.setString(2, produto.getNome());
            statement.setDouble(3, produto.getPrecoUnitario());
            statement.setDouble(4, produto.getPeso());
            statement.setInt(5, produto.getId());
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
