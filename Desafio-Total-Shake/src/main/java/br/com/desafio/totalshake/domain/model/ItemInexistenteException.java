package br.com.desafio.totalshake.domain.model;

public class ItemInexistenteException extends RuntimeException {

    public ItemInexistenteException(String message) {
        super(message);
    }
}
