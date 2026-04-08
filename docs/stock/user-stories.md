# Stock — User Stories

**Version**: 1.0 | **Date**: 2026-04-04

## User Stories
- **US-001** As a financial analyst, I create stock records with unique listing identifiers.
- **US-002** As a portfolio user, I filter stocks by ticker, exchange, sector, and company type.
- **US-003** As a risk analyst, I track volatility/liquidity/health/governance indicators.
- **US-004** As a governance reviewer, I track corporate structure and regulatory flags.
- **US-005** As an assessor, I maintain evidence and internal notes for auditability.

## Acceptance Scenarios
| ID | Scenario | Expected |
|----|----------|----------|
| TS-001 | Create with mandatory fields | Redirect to `/stocks` |
| TS-002 | Missing mandatory fields | Form with field errors |
| TS-003 | Duplicate stockId or ticker/exchange | Global business error |
| TS-004 | Invalid day or 52-week ranges | Global business error |
| TS-005 | currentPrice outside day range | Global business error |
| TS-006 | Valid update | Redirect to list |
| TS-007 | Detail unknown ID | 404 |

