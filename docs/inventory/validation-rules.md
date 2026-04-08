# Inventory — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

## Field Validation
| Field | Rule | Error |
|-------|------|-------|
| `inventoryId` | Required, max 100, alphanumeric | `"Inventory ID is required"` |
| `category` | Required enum | `"Category is required"` |
| `manufacturer` | Required, max 100 | `"Manufacturer is required"` |
| `model` | Required, max 100 | `"Model is required"` |
| `serialNumber` | Required, max 100 | `"Serial number is required"` |
| `yearOfManufacture` | Required, ≤ current year | `"Year of manufacture must be <= current year"` |
| `dataSource` | Required, max 100 | `"Data source is required"` |
| `weight` | If present, ≥ 0 | Validation error |
| `operatingHours` | If present, ≥ 0 | Validation error |
| `downtimeHours` | If present, ≥ 0 | Validation error |
| Risk scores | If present, 0–100 | Validation error |
| `utilizationRate` | If present, 0–100 | Validation error |
| Decimal costs | If present, ≥ 0 | Validation error |

## Uniqueness
| Rule | Error |
|------|-------|
| `inventoryId` globally unique | `"Inventory ID must be unique"` |
| `serialNumber` globally unique | `"Serial number must be unique"` |

