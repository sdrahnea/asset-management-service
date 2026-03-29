# AGENTS.md

## Repo snapshot
- The project started as a minimal scaffold and now contains a first implemented MVC/JPA slice for Bank Accounts.
- Keep all new Spring components under `com.sdr.ams` so `@SpringBootApplication` scanning continues to work.
- `pom.xml` stack remains: **Spring Boot 4.0.5**, **Java 25**, Spring MVC, Thymeleaf, Spring Data JPA, and H2.
- Shared entity base is `src/main/java/com/sdr/ams/model/core/CoreEntity.java` (`id`, `name`, `createdAt`, `createdBy`, `updatedAt`, `updatedBy`).

## Architecture cues from current implementation
- Implemented flow is `controller -> service -> repository -> JPA/H2`:
  - `src/main/java/com/sdr/ams/controller/BankAccountController.java`
  - `src/main/java/com/sdr/ams/service/BankAccountService.java`
  - `src/main/java/com/sdr/ams/repository/BankAccountRepository.java`
  - `src/main/java/com/sdr/ams/model/financial/BankAccount.java`
- Home page entrypoint is `GET /` in `src/main/java/com/sdr/ams/controller/HomeController.java` with template `src/main/resources/templates/index.html`.
- Bank account CRUD pages are:
  - `src/main/resources/templates/bank-accounts/list.html`
  - `src/main/resources/templates/bank-accounts/form.html`
- Asset entities are grouped by domain package under `src/main/java/com/sdr/ams/model/` (`financial`, `tangible`, `intangible`) and currently extend `CoreEntity`.
- Use the Bank Account slice as the reference pattern when adding CRUD for other asset types.

## Developer workflow
- Preferred build entrypoint is the Maven wrapper in the repo root: on Windows use `./mvnw.cmd test`, `./mvnw.cmd spring-boot:run`, and `./mvnw.cmd package`.
- On this machine, the wrapper fails until Java is installed and `JAVA_HOME` is set correctly; document or preserve that prerequisite when adjusting setup instructions.
- There is no Gradle build, Docker setup, or custom script layer in the repo today; keep commands Maven-native unless you add the supporting files.
- Runtime config lives in `src/main/resources/application.yaml`: in-memory H2 datasource (`jdbc:h2:mem:assetdb`), `spring.jpa.hibernate.ddl-auto: update`, SQL logging enabled, and H2 console at `/h2-console`.
- `HELP.md` is the standard Spring Initializr help page, and `.gitignore` ignores `HELP.md`; use `pom.xml` and source layout as the authoritative description of the project.

## Conventions worth preserving
- Match the existing package naming and keep code under `src/main/java` / `src/test/java` with the same `com.sdr.ams` root.
- If you add persistence code, prefer the existing JPA/H2 stack already declared in `pom.xml` instead of introducing a second persistence approach.
- If you add web UI, prefer Thymeleaf templates because the starter and test support are already present.
- Follow current layer split when adding features: `controller`, `service`, `repository`, `model`.
- Keep MVC flows PRG-style (POST then redirect), matching `BankAccountController`.
- New asset entities should extend `CoreEntity` and define explicit `@Entity` + `@Table`.
- Existing tests include full-context (`AssetManagementServiceApplicationTests`) and MVC-slice (`HomeControllerWebMvcTest`); mirror this split for new tests.
- `pom.xml` intentionally contains empty metadata elements (`<license/>`, `<developers/>`, `<scm/>`, etc.) to override parent inheritance from Spring Boot; do not delete them unless you want inherited metadata back.

