# Patent — Application Surface / Route Map

**Version**: 1.0 | **Date**: 2026-04-04

## Routes
| Method | Path | Description |
|--------|------|-------------|
| GET | `/patents` | List patents with optional filters |
| GET | `/patents/new` | Create form |
| POST | `/patents` | Create submit |
| GET | `/patents/{id}` | Detail view |
| GET | `/patents/{id}/edit` | Edit form |
| POST | `/patents/{id}` | Update submit |
| POST | `/patents/{id}/delete` | Delete submit |

## List Filters
- `patentType`
- `legalStatus`
- `technologyField`
- `assigneeOwner`

## Create Defaults
- `dataSource = manual-entry`
- `patentType = UTILITY`
- `legalStatus = PENDING`
- `regionalSystem = NONE`
- `applicationDate = today`

## Form Contract
### Required
- `patentId`, `title`, `patentType`, `assigneeOwner`, `applicationNumber`, `applicationDate`, `legalStatus`, `dataSource`

### Conditional
- if `legalStatus == GRANTED`: require `grantNumber`, `grantDate`

### Error Handling
- Bean validation errors -> field errors on form
- Service/runtime business errors -> global error (`patent.error`) on form

## Templates
- `patents/list`
- `patents/form`
- `patents/detail`

