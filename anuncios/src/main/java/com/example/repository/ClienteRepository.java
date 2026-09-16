package com.example.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.model.Cliente;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClienteRepository {
        private static ClienteRepository instance;
        private Map<Integer, Cliente> clientes = new HashMap<>();
        private AtomicInteger nextId = new AtomicInteger(1);

        private static final String FICHEIRO_DADOS = System.getProperty("user.home")
                        + "/Documents/anuncios-imobiliarios/anuncios/clientes.json";

        private ObjectMapper objectMapper = new ObjectMapper();

        private ClienteRepository() {
                carregarDadosFicheiro();
        }

        public static synchronized ClienteRepository getInstance() {
                if (instance == null) {
                        instance = new ClienteRepository();
                }
                return instance;
        }

        public Cliente save(Cliente cliente) {
                int novoId = nextId.getAndIncrement();
                cliente.setId(novoId);
                clientes.put(novoId, cliente);
                guardarDadosFicheiro(); // ← Persiste em ficheiro
                return cliente;
        }

        public Cliente findById(int id) {
                return clientes.get(id);
        }

        public List<Cliente> findAll() {
                return new ArrayList<>(clientes.values());
        }

        public void delete(int id) {
                clientes.remove(id);
                guardarDadosFicheiro(); // ← Persiste em ficheiro
        }

        // ========== NOVOS MÉTODOS ==========

        private void carregarDadosFicheiro() {
                File ficheiro = new File(FICHEIRO_DADOS);

                if (!ficheiro.exists()) {
                        System.out.println("Nenhum ficheiro de dados encontrado. Iniciando vazio.");
                        return;
                }

                try {
                        // Ler lista de agentes do JSON
                        List<Cliente> clienteList = objectMapper.readValue(
                                        ficheiro,
                                        new TypeReference<List<Cliente>>() {
                                        });

                        // Repopular o Map e atualizar o nextId
                        clientes.clear();
                        for (Cliente cliente : clienteList) {
                                clientes.put(cliente.getId(), cliente);

                                // Atualizar nextId para ser maior que o ID mais alto
                                if (cliente.getId() >= nextId.get()) {
                                        nextId.set(cliente.getId() + 1);
                                }
                        }
                        System.out.println("✓ Dados carregados de " + FICHEIRO_DADOS);

                } catch (IOException e) {
                        System.err.println("Erro ao carregar dados do ficheiro: " + e.getMessage());
                }
        }

        private void guardarDadosFicheiro() {
                try {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.writerWithDefaultPrettyPrinter()
                                        .writeValue(new File(FICHEIRO_DADOS), clientes.values());
                        System.out.println("✓ Dados guardados em " + FICHEIRO_DADOS);
                } catch (IOException e) {
                        System.out.println("Erro ao guardar dados em ficheiro: " + e.getMessage());
                }
        }
}
