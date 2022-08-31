# Desafio ToTal Shake: Semana 4

#### Criação de outra API (pagamento) e integração com API de pedido

A ideia é continuar trabalhando com a API de PEDIDO criada na etapa 3 e criar uma nova API de PAGAMENTOS.
Vamos ter dois microsserviços: um de Pedido e outro de Pagamento, cada um com seu banco de dados e vamos simular a nossa aplicação na prática integrando as duas aplicações.
Então façam um CRUD para cada um dos microsserviços, estruturando-os no padrão camada MVC.
Crie os pacotes controller, model, repository, service e dto.
Utilize o padrão DTO (data transfer object) no projeto para expormos somente os atributos desejados na aplicação.

- Spring Web
- Spring Data JPA
- Dependência para o banco de dados para o banco de sua preferência
- Spring Boot DevTools
- Lombok
- Validation
- Feign 
- Flyway Migration (opcional)

## API Pagamentos:

### Classe Pagamento

- private Long id (Chave primária, autogerada);
- private BigDecimal valor;
- private String nome;
- private String numero;
- private String expiracao;
- private String codigo;
- private Status status;
- private Long pedidoId;
- private FormaDePagamento formaDePagamento. 
  
  Obs: Para os atributos, pratiquem a ideia de programação defensiva, realizando as validações através das annotations do Validation, desta forma:

- O atributo valor deve receber somente números positivos, e não dever ser nulo.
- Nome não deve estar em branco, e deve conter no máximo 100 caracteres.
- Numero não deve estar em branco, e deve conter no máximo 100 caracteres.
- Código não deve estar em branco, e deve conter no mínimo  3 e no máximo 3 caracteres
- Enum Status, não deve ser nula. Dica: utilizar também o Enumerated(EnumType.STRING)


### Enum Status

- CRIADO;
- CONFIRMADO;
- CANCELADO.
  
### Enum FormaDePagamento

- PIX;
- DINHEIRO;
- CARTAO_DEBITO;
- CARTAO_CREDITO.

## Desafio Opcional 1:

Quando o pagamento for confirmado, uma mensagem será enviada para o microsserviço de pedidos, indicando que o pedido está pago.

Para isso, o microsserviço de pagamento deve realizar  uma requisição do tipo PUT passando o Id do pedido, e o status como pago. O microsserviço de pedidos recebe a requisição, e troca o status.

Para realizar a comunicação síncrona, utilize o Spring Cloud OpenFeign, que é uma solução do Spring que utiliza um cliente HTTP justamente para fazer integrações de Back-End para Back-End.

---

 ## Desafio Opcional 2: Versionamento do Banco de dados com Migration
  
Vamos usar as migrations para criar as tabelas no banco de dados com o versionamento. Pensando em boas práticas, é interessante a ideia de utilizar o  Flyway Migration, que faz um versionamento de controle do nosso banco de dados, e a partir disso conseguimos verificar o histórico da evolução do banco de dados, quando a tabela foi criada, alterada e se foi incluído algum campo, por exemplo.
  
Dentro da pasta db.migration, crie o arquivo chamado V1__criar_tabela_pagamentos.sql
Para ganharmos tempo, add o seguinte código dentro do arquivo:
  
```
CREATE TABLE pagamentos (
 id bigint(20) NOT NULL AUTO_INCREMENT,
 valor decimal(19,2) NOT NULL,
 nome varchar(100) DEFAULT NULL,
 numero varchar(19) DEFAULT NULL,
 expiracao varchar(7) DEFAULT NULL,
 codigo varchar(3) DEFAULT NULL,
 status varchar(255) NOT NULL,
 forma_de_pagamento_id bigint(20) NOT NULL,
 pedido_id bigint(20) NOT NULL,
PRIMARY KEY (id)
);
```

Por fim, quando rodarmos a aplicação Spring Boot, o Flyway, de forma automática ela cria a base de dados para gerar a tabela de pagamentos de acordo com o código abaixo, que deve estar no arquivo application.properties, informando o driver do banco de sua preferência, a URL do banco de dados que queremos criar e o nome e a senha do usuário. 
  
  ```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/alurafood-pagamento?createDatabaseIfNotExist=true
spring.datasource.username=seuusernamedobanco
spring.datasource.password=suasenhadobanco
spring.jpa.show-sql=true
  ```
---

## Desafio Opcional 3 - Service Discovery (Serviço de Descoberta): 
  
 Service Discovery é um tipo de catálogo que vai conter o endereço de todas as instâncias e microsserviços que estão registrados nele.
  
