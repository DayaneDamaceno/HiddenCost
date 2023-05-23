package br.edu.fesa.infra.dao;

import br.edu.fesa.infra.models.*;
import br.edu.fesa.presentation.AppContext;

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

    public void salvar(Produto produto, ProdutoXEquipamento equipamento) {
        try {
            String query = "insert into PRODUTOS_EQUIPAMENTOS (id_produto, id_equipamento, tempo_de_uso) values (?,?,?)";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getId());
            statement.setInt(2, equipamento.getEquipamento().getId());
            statement.setInt(3, equipamento.getTempoDeUsoEmMinutos());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    public void atualizar(Produto produto, ProdutoXEquipamento equipamento) {
        try {
            String query = "UPDATE PRODUTOS_EQUIPAMENTOS SET tempo_de_uso = ? WHERE id_produto = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, equipamento.getTempoDeUsoEmMinutos());
            statement.setInt(2, produto.getId());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    public void deletar(ProdutoXEquipamento equipamento) {
        String query = "DELETE FROM PRODUTOS_EQUIPAMENTOS WHERE id_produto_equipamento = ?";

        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, equipamento.getId());
            statement.execute();
        } catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    public List<ProdutoXEquipamento> obterEquipamentosDeUmProduto(Produto produto){
        List<ProdutoXEquipamento> equipamentos = new ArrayList<>();
        String query = """
                select E.*, PE.id_produto_equipamento ,PE.tempo_de_uso\s
                from EQUIPAMENTOS E\s
                    inner join PRODUTOS_EQUIPAMENTOS PE
                        on E.id_equipamento = PE.id_equipamento
                    where PE.id_produto = ?;""";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, produto.getId());

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Equipamento equipamento = new Equipamento(result.getInt("ID_EQUIPAMENTO"),
                        result.getString("NOME"),
                        TipoEquipamento.valueOf(result.getString("TIPO")),
                        result.getString("MARCA"));

                equipamentos.add(new ProdutoXEquipamento(
                        result.getInt("id_produto_equipamento"),
                        equipamento,
                        result.getInt("TEMPO_DE_USO")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return equipamentos;
    }

    public List<Produto> obterProdutosQueUsamUmEquipamento(Equipamento equipamento){
        List<Produto> produtos = new ArrayList<>();
        String query = """
                select P.*, PE.id_produto_equipamento, PE.tempo_de_uso
                from PRODUTOS P
                         inner join PRODUTOS_EQUIPAMENTOS PE
                                    on P.id_produto = PE.id_produto
                where PE.id_equipamento = ?;""";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, equipamento.getId());

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Produto produto = new Produto(
                        result.getInt("ID_PRODUTO"),
                        AppContext.usuarioLogado,
                        result.getString("NOME"),
                        result.getDouble("PESO"),
                        result.getDouble("PRECO_UNITARIO"));

                produtos.add(produto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produtos;
    }
}
