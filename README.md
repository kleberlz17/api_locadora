# 🎬 API de Locadora de Filmes

API desenvolvida em **Java 21** com **Spring Boot**, que simula uma locadora de filmes, permitindo o cadastro de filmes e clientes, locação de filmes e cálculo de multas.  
Conta com documentação via Swagger e suporte a containerização com Docker.

---

## ✅ Funcionalidades

- Criação e gerenciamento de filmes  
- Cadastro e atualização de clientes  
- Realização de locações de filmes para clientes  
- Devolução de filmes com cálculo automático de multa em caso de atraso  
- Consulta de locações por cliente ou por filme  
- Consulta de todos os filmes e clientes  
- Validações com anotações do Bean Validation  
- Conversão entre entidades e DTOs feita por lógica própria (sem MapStruct), com objetivo de treinar e reforçar o entendimento  
- Testes unitários e de integração implementados com JUnit e Mockito  
- Tratamento global de exceções com mensagens personalizadas  
- Documentação interativa gerada com Swagger  
- Suporte a containerização com Docker  

---

## 🛠️ Tecnologias e Ferramentas

- **Java 21**  
- **Spring Boot 3.5.0**  
- **Spring Data JPA / Hibernate**  
- **PostgreSQL**  
- **Swagger** (via `springdoc-openapi-starter-webmvc-ui`)  
- **JUnit & Mockito** (para testes unitários e de integração)  
- **Lombok**  
- **Docker** (com `Dockerfile` já configurado)  

---

## 🚀 Observações

- API **não possui autenticação**, com foco na prática de desenvolvimento e construção de APIs REST.   

- Documentação interativa gerada via Swagger.

---

👉 Acesse a documentação da API: [http://localhost:9090/swagger-ui/index.html](http://localhost:9090/swagger-ui/index.html)
