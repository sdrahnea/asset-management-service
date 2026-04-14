# AGENTS.md

## Repo snapshot (as of 2026-04-14)
- Full CRUD for all 13 asset types under `com.sdr.ams` with server-rendered Thymeleaf UI.
- Stack: Spring Boot 4.0.5, Java 25, Spring MVC, Spring Data JPA, Thymeleaf, H2, `jakarta.validation-api`.
- Persistence: H2 in-memory (`jdbc:h2:mem:assetdb`), `spring.jpa.hibernate.ddl-auto: update`, H2 console at `/h2-console`.
- `CoreEntity` (`com.sdr.ams.model.core`) is the `@MappedSuperclass` base for all entities; provides `id`, `name`, `createdAt`, `createdBy`, `updatedAt`, `updatedBy` with `@PrePersist`/`@PreUpdate` auto-fill defaulting to `"system"`.
- Domain specs in `docs/<entity>/` folders are the source of truth for field-level behavior.
- Demo data is seeded on every startup via `DemoDataSeeder` (see **Demo Data** section below).

## Entity inventory

### Financial (`com.sdr.ams.model.financial`)
- **Rich**: `BankAccount`, `Stock`, `Bond`
- Generic+full spec: _(none)_

### Tangible (`com.sdr.ams.model.tangible`)
- **Rich**: `RealEstate`, `Vehicle`
- Generic+full spec: `Cash`, `Inventory`, `Machinery`

### Intangible (`com.sdr.ams.model.intangible`)
- **Rich**: `Trademark`, `Patent`, `Reputation`
- Generic+full spec: `Brand`, `Copyright`

> **Rich** = standalone controller with detail page + filtering + service-level `normalizeAndValidate`.
> **Generic+full spec** = `CoreEntityCrudController<T>` + `CoreEntityCrudService<T>`, but with custom rich form/list templates (not the shared `entities/` templates).

## Architecture patterns

### Rich tier
- Standalone `@Controller` (not extending `CoreEntityCrudController`).
- Standalone `@Service` (not extending `CoreEntityCrudService`) with:
  - `normalizeAndValidate(entity, excludeId)` — trim, uppercase identifiers, empty→null, then business + uniqueness checks.
  - `findAll(filter params)` delegating to `repository.search(...)`.
  - `IllegalArgumentException` for violations; controller catches and calls `bindingResult.reject(...)`, re-renders form.
- `JpaRepository` with `search(...)` JPQL + `existsXxx(..., excludeId)` uniqueness queries.
- Templates: `list.html`, `form.html`, `detail.html`.

### Generic tier
- Extends `CoreEntityCrudController<T>` → `CoreEntityCrudService<T>` → `JpaRepository<T, Long>`.
- Custom `list.html` and `form.html` per entity (rich multi-section layout), but no `detail.html`.
- Template model keys: `items`, `singularTitle`, `pluralTitle`, `basePath` (list); `item`, `isEdit`, ... (form).
- Note: `entities/` folder in templates contains unused generic stubs.

### `@AttributeOverride` on `name`
All of the following remap the inherited `name` column to a domain-specific ID column:
`Bond` (bondId), `BankAccount`* , `Cash` (cashId), `Copyright` (copyrightId), `Brand` (brandId),
`Inventory` (inventoryId), `Machinery` (machineId), `Patent` (patentId), `Reputation` (reputationId),
`Stock` (stockId), `Trademark` (trademarkId), `Vehicle` (vehicleId).
(*`BankAccount` uses `name` for `holderName`, not a domain ID.)

## Service-layer conventions

| Update style | Services |
|---|---|
| `BeanUtils.copyProperties(input, existing, "id","createdAt","createdBy","updatedAt","updatedBy")` | `VehicleService`, `TrademarkService`, `StockService`, `BondService`, `PatentService`, `ReputationService`, `CashService`, `InventoryService`, `BrandService`, `CopyrightService` |
| Explicit manual field copy | `BankAccountService`, `RealEstateService` |

- Rich services: do NOT extend `CoreEntityCrudService`; own full transaction + validation lifecycle.
- Generic services: extend `CoreEntityCrudService<T>`, override `create`/`update` to use `BeanUtils`.

## Route map

| Tier | Routes |
|---|---|
| Rich | `/bank-accounts`, `/bonds`, `/patents`, `/real-estates`, `/reputations`, `/stocks`, `/trademarks`, `/vehicles` |
| Generic | `/cash`, `/copyrights`, `/inventories`, `/machineries`, `/brands` |

- `/` → `HomeController`

## Template structure

| Folder | list | form | detail |
|---|---|---|---|
| `bank-accounts/` | ✓ | ✓ | ✓ |
| `bonds/` | ✓ | ✓ | ✓ |
| `patents/` | ✓ | ✓ | ✓ |
| `real-estates/` | ✓ | ✓ | ✓ |
| `reputations/` | ✓ | ✓ | ✓ |
| `stocks/` | ✓ | ✓ | ✓ |
| `trademarks/` | ✓ | ✓ | ✓ |
| `vehicles/` | ✓ | ✓ | ✓ |
| `brands/` | ✓ | ✓ | — |
| `cash/` | ✓ | ✓ | — |
| `copyrights/` | ✓ | ✓ | — |
| `inventories/` | ✓ | ✓ | — |
| `machineries/` | ✓ | ✓ | — |
| `entities/` | stub | stub | — (unused) |

