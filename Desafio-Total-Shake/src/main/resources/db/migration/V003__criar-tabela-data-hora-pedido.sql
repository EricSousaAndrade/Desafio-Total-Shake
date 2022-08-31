CREATE TABLE data_hora_status_pedido(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	pedido_id BIGINT,
	data_hora_criado DATETIME,
	data_hora_realizado DATETIME,
	data_hora_cancelado DATETIME,
	data_hora_confirmado DATETIME,
	data_hora_saiu_para_entrega DATETIME,
	data_hora_entrega DATETIME,
	data_hora_pagamento_recusado DATETIME,
	FOREIGN KEY(pedido_id) REFERENCES pedido(id)
);