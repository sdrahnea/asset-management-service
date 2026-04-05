# Brand — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

---

## 1. Overview
The Brand module stores brand asset records covering identity, legal/trademark status, market positioning, performance indicators, digital footprint, financial metrics, and operational metadata.

---

## 2. Functional Requirements

### 2.1 Core CRUD
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create, list, view, edit, delete brand records | ✅ Done |
| FR-002 | List page at `/brands` with filter support | ✅ Done |
| FR-003 | Detail page at `/brands/{id}` | ✅ Done |

### 2.2 Identity Fields
| ID | Requirement | Status |
|----|-------------|--------|
| FR-010 | Brand ID (`brandId`) — maps to `name` via `@AttributeOverride`, required, unique | ✅ Done |
| FR-011 | Brand name (required) | ✅ Done |
| FR-012 | Tagline, description, brand category | ✅ Done |
| FR-013 | Parent company / owner (required) | ✅ Done |
| FR-014 | Country of origin | ✅ Done |

### 2.3 Legal / Trademark
| ID | Requirement | Status |
|----|-------------|--------|
| FR-020 | Trademark registration number (globally unique when set) | ✅ Done |
| FR-021 | Trademark type (WORDMARK, FIGURATIVE, COMBINED) | ✅ Done |
| FR-022 | Jurisdictions covered | ✅ Done |
| FR-023 | Registration date, expiration date | ✅ Done |
| FR-024 | Brand status (ACTIVE, EXPIRED, PENDING, DISPUTED) | ✅ Done |
| FR-025 | Business rule: expiration date must be ≥ registration date | ✅ Done |

### 2.4 Market Positioning
| ID | Requirement | Status |
|----|-------------|--------|
| FR-030 | Industry/sector, target audience | ✅ Done |
| FR-031 | Brand positioning statement, value proposition | ✅ Done |
| FR-032 | Competitors, geographic markets | ✅ Done |

### 2.5 Performance Indicators
| ID | Requirement | Status |
|----|-------------|--------|
| FR-040 | Brand equity score (0–100) | ✅ Done |
| FR-041 | Sentiment level (POSITIVE, NEUTRAL, NEGATIVE) | ✅ Done |
| FR-042 | Market share, customer loyalty, NPS | ✅ Done |

### 2.6 Digital Footprint
| ID | Requirement | Status |
|----|-------------|--------|
| FR-050 | Website, social media accounts | ✅ Done |
| FR-051 | Follower count, engagement metrics | ✅ Done |
| FR-052 | SEO visibility | ✅ Done |

### 2.7 Financial Metrics (Optional)
| ID | Requirement | Status |
|----|-------------|--------|
| FR-060 | Revenue attributed to brand, licensing income | ✅ Done |
| FR-061 | Brand valuation, maintenance cost | ✅ Done |

### 2.8 Metadata
| ID | Requirement | Status |
|----|-------------|--------|
| FR-070 | Data source (required), responsible agent, tags, notes | ✅ Done |

---

## 3. Non-Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | brandId normalized: uppercase, no whitespace | ✅ Done |
| NFR-002 | Uniqueness enforced: brandId, trademarkRegistrationNumber | ✅ Done |
| NFR-003 | ExpirationDate ≥ registrationDate when both set | ✅ Done |

---

## 4. Out of Scope (v1)
- Logo and visual asset file storage
- Automated sentiment feed integration
- Time-series performance metrics

