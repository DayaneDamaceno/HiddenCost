package br.edu.fesa.infra.models;


import java.util.List;

public class Produto {
    private Integer id;
    private Usuario usuario;
    private String nome;
    private double precoUnitario;
    private List<Ingrediente> ingredientes;
    private List<Equipamento> equipamentos;


    public Produto() {
    }

    public Produto(Integer id, Usuario usuario, String nome, double precoUnitario) {
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
    }


    public Produto(Integer id, Usuario usuario, String nome, double precoUnitario, List<Ingrediente> ingredientes, List<Equipamento> equipamentos) {
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.ingredientes = ingredientes;
        this.equipamentos = equipamentos;
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

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }
}
