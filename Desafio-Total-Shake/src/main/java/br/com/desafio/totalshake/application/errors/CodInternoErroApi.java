package br.com.desafio.totalshake.application.errors;

public enum CodInternoErroApi {
    AP001("AP-001", "Um ou mais campos invalidos"),
    AP201("AP-201", "Pedido inexistente"),
    AP202("AP-202", "Esse item não existe no pedido"),
    AP203("AP-203", "Quantidade inválida, informe um valor maior que 0"),
    AP301("AP-301", "Operação inválida, verifique o Status do pedido"),
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
