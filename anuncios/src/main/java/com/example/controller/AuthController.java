package com.example.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import com.example.model.AuthRequest;
import com.example.service.AuthService;
import com.example.service.AuthService.Sessao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("api/auth")
public class AuthController {
    static final String SESSION_KEY = "utilizador";

    private final AuthService service = new AuthService();

    @POST
    @Path("/registar")
    @Consumes("application/json")
    @Produces("application/json")
    public Response registar(AuthRequest req, @Context HttpServletRequest request) {
        try {
            Sessao s = service.registar(req);
            return Response.status(Response.Status.CREATED).entity(iniciarSessao(request, s)).build();
        } catch (IllegalArgumentException e) {
            return erro(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(AuthRequest req, @Context HttpServletRequest request) {
        try {
            Sessao s = service.login(req);
            if (s == null) {
                return erro(Response.Status.UNAUTHORIZED, "Email ou palavra-passe incorretos");
            }
            return Response.ok(iniciarSessao(request, s)).build();
        } catch (IllegalArgumentException e) {
            return erro(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/me")
    @Produces("application/json")
    public Response me(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object u = session == null ? null : session.getAttribute(SESSION_KEY);
        if (u == null) {
            return erro(Response.Status.UNAUTHORIZED, "Sem sessão iniciada");
        }
        return Response.ok(u).build();
    }

    private Map<String, Object> iniciarSessao(HttpServletRequest request, Sessao s) {
        // Novo id de sessão após autenticação (evita session fixation)
        HttpSession antiga = request.getSession(false);
        if (antiga != null) {
            antiga.invalidate();
        }
        Map<String, Object> dados = new LinkedHashMap<>();
        dados.put("tipo", s.tipo);
        dados.put("id", s.id);
        dados.put("nome", s.nome);
        dados.put("email", s.email);
        request.getSession(true).setAttribute(SESSION_KEY, dados);
        return dados;
    }

    private Response erro(Response.Status status, String mensagem) {
        Map<String, String> corpo = new LinkedHashMap<>();
        corpo.put("erro", mensagem);
        return Response.status(status).entity(corpo).type("application/json").build();
    }
}
