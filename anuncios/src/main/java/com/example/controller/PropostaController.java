package com.example.controller;

import java.util.List;

import com.example.model.Proposta;
import com.example.service.PropostaService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/propostas")
public class PropostaController {
    private PropostaService service = new PropostaService();

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response criar(Proposta proposta) {
        try {
            Proposta criado = service.criar(proposta);
            return Response.status(Response.Status.CREATED).entity(criado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response obter(@PathParam("id") int id) {
        Proposta proposta = service.obterPorId(id);
        if (proposta == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(proposta).build();
    }

    @GET
    @Produces("application/json")
    public Response listar() {
        List<Proposta> propostas = service.listarTodos();
        return Response.ok(propostas).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) {
        service.eliminar(id);
        return Response.noContent().build();
    }
}
