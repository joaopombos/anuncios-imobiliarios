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

        if (!("Novo".equals(imovel.getEstado()) ||
                "Em construção".equals(imovel.getEstado()) ||
                "Abandonado".equals(imovel.getEstado()) ||
                "Em ruina".equals(imovel.getEstado()) || "Usado".equals(imovel.getEstado()))) {
            throw new IllegalArgumentException("Estado de imóvel inválido!");
        }

        if (!("Ativo".equals(imovel.getEstadoAn()) ||
                "Desativado".equals(imovel.getEstadoAn()) ||
                "Rascunho".equals(imovel.getEstadoAn()))) {
            throw new IllegalArgumentException("Estado de anúncio inválido!");
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

    public Imovel atualizarEstado(int id, String novoEstado) {
        if (!("Ativo".equals(novoEstado)
                || "Desativado".equals(novoEstado)
                || "Rascunho".equals(novoEstado))) {

            throw new IllegalArgumentException("Estado de anúncio inválido!");
        }

        Imovel imovel = repository.findById(id);

        if (imovel == null) {
            return null;
        }

        return repository.atualizarEstado(id, novoEstado);
    }

    public Imovel alterarEstado(int id, String novoEstado) {
        if (novoEstado == null || novoEstado.isBlank()) {
            throw new IllegalArgumentException(
                    "O estado do anúncio é obrigatório.");
        }

        if (!novoEstado.equals("Ativo")
                && !novoEstado.equals("Desativado")
                && !novoEstado.equals("Rascunho")) {

            throw new IllegalArgumentException(
                    "Estado inválido. Os estados permitidos são: "
                            + "Ativo, Desativado e Rascunho.");
        }
        
        return repository.atualizarEstado(id, novoEstado);
    }

}
