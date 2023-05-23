package br.edu.fesa.service;

import br.edu.fesa.infra.dao.IngredienteDAO;
import br.edu.fesa.infra.dao.ProdutoDAO;
import br.edu.fesa.infra.dao.ProdutoXEquipamentoDAO;
import br.edu.fesa.infra.dao.ProdutoXIngredienteDAO;
import br.edu.fesa.infra.models.*;
import br.edu.fesa.presentation.AppContext;

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
    public void salvarProduto(Produto produto){
        produto.setPrecoUnitario(calcularPrecoUnitario(produto));
        produto.setId(produtoDAO.salvar(produto).getId());

        for (ProdutoXIngrediente ingrediente: produto.getIngredientes()) {
            produtoXIngredienteDAO.salvar(produto, ingrediente);
        }
        for (ProdutoXEquipamento equipamento: produto.getEquipamentos()) {
            produtoXEquipamentoDAO.salvar(produto, equipamento);
        }
    }

    private static double calcularPrecoUnitario(Produto produto) {
        List<ProdutoXEquipamento> equipamentos = produto.getEquipamentos().stream().map(e -> {
            if(e.getEquipamento().getTipo() == TipoEquipamento.GAS){
                double custo = AppContext.gasPorMin * e.getTempoDeUsoEmMinutos();
                e.setCusto(custo);
            }
            if(e.getEquipamento().getTipo() == TipoEquipamento.ELETRODOMESTICO){
                double custo = AppContext.energiaPorMin * e.getTempoDeUsoEmMinutos();
                e.setCusto(custo);
            }
            return e;
        }).toList();

        produto.setEquipamentos(equipamentos);


        double consumoDosIngredientes = 0;
        double consumoDosEquipamentos = 0;

        for (ProdutoXIngrediente ingrediente: produto.getIngredientes()) {
            consumoDosIngredientes += ingrediente.getMedida() * ingrediente.getIngrediente().getCustoUnitario();
        }
        for (ProdutoXEquipamento equipamento: produto.getEquipamentos()) {
            consumoDosEquipamentos += equipamento.getCusto();
        }

        double precoFinalSugerido = (consumoDosIngredientes + consumoDosEquipamentos);
        precoFinalSugerido += 0.02 * consumoDosEquipamentos;
        precoFinalSugerido += 0.2 * consumoDosIngredientes;


        return precoFinalSugerido;
    }

    public void editarProduto(Produto produto){
        produto.setPrecoUnitario(calcularPrecoUnitario(produto));
        produtoDAO.atualizar(produto);

        for (ProdutoXIngrediente ingrediente: produto.getIngredientes()) {
            produtoXIngredienteDAO.atualizar(produto, ingrediente);
        }
        for (ProdutoXEquipamento equipamento: produto.getEquipamentos()) {
            produtoXEquipamentoDAO.atualizar(produto, equipamento);
        }
    }

    public void recalcularPrecoUnitarioQuandoEquipamentoMudar(Equipamento equipamento){
        List<Produto> produtos = produtoXEquipamentoDAO.obterProdutosQueUsamUmEquipamento(equipamento);
        for (Produto produto: produtos) {
            produto.setIngredientes(produtoXIngredienteDAO.obterIngredientesDeUmProduto(produto));
            produto.setEquipamentos(produtoXEquipamentoDAO.obterEquipamentosDeUmProduto(produto));
            editarProduto(produto);
        }
    }
    public void recalcularPrecoUnitarioQuandoIngredienteMudar(Ingrediente ingrediente){
        List<Produto> produtos = produtoXIngredienteDAO.obterProdutosQueUsamUmIngrediente(ingrediente);
        for (Produto produto: produtos) {
            produto.setIngredientes(produtoXIngredienteDAO.obterIngredientesDeUmProduto(produto));
            produto.setEquipamentos(produtoXEquipamentoDAO.obterEquipamentosDeUmProduto(produto));
            editarProduto(produto);
        }
    }

    public void recalcularTodos(){
        List<Produto> produtos = obterTodosProdutos();
        for (Produto produto: produtos) {
            editarProduto(produto);
        }
    }
}
