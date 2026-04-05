# Brand — API Surface

**Version**: 1.0 | **Date**: 2026-04-04

## Routes
| Method | Path | Description |
|--------|------|-------------|
| GET | `/brands` | List with optional filters |
| GET | `/brands/new` | Create form |
| POST | `/brands` | Submit new brand |
| GET | `/brands/{id}` | Detail |
| GET | `/brands/{id}/edit` | Edit form |
| POST | `/brands/{id}` | Submit update |
| POST | `/brands/{id}/delete` | Delete |

## Key Form Fields (POST /brands)
| Field | Required | Notes |
|-------|----------|-------|
| `brandId` | ✅ | Internal ID, normalized to uppercase |
| `brandName` | ✅ | |
| `parentCompany` | ✅ | |
| `brandStatus` | ✅ | ACTIVE / EXPIRED / PENDING / DISPUTED |
| `dataSource` | ✅ | |
| `trademarkRegistrationNumber` | | Globally unique when set |
| `trademarkType` | | WORDMARK / FIGURATIVE / COMBINED |
| `trademarkRegistrationDate`, `trademarkExpirationDate` | | expiration ≥ registration |
| `brandEquityScore` | | 0–100 |
| `nps` | | -100–100 |

**Success**: `302 redirect /brands`  
**Failure**: `200` re-rendered form with errors

## Planned REST API (Future)
| Method | Path |
|--------|------|
| GET | `/api/brands` |
| GET | `/api/brands/{id}` |
| POST | `/api/brands` |
| PUT | `/api/brands/{id}` |
| DELETE | `/api/brands/{id}` |

