# 🛡️ GuildAPI - Sistema de Gestão de Aventureiros

## 📌 Descrição

Este projeto consiste na evolução de um sistema de gestão de aventureiros, integrando um banco de dados legado (`schema audit`) com um novo domínio (`schema aventura`).  
Além disso, foram implementados:

- **Consultas estratégicas** sobre missões usando uma view com cache para melhor desempenho.
- **Marketplace da Guilda** com buscas textuais, filtros e agregações utilizando **Elasticsearch**.

O objetivo é demonstrar a capacidade de mapear bancos relacionais e de busca, respeitando regras de negócio e boas práticas de arquitetura.

---

## 🏗️ Tecnologias Utilizadas

- Java 21
- Spring Boot 3.x
- Spring Data JPA / Hibernate
- Spring Data Elasticsearch
- PostgreSQL 17
- Elasticsearch 9.x
- Docker
- Lombok
- Maven

---

## 🗄️ Banco de Dados (PostgreSQL)

O sistema utiliza um banco PostgreSQL baseado na imagem do exercício:  
`leogloriainfnet/postgres-tp2-spring:2.0-win`.

### ▶️ Subir o container

```bash
docker run -d \
  --name guild-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=password \
  -e POSTGRES_DB=postgres \
  -p 5433:5432 \
  leogloriainfnet/postgres-tp2-spring:2.0-win
```

O índice `guilda_loja` já está populado com 400 produtos.

## 🚀 Como Executar o Projeto

### 1. Subir os containers (PostgreSQL e Elasticsearch)

```bash
# PostgreSQL
docker run -d --name guild-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=postgres -p 5433:5432 leogloriainfnet/postgres-tp2-spring:2.0-win

# Elasticsearch
docker run -d --name guilda-es -p 9200:9200 -e ES_JAVA_OPTS="-Xms512m -Xmx512m" leogloriainfnet/elastic-tp2-spring:1.0-alternativo
```
### 2. Executar a aplicação Spring Boot

## 📁 Estrutura do Projeto

```text
    src/main/java/com/example/guildapi/
    ├── advice/                # Tratamento de exceções (ControllerAdvice)
    ├── config/                # Configurações de cache
    ├── controller/            # Endpoints REST
    ├── dto/                   # Data Transfer Objects
    ├── exceptions/            # Exceções personalizadas
    ├── model/
    │   ├── audit/             # Schema legado
    │   ├── aventura/          # Domínio principal
    │   ├── elastic/           # Documentos do Elasticsearch
    │   └── operacoes/         # View tática (PainelTaticoMissao)
    │   └── enums/             # Enumerates
    ├── repository/            # Acesso a dados (JPA e Elasticsearch)
    └── service/               # Regras de negócio
```

## 🧪 Endpoints da API

### 🎯 Aventureiros

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/aventureiros/todos` | Lista todos aventureiros (paginado) |
| GET | `/aventureiros` | Lista com filtros (classe, ativo, nível) |
| GET | `/aventureiros/{id}` | Busca por ID (com companheiro e missões) |
| POST | `/aventureiros` | Cadastra novo aventureiro |
| PATCH | `/aventureiros/{id}` | Atualiza dados do aventureiro |
| PATCH | `/aventureiros/{id}/encerrar` | Encerra vínculo (ativo = false) |
| PATCH | `/aventureiros/{id}/recrutar` | Recruta novamente (ativo = true) |
| PATCH | `/aventureiros/{id}/adicionarCompanheiro` | Adiciona companheiro |
| PATCH | `/aventureiros/{id}/removerCompanheiro` | Remove companheiro |

### 🎯 Missões

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/missoes` | Lista missões com filtros |
| GET | `/missoes/{id}` | Busca missão por ID (com participantes) |
| GET | `/missoes/top15dias` | **TP3 – Ranking tático**: top 10 missões dos últimos 15 dias (ordenado por `indice_prontidao`) |

