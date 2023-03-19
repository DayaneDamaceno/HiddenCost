package br.edu.fesa.dao;

import java.sql.*;

public class DatabaseConnection {
    private static Connection conexao = null;

    static
    {
        String connectionString = "jdbc:sqlserver://localhost;database=hiddenCost;encrypt=true;integratedSecurity=true;trustServerCertificate=true;";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conexao = DriverManager.getConnection(connectionString);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConexao()
    {
        return conexao;
    }
}
