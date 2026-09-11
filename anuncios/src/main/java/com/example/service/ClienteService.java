package com.example.service;

import java.util.List;

import com.example.model.Cliente;
import com.example.repository.ClienteRepository;

public class ClienteService {
    private ClienteRepository repository = ClienteRepository.getInstance();

    public Cliente criar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (cliente.getContacto() <= 0) {
            throw new IllegalArgumentException("Contacto inválido");
        }
        return repository.save(cliente);
    }

    public Cliente obterPorId(int id) {
        return repository.findById(id);
    }

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public void eliminar(int id) {
        repository.delete(id);
    }
}
