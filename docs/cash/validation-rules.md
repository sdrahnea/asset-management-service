# Cash — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

## Field Validation
| Field | Rule | Error |
|-------|------|-------|
| `cashId` | Required, max 100 | `"Cash ID is required"` |
| `amount` | Required, ≥ 0 | Amount required, ≥ 0 |
| `currency` | Required, 3-letter ISO | `"Currency is required"` |
| `valuationDate` | Required | `"Valuation date is required"` |
| `cashType` | Required enum | `"Cash type is required"` |
| `dataSource` | Required, max 100 | `"Data source is required"` |

## Uniqueness
| Rule | Error |
|------|-------|
| `cashId` globally unique | `"Cash ID must be unique"` |

