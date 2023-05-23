package br.edu.fesa.infra.dao;

import br.edu.fesa.infra.models.TipoEquipamento;
import br.edu.fesa.infra.models.TipoUsuario;
import br.edu.fesa.infra.models.Usuario;

import java.sql.*;
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
                usuarios.add(new Usuario(result.getInt("ID_USUARIO"), result.getString("NOME"), result.getString("EMAIL"), result.getString("SENHA")));
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
                usuario.setId(resultSet.getInt("ID_USUARIO"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setTipo(TipoUsuario.valueOf(resultSet.getString("tipo")));
            }

            return usuario;

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
            return null;
        }
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        try {
            String query = "Insert into USUARIOS (nome, email, senha, tipo) values (?,?,?, 'COMUM')";
            PreparedStatement statement = databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                usuario.setId(rs.getInt(1));
            }

            return usuario;

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
            return null;
        }
    }

    @Override
    public void atualizar(Usuario usuario) {
        try {
            String query = "UPDATE USUARIOS SET nome=?, email=?, senha=? WHERE id_usuario = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());
            statement.setInt(4, usuario.getId());
            statement.execute();

        }catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }

    @Override
    public void deletar(Usuario usuario) {
        String query = "DELETE FROM USUARIOS WHERE id_usuario = ?";

        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, usuario.getId());
            statement.execute();
        } catch (SQLException err){
            System.out.println("Erro na base de dados! " + err);
        }
    }


}
