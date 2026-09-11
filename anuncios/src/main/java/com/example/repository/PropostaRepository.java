package com.example.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.model.Proposta;

public class PropostaRepository {
    private static PropostaRepository instance;
        private Map<Integer, Proposta> propostas = new HashMap<>();
        private AtomicInteger nextId = new AtomicInteger(1);

        private PropostaRepository() {
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
                return proposta;
        }

        public Proposta findById(int id) {
                return propostas.get(id);
        }

        public List<Proposta> findAll() {
                return new ArrayList<>(propostas.values());
        }

        public void delete(int id) {
                propostas.remove(id);
        }
}
