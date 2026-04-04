# Bank Account — Test Scenarios

**Version**: 1.0  
**Date**: 2026-04-04

---

## 1. Service Tests (`BankAccountService`)

### 1.1 IBAN Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-001 | Valid Romanian IBAN accepted | `RO49AAAA1B31007593840000` | No exception |
| TS-S-002 | IBAN with lowercase letters normalized then validated | `ro49aaaa1b31007593840000` | Normalized to uppercase, no exception |
| TS-S-003 | IBAN with internal spaces normalized then validated | `RO49 AAAA 1B31 0075 9384 0000` | Spaces stripped, no exception |
| TS-S-004 | IBAN with wrong format rejected | `INVALID123` | `IllegalArgumentException: "IBAN format is invalid"` |
| TS-S-005 | IBAN with wrong checksum rejected | `RO49AAAA1B31007593840001` | `IllegalArgumentException: "IBAN checksum is invalid"` |
| TS-S-006 | Empty IBAN rejected | `""` / `null` | `IllegalArgumentException: "IBAN is required"` |

### 1.2 SWIFT/BIC Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-010 | Valid 8-char SWIFT accepted | `RNCBROBU` | No exception |
| TS-S-011 | Valid 11-char SWIFT accepted | `RNCBROBUXXX` | No exception |
| TS-S-012 | SWIFT with lowercase normalized | `rncbrobu` | Normalized to uppercase, no exception |
| TS-S-013 | Empty SWIFT accepted (optional field) | `""` / `null` | No exception |
| TS-S-014 | Invalid SWIFT format rejected | `BADSWIFT` | `IllegalArgumentException: "SWIFT/BIC format is invalid"` |

### 1.3 Business Rules

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-020 | `closedAt` before `openedAt` rejected | openedAt=2026-01-10, closedAt=2026-01-01 | `IllegalArgumentException: "Closed date must be greater than or equal to opened date"` |
| TS-S-021 | `closedAt` equal to `openedAt` accepted | openedAt=2026-01-10, closedAt=2026-01-10 | No exception |
| TS-S-022 | `closedAt` set when status=ACTIVE is cleared silently | status=ACTIVE, closedAt=2026-01-15 | `closedAt` becomes `null` before save |
| TS-S-023 | `closedAt` retained when status=CLOSED | status=CLOSED, closedAt=2026-01-15 | `closedAt` is persisted |
| TS-S-024 | Missing holder name rejected | name=null | `IllegalArgumentException: "Account holder name is required"` |
| TS-S-025 | Missing accountType rejected | accountType=null | `IllegalArgumentException: "Account type is required"` |
| TS-S-026 | Missing bank name rejected | bankName=null | `IllegalArgumentException: "Bank name is required"` |
| TS-S-027 | Missing currency rejected | currency=null | `IllegalArgumentException: "Currency is required"` |

### 1.4 Uniqueness

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-030 | Duplicate IBAN on create rejected | Same IBAN as existing record | `IllegalArgumentException: "IBAN must be unique"` |
| TS-S-031 | Duplicate IBAN on update (different record) rejected | IBAN matching another record | `IllegalArgumentException: "IBAN must be unique"` |
| TS-S-032 | Same IBAN on update (same record) accepted | IBAN matches own record | No exception (excludeId applied) |
| TS-S-033 | Duplicate local account + bank + branch rejected | Same local account, bank, branch as existing record | `IllegalArgumentException: "Local account number must be unique for the same bank and branch"` |
| TS-S-034 | Same local account in different bank accepted | Same local account number, different bank | No exception |
| TS-S-035 | Null local account number bypasses uniqueness check | localAccountNumber=null | No exception |

---

## 2. Controller Tests (`BankAccountController`)

> Test class: use `@WebMvcTest(BankAccountController.class)` with `BankAccountService` mocked.

### 2.1 GET Endpoints

