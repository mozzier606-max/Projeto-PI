/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetointegrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Livro;
/**
 *
 * @author Usuario
 */
public class LivroDAO {
     public boolean cadastrarLivro(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, categoria, status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            st.setString(1, livro.getTitulo());
            st.setString(2, livro.getAutor());
            st.setString(3, livro.getCategoria());
            st.setString(4, livro.getStatus() != null ? livro.getStatus() : "Disponível");
            
            int affectedRows = st.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        livro.setId(rs.getInt(1));
                        System.out.println("✅ Livro cadastrado com ID: " + livro.getId());
                        return true;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao cadastrar livro: " + e.getMessage());
        }
        return false;
    }
    
    public List<Livro> listarLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros ORDER BY titulo";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setCategoria(rs.getString("categoria"));
                livro.setStatus(rs.getString("status"));
                
                livros.add(livro);
            }
            
            System.out.println("📚 Total de livros encontrados: " + livros.size());
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar livros: " + e.getMessage());
        }
        
        return livros;
    }
    
    public List<Livro> pesquisarLivros(String termo) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE titulo LIKE ? OR autor LIKE ? OR categoria LIKE ? ORDER BY titulo";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String termoPesquisa = "%" + termo + "%";
            stmt.setString(1, termoPesquisa);
            stmt.setString(2, termoPesquisa);
            stmt.setString(3, termoPesquisa);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setCategoria(rs.getString("categoria"));
                livro.setStatus(rs.getString("status"));
                
                livros.add(livro);
            }
            
            System.out.println("🔍 Livros encontrados na pesquisa: " + livros.size());
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao pesquisar livros: " + e.getMessage());
        }
        
        return livros;
    }
    
    public boolean excluirLivro(int id) {
        String sql = "DELETE FROM livros WHERE id = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✅ Livro excluído com sucesso! ID: " + id);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir livro: " + e.getMessage());
        }
        return false;
    }
    
    public boolean atualizarStatusLivro(int id, String status) {
        String sql = "UPDATE livros SET status = ? WHERE id = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, id);
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✅ Status do livro atualizado para: " + status);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar status do livro: " + e.getMessage());
        }
        return false;
    }
    
    public Livro buscarPorId(int id) {
        String sql = "SELECT * FROM livros WHERE id = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setCategoria(rs.getString("categoria"));
                livro.setStatus(rs.getString("status"));
                return livro;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar livro por ID: " + e.getMessage());
        }
        return null;
    }
}
