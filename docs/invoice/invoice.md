# Invoice - Domain Specification

This document defines the v1 invoice data contract for a standard B2B receivable/payable record.

---

## Core Identification

- `invoice_id` (UUID)
- `invoice_number` (business identifier, example: `INV-2026-00123`)
- `issue_date`
- `due_date`
- `currency`
- `status` (`issued`, `partially_paid`, `paid`, `overdue`, `cancelled`)

## Counterparties

### Seller
- `seller_id`
- `seller_name`
- `seller_tax_id`
- `seller_address`
- `seller_contact_info`

### Buyer
- `buyer_id`
- `buyer_name`
- `buyer_tax_id`
- `buyer_address`
- `buyer_contact_info`

## Invoice Lines

Each item includes:
- `item_id`
- `description`
- `quantity`
- `unit_price`
- `unit_of_measure`
- `tax_rate`
- `tax_amount`
- `line_total`

## Totals and Settlement

- `subtotal`
- `total_tax`
- `total_amount`
- `amount_paid`
- `balance_due`
- `payment_terms`
- `payment_method`
- `bank_account_iban`

## Audit Metadata

- `created_at`
- `updated_at`

---

## High-Level Business Intent

The Invoice module supports issuing, tracking, and reconciling invoices with explicit party details, line-level calculations, and payment state visibility.

