# Copyright — User Stories & Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

---

## User Stories

### US-001 — Create Copyright Record
**As a** Compliance Manager, **I want to** create a copyright record **so that** IP assets can be tracked and assessed.
- [ ] Mandatory: copyrightId, title, workType, authors, copyrightOwners, countryOfOrigin, copyrightStatus, sourceOfInformation
- [ ] copyrightId unique; pattern validation (alphanumeric)

### US-002 — View Copyright List
**As a** IP Manager, **I want to** see all copyrights with key details **so that** I can manage the portfolio.
- [ ] List at `/copyrights` with copyrightId, title, authors, workType, copyrightStatus, registrationNumber, countryOfOrigin

### US-003 — Edit a Copyright Record
**As a** IP Manager, **I want to** update a copyright position **so that** the data reflects current status.
- [ ] All 40+ fields editable
- [ ] Temporal validation enforced

### US-004 — Track Legal Status
**As a** Legal Team, **I want to** record registration number, authority, status, and protection dates **so that** legal compliance is visible.
- [ ] Registration metadata fields editable
- [ ] Protection date range validation

### US-005 — Record Financial Information
**As a** Finance Manager, **I want to** record valuation, royalty rates, and revenue **so that** financial impact is assessed.
- [ ] Financial fields editable with decimal validation

### US-006 — Manage Rights & Licensing
**As a** Licensing Manager, **I want to** record licensing type, licensees, permitted uses, royalty terms **so that** license agreements are tracked.
- [ ] Licensing section complete and editable

### US-007 — Assess Risk
**As a** Risk Analyst, **I want to** record infringement risk, disputes risk, expiration risk, and compliance flags **so that** portfolio risk is visible.
- [ ] Risk fields editable with dropdown selections

---

## Test Scenarios

| ID | Scenario | Expected |
|----|----------|----------|
| TS-001 | Valid copyright record created | No exception |
| TS-002 | Missing copyrightId | `"Copyright ID is required"` |
| TS-003 | Missing title | `"Title is required"` |
| TS-004 | Missing authors | `"Author(s) is required"` |
| TS-005 | Missing copyrightStatus | `"Copyright status is required"` |
| TS-006 | Missing sourceOfInformation | `"Source of information is required"` |
| TS-007 | copyrightId pattern invalid | Validation error |
| TS-008 | copyrightId normalized and unique | Duplicate rejected |
| TS-009 | Same copyrightId on update self | No exception (excludeId) |
| TS-010 | Create form loads with defaults | 200, defaults applied |
| TS-011 | Valid POST redirects | 302 → `/copyrights` |
| TS-012 | Invalid POST re-renders form | 200 with errors |
| TS-013 | All 40+ fields save correctly | Full data persisted |
| TS-014 | Temporal validation: creation ≤ publication | Enforced |
| TS-015 | Temporal validation: protection end ≥ start | Enforced |
| TS-016 | Decimal validation: ownership % 0–100 | Enforced |
| TS-017 | Edit form pre-populated | Current values shown |
| TS-018 | Delete removes record | List no longer shows |

