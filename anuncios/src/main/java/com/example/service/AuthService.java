package com.example.service;

import com.example.model.AgenteImobiliario;
import com.example.model.AuthRequest;
import com.example.model.Cliente;

public class AuthService {
    public static final String CLIENTE = "CLIENTE";
    public static final String AGENTE = "AGENTE";

    private final ClienteService clienteService = new ClienteService();
    private final AgenteService agenteService = new AgenteService();

    /** Dados da sessão. */
    public static class Sessao {
        public final String tipo;
        public final int id;
        public final String nome;
        public final String email;

        public Sessao(String tipo, int id, String nome, String email) {
            this.tipo = tipo;
            this.id = id;
            this.nome = nome;
            this.email = email;
        }
    }

    public synchronized Sessao registar(AuthRequest req) {
        validarTipo(req.getTipo());
        if (req.getEmail() == null || req.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (req.getPassword() == null || req.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Palavra-passe é obrigatória");
        }
        String email = req.getEmail().trim();

        if (CLIENTE.equals(req.getTipo())) {
            for (Cliente c : clienteService.listarTodos()) {
                if (email.equalsIgnoreCase(c.getEmail())) {
                    throw new IllegalArgumentException("Já existe uma conta com este email");
                }
            }
            Cliente novo = new Cliente(0, req.getNome(), req.getContacto());
            novo.setEmail(email);
            novo.setPassword(req.getPassword());
            Cliente c = clienteService.criar(novo);
            return new Sessao(CLIENTE, c.getId(), c.getNome(), c.getEmail());
        }

        for (AgenteImobiliario a : agenteService.listarTodos()) {
            if (email.equalsIgnoreCase(a.getEmail())) {
                throw new IllegalArgumentException("Já existe uma conta com este email");
            }
        }
        AgenteImobiliario novo = new AgenteImobiliario(0, req.getNome(), req.getContacto());
        novo.setEmail(email);
        novo.setPassword(req.getPassword());
        AgenteImobiliario a = agenteService.criar(novo);
        return new Sessao(AGENTE, a.getId(), a.getNome(), a.getEmail());
    }

    /** Compara apenas email e palavra-passe. @return a sessão, ou null se estiverem errados. */
    public Sessao login(AuthRequest req) {
        if (req.getEmail() == null || req.getPassword() == null) {
            return null;
        }
        String email = req.getEmail().trim();

        for (Cliente c : clienteService.listarTodos()) {
            if (email.equalsIgnoreCase(c.getEmail()) && req.getPassword().equals(c.getPassword())) {
                return new Sessao(CLIENTE, c.getId(), c.getNome(), c.getEmail());
            }
        }
        for (AgenteImobiliario a : agenteService.listarTodos()) {
            if (email.equalsIgnoreCase(a.getEmail()) && req.getPassword().equals(a.getPassword())) {
                return new Sessao(AGENTE, a.getId(), a.getNome(), a.getEmail());
            }
        }
        return null;
    }

    private static void validarTipo(String tipo) {
        if (!CLIENTE.equals(tipo) && !AGENTE.equals(tipo)) {
            throw new IllegalArgumentException("Tipo de utilizador inválido");
        }
    }
}
