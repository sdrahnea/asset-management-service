# Invoice - Implementation Tasks

**Version**: 1.0 | **Date**: 2026-04-15

---

## Phase 1 - Specification and Baseline

- [x] TASK-001: Define invoice domain model from provided JSON contract
- [x] TASK-002: Document field-level requirements and validation matrix
- [x] TASK-003: Document route and API surface (planned)
- [x] TASK-004: Create test scenario catalog for service/controller/UI

## Phase 2 - Backend Implementation (Planned)

- [ ] TASK-100: Create `Invoice` entity with nested party/item structure
- [ ] TASK-101: Add repository and uniqueness queries for invoiceNumber
- [ ] TASK-102: Implement service-level normalization and total consistency checks
- [ ] TASK-103: Implement controller CRUD and filter endpoints

## Phase 3 - UI Implementation (Planned)

- [ ] TASK-200: Create `invoices/list.html` with filters and key columns
- [ ] TASK-201: Create `invoices/form.html` with grouped sections
- [ ] TASK-202: Create `invoices/detail.html` with item table and totals block
- [ ] TASK-203: Add read-only audit fields on edit/detail views

## Phase 4 - Quality and Demo Data (Planned)

- [ ] TASK-300: Add service tests for business/uniqueness/normalization rules
- [ ] TASK-301: Add controller tests for happy path and validation failures
- [ ] TASK-302: Add demo data seed examples for presentation mode

