# Vehicle — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

## Mandatory
| Field | Rule | Error |
|-------|------|-------|
| `vehicleId` | Required, max 100, alphanumeric | `"Vehicle ID is required"` |
| `vin` | Required, length 11..17, VIN regex | `"VIN is required"` |
| `licensePlate` | Required, max 20 | `"License plate is required"` |
| `vehicleType` | Required enum | `"Vehicle type is required"` |
| `make` | Required, max 100 | `"Make is required"` |
| `model` | Required, max 100 | `"Model is required"` |
| `yearOfManufacture` | Required integer | `"Year of manufacture is required"` |
| `engineType` | Required enum | `"Engine type is required"` |
| `dataSource` | Required, max 100 | `"Data source is required"` |

## Uniqueness
| Rule | Error |
|------|-------|
| `vehicleId` unique | `"Vehicle ID must be unique"` |
| `vin` unique | `"VIN must be unique"` |
| `licensePlate + registrationCountry` unique | `"License plate must be unique per registration country"` |

## Business Rules
| Rule | Error |
|------|-------|
| `yearOfManufacture <= current year` | `"Year of manufacture must be less than or equal to the current year"` |
| `registrationDate <= today` | `"Registration date must be today or earlier"` |
| `purchaseDate <= today` | `"Purchase date must be today or earlier"` |
| `valuationDate <= today` | `"Valuation date must be today or earlier"` |
| `nextServiceDue >= lastServiceDate` | `"Next service due must be greater than or equal to last service date"` |

## Numeric Constraints
- Multiple cost/value fields: >= 0
- Depreciation rate: 0..100
- Score fields constrained by precision/scale annotations

