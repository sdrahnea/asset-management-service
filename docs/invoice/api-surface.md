# Invoice - API Surface

**Version**: 1.0 | **Date**: 2026-04-15

---

## UI Routes (Planned)

| Method | Path | Handler | Description |
|--------|------|---------|-------------|
| GET | `/invoices` | `list()` | List invoices with optional filters |
| GET | `/invoices/new` | `createForm()` | Empty create form |
| POST | `/invoices` | `create()` | Submit new invoice |
| GET | `/invoices/{id}` | `detail()` | Invoice detail view |
| GET | `/invoices/{id}/edit` | `editForm()` | Pre-filled edit form |
| POST | `/invoices/{id}` | `update()` | Submit update |
| POST | `/invoices/{id}/delete` | `delete()` | Delete invoice |

---

## Query Parameters (GET /invoices, Planned)

| Param | Type | Description |
|-------|------|-------------|
| `invoiceNumber` | String | Partial invoice number match |
| `status` | InvoiceStatus enum | Exact status filter |
| `sellerName` | String | Seller name filter |
| `buyerName` | String | Buyer name filter |
| `issueDateFrom` | Date | Lower bound for issue date |
| `issueDateTo` | Date | Upper bound for issue date |
| `dueDateFrom` | Date | Lower bound for due date |
| `dueDateTo` | Date | Upper bound for due date |

---

## Payload Contract (Reference)

The target payload structure follows the JSON model defined in `invoice.md`, including nested `seller`, `buyer`, and `items`.

**Success**: `302` redirect to `/invoices`  
**Failure**: `200` re-rendered form with validation errors

---

## Planned REST API (Future)

| Method | Path | Purpose |
|--------|------|---------|
| GET | `/api/invoices` | JSON list |
| GET | `/api/invoices/{id}` | JSON detail |
| POST | `/api/invoices` | JSON create |
| PUT | `/api/invoices/{id}` | JSON update |
| DELETE | `/api/invoices/{id}` | JSON delete |

