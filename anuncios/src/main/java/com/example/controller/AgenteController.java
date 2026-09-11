package com.example.controller;

import java.util.List;

import com.example.model.AgenteImobiliario;
import com.example.service.AgenteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/agentes")
public class AgenteController {
    private AgenteService service = new AgenteService();

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response criar(AgenteImobiliario agente) {
        try {
            AgenteImobiliario criado = service.criar(agente);
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
        AgenteImobiliario agente = service.obterPorId(id);
        if (agente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(agente).build();
    }

    @GET
    @Produces("application/json")
    public Response listar() {
        List<AgenteImobiliario> agentes = service.listarTodos();
        return Response.ok(agentes).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) {
        service.eliminar(id);
        return Response.noContent().build();
    }
}
