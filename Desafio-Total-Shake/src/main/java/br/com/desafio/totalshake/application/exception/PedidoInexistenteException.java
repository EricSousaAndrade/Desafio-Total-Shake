package br.com.desafio.totalshake.application.exception;

public class PedidoInexistenteException extends RuntimeException{
    public PedidoInexistenteException(String message) {
        super(message);
    }
}
