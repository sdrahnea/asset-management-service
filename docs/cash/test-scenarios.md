# Cash — Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

---

## 1. Service Tests (`CashService`)

### 1.1 Field Normalization

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-001 | cashId normalized to uppercase | `"cash-001"` | Normalized to `"CASH-001"` |
| TS-S-002 | cashId whitespace trimmed | `"  CASH-001  "` | Trimmed to `"CASH-001"` |
| TS-S-003 | cashId with internal spaces | `"CASH 001"` | Spaces preserved (no collapse) |
| TS-S-004 | Empty cashId converted to null | `""` | `null` (then fails required check) |
| TS-S-005 | Holder name trimmed | `"  Acme Inc  "` | Trimmed to `"Acme Inc"` |
| TS-S-006 | All string fields trimmed | Various | All whitespace trimmed |

### 1.2 Required Field Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-010 | Valid cash record created | All required fields present | No exception |
| TS-S-011 | Missing cashId | `cashId=null` | `IllegalArgumentException: "Cash ID is required"` |
| TS-S-012 | Empty cashId | `cashId=""` | `IllegalArgumentException: "Cash ID is required"` |
| TS-S-013 | Missing amount | `amount=null` | `IllegalArgumentException: "Amount is required"` |
| TS-S-014 | Missing currency | `currency=null` | `IllegalArgumentException: "Currency is required"` |
| TS-S-015 | Missing valuationDate | `valuationDate=null` | `IllegalArgumentException: "Valuation date is required"` |
| TS-S-016 | Missing cashType | `cashType=null` | `IllegalArgumentException: "Cash type is required"` |
| TS-S-017 | Missing dataSource | `dataSource=null` | `IllegalArgumentException: "Data source is required"` |

### 1.3 Amount Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-020 | Zero amount accepted | `amount=0.00` | No exception |
| TS-S-021 | Positive amount accepted | `amount=1000.50` | No exception |
| TS-S-022 | Negative amount rejected | `amount=-100.00` | `IllegalArgumentException: "Amount must be >= 0"` |
| TS-S-023 | Large amount accepted | `amount=999999999999.99` | No exception (within DECIMAL(22,2)) |
| TS-S-024 | Decimal precision preserved | `amount=100.55` | Stored as `100.55`, not rounded |

### 1.4 Currency Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-030 | Valid 3-letter ISO accepted | `currency="EUR"` | No exception |
| TS-S-031 | Valid currency accepted | `currency="RON"` | No exception |
| TS-S-032 | Valid currency accepted | `currency="USD"` | No exception |
| TS-S-033 | Empty currency rejected | `currency=""` | `IllegalArgumentException: "Currency is required"` |
| TS-S-034 | Null currency rejected | `currency=null` | `IllegalArgumentException: "Currency is required"` |
| TS-S-035 | Non-ISO currency accepted (v1 does not validate format) | `currency="XXX"` | No exception (recommend ISO validation in v2) |

### 1.5 Uniqueness Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-040 | Duplicate cashId on create rejected | Same cashId as existing record | `IllegalArgumentException: "Cash ID must be unique"` |
| TS-S-041 | Duplicate cashId on update (different record) rejected | cashId matching another record | `IllegalArgumentException: "Cash ID must be unique"` |
| TS-S-042 | Same cashId on update (same record) accepted | Updating own record with same cashId | No exception (excludeId applied) |
| TS-S-043 | cashId case-insensitive uniqueness | Existing: `"CASH-001"`, new: `"cash-001"` | `IllegalArgumentException: "Cash ID must be unique"` (normalized before check) |

### 1.6 Enum Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-050 | Valid CashType accepted | `cashType=OPERATING` | No exception |
| TS-S-051 | All CashTypes accepted | OPERATING, RESTRICTED, PETTY, INVESTMENT, ESCROW, OTHER | All accepted |
| TS-S-052 | Invalid CashType rejected | `cashType="INVALID"` | Spring binding error (400 Bad Request) |
| TS-S-053 | Valid ReconciliationStatus accepted | `reconciliationStatus=RECONCILED` | No exception (optional field) |
| TS-S-054 | Valid RiskLevel accepted | `counterpartyRisk=HIGH` | No exception (optional field) |

