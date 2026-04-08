# Patent — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

## Mandatory
| Field | Rule | Error |
|-------|------|-------|
| `patentId` | Required, max 100 | `"Patent ID is required"` |
| `title` | Required, max 255 | `"Title is required"` |
| `patentType` | Required enum | `"Patent type is required"` |
| `assigneeOwner` | Required, max 255 | `"Assignee/owner is required"` |
| `applicationNumber` | Required, max 100 | `"Application number is required"` |
| `applicationDate` | Required date | `"Application date is required"` |
| `legalStatus` | Required enum | `"Legal status is required"` |
| `dataSource` | Required, max 100 | `"Data source is required"` |

## Uniqueness
| Rule | Error |
|------|-------|
| `patentId` unique | `"Patent ID must be unique"` |
| `applicationNumber` unique | `"Application number must be unique"` |
| `publicationNumber` unique (if present) | `"Publication number must be unique"` |
| `grantNumber` unique (if present) | `"Grant number must be unique"` |

## Date and Status Rules
| Rule | Error |
|------|-------|
| publicationDate >= applicationDate | `"Publication date must be on or after application date"` |
| grantDate >= applicationDate | `"Grant date must be on or after application date"` |
| expiryDate >= grantDate | `"Expiry date must be on or after grant date"` |
| if legalStatus=GRANTED then grantNumber required | `"Grant number is required when legal status is GRANTED"` |
| if legalStatus=GRANTED then grantDate required | `"Grant date is required when legal status is GRANTED"` |

## Numeric Rules
- Ownership percentage: 0..100
- Royalty rates: 0..100
- Technological/market/strength scores: 0..100
- Citations received/made: >= 0
- Fees/revenue/valuation/cost: >= 0

