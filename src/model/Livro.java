/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class Livro {
     private int id;
    private String titulo;
    private String autor;
    private String status;
    private String categoria;

    public Livro() {
    }

    public Livro(int id, String titulo, String autor, String categoria, String status) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.status = status;
    }
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return categoria; }
    public void setIsbn(String isbn) { this.categoria = isbn; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
      public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public void emprestar() {
        this.status = "Emprestado";
    }

    public void devolver() {
        this.status = "Disponível";
    }

    @Override
    public String toString() {
        return titulo + " - " + autor + " (" + status + ")";
    }

    public void add(Livro livro) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
}
