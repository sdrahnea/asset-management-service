# Stock — Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

## Service Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-001 | Valid create | Persisted |
| TS-S-002 | Missing stockId/ticker/exchange/companyName/dataSource | Validation error |
| TS-S-003 | Duplicate stockId | Business error |
| TS-S-004 | Duplicate ticker+exchange | Business error |
| TS-S-005 | Duplicate ISIN/CUSIP (if set) | Business error |
| TS-S-006 | dayHigh < dayLow | Business error |
| TS-S-007 | 52-week high < low | Business error |
| TS-S-008 | currentPrice below dayLow | Business error |
| TS-S-009 | currentPrice above dayHigh | Business error |
| TS-S-010 | Update self identifiers | Allowed |
| TS-S-011 | Identifier normalization | Uppercase/no spaces |

## Controller Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-C-001 | `GET /stocks` | 200 list view |
| TS-C-002 | list filters applied | filtered result set |
| TS-C-003 | `GET /stocks/new` | 200 form defaults |
| TS-C-004 | valid create POST | redirect `/stocks` |
| TS-C-005 | invalid create POST | 200 form with errors |
| TS-C-006 | `GET /stocks/{id}` | 200 detail |
| TS-C-007 | unknown ID detail/edit | 404 |
| TS-C-008 | valid update POST | redirect `/stocks` |
| TS-C-009 | invalid update POST | 200 form with errors |
| TS-C-010 | delete POST | redirect `/stocks` |

## UI Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-U-001 | enum dropdowns populated | all values visible |
| TS-U-002 | global business errors rendered | visible above form |
| TS-U-003 | required markers and field errors | visible and consistent |

