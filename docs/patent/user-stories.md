# Patent — User Stories

**Version**: 1.0 | **Date**: 2026-04-04

## User Stories
- **US-001** As an IP analyst, I create a patent record so filings are tracked.
- **US-002** As legal counsel, I manage status and legal dates to avoid compliance gaps.
- **US-003** As portfolio manager, I record valuation/revenue/royalty data.
- **US-004** As strategy analyst, I track risk/strength/blocking indicators.
- **US-005** As operations user, I filter list by type/status/technology/assignee.

## Acceptance Scenarios
| ID | Scenario | Expected |
|----|----------|----------|
| TS-001 | Create with mandatory fields | Redirect to list |
| TS-002 | Missing mandatory fields | Re-render with errors |
| TS-003 | Invalid chronology | Business validation error |
| TS-004 | GRANTED without grant number/date | Business validation error |
| TS-005 | Duplicate key identifiers | Business validation error |
| TS-006 | Valid update same record IDs | Allowed via excludeId |
| TS-007 | Detail view existing patent | 200 + detail template |

