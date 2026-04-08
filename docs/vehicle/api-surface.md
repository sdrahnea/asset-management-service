# Vehicle — Application Surface / Route Map

**Version**: 1.0 | **Date**: 2026-04-04

## Routes
| Method | Path | Description |
|--------|------|-------------|
| GET | `/vehicles` | List with optional filters |
| GET | `/vehicles/new` | Create form |
| POST | `/vehicles` | Create submit |
| GET | `/vehicles/{id}` | Detail view |
| GET | `/vehicles/{id}/edit` | Edit form |
| POST | `/vehicles/{id}` | Update submit |
| POST | `/vehicles/{id}/delete` | Delete submit |

## List Filters
- `vehicleType`
- `registrationStatus`
- `ownershipType`

## Create Defaults
- `yearOfManufacture = current year`
- `dataSource = manual-entry`
- `vehicleType = CAR`
- `engineType = PETROL`
- `registrationStatus = ACTIVE`
- `ownershipType = COMPANY`

## Form Contract
### Required
- `vehicleId`, `vin`, `licensePlate`, `vehicleType`, `make`, `model`, `yearOfManufacture`, `engineType`, `dataSource`

### Error Handling
- Bean validation failures -> field-level errors
- Service/runtime business failures -> global error key `vehicle.error`

## Templates
- `vehicles/list`
- `vehicles/form`
- `vehicles/detail`

