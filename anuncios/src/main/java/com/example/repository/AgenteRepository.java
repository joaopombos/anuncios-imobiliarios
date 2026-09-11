package com.example.repository;

import com.example.model.AgenteImobiliario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AgenteRepository {
        private static AgenteRepository instance;
        private Map<Integer, AgenteImobiliario> agentes = new HashMap<>();
        private AtomicInteger nextId = new AtomicInteger(1);

        private AgenteRepository() {
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
        }
}
