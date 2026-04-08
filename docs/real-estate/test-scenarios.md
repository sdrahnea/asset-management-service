# Real Estate — Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

## Service Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-001 | Create valid property | Persisted |
| TS-S-002 | Missing required title/address/owner | Validation error |
| TS-S-003 | Missing required enums/values | Validation error |
| TS-S-004 | Duplicate cadastral number | Business error |
| TS-S-005 | Duplicate land registry number | Business error |
| TS-S-006 | Coordinate only latitude or longitude | Business error |
| TS-S-007 | Year built in future | Business error |
| TS-S-008 | Valuation date in future | Business error |
| TS-S-009 | Last renovation in future | Business error |
| TS-S-010 | builtArea < usableArea | Business error |
| TS-S-011 | Identifier normalization (spaces/case) | Uppercase no spaces |
| TS-S-012 | Yield calculation when price/income exist | Yield computed |

## Controller Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-C-001 | `GET /real-estates` | 200 list view |
| TS-C-002 | filters applied on list | filtered result set |
| TS-C-003 | `GET /real-estates/new` | 200 form defaults |
| TS-C-004 | valid create POST | redirect `/real-estates` |
| TS-C-005 | invalid create POST | form with errors |
| TS-C-006 | `GET /real-estates/{id}` | 200 detail |
| TS-C-007 | unknown id detail/edit | 404 |
| TS-C-008 | valid update POST | redirect `/real-estates` |
| TS-C-009 | invalid update POST | form with errors |
| TS-C-010 | delete POST | currently redirects `/bank-accounts` |

## UI Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-U-001 | Enum dropdowns populated | all values shown |
| TS-U-002 | Utilities/amenities options available | checkbox groups shown |
| TS-U-003 | Global business error block rendered | visible on form |

