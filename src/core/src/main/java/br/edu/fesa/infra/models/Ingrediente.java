package br.edu.fesa.infra.models;


public class Ingrediente {
    private Integer id;
    private String nome;
    private UnidadeDeMedidaIngrediente unidadeDeMedida;
    private double preco;
    private double custoUnitario;
    private double peso;

    public Ingrediente() {
    }

    public Ingrediente(Integer id, String nome, UnidadeDeMedidaIngrediente unidadeDeMedida, double preco, double peso, double custoUnitario) {
        this.id = id;
        this.nome = nome;
        this.unidadeDeMedida = unidadeDeMedida;
        this.preco = preco;
        this.peso = peso;
        this.custoUnitario = custoUnitario;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UnidadeDeMedidaIngrediente getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(UnidadeDeMedidaIngrediente unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getCustoUnitario() {
        return custoUnitario;
    }

    public void setCustoUnitario(double custoUnitario) {
        this.custoUnitario = custoUnitario;
    }
}
