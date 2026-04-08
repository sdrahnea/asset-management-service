# Machinery — User Stories

**Version**: 1.0 | **Date**: 2026-04-04

## User Stories
- **US-001** As an asset manager, I create machinery records so assets are traceable.
- **US-002** As an operations lead, I view machinery list data for planning and utilization.
- **US-003** As a maintenance coordinator, I update condition and maintenance schedule fields.
- **US-004** As a finance user, I record purchase/book/insurance/depreciation values.
- **US-005** As a compliance officer, I maintain certifications, permits, and classification.
- **US-006** As a risk analyst, I maintain risk/criticality/utilization indicators.

## Acceptance Focus
| ID | Scenario | Expected |
|----|----------|----------|
| TS-001 | Create with required fields | Success redirect to list |
| TS-002 | Missing required field | Form re-render with validation error |
| TS-003 | Future yearOfManufacture | Validation failure |
| TS-004 | Duplicate machineId | Global business error |
| TS-005 | Duplicate serialNumber | Global business error |
| TS-006 | Valid update | Success redirect |
| TS-007 | Delete item | Removed from list |

