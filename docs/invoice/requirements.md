# Invoice - Requirements

**Version**: 1.0 | **Date**: 2026-04-15 | **Status**: Planned

---

## 1. Overview

The Invoice module manages invoice identity, buyer/seller master data, line items, tax and totals, payment details, and lifecycle status.

---

## 2. Functional Requirements

### 2.1 Core CRUD
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create, list, view, edit, delete invoice records | Planned |
| FR-002 | Detail page at `/invoices/{id}` with full invoice breakdown | Planned |
| FR-003 | List page at `/invoices` with key filters and totals snapshot | Planned |

### 2.2 Invoice Identity
| ID | Requirement | Status |
|----|-------------|--------|
| FR-010 | `invoiceId` UUID persisted as immutable technical ID | Planned |
| FR-011 | `invoiceNumber` required and unique | Planned |
| FR-012 | `issueDate`, `dueDate`, `currency`, `status` required | Planned |

### 2.3 Parties
| ID | Requirement | Status |
|----|-------------|--------|
| FR-020 | Capture seller details (id, name, taxId, address, contact) | Planned |
| FR-021 | Capture buyer details (id, name, taxId, address, contact) | Planned |

### 2.4 Items and Totals
| ID | Requirement | Status |
|----|-------------|--------|
| FR-030 | At least one invoice item required | Planned |
| FR-031 | Per-item calculations: quantity, unitPrice, taxRate, taxAmount, lineTotal | Planned |
| FR-032 | Header totals: subtotal, totalTax, totalAmount, amountPaid, balanceDue | Planned |
| FR-033 | Derived consistency checks between line and header totals | Planned |

### 2.5 Payment and Lifecycle
| ID | Requirement | Status |
|----|-------------|--------|
| FR-040 | Store `paymentTerms`, `paymentMethod`, optional `bankAccountIban` | Planned |
| FR-041 | Support statuses: ISSUED, PARTIALLY_PAID, PAID, OVERDUE, CANCELLED | Planned |
| FR-042 | Validate lifecycle dates (`dueDate >= issueDate`) | Planned |

---

## 3. Non-Functional Requirements

| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | Monetary values use decimal precision (`BigDecimal`) | Planned |
| NFR-002 | Service throws `IllegalArgumentException` for business rule violations | Planned |
| NFR-003 | All string fields trimmed; empty strings normalized to null where optional | Planned |
| NFR-004 | Audit fields (`createdAt`, `updatedAt`) read-only in UI forms | Planned |

---

## 4. Out of Scope (v1)

- E-invoicing protocol integration (UBL/Peppol)
- External tax validation services
- Attachment/document upload
- Multi-currency conversion and FX revaluation

