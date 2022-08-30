package br.com.desafio.totalshake.application.errors.response;

public class ErroCampoResponseDTO {

    private final String mensagem;
    private final String erro;

    public ErroCampoResponseDTO(String mensagem, String erro) {
        this.mensagem = mensagem;
        this.erro = erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getErro() {
        return erro;
    }
}

