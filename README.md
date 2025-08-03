![Java](https://img.shields.io/badge/Java-17-blue?logo=openjdk&logoColor=white)  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?logo=spring&logoColor=white)  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql&logoColor=white)  
![Docker](https://img.shields.io/badge/Docker-Compose-blue?logo=docker&logoColor=white)

# API de Cadastro de Estudantes

API RESTful desenvolvida como solução para o desafio técnico de estágio da Avantsoft. O sistema implementa as  
funcionalidades básicas para cadastrar e consultar estudantes.

## Como Executar o Projeto:

1. Clone o repositório e acesse a pasta recém-criada:

```bash
  git clone https://github.com/niashi/students-registration.git
  cd seu-repositorio
```

2. No terminal, na raiz do projeto, execute o comando para iniciar o container do banco de dados em segundo plano:

```bash
  docker-compose up -d  
```   

3. Rode a Aplicação Spring Boot

Dessa forma, a API estará disponível em `http://localhost:8080`.

---

## Tecnologias Utilizadas

- **Java 17**;
- **Spring Boot**;
- **Spring Data JPA / Hibernate**;
- **PostgreSQL**;
- **Maven**;
- **JUnit + Mockito**;
- **Docker** (Conteinerização do Banco de Dados).

---

## Endpoints

| Ação                       | Método HTTP | URL                 | Exemplo de Requisição                 | Resposta de Sucesso            |
|:---------------------------|:------------|:--------------------|:--------------------------------------|:-------------------------------|
| Criar novo estudante       | `POST`      | `/api/student`      | `{"name": "Ana Silva", "grade": 9.5}` | `204 No Content`               |
| Listar todos os estudantes | `GET`       | `/api/student`      | -                                     | `200 OK` + lista de estudantes |
| Buscar estudante por ID    | `GET`       | `/api/student/{id}` | -                                     | `200 OK` + dados do estudante  |

---  

## Validações e Regras de Negócio

- Cálculo da primeira letra não repetida do nome do estudante nas respostas das requisições do tipo `GET`.
- Os dados de entrada são validados, de modo que deve sempre haver um `Nome` e uma `Nota`. Além disso, esta última deve
  estar entre 0 e 10.
- Em caso de erros de entrada, respostas claras e formatadas são apresentadas:

```json
{
  "grade": "Grade must be at most 10",
  "name": "Field must not be blank"
}
```

---

## Respostas de Erro da API

| Erro                                                                       | Exemplo de Requisição                         | Resposta de Erro                                              |
|:---------------------------------------------------------------------------|:----------------------------------------------|---------------------------------------------------------------|
| Ao criar um novo estudante, o cliente não passa o campo `name`             | `{"name": "", "grade": 10}` - `{"grade": 10}` | `400 BAD REQUEST` + `{ "name": "Field must not be blank" }`   |
| Ao criar um novo estudante, o cliente não passa o campo `grade`            | `{"name": "Example"}`                         | `400 BAD REQUEST` + `{ "grade": "Field must not be null" }`   |
| Ao criar um novo estudante, o campo `grade` recebe um valor `menor que` 0  | `{"name": "Example", "grade": -1}`            | `400 BAD REQUEST` + `{ "grade": "Grade must be at least 0" }` |
| Ao criar um novo estudante, o campo `grade` recebe um valor `maior que` 10 | `{"name": "Example", "grade": 11}`            | `400 BAD REQUEST` + `{ "grade": "Grade must be at most 10" }` |

---

## Decisões Técnicas

Durante o desenvolvimento, algumas decisões de arquitetura e design foram tomadas visando seguir as melhores práticas.

### 1. Arquitetura em Camadas (Controller, Service, Repository)

O sistema possui as seguintes camadas, responsáveis pela separação de responsabilidades:

- **Controller:** lida com as requisições HTTP à API, chamando o serviço.
- **Service:** abrange a regra de negócios e orquestra as operações, chamando o repositório.
- **Repository:** acessa os dados do banco, abstraindo a comunicação.

Essa separação torna o código mais organizado, testável e fácil de manter.

---

### 2. Uso de DTOs (Data Transfer Objects)

Foram utilizados DTOs de entrada e saída de dados (`StudentRequestDTO`, `StudentResponseDTO`). Isso traz algumas
vantagens:

- Evita a exposição de campos internos da entidade, protegendo a aplicação contra vulnerabilidades, pois apenas os
  campos presentes no DTO serão conhecidos.
- A estrutura interna do banco de dados pode mudar sem quebrar o contrato com os clientes da API.
- Permite validar campos de entrada através da anotação `@Valid` sem poluir a entidade `Student`.

---

### 3. Validação Declarativa e Tratamento de Exceções Global

Em vez de validações manuais com `if/else`, optou-se pelo uso de anotações do **Jakarta Validation** (`@NotBlank`,
`@Min`, `@Max`) diretamente no DTO de entrada, o que torna o código mais limpo e declarativo.

Para fornecer respostas de erro claras e consistentes, foi criada a classe `GlobalExceptionHandler`, que centraliza o
tratamento de exceções de validação, retornando um JSON formatado com os erros, o que melhora a experiência dos usuários
da API.

---

### 4. Docker para o Ambiente de Desenvolvimento

Para garantir que o ambiente de desenvolvimento seja isolado e reprodutível, o banco de dados PostgreSQL foi  
configurado via **Docker Compose**. Dessa forma, qualquer desenvolvedor pode subir a infraestrutura necessária via
`docker-compose up`.

---

### 5. Processar a primeira letra não repetida em tempo real ou pré-processar?

O processamento em tempo real da primeira letra não repetida em cada nome em vez de salvá-la numa coluna da tabela
`Student` teve como motivação a integridade dos dados.
Caso o nome do estudante mude, a primeira letra não repetida poderá mudar também, e calculá-la somente no momento da
leitura garante que isso não seja um problema e mantém a integridade do dado.

---

### 6. Classe wrapper `Double` na StudentRequestDTO

No DTO de recebimento dos dados do estudante (`StudentRequestDTO`), optei pelo uso do campo `grade` como sendo `Double`
em vez da primitiva `double`, pois dessa forma a validação de campo nulo poderia ser efetivada, tendo em visto que a
classe `Double` admite valores nulos.

--- 

### 7. Mapper para transformar dados de saída

Optei pelo uso de um `StudentMapper` para transformar a entidade `Student` nos dados que de fato serão enviados para o
cliente (`StudentResponseDTO`), pois isso mantém o serviço limpo e responsável somente pelas regras de negócio.

---

## Próximos Ajustes

Futuramente, com a API ficando mais robusta e ganhando mais endpoints, alguns ajustes poderiam ser feitos:

- Criar um `.env` para armazenar as variáveis de ambiente e não expor dados sensíveis no `application.properties` e no
  `docker-compose.yml`;
- Criar um teste de integração para o banco de dados, tendo em vista que o teste unitário da `StudentService` já foi
  feito.