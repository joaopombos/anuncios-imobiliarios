package com.example.controller;

import java.util.List;

import com.example.model.Imovel;
import com.example.service.ImovelService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/imoveis")
public class ImovelController {
    private ImovelService service = new ImovelService();

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response criar(Imovel imovel) {
        try {
            Imovel criado = service.criar(imovel);
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
        Imovel imovel = service.obterPorId(id);
        if (imovel == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(imovel).build();
    }

    @GET
    @Produces("application/json")
    public Response listar() {
        List<Imovel> imoveis = service.listarTodos();
        return Response.ok(imoveis).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) {
        service.eliminar(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/estado")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarEstado(
            @PathParam("id") int id,
            EstadoAnuncioDTO dto) {

        try {
            Imovel imovel = service.alterarEstado(id, dto.getEstadoAn());

            if (imovel == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.ok(imovel).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

}
