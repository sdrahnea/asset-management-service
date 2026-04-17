# Invoice - Implementation Tasks

**Version**: 1.0 | **Date**: 2026-04-15

---

## Phase 1 - Specification and Baseline ✅ Complete

- [x] TASK-001: Define invoice domain model from provided JSON contract
- [x] TASK-002: Document field-level requirements and validation matrix
- [x] TASK-003: Document route and API surface
- [x] TASK-004: Create test scenario catalog for service/controller/UI

## Phase 2 - Backend Implementation ✅ Complete

- [x] TASK-100: `Invoice` entity with `@ElementCollection` of `InvoiceItem` and two `@Embedded` `InvoiceParty` objects (seller/buyer); `@AttributeOverride` for invoiceId
- [x] TASK-101: `InvoiceRepository` with `search(...)` JPQL, `existsInvoiceId`, `existsInvoiceNumber`
- [x] TASK-102: `InvoiceService` with full `normalizeAndValidate`: party normalization, empty-item stripping, arithmetic consistency (lineTotal, subtotal, totalTax, totalAmount, balanceDue), IBAN checksum, uniqueness checks, PAID→zero-balance rule
- [x] TASK-103: `InvoiceController` — full CRUD + filter endpoints; pre-fills 5 blank item rows; `BeanUtils` copy on update

## Phase 3 - UI Implementation ✅ Complete

- [x] TASK-200: `invoices/list.html` — filters (invoiceNumber, status, sellerName, buyerName, issueDateFrom/To), key columns
- [x] TASK-201: `invoices/form.html` — header, seller, buyer, item table (5 rows), totals/payment, audit read-only
- [x] TASK-202: `invoices/detail.html` — all sections with item table and totals block
- [x] TASK-203: Home page navigation wired under Financial Assets

## Phase 4 - Quality and Demo Data (Planned)

- [ ] TASK-300: Add service tests for business/uniqueness/normalization rules
- [ ] TASK-301: Add controller `@WebMvcTest` for happy path and validation failures
- [ ] TASK-302: Filter by dueDateFrom/To on list page (model attributes already passed, form field not yet exposed)

