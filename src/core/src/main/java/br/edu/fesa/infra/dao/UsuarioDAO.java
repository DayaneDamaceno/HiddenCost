package br.edu.fesa.infra.dao;

import br.edu.fesa.infra.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO implements Dao<Usuario> {

    private Connection databaseConnection;

    public UsuarioDAO() {
        this.databaseConnection = DatabaseConnection.getConexao();
    }

    @Override
    public List<Usuario> obterTodos() {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        String query = "SELECT * FROM USUARIOS";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                usuarios.add(new Usuario(result.getInt("ID_USUARIO"), result.getString("NOME"), result.getString("EMAIL")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
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
        try {
            String query = "Insert into USUARIOS (nome, email, senha) values (?,?,?)";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    @Override
    public void atualizar(Usuario usuario, String[] params) {

    }

    @Override
    public void deletar(Usuario usuario) {

    }


}
