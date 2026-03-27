package main.java.edu.ifgoiano;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/mensagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MensagemResource {

    // Lista estática para manter os dados enquanto o app estiver rodando
    private static List<Mensagem> mensagens = new ArrayList<>();

    @GET
    public List<Mensagem> listar() {
        return mensagens;
    }

    @POST
    public Response enviar(Mensagem mensagem) {
        mensagens.add(mensagem);
        return Response.status(Response.Status.CREATED).entity(mensagem).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Optional<Mensagem> msg = mensagens.stream()
                .filter(m -> m.id.equals(id))
                .findFirst();
        
        if (msg.isPresent()) {
            return Response.ok(msg.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) {
        boolean removido = mensagens.removeIf(m -> m.id.equals(id));
        if (removido) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}