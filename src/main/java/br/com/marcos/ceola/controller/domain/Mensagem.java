package br.com.marcos.ceola.controller.domain;

import org.bson.types.ObjectId;

import java.time.Instant;

public class Mensagem {

    private ObjectId id;
    private long idMesa;
    private String conteudo;
    private String usuario;
    private Instant dataCriacao;

    public Mensagem() {

    }

    public Mensagem(long idMesa, String conteudo, String usuario) {
        this.idMesa = idMesa;
        this.conteudo = conteudo;
        this.usuario = usuario;
        this.dataCriacao = Instant.now();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(long idMesa) {
        this.idMesa = idMesa;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
