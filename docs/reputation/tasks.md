# Reputation — Implementation Tasks

**Version**: 1.0 | **Date**: 2026-04-04

## Phase 1 (Implemented)
- [x] Create `Reputation` entity with domain fields and enums
- [x] Add `@AttributeOverride` for `reputation_id`
- [x] Add uniqueness constraints for ID and subject reference
- [x] Implement service normalization and business validation
- [x] Implement repository search and uniqueness checks
- [x] Implement controller CRUD + detail + list filters
- [x] Implement templates `reputations/list.html`, `reputations/form.html`, `reputations/detail.html`

## Phase 2 (Planned)
- [ ] Add focused service unit tests for event and uniqueness rules
- [ ] Add WebMvc tests for form/global error flows and filters
- [ ] Add dashboard-level aggregations (trend, risk, sentiment)

## Phase 3 (Planned)
- [ ] Add API endpoints for external reporting/integration
- [ ] Add automated sentiment feed ingestion hooks
- [ ] Add alerting for high-risk volatility/event spikes

## Quality Gates
- [x] Mandatory fields validated
- [x] Event consistency rules validated
- [x] Uniqueness constraints validated
- [ ] Dedicated service/controller test suite completed

