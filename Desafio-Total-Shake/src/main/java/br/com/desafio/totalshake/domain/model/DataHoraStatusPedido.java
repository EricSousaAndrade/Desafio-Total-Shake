package br.com.desafio.totalshake.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "data_hora_status_pedido")
public class DataHoraStatusPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraCriado;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraRealizado;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraCancelado;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraPago;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraConfirmado;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraPronto;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraSaiuParaEntrega;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraEntrega;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
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
}
