package br.com.marcos.ceola.controller.controler;

import br.com.marcos.ceola.controller.dto.MensagemMesaDTO;
import br.com.marcos.ceola.controller.service.MensagemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@ServerEndpoint("/mesa/{id_mesa}/{username}")
@ApplicationScoped
public class MesaWs {

    @Inject
    MensagemService mensagemService;

    private final Map<Long, Collection<Session>> sessoesPorIdMesa = new HashMap<>();

    @OnOpen
    public void onOpen(Session sessao, @PathParam("id_mesa") Long idMesa, @PathParam("username") String username) throws JsonProcessingException {
        if (sessoesPorIdMesa.containsKey(idMesa)) {
            sessoesPorIdMesa.get(idMesa).add(sessao);
        } else {
            sessoesPorIdMesa.put(idMesa, criarCacheDeSessoes(sessao));
        }

        var mensagem = new MensagemMesaDTO(
                idMesa,
                String.format("O usuario %s acabou de entrar na mesa %d", username, idMesa),
                username);

        mensagemService.add(mensagem);

        broadcast(mensagem);
    }

    private Collection<Session> criarCacheDeSessoes(Session sessao) {
        var lista = new LinkedList<Session>();
        lista.add(sessao);

        return lista;
    }

    @OnClose
    public void onClose(Session session, @PathParam("id_mesa") Long idMesa, @PathParam("username") String username) throws IOException {
        var mensagem = new MensagemMesaDTO(
                idMesa,
                String.format("O usuario %s acabou de sair da mesa %d", username, idMesa),
                username);

        mensagemService.add(mensagem);

        broadcast(mensagem);

        if (sessoesPorIdMesa.get(idMesa).size() == 1) {
            sessoesPorIdMesa.remove(idMesa);
        } else {
            sessoesPorIdMesa.get(idMesa).remove(session);
        }

        session.close();
    }

    @OnError
    public void onError(Session session, @PathParam("name") String name, Throwable throwable) {

    }

    @OnMessage
    public void onMessage(String message, @PathParam("id_mesa") Long idMesa, @PathParam("username") String username) {
        var mensagemDTO = new MensagemMesaDTO(idMesa, message, username);

        mensagemService.add(mensagemDTO);

        broadcast(mensagemDTO);
    }

    private void broadcast(MensagemMesaDTO mensagemMesaDTO) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            var json = mapper.writeValueAsString(mensagemMesaDTO);

            sessoesPorIdMesa.get(mensagemMesaDTO.getIdMesa()).forEach(sessao ->
                    sessao.getAsyncRemote().sendObject(json, result -> {
                        if (result.getException() != null) {
                            System.out.println("Unable to send message: " + result.getException());
                        }
                    })
            );
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}