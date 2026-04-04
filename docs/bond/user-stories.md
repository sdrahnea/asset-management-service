# Bond — User Stories

**Version**: 1.0 | **Date**: 2026-04-04

---

## US-001 — Create a Bond Record
**As a** Financial Analyst, **I want to** create a bond record with identification, financial, and lifecycle data **so that** it is available for assessment and reporting.

**Acceptance Criteria**:
- [ ] Form at `/bonds/new` with all field groups
- [ ] Mandatory: bondId, title, issuer, currency, bondType, dataSource
- [ ] Default values: bondType=CORPORATE, tradingStatus=ACTIVE, dataSource=manual-entry
- [ ] Unique bondId, ISIN, CUSIP/SEDOL enforced on submit
- [ ] Redirect to list on success

## US-002 — Filter Bond List
**As a** Financial Analyst, **I want to** filter bonds by issuer, type, trading status, and currency **so that** I can quickly find relevant instruments.

**Acceptance Criteria**:
- [ ] Filter form on list page with issuer (text), bondType (dropdown), tradingStatus (dropdown), currency (text)
- [ ] Reset link clears filters

## US-003 — View Bond Detail
**As a** Financial Analyst, **I want to** view the full bond record in a structured layout grouped by category **so that** I can perform a complete assessment.

**Acceptance Criteria**:
- [ ] Detail page at `/bonds/{id}` showing all fields
- [ ] Sections: Identification, Financial, Lifecycle, Legal, Market, Risk, Metadata, Audit

## US-004 — Edit a Bond Record
**As a** Financial Analyst, **I want to** update bond data **so that** the record stays current.

**Acceptance Criteria**:
- [ ] Edit form at `/bonds/{id}/edit` pre-populated
- [ ] Uniqueness excludes current record
- [ ] Business rule (date ordering) validated on save

## US-005 — Delete a Bond Record
**As a** Financial Analyst, **I want to** delete an obsolete bond record.

**Acceptance Criteria**:
- [ ] Delete action on list row; POST to `/bonds/{id}/delete`; redirect to list

## US-006 — Record Risk and Assessment Data
**As a** Risk Analyst, **I want to** record risk scores, duration, convexity, and credit indicators **so that** the bond can be automatically evaluated.

**Acceptance Criteria**:
- [ ] All risk fields (riskScore, durationMacaulay, durationModified, convexity, probabilityOfDefault, lossGivenDefault, valueAtRisk, esgScore) editable on form
- [ ] All scores visible on detail page

