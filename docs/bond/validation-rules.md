# Bond — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

---

## 1. Field-Level Validation

| Field | Rule | Layer | Error Message |
|-------|------|-------|---------------|
| `bondId` | Required, max 100 chars | Bean + Service | `"Bond ID is required"` |
| `title` | Required, max 255 chars | Bean + Service | `"Bond title is required"` |
| `issuer` | Required, max 255 chars | Bean + Service | `"Issuer is required"` |
| `currency` | Required, max 3 chars (ISO) | Bean + Service | `"Currency is required"` |
| `bondType` | Required, valid enum | Bean + Service | `"Bond type is required"` |
| `dataSource` | Required, max 100 chars | Bean + Service | `"Data source is required"` |
| `isin` | Optional, max 12 chars | Bean | — |
| `cusipSedol` | Optional, max 20 chars | Bean | — |
| `couponRate` | ≥ 0, ≤ 1000 | Bean | `"Coupon rate must be at most 1000"` |
| `riskScore`, `esgScore`, `probabilityOfDefault`, `lossGivenDefault` | 0–100 | Bean | Respective messages |
| `durationMacaulay`, `durationModified` | ≥ 0 | Bean | — |
| `cleanPrice`, `dirtyPrice`, `accruedInterest`, `faceValue` | ≥ 0 | Bean | — |
| `bidAskSpread`, `tradingVolume`, `turnover` | ≥ 0 | Bean | — |

---

## 2. Business Rules

| ID | Rule | Layer | Error |
|----|------|-------|-------|
| BR-001 | `maturityDate >= issueDate` when both set | Bean (`@AssertTrue`) + Service | `"Maturity date must be on or after issue date"` |
| BR-002 | `callDate >= issueDate` when both set | Bean + Service | `"Call date must be on or after issue date"` |
| BR-003 | `putDate >= issueDate` when both set | Bean + Service | `"Put date must be on or after issue date"` |
| BR-004 | `callDate <= maturityDate` when both set | Bean + Service | `"Call date must be on or before maturity date"` |
| BR-005 | `putDate <= maturityDate` when both set | Bean + Service | `"Put date must be on or before maturity date"` |

---

## 3. Uniqueness Rules

| ID | Rule | Query | Error |
|----|------|-------|-------|
| UQ-001 | `bondId` globally unique (case-insensitive) | `existsBondId(bondId, excludeId)` | `"Bond ID must be unique"` |
| UQ-002 | `isin` globally unique when non-null (case-insensitive) | `existsIsin(isin, excludeId)` | `"ISIN must be unique"` |
| UQ-003 | `cusipSedol` globally unique when non-null (case-insensitive) | `existsCusipSedol(cusipSedol, excludeId)` | `"CUSIP/SEDOL must be unique"` |

