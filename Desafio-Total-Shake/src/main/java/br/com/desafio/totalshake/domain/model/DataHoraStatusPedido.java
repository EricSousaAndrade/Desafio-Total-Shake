package br.com.desafio.totalshake.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DATA_HORA_STATUS_PEDIDO")
public class DataHoraStatusPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "data_hora_criado")
    private LocalDateTime dataHoraCriado;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "data_hora_realizado")
    private LocalDateTime dataHoraRealizado;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "data_hora_cancelado")
    private LocalDateTime dataHoraCancelado;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "data_hora_pago")
    private LocalDateTime dataHoraPago;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "data_hora_confirmado")
    private LocalDateTime dataHoraConfirmado;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "data_hora_pronto")
    private LocalDateTime dataHoraPronto;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "data_hora_saiu_para_entrega")
    private LocalDateTime dataHoraSaiuParaEntrega;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "data_hora_entrega")
    private LocalDateTime dataHoraEntrega;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "data_hora_pagamento_recusado")
    private LocalDateTime dataHoraPagamentoRecusado;

    public void salvarDataHoraCriacao(){
        this.dataHoraCriado = LocalDateTime.now();
    }

    public void salvarDataHoraRealizado(){
        this.dataHoraRealizado = LocalDateTime.now();
    }

    public void salvarDataHoraCancelado(){
        this.dataHoraCancelado = LocalDateTime.now();
    }

    public LocalDateTime getDataHoraCriado() {
        return dataHoraCriado;
    }

    public LocalDateTime getDataHoraRealizado() {
        return dataHoraRealizado;
    }

    public LocalDateTime getDataHoraCancelado() {
        return dataHoraCancelado;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
