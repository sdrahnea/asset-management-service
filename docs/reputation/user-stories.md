# Reputation — User Stories

**Version**: 1.0 | **Date**: 2026-04-04

## User Stories
- **US-001** As an analyst, I create reputation records linked to a subject entity.
- **US-002** As a risk manager, I track reputation events and impact over time.
- **US-003** As a strategist, I maintain trend and competitive indicators.
- **US-004** As compliance staff, I track legal/ethical/compliance concerns.
- **US-005** As operations user, I filter and review reputation portfolio lists.

## Acceptance Scenarios
| ID | Scenario | Expected |
|----|----------|----------|
| TS-001 | Create with mandatory fields | Redirect to `/reputations` |
| TS-002 | Missing mandatory field | Re-render form with errors |
| TS-003 | Duplicate reputationId | Global business error |
| TS-004 | Duplicate entity reference pair | Global business error |
| TS-005 | Event details without eventType | Global business error |
| TS-006 | eventDate in future | Global business error |
| TS-007 | Valid update | Redirect to list |

