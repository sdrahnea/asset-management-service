# Cash — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

## 1. Overview
The Cash module records cash position data covering amount, currency, location, ownership, movement, reconciliation status, risk indicators, and operational metadata.

## 2. Functional Requirements

### Core CRUD
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create, list, view, edit, delete cash records | ✅ Done |
| FR-002 | List page at `/cash` with filters | ✅ Done |
| FR-003 | Form and detail pages | ✅ Done |

### Core Cash Attributes
| ID | Requirement | Status |
|----|-------------|--------|
| FR-010 | Cash ID (`cashId`) — maps to `name` via `@AttributeOverride`, required, unique | ✅ Done |
| FR-011 | Amount (required, ≥ 0) | ✅ Done |
| FR-012 | Currency (required, ISO 3-letter) | ✅ Done |
| FR-013 | Valuation date (required) | ✅ Done |
| FR-014 | Cash type: OPERATING, RESTRICTED, PETTY, INVESTMENT, ESCROW, OTHER | ✅ Done |

### Location & Ownership
| ID | Requirement | Status |
|----|-------------|--------|
| FR-020 | Holder/owner, holding account reference, institution, jurisdiction | ✅ Done |

### Lifecycle & Movement
| ID | Requirement | Status |
|----|-------------|--------|
| FR-030 | Opening and closing balance | ✅ Done |
| FR-031 | Inflows, outflows, net cash movement, forecast | ✅ Done |
| FR-032 | Reconciliation status: RECONCILED, PENDING, DISPUTED | ✅ Done |

### Risk & Assessment
| ID | Requirement | Status |
|----|-------------|--------|
| FR-040 | Counterparty risk level (LOW, MEDIUM, HIGH) | ✅ Done |
| FR-041 | Concentration risk, volatility, compliance flags | ✅ Done |

### Metadata
| ID | Requirement | Status |
|----|-------------|--------|
| FR-050 | Data source (required), responsible analyst, tags, notes | ✅ Done |

## 3. Non-Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | cashId normalized: uppercase, no whitespace | ✅ Done |
| NFR-002 | cashId globally unique | ✅ Done |
| NFR-003 | Amount ≥ 0 | ✅ Done |

## 4. Out of Scope (v1)
- Real-time bank feed integration
- Cash pooling structure
- Intercompany cash positions
- Transaction-level AML flags

