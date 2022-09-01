package br.com.desafio.totalshake.application.errors.exceptions;

public class StatusInvalidoException extends RuntimeException {

    private final String mensagem;

    public StatusInvalidoException(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String getMessage() {
        return mensagem;
    }
}
