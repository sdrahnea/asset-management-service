# Bond — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

---

## 1. Overview

The Bond module enables users to record, manage, and assess bond instrument data covering identification, financial characteristics, lifecycle, legal structure, market pricing, and risk indicators.

---

## 2. Functional Requirements

### 2.1 Core CRUD
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create, list, view, edit, delete bond records | ✅ Done |
| FR-002 | Detail page at `/bonds/{id}` showing all fields | ✅ Done |
| FR-003 | List page at `/bonds` with filter support (issuer, bondType, tradingStatus, currency) | ✅ Done |

### 2.2 Identification
| ID | Requirement | Status |
|----|-------------|--------|
| FR-010 | Bond ID (`bondId`) — internal unique identifier, maps to `name` via `@AttributeOverride` | ✅ Done |
| FR-011 | Title (human-readable bond name) | ✅ Done |
| FR-012 | ISIN (global securities identifier, globally unique) | ✅ Done |
| FR-013 | CUSIP/SEDOL (regional identifier, globally unique when provided) | ✅ Done |
| FR-014 | Issuer (required) | ✅ Done |
| FR-015 | Issuer country | ✅ Done |
| FR-016 | Bond type (GOVERNMENT, CORPORATE, MUNICIPAL, COVERED, GREEN, SUPRANATIONAL, OTHER) | ✅ Done |

### 2.3 Financial Characteristics
| ID | Requirement | Status |
|----|-------------|--------|
| FR-020 | Face value / par value | ✅ Done |
| FR-021 | Coupon type (FIXED, FLOATING, ZERO_COUPON) | ✅ Done |
| FR-022 | Coupon rate | ✅ Done |
| FR-023 | Coupon frequency (ANNUAL, SEMIANNUAL, QUARTERLY, MONTHLY) | ✅ Done |
| FR-024 | Day count convention (THIRTY_360, ACT_365, ACT_ACT) | ✅ Done |
| FR-025 | Current yield, yield to maturity, yield to call | ✅ Done |
| FR-026 | Currency (required, ISO 3-letter) | ✅ Done |

### 2.4 Lifecycle
| ID | Requirement | Status |
|----|-------------|--------|
| FR-030 | Issue date | ✅ Done |
| FR-031 | Maturity date — must be ≥ issue date | ✅ Done |
| FR-032 | Call date — must be between issue and maturity when provided | ✅ Done |
| FR-033 | Put date — must be between issue and maturity when provided | ✅ Done |
| FR-034 | Amortization schedule | ✅ Done |
| FR-035 | Redemption type (BULLET, AMORTIZING, SINKING_FUND) | ✅ Done |

### 2.5 Legal & Structural
| ID | Requirement | Status |
|----|-------------|--------|
| FR-040 | Seniority (SENIOR_SECURED, SENIOR_UNSECURED, SUBORDINATED, JUNIOR_SUBORDINATED, OTHER) | ✅ Done |
| FR-041 | Collateral, covenants, prospectus reference, rating | ✅ Done |

### 2.6 Market & Trading
| ID | Requirement | Status |
|----|-------------|--------|
| FR-050 | Clean price, dirty price, accrued interest | ✅ Done |
| FR-051 | Trading status (ACTIVE, SUSPENDED, DELISTED, MATURED) | ✅ Done |
| FR-052 | Trading venue, bid-ask spread, trading volume, turnover | ✅ Done |

### 2.7 Assessment & Risk
| ID | Requirement | Status |
|----|-------------|--------|
| FR-060 | Risk score (0–100) | ✅ Done |
| FR-061 | Macaulay duration, modified duration, convexity | ✅ Done |
| FR-062 | Probability of default, loss given default | ✅ Done |
| FR-063 | Value at Risk, stress test results | ✅ Done |
| FR-064 | ESG score (0–100) | ✅ Done |

### 2.8 Metadata
| ID | Requirement | Status |
|----|-------------|--------|
| FR-070 | Portfolio reference, assessment history, documents | ✅ Done |
| FR-071 | Tags, analyst notes | ✅ Done |
| FR-072 | Data source (required), responsible agent, notes | ✅ Done |

---

## 3. Non-Functional Requirements

| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | Bond ID, ISIN, CUSIP/SEDOL normalized to uppercase, whitespace removed | ✅ Done |
| NFR-002 | Currency normalized to uppercase, whitespace removed | ✅ Done |
| NFR-003 | Uniqueness enforced for bondId, ISIN, CUSIP/SEDOL | ✅ Done |
| NFR-004 | Service throws `IllegalArgumentException` for all business violations | ✅ Done |
| NFR-005 | Controller surfaces service errors via `BindingResult` global error | ✅ Done |

---

## 4. Out of Scope (v1)

- Real-time market data integration
- Coupon payment schedule generation
- Duration/convexity auto-calculation from coupon inputs
- Bond search by maturity range or rating