### 1.7 Optional Field Handling

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-060 | Null holder accepted | `holder=null` | No exception |
| TS-S-061 | Empty holder converted to null | `holder=""` | `null` |
| TS-S-062 | Null movement fields accepted | `openingBalance=null, closingBalance=null` | No exception |
| TS-S-063 | Null risk fields accepted | `counterpartyRisk=null, concentrationRisk=null` | No exception |
| TS-S-064 | Null metadata fields accepted | `responsibleAnalyst=null, notes=null` | No exception |

### 1.8 Update Behavior

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-070 | Update with all fields | Valid payload for all fields | All fields updated |
| TS-S-071 | Update with partial fields | Only some fields provided | Only provided fields updated; others unchanged |
| TS-S-072 | Update with no change to cashId | Same cashId as existing | No uniqueness violation |
| TS-S-073 | Update sets updatedAt and updatedBy | Update any field | `updatedAt` set to current time, `updatedBy` set appropriately |

---

## 2. Controller Tests (`CashController`)

> Test class: use `@WebMvcTest(CashController.class)` with `CashService` mocked.

### 2.1 GET Endpoints

| ID | Scenario | Endpoint | Expected Response |
|----|----------|----------|-------------------|
| TS-C-001 | List page loads | `GET /cash` | `200 OK`, model contains `items` list, renders `cash/list` |
| TS-C-002 | Create form loads with defaults | `GET /cash/new` | `200 OK`, model contains `item` with defaults, renders `cash/form` |
| TS-C-003 | Create form shows cashType=OPERATING | `GET /cash/new` | `cashType` model attr shows `OPERATING` selected |
| TS-C-004 | Create form shows currency=EUR | `GET /cash/new` | `currency` model attr shows `EUR` selected |
| TS-C-005 | Detail page loads for valid ID (planned Phase 2) | `GET /cash/1` | `200 OK`, model contains `item`, renders `cash/detail` |
| TS-C-006 | Edit form loads for valid ID | `GET /cash/1/edit` | `200 OK`, model contains `item` pre-populated, `isEdit=true`, renders `cash/form` |
| TS-C-007 | Edit form loads with user data | `GET /cash/1/edit` | Form fields pre-filled with existing record data |

### 2.2 POST — Create

| ID | Scenario | Input | Expected Response |
|----|----------|-------|-------------------|
| TS-C-010 | Valid create redirects to list | Valid payload with all required fields | `302 redirect to /cash` |
| TS-C-011 | Missing cashId re-renders form | Missing `cashId` | `200 OK`, form re-rendered with field error |
| TS-C-012 | Missing amount re-renders form | Missing `amount` | `200 OK`, form re-rendered with field error |
| TS-C-013 | Missing currency re-renders form | Missing `currency` | `200 OK`, form re-rendered with field error |
| TS-C-014 | Missing valuationDate re-renders form | Missing `valuationDate` | `200 OK`, form re-rendered with field error |
| TS-C-015 | Missing cashType re-renders form | Missing `cashType` | `200 OK`, form re-rendered with field error |
| TS-C-016 | Missing dataSource re-renders form | Missing `dataSource` | `200 OK`, form re-rendered with field error |
| TS-C-017 | Negative amount re-renders form | `amount=-50` | `200 OK`, form re-rendered with field error |
| TS-C-018 | Duplicate cashId re-renders form with global error | Duplicate cashId | `200 OK`, form re-rendered with `"Cash ID must be unique"` global error |
| TS-C-019 | Invalid enum value re-renders form | `cashType="INVALID"` | `200 OK`, form re-rendered with binding error |

### 2.3 POST — Update

