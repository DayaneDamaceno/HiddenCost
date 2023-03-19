package br.edu.fesa;

import br.edu.fesa.dao.UsuarioDAO;
import br.edu.fesa.models.Usuario;

public class Main {
    public static void main(String[] args) {
        UsuarioDAO usuarioDao = new UsuarioDAO();

        String[] parametros = new String[]{"dayane.damaceno@hotmail.com", "teste"};
        Usuario usuario = usuarioDao.buscar(parametros);

        if(usuario != null)
            System.out.printf("%s é um usuario valido%n", usuario.getNome());
        else
            System.out.println("Usuario não encontrado");

    }
}