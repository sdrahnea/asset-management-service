# Real Estate — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

## Mandatory
| Field | Rule | Error |
|-------|------|-------|
| `title` | Required | `"Title is required"` |
| `propertyType` | Required enum | `"Property type is required"` |
| `address` | Required, max 255 | `"Address is required"` |
| `landArea` | Required, >= 0 | `"Land area is required"` |
| `ownershipType` | Required enum | `"Ownership type is required"` |
| `ownerName` | Required | `"Owner name is required"` |
| `cadastralNumber` | Required, alphanumeric | `"Cadastral number is required"` |
| `landRegistryNumber` | Required, alphanumeric | `"Land registry number is required"` |
| `currentMarketValue` | Required, >= 0 | `"Current market value is required"` |
| `valuationDate` | Required | `"Valuation date is required"` |
| `dataSource` | Required | `"Data source is required"` |

## Uniqueness
| Rule | Error |
|------|-------|
| Cadastral number unique | `"Cadastral number must be unique"` |
| Land registry number unique | `"Land registry number must be unique"` |

## Business Rules
| Rule | Error |
|------|-------|
| latitude and longitude both present or both absent | `"Latitude and longitude must both be provided together"` |
| `yearBuilt <= current year` | `"Year built must be less than or equal to the current year"` |
| `valuationDate <= today` | `"Valuation date must be today or earlier"` |
| `lastRenovationDate <= today` | `"Last renovation date must be today or earlier"` |
| `builtArea >= usableArea` when both set | `"Built area must be greater than or equal to usable area"` |

