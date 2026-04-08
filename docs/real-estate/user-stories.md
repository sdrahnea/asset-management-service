# Real Estate — User Stories

**Version**: 1.0 | **Date**: 2026-04-04

## User Stories
- **US-001** As an assessor, I create real-estate records so properties are consistently evaluated.
- **US-002** As a portfolio manager, I filter properties by type/ownership/neighborhood/date/status.
- **US-003** As a legal user, I maintain cadastral and land-registry identifiers and ownership data.
- **US-004** As a finance user, I capture valuation/cost/income fields and review yield indicators.
- **US-005** As a risk analyst, I maintain maintenance and risk score fields.

## Acceptance Scenarios
| ID | Scenario | Expected |
|----|----------|----------|
| TS-001 | Create with mandatory fields | Redirect to list |
| TS-002 | Missing mandatory fields | Form errors displayed |
| TS-003 | Duplicate cadastral/land-registry numbers | Global business error |
| TS-004 | Invalid date or area relationships | Global business error |
| TS-005 | Valid update | Redirect to list |
| TS-006 | Detail fetch unknown ID | 404 |

