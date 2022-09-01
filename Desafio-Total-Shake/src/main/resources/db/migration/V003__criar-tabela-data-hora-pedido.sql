CREATE TABLE DATA_HORA_STATUS_PEDIDO(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	pedido_id BIGINT,
	data_hora_criado DATETIME,
	data_hora_realizado DATETIME,
	data_hora_pago DATETIME,
	data_hora_cancelado DATETIME,
	data_hora_confirmado DATETIME,
	data_hora_pronto DATETIME,
	data_hora_saiu_para_entrega DATETIME,
	data_hora_entrega DATETIME,
	data_hora_pagamento_recusado DATETIME,
	FOREIGN KEY(pedido_id) REFERENCES PEDIDO(id)
);