### 📊 Relatórios (Participações)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/participacoes/ranking` | Ranking de aventureiros |
| GET | `/participacoes/missoes` | Relatório de missões com métricas |

### 🛒 Marketplace (Elasticsearch)

#### **Parte A – Buscas textuais**

| Método | Endpoint | Exemplo |
|--------|----------|---------|
| GET | `/produtos/busca/nome?termo={termo}` | `?termo=espada` |
| GET | `/produtos/busca/descricao?termo={termo}` | `?termo=dragões` |
| GET | `/produtos/busca/frase?termo={frase}` | `?termo=Item utilizado em batalhas` |
| GET | `/produtos/busca/fuzzy?termo={termo}` | `?termo=espdaa` (tolerante a erro) |
| GET | `/produtos/busca/multicampos?termo={termo}` | `?termo=dragão` (nome + descrição) |

#### **Parte B – Filtros**

| Método | Endpoint | Exemplo |
|--------|----------|---------|
| GET | `/produtos/busca/com-filtro?termo={termo}&categoria={cat}` | `?termo=dragões&categoria=armas` |
| GET | `/produtos/busca/faixa-preco?min={min}&max={max}` | `?min=100&max=300` |
| GET | `/produtos/busca/avancada?categoria={cat}&raridade={rar}&min={min}&max={max}` | `?categoria=armas&raridade=comum&min=100&max=400` |

#### **Parte C – Agregações**

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/produtos/agregacoes/por-categoria` | Quantidade de produtos por categoria |
| GET | `/produtos/agregacoes/por-raridade` | Quantidade por raridade |
| GET | `/produtos/agregacoes/preco-medio` | Preço médio de todos os produtos |
| GET | `/produtos/agregacoes/faixas-preco` | Distribuição por faixas: `*-100`, `100-300`, `300-700`, `700-*` |

## 📝 Headers de Paginação

Endpoints de listagem (`/aventureiros`, `/missoes`, etc.) retornam os seguintes headers:

| Header | Descrição |
|--------|-----------|
| `X-Total-Count` | Total de registros |
| `X-Page` | Página atual |
| `X-Size` | Tamanho da página |
| `X-Total-Pages` | Total de páginas |

## ⚡ Estratégia de Cache (TP3 – Questão 2)

O endpoint `/missoes/top15dias` consulta a view `operacoes.vw_painel_tatico_missao`, que envolve junções e agregações. Para evitar consultas repetidas ao banco, foi implementado cache na camada de serviço utilizando:

- `@EnableCaching` + `@Cacheable` (Spring Cache).
- Cache `ConcurrentMapCacheManager` (em memória) com expiração automática a cada 5 minutos (`@Scheduled` + `@CacheEvict`).

Com isso, a primeira requisição após a expiração executa a consulta pesada; as demais dentro do intervalo de 5 minutos retornam instantaneamente do cache.

## 📊 Schema do Banco

### Schema `audit` (legado)

- `organizacoes` – Organizações/guildas
- `usuarios` – Usuários do sistema
- `roles` – Papéis de acesso
- `permissions` – Permissões
- `user_roles` – Relação usuário-papel
- `role_permissions` – Relação papel-permissão
- `api_keys` – Chaves de integração
- `audit_entries` – Registros de auditoria

### Schema `aventura` (domínio)

- `aventureiros` – Aventureiros cadastrados
- `missoes` – Missões disponíveis
- `participacao_missao` – Participação de aventureiros em missões

### Schema `operacoes` (TP3)

- `vw_painel_tatico_missao` – View (ou materialized view) com métricas estratégicas das missões.

---

# Anexo
Segue o anexo dos prints de testes do Elasticsearch [Screenshots_AT.pdf](https://github.com/user-attachments/files/26639702/Screenshots_AT.pdf)

# 👥 Autores
Trabalho acadêmico feito por Leticia Saraiva - Assessment de Spring Boot

# 📄 Licença
Este projeto é para fins educacionais.

