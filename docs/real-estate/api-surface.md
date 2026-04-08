# Real Estate — Application Surface / Route Map

**Version**: 1.0 | **Date**: 2026-04-04

## Routes
| Method | Path | Description |
|--------|------|-------------|
| GET | `/real-estates` | List with optional filters |
| GET | `/real-estates/new` | Create form |
| POST | `/real-estates` | Create submit |
| GET | `/real-estates/{id}` | Detail view |
| GET | `/real-estates/{id}/edit` | Edit form |
| POST | `/real-estates/{id}` | Update submit |
| POST | `/real-estates/{id}/delete` | Delete submit |

## Supported List Filters
- `propertyType`
- `ownershipType`
- `neighborhoodType`
- `valuationDate`
- `maintenanceStatus`

## Create Defaults
- `valuationDate = today`
- `dataSource = manual-entry`
- `maintenanceStatus = UNKNOWN`

## Form Error Handling
- Bean validation failures -> field errors
- Service/business failures -> global error key `realEstate.error`

## Template Contracts
- List: `realEstates`, selected filter fields, enum arrays
- Form: `realEstate`, `isEdit`, enum arrays, `utilityOptions`, `amenityOptions`
- Detail: `realEstate`

## Known Caveat
- Delete endpoint currently redirects to `/bank-accounts` (existing code behavior)

