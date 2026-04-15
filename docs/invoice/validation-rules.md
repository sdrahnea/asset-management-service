# Invoice - Validation Rules

**Version**: 1.0 | **Date**: 2026-04-15

---

## 1. Field-Level Validation

| Field | Rule | Layer | Error Message |
|-------|------|-------|---------------|
| `invoiceId` | Required UUID | Bean + Service | `Invoice ID is required` |
| `invoiceNumber` | Required, max 50 chars | Bean + Service | `Invoice number is required` |
| `issueDate` | Required | Bean + Service | `Issue date is required` |
| `dueDate` | Required | Bean + Service | `Due date is required` |
| `currency` | Required, exactly 3 letters | Bean + Service | `Currency must be ISO 4217` |
| `status` | Required enum | Bean + Service | `Status is required` |
| `sellerName`, `buyerName` | Required | Bean + Service | `Party name is required` |
| `sellerTaxId`, `buyerTaxId` | Required | Bean + Service | `Party tax ID is required` |
| `quantity` | > 0 | Bean + Service | `Quantity must be greater than zero` |
| `unitPrice`, `taxAmount`, `subtotal`, `totalTax`, `totalAmount`, `amountPaid`, `balanceDue` | >= 0 | Bean + Service | `Amount cannot be negative` |
| `taxRate` | 0..100 | Bean + Service | `Tax rate must be between 0 and 100` |
| `bankAccountIban` | Optional, valid IBAN format/checksum if provided | Service | `Invalid IBAN` |

---

## 2. Business Rules

| ID | Rule | Layer | Error |
|----|------|-------|-------|
| BR-001 | `dueDate >= issueDate` | Bean (`@AssertTrue`) + Service | `Due date must be on or after issue date` |
| BR-002 | Invoice must contain at least one item | Service | `At least one invoice item is required` |
| BR-003 | `lineTotal = quantity * unitPrice + taxAmount` per item | Service | `Line total mismatch` |
| BR-004 | `subtotal = sum(quantity * unitPrice)` | Service | `Subtotal mismatch` |
| BR-005 | `totalTax = sum(taxAmount)` | Service | `Total tax mismatch` |
| BR-006 | `totalAmount = subtotal + totalTax` | Service | `Total amount mismatch` |
| BR-007 | `balanceDue = totalAmount - amountPaid` | Service | `Balance due mismatch` |
| BR-008 | `amountPaid <= totalAmount` | Service | `Amount paid cannot exceed total amount` |

---

## 3. Uniqueness Rules

| ID | Rule | Query | Error |
|----|------|-------|-------|
| UQ-001 | `invoiceId` globally unique | `existsByInvoiceId(invoiceId)` | `Invoice ID must be unique` |
| UQ-002 | `invoiceNumber` globally unique (case-insensitive) | `existsByInvoiceNumber(invoiceNumber, excludeId)` | `Invoice number must be unique` |
| UQ-003 | `itemId` unique inside one invoice | Service-level set check and DB unique key | `Item ID must be unique within invoice` |

