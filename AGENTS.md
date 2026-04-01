# AGENTS.md

## Repo snapshot (as of 2026-03-31)
- Full CRUD is implemented for **all 13 asset types** across three domain packages.
- `pom.xml` stack: **Spring Boot 4.0.5**, **Java 25**, Spring MVC (`spring-boot-starter-webmvc`), Thymeleaf, Spring Data JPA, H2, `jakarta.validation-api 4.0.0-M1`.
- All Spring components live under `com.sdr.ams` so `@SpringBootApplication` scanning continues to work.
- In-memory H2 datasource (`jdbc:h2:mem:assetdb`), `spring.jpa.hibernate.ddl-auto: update`, H2 console at `/h2-console`.
- `docs/` contains domain-spec markdown for each asset type (e.g., `docs/vehicle.md`, `docs/trademark.md`); use them as the authoritative field-level reference when enriching stub entities.

---

## Entity inventory

### Financial – `com.sdr.ams.model.financial`
| Entity | Table | Tier | Notes |
|--------|-------|------|-------|
| `BankAccount` | `bank_account` | Rich | IBAN/SWIFT validation, unique constraints, status/ownership/account-type enums |
| `Bond` | `bond` | Stub | Name-only |
| `Stock` | `stock` | Stub | Name-only |

### Tangible – `com.sdr.ams.model.tangible`
| Entity | Table | Tier | Notes |
|--------|-------|------|-------|
| `Cash` | `cash` | Stub | Name-only |
| `Inventory` | `inventory` | Stub | Name-only |
| `Machinery` | `machinery` | Stub | Name-only |
| `RealEstate` | `real_estate` | Rich | Cadastral/land-registry unique constraints, `@ElementCollection` for utilities & amenities sets, filterable list |
| `Vehicle` | `vehicle` | Rich | `@AttributeOverride` remaps `name` → `vehicle_id`; VIN, license plate unique constraints; filterable list |

### Intangible – `com.sdr.ams.model.intangible`
| Entity | Table | Tier | Notes |
|--------|-------|------|-------|
| `Brand` | `brand` | Stub | Name-only |
| `Copyright` | `copyright` | Stub | Name-only |
| `Patent` | `patent` | Stub | Name-only |
| `Reputation` | `reputation` | Stub | Name-only |
| `Trademark` | `trademark` | Rich | `@AttributeOverride` remaps `name` → `trademark_id`; application/registration number unique constraints; filterable list |

---

## Architecture – two implementation tiers

### Tier 1 – Generic (stub entities, name-only)
Used when an entity only needs `CoreEntity` fields (`id`, `name`, audit timestamps/actors).

- **Controller** extends `CoreEntityCrudController<T>` – provides `list`, `new`, `create`, `edit`, `update`, `delete` out-of-the-box.
- **Service** extends `CoreEntityCrudService<T>` – delegates to `JpaRepository`; `update()` copies only `name` and `updatedBy`.
- **Repository** extends `JpaRepository<T, Long>` – no custom queries needed.
- **Templates** – each entity has its own subdirectory (`bonds/`, `stocks/`, …) with `list.html` and `form.html` that receive `items`, `singularTitle`, `pluralTitle`, `basePath` model attributes.

Examples: `BondController`, `StockController`, `BrandController`, `CashController`, `InventoryController`, `MachineryController`, `CopyrightController`, `PatentController`, `ReputationController`.

### Tier 2 – Rich (domain-specific fields, validation, filtering)
Used when an entity has unique constraints, enums, validation rules, or list filtering.

- **Controller** is a standalone `@Controller` (does **not** extend `CoreEntityCrudController`). Uses `@Valid` + `BindingResult`, populates enum lists via a private `populateEnums(model)` helper, accepts `@RequestParam` filters on the list endpoint.
- **Service** is a standalone `@Service` (does **not** extend `CoreEntityCrudService`). Contains `normalizeAndValidate(entity, excludeId)` for uniqueness checks and field normalization. `update()` uses `BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt", "updatedBy")` to copy all domain fields while preserving audit fields.
- **Repository** extends `JpaRepository<T, Long>` and adds JPQL `@Query` methods: a `search(…)` method for optional-param filtering (ordered by `updatedAt desc`) and `existsXxx(value, excludeId)` boolean queries for uniqueness validation.
- **Templates** – subdirectory has `list.html`, `form.html`, **and** `detail.html`.

Examples: `BankAccountController/Service/Repository`, `RealEstateController/Service/Repository`, `VehicleController/Service/Repository`, `TrademarkController/Service/Repository`.

---

## Shared base classes

### `CoreEntity` (`com.sdr.ams.model.core.CoreEntity`)
`@MappedSuperclass` – **not** `@Entity`. Fields:
- `id` – `Long`, `@GeneratedValue(IDENTITY)`
- `name` – `String`, `@Column(nullable = false)`
- `createdAt`, `updatedAt` – `Instant`, auto-set by `@PrePersist` / `@PreUpdate`
- `createdBy`, `updatedBy` – `String(100)`, defaulted to `"system"` by lifecycle hooks if blank

