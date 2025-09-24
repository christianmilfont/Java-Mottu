# Resetando senha do MySQL e criando um database

Este guia rápido mostra como iniciar o MySQL em modo seguro (skip-grant-tables), resetar a senha do usuário root e criar um banco de dados novo.

---

## Passo 1: Parar o MySQL

Pare o serviço MySQL no XAMPP ou no gerenciador de serviços do Windows.

---

## Passo 2: Iniciar MySQL em modo skip-grant-tables

Abra o Prompt de Comando (`cmd`) e navegue até o diretório do MySQL:

```bash
cd C:\xampp\mysql\bin
```
Executando o MySQL ignorando as permissoes
````bash
mysqld --skip-grant-tables

````
Deixe essa janela aberta ja que o MySQL esta rodando sem permissoes

## Passo 3: Abrir outro prompt e conectar ao MySQL
mysql -u root
````bash
mysql -u root

````

## Passo 4: Resetar a senha do root
No prompt de comando rode 
````bash
FLUSH PRIVILEGES;
ALTER USER 'root'@'localhost' IDENTIFIED BY 'nova_senha';
````

### Um erro que aconteceu comigo foi com o arquivo "ABdata1"
Para isso eu tive que configurar ele para aceitar nao somente leitura, mas escrita tambem!
Ele se localiza em  ````C:\xampp\mysql\data````

# Segurança nos links de ação (opcional)
garantir que só o ADMIN veja os links de edição e deleção, usei no template list do Thymeleaf com Spring Security assim:
````commandline
<td>
    <span sec:authorize="hasRole('ROLE_ADMIN')">
        <a th:href="@{'/motos/edit/' + ${moto.id}}">Editar</a> |
        <a th:href="@{'/motos/delete/' + ${moto.id}}"
           onclick="return confirm('Deseja realmente deletar esta moto?');">Deletar</a>
    </span>
</td>

````
# Projeto DevOps - Implementação de Aplicação Java Spring com Docker e MySQL na Nuvem

## Descrição do Projeto

Este projeto tem como objetivo implementar uma aplicação Java Spring Boot com o banco de dados MySQL 8.0, utilizando Docker e Docker Compose para containerização e orquestração dos serviços. A aplicação é configurada para rodar na nuvem e se conecta a um banco de dados MySQL em um container Docker. A solução utiliza o Flyway para migrações de banco de dados e é escalável, fácil de manter e de implantar, seguindo as melhores práticas de DevOps e Cloud Computing.

## Arquitetura da Solução

A arquitetura do sistema é baseada em containers, o que traz flexibilidade, escalabilidade e facilidade de manutenção. A solução é composta por dois principais containers:

1. **Aplicação Java Spring Boot**:
   - Desenvolvida em **Java 21** com **Spring Boot**, a aplicação oferece uma API RESTful para interação com o sistema.
   - A aplicação é empacotada em um container Docker, permitindo que seja executada em qualquer ambiente de forma padronizada.
   - O Spring Boot é configurado para conectar-se a um banco de dados MySQL rodando em outro container.

2. **Banco de Dados MySQL 8.0**:
   - O banco de dados é executado em um container separado usando a imagem oficial do **MySQL 8.0**.
   - O banco de dados é inicializado com o schema definido pelo Flyway, garantindo que a estrutura do banco seja gerenciada de forma consistente entre os diferentes ambientes.

A comunicação entre a aplicação e o banco de dados é feita via rede Docker, garantindo que ambos os containers consigam se comunicar de maneira segura e eficiente.

## Tecnologias Utilizadas

- **Java 21** (JDK 21)
- **Spring Boot** (Framework Java para microserviços)
- **MySQL 8.0** (Banco de dados relacional)
- **Docker** (Containerização)
- **Docker Compose** (Orquestração de containers)
- **Flyway** (Migrations de banco de dados)
- **Azure Container Registry (ACR)** (Armazenamento das imagens Docker)
- **Azure Container Instances (ACI)** (Execução de containers na nuvem)
## Benefícios para o Negócio

A implementação dessa solução traz diversos benefícios para o negócio:

### 1. **Escalabilidade**:
   - A utilização de **Docker** permite que a aplicação seja facilmente escalável. A arquitetura baseada em containers permite aumentar ou reduzir os recursos conforme a demanda, sem a necessidade de reconfigurar a infraestrutura.
   - Com a configuração do **Docker Compose**, é possível subir múltiplos containers de forma simples para aumentar a disponibilidade da aplicação e do banco de dados.

### 2. **Portabilidade**:
   - A aplicação é empacotada em containers, o que garante que ela funcione de maneira consistente em qualquer ambiente, seja no desenvolvimento local, ambientes de testes, ou produção.
   - Isso reduz problemas de "funciona na minha máquina", pois o código e o ambiente estão isolados dentro do container.

### 3. **Facilidade de Implementação e Manutenção**:
   - O uso de **Docker Compose** facilita a configuração e o gerenciamento de múltiplos containers (aplicação e banco de dados). A configuração é declarativa e facilita a replicação do ambiente de desenvolvimento para produção.
   - O **Flyway** controla as migrações do banco de dados, garantindo que todas as alterações de schema sejam aplicadas corretamente e de forma ordenada, evitando problemas de inconsistência entre ambientes.

### 4. **Custo Efetivo**:
   - Ao utilizar containers, é possível reduzir custos com servidores e infraestrutura, já que os containers consomem menos recursos e são mais eficientes.
   - A configuração do MySQL em um container torna o gerenciamento do banco de dados mais simples e eficiente.

### 5. **Facilidade de Testes e Deploy**:
   - Com **Docker**, o processo de deploy é simplificado, já que a imagem Docker contém toda a aplicação e suas dependências.
   - Além disso, é possível configurar facilmente o pipeline de integração contínua (CI) e deploy contínuo (CD) para automatizar os testes e o deploy em diferentes ambientes.

## Como Rodar a Aplicação

### Pré-requisitos

Antes de rodar a aplicação, é necessário ter o **Docker** e o **Docker Compose** instalados no seu sistema.

1. **Instalar Docker**: [Docker - Instalação](https://docs.docker.com/get-docker/)
2. **Instalar Docker Compose**: [Docker Compose - Instalação](https://docs.docker.com/compose/install/)

### Passo a Passo

1. **Clone o Repositório**:

   ```bash
       git clone https://github.com/seu-usuario/projeto-devops.git
       cd projeto-devops
   ```
## Construir a Imagem da Aplicação:
    ```bash
       docker build -t myapp .
    ```
## Subir os Containers com Docker Compose:
    ```bash
        docker-compose up -d
    ```

## Testes:
Exemplo de POST (inserir um usuário):   
    ```bash
        curl -X POST http://localhost:8080/api/users \
          -H "Content-Type: application/json" \
          -d '{"name": "John", "email": "john@example.com"}'
    ```
## Verificar os Containers em Execução e parar:
    ```bash
        docker ps
        docker-compose down
    ```
Explicação das mudanças:

URL de conexão:
````sql
Antes: jdbc:mysql://localhost:3306/mottu_challenge

Agora: jdbc:mysql://db:3306/mottu_challenge

````

A URL foi alterada para db:3306, onde db é o nome do serviço MySQL no docker-compose.yml. Isso permite que o Spring Boot se conecte ao MySQL dentro do container ao invés de tentar se conectar ao localhost.

Outras configurações:

O restante das configurações está OK para o seu cenário, especialmente a configuração do Flyway para migrações de banco de dados e as propriedades do JPA/Hibernate.
