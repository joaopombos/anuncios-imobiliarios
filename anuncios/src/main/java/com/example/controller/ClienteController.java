package com.example.controller;

import java.util.List;

import com.example.model.Cliente;
import com.example.service.ClienteService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
public class ClienteController {
    private ClienteService service = new ClienteService();

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response criar(Cliente cliente) {
        try {
            Cliente criado = service.criar(cliente);
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
        Cliente cliente = service.obterPorId(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(cliente).build();
    }

    @GET
    @Produces("application/json")
    public Response listar() {
        List<Cliente> clientes = service.listarTodos();
        return Response.ok(clientes).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) {
        service.eliminar(id);
        return Response.noContent().build();
    }
}