## Domain-specific notes

- **BankAccount**: IBAN regex + checksum, SWIFT format, IBAN global uniqueness, localAccountNumber unique per bankName+branchName, closedAt ≥ openedAt; enum defaults set in controller (ACTIVE, INDIVIDUAL, CURRENT, EUR); IBAN masked on list page.
- **RealEstate**: large spec with many enums grouped by section; uniqueness on cadastralNumber + landRegistryNumber; no file upload fields.
- **Vehicle**: VIN + license-plate uniqueness (plate scoped by registrationCountry); strong temporal validations; `@AttributeOverride` for vehicleId.
- **Trademark**: applicationNumber + registrationNumber uniqueness; lifecycle date ordering; `@AttributeOverride` for trademarkId.
- **Stock**: filtering + detail page; uniqueness on stockId, tickerSymbol-per-exchange, ISIN, CUSIP; `@AttributeOverride` for stockId.
- **Bond**: date-ordering rules (issueDate ≤ maturityDate, callDate ≤ maturityDate, putDate ≤ maturityDate); uniqueness on bondId + ISIN + CUSIP/SEDOL; filtering by issuer, bondType, tradingStatus, currency; `@AttributeOverride` for bondId.
- **Patent**: applicationDate ≤ publicationDate ≤ grantDate ≤ expiryDate; GRANTED status requires grantNumber + grantDate; uniqueness on patentId + applicationNumber + publicationNumber + grantNumber; filtering by patentType, legalStatus, technologyField, assigneeOwner; `@AttributeOverride` for patentId.
- **Reputation**: uniqueness on reputationId + composite (entityType, entityId); filtering by entityId, entityType, trendDirection, competitivePosition; `@AttributeOverride` for reputationId.
- **Cash**: cash-position model (amount, currency, valuationDate, cashType, holderOwner, etc.); `@AttributeOverride` for cashId; uniqueness on cashId.
- **Inventory**: identity + technical + location + maintenance + financial + compliance + risk sections; `@AttributeOverride` for inventoryId; uniqueness on inventoryId + serialNumber.
- **Machinery**: same section grouping as Inventory; `@AttributeOverride` for machineId; uniqueness on machineId + serialNumber.
- **Copyright**: work identity + ownership + legal + rights/restrictions + licensing + financial + risk + metadata; `@AttributeOverride` for copyrightId; uniqueness on copyrightId + registrationNumber.
- **Brand**: identity + legal/trademark + market positioning + performance + digital footprint + operational + financial metrics; `@AttributeOverride` for brandId; uniqueness on brandId + trademarkRegistrationNumber.

## Demo Data

- `DemoDataSeeder` (`com.sdr.ams.config`) seeds all 13 entities at application startup.
- Only seeds when table is empty (idempotent).
- Controlled by `application.yaml`:
  ```yaml
  app:
    demo-data:
      enabled: true          # set false to skip all seeding
      record-count: 25       # default records per entity
      max-records:
        bank-accounts: 20    # per-entity override keys match route path segments
        bonds: 10
  ```
- `DemoDataProperties` resolves per-entity count via `recordCountFor(entityKey)`, falling back to `record-count`.

## SDD Documentation

- Every entity has a full `docs/<entity>/` SDD folder — navigation via `docs/SDD-INDEX.md`.
- Standard file set per folder: `README.md`, `<entity>.md`, `requirements.md`, `data-model.md`, `validation-rules.md`, `user-stories.md`, `tasks.md`, `test-scenarios.md`, `api-surface.md`, `compliance.md`.
- Domain specs are the source of truth when enriching an entity — read the relevant `docs/<entity>/` before modifying a model.

## Known codebase caveats
- `RealEstateController#delete` currently redirects to `/bank-accounts` (bug — should redirect to `/real-estates`).
- `CopyrightController` extends `CoreEntityCrudController` (generic tier) but `copyrights/form.html` and `copyrights/list.html` are custom-built rich templates; they do **not** follow the shared generic `items`/`basePath` model-attribute contract.
- `entities/` template folder contains unused generic stubs (`list.html`, `form.html`) not wired to any active controller.
- `application.yaml` has multipart limits commented out; no active file-upload functionality.

## Developer workflow (Windows)
```powershell
./mvnw.cmd test             # run tests
./mvnw.cmd spring-boot:run  # start app on http://localhost:8080
./mvnw.cmd package          # build JAR
```
- `JAVA_HOME` must point to JDK 25.
- H2 console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:assetdb`).
- Existing tests: `AssetManagementServiceApplicationTests`, `HomeControllerWebMvcTest` — add focused controller/service tests when promoting a generic entity to rich tier.
