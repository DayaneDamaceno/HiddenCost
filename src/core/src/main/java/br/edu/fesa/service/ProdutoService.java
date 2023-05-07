package br.edu.fesa.service;

import br.edu.fesa.infra.dao.IngredienteDAO;
import br.edu.fesa.infra.dao.ProdutoDAO;
import br.edu.fesa.infra.dao.ProdutoXEquipamentoDAO;
import br.edu.fesa.infra.dao.ProdutoXIngredienteDAO;
import br.edu.fesa.infra.models.Produto;

import java.util.List;

public class ProdutoService {

    private ProdutoXIngredienteDAO produtoXIngredienteDAO = new ProdutoXIngredienteDAO();
    private ProdutoXEquipamentoDAO produtoXEquipamentoDAO = new ProdutoXEquipamentoDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public List<Produto> obterTodosProdutos(){
        List<Produto> produtos = produtoDAO.obterTodos();

        for (Produto produto: produtos) {
            produto.setIngredientes(produtoXIngredienteDAO.obterIngredientesDeUmProduto(produto));
            produto.setEquipamentos(produtoXEquipamentoDAO.obterEquipamentosDeUmProduto(produto));
        }

        return produtos;
    }
}
