# Bank Account — Validation Rules

**Version**: 1.0  
**Date**: 2026-04-04

---

## 1. Field-Level Validation

All field validations are enforced at two layers:

- **Bean Validation** (`@NotBlank`, `@Pattern`, etc.) on the entity, triggered by `@Valid` in the controller
- **Service-level** checks inside `BankAccountService#normalizeAndValidate(...)`, which re-throws `IllegalArgumentException`

| Field | Rule | Layer | Error Message |
|-------|------|-------|---------------|
| `name` (holder) | Required, non-blank | Service | `"Account holder name is required"` |
| `accountType` | Required, must be a valid enum value | Bean + Service | `"Account type is required"` |
| `bankName` | Required, non-blank, max 200 chars | Bean + Service | `"Bank name is required"` |
| `iban` | Required, non-blank, max 34 chars | Bean + Service | `"IBAN is required"` |
| `iban` | Must match regex `^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$` | Bean | `"IBAN format is invalid"` |
| `iban` | Must pass ISO 7064 MOD-97-10 checksum | Service | `"IBAN checksum is invalid"` |
| `swiftCode` | Optional; when present, must match `^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?$`, max 11 chars | Bean + Service | `"SWIFT/BIC format is invalid"` |
| `localAccountNumber` | Optional, max 64 chars | Bean | — |
| `branchName` | Optional, max 200 chars | Bean | — |
| `currency` | Required, non-blank, must match `^[A-Za-z]{3}$` (3-letter ISO code) | Bean + Service | `"Currency is required"` / `"Currency must be a 3-letter ISO code"` |
| `status` | Required, must be a valid enum value | Bean + Service | `"Status is required"` |
| `ownershipType` | Required, must be a valid enum value | Bean + Service | `"Ownership type is required"` |
| `accountPurpose` | Optional, max 255 chars | Bean | — |
| `verificationStatus` | Optional, max 50 chars | Bean | — |
| `linkedEntity` | Optional, max 255 chars | Bean | — |

---

## 2. Business Rules

| ID | Rule | Layer | Error Message |
|----|------|-------|---------------|
| BR-001 | `closedAt` must be on or after `openedAt` when both are provided | Bean (`@AssertTrue`) + Service | `"Closed date must be greater than or equal to opened date"` |
| BR-002 | `closedAt` is silently cleared (set to `null`) if `status ≠ CLOSED` | Service (normalization) | — (silent normalization, no error) |
| BR-003 | IBAN is normalized: stripped of whitespace and uppercased before any validation | Service (normalization) | — |
| BR-004 | SWIFT/BIC is normalized: stripped of whitespace and uppercased before validation | Service (normalization) | — |
| BR-005 | Currency is normalized: stripped of whitespace and uppercased before validation | Service (normalization) | — |

---

## 3. Uniqueness Rules

| ID | Rule | Query | Error Message |
|----|------|-------|---------------|
| UQ-001 | `iban` must be globally unique (case-insensitive) | `BankAccountRepository#existsIban(iban, excludeId)` | `"IBAN must be unique"` |
| UQ-002 | `localAccountNumber` must be unique per `bankName + branchName` (case-insensitive; null branchName treated as empty string) | `BankAccountRepository#existsLocalAccountForBankAndBranch(...)` | `"Local account number must be unique for the same bank and branch"` |

> `excludeId` is passed as the current record's ID on update so a record is not flagged as
> conflicting with itself.

---

## 4. IBAN Checksum Algorithm

The MOD-97-10 checksum is implemented in `BankAccountService#isIbanChecksumValid(String iban)`:

1. Move the first 4 characters of the IBAN to the end.
2. Replace each letter with two digits: A=10, B=11, …, Z=35.
3. Compute the remainder of the resulting integer divided by 97.
4. The IBAN is valid if and only if the remainder equals **1**.

---

## 5. SWIFT/BIC Format

Format: `AAAA BB CC DDD` (spaces shown for clarity, stored without spaces)

| Part | Length | Content |
|------|--------|---------|
| Bank code | 4 chars | A–Z letters |
| Country code | 2 chars | A–Z letters |
| Location code | 2 chars | A–Z or 0–9 |
| Branch code | 3 chars | Optional; A–Z or 0–9 |

Full regex (after normalization): `^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?$`

---

## 6. Validation Error Surfacing

- **Bean validation errors** are collected by Spring MVC into `BindingResult` and rendered via
  `th:errors` in the form template.
- **Service-level `IllegalArgumentException`** is caught in the controller and added as a global
  form error via `BindingResult#reject("bankAccount.error", message)`, then the form is re-rendered.
- The form template displays global errors in a red block above the form fields.

