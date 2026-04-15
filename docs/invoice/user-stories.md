# Invoice - User Stories

**Version**: 1.0 | **Date**: 2026-04-15

---

## US-001 - Create Invoice
**As a** Billing Specialist, **I want to** create an invoice with seller, buyer, line items, and totals **so that** receivables can be tracked correctly.

**Acceptance Criteria**:
- Form at `/invoices/new` with header, party, item, totals, payment sections
- Mandatory header and party fields validated on submit
- At least one line item required
- Redirect to list on success

## US-002 - View Invoice Detail
**As a** Finance User, **I want to** view all invoice information grouped by section **so that** I can verify legal and financial correctness.

**Acceptance Criteria**:
- Detail page at `/invoices/{id}`
- Sections: Header, Seller, Buyer, Items, Totals, Payment, Audit

## US-003 - Edit Invoice
**As a** Billing Specialist, **I want to** update draft or issued invoices **so that** corrections can be applied before settlement.

**Acceptance Criteria**:
- Edit page pre-populated with existing values
- Business-rule validation runs on save
- Uniqueness check excludes the current record

## US-004 - Track Payment State
**As an** Accountant, **I want to** record amount paid and status **so that** outstanding balances are always visible.

**Acceptance Criteria**:
- `amountPaid` updates `balanceDue`
- `status` supports ISSUED, PARTIALLY_PAID, PAID, OVERDUE, CANCELLED
- Invalid overpayment is rejected

## US-005 - Filter Invoices
**As a** Finance User, **I want to** filter invoices by status, date range, and counterparty **so that** I can quickly find relevant records.

**Acceptance Criteria**:
- Filters on `/invoices` list
- Reset filter action available

