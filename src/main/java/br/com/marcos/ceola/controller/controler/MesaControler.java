package br.com.marcos.ceola.controller.controler;

import br.com.marcos.ceola.controller.dto.MensagemMesaDTO;
import br.com.marcos.ceola.controller.service.MensagemService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Date;
import java.util.List;

@ApplicationScoped
@Path("/mensagens")
public class MesaControler {

    @Inject
    private MensagemService mensagemService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MensagemMesaDTO> listarMensagens() {
        return mensagemService
                .buscar10UltimasMensagens()
                .stream()
                .map(mensagem -> new MensagemMesaDTO(mensagem.getIdMesa(), mensagem.getConteudo(), mensagem.getUsuario(), Date.from(mensagem.getDataCriacao())))
                .toList();
    }

}
