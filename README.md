# 🎬 API de Locadora de Filmes

API desenvolvida em Java 21 com Spring Boot, que simula uma locadora de filmes, permitindo o cadastro de filmes e clientes, locação de filmes e cálculo de multas.  
Conta com documentação via Swagger e suporte a containerização com Docker.

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

## 🛠️ Tecnologias e Ferramentas

- Java 21  
- Spring Boot 3.5.0  
- Spring Data JPA / Hibernate  
- PostgreSQL  
- Swagger (via springdoc-openapi-starter-webmvc-ui)  
- JUnit & Mockito (para testes unitários e de integração)  
- Lombok  
- Docker (com Dockerfile já configurado)  

## 🚀 CI/CD - Integração e Deploy Contínuo

Este projeto possui um pipeline automatizado configurado com GitHub Actions, que realiza as seguintes etapas ao dar push na branch `main`:

- **Build e Testes**:  
  O código é compilado e testado com Maven, usando um profile específico (`ci`) para testes com banco PostgreSQL rodando em container.

- **Empacotamento**:  
  O artefato `.jar` gerado é renomeado e armazenado como artefato do workflow.

- **Build e Push da imagem Docker**:  
  Após o sucesso nos testes, uma imagem Docker é criada e enviada para o Docker Hub, com tags de versão (`latest`, `1.0.x` e o commit SHA).

Dessa forma, garantimos que o que está no Docker Hub é sempre a versão validada e testada da API.

## 🐳 Como rodar com Docker

Você pode baixar a imagem oficial da API no Docker Hub:

```bash
docker pull kleberlz7/api-locadora:latest
docker run -p 9090:9090 kleberlz7/api-locadora:latest
