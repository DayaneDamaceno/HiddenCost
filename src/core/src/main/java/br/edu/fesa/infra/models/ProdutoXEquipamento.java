package br.edu.fesa.infra.models;


public class ProdutoXEquipamento {
    private Integer id;
    private Equipamento equipamento;
    private Integer tempoDeUsoEmMinutos;

    public ProdutoXEquipamento() {
    }

    public ProdutoXEquipamento(Equipamento equipamento, Integer tempoDeUsoEmMinutos) {
        this.equipamento = equipamento;
        this.tempoDeUsoEmMinutos = tempoDeUsoEmMinutos;
    }
    public ProdutoXEquipamento(Integer id, Equipamento equipamento, Integer tempoDeUsoEmMinutos) {
        this.id = id;
        this.equipamento = equipamento;
        this.tempoDeUsoEmMinutos = tempoDeUsoEmMinutos;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Integer getTempoDeUsoEmMinutos() {
        return tempoDeUsoEmMinutos;
    }

    public void setTempoDeUsoEmMinutos(Integer tempoDeUsoEmMinutos) {
        this.tempoDeUsoEmMinutos = tempoDeUsoEmMinutos;
    }
}
