# Machinery — Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

## Service Scenarios
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-001 | Create valid machinery | Persisted successfully |
| TS-S-002 | Missing machineId | Validation error |
| TS-S-003 | Invalid machineId pattern | Validation error |
| TS-S-004 | Missing category | Validation error |
| TS-S-005 | Missing manufacturer/model | Validation error |
| TS-S-006 | Missing serialNumber | Validation error |
| TS-S-007 | Duplicate machineId | Business error |
| TS-S-008 | Duplicate serialNumber | Business error |
| TS-S-009 | Future yearOfManufacture | Validation error |
| TS-S-010 | utilizationRate > 100 | Validation error |
| TS-S-011 | Negative price/cost value | Validation error |
| TS-S-012 | Update same machine (excludeId) | Allowed |

## Controller Scenarios
| ID | Scenario | Expected |
|----|----------|----------|
| TS-C-001 | `GET /machineries` | 200 + list template |
| TS-C-002 | `GET /machineries/new` | 200 + form template |
| TS-C-003 | Valid `POST /machineries` | 302 redirect |
| TS-C-004 | Invalid POST with missing field | 200 + form errors |
| TS-C-005 | Valid `POST /machineries/{id}` | 302 redirect |
| TS-C-006 | `POST /machineries/{id}/delete` | 302 redirect |

## UI Scenarios
| ID | Scenario | Expected |
|----|----------|----------|
| TS-U-001 | Required fields marked | Visible on form |
| TS-U-002 | Enum dropdowns populated | All enum values present |
| TS-U-003 | Global service error shown | Error block rendered |
| TS-U-004 | List actions present | Edit/Delete available |

