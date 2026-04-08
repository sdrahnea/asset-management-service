# Patent — Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

## Service Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-001 | Valid create | Persisted |
| TS-S-002 | Missing patentId | Validation error |
| TS-S-003 | Missing applicationDate | Validation error |
| TS-S-004 | publicationDate before applicationDate | Business error |
| TS-S-005 | grantDate before applicationDate | Business error |
| TS-S-006 | expiryDate before grantDate | Business error |
| TS-S-007 | legalStatus=GRANTED without grantNumber | Business error |
| TS-S-008 | legalStatus=GRANTED without grantDate | Business error |
| TS-S-009 | duplicate patentId/applicationNumber | Business error |
| TS-S-010 | duplicate publication/grant number (if set) | Business error |
| TS-S-011 | update self with same IDs | Allowed |
| TS-S-012 | identifier normalization (spaces/case) | Stored uppercase/no spaces |

## Controller Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-C-001 | `GET /patents` | 200 list view |
| TS-C-002 | list filter params applied | results filtered |
| TS-C-003 | `GET /patents/new` | 200 form with defaults |
| TS-C-004 | valid create POST | 302 redirect |
| TS-C-005 | invalid create POST | 200 form with errors |
| TS-C-006 | `GET /patents/{id}` | 200 detail |
| TS-C-007 | `GET /patents/{id}/edit` | 200 edit form |
| TS-C-008 | valid update POST | 302 redirect |
| TS-C-009 | invalid update POST | 200 form with errors |
| TS-C-010 | delete POST | 302 redirect |

## UI Tests
| ID | Scenario | Expected |
|----|----------|----------|
| TS-U-001 | enum dropdowns populated | all values shown |
| TS-U-002 | global business error rendering | visible above form |
| TS-U-003 | required markers and field errors | visible and consistent |
| TS-U-004 | detail page displays key legal IDs and status | values shown |

