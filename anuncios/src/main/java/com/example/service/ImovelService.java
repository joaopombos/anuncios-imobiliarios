package com.example.service;

import java.util.List;

import com.example.model.AgenteImobiliario;
import com.example.model.Imovel;
import com.example.repository.AgenteRepository;
import com.example.repository.ImovelRepository;

public class ImovelService {
    private ImovelRepository repository = ImovelRepository.getInstance();
    private AgenteRepository agenteRepository = AgenteRepository.getInstance();

    public Imovel criar(Imovel imovel) {
        if (imovel.getTituloAn() == null || imovel.getTituloAn().trim().isEmpty()) {
            throw new IllegalArgumentException("Título do anúncio é obrigatório");
        }

        AgenteImobiliario agente = agenteRepository.findById(imovel.getAgente().getId());

        if (agente == null) {
            throw new IllegalArgumentException("Agente não encontrado");
        }
        imovel.setAgente(agente);

        return repository.save(imovel);
    }

    public Imovel obterPorId(int id) {
        return repository.findById(id);
    }

    public List<Imovel> listarTodos() {
        return repository.findAll();
    }

    public void eliminar(int id) {
        repository.delete(id);
    }
}
