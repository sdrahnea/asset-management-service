# Bank Account — Implementation Tasks

**Version**: 1.0  
**Date**: 2026-04-04

---

## Legend

| Symbol | Meaning |
|--------|---------|
| ✅ | Completed and in codebase |
| 🔲 | Planned / not yet implemented |
| ⚠️ | Partial or needs review |

---

## Phase 1 — Core CRUD (v1) ✅ Complete

### Data Model
- [x] ✅ TASK-001: Create `BankAccount` entity extending `CoreEntity`
- [x] ✅ TASK-002: Add mandatory fields: `accountType`, `bankName`, `iban`, `currency`, `status`, `ownershipType`
- [x] ✅ TASK-003: Add optional fields: `swiftCode`, `localAccountNumber`, `branchName`, `openedAt`, `closedAt`, `accountPurpose`, `assessmentScore`, `verificationStatus`, `lastReviewDate`, `linkedEntity`
- [x] ✅ TASK-004: Define enums: `AccountType`, `Status`, `OwnershipType`
- [x] ✅ TASK-005: Add `@UniqueConstraint` for IBAN and local account + bank + branch
- [x] ✅ TASK-006: Add bean validation annotations (`@NotBlank`, `@NotNull`, `@Pattern`, `@Size`)
- [x] ✅ TASK-007: Add `@AssertTrue` for `closedAt >= openedAt` business rule
- [x] ✅ TASK-008: Add `getMaskedIban()` computed method for list-page display

### Repository
- [x] ✅ TASK-010: Create `BankAccountRepository` extending `JpaRepository<BankAccount, Long>`
- [x] ✅ TASK-011: Add `existsIban(iban, excludeId)` query for IBAN uniqueness check
- [x] ✅ TASK-012: Add `existsLocalAccountForBankAndBranch(localAccNum, bankName, branchName, excludeId)` query

### Service
- [x] ✅ TASK-020: Create `BankAccountService` with `findAll()`, `findById()`, `create()`, `update()`, `delete()`
- [x] ✅ TASK-021: Implement `normalizeAndValidate(BankAccount, excludeId)` with normalization pipeline
- [x] ✅ TASK-022: Implement IBAN regex validation in service
- [x] ✅ TASK-023: Implement IBAN MOD-97-10 checksum validation
- [x] ✅ TASK-024: Implement SWIFT/BIC format validation
- [x] ✅ TASK-025: Implement `closedAt` business rule (enforce null when status ≠ CLOSED)
- [x] ✅ TASK-026: Implement uniqueness validation (IBAN, local account)
- [x] ✅ TASK-027: Implement manual field-copy update pattern (explicit field assignment)

### Controller
- [x] ✅ TASK-030: Create `BankAccountController` with routes for list, create form, create POST, detail, edit form, update POST, delete
- [x] ✅ TASK-031: Populate enum model attributes for dropdowns
- [x] ✅ TASK-032: Set default values on new form (status, ownership, accountType, currency)
- [x] ✅ TASK-033: Handle `@Valid` + `BindingResult` for bean validation errors
- [x] ✅ TASK-034: Catch `IllegalArgumentException` from service and surface as global form error

### Templates
- [x] ✅ TASK-040: Create `bank-accounts/list.html` with masked IBAN column and View/Edit/Delete actions
- [x] ✅ TASK-041: Create `bank-accounts/form.html` with all fields, error display, and audit read-only section
- [x] ✅ TASK-042: Create `bank-accounts/detail.html` with all fields including full IBAN
- [x] ✅ TASK-043: Add JavaScript to show/hide `closedAt` field based on status dropdown value
- [x] ✅ TASK-044: Link shared `templates.css` stylesheet

---

## Phase 2 — Enhancements (Planned)

### Search and Filtering
- [ ] 🔲 TASK-100: Add filter parameters to `BankAccountController#list()` (bankName, status, accountType, currency)
- [ ] 🔲 TASK-101: Add `search(...)` query to `BankAccountRepository` with optional filter parameters
- [ ] 🔲 TASK-102: Add filter form to `bank-accounts/list.html`

### Data Quality
- [ ] 🔲 TASK-110: Add sorting options on list page (by name, bank, status, openedAt)
- [ ] 🔲 TASK-111: Add pagination to list page for large datasets

### Lifecycle
- [ ] 🔲 TASK-120: Implement soft-delete (add `deletedAt` field; exclude soft-deleted from all queries)
- [ ] 🔲 TASK-121: Add "Suspend" quick-action button on list/detail pages

### Audit and Compliance
- [ ] 🔲 TASK-130: Allow `createdBy` and `updatedBy` to be populated from logged-in user (Spring Security integration)
- [ ] 🔲 TASK-131: Add assessment history log (append-only, linked to bank account)

### Testing
- [ ] 🔲 TASK-140: Write unit tests for `BankAccountService` (IBAN validation, checksum, uniqueness, closedAt rule)
- [ ] 🔲 TASK-141: Write `@WebMvcTest` tests for `BankAccountController` (form submission, validation errors, redirect on success)
- [ ] 🔲 TASK-142: Write integration test for full create → read → update → delete cycle

