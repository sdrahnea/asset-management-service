# Cash — Application Surface / Route Map

**Version**: 1.0 | **Date**: 2026-04-04

---

## 1. Route Summary

All routes are served by `CashController` under the prefix `/cash`.

| Method | Path | Handler | Description |
|--------|------|---------|-------------|
| `GET` | `/cash` | `list()` | Render list of all cash positions |
| `GET` | `/cash/new` | `createForm()` | Render empty create form |
| `POST` | `/cash` | `create()` | Submit new cash position |
| `GET` | `/cash/{id}` | `detail()` | Render detail view for one position (planned Phase 2) |
| `GET` | `/cash/{id}/edit` | `editForm()` | Render pre-filled edit form |
| `POST` | `/cash/{id}` | `update()` | Submit update for existing position |
| `POST` | `/cash/{id}/delete` | `delete()` | Delete a cash position |

---

## 2. Request / Response Contracts

### 2.1 GET `/cash` — List

**Response**: `200 OK`, renders `cash/list.html`

**Model attributes**:

| Attribute | Type | Description |
|-----------|------|-------------|
| `items` | `List<Cash>` | All cash records, ordered by last updated |
| `singularTitle` | `String` | `"Cash Position"` |
| `pluralTitle` | `String` | `"Cash Positions"` |
| `basePath` | `String` | `"/cash"` |

**Template rendering context**:
```html
<table>
  <thead>
    <tr>
      <th>Cash ID</th>
      <th>Amount</th>
      <th>Currency</th>
      <th>Type</th>
      <th>Institution</th>
      <th>Valuation Date</th>
      <th>Actions</th>
    </tr>
  </thead>
  <tbody>
    <tr th:each="cash : ${items}">
      <td th:text="${cash.cashId}"></td>
      <td th:text="${#numbers.formatDecimal(cash.amount, 1, 2)}"></td>
      <td th:text="${cash.currency}"></td>
      <td th:text="${cash.cashType}"></td>
      <td th:text="${cash.institution}"></td>
      <td th:text="${#dates.format(cash.valuationDate, 'yyyy-MM-dd')}"></td>
      <td>
        <a href="/cash/{id}">View</a> |
        <a href="/cash/{id}/edit">Edit</a> |
        <form method="post" action="/cash/{id}/delete" style="display:inline;">
          <button type="submit">Delete</button>
        </form>
      </td>
    </tr>
  </tbody>
</table>
```

---

### 2.2 GET `/cash/new` — Create Form

**Response**: `200 OK`, renders `cash/form.html`

**Model attributes**:

| Attribute | Type | Description |
|-----------|------|-------------|
| `item` | `Cash` | Empty object with defaults |
| `isEdit` | `Boolean` | `false` |
| `singularTitle` | `String` | `"Cash Position"` |
| `pluralTitle` | `String` | `"Cash Positions"` |
| `basePath` | `String` | `"/cash"` |
| `cashTypes` | `CashType[]` | Enum values for dropdown |
| `currencies` | `String[]` | Common currency codes (EUR, RON, USD, GBP, CHF) |
| `reconciliationStatuses` | `ReconciliationStatus[]` | Enum values for dropdown |
| `riskLevels` | `RiskLevel[]` | Enum values for dropdown |

**Default values set on new form**:
- `cashType` → `OPERATING`
- `currency` → `EUR`
- `reconciliationStatus` → `PENDING`
- `counterpartyRisk` → `MEDIUM`
- `amount` → `0.00`

---

### 2.3 POST `/cash` — Create

**Request**: `application/x-www-form-urlencoded`

**Form fields**:

