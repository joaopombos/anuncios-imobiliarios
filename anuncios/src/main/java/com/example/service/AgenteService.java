package com.example.service;

import java.util.List;

import com.example.model.AgenteImobiliario;
import com.example.repository.AgenteRepository;

public class AgenteService {
    private AgenteRepository repository = AgenteRepository.getInstance();

    public AgenteImobiliario criar(AgenteImobiliario agente) {
        if (agente.getNome() == null || agente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (agente.getContacto() <= 0) {
            throw new IllegalArgumentException("Contacto inválido");
        }
        return repository.save(agente);
    }

    public AgenteImobiliario obterPorId(int id) {
        return repository.findById(id);
    }

    public List<AgenteImobiliario> listarTodos() {
        return repository.findAll();
    }

    public void eliminar(int id) {
        repository.delete(id);
    }
}
