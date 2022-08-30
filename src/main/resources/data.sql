USE week3;

describe item_pedido;

CREATE TABLE tb_pedido(
ID int PRIMARY KEY AUTO_INCREMENT,
dataHora timestamp,
status varchar(30)
);

CREATE TABLE tb_item_pedido(
ID int PRIMARY KEY AUTO_INCREMENT,
quantidade int,
descricao varchar(35),
pedido_id int,
FOREIGN KEY (pedido_id)
REFERENCES tb_pedido(id)
);