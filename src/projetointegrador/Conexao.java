/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetointegrador;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class Conexao {
    
     private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";  
    private static final String PASSWORD = "Knotslip0.";  
    
    
    private static final String OPTIONS = "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    public static Connection getConnection() throws SQLException {
        try {
         
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            return DriverManager.getConnection(URL + OPTIONS, USER, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado!", e);
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar com o banco de dados: " + e.getMessage(), e);
        }
    }
    
    public static boolean testarConexao() {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Conexão com o banco de dados estabelecida com sucesso!");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Erro na conexão: " + e.getMessage());
            return false;
        }
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    void conectar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
