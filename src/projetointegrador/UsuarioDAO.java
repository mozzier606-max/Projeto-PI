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
import model.Usuario;
/**
 *
 * @author Usuario
 */
public class UsuarioDAO {
    public boolean cadastrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, telefone, pendencia) VALUES ( ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefone());
            stmt.setBoolean(4, usuario.isPendencia());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setId(rs.getInt(1));
                        System.out.println("✅ Usuário cadastrado com ID: " + usuario.getId());
                        return true;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao cadastrar usuário: " + e.getMessage());
        }
        return false;
    }
    
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY nome";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setPendencia(rs.getBoolean("pendencia"));
                
                usuarios.add(usuario);
            }
            
            System.out.println("👥 Total de usuários encontrados: " + usuarios.size());
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar usuários: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    public List<Usuario> pesquisarUsuarios(String termo) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE nome LIKE ? OR email LIKE ? ORDER BY nome";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String termoPesquisa = "%" + termo + "%";
            stmt.setString(1, termoPesquisa);
            stmt.setString(2, termoPesquisa);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setPendencia(rs.getBoolean("pendencia"));
                
                usuarios.add(usuario);
            }
            
            System.out.println("🔍 Usuários encontrados na pesquisa: " + usuarios.size());
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao pesquisar usuários: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    public boolean excluirUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✅ Usuário excluído com sucesso! ID: " + id);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir usuário: " + e.getMessage());
        }
        return false;
    }
    
    public boolean atualizarPendenciaUsuario(int id, boolean pendencia) {
        String sql = "UPDATE usuarios SET pendencia = ? WHERE id = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, pendencia);
            stmt.setInt(2, id);
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✅ Pendência do usuário atualizada para: " + pendencia);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar pendência do usuário: " + e.getMessage());
        }
        return false;
    }
    
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setPendencia(rs.getBoolean("pendencia"));
                return usuario;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return null;
    }
}
