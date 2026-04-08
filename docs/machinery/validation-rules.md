# Machinery — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

## Field Validation
| Field | Rule | Error |
|-------|------|-------|
| `machineId` | Required, max 100, alphanumeric | `"Machine ID is required"` |
| `category` | Required enum | `"Category is required"` |
| `manufacturer` | Required, max 100 | `"Manufacturer is required"` |
| `model` | Required, max 100 | `"Model is required"` |
| `serialNumber` | Required, max 100 | `"Serial number is required"` |
| `yearOfManufacture` | Required, <= current year | `"Year of manufacture is required"` |
| `dataSource` | Required, max 100 | `"Data source is required"` |
| `operatingHours` | If present, >= 0 | Validation error |
| `downtimeHours` | If present, >= 0 | Validation error |
| `utilizationRate` | If present, 0..100 | Validation error |
| `failureRiskScore` | If present, numeric constrained by precision/scale | Validation error |
| `safetyRiskScore` | If present, numeric constrained by precision/scale | Validation error |
| Financial values | If present, >= 0 | Validation error |

## Uniqueness
| Rule | Error |
|------|-------|
| `machineId` globally unique | `"Machine ID must be unique"` |
| `serialNumber` globally unique | `"Serial number must be unique"` |

