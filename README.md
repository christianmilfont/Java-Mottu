# Projeto Java:
Aplicação voltada para criação de motos, para localiza-la em qual parte do pátio está. Complementando nossas aplicações Mobile e .NET que já cadastra os pátios e clientes alocados nas motos, além do nosso IoT que possui um sistema de alerta com seu Dashboard específico encontrando a moto de uma maneira mais física e auditiva!

## Grupo:
- Christian Milfont Rm555345
- Iago Victor Rm558450

### Tecnologias utilizadas:
- MySQL 8.0
- Java SDK 21 (Sdk da oracle utilizada pelos laboratórios da FIAP)
- SpringBoot
- Flaway (Para migrar os dados para o banco, automatizando processos)
- Thymeleaf (Para denonstrar a aplicação montada de uma maneira mais visual utilizando HTML)
- Spring Security (Para permisionamentos dos acessos de determinadas ações da API, configurei o projeto com os arquivos SecurityConfig e na parte de Services nosso UserDetails para definir nossas Roles)
### Comandos essenciais (Utilize os comandos abaixo sobre o banco de dados para conseguir rodar a aplicação sem problemas):
```
    git clone https://github.com/christianmilfont/Java-Mottu.git
    cd Java-Mottu
```


## Resetando senha do MySQL e criando um database

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
ALTER USER 'root'@'localhost' IDENTIFIED BY 'senha123';
(crie exatamente essa senha pois é a que está definida no application properties)
````
## Passo 5: Criar banco de dados:
```
CREATE DATABASE mottu_challenge;
```
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







