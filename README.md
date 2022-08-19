# Desafio-Total-Shake

#### Exercício sobre o assunto de Microsserviços.

A ideia é continuar trabalhando com o Total Shake, e vamos utilizar um tipo de arquitetura de microsserviços em que o Total Shake era um monolito e decidimos quebrar a aplicação em parte e transformá-la em microsserviço.

Para iniciar um projeto usando o Spring, podemos usar o site padrão "start.spring.io", esse é o Spring Initializr que facilita para começarmos o projeto do zero.

Então vamos ter dois microsserviços: um de Pedido e outro de
Pagamento, cada um com seu banco de dados e vamos simular a nossa
aplicação na prática. Então façam um CRUD para cada um dos microsserviços, estruturando-os no padrão camada MVC. Crie os pacotes controller, model, repository, service e dto. Utilize o padrão DTO (data transfer object) no projeto para expormos somente os atributos desejados na aplicação.

Crie as APIs dos microsserviços do zero. 

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
