package br.com.marcos.ceola.controller.service;

import br.com.marcos.ceola.controller.domain.Mensagem;
import br.com.marcos.ceola.controller.dto.MensagemMesaDTO;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Indexes.descending;

@ApplicationScoped
public class MensagemService {

    private final MongoCollection<Mensagem> mensagensMesa;

    @Inject
    public MensagemService(MongoClient mongoClient) {
        var database = mongoClient.getDatabase("mesas");
        this.mensagensMesa = database.getCollection("mensagens_mesas", Mensagem.class);
    }

    public Collection<Mensagem> buscar10UltimasMensagens() {
        var mensagens = new ArrayList<Mensagem>(10);

        mensagensMesa
                .find()
                .sort(descending("dataCriacao"))
                .limit(10)
                .forEach(mensagem -> mensagens.add(mensagem));

        return mensagens;
    }

    public void add(MensagemMesaDTO mensagemDTO) {
        var mensagem = new Mensagem(mensagemDTO.getIdMesa(), mensagemDTO.getConteudo(), mensagemDTO.getUsername());
        mensagensMesa.insertOne(mensagem);
    }

    public void add(Mensagem mensagem) {
        mensagensMesa.insertOne(mensagem);
    }
}
