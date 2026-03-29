# 🛡️ GuildAPI - Sistema de Gestão de Aventureiros

## 📌 Descrição

Este projeto consiste na evolução de um sistema de gestão de aventureiros, integrando um banco de dados legado (`schema audit`) com um novo domínio (`schema aventura`).

O objetivo é demonstrar a capacidade de mapear um banco existente utilizando JPA, respeitando suas constraints e relacionamentos, além de expandir o sistema com novas entidades e regras de negócio.

---

## 🏗️ Tecnologias Utilizadas

- Java 21
- Spring Boot 
- Spring Data JPA
- Hibernate
- PostgreSQL (Docker)
- Lombok

---

## 🗄️ Banco de Dados

O sistema utiliza um banco PostgreSQL legado disponível em:

https://hub.docker.com/r/leogloriainfnet/postgres-tp2-spring

### ▶️ Subir o banco:

```bash
docker run -d -p 5432:5432 --name guild-db leogloriainfnet/postgres-tp2-spring