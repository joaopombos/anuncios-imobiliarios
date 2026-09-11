package com.example.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.model.Imovel;

public class ImovelRepository {
    private static ImovelRepository instance;
        private Map<Integer, Imovel> imoveis = new HashMap<>();
        private AtomicInteger nextId = new AtomicInteger(1);

        private ImovelRepository() {
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
        }
}