| ID | Scenario | Input | Expected Response |
|----|----------|-------|-------------------|
| TS-C-020 | Valid update redirects to list | Valid payload | `302 redirect to /cash` |
| TS-C-021 | Validation failure on update re-renders form | Missing required field | `200 OK`, form re-rendered with errors, `isEdit=true` |
| TS-C-022 | Same cashId on update accepted (not duplicate) | Update own record with same cashId | `302 redirect to /cash` |
| TS-C-023 | Update preserves createdAt/createdBy | Update any field | Response indicates original timestamps unchanged |
| TS-C-024 | Update modifies updatedAt/updatedBy | Update any field | Response indicates updated timestamps changed |

### 2.4 POST — Delete

| ID | Scenario | Input | Expected Response |
|----|----------|--------|-------------------|
| TS-C-030 | Delete valid record redirects to list | `POST /cash/1/delete` | `302 redirect to /cash` |
| TS-C-031 | Delete non-existent record returns 404 | `POST /cash/999/delete` | `404 Not Found` |

### 2.5 Enum Model Attributes

| ID | Scenario | Endpoint | Expected Model Attribute |
|----|----------|----------|------------------------|
| TS-C-040 | Create form shows all CashType values | `GET /cash/new` | Model contains array/list of all 6 CashType enums |
| TS-C-041 | Create form shows all ReconciliationStatus values | `GET /cash/new` | Model contains array/list of all 3 ReconciliationStatus enums |
| TS-C-042 | Create form shows all RiskLevel values | `GET /cash/new` | Model contains array/list of all 3 RiskLevel enums |

---

## 3. Integration Tests (Full Workflow)

### 3.1 Create → Read → Update → Delete Cycle

| ID | Scenario | Steps | Expected Result |
|----|----------|-------|-----------------|
| TS-I-001 | Full CRUD workflow | Create record → Read it → Update it → Delete it | All operations succeed; record visible after create, modified after update, gone after delete |
| TS-I-002 | Create visible in list | Create record with cashId=TEST-001 | Record appears in `/cash` list |
| TS-I-003 | Detail page shows all fields | Create record with all fields filled | Detail page (v2) displays all fields correctly |
| TS-I-004 | Edit preserves read-only audit fields | Create → Edit → Save | createdAt/createdBy unchanged; updatedAt/updatedBy changed |

---

## 4. UI / Template Scenarios

> Manual or Selenium-based tests.

### 4.1 List Page (`cash/list.html`)

| ID | Scenario | Steps | Expected Result |
|----|----------|-------|-----------------|
| TS-U-001 | List columns visible | Open `/cash` with data | Columns: cashId, amount, currency, cashType, institution, valuationDate shown |
| TS-U-002 | Empty list shows message | Navigate to `/cash` with no data | Friendly message: `"No cash positions found"` |
| TS-U-003 | Create button leads to form | Click "Create" button on list | Redirects to `/cash/new` form |
| TS-U-004 | Edit button opens form | Click "Edit" on a row | Redirects to `/cash/{id}/edit` form |
| TS-U-005 | Delete button triggers action | Click "Delete" on a row | POST to `/cash/{id}/delete` (may require confirmation) |

### 4.2 Form Page (`cash/form.html`)

| ID | Scenario | Steps | Expected Result |
|----|----------|-------|-----------------|
| TS-U-010 | Form fields grouped by section | Open create form | Sections visible: Core, Location, Movement, Risk, Metadata |
| TS-U-011 | Required fields marked | Open form | Required fields have `*` or indicator |
| TS-U-012 | Optional fields present | Open form | Optional fields (holder, notes, etc.) visible but not required |
| TS-U-013 | Defaults populated on create | `GET /cash/new` | Default values set: OPERATING, EUR, PENDING, MEDIUM |
| TS-U-014 | Form populated on edit | `GET /cash/1/edit` | All fields pre-filled with existing data |
| TS-U-015 | Audit fields read-only | Open any form | createdAt, createdBy, updatedAt, updatedBy not editable |
| TS-U-016 | Submit button present | Open form | "Save" or "Submit" button visible |
| TS-U-017 | Cancel/Back link present | Open form | Link back to list or home visible |

