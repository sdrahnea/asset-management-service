# Stock — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

## Mandatory
| Field | Rule | Error |
|-------|------|-------|
| `stockId` | Required, max 100 | `"Stock ID is required"` |
| `tickerSymbol` | Required, max 20 | `"Ticker symbol is required"` |
| `exchange` | Required, max 30 | `"Exchange is required"` |
| `companyName` | Required, max 255 | `"Company name is required"` |
| `dataSource` | Required, max 100 | `"Data source is required"` |

## Uniqueness
| Rule | Error |
|------|-------|
| `stockId` unique | `"Stock ID must be unique"` |
| `(tickerSymbol, exchange)` unique | `"Ticker symbol must be unique per exchange"` |
| `isin` unique (if present) | `"ISIN must be unique"` |
| `cusip` unique (if present) | `"CUSIP/SEDOL must be unique"` |

## Business Rules
| Rule | Error |
|------|-------|
| `dayHigh >= dayLow` | `"Day high must be greater than or equal to day low"` |
| `52WeekHigh >= 52WeekLow` | `"52-week high must be greater than or equal to 52-week low"` |
| `currentPrice` inside day range | `"Current price must be between day low and day high"` |

## Numeric Constraints
- Multiple price/fundamental fields: non-negative where specified
- Risk/governance/assessment scores: 0..100
- Dividend yield/payout ratio: 0..1000
- Precision/scale per JPA column annotations