Remapping `name` column: use `@AttributeOverride(name = "name", column = @Column(name = "custom_col", …))` on the entity class (see `Vehicle`, `Trademark`). When doing so, also override `getName()`/`setName()` with `@JsonIgnore` if the field is exposed under a domain-specific accessor.

### `CoreEntityCrudController<T extends CoreEntity>` (`com.sdr.ams.controller`)
Abstract controller providing full CRUD routing for stub entities. Constructor args: `service`, `singularTitle`, `pluralTitle`, `basePath`. Subclass must implement `createEntity()`. Routes are relative to the subclass's `@RequestMapping`.

### `CoreEntityCrudService<T extends CoreEntity>` (`com.sdr.ams.service`)
Abstract service wrapping a `JpaRepository<T, Long>`. Provides `findAll`, `findById`, `create`, `update` (copies `name` + `updatedBy` only), `delete`. Annotated `@Transactional`.

---

## Route map
| URL prefix | Controller | Tier |
|------------|------------|------|
| `/` | `HomeController` | – |
| `/bank-accounts` | `BankAccountController` | Rich |
| `/bonds` | `BondController` | Generic |
| `/stocks` | `StockController` | Generic |
| `/cash` | `CashController` | Generic |
| `/inventories` | `InventoryController` | Generic |
| `/machineries` | `MachineryController` | Generic |
| `/real-estates` | `RealEstateController` | Rich |
| `/vehicles` | `VehicleController` | Rich |
| `/brands` | `BrandController` | Generic |
| `/copyrights` | `CopyrightController` | Generic |
| `/patents` | `PatentController` | Generic |
| `/reputations` | `ReputationController` | Generic |
| `/trademarks` | `TrademarkController` | Rich |

---

## Template structure
```
templates/
  index.html                  ← home page, links to all 13 asset lists
  bank-accounts/              ← list.html, form.html, detail.html
  bonds/                      ← list.html, form.html
  brands/                     ← list.html, form.html
  cash/                       ← list.html, form.html
  copyrights/                 ← list.html, form.html
  entities/                   ← list.html, form.html  (reference/generic template, not routed)
  inventories/                ← list.html, form.html
  machineries/                ← list.html, form.html
  patents/                    ← list.html, form.html
  real-estates/               ← list.html, form.html, detail.html
  reputations/                ← list.html, form.html
  stocks/                     ← list.html, form.html
  trademarks/                 ← list.html, form.html, detail.html
  vehicles/                   ← list.html, form.html, detail.html
```
Generic `list.html` templates receive: `items`, `singularTitle`, `pluralTitle`, `basePath`.  
Generic `form.html` templates receive: `item`, `isEdit`, `singularTitle`, `pluralTitle`, `basePath`.  
Rich templates use entity-specific model attribute names (e.g., `bankAccount`, `vehicle`, `trademark`, `realEstate`).

---

## Conventions worth preserving
- **PRG pattern** – every `POST` handler redirects (`return "redirect:" + basePath`) on success.
- **Package naming** – keep all new code under `com.sdr.ams`; use `controller`, `service`, `repository`, `model/<domain>` sub-packages.
- **New stub entity checklist**: create entity (extends `CoreEntity`, `@Entity` + `@Table`), extend `CoreEntityCrudService`, extend `JpaRepository`, extend `CoreEntityCrudController`, add `list.html` + `form.html` templates, add link in `index.html`.
- **New rich entity checklist**: follow `Vehicle` or `Trademark` as the reference pattern; add `detail.html`; add `search()` + uniqueness `existsXxx()` queries in the repository; add `normalizeAndValidate()` in the service; expose enum lists via `populateEnums(model)` in the controller.
- **Validation** – use `jakarta.validation` annotations on entity fields and `@Valid` + `BindingResult` in rich controllers; re-render the form on errors instead of redirecting.
- **Persistence** – always extend the JPA/H2 stack; do not introduce a second persistence approach.
- **UI** – always add Thymeleaf templates; do not introduce a JS framework.
- **Tests** – two existing test classes: `AssetManagementServiceApplicationTests` (full Spring context, `@SpringBootTest`) and `HomeControllerWebMvcTest` (`@WebMvcTest(HomeController.class)`). Mirror this split for new test classes.
- **`pom.xml` metadata** – empty `<license/>`, `<developers/>`, `<scm/>` elements override Spring Boot parent inheritance; do not remove them.

---

## Developer workflow
- Build / test / run on Windows: `./mvnw.cmd test`, `./mvnw.cmd spring-boot:run`, `./mvnw.cmd package`.
- `JAVA_HOME` must point to a Java 25 JDK before any Maven wrapper command will succeed.
- No Gradle, Docker, or custom script layer exists; keep commands Maven-native.
- H2 console available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:assetdb`, user: `sa`, no password`).
