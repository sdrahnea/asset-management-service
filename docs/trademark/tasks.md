# Trademark — Implementation Tasks

**Version**: 1.0 | **Date**: 2026-04-04

## Phase 1 (Implemented)
- [x] Create `Trademark` entity with rich legal/IP fields and enums
- [x] Add `@AttributeOverride` for `trademark_id`
- [x] Add uniqueness constraints (`trademarkId`, application, registration)
- [x] Implement service normalization and business validation
- [x] Implement repository search and uniqueness checks
- [x] Implement controller CRUD + detail + list filtering
- [x] Implement templates `trademarks/list.html`, `trademarks/form.html`, `trademarks/detail.html`

## Phase 2 (Planned)
- [ ] Add focused service unit tests for priority/date/uniqueness rules
- [ ] Add WebMvc tests for create/update/global-error flows and filters
- [ ] Add summary views for lifecycle and renewal risk

## Phase 3 (Planned)
- [ ] Add external office integration hooks (WIPO/EUIPO/national)
- [ ] Add renewal/deadline notifications
- [ ] Add API endpoints for portfolio integrations

## Quality Gates
- [x] Mandatory validation enforced
- [x] Priority and date-order rules enforced
- [x] Uniqueness constraints enforced
- [ ] Dedicated automated service/controller tests completed

