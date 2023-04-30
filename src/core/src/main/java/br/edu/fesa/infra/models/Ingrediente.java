package br.edu.fesa.infra.models;


public class Ingrediente {
    private Integer id;
    private String nome;
    private double preco;
    private double peso;

    public Ingrediente() {
    }

    public Ingrediente(Integer id, String nome, double preco, double peso) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.peso = peso;
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
}