### 4.3 Error Display

| ID | Scenario | Steps | Expected Result |
|----|----------|-------|-----------------|
| TS-U-020 | Field error shown | Submit form with missing required field | Error message appears below field (e.g., "Cash ID is required") |
| TS-U-021 | Global error shown | Submit form with duplicate cashId | Global error block displays at top of form |
| TS-U-022 | Multiple errors shown | Submit form with several invalid fields | All errors listed (field + global) |
| TS-U-023 | Error classes applied | Field has error | CSS class `"form-error"` or `"is-invalid"` applied for styling |

### 4.4 Enum Dropdowns

| ID | Scenario | Steps | Expected Result |
|----|----------|-------|-----------------|
| TS-U-030 | cashType dropdown shows all options | Open form, click cashType dropdown | OPERATING, RESTRICTED, PETTY, INVESTMENT, ESCROW, OTHER visible |
| TS-U-031 | reconciliationStatus dropdown shows all options | Open form, click reconciliationStatus dropdown | RECONCILED, PENDING, DISPUTED visible |
| TS-U-032 | counterpartyRisk dropdown shows all options | Open form, click counterpartyRisk dropdown | LOW, MEDIUM, HIGH visible |
| TS-U-033 | Default selected on create | Open create form | Default enums pre-selected (OPERATING, PENDING, MEDIUM) |
| TS-U-034 | Value selected on edit | Open edit form | Current enum values pre-selected |

---

## 5. Edge Cases & Boundary Conditions

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-E-001 | cashId at max length (100 chars) | 100-character cashId | Accepted and stored |
| TS-E-002 | cashId exceeds max length (101+ chars) | 101+ character cashId | Field validation error: `"Cash ID must be ≤ 100 characters"` |
| TS-E-003 | Amount at upper boundary | `999999999999999999.99` | Accepted (DECIMAL(22,2) supports this) |
| TS-E-004 | Amount with single decimal place | `100.5` | Stored as `100.50` (trailing zero added) |
| TS-E-005 | Notes at max length (1000 chars) | 1000-character notes field | Accepted and stored |
| TS-E-006 | Long description text | 5000-character cashForecast | Rejected if exceeds VARCHAR(1000) |
| TS-E-007 | Null optional fields not validated | holder=null, notes=null | No exception |
| TS-E-008 | Empty string optional fields converted to null | holder="", notes="" | Converted to `null` during normalization |
| TS-E-009 | Date in future | `valuationDate` = tomorrow | Accepted (no temporal validation in v1) |
| TS-E-010 | Date in past | `valuationDate` = 1900-01-01 | Accepted (no temporal bounds in v1) |

---

## 6. Data Quality & Consistency

| ID | Scenario | Condition | Expected Result |
|----|----------|-----------|-----------------|
| TS-D-001 | Movement math consistent | openingBalance + inflows - outflows = closingBalance | No validation; values can be inconsistent (manual responsibility) |
| TS-D-002 | Risk flags populated | complianceFlags has content | Displayed as free-form text; no format validation |
| TS-D-003 | Multiple records with same institution | Two records: institution="Bank A" | Both created successfully; no uniqueness constraint on institution |
| TS-D-004 | Same currency across records | Multiple records with currency="EUR" | All created successfully; no constraint |
| TS-D-005 | Case sensitivity preserved in text fields | holder="Acme Inc" | Stored as provided; no forced case |

---

## 7. Performance & Load Tests

