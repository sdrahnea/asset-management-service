# Brand — User Stories

**Version**: 1.0 | **Date**: 2026-04-04

## US-001 — Create Brand Record
**As a** Brand Manager, **I want to** create a brand record **so that** it can be tracked and assessed.
- [ ] Form at `/brands/new` with all field groups
- [ ] Mandatory: brandId, brandName, parentCompany, brandStatus, dataSource
- [ ] Unique brandId and trademarkRegistrationNumber enforced

## US-002 — Filter Brand List
**As a** Analyst, **I want to** filter brands by status, category, and country **so that** I can find relevant brands quickly.
- [ ] Filter form on list page

## US-003 — View Brand Detail
**As a** Analyst, **I want to** see all brand data grouped by section **so that** I can perform a complete assessment.
- [ ] Detail page at `/brands/{id}` with sections: Identity, Legal, Market, Performance, Digital, Financial, Metadata

## US-004 — Edit Brand Record
**As a** Brand Manager, **I want to** update brand data **so that** it stays current.
- [ ] Edit form pre-populated; uniqueness excludes current record

## US-005 — Delete Brand Record
**As a** Brand Manager, **I want to** delete obsolete brand records.
- [ ] Delete via `POST /brands/{id}/delete`

## US-006 — Track Legal and Trademark Status
**As a** Compliance Officer, **I want to** record trademark registration, jurisdiction, and expiration **so that** IP risks are visible.
- [ ] trademarkRegistrationNumber, type, jurisdictions, dates, and status editable
- [ ] Expiration ≥ registration enforced

