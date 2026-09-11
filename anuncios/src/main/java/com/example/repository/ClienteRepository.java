package com.example.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.model.Cliente;

public class ClienteRepository {
    private static ClienteRepository instance;
        private Map<Integer, Cliente> clientes = new HashMap<>();
        private AtomicInteger nextId = new AtomicInteger(1);

        private ClienteRepository() {
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
        }
}
