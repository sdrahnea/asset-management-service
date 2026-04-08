# Reputation — Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

## Service Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-001 | Valid create | Persisted |
| TS-S-002 | Missing reputationId/entityId/displayName/dataSource | Validation error |
| TS-S-003 | Missing entityType | Validation error |
| TS-S-004 | eventDate in future | Business error |
| TS-S-005 | event details provided without eventType | Business error |
| TS-S-006 | Duplicate reputationId | Business error |
| TS-S-007 | Duplicate (entityType, entityId) | Business error |
| TS-S-008 | Update self same identifiers | Allowed |
| TS-S-009 | Identifier normalization | Stored uppercase/no spaces |

## Controller Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-C-001 | `GET /reputations` | 200 list view |
| TS-C-002 | list filters applied | filtered result set |
| TS-C-003 | `GET /reputations/new` | 200 form with defaults |
| TS-C-004 | valid create POST | redirect `/reputations` |
| TS-C-005 | invalid create POST | 200 form + errors |
| TS-C-006 | `GET /reputations/{id}` | 200 detail |
| TS-C-007 | unknown ID | 404 |
| TS-C-008 | valid update POST | redirect list |
| TS-C-009 | invalid update POST | form errors shown |
| TS-C-010 | delete POST | redirect list |

## UI Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-U-001 | enum dropdowns populated | all enum values visible |
| TS-U-002 | global business error block rendered | visible on form |
| TS-U-003 | required markers and field errors | visible and consistent |