| ID | Scenario | Condition | Expected Result |
|----|----------|-----------|-----------------|
| TS-P-001 | List page with 100+ records | Load `/cash` | Page loads in < 2s; pagination/lazy loading recommended for >1000 |
| TS-P-002 | Create with many optional fields | Submit form with all fields populated | Insert completes in < 100ms |
| TS-P-003 | Update large text field | Update notes field with 1000 characters | Update completes in < 100ms |
| TS-P-004 | Search/filter query performance | Filter by cashType (Phase 2) | Query executes in < 500ms for 10k records |

---

## 8. Browser & Cross-Browser Tests

| ID | Scenario | Browser | Expected Result |
|----|----------|---------|-----------------|
| TS-B-001 | Form displays correctly | Chrome, Firefox, Safari, Edge | All fields visible, buttons clickable, responsive layout |
| TS-B-002 | Dropdown menus functional | Chrome, Firefox | Dropdown opens/closes, values selectable |
| TS-B-003 | Date picker functional | Chrome (HTML5 input type="date") | Calendar appears; date selectable |
| TS-B-004 | Form validation messages visible | All browsers | Error messages readable, not overlapping |
| TS-B-005 | Mobile responsive (planned) | iPhone, Android | Form fits screen, inputs touchable |

---

## 9. Regression Test Suite (Post-Implementation)

Run these tests before each release:

### Critical Path
- [ ] TS-S-001 through TS-S-043 (Service validation logic)
- [ ] TS-C-010, TS-C-018, TS-C-020, TS-C-030 (Controller CRUD)
- [ ] TS-I-001 (Full workflow)

### Regular Suite
- [ ] All TS-S-* (service tests)
- [ ] TS-C-001 through TS-C-042 (controller tests)
- [ ] TS-U-001 through TS-U-034 (UI tests)

### Extended Suite (Monthly)
- [ ] All of above
- [ ] TS-E-* (edge cases)
- [ ] TS-D-* (data quality)
- [ ] TS-P-* (performance)

---

## Test Data Setup

### Fixtures (for repeatable testing)

**Valid Cash Record**:
```
cashId: CASH-20260404-001
amount: 50000.00
currency: EUR
valuationDate: 2026-04-04
cashType: OPERATING
dataSource: Treasury System
holder: Acme Inc
institution: Banca Transilvania
jurisdiction: Romania
reconciliationStatus: PENDING
counterpartyRisk: MEDIUM
```

**Alternative Valid Record**:
```
cashId: CASH-ESCROW-2026
amount: 150000.50
currency: USD
valuationDate: 2026-04-01
cashType: ESCROW
dataSource: Manual Entry
```

**Minimal Valid Record** (only required fields):
```
cashId: MINIMAL-001
amount: 0.00
currency: EUR
valuationDate: 2026-04-04
cashType: PETTY
dataSource: Petty Cash Box
```

---

## Manual Test Checklist

Before signing off on Phase 1:

### Create & Edit
- [ ] Can create a new cash position
- [ ] Can list all created positions
- [ ] Can edit an existing position
- [ ] Can delete a position
- [ ] Deleted position no longer in list

### Validation
- [ ] Required fields enforce themselves
- [ ] Amount ≥ 0 validation works
- [ ] Duplicate cashId rejected
- [ ] Error messages are user-friendly

### Defaults
- [ ] New form shows correct defaults (OPERATING, EUR, PENDING, MEDIUM)
- [ ] Edit form shows current values
- [ ] Timestamps (createdAt, updatedAt) set correctly

### UI
- [ ] List page formatted nicely, all columns visible
- [ ] Form page is organized by section
- [ ] Links (Back, Create, Edit, Delete) all work
- [ ] No broken images, CSS styling consistent

---

## Known Test Gaps (for Phase 2)

- [ ] No detail page tests (planned Phase 2)
- [ ] No filtering/search tests (planned Phase 2)
- [ ] No pagination tests (planned Phase 2)
- [ ] No unit tests for service (planned Phase 2)
- [ ] No soft-delete tests (planned Phase 2)
- [ ] No audit trail tests (planned Phase 2)

