# Patent — Implementation Tasks

**Version**: 1.0 | **Date**: 2026-04-04

## Phase 1 (Implemented)
- [x] Create `Patent` entity with domain fields and enums
- [x] Add `@AttributeOverride` for `patent_id`
- [x] Add unique constraints for patent/application/publication/grant numbers
- [x] Implement service normalization and business validation
- [x] Implement repository search and uniqueness checks
- [x] Implement controller CRUD + detail + filter list
- [x] Implement templates `patents/list.html`, `patents/form.html`, `patents/detail.html`

## Phase 2 (Planned)
- [ ] Add focused unit tests for date/business/uniqueness rules
- [ ] Add controller web tests for form error flows and filters
- [ ] Add dashboard summary widgets (status/risk/distribution)

## Phase 3 (Planned)
- [ ] Add API endpoints for portfolio integration
- [ ] Add external office sync hooks and deadline notifications
- [ ] Add advanced analytics (citations/strength trend)

## Quality Gates
- [x] Mandatory fields validated
- [x] Chronological/date rules validated
- [x] GRANTED-specific constraints validated
- [x] Uniqueness constraints validated
- [ ] Dedicated service/controller test suite completed

