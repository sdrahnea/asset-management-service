# Bank Account — Application Surface / Route Map

**Version**: 1.0  
**Date**: 2026-04-04

---

## 1. Route Summary

All routes are served by `BankAccountController` under the prefix `/bank-accounts`.

| Method | Path | Handler | Description |
|--------|------|---------|-------------|
| `GET` | `/bank-accounts` | `list()` | Render list of all bank accounts |
| `GET` | `/bank-accounts/new` | `createForm()` | Render empty create form |
| `POST` | `/bank-accounts` | `create()` | Submit new bank account |
| `GET` | `/bank-accounts/{id}` | `detail()` | Render detail view for one account |
| `GET` | `/bank-accounts/{id}/edit` | `editForm()` | Render pre-filled edit form |
| `POST` | `/bank-accounts/{id}` | `update()` | Submit update for existing account |
| `POST` | `/bank-accounts/{id}/delete` | `delete()` | Delete a bank account |

---

## 2. Request / Response Contracts

### 2.1 GET `/bank-accounts` — List

**Response**: `200 OK`, renders `bank-accounts/list.html`

**Model attributes**:

| Attribute | Type | Description |
|-----------|------|-------------|
| `bankAccounts` | `List<BankAccount>` | All bank account records, ordered by last updated |

---

### 2.2 GET `/bank-accounts/new` — Create Form

**Response**: `200 OK`, renders `bank-accounts/form.html`

**Model attributes**:

| Attribute | Type | Description |
|-----------|------|-------------|
| `bankAccount` | `BankAccount` | Empty object with defaults (ACTIVE, INDIVIDUAL, CURRENT, EUR) |
| `isEdit` | `Boolean` | `false` |
| `accountTypes` | `AccountType[]` | Enum values for dropdown |
| `statuses` | `Status[]` | Enum values for dropdown |
| `ownershipTypes` | `OwnershipType[]` | Enum values for dropdown |

---

### 2.3 POST `/bank-accounts` — Create

**Request**: `application/x-www-form-urlencoded`

**Form fields**:

| Field | Type | Required | Notes |
|-------|------|----------|-------|
| `name` | String | ✅ | Account holder name |
| `accountType` | String (enum name) | ✅ | One of: `CURRENT`, `SAVINGS`, `CORPORATE`, `ESCROW`, `DEPOSIT`, `OTHER` |
| `bankName` | String | ✅ | |
| `branchName` | String | ❌ | |
| `iban` | String | ✅ | Validated for format and checksum |
| `swiftCode` | String | ❌ | Validated for format if present |
| `localAccountNumber` | String | ❌ | |
| `currency` | String | ✅ | 3-letter ISO code |
| `status` | String (enum name) | ✅ | One of: `ACTIVE`, `CLOSED`, `SUSPENDED` |
| `ownershipType` | String (enum name) | ✅ | One of: `INDIVIDUAL`, `JOINT`, `CORPORATE` |
| `openedAt` | Date (`YYYY-MM-DD`) | ❌ | |
| `closedAt` | Date (`YYYY-MM-DD`) | ❌ | Only meaningful when `status=CLOSED` |
| `accountPurpose` | String | ❌ | Max 255 chars |
| `assessmentScore` | Decimal | ❌ | |
| `verificationStatus` | String | ❌ | Max 50 chars |
| `lastReviewDate` | Date (`YYYY-MM-DD`) | ❌ | |
| `linkedEntity` | String | ❌ | Max 255 chars |

**Success response**: `302 redirect to /bank-accounts`

**Failure response**: `200 OK`, form re-rendered with validation errors in model

---

### 2.4 GET `/bank-accounts/{id}` — Detail

**Path param**: `id` — Long (primary key)

**Response**: `200 OK`, renders `bank-accounts/detail.html`

**Model attributes**:

| Attribute | Type | Description |
|-----------|------|-------------|
| `bankAccount` | `BankAccount` | Full record including all fields and full IBAN |

**Error**: `404 Not Found` if `id` does not exist

---

### 2.5 GET `/bank-accounts/{id}/edit` — Edit Form

**Path param**: `id` — Long

**Response**: `200 OK`, renders `bank-accounts/form.html`

**Model attributes**: Same as create form (`isEdit=true`, `bankAccount` pre-populated)

**Error**: `404 Not Found` if `id` does not exist

---

### 2.6 POST `/bank-accounts/{id}` — Update

**Path param**: `id` — Long

**Request fields**: Same as create (see §2.3)

**Success response**: `302 redirect to /bank-accounts`

**Failure response**: `200 OK`, form re-rendered with errors, `isEdit=true`

---

### 2.7 POST `/bank-accounts/{id}/delete` — Delete

**Path param**: `id` — Long

**Response**: `302 redirect to /bank-accounts`

---

## 3. Global Error Handling

Service-level `IllegalArgumentException` is caught by the controller and added as a global
error on `BindingResult`:

```
bindingResult.reject("bankAccount.error", ex.getMessage());
```

The form template renders global errors in a red error block above the form:

```html
<div th:if="${#fields.hasGlobalErrors()}">
    <p th:each="err : ${#fields.globalErrors()}" th:text="${err}">Error</p>
</div>
```

---

## 4. Navigation Links

| Page | Links |
|------|-------|
| List | Create Bank Account, Back to Home |
| Form | Back to List, Back to Home |
| Detail | Back to List, Back to Home, Edit |

---

## 5. Planned API Extension (Future)

> Not yet implemented. Listed for planning purposes.

| Method | Path | Purpose |
|--------|------|---------|
| `GET` | `/api/bank-accounts` | JSON list endpoint for programmatic consumers |
| `GET` | `/api/bank-accounts/{id}` | JSON detail endpoint |
| `POST` | `/api/bank-accounts` | JSON create endpoint |
| `PUT` | `/api/bank-accounts/{id}` | JSON update endpoint |
| `DELETE` | `/api/bank-accounts/{id}` | JSON delete endpoint |

**Example planned response (`GET /api/bank-accounts/1`)**:

```json
{
  "id": 1,
  "name": "Operating Account",
  "accountType": "CURRENT",
  "bankName": "Banca Transilvania",
  "branchName": "Cluj-Napoca Central",
  "iban": "RO49AAAA1B31007593840000",
  "swiftCode": "BTRLRO22",
  "currency": "RON",
  "status": "ACTIVE",
  "ownershipType": "CORPORATE",
  "openedAt": "2023-01-15",
  "closedAt": null,
  "accountPurpose": "Business operations",
  "assessmentScore": 88.50,
  "verificationStatus": "Verified",
  "lastReviewDate": "2026-01-10",
  "linkedEntity": "Project Atlas",
  "createdAt": "2026-01-01T08:00:00Z",
  "createdBy": "system",
  "updatedAt": "2026-03-15T14:22:00Z",
  "updatedBy": "system"
}
```

