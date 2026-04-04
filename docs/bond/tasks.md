# Bond — Implementation Tasks

**Version**: 1.0 | **Date**: 2026-04-04

---

## Phase 1 — Core Implementation ✅ Complete

- [x] ✅ TASK-001: Bond entity with all field groups, enums, constraints, `@AttributeOverride` for bondId
- [x] ✅ TASK-002: Unique constraints for bondId, isin, cusipSedol
- [x] ✅ TASK-003: Business rule assertions (date ordering)
- [x] ✅ TASK-004: BondRepository with search() and 3 uniqueness queries
- [x] ✅ TASK-005: BondService with normalizeAndValidate(), mandatory/business/uniqueness checks
- [x] ✅ TASK-006: BondController — full CRUD with filters, @Valid, BindingResult, detail endpoint
- [x] ✅ TASK-007: `bonds/list.html` with filter form and key columns
- [x] ✅ TASK-008: `bonds/form.html` with grouped field sections
- [x] ✅ TASK-009: `bonds/detail.html` with all fields

## Phase 2 — Enhancements (Planned)

- [ ] 🔲 TASK-100: Filter by maturity date range on list page
- [ ] 🔲 TASK-101: Filter by rating
- [ ] 🔲 TASK-102: Auto-calculate yield metrics from coupon and face value
- [ ] 🔲 TASK-103: Service unit tests (business rules, uniqueness, normalization)
- [ ] 🔲 TASK-104: Controller @WebMvcTest (form submit, validation errors, redirect)
- [ ] 🔲 TASK-105: Pagination and sorting on list page
- [ ] 🔲 TASK-106: Soft-delete support

