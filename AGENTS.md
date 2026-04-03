# AGENTS.md

## Repo snapshot (as of 2026-04-01)
- CRUD exists for all 13 asset types under `com.sdr.ams` with server-rendered Thymeleaf UI.
- Stack in `pom.xml`: Spring Boot 4.0.5, Java 25, Spring MVC, Spring Data JPA, Thymeleaf, H2, `jakarta.validation-api`.
- Persistence is H2 in-memory (`jdbc:h2:mem:assetdb`) with `spring.jpa.hibernate.ddl-auto: update` and H2 console at `/h2-console`.
- `CoreEntity` is the common base for all entities (`id`, `name`, `createdAt`, `createdBy`, `updatedAt`, `updatedBy`).
- Domain specs in `docs/*.md` are the source of truth for field-level behavior when enriching entities.

## Entity inventory

### Financial (`com.sdr.ams.model.financial`)
- Rich: `BankAccount`, `Stock`
- Generic: `Bond`

### Tangible (`com.sdr.ams.model.tangible`)
- Rich: `RealEstate`, `Vehicle`
- Generic: `Cash` (now with full spec), `Inventory` (now with full spec), `Machinery` (now with full spec)

### Intangible (`com.sdr.ams.model.intangible`)
- Rich: `Trademark`
- Generic: `Brand`, `Copyright` (now with full spec), `Patent`, `Reputation`

## Architecture patterns in this repo
- Generic tier uses `CoreEntityCrudController<T>` + `CoreEntityCrudService<T>` + plain `JpaRepository<T, Long>`.
- Rich tier uses standalone controller/service/repository with:
  - list filtering via repository `search(...)`
  - service-level `normalizeAndValidate(entity, excludeId)`
  - uniqueness checks via `existsXxx(..., excludeId)` queries
  - templates: `list.html`, `form.html`, `detail.html`
- Rich examples: `BankAccount`, `RealEstate`, `Vehicle`, `Trademark`, `Stock`.
- `@AttributeOverride` remaps `name` for domain IDs in `Vehicle`, `Trademark`, `Stock`, `Machinery`, `Inventory`, `Copyright`, and `Cash`.

## Service-layer conventions (important)
- Normalization before validation is expected (trim, uppercase identifiers, empty -> null).
- Mixed update styles currently exist and should be preserved per entity style:
  - `BeanUtils.copyProperties(...)`: `VehicleService`, `TrademarkService`, `StockService`
  - explicit manual field copy: `BankAccountService`, `RealEstateService`
- Rich services throw `IllegalArgumentException` for business/uniqueness violations; controllers surface these via `BindingResult.reject(...)` and re-render the form.

## Route map
- `/` -> `HomeController`
- Rich controllers: `/bank-accounts`, `/real-estates`, `/vehicles`, `/trademarks`, `/stocks`
- Generic controllers: `/bonds`, `/cash`, `/inventories`, `/machineries`, `/brands`, `/copyrights`, `/patents`, `/reputations`

## Template structure
- `src/main/resources/templates/index.html` links to all asset list pages.
- Rich folders with detail page: `bank-accounts/`, `real-estates/`, `vehicles/`, `trademarks/`, `stocks/`.
- Generic folders use per-entity CRUS pages (`list.html`, `form.html`) for future customization.
- Generic template contract:
  - list: `items`, `singularTitle`, `pluralTitle`, `basePath`
  - form: `item`, `isEdit`, `singularTitle`, `pluralTitle`, `basePath`

## Current domain-specific notes
- `BankAccount`: strict IBAN regex + checksum, SWIFT format validation, IBAN uniqueness, local-account uniqueness per bank+branch, closed-date rule.
- `RealEstate`: large spec-based model with many enums and grouped fields; uniqueness on cadastral and land-registry numbers; optional upload fields were removed from model/UI.
- `Vehicle`: VIN and license-plate uniqueness (plate scoped by country), strong temporal validations; uses `@AttributeOverride` for vehicleId.
- `Trademark`: application/registration uniqueness and lifecycle date ordering checks; uses `@AttributeOverride` for trademarkId.
- `Stock`: now rich/spec-based (not stub), supports filtering + detail page + uniqueness on stock ID, ticker per exchange, ISIN, CUSIP; uses `@AttributeOverride` for stockId.
- `Cash`: comprehensive cash-position model with core amount/currency/date/type, location/ownership, movement/reconciliation, risk indicators, and operational metadata; uses `@AttributeOverride` for cashId; uniqueness on cashId.
- `Inventory`: comprehensive asset model aligned to machinery spec sections (identity, technical, location, maintenance, financial, compliance, risk, metadata); uses `@AttributeOverride` for inventoryId; uniqueness on inventoryId and serialNumber.
- `Machinery`: comprehensive asset model with identity, technical specs, location, maintenance, financial, compliance, and risk fields; uses `@AttributeOverride` for machineId; uniqueness on machineId and serialNumber.
- `Copyright`: comprehensive rights-management model covering work identity, ownership, legal metadata, rights/restrictions, licensing, financial indicators, risk scoring, and operational metadata; uses `@AttributeOverride` for copyrightId; uniqueness on copyrightId and registrationNumber.

## Known codebase caveats
- `RealEstateController#delete` currently redirects to `/bank-accounts` (actual behavior in code).
- `application.yaml` has multipart limits commented out; active code no longer relies on real-estate file uploads.

## Developer workflow (Windows)
- Use Maven wrapper from repo root:
  - `./mvnw.cmd test`
  - `./mvnw.cmd spring-boot:run`
  - `./mvnw.cmd package`
- `JAVA_HOME` must point to JDK 25.
- Existing tests are minimal (`AssetManagementServiceApplicationTests`, `HomeControllerWebMvcTest`); add focused controller/service tests when extending rich entities.
