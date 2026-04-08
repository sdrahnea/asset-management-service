# Trademark — User Stories

**Version**: 1.0 | **Date**: 2026-04-04

## User Stories
- **US-001** As an IP analyst, I create trademark records with legal identifiers and status.
- **US-002** As legal counsel, I track filing/priority/registration/expiration lifecycle dates.
- **US-003** As portfolio manager, I track valuation, licensing, and commercial importance.
- **US-004** As risk analyst, I maintain litigation/infringement/renewal assessment scores.
- **US-005** As operations user, I filter list by mark/legal/owner/licensing status.

## Acceptance Scenarios
| ID | Scenario | Expected |
|----|----------|----------|
| TS-001 | Create with mandatory fields | Redirect to `/trademarks` |
| TS-002 | Missing mandatory fields | Form with field errors |
| TS-003 | Priority claimed without number/date | Global business error |
| TS-004 | Invalid date ordering | Global business error |
| TS-005 | Duplicate identifier | Global business error |
| TS-006 | Valid update | Redirect to list |
| TS-007 | Detail unknown ID | 404 |

