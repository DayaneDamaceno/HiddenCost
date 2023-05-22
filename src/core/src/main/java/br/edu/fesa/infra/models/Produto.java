package br.edu.fesa.infra.models;


import java.util.List;

public class Produto {
    private Integer id;
    private Usuario usuario;
    private String nome;
    private double peso;
    private double precoUnitario;
    private List<ProdutoXIngrediente> ingredientes;
    private List<ProdutoXEquipamento> equipamentos;


    public Produto() {
    }

    public Produto(Integer id, Usuario usuario, String nome, double precoUnitario,double peso, List<ProdutoXIngrediente> ingredientes, List<ProdutoXEquipamento> equipamentos) {
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.peso = peso;
        this.ingredientes = ingredientes;
        this.equipamentos = equipamentos;
    }

    public Produto(Integer id, Usuario usuario, String nome, double peso, double precoUnitario) {
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
        this.peso = peso;
        this.precoUnitario = precoUnitario;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public List<ProdutoXIngrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<ProdutoXIngrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<ProdutoXEquipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<ProdutoXEquipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }
}
