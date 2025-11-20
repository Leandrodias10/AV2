# Sistema Acadêmico - Spring Boot 3 (Prática Avaliativa)

Projeto base para a prática avaliativa: **Sistema Acadêmico** com Spring Boot 3,
segurança, monitoramento (Prometheus + Grafana), testes de performance (Gatling),
documentação OpenAPI e deploy.

## O que tem neste zip
- Backend Spring Boot (Maven) com:
  - Endpoints REST para Students e Courses (CRUD básico)
  - H2 em memória
  - Spring Security (in-memory users: admin/admin123, user/user123)
  - Spring Boot Actuator + Micrometer Prometheus
  - OpenAPI / Swagger UI
- `docker-compose` com Prometheus + Grafana (config básico)
- Gatling folder (simulação básica)
- Dockerfile para empacotar a aplicação
- README com instruções

## Como abrir no VS Code
1. Descompacte o zip.
2. Abra a pasta `sistema-academico-springboot` no VS Code.
3. Para rodar local:
   - Requer JDK 17+ e Maven instalados.
   - No terminal execute:
     ```bash
     mvn clean package
     mvn spring-boot:run
     ```
   - A aplicação ficará disponível em `http://localhost:8080`.
   - H2 Console: `http://localhost:8080/h2-console` (jdbc url já configurada).
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

## Prometheus & Grafana (docker-compose)
1. Na pasta raiz execute:
   ```bash
   docker-compose up -d
   ```
2. Prometheus ficará em `http://localhost:9090` e Grafana em `http://localhost:3000`.
   - Grafana credenciais padrão: `admin/admin`
3. Prometheus já está configurado para coletar `/actuator/prometheus` da aplicação em `host.docker.internal:8080` (ajuste se necessário, por exemplo em Linux).

## Gatling
- Há uma pasta `gatling` com um exemplo de simulação. Para rodar via Maven você pode usar o plugin Gatling (já adicionado no `pom.xml`), ou rodar Gatling local.

## Próximos passos que eu posso fazer (basta pedir)
- Integrar autenticação com Keycloak.
- Adicionar testes unitários e exemplos de integração.
- Criar dashboards Grafana prontos.
- Gerar cenários Gatling completos para endpoints críticos.

Boa sorte — se quiser eu já gero o zip completo aqui para download e incluo configurações extras.  
