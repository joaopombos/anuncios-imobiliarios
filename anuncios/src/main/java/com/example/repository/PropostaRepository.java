package com.example.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.model.Proposta;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

public class PropostaRepository {
        private static PropostaRepository instance;
        private Map<Integer, Proposta> propostas = new HashMap<>();
        private AtomicInteger nextId = new AtomicInteger(1);

        private static final String FICHEIRO_DADOS = System.getProperty("user.home")
                        + "/Documents/anuncios-imobiliarios/anuncios/propostas.json";

        private ObjectMapper objectMapper;

        private PropostaRepository() {
                // ← Configure o objectMapper no construtor
                objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                
                carregarDadosFicheiro();
        }

        public static synchronized PropostaRepository getInstance() {
                if (instance == null) {
                        instance = new PropostaRepository();
                }
                return instance;
        }

        public Proposta save(Proposta proposta) {
                int novoId = nextId.getAndIncrement();
                proposta.setId(novoId);
                propostas.put(novoId, proposta);
                guardarDadosFicheiro(); // ← Persiste em ficheiro
                return proposta;
        }

        public Proposta findById(int id) {
                return propostas.get(id);
        }

        public List<Proposta> findAll() {
                return new ArrayList<>(propostas.values());
        }

        public Proposta atualizarEstado(int id, String novoEstado) {
                Proposta proposta = propostas.get(id);

                if (proposta == null) {
                        return null;
                }

                proposta.setEstado(novoEstado);
                guardarDadosFicheiro();

                return proposta;
        }

        public void delete(int id) {
                propostas.remove(id);
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
                        // Ler lista de propostas do JSON
                        List<Proposta> propostaList = objectMapper.readValue(
                                        ficheiro,
                                        new TypeReference<List<Proposta>>() {
                                        });

                        // Repopular o Map e atualizar o nextId
                        propostas.clear();
                        for (Proposta proposta : propostaList) {
                                propostas.put(proposta.getId(), proposta);

                                // Atualizar nextId para ser maior que o ID mais alto
                                if (proposta.getId() >= nextId.get()) {
                                        nextId.set(proposta.getId() + 1);
                                }
                        }
                        System.out.println("✓ Dados carregados de " + FICHEIRO_DADOS);

                } catch (IOException e) {
                        System.err.println("Erro ao carregar dados do ficheiro: " + e.getMessage());
                }
        }

        private void guardarDadosFicheiro() {
                try {
                        // ← Use o objectMapper da classe, não crie um novo
                        objectMapper.writerWithDefaultPrettyPrinter()
                                        .writeValue(new File(FICHEIRO_DADOS), propostas.values());
                        System.out.println("✓ Dados guardados em " + FICHEIRO_DADOS);
                } catch (IOException e) {
                        System.out.println("Erro ao guardar dados em ficheiro: " + e.getMessage());
                }
        }
}
