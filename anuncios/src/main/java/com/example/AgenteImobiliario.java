package com.example;

public class AgenteImobiliario {
    private int id;
    public String nome;
    public int contacto;

    public AgenteImobiliario(int id, String nome, int contacto) {
        this.id = id;
        this.nome = nome;
        this.contacto = contacto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getContacto() {
        return contacto;
    }

    public void setContacto(int contacto) {
        this.contacto = contacto;
    }
}