| Field | Type | Required | Notes |
|-------|------|----------|-------|
| `name` (remapped to `cashId`) | String | ✅ | Max 100 chars; uppercase, no internal spaces |
| `amount` | BigDecimal | ✅ | ≥ 0, up to 22 total digits with 2 decimal places |
| `currency` | String | ✅ | 3-letter ISO code (e.g., EUR, RON, USD) |
| `valuationDate` | Date (`YYYY-MM-DD`) | ✅ | |
| `cashType` | String (enum name) | ✅ | One of: `OPERATING`, `RESTRICTED`, `PETTY`, `INVESTMENT`, `ESCROW`, `OTHER` |
| `dataSource` | String | ✅ | Max 100 chars; e.g., "Treasury System", "Manual Entry", "Bank Feed" |
| `holder` | String | ❌ | Max 255 chars; company, business unit, or individual name |
| `holdingAccountReference` | String | ❌ | Max 255 chars; bank account ID, wallet ID, or internal reference |
| `institution` | String | ❌ | Max 255 chars; bank, payment provider, custodian name |
| `jurisdiction` | String | ❌ | Max 100 chars; country code or name |
| `openingBalance` | BigDecimal | ❌ | ≥ 0 |
| `closingBalance` | BigDecimal | ❌ | ≥ 0 |
| `cashInflows` | BigDecimal | ❌ | ≥ 0 |
| `cashOutflows` | BigDecimal | ❌ | ≥ 0 |
| `netCashMovement` | BigDecimal | ❌ | Can be negative (inflows - outflows) |
| `cashForecast` | String | ❌ | Max 1000 chars; narrative or structured forecast |
| `reconciliationStatus` | String (enum name) | ❌ | One of: `RECONCILED`, `PENDING`, `DISPUTED` |
| `counterpartyRisk` | String (enum name) | ❌ | One of: `LOW`, `MEDIUM`, `HIGH` |
| `concentrationRisk` | String | ❌ | Max 500 chars; narrative description |
| `volatility` | String | ❌ | Max 500 chars; narrative description |
| `complianceFlags` | String | ❌ | Max 1000 chars; AML/KYC notes |
| `responsibleAnalyst` | String | ❌ | Max 100 chars; name or ID |
| `tags` | String | ❌ | Max 500 chars; comma-separated labels |
| `notes` | String | ❌ | Max 1000 chars; free-form comments |

**Success response**: `302 redirect to /cash` with success message (recommended)

**Failure response**: `200 OK`, form re-rendered with validation errors in model:
- Field-level errors displayed below each field
- Global errors (e.g., uniqueness violations) displayed at top of form
- Form values preserved for user correction

**Example error scenario**:
```
POST /cash
Form: { cashId: "CASH-001", amount: -50, currency: "EUR", ... }

Response:
  Status: 200 OK
  Template: cash/form.html
  Model:
    item: { cashId: "CASH-001", amount: -50, ... }
    isEdit: false
    bindingResult.fieldErrors('amount'): ["Amount must be >= 0"]
```

---

### 2.4 GET `/cash/{id}` — Detail (Planned Phase 2)

**Path param**: `id` — Long (primary key)

**Response**: `200 OK`, renders `cash/detail.html`

**Model attributes**:

| Attribute | Type | Description |
|-----------|------|-------------|
| `item` | `Cash` | Full record including all fields |
| `singularTitle` | `String` | `"Cash Position"` |
| `basePath` | `String` | `"/cash"` |

**Expected layout**:
- All fields displayed in organized sections (Core, Location, Movement, Risk, Metadata)
- Badges/color coding for reconciliation status and risk level
- Audit trail (createdAt, createdBy, updatedAt, updatedBy) in read-only section
- Edit and Delete action buttons
- Back to list link

**Error**: `404 Not Found` if `id` does not exist

---

### 2.5 GET `/cash/{id}/edit` — Edit Form

**Path param**: `id` — Long

**Response**: `200 OK`, renders `cash/form.html`

**Model attributes**: Same as create form (§2.2) with:
- `item` pre-populated with existing record data
- `isEdit` set to `true`

**Form fields**: Same as create (see §2.3)

**Error**: `404 Not Found` if `id` does not exist

---

### 2.6 POST `/cash/{id}` — Update

**Path param**: `id` — Long

**Request fields**: Same as create (see §2.3)

**Validation notes**:
- `cashId` uniqueness check excludes current record (excludeId pattern)
- All other validation rules same as create

**Success response**: `302 redirect to /cash` with success message

**Failure response**: `200 OK`, form re-rendered with errors, `isEdit=true`

---

### 2.7 POST `/cash/{id}/delete` — Delete

**Path param**: `id` — Long

**Response**: `302 redirect to /cash`

**Notes**:
- Hard delete (permanent); soft-delete planned for Phase 2
- POST method (not DELETE) for HTML form compatibility
- No confirmation page in v1; consider adding confirmation dialog in v2

---

## 3. Global Error Handling

Service-level `IllegalArgumentException` is caught by the controller and added as a global error on `BindingResult`:

