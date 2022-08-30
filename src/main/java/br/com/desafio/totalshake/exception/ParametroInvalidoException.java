package br.com.desafio.totalshake.exception;

public class ParametroInvalidoException extends Exception{

    public ParametroInvalidoException(String message){
        super(message);
    }

    public ParametroInvalidoException(){
        super("Parâmetro inválido.");
    }
}
