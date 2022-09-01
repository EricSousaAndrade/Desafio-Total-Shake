package br.com.desafio.totalshake.application.errors.response;

import java.util.List;

public class ExceptionResponseDTO {

    private final int httpCode;
    private final String mensagem;
    private final String codInterno;
    private List<ErroCampoResponseDTO> erros;
    private String statusPedido;

    public ExceptionResponseDTO(int httpCode, String mensagem, String codInterno, List<ErroCampoResponseDTO> erros) {
        this.httpCode = httpCode;
        this.mensagem = mensagem;
        this.codInterno = codInterno;
        this.erros = erros;
    }

    public ExceptionResponseDTO(int httpCode, String mensagem, String codInterno) {
        this.httpCode = httpCode;
        this.mensagem = mensagem;
        this.codInterno = codInterno;
    }

    public ExceptionResponseDTO(int httpCode, String mensagem, String codInterno, ErroStatusPedidoDTO statusPedidoErro) {
        this.httpCode = httpCode;
        this.mensagem = mensagem;
        this.codInterno = codInterno;
        this.statusPedido = statusPedidoErro.getStatusAtualDoPedido().name();
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getCodInterno() {
        return codInterno;
    }

    public List<ErroCampoResponseDTO> getErros() {
        return erros;
    }

    public String getStatusPedido() {
        return statusPedido;
    }
}
