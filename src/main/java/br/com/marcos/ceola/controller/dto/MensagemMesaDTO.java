
package br.com.marcos.ceola.controller.dto;

import java.util.Date;

public class MensagemMesaDTO {

    private long idMesa;
    private String conteudo;
    private String username;

    private Date data;

    public MensagemMesaDTO(long idMesa, String conteudo, String username) {
        this.idMesa = idMesa;
        this.conteudo = conteudo;
        this.username = username;
    }

    public MensagemMesaDTO(long idMesa, String conteudo, String username, Date data) {
        this.idMesa = idMesa;
        this.conteudo = conteudo;
        this.username = username;
        this.data = data;
    }

    public long getIdMesa() {
        return idMesa;
    }

    public String getConteudo() {
        return conteudo;
    }

    public String getUsername() {
        return username;
    }

    public Date getData() {
        return data;
    }
}