```java
catch (IllegalArgumentException ex) {
    bindingResult.reject("cash.error", ex.getMessage());
    // Re-render form with error
}
```

The form template renders global errors in a styled block:

```html
<div th:if="${#fields.hasGlobalErrors()}" class="alert alert-danger">
    <p th:each="err : ${#fields.globalErrors()}" th:text="${err}">Error</p>
</div>
```

---

## 4. Navigation Links

| Page | Links |
|------|-------|
| List (`/cash`) | Create Cash Position, Back to Home |
| Create Form (`/cash/new`) | Back to List, Back to Home |
| Edit Form (`/cash/{id}/edit`) | Back to List, Back to Home |
| Detail (`/cash/{id}`, planned Phase 2) | Back to List, Edit, Back to Home |

---

## 5. Form Field Organization (UI Layout)

### Core Section
```
[Cash ID *]           [Amount *]
[Currency *]          [Valuation Date *]
[Cash Type *]         [Data Source *]
```

### Location & Ownership Section
```
[Holder]              [Institution]
[Holding Account Ref] [Jurisdiction]
```

### Movement & Reconciliation Section
```
[Opening Balance]     [Closing Balance]
[Cash Inflows]        [Cash Outflows]
[Net Cash Movement]   [Reconciliation Status]
[Cash Forecast (large text area)]
```

### Risk & Compliance Section
```
[Counterparty Risk]   [Concentration Risk (large)]
[Volatility (large)]  
[Compliance Flags (large)]
```

### Metadata Section
```
[Responsible Analyst] [Tags]
[Notes (large text area)]
```

### Audit Section (Read-Only)
```
[Created At (RO)]     [Created By (RO)]
[Updated At (RO)]     [Updated By (RO)]
```

---

## 6. HTTP Status Codes

| Status | Scenario | Example |
|--------|----------|---------|
| `200 OK` | Form GET, validation failure on POST | `GET /cash/new`, `POST /cash` with errors |
| `302 Found` | Successful create/update/delete | `POST /cash` → redirect `/cash` |
| `404 Not Found` | Record doesn't exist | `GET /cash/999`, `GET /cash/999/edit` |
| `400 Bad Request` | Invalid path param or binding error | `GET /cash/abc` (invalid ID format) |
| `500 Internal Server Error` | Unhandled exception | Database error, null pointer, etc. |

---

## 7. Enum Dropdown Options

### CashType Dropdown
```
OPERATING       (Operating / Working Capital)
RESTRICTED      (Restricted / Escrow / Legal Hold)
PETTY           (Petty Cash / Small Amounts)
INVESTMENT      (Investment / Held for Growth)
ESCROW          (Escrow / Held for Third Party)
OTHER           (Other / Miscellaneous)
```

### Currency Dropdown (Common Values)
```
EUR             (Euro)
RON             (Romanian Leu)
USD             (US Dollar)
GBP             (British Pound)
CHF             (Swiss Franc)
[Other ISO codes available on request]
```

### ReconciliationStatus Dropdown
```
RECONCILED      (Verified, Matched with Source)
PENDING         (Awaiting Reconciliation)
DISPUTED        (Has Discrepancies or Questions)
```

### RiskLevel Dropdown (for counterpartyRisk)
```
LOW             (Minimal Risk)
MEDIUM          (Moderate Risk)
HIGH            (Elevated Risk)
```

---

## 8. Validation Error Messages

| Validation | Error Message |
|-----------|---------------|
| Missing cashId | `"Cash ID is required"` |
| cashId too long (>100) | `"Cash ID must be ≤ 100 characters"` |
| Missing amount | `"Amount is required"` |
| Amount < 0 | `"Amount must be ≥ 0"` |
| Missing currency | `"Currency is required"` |
| Missing valuationDate | `"Valuation date is required"` |
| Missing cashType | `"Cash type is required"` |
| Missing dataSource | `"Data source is required"` |
| Duplicate cashId | `"Cash ID must be unique"` |
| Field too long | `"[Field name] must be ≤ [N] characters"` |

---

## 9. Success & Feedback Messages

| Action | Message | Type |
|--------|---------|------|
| Create | `"Cash position created successfully."` | success |
| Update | `"Cash position updated successfully."` | success |
| Delete | `"Cash position deleted successfully."` | success |
| Validation error | `"Please correct the errors below."` | error |
| Unexpected error | `"An unexpected error occurred. Please try again."` | error |

