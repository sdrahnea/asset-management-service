# Reputation — Application Surface / Route Map

**Version**: 1.0 | **Date**: 2026-04-04

## Routes
| Method | Path | Description |
|--------|------|-------------|
| GET | `/reputations` | List with optional filters |
| GET | `/reputations/new` | Create form |
| POST | `/reputations` | Create submit |
| GET | `/reputations/{id}` | Detail view |
| GET | `/reputations/{id}/edit` | Edit form |
| POST | `/reputations/{id}` | Update submit |
| POST | `/reputations/{id}/delete` | Delete submit |

## List Filters
- `entityId`
- `entityType`
- `trendDirection`
- `competitivePosition`

## Create Defaults
- `dataSource = manual-entry`
- `entityType = COMPANY`
- `trendDirection = STABLE`

## Form Contract
### Required
- `reputationId`
- `entityType`
- `entityId`
- `displayName`
- `dataSource`

### Conditional
- If any event detail (`eventDate`, `eventDescription`, `eventImpactScore`, `eventSource`) is provided, `eventType` is required

## Error Handling
- Bean validation failures -> field-level errors
- Service/runtime business failures -> global error key `reputation.error`

## Templates
- `reputations/list`
- `reputations/form`
- `reputations/detail`

