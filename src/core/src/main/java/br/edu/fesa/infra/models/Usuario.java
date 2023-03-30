package br.edu.fesa.infra.models;

import java.util.regex.Pattern;

public class Usuario {
    private Integer id;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
    }
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    public Usuario(Integer id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    public Usuario(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public boolean validate(){
        return validateSenha() && validateEmail();
    }

    private boolean validateSenha(){
        String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$";
        Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

        return PASSWORD_PATTERN.matcher(this.senha).matches();
    }
    private boolean validateEmail(){
        String EMAIL_REGEX =  "^(.+)@(.+)$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        return EMAIL_PATTERN.matcher(this.email).matches();
    }
}
