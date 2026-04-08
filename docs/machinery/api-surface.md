# Machinery — Application Surface / Route Map

**Version**: 1.0 | **Date**: 2026-04-04

## Route Summary
| Method | Path | Description |
|--------|------|-------------|
| GET | `/machineries` | List machinery |
| GET | `/machineries/new` | Create form |
| POST | `/machineries` | Create submit |
| GET | `/machineries/{id}/edit` | Edit form |
| POST | `/machineries/{id}` | Update submit |
| POST | `/machineries/{id}/delete` | Delete submit |
| GET | `/machineries/{id}` | Detail (planned) |

## Form Contract (high-level)
### Required
- `machineId`
- `category`
- `manufacturer`
- `model`
- `serialNumber`
- `yearOfManufacture`
- `dataSource`

### Major optional groups
- Technical specs
- Location/ownership
- Maintenance/condition
- Financial/valuation
- Compliance/documentation
- Risk indicators
- Metadata

## Behavior
- Success create/update/delete: redirect to `/machineries`
- Validation/business error: re-render form with field/global errors
- Enum attributes must be provided to template for dropdowns

