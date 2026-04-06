# Cash — User Stories, Tests, and Remaining Specs

**Version**: 1.0 | **Date**: 2026-04-04

---

## User Stories

### US-001 — Create Cash Position Record
**As a** Treasury Analyst, **I want to** create a cash position record **so that** it can be tracked and assessed.
- [ ] Mandatory: cashId, amount, currency, valuationDate, cashType, dataSource
- [ ] cashId unique; amount ≥ 0

### US-002 — View Cash List
**As a** Treasury Analyst, **I want to** see all cash positions with key details **so that** I can monitor the portfolio.
- [ ] List at `/cash` with cashId, amount, currency, cashType, institution, valuationDate

### US-003 — Edit a Cash Record
**As a** Treasury Analyst, **I want to** update a cash position **so that** the data reflects the latest position.

### US-004 — Track Reconciliation Status
**As a** Finance Controller, **I want to** record reconciliation status **so that** I can identify unreconciled cash.
- [ ] reconciliationStatus field editable on form

### US-005 — Record Risk Indicators
**As a** Risk Analyst, **I want to** record counterparty risk, concentration risk, and compliance flags **so that** cash risk is visible.

---

## Test Scenarios

| ID | Scenario | Expected |
|----|----------|----------|
| TS-001 | Valid cash record created | No exception |
| TS-002 | Missing cashId | `"Cash ID is required"` |
| TS-003 | Missing amount | `"Amount is required"` |
| TS-004 | Amount < 0 | Validation error |
| TS-005 | Missing currency | `"Currency is required"` |
| TS-006 | Missing valuationDate | `"Valuation date is required"` |
| TS-007 | Missing dataSource | `"Data source is required"` |
| TS-008 | cashId normalized to uppercase | `cash-001` → `CASH-001` |
| TS-009 | Duplicate cashId rejected | `"Cash ID must be unique"` |
| TS-010 | Same cashId on update self | No exception (excludeId) |
| TS-011 | Create form loads with defaults | 200, cashType=OPERATING |
| TS-012 | Valid POST redirects | 302 → `/cash` |
| TS-013 | Invalid POST re-renders form | 200 with errors |

