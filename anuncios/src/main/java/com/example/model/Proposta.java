package com.example.model;

public class Proposta {
    private int id;
    private int preco;
    private String estado;
    private String comentario;
    private Cliente cliente;
    private Imovel imovel;

    public Proposta() {

    }

    public Proposta(int id, int preco, String estado, String comentario, Cliente cliente, Imovel imovel) {
        this.id = id;
        this.preco = preco;
        this.estado = estado;
        this.comentario = comentario;
        this.cliente = cliente;
        this.imovel = imovel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
}
