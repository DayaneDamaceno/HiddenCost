package br.edu.fesa.infra.models;


public class Ingrediente {
    private Integer id;
    private String nome;
    private double preso;
    private double peso;

    public Ingrediente() {
    }

    public Ingrediente(Integer id, String nome, double preso, double peso) {
        this.id = id;
        this.nome = nome;
        this.preso = preso;
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

    public double getPreso() {
        return preso;
    }

    public void setPreso(double preso) {
        this.preso = preso;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
