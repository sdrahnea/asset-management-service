# AGENTS.md

## Repo snapshot
- This repository is currently a **minimal Spring Boot scaffold**: the only Java sources are `src/main/java/com/sdr/ams/AssetManagementServiceApplication.java` and `src/test/java/com/sdr/ams/AssetManagementServiceApplicationTests.java`.
- The package root is `com.sdr.ams`; keep new Spring components under this package so `@SpringBootApplication` component scanning continues to find them.
- `pom.xml` defines the intended stack: **Spring Boot 4.0.5**, **Java 25**, Spring MVC (`spring-boot-starter-webmvc`), Thymeleaf, Spring Data JPA, H2, plus the matching test starters.
- `src/main/resources/application.yaml` only sets `spring.application.name: asset-management-service`; there are no environment-specific profiles or datasource settings yet.

## Architecture cues from the scaffold
- There are **no controllers, entities, repositories, services, templates, or static assets yet**. Do not assume an existing layered architecture beyond standard Spring Boot conventions.
- The dependency set suggests a future flow of **MVC controller -> Thymeleaf view and/or HTTP endpoint -> JPA repository -> H2-backed persistence**, but this is not implemented yet.
- `src/main/resources/templates/` and `src/main/resources/static/` exist as the conventional locations for server-rendered views and web assets; add files there only if you are actually introducing MVC pages/static content.
- The single test uses `@SpringBootTest` (`AssetManagementServiceApplicationTests`) to boot the full application context; mirror that only for integration-level checks, not for every unit of logic.

## Developer workflow
- Preferred build entrypoint is the Maven wrapper in the repo root: on Windows use `./mvnw.cmd test`, `./mvnw.cmd spring-boot:run`, and `./mvnw.cmd package`.
- On this machine, the wrapper fails until Java is installed and `JAVA_HOME` is set correctly; document or preserve that prerequisite when adjusting setup instructions.
- There is no Gradle build, Docker setup, or custom script layer in the repo today; keep commands Maven-native unless you add the supporting files.
- `HELP.md` is the standard Spring Initializr help page, and `.gitignore` even ignores `HELP.md`; use `pom.xml` and source layout as the authoritative description of the project.

## Conventions worth preserving
- Match the existing package naming and keep code under `src/main/java` / `src/test/java` with the same `com.sdr.ams` root.
- If you add persistence code, prefer the existing JPA/H2 stack already declared in `pom.xml` instead of introducing a second persistence approach.
- If you add web UI, prefer Thymeleaf templates because the starter and test support are already present.
- Keep changes small and scaffold-aligned: since the repo is mostly empty, explain any new package structure in the PR/commit and update `README.md` if you introduce the first real modules.
- `pom.xml` intentionally contains empty metadata elements (`<license/>`, `<developers/>`, `<scm/>`, etc.) to override parent inheritance from Spring Boot; do not delete them unless you want inherited metadata back.

