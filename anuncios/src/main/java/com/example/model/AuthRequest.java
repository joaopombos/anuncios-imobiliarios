package com.example.model;

/** Corpo dos pedidos de login e registo. */
public class AuthRequest {
    private String tipo;     // CLIENTE ou AGENTE
    private String nome;     // só no registo
    private int contacto;    // só no registo
    private String email;
    private String password;

    public AuthRequest() {
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getContacto() { return contacto; }
    public void setContacto(int contacto) { this.contacto = contacto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
