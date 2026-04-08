# Trademark — Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

## Service Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-001 | Valid create | Persisted |
| TS-S-002 | Missing mandatory fields | Validation error |
| TS-S-003 | priorityClaimed true without number/date | Business error |
| TS-S-004 | priorityDate after applicationDate | Business error |
| TS-S-005 | registrationDate before applicationDate | Business error |
| TS-S-006 | expirationDate before registrationDate | Business error |
| TS-S-007 | firstUseInCommerce before firstUse | Business error |
| TS-S-008 | duplicate trademarkId/application/registration | Business error |
| TS-S-009 | update self same identifiers | Allowed |
| TS-S-010 | identifier normalization | Uppercase/no spaces |

## Controller Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-C-001 | `GET /trademarks` | 200 list view |
| TS-C-002 | list filters applied | filtered result set |
| TS-C-003 | `GET /trademarks/new` | 200 form defaults |
| TS-C-004 | valid create POST | redirect `/trademarks` |
| TS-C-005 | invalid create POST | 200 form with errors |
| TS-C-006 | `GET /trademarks/{id}` | 200 detail |
| TS-C-007 | unknown ID detail/edit | 404 |
| TS-C-008 | valid update POST | redirect `/trademarks` |
| TS-C-009 | invalid update POST | 200 form with errors |
| TS-C-010 | delete POST | redirect `/trademarks` |

## UI Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-U-001 | enum dropdowns populated | all enum values visible |
| TS-U-002 | global business errors rendered | visible above form |
| TS-U-003 | required markers and field errors | visible and consistent |

