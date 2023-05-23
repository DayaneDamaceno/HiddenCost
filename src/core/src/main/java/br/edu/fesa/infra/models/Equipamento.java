package br.edu.fesa.infra.models;


public class Equipamento {
    private Integer id;
    private Usuario usuario;
    private TipoEquipamento tipo;
    private String marca;
    private String nome;


    public Equipamento() {
    }

    public Equipamento(Integer id, String nome,TipoEquipamento tipo, String marca) {
        this.id = id;
        this.tipo = tipo;
        this.marca = marca;
        this.nome = nome;
    }

    public Equipamento(Integer id, Usuario usuario, TipoEquipamento tipo, String marca, String nome) {
        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.marca = marca;
        this.nome = nome;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoEquipamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEquipamento tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
