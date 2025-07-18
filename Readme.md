Spring Boot Microservice Appliaction



# 📚 Bookstore Microservices Application

This project is a microservices-based **bookstore application**, designed as a learning project inspired by the [Microservices Project Series on YouTube](https://www.youtube.com/playlist?list=PLuNxlOYbv61g_ytin-wgkecfWDKVCEDmB).



![alt text](bookstore-spring-microservices.png)

---

## 🧩 Modules Overview

### 1. 📦 catalog-service
> Provides REST APIs for managing the product catalog (books).

- **Tech Stack**:  
  `Spring Boot`, `Spring Data JPA`, `PostgreSQL`

---

### 2. 🛒 order-service
> Manages orders and publishes order-related events to the message broker.

- **Tech Stack**:  
  `Spring Boot`, `Spring Security OAuth2`, `Keycloak`,  
  `Spring Data JPA`, `PostgreSQL`, `RabbitMQ`

---

### 3. 📧 notification-service
> Listens to order events and sends user notifications.

- **Tech Stack**:  
  `Spring Boot`, `RabbitMQ`

---

### 4. 🚪 api-gateway
> Serves as an API Gateway to internal backend services (`catalog-service`, `order-service`).

- **Tech Stack**:  
  `Spring Boot`, `Spring Cloud Gateway`

---

### 5. 🌐 bookstore-webapp
> Customer-facing web application where users can browse books, place orders, and view order history.

- **Tech Stack**:  
  `Spring Boot`, `Spring Security OAuth2`, `Keycloak`,  
  `Thymeleaf`, `Alpine.js`, `Bootstrap`

---

## 🎯 Learning Objectives

- ✅ Build REST APIs with **Spring Boot**
- ✅ Persist data using **Spring Data JPA**, **PostgreSQL**, and **Flyway**
- ✅ Implement **asynchronous communication** using **RabbitMQ**
- ✅ Secure applications using **OAuth2** with **Spring Security** and **Keycloak**
- ✅ Setup **API Gateway** with **Spring Cloud Gateway**
- ✅ Implement **resiliency** using **Resilience4j**
- ✅ Handle **distributed scheduling** using **ShedLock**
- ✅ Use **RestClient** & **Declarative HTTP Clients** for inter-service communication
- ✅ Create **aggregated Swagger documentation** at API Gateway
- ✅ Setup **local development environment** using **Docker**, **Docker Compose**, and **Testcontainers**
- ✅ Test services using **JUnit 5**, **RestAssured**, **Testcontainers**, **Awaitility**, and **WireMock**
- ✅ Build responsive web UI using **Thymeleaf**, **Alpine.js**, and **Bootstrap**
- ✅ Monitor services using **Grafana**, **Prometheus**, **Loki**, and **Tempo**

---

## 🧰 Local Development Setup

### 📦 Prerequisites

- ✅ Java 21  
  _Recommended: use [SDKMAN](https://sdkman.io/) to manage Java versions_
- ✅ [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- ✅ [IntelliJ IDEA](https://www.jetbrains.com/idea/) or your favorite IDE
- ✅ [Postman](https://www.postman.com/) or any REST client

---

> 💡 **Note**: This is a learning project. For the full tutorial series, visit the [YouTube Playlist](https://www.youtube.com/playlist?list=PLuNxlOYbv61g_ytin-wgkecfWDK_)
