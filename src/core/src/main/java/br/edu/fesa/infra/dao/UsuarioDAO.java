package br.edu.fesa.infra.dao;

import br.edu.fesa.infra.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsuarioDAO implements Dao<Usuario> {

    private Connection databaseConnection;

    public UsuarioDAO() {
        this.databaseConnection = DatabaseConnection.getConexao();
    }

    @Override
    public List<Usuario> obterTodos() {
        return null;
    }

    @Override
    public Usuario buscar(Usuario usuario) {
        try {
            String query = "SELECT * FROM USUARIOS where email=? and senha=?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, usuario.getEmail());
            statement.setString(2, usuario.getSenha());
            ResultSet resultSet = statement.executeQuery();

            boolean isEmpty = !resultSet.isBeforeFirst();

            if(isEmpty){
                return null;
            }

            while (resultSet.next()) {
                usuario.setNome(resultSet.getString("nome"));
            }

            return usuario;

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
            return null;
        }
    }

    @Override
    public void salvar(Usuario usuario) {

    }

    @Override
    public void atualizar(Usuario usuario, String[] params) {

    }

    @Override
    public void deletar(Usuario usuario) {

    }


}
