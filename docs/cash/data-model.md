# Cash — Data Model, Validation, Tasks & API Surface

**Version**: 1.0 | **Date**: 2026-04-04 | **Table**: `cash`

`Cash` extends `CoreEntity`. `name` overridden to `cash_id` via `@AttributeOverride`.

---

## Enums
| Enum | Values |
|------|--------|
| `CashType` | OPERATING, RESTRICTED, PETTY, INVESTMENT, ESCROW, OTHER |
| `ReconciliationStatus` | RECONCILED, PENDING, DISPUTED |
| `RiskLevel` | LOW, MEDIUM, HIGH |

---

## Key Fields

### Core
| Field | Column | Type | Required |
|-------|--------|------|----------|
| `cashId` | `cash_id` | VARCHAR(100) | ✅ (unique) |
| `amount` | `amount` | DECIMAL(22,2) | ✅ (≥0) |
| `currency` | `currency` | VARCHAR(3) | ✅ (ISO code) |
| `valuationDate` | `valuation_date` | DATE | ✅ |
| `cashType` | `cash_type` | ENUM(20) | ✅ |

### Location & Ownership
| Field | Column | Type |
|-------|--------|------|
| `holder` | `holder` | VARCHAR(255) |
| `holdingAccountReference` | `holding_account_reference` | VARCHAR(255) |
| `institution` | `institution` | VARCHAR(255) |
| `jurisdiction` | `jurisdiction` | VARCHAR(100) |

### Movement
| Field | Column | Type |
|-------|--------|------|
| `openingBalance` | `opening_balance` | DECIMAL(22,2) |
| `closingBalance` | `closing_balance` | DECIMAL(22,2) |
| `cashInflows` | `cash_inflows` | DECIMAL(22,2) |
| `cashOutflows` | `cash_outflows` | DECIMAL(22,2) |
| `netCashMovement` | `net_cash_movement` | DECIMAL(22,2) |
| `cashForecast` | `cash_forecast` | VARCHAR(1000) |
| `reconciliationStatus` | `reconciliation_status` | ENUM(20) |

### Risk
| Field | Column | Type |
|-------|--------|------|
| `counterpartyRisk` | `counterparty_risk` | ENUM(10) |
| `concentrationRisk` | `concentration_risk` | VARCHAR(500) |
| `volatility` | `volatility` | VARCHAR(500) |
| `complianceFlags` | `compliance_flags` | VARCHAR(1000) |

### Metadata
| Field | Required |
|-------|----------|
| `dataSource` | ✅ |
| `responsibleAnalyst`, `tags`, `notes` | |

---

## Validation Rules

| Field | Rule | Error |
|-------|------|-------|
| `cashId` | Required, max 100 chars | `"Cash ID is required"` |
| `amount` | Required, ≥ 0 | `"Amount is required and must be >= 0"` |
| `currency` | Required, 3-letter ISO | `"Currency is required"` |
| `valuationDate` | Required | `"Valuation date is required"` |
| `cashType` | Required enum | `"Cash type is required"` |
| `dataSource` | Required | `"Data source is required"` |
| `cashId` | Globally unique | `"Cash ID must be unique"` |

---

## Tasks

### Phase 1 ✅ Done
- [x] Cash entity, enums, constraints, @AttributeOverride for cashId
- [x] CashRepository with uniqueness query
- [x] CashService with normalize/validate
- [x] CashController full CRUD
- [x] `cash/list.html`, `cash/form.html`

### Phase 2 (Planned)
- [ ] Detail page `cash/detail.html`
- [ ] Service unit tests
- [ ] Filter by cashType, reconciliationStatus, currency

---

## API Surface

| Method | Path | Description |
|--------|------|-------------|
| GET | `/cash` | List |
| GET | `/cash/new` | Create form |
| POST | `/cash` | Submit create |
| GET | `/cash/{id}` | Detail (planned) |
| GET | `/cash/{id}/edit` | Edit form |
| POST | `/cash/{id}` | Submit update |
| POST | `/cash/{id}/delete` | Delete |

