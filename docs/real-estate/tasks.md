# Real Estate — Implementation Tasks

**Version**: 1.0 | **Date**: 2026-04-04

## Phase 1 (Implemented)
- [x] Rich entity model with extensive enum and field coverage
- [x] Unique constraints for cadastral and land-registry numbers
- [x] Service normalization and business validations
- [x] Search/list filtering support in repository/controller
- [x] CRUD + detail controller routes
- [x] Templates: list/form/detail

## Phase 2 (Planned)
- [ ] Add focused unit tests for business-rule matrix
- [ ] Add controller web tests for filter and error flows
- [ ] Refine UX for large form sections and grouped validations

## Phase 3 (Planned)
- [ ] Add API endpoints for external portfolio/reporting
- [ ] Add valuation/market integration hooks
- [ ] Add deadline/maintenance notifications

## Known Caveat
- Controller delete currently redirects to `/bank-accounts` (code behavior). Should be corrected to `/real-estates` in future fix.

