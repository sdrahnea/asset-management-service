# Bond — API Surface

**Version**: 1.0 | **Date**: 2026-04-04

---

## Routes

| Method | Path | Handler | Description |
|--------|------|---------|-------------|
| GET | `/bonds` | `list()` | List with optional filters |
| GET | `/bonds/new` | `createForm()` | Empty create form |
| POST | `/bonds` | `create()` | Submit new bond |
| GET | `/bonds/{id}` | `detail()` | Detail view |
| GET | `/bonds/{id}/edit` | `editForm()` | Pre-filled edit form |
| POST | `/bonds/{id}` | `update()` | Submit update |
| POST | `/bonds/{id}/delete` | `delete()` | Delete bond |

---

## Query Parameters (GET /bonds)

| Param | Type | Description |
|-------|------|-------------|
| `issuer` | String | Partial match on issuer name |
| `bondType` | BondType enum | Exact match |
| `tradingStatus` | TradingStatus enum | Exact match |
| `currency` | String | Exact match (case-insensitive) |

---

## Key Form Fields (POST /bonds)

| Field | Required | Type |
|-------|----------|------|
| `bondId` | ✅ | String |
| `title` | ✅ | String |
| `issuer` | ✅ | String |
| `currency` | ✅ | String (3-letter) |
| `bondType` | ✅ | Enum name |
| `dataSource` | ✅ | String |
| `isin` | | String |
| `cusipSedol` | | String |
| `issuerCountry` | | String |
| `faceValue` | | Decimal |
| `couponType`, `couponRate`, `couponFrequency` | | Enum/Decimal |
| `issueDate`, `maturityDate`, `callDate`, `putDate` | | Date (YYYY-MM-DD) |
| `riskScore`, `esgScore`, `probabilityOfDefault`, `lossGivenDefault` | | Decimal (0–100) |

**Success**: `302 redirect /bonds`  
**Failure**: `200` re-rendered form with errors

---

## Planned REST API (Future)

| Method | Path | Purpose |
|--------|------|---------|
| GET | `/api/bonds` | JSON list |
| GET | `/api/bonds/{id}` | JSON detail |
| POST | `/api/bonds` | JSON create |
| PUT | `/api/bonds/{id}` | JSON update |
| DELETE | `/api/bonds/{id}` | JSON delete |