| ID | Scenario | Endpoint | Expected Response |
|----|----------|----------|-------------------|
| TS-C-001 | List page loads | `GET /bank-accounts` | `200 OK`, model contains `bankAccounts`, renders `bank-accounts/list` |
| TS-C-002 | Create form loads with defaults | `GET /bank-accounts/new` | `200 OK`, model contains `bankAccount` with status=ACTIVE, renders `bank-accounts/form` |
| TS-C-003 | Detail page loads for valid ID | `GET /bank-accounts/1` | `200 OK`, model contains `bankAccount`, renders `bank-accounts/detail` |
| TS-C-004 | Detail page returns 404 for unknown ID | `GET /bank-accounts/999` | `404 Not Found` |
| TS-C-005 | Edit form loads for valid ID | `GET /bank-accounts/1/edit` | `200 OK`, model contains `bankAccount` and `isEdit=true`, renders `bank-accounts/form` |

### 2.2 POST — Create

| ID | Scenario | Input | Expected Response |
|----|----------|-------|-------------------|
| TS-C-010 | Valid create redirects to list | Valid payload | `302 redirect to /bank-accounts` |
| TS-C-011 | Missing mandatory field re-renders form | Missing `iban` | `200 OK`, form re-rendered with field errors |
| TS-C-012 | Invalid IBAN format re-renders form | `iban=INVALID` | `200 OK`, form re-rendered with global error |
| TS-C-013 | Duplicate IBAN re-renders form with global error | Duplicate IBAN | `200 OK`, form re-rendered with `"IBAN must be unique"` global error |

### 2.3 POST — Update

| ID | Scenario | Input | Expected Response |
|----|----------|-------|-------------------|
| TS-C-020 | Valid update redirects to list | Valid payload | `302 redirect to /bank-accounts` |
| TS-C-021 | Validation failure on update re-renders form | Missing `bankName` | `200 OK`, form re-rendered with errors, `isEdit=true` |

### 2.4 POST — Delete

| ID | Scenario | Input | Expected Response |
|----|----------|-------|-------------------|
| TS-C-030 | Delete valid record redirects to list | `POST /bank-accounts/1/delete` | `302 redirect to /bank-accounts` |

---

## 3. UI / Template Scenarios

> Manual or Selenium-based tests.

| ID | Scenario | Steps | Expected Result |
|----|----------|-------|-----------------|
| TS-U-001 | IBAN masked on list page | Open `/bank-accounts`, create a record | IBAN shows `***...1234` format |
| TS-U-002 | Full IBAN on detail page | Click "View" on a list row | Full IBAN is visible |
| TS-U-003 | `closedAt` hidden when status=ACTIVE | Open create form, select ACTIVE status | `closedAt` field is hidden |
| TS-U-004 | `closedAt` visible when status=CLOSED | Open create form, select CLOSED status | `closedAt` field appears |
| TS-U-005 | `closedAt` clears when status changed away from CLOSED | Select CLOSED, enter date, switch to ACTIVE | Date field is cleared |
| TS-U-006 | Form error shown for invalid IBAN | Submit form with `iban=INVALID123` | Error message appears below the IBAN field or as global error |
| TS-U-007 | Audit fields read-only on form | Open edit form | `createdAt`, `createdBy`, `updatedAt`, `updatedBy` are not editable |
| TS-U-008 | Empty list shows placeholder message | Navigate to `/bank-accounts` with no data | `"No bank accounts found."` message shown |

---

## 4. Edge Cases

| ID | Scenario | Notes |
|----|----------|-------|
| TS-E-001 | IBAN with spaces is accepted and normalized | User inputs `RO49 AAAA 1B31 ...`; service strips spaces |
| TS-E-002 | IBAN with mixed case is accepted and normalized | User inputs `ro49aaaa...`; service uppercases |
| TS-E-003 | BranchName null vs blank treated consistently | `""` and `null` both stored as `null`; uniqueness check uses `COALESCE(branch_name,'')` |
| TS-E-004 | Update with no change to IBAN must not fail uniqueness | ExcludeId prevents self-conflict |
| TS-E-005 | Setting status back to ACTIVE after CLOSED clears closedAt | `closedAt` is not retained when status is no longer CLOSED |

