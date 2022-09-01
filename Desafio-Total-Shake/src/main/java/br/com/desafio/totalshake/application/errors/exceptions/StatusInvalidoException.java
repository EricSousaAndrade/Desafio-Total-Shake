package br.com.desafio.totalshake.application.errors.exceptions;

import br.com.desafio.totalshake.domain.model.Status;

public class StatusInvalidoException extends RuntimeException {

    private final String mensagem;
    private final String codInterno;
    private final Status status;

    public StatusInvalidoException(String mensagem, String codInterno, Status status) {
        this.mensagem = mensagem;
        this.codInterno = codInterno;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return mensagem;
    }

    public String getCodInterno() {
        return codInterno;
    }

    public Status getStatus() {
        return status;
    }
}
