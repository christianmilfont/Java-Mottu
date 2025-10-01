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
# Projeto DevOps - Implementação de Aplicação Java Spring com Docker, MySQL e Azure

## Descrição do Projeto

Este projeto tem como objetivo implementar uma aplicação Java Spring Boot com o banco de dados MySQL 8.0, utilizando Docker e Docker Compose para containerização e orquestração dos serviços. A aplicação será configurada para rodar na nuvem, usando o **Azure Container Instances (ACI)** para execução do container da aplicação e **Azure Container Registry (ACR)** para armazenar a imagem Docker da aplicação.

## Arquitetura da Solução

A arquitetura da solução é composta por dois componentes principais:

1. **Aplicação Java Spring Boot**:
   - A aplicação Java Spring Boot será empacotada em um container Docker.
   - Ela se conecta a um banco de dados MySQL rodando em um container separado (no local ou na nuvem).

2. **Banco de Dados MySQL 8.0**:
   - O banco de dados MySQL 8.0 será executado no **Azure Container Instances (ACI)** ou em um serviço PaaS de banco de dados, como o **Azure Database for MySQL**.

A solução vai permitir rodar a aplicação na nuvem de forma escalável e de fácil manutenção.

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

### 1. **Escalabilidade**:
   - A aplicação pode ser facilmente escalada para atender a maiores volumes de tráfego. A utilização de ACI permite aumentar a capacidade de execução de containers conforme a demanda.

### 2. **Portabilidade**:
   - Com o uso de **Docker**, a aplicação pode ser executada em qualquer ambiente de maneira consistente, seja localmente ou em diferentes ambientes de nuvem (neste caso, **Azure**).

### 3. **Facilidade de Implementação e Manutenção**:
   - A implementação e manutenção são simplificadas, pois o uso de **Docker Compose** facilita a configuração e inicialização de múltiplos containers (aplicação e banco de dados).
   - O **Flyway** automatiza o gerenciamento de migrações de banco de dados.

### 4. **Integração com Azure**:
   - A solução pode ser facilmente integrada ao **Azure** para aproveitar sua infraestrutura escalável e resiliente.
   - **ACR** permite armazenar e versionar as imagens Docker da aplicação.
   - **ACI** oferece uma maneira simples de rodar containers na nuvem sem a necessidade de gerenciar infraestrutura de servidores.

### 5. **Custo Efetivo**:
   - Usar ACI e ACR oferece uma solução econômica, pois você paga apenas pelo tempo de execução dos containers e pela quantidade de armazenamento utilizado no ACR, sem a sobrecarga de gerenciar máquinas virtuais ou servidores.

## Como Rodar a Aplicação

### 1. **Pré-requisitos**

Antes de rodar a aplicação, é necessário ter o **Docker**, **Docker Compose** e **Azure CLI** instalados no seu sistema.

- **Instalar Docker**: [Docker - Instalação](https://docs.docker.com/get-docker/)
- **Instalar Docker Compose**: [Docker Compose - Instalação](https://docs.docker.com/compose/install/)
- **Instalar Azure CLI**: [Azure CLI - Instalação](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli)

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

# Passo a Passo para Implementação no Azure

Agora que a aplicação está funcionando localmente, podemos enviá-la para o Azure Container Registry (ACR) e rodá-la no Azure Container Instances (ACI).

### 3.1. Criar um Azure Container Registry (ACR)
Como usar

Salve como deploy_full_aci.sh

Dê permissão de execução:

chmod +x deploy_full_aci.sh


Execute:

./deploy_full_aci.sh

📄 Resultado da execução

Dois containers rodando no Azure:

mottu-db com MySQL

mottu-app com sua aplicação

Ambos se comunicam pela VNET

Ao final você verá:

O IP público da aplicação

Os logs da aplicação Java tentando conectar no banco

✅ Como mostrar na entrega:

Print do IP público da aplicação:
Copie o que o script te mostrar (ex: http://20.50.44.11:8080) e acesse via navegador

Print dos logs:
O script já mostra os logs, com saída como:

Connecting to MySQL...
Connected successfully!
Executing query...


Demonstração em vídeo (opcional):
Grave com OBS ou use prints organizados em PDF mostrando:

A criação dos recursos no Azure

A URL funcionando

Os logs no terminal

✅ Checklist da entrega final
Entrega	Feito?
Aplicação rodando no Azure ACI	✅
Banco MySQL rodando no Azure ACI	✅
Ambos conectados via VNET	✅
Logs da aplicação acessando o banco	✅
IP público acessível	✅
Dockerfile funcionando	✅
Script automatizado deploy_full_aci.sh
### A aplicação estará acessível via o IP público atribuído ao container ACI.
