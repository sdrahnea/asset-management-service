# Machinery — Implementation Tasks

**Version**: 1.0 | **Date**: 2026-04-04

## Phase 1 (Implemented)
- [x] Create `Machinery` entity with full domain fields
- [x] Add enum definitions and validation annotations
- [x] Add `@AttributeOverride` for `machine_id`
- [x] Add unique constraints for `machine_id` and `serial_number`
- [x] Create repository with uniqueness checks
- [x] Implement service CRUD + normalize/validate
- [x] Implement controller CRUD routes for `/machineries`
- [x] Create `machineries/list.html` and `machineries/form.html`

## Phase 2 (Planned)
- [ ] Add `machineries/detail.html`
- [ ] Add list filtering (category, condition, criticality, location)
- [ ] Add list sorting and pagination
- [ ] Add service unit tests
- [ ] Add controller web tests

## Phase 3 (Planned)
- [ ] Add REST API endpoints
- [ ] Add alerting for overdue maintenance and high risk
- [ ] Add optional ERP/CMMS integration hooks

## Quality Gates
- [x] Required-field validation and uniqueness checks enforced
- [x] Year/range constraints enforced
- [ ] Test coverage target for service and controller achieved