Vantagens de se utilizar o service discovery em Microsserviços:
-   Ter um serviço onde as instâncias possam se registrar;
-   Descobrir de forma dinâmica, o endereço das instâncias do serviço que deseja consumir.
  
Então a proposta é termos um projeto que vai atuar no service discovery. Para isso, vamos ter um servidor e os microsserviços que vão poder se registrar nele para ocorrer a comunicação.

Para criarmos o servidor com o service discovery vamos usar o Eureka Server. 
Para o service discovery, crie o projeto chamado "server" utilizando o SpringInitializr. Como parte da configuração, adicione a dependência Eureka Discovery Server.  

---

### Conteúdo Auxiliar

Abaixo estão links de apoio que poderão auxiliar na resolução do desafio.

| Assunto | Link |
| ------ | ------ |
| Microsserviços | [https://www.opus-software.com.br/os-beneficios-da-arquitetura-de-micro-servicos/] |
| Feign | [https://domineospring.wordpress.com/2017/06/02/feign-uma-forma-simples-para-consumir-servicos/] |
| Feign | [https://www.youtube.com/watch?v=zRZuZrwYYc0] |
| Migrations | [https://www.youtube.com/results?search_query=migration+java] |
| Discovery | [https://www.youtube.com/watch?v=Td_6NcZJ4WM] |
| Discovery | [https://www.youtube.com/watch?v=cOQQYrhYH4U] |
| Discovery | [https://www.youtube.com/watch?v=-gLLeoS1m6s] |  
| Discovery | [https://4soft.co/tutorial-how-to-implement-service-discovery-with-eureka-java/] |

---
---

# Desafio ToTal Shake: Semana 3

#### Criação de um CRUD

A ideia deste desafio é continuar trabalhando com o ToTal Shake, sendo que esta semana vamos praticar a criação de entidades e a conexão com o banco de dados.

Desta forma, o objetivo principal é a criação da entidade Pedido e Item Pedido (especificado abaixo) e a realização da conexão com o banco de dados de sua preferência. Para tal, façam um CRUD, estruturando no padrão camada MVC. Crie os pacotes controller, model, repository, service e dto. Utilize o padrão DTO (data transfer object) no projeto para expormos somente os atributos desejados na aplicação.

Crie exceções personalizadas para cada possivel erro que você encontrar, como por exemplo PedidoNotFoundException.

Adicione as dependências que vamos precisar para o projeto. Vamos usar estas, e mais as que você julgar necessárias: 

- Spring Web
- Spring Data JPA
- Dependência para o banco de dados para o banco de sua preferência
- Spring Boot DevTools
- Lombok
- Validation

## API Pedidos:

### Classe Pedido
- private Long id (Chave primária, autogerada);
- private LocalDateTime dataHora;
- private Status status;
- private List< ItemPedido > itensPedidoList.

 ### Obs: Para os atributos, pratiquem a ideia de programação defensiva, realizando as validações através das annotations do Validation, desta forma:
- Nenhum atributo pode deve estar em branco
- Enum Status, não deve ser nula. Dica: utilizar também o Enumerated(EnumType.STRING)

### Enum Status
- REALIZADO;
- CANCELADO;
- PAGO;
- NAO_AUTORIZADO;
- CONFIRMADO;
- PRONTO;
- SAIU_PARA_ENTREGA;
- ENTREGUE.

### Classe Item Pedido
- private Long id (Chave primária, autogerada);
- private Integer quantidade;
- private String descricao;
- private Pedido pedido.


### Conteúdo Auxiliar
Abaixo estão links de apoio que poderão auxiliar na resolução do desafio.

| Assunto | Link |
| ------ | ------ |
| Spring Boot | [https://www.devmedia.com.br/primeiros-passos-com-o-spring-boot/33654#Spring] |
| Spring Boot | [https://www.youtube.com/watch?v=JLShKaS0XxY] |
| Spring Initializr | [https://start.spring.io/] |
| API RESTful | [https://mari-azevedo.medium.com/construindo-uma-api-restful-com-java-e-spring-framework-46b74371d107] |
| Boas práticas Rest | [https://www.youtube.com/watch?v=P-juXKmJy_g] |
| ModelMapper | [https://www.youtube.com/watch?v=HU7bfKG8nV4] |
| ModelMapper| [https://medium.com/@hectordemedeiros/criando-uma-api-rest-em-springboot-utilizando-modelmapper-b56515c62e84] |

### Desafio Extra
Quem conseguir terminar o desafio acima, pode tentar utilizar a paginação do java para paginar as buscar por muitos dados.
