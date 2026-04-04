# Bond — Data Model Specification

**Version**: 1.0 | **Date**: 2026-04-04  
**Entity class**: `com.sdr.ams.model.financial.Bond` | **Table**: `bond`

---

## 1. Inheritance

`Bond` extends `CoreEntity` (id, name→bond_id via `@AttributeOverride`, createdAt, createdBy, updatedAt, updatedBy).

---

## 2. Enums

| Enum | Values |
|------|--------|
| `BondType` | GOVERNMENT, CORPORATE, MUNICIPAL, COVERED, GREEN, SUPRANATIONAL, OTHER |
| `CouponType` | FIXED, FLOATING, ZERO_COUPON |
| `CouponFrequency` | ANNUAL, SEMIANNUAL, QUARTERLY, MONTHLY |
| `DayCountConvention` | THIRTY_360, ACT_365, ACT_ACT |
| `RedemptionType` | BULLET, AMORTIZING, SINKING_FUND |
| `Seniority` | SENIOR_SECURED, SENIOR_UNSECURED, SUBORDINATED, JUNIOR_SUBORDINATED, OTHER |
| `TradingStatus` | ACTIVE, SUSPENDED, DELISTED, MATURED |

---

## 3. Fields

### 3.1 Identification

| Field | Column | Type | Required | Unique | Notes |
|-------|--------|------|----------|--------|-------|
| `bondId` | `bond_id` | VARCHAR(100) | ✅ | ✅ | Maps to `name` via @AttributeOverride |
| `title` | `title` | VARCHAR(255) | ✅ | | Human-readable bond name |
| `isin` | `isin` | VARCHAR(12) | | ✅ (when set) | ISO 6166 identifier |
| `cusipSedol` | `cusip_sedol` | VARCHAR(20) | | ✅ (when set) | US/UK market identifier |
| `issuer` | `issuer` | VARCHAR(255) | ✅ | | Government, corp, etc. |
| `issuerCountry` | `issuer_country` | VARCHAR(100) | | | Country of issuance |
| `bondType` | `bond_type` | ENUM(30) | ✅ | | Bond classification |

### 3.2 Financial

| Field | Column | Type | Required | Notes |
|-------|--------|------|----------|-------|
| `faceValue` | `face_value` | DECIMAL(20,2) | | Par value |
| `couponType` | `coupon_type` | ENUM(20) | | Fixed/floating/zero |
| `couponRate` | `coupon_rate` | DECIMAL(12,4) | | Annual rate; 0–1000 |
| `couponFrequency` | `coupon_frequency` | ENUM(20) | | Payment schedule |
| `couponCalculationMethod` | `coupon_calculation_method` | ENUM(20) | | Day count convention |
| `currentYield` | `current_yield` | DECIMAL(12,4) | | |
| `yieldToMaturity` | `yield_to_maturity` | DECIMAL(12,4) | | |
| `yieldToCall` | `yield_to_call` | DECIMAL(12,4) | | |
| `currency` | `currency` | VARCHAR(3) | ✅ | ISO 4217 3-letter code |

### 3.3 Lifecycle

| Field | Column | Type | Required | Constraint |
|-------|--------|------|----------|------------|
| `issueDate` | `issue_date` | DATE | | |
| `maturityDate` | `maturity_date` | DATE | | ≥ issueDate |
| `callDate` | `call_date` | DATE | | issueDate ≤ callDate ≤ maturityDate |
| `putDate` | `put_date` | DATE | | issueDate ≤ putDate ≤ maturityDate |
| `amortizationSchedule` | `amortization_schedule` | VARCHAR(1000) | | |
| `redemptionType` | `redemption_type` | ENUM(20) | | |

### 3.4 Legal & Structural

| Field | Column | Type | Notes |
|-------|--------|------|-------|
| `seniority` | `seniority` | ENUM(30) | |
| `collateral` | `collateral` | VARCHAR(1000) | |
| `covenants` | `covenants` | VARCHAR(2000) | |
| `prospectusReference` | `prospectus_reference` | VARCHAR(500) | |
| `rating` | `rating` | VARCHAR(50) | Moody's / S&P / Fitch |

### 3.5 Market & Trading

| Field | Column | Type | Notes |
|-------|--------|------|-------|
| `cleanPrice` | `clean_price` | DECIMAL(20,4) | |
| `dirtyPrice` | `dirty_price` | DECIMAL(20,4) | |
| `accruedInterest` | `accrued_interest` | DECIMAL(20,4) | |
| `tradingStatus` | `trading_status` | ENUM(20) | |
| `tradingVenue` | `trading_venue` | VARCHAR(100) | |
| `bidAskSpread` | `bid_ask_spread` | DECIMAL(16,4) | |
| `tradingVolume` | `trading_volume` | DECIMAL(20,2) | |
| `turnover` | `turnover` | DECIMAL(20,2) | |

### 3.6 Risk & Assessment

| Field | Column | Type | Range |
|-------|--------|------|-------|
| `riskScore` | `risk_score` | DECIMAL(7,2) | 0–100 |
| `durationMacaulay` | `duration_macaulay` | DECIMAL(12,4) | ≥0 |
| `durationModified` | `duration_modified` | DECIMAL(12,4) | ≥0 |
| `convexity` | `convexity` | DECIMAL(18,6) | |
| `probabilityOfDefault` | `probability_of_default` | DECIMAL(7,2) | 0–100 |
| `lossGivenDefault` | `loss_given_default` | DECIMAL(7,2) | 0–100 |
| `valueAtRisk` | `value_at_risk` | DECIMAL(20,4) | |
| `stressTestResults` | `stress_test_results` | VARCHAR(2000) | |
| `esgScore` | `esg_score` | DECIMAL(7,2) | 0–100 |

### 3.7 Metadata

| Field | Column | Type | Required |
|-------|--------|------|----------|
| `portfolioReference` | `portfolio_reference` | VARCHAR(255) | |
| `assessmentHistory` | `assessment_history` | VARCHAR(4000) | |
| `documents` | `documents` | VARCHAR(2000) | |
| `tags` | `tags` | VARCHAR(500) | |
| `analystNotes` | `analyst_notes` | VARCHAR(4000) | |
| `dataSource` | `data_source` | VARCHAR(100) | ✅ |
| `responsibleAgent` | `responsible_agent` | VARCHAR(100) | |
| `notes` | `notes` | VARCHAR(4000) | |

---

## 4. Unique Constraints

| Name | Column(s) |
|------|-----------|
| `uk_bond_bond_id` | `bond_id` |
| `uk_bond_isin` | `isin` |
| `uk_bond_cusip_sedol` | `cusip_sedol` |

---

## 5. Normalization Rules

| Field | Rule |
|-------|------|
| `bondId`, `isin`, `cusipSedol`, `currency` | Uppercase + strip whitespace |
| All text fields | Trim; empty string → null |

