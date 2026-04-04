# Bank Account — User Stories

**Version**: 1.0  
**Date**: 2026-04-04

---

## Personas

| Persona | Role |
|---------|------|
| **Financial Analyst** | Records and reviews bank account information for assessment purposes |
| **Compliance Officer** | Audits records for regulatory compliance |
| **System / Data Agent** | Automated pipeline that creates or updates records programmatically |

---

## Epic: Bank Account Record Management

---

### US-001 — Create a Bank Account Record

**As a** Financial Analyst,  
**I want to** create a new bank account record with all required fields,  
**So that** the account is available for assessment and reporting.

**Acceptance Criteria**:
- [ ] The form is accessible at `/bank-accounts/new`
- [ ] Mandatory fields (name, accountType, bankName, iban, currency, status, ownershipType) must be filled
- [ ] IBAN is validated for format and checksum before saving
- [ ] SWIFT/BIC is validated for format if provided
- [ ] Duplicate IBAN is rejected with a clear error message
- [ ] Upon success, the user is redirected to the bank accounts list
- [ ] Default values pre-filled on new form: status=ACTIVE, ownershipType=INDIVIDUAL, accountType=CURRENT, currency=EUR

---

### US-002 — View Bank Account List

**As a** Financial Analyst,  
**I want to** see a list of all bank accounts with key fields,  
**So that** I can quickly scan and navigate to the one I need.

**Acceptance Criteria**:
- [ ] List is accessible at `/bank-accounts`
- [ ] Columns displayed: Name, IBAN (masked), Bank Name, Account Type, Currency, Status, Ownership Type, Opened At
- [ ] IBAN shows only the last 4 characters masked (e.g., `***********1234`)
- [ ] Each row has View, Edit, and Delete actions
- [ ] Empty state shows a "No bank accounts found." message

---

### US-003 — View Bank Account Detail

**As a** Financial Analyst,  
**I want to** view the full details of a single bank account,  
**So that** I can review all recorded information including the full IBAN.

**Acceptance Criteria**:
- [ ] Detail page accessible at `/bank-accounts/{id}`
- [ ] All fields are displayed (including full IBAN, unlike list page)
- [ ] Audit fields (createdAt, createdBy, updatedAt, updatedBy) are shown
- [ ] Page links back to the list and to the edit form

---

### US-004 — Edit a Bank Account Record

**As a** Financial Analyst,  
**I want to** update an existing bank account record,  
**So that** I can correct data or reflect changes in the account's state.

**Acceptance Criteria**:
- [ ] Edit form accessible at `/bank-accounts/{id}/edit`
- [ ] All editable fields are pre-populated from the existing record
- [ ] Audit fields (createdAt, createdBy) are read-only
- [ ] Validation re-runs on save; errors are shown inline or as global messages
- [ ] IBAN uniqueness check excludes the current record
- [ ] Local account uniqueness check excludes the current record
- [ ] `closedAt` field is hidden when status ≠ CLOSED
- [ ] Upon success, the user is redirected to the list

---

### US-005 — Close a Bank Account

**As a** Financial Analyst,  
**I want to** set a bank account status to CLOSED and record the closing date,  
**So that** the account history is preserved but the account is clearly inactive.

**Acceptance Criteria**:
- [ ] User changes status to `CLOSED` in the edit form
- [ ] The `closedAt` date field becomes visible when CLOSED is selected
- [ ] `closedAt` is required to be on or after `openedAt` if both are provided
- [ ] `closedAt` is automatically cleared if the user changes status away from `CLOSED`
- [ ] The closed date is persisted and visible on the detail page

---

### US-006 — Delete a Bank Account Record

**As a** Financial Analyst,  
**I want to** delete a bank account record,  
**So that** obsolete or erroneously created records are removed from the system.

**Acceptance Criteria**:
- [ ] Delete action is available on each row in the list view
- [ ] Deletion is performed via `POST /bank-accounts/{id}/delete`
- [ ] After deletion, the user is redirected to the list
- [ ] Deleted record no longer appears in the list

---

### US-007 — Prevent Storage of Sensitive Financial Data

**As a** Compliance Officer,  
**I want to** ensure that card numbers, PINs, CVV codes, and credentials are never stored,  
**So that** the system complies with GDPR and PCI-DSS requirements.

**Acceptance Criteria**:
- [ ] No field for card number, CVV, PIN, or credentials exists in the data model or form
- [ ] No transaction history fields exist in the data model
- [ ] The compliance document clearly lists prohibited fields

---

### US-008 — Automated Record Creation by Data Agent

**As a** System / Data Agent,  
**I want to** submit bank account data via the web form endpoint,  
**So that** records can be created programmatically without manual UI interaction.

**Acceptance Criteria**:
- [ ] `POST /bank-accounts` accepts `application/x-www-form-urlencoded` with all required fields
- [ ] Response is a redirect (`302`) on success or a re-rendered form on validation failure
- [ ] All validation rules apply equally to programmatic and manual submissions

---

### US-009 — Record Assessment and Compliance Metadata

**As a** Compliance Officer,  
**I want to** record an assessment score, verification status, and last review date for each account,  
**So that** the compliance workflow has a structured place to track evaluation outcomes.

**Acceptance Criteria**:
- [ ] `assessmentScore`, `verificationStatus`, and `lastReviewDate` fields are editable on the form
- [ ] All three fields are visible on the detail page
- [ ] Fields are optional and do not block saving when empty

