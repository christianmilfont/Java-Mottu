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

Explicação das mudanças:

URL de conexão:
````sql
Antes: jdbc:mysql://localhost:3306/mottu_challenge

Agora: jdbc:mysql://db:3306/mottu_challenge

````

A URL foi alterada para db:3306, onde db é o nome do serviço MySQL no docker-compose.yml. Isso permite que o Spring Boot se conecte ao MySQL dentro do container ao invés de tentar se conectar ao localhost.

Outras configurações:

O restante das configurações está OK para o seu cenário, especialmente a configuração do Flyway para migrações de banco de dados e as propriedades do JPA/Hibernate.