# Ticketmaster Global - High Performance Ticket Platform

Este repositório está sendo construído passo a passo visando a implementação de uma plataforma de venda de ingressos em escala global, projetada para suportar cenários de altíssima concorrência (ex: abertura de vendas para grandes shows).

O projeto adota uma arquitetura de **Microsserviços**, aplicando os padrões de **Arquitetura Hexagonal (Ports & Adapters)**, **Event-Driven Architecture (EDA)** com Apache Kafka, e segurança baseada em **OAuth2/OIDC** com Keycloak.

---

##  Arquitetura do Sistema

O ecossistema é dividido em serviços especializados para garantir o isolamento de contextos de negócio (Bounded Contexts):

* **`catalog-service` (Quarkus 3.x):** Otimizado para leitura e buscas de alta performance. Gerencia os eventos, locais e assentos disponíveis. Compilado nativamente com GraalVM.
* **`order-service` (Spring Boot 3.x):** O núcleo transacional. Gerencia a reserva de ingressos (Hold), validação de regras de negócio, expiração e persistência relacional.
* **`infra/`:** Centraliza a orquestração do ambiente de desenvolvimento através de Docker Compose, contendo os brokers, bancos de dados e o servidor de identidade.
