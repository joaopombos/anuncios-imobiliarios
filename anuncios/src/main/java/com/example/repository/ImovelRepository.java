package com.example.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.model.Imovel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ImovelRepository {
        private static ImovelRepository instance;
        private Map<Integer, Imovel> imoveis = new HashMap<>();
        private AtomicInteger nextId = new AtomicInteger(1);
        private ObjectMapper objectMapper;

        private static final String FICHEIRO_DADOS = System.getProperty("user.home")
                        + "/Documents/anuncios-imobiliarios/anuncios/anuncios.json";

        private ImovelRepository() {
                // ← Configure aqui dentro do construtor
                objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                carregarDadosFicheiro();
        }

        public static synchronized ImovelRepository getInstance() {
                if (instance == null) {
                        instance = new ImovelRepository();
                }
                return instance;
        }

        public Imovel save(Imovel agente) {
                int novoId = nextId.getAndIncrement();
                agente.setId(novoId);
                imoveis.put(novoId, agente);
                guardarDadosFicheiro(); // ← Persiste em ficheiro
                return agente;
        }

        public Imovel findById(int id) {
                return imoveis.get(id);
        }

        public List<Imovel> findAll() {
                return new ArrayList<>(imoveis.values());
        }

        public void delete(int id) {
                imoveis.remove(id);
                guardarDadosFicheiro(); // ← Persiste em ficheiro
        }

        public Imovel atualizarEstado(int id, String novoEstado) {
                Imovel imovel = imoveis.get(id);

                if (imovel == null) {
                        return null;
                }

                imovel.setEstadoAn(novoEstado);
                guardarDadosFicheiro();

                return imovel;
        }

        private void carregarDadosFicheiro() {
                File ficheiro = new File(FICHEIRO_DADOS);

                if (!ficheiro.exists()) {
                        System.out.println("Nenhum ficheiro de dados encontrado. Iniciando vazio.");
                        return;
                }

                try {
                        // Ler lista de agentes do JSON
                        List<Imovel> imovelList = objectMapper.readValue(
                                        ficheiro,
                                        new TypeReference<List<Imovel>>() {
                                        });

                        System.out.println("Imóveis carregados: " + imovelList.size());
                        for (Imovel im : imovelList) {
                                System.out.println("ID: " + im.getId() +
                                                ", Estado: " + im.getEstadoAn() +
                                                ", Preço: " + im.getPreco() +
                                                ", Agente: "
                                                + (im.getAgente() != null ? im.getAgente().getId() : "null"));
                        }

                        // Repopular o Map e atualizar o nextId
                        imoveis.clear();
                        for (Imovel imovel : imovelList) {
                                imoveis.put(imovel.getId(), imovel);

                                // Atualizar nextId para ser maior que o ID mais alto
                                if (imovel.getId() >= nextId.get()) {
                                        nextId.set(imovel.getId() + 1);
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
                                        .writeValue(new File(FICHEIRO_DADOS), imoveis.values());
                        System.out.println("✓ Dados guardados em " + FICHEIRO_DADOS);
                } catch (IOException e) {
                        System.out.println("Erro ao guardar dados em ficheiro: " + e.getMessage());
                }
        }

}
