package com.example.model;

public class Proposta {
    private int id;
    private double preco;
    private boolean aprovacao;
    private String comentario;
    private Cliente cliente;
    private Imovel imovel;
     
    public Proposta(){

    }

    public Proposta(int id, double preco, boolean aprovacao, String comentario, Cliente cliente, Imovel imovel){
        this.id = id;
        this.preco = preco;
        this.aprovacao = aprovacao;
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

    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isAprovacao() {
        return aprovacao;
    }
    public void setAprovacao(boolean aprovacao) {
        this.aprovacao = aprovacao;
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
