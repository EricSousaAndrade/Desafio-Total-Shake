package br.com.desafio.totalshake.application.errors;

public enum CodInternoErroApi {
    AP001("AP-001", "Um ou mais campos invalidos"),
    AP002("AP-002", "Pedido inexistente"),
    AP003("AP-003", "Esse item não existe no pedido"),
    AP004("AP-004", "Quantidade inválida, informe um valor maior que 0")
    ;


    private final String codigo;
    private final String mensagem;


    CodInternoErroApi(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
