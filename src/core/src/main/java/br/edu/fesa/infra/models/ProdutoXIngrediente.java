package br.edu.fesa.infra.models;


public class ProdutoXIngrediente {
    private int id;
    private Ingrediente ingrediente;
    private double medida;
    private double custoUnitario;

    public ProdutoXIngrediente() {
    }
    public ProdutoXIngrediente(Ingrediente ingrediente, double medida) {
        this.ingrediente = ingrediente;
        this.medida = medida;
    }
    public ProdutoXIngrediente(int id, Ingrediente ingrediente, double medida, double custoUnitario) {
        this.id = id;
        this.ingrediente = ingrediente;
        this.medida = medida;
        this.custoUnitario = custoUnitario;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public double getMedida() {
        return medida;
    }

    public void setMedida(double medida) {
        this.medida = medida;
    }

    public double getCustoUnitario() {
        return custoUnitario;
    }

    public void setCustoUnitario(double custoUnitario) {
        this.custoUnitario = custoUnitario;
    }
}
