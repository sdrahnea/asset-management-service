# Stock — Application Surface / Route Map

**Version**: 1.0 | **Date**: 2026-04-04

## Routes
| Method | Path | Description |
|--------|------|-------------|
| GET | `/stocks` | List with optional filters |
| GET | `/stocks/new` | Create form |
| POST | `/stocks` | Create submit |
| GET | `/stocks/{id}` | Detail view |
| GET | `/stocks/{id}/edit` | Edit form |
| POST | `/stocks/{id}` | Update submit |
| POST | `/stocks/{id}/delete` | Delete submit |

## List Filters
- `tickerSymbol`
- `exchange`
- `sector`
- `companyType`

## Create Defaults
- `dataSource = manual-entry`
- `companyType = PUBLIC`
- `competitivePosition = CHALLENGER`

## Form Contract
### Required
- `stockId`
- `tickerSymbol`
- `exchange`
- `companyName`
- `dataSource`

### Error Handling
- Bean validation failures -> field-level errors
- Service/runtime business failures -> global error key `stock.error`

## Templates
- `stocks/list`
- `stocks/form`
- `stocks/detail`

