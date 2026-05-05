
# Salao-beleza-API

API REST para gerenciamento de salão de beleza: clientes, profissionais, serviços, agendamentos e pagamentos.

## Desenvolvimento com IA

Este projeto foi desenvolvido com o suporte do **Claude Code** e do framework 
**[Superpowers](https://github.com/Dev-MilenaSantos/superpowers)** — um sistema 
de habilidades agênticas que potencializa o desenvolvimento de software com IA.

O Superpowers auxiliou no processo de desenvolvimento através de fluxos 
estruturados como brainstorming, planejamento de implementação, desenvolvimento 
orientado a testes (TDD) e revisão de código — garantindo qualidade e 
consistência ao longo do projeto.

Todo o planejamento, decisões de arquitetura e condução do projeto foram de 
minha autoria. 

O uso de IA no desenvolvimento de software é uma competência moderna e estratégica.

## Tecnologias

- Java 21 · Spring Boot 4.0 · Spring Data JPA
- H2 (banco em memória) · Bean Validation · Lombok
- JUnit 5 · AssertJ

## Como executar

```bash
./mvnw spring-boot:run
```

A aplicação sobe na porta **8080**.

Console do banco H2: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:salao`
- Usuário: `sa` · Senha: (vazia)

## Como rodar os testes

```bash
./mvnw test
```

27 testes, 0 falhas.

## Endpoints

### Clientes `/clientes`
| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por ID |
| POST | `/clientes` | Cria novo |
| PUT | `/clientes/{id}` | Atualiza |
| DELETE | `/clientes/{id}` | Remove |

### Profissionais `/profissionais`
| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/profissionais` | Lista todos |
| GET | `/profissionais/{id}` | Busca por ID |
| POST | `/profissionais` | Cria novo |
| PUT | `/profissionais/{id}` | Atualiza |
| DELETE | `/profissionais/{id}` | Remove |

### Serviços `/servicos`
| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/servicos` | Lista todos |
| GET | `/servicos/{id}` | Busca por ID |
| POST | `/servicos` | Cria novo |
| PUT | `/servicos/{id}` | Atualiza |
| DELETE | `/servicos/{id}` | Remove |

### Agendamentos `/agendamentos`
| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/agendamentos` | Lista todos |
| GET | `/agendamentos/{id}` | Busca por ID |
| GET | `/agendamentos/cliente/{id}` | Por cliente |
| GET | `/agendamentos/profissional/{id}` | Por profissional |
| POST | `/agendamentos` | Cria (valida conflito de horário) |
| PUT | `/agendamentos/{id}/status` | Atualiza status |
| DELETE | `/agendamentos/{id}` | Cancela |

### Pagamentos `/pagamentos`
| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/pagamentos` | Lista todos |
| GET | `/pagamentos/{id}` | Busca por ID |
| GET | `/pagamentos/agendamento/{id}` | Por agendamento |
| POST | `/pagamentos` | Registra pagamento |

## Exemplos de requisição

### Criar cliente
```json
POST /clientes
{
  "nome": "Ana Silva",
  "telefone": "21999999999",
  "email": "ana@email.com"
}
```

### Criar agendamento
```json
POST /agendamentos
{
  "clienteId": 1,
  "profissionalId": 1,
  "servicoId": 1,
  "dataHora": "2026-05-10T10:00:00"
}
```

### Registrar pagamento
```json
POST /pagamentos
{
  "agendamentoId": 1,
  "valor": 80.00,
  "formaPagamento": "PIX"
}
```

Formas de pagamento: `DINHEIRO`, `CARTAO`, `PIX`

Status de agendamento: `CONFIRMADO`, `CANCELADO`, `CONCLUIDO`, `NO_SHOW`

## Tratamento de erros

| Status | Situação |
|--------|----------|
| 400 | Dados inválidos (validação) |
| 404 | Recurso não encontrado |
| 409 | Conflito (horário ocupado ou pagamento duplicado) |
