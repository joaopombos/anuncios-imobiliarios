package com.example.model;

import java.time.LocalDate;

public class Imovel {
    private int id;
    private String morada;
    private int tipologia;
    private double metros;
    private int andares;
    private int wc;
    private int varandas;
    private String estado;
    private int anoConstrucao;
    private String classeEnergetica;
    private String tituloAn;
    private String descricaoAn;
    private LocalDate dataPub;
    private String estadoAn;
    private double preco;
    private AgenteImobiliario agente;

    public Imovel() {
        
    }


    public Imovel(int id, String morada, int tipologia, double metros, int andares, int wc, int varandas, String estado,
            int anoConstrucao, String classeEnergetica, String tituloAn, String descricaoAn, LocalDate dataPub,
            String estadoAn, double preco, AgenteImobiliario agente) {
        this.id = id;
        this.morada = morada;
        this.tipologia = tipologia;
        this.metros = metros;
        this.andares = andares;
        this.wc = wc;
        this.varandas = varandas;
        this.estado = estado;
        this.anoConstrucao = anoConstrucao;
        this.classeEnergetica = classeEnergetica;
        this.tituloAn = tituloAn;
        this.descricaoAn = descricaoAn;
        this.dataPub = dataPub;
        this.estadoAn = estadoAn;
        this.preco = preco;
        this.agente = agente;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getEstadoAn() {
        return estadoAn;
    }

    public void setEstadoAn(String estadoAn) {
        this.estadoAn = estadoAn;
    }

    public LocalDate getDataPub() {
        return dataPub;
    }

    public void setDataPub(LocalDate dataPub) {
        this.dataPub = dataPub;
    }

    public String getDescricaoAn() {
        return descricaoAn;
    }

    public void setDescricaoAn(String descricaoAn) {
        this.descricaoAn = descricaoAn;
    }

    public String getClasseEnergetica() {
        return classeEnergetica;
    }

    public void setClasseEnergetica(String classeEnergetica) {
        this.classeEnergetica = classeEnergetica;
    }


    public String getTituloAn() {
        return tituloAn;
    }

    public void setTituloAn(String tituloAn) {
        this.tituloAn = tituloAn;
    }

    public int getAnoConstrucao() {
        return anoConstrucao;
    }

    public void setAnoConstrucao(int anoConstrucao) {
        this.anoConstrucao = anoConstrucao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getVarandas() {
        return varandas;
    }

    public void setVarandas(int varandas) {
        this.varandas = varandas;
    }

    public int getWc() {
        return wc;
    }

    public void setWc(int wc) {
        this.wc = wc;
    }

    public int getAndares() {
        return andares;
    }

    public void setAndares(int andares) {
        this.andares = andares;
    }

    public double getMetros() {
        return metros;
    }

    public void setMetros(double metros) {
        this.metros = metros;
    }

    public int getTipologia() {
        return tipologia;
    }

    public void setTipologia(int tipologia) {
        this.tipologia = tipologia;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AgenteImobiliario getAgente() {
        return agente;
    }

    public void setAgente(AgenteImobiliario agente) {
        this.agente = agente;
    }
}
