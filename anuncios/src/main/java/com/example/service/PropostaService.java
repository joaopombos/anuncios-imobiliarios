package com.example.service;

import java.util.List;

import com.example.model.Cliente;
import com.example.model.Imovel;
import com.example.model.Proposta;
import com.example.repository.ClienteRepository;
import com.example.repository.ImovelRepository;
import com.example.repository.PropostaRepository;

public class PropostaService {
    private PropostaRepository repository = PropostaRepository.getInstance();
    private ClienteRepository clienteRepository = ClienteRepository.getInstance();
    private ImovelRepository imovelRepository = ImovelRepository.getInstance();


    public Proposta criar(Proposta proposta) {

    if (proposta == null) {
        throw new IllegalArgumentException("Proposta inválida");
    }

    if (proposta.getComentario() == null ||
        proposta.getComentario().trim().isEmpty()) {
        throw new IllegalArgumentException("Comentário é obrigatório");
    }

    if (proposta.getPreco() <= 0) {
        throw new IllegalArgumentException("Preço inválido");
    }

    if (proposta.getCliente() == null) {
        throw new IllegalArgumentException("Cliente é obrigatório");
    }

    if (proposta.getImovel() == null) {
        throw new IllegalArgumentException("Imóvel é obrigatório");
    }

    Cliente cliente = clienteRepository.findById(proposta.getCliente().getId());

    if (cliente == null) {
        throw new IllegalArgumentException("Cliente não encontrado");
    }

    Imovel imovel = imovelRepository.findById(proposta.getImovel().getId());

    if (imovel == null) {
        throw new IllegalArgumentException("Imóvel não encontrado");
    }

    // Garante que são os objetos existentes nos repositories
    proposta.setCliente(cliente);
    proposta.setImovel(imovel);

    return repository.save(proposta);
}


    public Proposta obterPorId(int id) {
        return repository.findById(id);
    }

    public List<Proposta> listarTodos() {
        return repository.findAll();
    }

    public void eliminar(int id) {
        repository.delete(id);
    }
}
