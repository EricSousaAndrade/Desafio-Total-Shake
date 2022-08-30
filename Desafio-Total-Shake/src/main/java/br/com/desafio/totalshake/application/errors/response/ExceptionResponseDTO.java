package br.com.desafio.totalshake.application.errors.response;

import java.util.List;

public class ExceptionResponseDTO {

    private final int httpCode;
    private final String mensagem;
    private final String codInterno;
    private final List<ErroCampoResponseDTO> erros;

    public ExceptionResponseDTO(int httpCode, String mensagem, String codInterno, List<ErroCampoResponseDTO> erros) {
        this.httpCode = httpCode;
        this.mensagem = mensagem;
        this.codInterno = codInterno;
        this.erros = erros;
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
}
