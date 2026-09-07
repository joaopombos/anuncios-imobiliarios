package com.example;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/agentes")
public class AgenteController {

    @POST 
    public Response criar(AgenteImobiliario agente) {
        AgenteImobiliario criado = service.criar(agente);
        return Response.status(Response.Status.CREATED).entity(criado).build();
    }
    
}
