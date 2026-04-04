# Bank Account — Requirements

**Version**: 1.0  
**Date**: 2026-04-04  
**Status**: Implemented

---

## 1. Overview

The Bank Account module enables users of the Asset Management Service to record, manage,
and assess bank account information linked to individuals, companies, or projects.
It is scoped to **reference data management** only — it does not process transactions,
store card data, or act as a payment gateway.

---

## 2. Functional Requirements

### 2.1 Core CRUD Operations

| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Users can create a new bank account record | ✅ Done |
| FR-002 | Users can view a list of all bank accounts | ✅ Done |
| FR-003 | Users can view the full detail of a single bank account | ✅ Done |
| FR-004 | Users can edit an existing bank account record | ✅ Done |
| FR-005 | Users can delete a bank account record | ✅ Done |

### 2.2 Identification and Account Data

| ID | Requirement | Status |
|----|-------------|--------|
| FR-010 | Each record must store the account holder name (`name`) | ✅ Done |
| FR-011 | Each record must store the account type (CURRENT, SAVINGS, CORPORATE, ESCROW, DEPOSIT, OTHER) | ✅ Done |
| FR-012 | Each record must store the bank name | ✅ Done |
| FR-013 | Each record may store a branch name | ✅ Done |
| FR-014 | Each record must store an IBAN | ✅ Done |
| FR-015 | Each record may store a SWIFT/BIC code | ✅ Done |
| FR-016 | Each record may store a local account number | ✅ Done |
| FR-017 | Each record must store the account currency (ISO 4217, 3-letter) | ✅ Done |
| FR-018 | Each record must store the account status (ACTIVE, CLOSED, SUSPENDED) | ✅ Done |
| FR-019 | Each record must store the ownership type (INDIVIDUAL, JOINT, CORPORATE) | ✅ Done |

### 2.3 Operational Metadata

| ID | Requirement | Status |
|----|-------------|--------|
| FR-020 | Users may record the date the account was opened (`openedAt`) | ✅ Done |
| FR-021 | Users may record the date the account was closed (`closedAt`) | ✅ Done |
| FR-022 | `closedAt` is only relevant and stored when status is `CLOSED` | ✅ Done |
| FR-023 | Users may record an account purpose (e.g., salary, escrow, business ops) | ✅ Done |
| FR-024 | Users may link the account to an internal entity (company, project, contract) | ✅ Done |

### 2.4 Assessment Metadata

| ID | Requirement | Status |
|----|-------------|--------|
| FR-030 | Users may record an internal assessment/risk score | ✅ Done |
| FR-031 | Users may record a verification status (free-text) | ✅ Done |
| FR-032 | Users may record the last review date | ✅ Done |

### 2.5 Validation Requirements

| ID | Requirement | Status |
|----|-------------|--------|
| FR-040 | IBAN must pass format validation (regex: country code + check digits + BBAN) | ✅ Done |
| FR-041 | IBAN must pass ISO 7064 MOD-97-10 checksum validation | ✅ Done |
| FR-042 | SWIFT/BIC must pass format validation when provided | ✅ Done |
| FR-043 | Currency must be a 3-letter ISO code | ✅ Done |
| FR-044 | `closedAt` must be on or after `openedAt` when both are present | ✅ Done |

### 2.6 Uniqueness Requirements

| ID | Requirement | Status |
|----|-------------|--------|
| FR-050 | IBAN must be globally unique across all bank account records | ✅ Done |
| FR-051 | Local account number must be unique per bank + branch combination | ✅ Done |

### 2.7 Display Requirements

| ID | Requirement | Status |
|----|-------------|--------|
| FR-060 | IBAN must be masked on the list page (show only last 4 characters) | ✅ Done |
| FR-061 | Full IBAN is shown on the detail page only | ✅ Done |
| FR-062 | `closedAt` input must be hidden on the form when status ≠ CLOSED | ✅ Done |
| FR-063 | Audit fields (createdAt, createdBy, updatedAt, updatedBy) are read-only on the form | ✅ Done |

### 2.8 Audit Fields

| ID | Requirement | Status |
|----|-------------|--------|
| FR-070 | `createdAt` and `createdBy` are auto-populated on first save | ✅ Done |
| FR-071 | `updatedAt` and `updatedBy` are auto-populated on every save | ✅ Done |

---

## 3. Non-Functional Requirements

| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | No card numbers, CVV/CVC, PINs, or online credentials must be stored | ✅ Done (fields excluded) |
| NFR-002 | No full transaction history must be stored | ✅ Done (not in model) |
| NFR-003 | IBAN is normalized to uppercase with whitespace removed before save | ✅ Done |
| NFR-004 | SWIFT/BIC is normalized to uppercase with whitespace removed before save | ✅ Done |
| NFR-005 | Currency code is normalized to uppercase before save | ✅ Done |
| NFR-006 | All user-facing string fields are trimmed before save | ✅ Done |
| NFR-007 | Service must throw `IllegalArgumentException` for business rule violations | ✅ Done |
| NFR-008 | Controller must surface service errors via `BindingResult` (global error) and re-render form | ✅ Done |

---

## 4. Out of Scope (v1)

- Transaction history storage
- Card number storage or masking
- Direct bank API integration (PSD2)
- Balance snapshots
- Account search/filter on list page (future enhancement)
- Bulk import
- Soft-delete (current delete is hard-delete)

