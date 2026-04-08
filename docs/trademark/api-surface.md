# Trademark — Application Surface / Route Map

**Version**: 1.0 | **Date**: 2026-04-04

## Routes
| Method | Path | Description |
|--------|------|-------------|
| GET | `/trademarks` | List with optional filters |
| GET | `/trademarks/new` | Create form |
| POST | `/trademarks` | Create submit |
| GET | `/trademarks/{id}` | Detail view |
| GET | `/trademarks/{id}/edit` | Edit form |
| POST | `/trademarks/{id}` | Update submit |
| POST | `/trademarks/{id}/delete` | Delete submit |

## List Filters
- `markType`
- `legalStatus`
- `ownerType`
- `licensingStatus`

## Create Defaults
- `dataSource = manual-entry`
- `markType = WORDMARK`
- `ownerType = CORPORATION`
- `legalStatus = FILED`
- `licensingStatus = NOT_LICENSED`
- `applicationDate = today`

## Form Contract
### Required
- `trademarkId`, `markName`, `markType`, `ownerName`, `ownerType`, `applicationNumber`, `applicationDate`, `legalStatus`, `dataSource`

### Conditional
- if `priorityClaimed == true`, require `priorityNumber` and `priorityDate`

## Error Handling
- Bean validation failures -> field-level errors
- Service/runtime business failures -> global error key `trademark.error`

## Templates
- `trademarks/list`
- `trademarks/form`
- `trademarks/detail`

