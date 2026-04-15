# Invoice - Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-15

---

## 1. Service Tests

| ID | Scenario | Input | Expected |
|----|----------|-------|----------|
| TS-S-001 | Valid invoice is accepted | Required header, parties, 1 valid item | Saved successfully |
| TS-S-002 | Missing invoiceNumber rejected | `invoiceNumber=null` | `Invoice number is required` |
| TS-S-003 | dueDate before issueDate rejected | `issueDate=2026-04-20`, `dueDate=2026-04-10` | Business error |
| TS-S-004 | No items rejected | `items=[]` | `At least one invoice item is required` |
| TS-S-005 | Negative amount rejected | `subtotal=-1` | Validation error |
| TS-S-006 | Overpayment rejected | `amountPaid > totalAmount` | `Amount paid cannot exceed total amount` |
| TS-S-007 | Header totals mismatch rejected | Inconsistent subtotal/tax/total | Business error |
| TS-S-008 | Duplicate invoiceNumber rejected | Existing invoiceNumber | Uniqueness error |
| TS-S-009 | Invalid IBAN rejected | malformed `bankAccountIban` | `Invalid IBAN` |
| TS-S-010 | invoiceNumber normalized | mixed case + spaces | Stored uppercase/trimmed |

---

## 2. Controller/UI Tests

| ID | Scenario | Endpoint | Expected |
|----|----------|----------|----------|
| TS-C-001 | List loads | `GET /invoices` | 200 |
| TS-C-002 | Create form loads | `GET /invoices/new` | 200 |
| TS-C-003 | Valid create redirects | `POST /invoices` valid payload | 302 -> `/invoices` |
| TS-C-004 | Invalid create re-renders form | Missing required fields | 200 with errors |
| TS-C-005 | Detail loads | `GET /invoices/{id}` | 200 |
| TS-C-006 | Edit loads | `GET /invoices/{id}/edit` | 200 |
| TS-C-007 | Valid update redirects | `POST /invoices/{id}` | 302 -> `/invoices` |
| TS-C-008 | Delete redirects | `POST /invoices/{id}/delete` | 302 -> `/invoices` |

---

## 3. Edge Cases

| ID | Scenario | Expected |
|----|----------|----------|
| TS-E-001 | Item with quantity precision (4 decimals) | Correct arithmetic rounding |
| TS-E-002 | Zero tax invoice (`taxRate=0`) | Accepted, totals consistent |
| TS-E-003 | Status set to PAID but balanceDue > 0 | Rejected by business rule |

