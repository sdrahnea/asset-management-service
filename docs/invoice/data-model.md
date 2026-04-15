# Invoice - Data Model Specification

**Version**: 1.0 | **Date**: 2026-04-15  
**Entity class**: `com.sdr.ams.model.financial.Invoice` (Planned) | **Table**: `invoice` (Planned)

---

## 1. Aggregate Structure

`Invoice` is an aggregate root with nested party objects (`seller`, `buyer`) and a child collection (`items`).

---

## 2. Enums

| Enum | Values |
|------|--------|
| `InvoiceStatus` | ISSUED, PARTIALLY_PAID, PAID, OVERDUE, CANCELLED |
| `PaymentMethod` | BANK_TRANSFER, CARD, CASH, OTHER |

---

## 3. Fields

### 3.1 Header

| Field | Type | Required | Unique | Notes |
|-------|------|----------|--------|-------|
| `invoiceId` | UUID | Yes | Yes | Technical immutable identifier |
| `invoiceNumber` | String(50) | Yes | Yes | Business identifier |
| `issueDate` | LocalDate | Yes | No | |
| `dueDate` | LocalDate | Yes | No | Must be >= issueDate |
| `currency` | String(3) | Yes | No | ISO 4217 |
| `status` | Enum | Yes | No | Lifecycle state |

### 3.2 Seller

| Field | Type | Required | Notes |
|-------|------|----------|-------|
| `sellerId` | String(100) | Yes | External or internal seller key |
| `sellerName` | String(255) | Yes | |
| `sellerTaxId` | String(100) | Yes | |
| `sellerAddress` | String(500) | Yes | |
| `sellerContactInfo` | String(255) | No | Email/phone |

### 3.3 Buyer

| Field | Type | Required | Notes |
|-------|------|----------|-------|
| `buyerId` | String(100) | Yes | External or internal buyer key |
| `buyerName` | String(255) | Yes | |
| `buyerTaxId` | String(100) | Yes | |
| `buyerAddress` | String(500) | Yes | |
| `buyerContactInfo` | String(255) | No | Email/phone |

### 3.4 Items

| Field | Type | Required | Notes |
|-------|------|----------|-------|
| `itemId` | String(100) | Yes | Unique within invoice |
| `description` | String(500) | Yes | |
| `quantity` | Decimal(18,4) | Yes | Must be > 0 |
| `unitPrice` | Decimal(18,4) | Yes | Must be >= 0 |
| `unitOfMeasure` | String(50) | No | e.g., hours, pcs |
| `taxRate` | Decimal(7,4) | Yes | 0..100 |
| `taxAmount` | Decimal(18,4) | Yes | >= 0 |
| `lineTotal` | Decimal(18,4) | Yes | quantity*unitPrice + taxAmount |

### 3.5 Totals and Payment

| Field | Type | Required | Notes |
|-------|------|----------|-------|
| `subtotal` | Decimal(18,4) | Yes | Sum of net line amounts |
| `totalTax` | Decimal(18,4) | Yes | Sum of line tax |
| `totalAmount` | Decimal(18,4) | Yes | subtotal + totalTax |
| `amountPaid` | Decimal(18,4) | Yes | >= 0 |
| `balanceDue` | Decimal(18,4) | Yes | totalAmount - amountPaid |
| `paymentTerms` | String(100) | No | Example: Net 30 |
| `paymentMethod` | Enum | No | |
| `bankAccountIban` | String(34) | No | IBAN format validation |

### 3.6 Audit

| Field | Type | Required | Notes |
|-------|------|----------|-------|
| `createdAt` | Instant/LocalDateTime | Yes | Auto-populated |
| `updatedAt` | Instant/LocalDateTime | Yes | Auto-updated |

---

## 4. Unique Constraints

| Name | Column(s) |
|------|-----------|
| `uk_invoice_invoice_id` | `invoice_id` |
| `uk_invoice_invoice_number` | `invoice_number` |
| `uk_invoice_item_id_per_invoice` | `(invoice_id, item_id)` |

---

## 5. Normalization Rules

| Field | Rule |
|-------|------|
| `invoiceNumber`, `currency`, `sellerTaxId`, `buyerTaxId`, `bankAccountIban` | Uppercase + trim |
| Optional text fields | Trim; empty string -> null |

