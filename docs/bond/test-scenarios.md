# Bond — Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

---

## 1. Service Tests

| ID | Scenario | Input | Expected |
|----|----------|-------|----------|
| TS-S-001 | Valid bond created | All mandatory fields set | No exception, record saved |
| TS-S-002 | Missing bondId rejected | bondId=null | `"Bond ID is required"` |
| TS-S-003 | Missing title rejected | title=null | `"Bond title is required"` |
| TS-S-004 | Missing issuer rejected | issuer=null | `"Issuer is required"` |
| TS-S-005 | Missing currency rejected | currency=null | `"Currency is required"` |
| TS-S-006 | Missing bondType rejected | bondType=null | `"Bond type is required"` |
| TS-S-007 | Missing dataSource rejected | dataSource=null | `"Data source is required"` |
| TS-S-008 | bondId normalized to uppercase | `bond-001` | Stored as `BOND-001` |
| TS-S-009 | ISIN normalized to uppercase | `ro1234567890` | Stored as `RO1234567890` |
| TS-S-010 | maturityDate < issueDate rejected | issueDate=2024-01-01, maturityDate=2023-01-01 | `"Maturity date must be on or after issue date"` |
| TS-S-011 | callDate < issueDate rejected | callDate=2023-01-01, issueDate=2024-01-01 | `"Call date must be on or after issue date"` |
| TS-S-012 | callDate > maturityDate rejected | callDate=2040-01-01, maturityDate=2032-01-01 | `"Call date must be on or before maturity date"` |
| TS-S-013 | Duplicate bondId rejected | Same bondId as existing | `"Bond ID must be unique"` |
| TS-S-014 | Duplicate ISIN rejected | Same ISIN | `"ISIN must be unique"` |
| TS-S-015 | Same ISIN on update self-record accepted | ISIN matches own record | No exception (excludeId) |
| TS-S-016 | Null ISIN bypasses uniqueness check | isin=null | No exception |

---

## 2. Controller Tests

| ID | Scenario | Endpoint | Expected |
|----|----------|----------|----------|
| TS-C-001 | List loads with no filters | `GET /bonds` | 200, model has `bonds` |
| TS-C-002 | List filtered by bondType | `GET /bonds?bondType=GOVERNMENT` | 200, filtered results |
| TS-C-003 | Create form loads | `GET /bonds/new` | 200, model has `bond` with defaults |
| TS-C-004 | Valid create redirects | `POST /bonds` (valid payload) | 302 → `/bonds` |
| TS-C-005 | Invalid create re-renders form | Missing issuer | 200, form with errors |
| TS-C-006 | Duplicate bondId shows global error | Duplicate bondId | 200, global error message |
| TS-C-007 | Detail page loads | `GET /bonds/1` | 200, model has `bond` |
| TS-C-008 | Detail 404 for unknown id | `GET /bonds/999` | 404 |
| TS-C-009 | Edit form loads | `GET /bonds/1/edit` | 200, isEdit=true |
| TS-C-010 | Valid update redirects | `POST /bonds/1` (valid) | 302 → `/bonds` |
| TS-C-011 | Delete redirects | `POST /bonds/1/delete` | 302 → `/bonds` |

---

## 3. Edge Cases

| ID | Scenario | Expected |
|----|----------|----------|
| TS-E-001 | Bond with only mandatory fields — optional fields null | Saved successfully |
| TS-E-002 | Null callDate — no date-ordering check triggered | No exception |
| TS-E-003 | bondId with spaces normalized | Spaces stripped before uniqueness check |

