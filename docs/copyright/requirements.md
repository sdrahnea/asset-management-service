# Copyright — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

## 1. Overview
The Copyright module records copyright/IP asset information covering work identification, ownership, legal metadata, rights, licensing, financial indicators, risk assessment, and operational metadata.

## 2. Functional Requirements

### Core CRUD
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create, list, view, edit, delete copyright records | ✅ Done |
| FR-002 | List page at `/copyrights` with filters | ✅ Done |
| FR-003 | Form and detail pages | ✅ Done |

### Work Identification
| ID | Requirement | Status |
|----|-------------|--------|
| FR-010 | Copyright ID — unique, required, alphanumeric | ✅ Done |
| FR-011 | Title — required, max 255 chars | ✅ Done |
| FR-012 | Type of work — required enum (8 types) | ✅ Done |
| FR-013 | Creation, publication, registration dates | ✅ Done |

### Ownership & Authorship
| ID | Requirement | Status |
|----|-------------|--------|
| FR-020 | Authors and copyright owners — required | ✅ Done |
| FR-021 | Ownership percentage (0–100) — optional | ✅ Done |
| FR-022 | Moral rights holder — optional | ✅ Done |
| FR-023 | Country of origin — required | ✅ Done |

### Legal Metadata
| ID | Requirement | Status |
|----|-------------|--------|
| FR-030 | Registration number — optional, unique if present | ✅ Done |
| FR-031 | Registration authority (OSIM, EUIPO, etc.) | ✅ Done |
| FR-032 | Copyright status — required enum (4 statuses) | ✅ Done |
| FR-033 | Protection dates (start, end) — optional | ✅ Done |
| FR-034 | Jurisdictions covered — optional | ✅ Done |

### Rights & Restrictions
| ID | Requirement | Status |
|----|-------------|--------|
| FR-040 | Rights granted and reserved — narrative fields | ✅ Done |
| FR-041 | Usage restrictions — narrative field | ✅ Done |
| FR-042 | Exclusivity type (exclusive/non-exclusive) | ✅ Done |
| FR-043 | Derivative works allowed — boolean | ✅ Done |
| FR-044 | DRM/technical protection measures | ✅ Done |

### Licensing & Agreements
| ID | Requirement | Status |
|----|-------------|--------|
| FR-050 | License type — 5 options (exclusive, open source, CC, etc.) | ✅ Done |
| FR-051 | Licensees and permitted uses | ✅ Done |
| FR-052 | License dates (start, end) | ✅ Done |
| FR-053 | Royalty terms and contract references | ✅ Done |

### Financial Data
| ID | Requirement | Status |
|----|-------------|--------|
| FR-060 | Royalty rate (0–100) — optional | ✅ Done |
| FR-061 | Revenue generated — optional, ≥0 | ✅ Done |
| FR-062 | Advance payments and licensing fees — optional | ✅ Done |
| FR-063 | Copyright valuation — optional, ≥0 | ✅ Done |

### Risk & Assessment
| ID | Requirement | Status |
|----|-------------|--------|
| FR-070 | Infringement, ownership disputes, expiration risk levels | ✅ Done |
| FR-071 | Compliance flags for legal/regulatory issues | ✅ Done |
| FR-072 | Market relevance and portfolio impact scores | ✅ Done |

### Metadata
| ID | Requirement | Status |
|----|-------------|--------|
| FR-080 | Source of information — required | ✅ Done |
| FR-081 | Responsible reviewer — optional | ✅ Done |
| FR-082 | Document references and tags/categories | ✅ Done |
| FR-083 | Notes/comments field | ✅ Done |

## 3. Non-Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | copyrightId normalized and validated (alphanumeric) | ✅ Done |
| NFR-002 | copyrightId and registrationNumber globally unique | ✅ Done |
| NFR-003 | Temporal validation: creation ≤ publication, registration ≤ today, etc. | ✅ Done |
| NFR-004 | Decimal constraints: ownership %, royalty rate, scores all 0–100 | ✅ Done |
| NFR-005 | Support 40+ fields with mixed data types | ✅ Done |

## 4. Out of Scope (v1)
- Real-time IP office API integration
- Automated copyright expiration monitoring
- Royalty calculation automation
- Infringement detection

