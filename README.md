# 🛡️ GuildAPI - Sistema de Gestão de Aventureiros

## 📌 Descrição

Este projeto consiste na evolução de um sistema de gestão de aventureiros, integrando um banco de dados legado (`schema audit`) com um novo domínio (`schema aventura`).

O objetivo é demonstrar a capacidade de mapear um banco existente utilizando JPA, respeitando suas constraints e relacionamentos, além de expandir o sistema com novas entidades e regras de negócio.

---

## 🏗️ Tecnologias Utilizadas

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Hibernate
- PostgreSQL 17
- Docker
- Lombok
- Maven

---

## 🗄️ Banco de Dados

O sistema utiliza um banco PostgreSQL. Para isso use a imagem do docker para o exercicio, mostrada abaixo.

### Imagem Docker

    ```bash
    docker run -d --name guild-postgres -e POSTGRES_USER=appuser -e POSTGRES_PASSWORD=apppass -e POSTGRES_DB=guilddb -p 5433:5432 leogloriainfnet/postgres-tp2-spring:1.0

# 🚀 Como Executar o Projeto

## Subir o Banco de Dados com Docker Compose
  Suba o docker com o comando abaixo e em seguida rode a aplicação clonada.
  
    ```bash
    docker run -d --name guild-postgres -e POSTGRES_USER=appuser -e POSTGRES_PASSWORD=appuser -e POSTGRES_DB=guilddb -p 5433:5432 leogloriainfnet/postgres-tp2-spring:1.0

# 📁 Estrutura do Projeto

    ```text
    src/main/java/com/example/guildapi/
    ├── advice/              # Exceções personalizadas
    ├── controller/          # Endpoints REST
    ├── service/             # Regras de negócio
    ├── repository/          # Acesso a dados (JPA)
    ├── model/
    │   ├── audit/           # Schema legado
    │   └── aventura/        # Novo domínio
        └── enums/           # Enumeradores
    ├── dto/                 # Data Transfer Objects

# 🧪 Endpoints da API

### Aventureiros

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| GET | `/aventureiros/todos` | Lista todos aventureiros (paginado) |
| GET | `/aventureiros` | Lista com filtros (classe, ativo, nível) |
| GET | `/aventureiros/{id}` | Busca por ID (com companheiro e missões) |
| POST | `/aventureiros` | Cadastra novo aventureiro |
| PATCH | `/aventureiros/{id}` | Atualiza dados do aventureiro |
| PATCH | `/aventureiros/{id}/encerrar` | Encerra vínculo (ativo = false) |
| PATCH | `/aventureiros/{id}/recrutar` | Recruta novamente (ativo = true) |
| PATCH | `/aventureiros/{id}/adicionarCompanheiro` | Adiciona companheiro |
| PATCH | `/aventureiros/{id}/removerCompanheiro` | Remove companheiro |

### Missões

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| GET | `/missoes` | Lista missões com filtros |
| GET | `/missoes/{id}` | Busca missão por ID (com participantes) |

### Relatórios

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| GET | `/relatorios/ranking` | Ranking de aventureiros |
| GET | `/relatorios/missoes` | Relatório de missões com métricas |

---

# 📝 Headers de Paginação

Todos os endpoints de listagem retornam os seguintes headers:

| Header | Descrição |
| :--- | :--- |
| X-Total-Count | Total de registros |
| X-Page | Página atual |
| X-Size | Tamanho da página |
| X-Total-Pages | Total de páginas |
  
# 📊 Schema do Banco

### Schema `audit` (legado)
* **organizacoes**: Organizações/guildas
* **usuarios**: Usuários do sistema
* **roles**: Papéis de acesso
* **permissions**: Permissões
* **user_roles**: Relação usuário-papel
* **role_permissions**: Relação papel-permissão
* **api_keys**: Chaves de integração
* **audit_entries**: Registros de auditoria

### Schema `aventura` (novo domínio)
* **aventureiros**: Aventureiros cadastrados
* **missoes**: Missões disponíveis
* **participacao_missao**: Participação de aventureiros em missões

---

# 👥 Autores
Trabalho acadêmico feito por Leticia Saraiva - Assessment de Spring Boot

# 📄 Licença
Este projeto é para fins educacionais.

