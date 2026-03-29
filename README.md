# Asset Management Service

Asset Management Service is an early-stage Spring Boot application intended to model and manage multiple classes of assets in a single system.

## Intended Domain Scope

The planned implementation focuses on three major asset categories:

- **Tangible Assets**: physical items such as cash, real estate, machinery, inventory, and vehicles.
- **Intangible Assets**: non-physical resources that hold value, including intellectual property (patents, trademarks), copyrights, and brand reputation.
- **Financial Assets**: investments and monetary holdings such as stocks, bonds, and bank accounts.

## Planned Implementation Focus

Based on the current Maven configuration, the intended implementation direction is:

- **Spring MVC** for HTTP endpoints and/or server-rendered flows
- **Thymeleaf** for HTML views if a web UI is introduced
- **Spring Data JPA** for persistence
- **H2** for local development and early-stage persistence

This suggests a likely application flow of controller -> service -> repository -> database as the project grows, but that structure is not implemented yet.

## Current Repository State

The repository is still a minimal scaffold. At the moment it contains:

- `src/main/java/com/sdr/ams/AssetManagementServiceApplication.java`
- `src/test/java/com/sdr/ams/AssetManagementServiceApplicationTests.java`
- `src/main/resources/application.yaml` with the application name set to `asset-management-service`

There are currently no controllers, entities, repositories, services, templates, or static assets in the codebase.

## Build and Run Prerequisites

- Use the Maven wrapper from the project root.
- The project is configured for **Spring Boot 4.0.5** and **Java 25** in `pom.xml`.
- On this machine, running the wrapper currently requires Java to be installed and `JAVA_HOME` to be set correctly.

Example commands on Windows PowerShell:

```powershell
./mvnw.cmd test
./mvnw.cmd spring-boot:run
./mvnw.cmd package
```
