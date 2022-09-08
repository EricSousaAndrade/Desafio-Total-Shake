package br.com.desafio.totalshake.exception;

public class NaoEncontradoException extends Throwable{

    public NaoEncontradoException(String message){
        super(message);
    }

    public NaoEncontradoException(){
        super("Item não encontrado.");
    }
}
