package com.example.repository;

import com.example.model.AgenteImobiliario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AgenteRepository {
        private static AgenteRepository instance;
        private Map<Integer, AgenteImobiliario> agentes = new HashMap<>();
        private AtomicInteger nextId = new AtomicInteger(1);

        private static final String FICHEIRO_DADOS = System.getProperty("user.home")
                        + "/Documents/anuncios-imobiliarios/anuncios/agentes.json";

        private ObjectMapper objectMapper = new ObjectMapper();

        private AgenteRepository() {
                carregarDadosFicheiro();
        }

        public static synchronized AgenteRepository getInstance() {
                if (instance == null) {
                        instance = new AgenteRepository();
                }
                return instance;
        }

        public AgenteImobiliario save(AgenteImobiliario agente) {
                int novoId = nextId.getAndIncrement();
                agente.setId(novoId);
                agentes.put(novoId, agente);
                guardarDadosFicheiro(); // ← Persiste em ficheiro
                return agente;
        }

        public AgenteImobiliario findById(int id) {
                return agentes.get(id);
        }

        public List<AgenteImobiliario> findAll() {
                return new ArrayList<>(agentes.values());
        }

        public void delete(int id) {
                agentes.remove(id);
                guardarDadosFicheiro(); // ← Persiste em ficheiro
        }


        private void carregarDadosFicheiro() {
                File ficheiro = new File(FICHEIRO_DADOS);

                if (!ficheiro.exists()) {
                        System.out.println("Nenhum ficheiro de dados encontrado. Iniciando vazio.");
                        return;
                }

                try {
                        // Ler lista de agentes do JSON
                        List<AgenteImobiliario> agentesList = objectMapper.readValue(
                                        ficheiro,
                                        new TypeReference<List<AgenteImobiliario>>() {
                                        });

                        // Repopular o Map e atualizar o nextId
                        agentes.clear();
                        for (AgenteImobiliario agente : agentesList) {
                                agentes.put(agente.getId(), agente);

                                // Atualizar nextId para ser maior que o ID mais alto
                                if (agente.getId() >= nextId.get()) {
                                        nextId.set(agente.getId() + 1);
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
                                        .writeValue(new File(FICHEIRO_DADOS), agentes.values());
                        System.out.println("✓ Dados guardados em " + FICHEIRO_DADOS);
                } catch (IOException e) {
                        System.out.println("Erro ao guardar dados em ficheiro: " + e.getMessage());
                }
        }
}
