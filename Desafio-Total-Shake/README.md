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
