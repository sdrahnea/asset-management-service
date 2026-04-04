# Bank Account — Data Model Specification

**Version**: 1.0  
**Date**: 2026-04-04  
**Entity class**: `com.sdr.ams.model.financial.BankAccount`  
**Table**: `bank_account`

---

## 1. Inheritance

`BankAccount` extends `CoreEntity`, which provides:

| Field | Column | Type | Notes |
|-------|--------|------|-------|
| `id` | `id` | `BIGINT` (PK, auto-increment) | Internal surrogate key |
| `name` | `name` | `VARCHAR(255)` NOT NULL | Represents account holder name |
| `createdAt` | `created_at` | `TIMESTAMP` NOT NULL, immutable | Set on first persist |
| `createdBy` | `created_by` | `VARCHAR(100)` NOT NULL, immutable | Defaults to `"system"` |
| `updatedAt` | `updated_at` | `TIMESTAMP` NOT NULL | Updated on every save |
| `updatedBy` | `updated_by` | `VARCHAR(100)` NOT NULL | Defaults to `"system"` |

---

## 2. Bank Account–Specific Fields

### 2.1 Identification

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `accountType` | `account_type` | `ENUM` | ✅ | NOT NULL | Values: `CURRENT`, `SAVINGS`, `CORPORATE`, `ESCROW`, `DEPOSIT`, `OTHER` |
| `bankName` | `bank_name` | `VARCHAR(200)` | ✅ | NOT NULL | Name of the financial institution |
| `branchName` | `branch_name` | `VARCHAR(200)` | ❌ | nullable | Branch name or code |

### 2.2 Account Numbering

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `iban` | `iban` | `VARCHAR(34)` | ✅ | NOT NULL, UNIQUE | ISO 13616 format; stored uppercase, no spaces |
| `swiftCode` | `swift_code` | `VARCHAR(11)` | ❌ | nullable | SWIFT/BIC code; stored uppercase, no spaces |
| `localAccountNumber` | `local_account_number` | `VARCHAR(64)` | ❌ | nullable; UNIQUE per `bank_name + branch_name` | Domestic account number |

### 2.3 Financial Attributes

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `currency` | `currency` | `VARCHAR(3)` | ✅ | NOT NULL | ISO 4217, 3-letter, stored uppercase |
| `status` | `status` | `ENUM` | ✅ | NOT NULL | Values: `ACTIVE`, `CLOSED`, `SUSPENDED` |
| `ownershipType` | `ownership_type` | `ENUM` | ✅ | NOT NULL | Values: `INDIVIDUAL`, `JOINT`, `CORPORATE` |

### 2.4 Lifecycle Dates

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `openedAt` | `opened_at` | `DATE` | ❌ | nullable | Date the account was opened |
| `closedAt` | `closed_at` | `DATE` | ❌ | nullable; only set when `status = CLOSED` | `closedAt >= openedAt` when both present |

### 2.5 Context and Assessment

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `accountPurpose` | `account_purpose` | `VARCHAR(255)` | ❌ | nullable | E.g., salary, escrow, business operations |
| `assessmentScore` | `assessment_score` | `DECIMAL(12,2)` | ❌ | nullable | Internal risk or health score |
| `verificationStatus` | `verification_status` | `VARCHAR(50)` | ❌ | nullable | Free-text verification state |
| `lastReviewDate` | `last_review_date` | `DATE` | ❌ | nullable | Date of the most recent review |
| `linkedEntity` | `linked_entity` | `VARCHAR(255)` | ❌ | nullable | Reference to a company, project, or contract |

---

## 3. Enums

### AccountType
```
CURRENT     — Standard current/checking account
SAVINGS     — Savings account
CORPORATE   — Business/corporate account
ESCROW      — Escrow/trust account
DEPOSIT     — Term deposit account
OTHER       — Any other type
```

### Status
```
ACTIVE      — Account is open and operational
CLOSED      — Account is permanently closed
SUSPENDED   — Account is temporarily suspended
```

### OwnershipType
```
INDIVIDUAL  — Owned by a single person
JOINT       — Shared ownership between two or more persons
CORPORATE   — Owned by a legal entity / company
```

---

## 4. Unique Constraints

| Constraint name | Columns | Rule |
|-----------------|---------|------|
| `uk_bank_account_iban` | `iban` | Globally unique |
| `uk_bank_account_local_number` | `local_account_number`, `bank_name`, `branch_name` | Unique per bank+branch combination |

---

## 5. Derived / Computed Attributes

| Attribute | Source | Notes |
|-----------|--------|-------|
| `maskedIban` | Computed from `iban` | All characters replaced with `*` except last 4; used on list page |

---

## 6. Normalization Rules (applied before validation in service)

| Field | Normalization |
|-------|--------------|
| `name` | Trim whitespace; empty → null |
| `bankName` | Trim whitespace; empty → null |
| `branchName` | Trim whitespace; empty → null |
| `localAccountNumber` | Trim whitespace; empty → null |
| `accountPurpose` | Trim whitespace; empty → null |
| `verificationStatus` | Trim whitespace; empty → null |
| `linkedEntity` | Trim whitespace; empty → null |
| `iban` | Trim + remove all internal whitespace + uppercase |
| `swiftCode` | Trim + remove all internal whitespace + uppercase |
| `currency` | Trim + remove all internal whitespace + uppercase |
| `closedAt` | Force null if `status ≠ CLOSED` |

---

## 7. Prohibited Fields (must never be added)

The following fields are explicitly excluded from the data model
for compliance and security reasons:

- Card numbers (debit or credit)
- CVV / CVC codes
- PIN codes
- Online banking credentials or tokens
- Full transaction history
- Customer identity documents (ID card, passport)

