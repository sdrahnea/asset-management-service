# Vehicle — User Stories

**Version**: 1.0 | **Date**: 2026-04-04

## User Stories
- **US-001** As an asset manager, I create vehicle records with unique VIN and plate-country identity.
- **US-002** As a fleet manager, I filter vehicles by type, registration status, and ownership type.
- **US-003** As a maintenance coordinator, I maintain service history and due dates.
- **US-004** As a finance analyst, I maintain valuation and operating-cost fields.
- **US-005** As a risk analyst, I maintain assessment and compliance risk indicators.

## Acceptance Scenarios
| ID | Scenario | Expected |
|----|----------|----------|
| TS-001 | Create with mandatory fields | Redirect to `/vehicles` |
| TS-002 | Missing mandatory fields | Form with field errors |
| TS-003 | Duplicate vehicleId/vin/plate-country | Global business error |
| TS-004 | Future dates / invalid year | Global business error |
| TS-005 | Invalid service date ordering | Global business error |
| TS-006 | Valid update | Redirect to list |
| TS-007 | Unknown detail/edit ID | 404 |

