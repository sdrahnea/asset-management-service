# Brand — Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

## Service Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-001 | Valid brand created | No exception |
| TS-S-002 | Missing brandId | `"Brand ID is required"` |
| TS-S-003 | Missing brandName | `"Brand name is required"` |
| TS-S-004 | Missing parentCompany | `"Parent company is required"` |
| TS-S-005 | Missing dataSource | `"Data source is required"` |
| TS-S-006 | brandId normalized to uppercase | `brand-001` → `BRAND-001` |
| TS-S-007 | Expiration before registration rejected | `"Expiration date must be on or after registration date"` |
| TS-S-008 | Expiration equals registration accepted | No exception |
| TS-S-009 | Duplicate brandId rejected | `"Brand ID must be unique"` |
| TS-S-010 | Duplicate trademarkRegistrationNumber rejected | `"Trademark registration number must be unique"` |
| TS-S-011 | Null trademarkRegistrationNumber bypasses uniqueness | No exception |
| TS-S-012 | Same brandId on update self-record | No exception (excludeId) |

## Controller Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-C-001 | List loads | 200, model has `brands` |
| TS-C-002 | Create form loads | 200, defaults set |
| TS-C-003 | Valid create redirects | 302 → `/brands` |
| TS-C-004 | Invalid create re-renders form | 200 with errors |
| TS-C-005 | Detail page loads | 200 |
| TS-C-006 | Detail 404 | 404 |
| TS-C-007 | Valid update redirects | 302 → `/brands` |
| TS-C-008 | Delete redirects | 302 → `/brands` |

## API Surface
| Method | Path | Description |
|--------|------|-------------|
| GET | `/brands` | List |
| GET | `/brands/new` | Create form |
| POST | `/brands` | Submit create |
| GET | `/brands/{id}` | Detail |
| GET | `/brands/{id}/edit` | Edit form |
| POST | `/brands/{id}` | Submit update |
| POST | `/brands/{id}/delete` | Delete |

