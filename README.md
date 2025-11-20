# 🎓 Sistema Acadêmico - Spring Boot 3
**Prática Avaliativa AV2** com Spring Boot 3, Segurança, Monitoramento, Testes de Performance e Deploy

---

## 📋 Índice
- [Visão Geral](#visão-geral)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Como Configurar](#como-configurar)
- [Como Executar](#como-executar)
- [Endpoints da API](#endpoints-da-api)
- [Monitoramento](#monitoramento)
- [Testes de Performance](#testes-de-performance)
- [Deploy](#deploy)

---

## 🎯 Visão Geral
Sistema acadêmico completo com:
- ✅ CRUD de Estudantes, Cursos e Inscrições
- ✅ Autenticação e Autorização com Spring Security
- ✅ Documentação Swagger/OpenAPI
- ✅ Monitoramento com Prometheus + Grafana
- ✅ Testes de performance com Gatling
- ✅ H2 Database (em memória)

---

## 🏗️ Arquitetura do Projeto

```
sistema-academico/
├── src/main/java/com/example/
│   ├── SistemaAcademicoApplication.java      (Main)
│   ├── config/
│   │   └── SecurityConfig.java               (Spring Security)
│   ├── entity/
│   │   ├── Student.java
│   │   ├── Course.java
│   │   └── Enrollment.java
│   ├── repository/
│   │   ├── StudentRepository.java
│   │   ├── CourseRepository.java
│   │   └── EnrollmentRepository.java
│   └── controller/
│       ├── StudentController.java
│       ├── CourseController.java
│       └── EnrollmentController.java
├── src/test/scala/simulations/
│   └── AcademicSystemSimulation.scala        (Gatling)
├── src/main/resources/
│   └── application.properties
├── pom.xml
├── docker-compose.yml
├── Dockerfile
├── monitoring/
│   └── prometheus.yml
└── README.md
```

---

## 🛠️ Tecnologias Utilizadas

**Backend:**
- Java 17
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- H2 Database
- Swagger/OpenAPI (Springdoc)
- Lombok

**Monitoramento:**
- Spring Boot Actuator
- Micrometer Prometheus
- Prometheus
- Grafana

**Testes:**
- Gatling 3.9.7
- JUnit 5
- Spring Test

**DevOps:**
- Docker & Docker Compose
- Maven

**Documentação:**
- OpenAPI 3.0 (Swagger)

---

## 📦 Pré-requisitos

Certifique-se de ter instalado:
- **JDK 17+** ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.8+** ([Download](https://maven.apache.org/download.cgi))
- **Docker & Docker Compose** ([Download](https://www.docker.com/products/docker-desktop/))
- **Git**

Verificar instalação:
```bash
java -version
mvn -version
docker --version
docker-compose --version
```

---

## ⚙️ Como Configurar

### 1️⃣ Clonar o Repositório
```bash
git clone https://github.com/Leandrodias10/AV2.git
cd AV2
```

### 2️⃣ Estrutura de Pastas
Certifique-se de criar as pastas necessárias:
```bash
mkdir -p src/main/java/com/example/config
mkdir -p src/main/java/com/example/entity
mkdir -p src/main/java/com/example/repository
mkdir -p src/main/java/com/example/controller
mkdir -p src/main/resources
mkdir -p src/test/scala/simulations
mkdir -p monitoring
```

### 3️⃣ Adicionar Arquivos de Configuração
- Copie `application.properties` para `src/main/resources/`
- Copie `prometheus.yml` para `monitoring/`

### 4️⃣ Build do Projeto
```bash
mvn clean compile
```

---

## 🚀 Como Executar

### ▶️ Opção 1: Rodar Localmente (Recomendado para Desenvolvimento)

1. **Terminal 1 - Inicie a aplicação Spring Boot:**
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

2. **Terminal 2 - Inicie o Docker Compose (Prometheus + Grafana + Keycloak):**
```bash
docker-compose up -d
```

### ✅ Validar Acesso

| Serviço | URL | Credenciais |
|---------|-----|-------------|
| **API REST** | http://localhost:8080/api | - |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | - |
| **H2 Console** | http://localhost:8080/h2-console | sa / (sem senha) |
| **Actuator (Prometheus)** | http://localhost:8080/actuator/prometheus | - |
| **Prometheus** | http://localhost:9090 | - |
| **Grafana** | http://localhost:3000 | admin / admin |
| **Keycloak** | http://localhost:8081 | admin / admin |

### ▶️ Opção 2: Executar com Docker (Aplicação em Container)

1. **Build da imagem:**
```bash
mvn clean package
docker build -t sistema-academico:latest .
```

2. **Criar arquivo `docker-compose.prod.yml`:** (opcional)
```yaml
version: '3.8'
services:
  app:
    image: sistema-academico:latest
    ports:
      - "8080:8080"
    networks:
      - academic-network
  
  # Adicione prometheus, grafana, keycloak...
```

3. **Executar:**
```bash
docker-compose -f docker-compose.prod.yml up -d
```

---

## 🔐 Segurança & Credenciais

### Usuários Padrão (In-Memory)
```
👤 Admin
   Username: admin
   Password: admin123
   Role: ADMIN

👤 Usuário
   Username: user
   Password: user123
   Role: USER
```

### Rotas Protegidas
- **GET /api/students** - Qualquer usuário autenticado
- **POST /api/students** - Apenas ADMIN
- **PUT /api/students/{id}** - Apenas ADMIN
- **DELETE /api/students/{id}** - Apenas ADMIN
- **/actuator/prometheus** - Sem autenticação (necessário para Prometheus)
- **/swagger-ui.html** - Sem autenticação

---

## 📊 Endpoints da API

### 📚 Estudantes

```http
GET    /api/students              # Listar todos
GET    /api/students/{id}         # Buscar por ID
GET    /api/students/email/{email} # Buscar por email
POST   /api/students              # Criar (ADMIN)
PUT    /api/students/{id}         # Atualizar (ADMIN)
DELETE /api/students/{id}         # Deletar (ADMIN)
```

**Exemplo - Criar Estudante:**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -u admin:admin123 \
  -d '{
    "firstName": "João",
    "lastName": "Silva",
    "email": "joao@example.com",
    "registration": "REG001",
    "status": "ACTIVE"
  }'
```

### 📖 Cursos

```http
GET    /api/courses              # Listar todos
GET    /api/courses/{id}         # Buscar por ID
GET    /api/courses/code/{code}  # Buscar por código
POST   /api/courses              # Criar (ADMIN)
PUT    /api/courses/{id}         # Atualizar (ADMIN)
DELETE /api/courses/{id}         # Deletar (ADMIN)
```

### 📋 Inscrições

```http
GET    /api/enrollments                    # Listar todas
GET    /api/enrollments/{id}               # Buscar por ID
GET    /api/enrollments/student/{studentId} # Listar por estudante
POST   /api/enrollments                    # Criar (USER, ADMIN)
PUT    /api/enrollments/{id}               # Atualizar (ADMIN)
DELETE /api/enrollments/{id}               # Deletar (ADMIN)
```

---

## 📈 Monitoramento

### Prometheus
- **URL:** http://localhost:9090
- **Coleta métricas de:** http://localhost:8080/actuator/prometheus
- **Intervalo:** A cada 5 segundos

### Grafana
- **URL:** http://localhost:3000
- **Login:** admin / admin
- **Adicionar Datasource:**
  1. Vá para Configuration → Data Sources
  2. Add → Prometheus
  3. URL: http://prometheus:9090
  4. Save & Test

### Métricas Disponíveis
```
jvm_memory_used_bytes
http_requests_total
http_request_duration_seconds
process_cpu_usage
system_cpu_usage
```

---

## ⚡ Testes de Performance

### Executar com Gatling

1. **Via Maven:**
```bash
mvn gatling:test
```

2. **Simulação Specific:**
```bash
mvn gatling:test -Dgatling.simulationClass=simulations.AcademicSystemSimulation
```

3. **Relatório gerado em:**
```
target/gatling/academicsystemsimulation-{timestamp}/index.html
```

### Cenários Testados
- ✅ Listar estudantes (10 requisições)
- ✅ Criar estudantes (5 requisições)
- ✅ Listar cursos (10 requisições)
- ✅ Criar cursos (3 requisições)
- ✅ Listar inscrições (10 requisições)

### Assertions
- Sucesso > 95%
- Tempo P95 < 5s
- Tempo Médio < 2s

---

## 🐳 Deploy

### Build e Push para Registro (Exemplo com Docker Hub)

```bash
# Build
mvn clean package
docker build -t seu-usuario/sistema-academico:latest .

# Login
docker login

# Push
docker push seu-usuario/sistema-academico:latest
```

### Kubernetes (Opcional)
```bash
kubectl apply -f k8s/deployment.yaml
kubectl expose deployment sistema-academico --type=LoadBalancer --port=80 --target-port=8080
```

---

## 🧪 Testes Unitários

```bash
mvn test
```

---

## 📝 Logs

### Ver logs em tempo real
```bash
# Spring Boot
mvn spring-boot:run | grep -i "error\|warn\|info"

# Docker Compose
docker-compose logs -f prometheus
docker-compose logs -f grafana
docker-compose logs -f keycloak
```

---

## 🆘 Troubleshooting

### ❌ Erro: Port 8080 já está em uso
```bash
# Linux/Mac
lsof -i :8080
kill -9 <PID>

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### ❌ Prometheus não consegue scrape da aplicação
- Verifique se a aplicação está rodando: http://localhost:8080
- Se usar Linux, ajuste em `prometheus.yml`: `targets: ['172.17.0.1:8080']`

### ❌ Erro de compilação com Lombok
```bash
mvn clean compile
mvn eclipse:eclipse -DdownloadSources -DdownloadJavadocs
```

---

## 📚 Documentação Oficial

- [Spring Boot 3](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Prometheus](https://prometheus.io/docs/introduction/overview/)
- [Grafana](https://grafana.com/docs/grafana/latest/)
- [Gatling](https://gatling.io/docs/gatling/reference/current/)
- [Springdoc OpenAPI](https://springdoc.org/)

---

## 📞 Suporte

Para dúvidas ou issues, abra uma Issue no GitHub!

---

**Desenvolvido com ❤️ para AV2**

Última atualização: 20/11/2025