---

## 10. Planned API Extension (Future – v3)

> Not yet implemented. Listed for planning purposes.

| Method | Path | Purpose |
|--------|------|---------|
| `GET` | `/api/cash` | JSON list endpoint with filtering, pagination, sorting |
| `GET` | `/api/cash/{id}` | JSON detail endpoint |
| `POST` | `/api/cash` | JSON create endpoint |
| `PUT` | `/api/cash/{id}` | JSON update endpoint |
| `DELETE` | `/api/cash/{id}` | JSON delete endpoint |
| `GET` | `/api/cash/search` | Advanced search with filters |

### Example Planned Response (`GET /api/cash/1`)

```json
{
  "id": 1,
  "cashId": "CASH-20260404-001",
  "amount": 50000.00,
  "currency": "EUR",
  "valuationDate": "2026-04-04",
  "cashType": "OPERATING",
  "dataSource": "Treasury System",
  "holder": "Acme Inc",
  "holdingAccountReference": "ACC-12345",
  "institution": "Banca Transilvania",
  "jurisdiction": "Romania",
  "openingBalance": 45000.00,
  "closingBalance": 50000.00,
  "cashInflows": 10000.00,
  "cashOutflows": 5000.00,
  "netCashMovement": 5000.00,
  "cashForecast": "Expected increase of 3000 EUR in May",
  "reconciliationStatus": "PENDING",
  "counterpartyRisk": "MEDIUM",
  "concentrationRisk": "15% of total assets held at single institution",
  "volatility": "Low variance, stable balance",
  "complianceFlags": "AML review pending",
  "responsibleAnalyst": "John Doe",
  "tags": "treasury,operating,active",
  "notes": "Main operating account for European operations",
  "createdAt": "2026-04-01T10:00:00Z",
  "createdBy": "system",
  "updatedAt": "2026-04-04T14:30:00Z",
  "updatedBy": "system"
}
```

### Example Planned List Response (`GET /api/cash?cashType=OPERATING&limit=10`)

```json
{
  "items": [
    { /* Cash record 1 */ },
    { /* Cash record 2 */ }
  ],
  "totalCount": 47,
  "pageNumber": 1,
  "pageSize": 10,
  "totalPages": 5,
  "_links": {
    "self": "/api/cash?cashType=OPERATING&page=1",
    "next": "/api/cash?cashType=OPERATING&page=2",
    "prev": null
  }
}
```

---

## 11. CORS & Security Headers (Recommendations for v2+)

When implementing REST API (v3), consider:
- CORS headers for cross-origin requests
- CSRF token in forms (CSRF protection)
- Content Security Policy (CSP)
- X-Frame-Options (clickjacking prevention)
- X-Content-Type-Options: nosniff

---

## 12. Pagination & Filtering (Phase 2)

**Planned URL patterns**:
```
GET /cash?page=2&size=50&sort=cashId,asc&cashType=OPERATING&currency=EUR
```

**Query parameters**:
- `page` – 1-indexed page number (default: 1)
- `size` – Records per page (default: 20)
- `sort` – Sort field and direction, e.g., `cachId,desc` or `amount,asc`
- `cashType` – Filter by cash type (optional)
- `currency` – Filter by currency (optional)
- `reconciliationStatus` – Filter by status (optional)
- `institution` – Filter by institution (optional)

**Response model**:
```java
Page<Cash> page = repository.findAll(specification, pageable);
model.addAttribute("items", page.getContent());
model.addAttribute("currentPage", page.getNumber());
model.addAttribute("totalPages", page.getTotalPages());
model.addAttribute("totalItems", page.getTotalElements());
```

---

## 13. Quick Reference

| Endpoint | Method | Purpose | Auth | Template |
|----------|--------|---------|------|----------|
| `/cash` | GET | List all positions | None | `cash/list.html` |
| `/cash/new` | GET | Create form | None | `cash/form.html` |
| `/cash` | POST | Create | None | `cash/form.html` (on error) |
| `/cash/{id}` | GET | Detail (Phase 2) | None | `cash/detail.html` |
| `/cash/{id}/edit` | GET | Edit form | None | `cash/form.html` |
| `/cash/{id}` | POST | Update | None | `cash/form.html` (on error) |
| `/cash/{id}/delete` | POST | Delete | None | Redirect to `/cash` |

