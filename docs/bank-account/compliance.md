# Bank Account — Compliance Notes

**Version**: 1.0  
**Date**: 2026-04-04

---

## 1. Regulatory Framework

This service operates in the context of Romanian and Moldovan financial regulation.
The following frameworks apply to the Bank Account module.

| Framework | Scope | Impact |
|-----------|-------|--------|
| **GDPR** (EU 2016/679) | Personal data processing | Account holder name, ownership data are personal data |
| **PSD2** (EU 2015/2366) | Payment services and bank account access | Applies if this service ever requests account data from banks via API |
| **PCI-DSS** | Payment card data | Applies only if card data is stored — currently out of scope |
| **BNR Regulations** (Banca Națională a României) | Banking secrecy, data handling | IBAN and account data treated as confidential |
| **BNM Regulations** (Banca Națională a Moldovei) | Banking secrecy | Same as above for Moldovan entities |

---

## 2. Data Classification

| Field | Classification | Reason |
|-------|----------------|--------|
| `name` (holder name) | Personal Data (GDPR Art. 4) | Identifies a natural or legal person |
| `iban` | Confidential Financial Data | Bank account identifier — sensitive under BNR/BNM |
| `localAccountNumber` | Confidential Financial Data | Domestic bank identifier |
| `swiftCode` | Internal Reference | Identifies bank, not individual — lower sensitivity |
| `currency` | Non-sensitive | Public information |
| `status`, `accountType`, `ownershipType` | Internal Classification | Non-sensitive operational metadata |
| `assessmentScore`, `verificationStatus` | Internal Assessment | Not personal data; internal scoring only |
| `linkedEntity` | Reference Data | May link to a person or company — review if personal |

---

## 3. Prohibited Data (Never Store)

The following data types are explicitly excluded from the `BankAccount` model
and must never be added without formal legal and security review:

| Data Type | Risk | Applicable Framework |
|-----------|------|---------------------|
| Full debit/credit card numbers | PCI-DSS Level 1 violation | PCI-DSS |
| CVV / CVC codes | Card fraud risk; PCI-DSS prohibition | PCI-DSS |
| PIN codes | Authentication credential | PCI-DSS, GDPR |
| Online banking passwords / tokens | Authentication credential | GDPR, PSD2 |
| Full transaction history | Regulatory data minimization requirement | GDPR Art. 5(1)(c) |
| Identity documents (ID card, passport scans) | Special category under GDPR | GDPR Art. 9 |

---

## 4. Data Minimization

Per **GDPR Article 5(1)(c)** — "data minimisation" principle:

> Personal data shall be adequate, relevant, and limited to what is necessary in relation to
> the purposes for which they are processed.

Applied to Bank Account:
- Only the holder name (not date of birth, address, national ID) is stored.
- IBAN is the minimum necessary account identifier.
- No transaction history, balances, or balance snapshots are stored.
- `assessmentScore` is an internal operational score, not a credit score derived from external bureau data.

---

## 5. IBAN Security Practices

- IBAN is **masked** in list views (only last 4 characters visible).
- Full IBAN is shown **only on the detail page** for authorized users.
- IBAN is **normalized** (uppercase, no spaces) before storage to prevent duplicates arising from format differences.
- No encryption at-rest is currently applied at the application layer (handled by infrastructure).

> **Recommendation for v2**: Apply column-level encryption to `iban` and `localAccountNumber`
> if the service handles accounts from regulated entities.

---

## 6. Access Control (Current State and Recommendations)

| Control | Current State | Recommendation |
|---------|---------------|----------------|
| Authentication | ❌ Not implemented | Add Spring Security with role-based access |
| Authorization | ❌ Not implemented | Restrict create/edit/delete to authorized roles |
| Audit logging | ⚠️ Partial (`createdBy`/`updatedBy` are stored but default to `"system"`) | Integrate with authenticated user principal |
| IBAN full display | ⚠️ Restricted by UI only (not API-level) | Add API-level masking or role guard for full IBAN |

---

## 7. Data Retention

No automated data retention policy is currently implemented.

> **Recommendation**: Define a retention period for closed accounts (e.g., 7 years per
> Romanian financial archiving law) and implement a scheduled cleanup or archiving process.

---

## 8. GDPR Subject Rights Impact

If account holder names refer to natural persons, the following GDPR rights apply:

| Right | Impact on this feature |
|-------|----------------------|
| Right of access (Art. 15) | User can request all records where they are the holder |
| Right to erasure (Art. 17) | Deleted records must be permanently removed; no soft-delete currently |
| Right to rectification (Art. 16) | Holder name and other personal fields can be updated via edit form |
| Right to data portability (Art. 20) | Export capability is currently not implemented (planned) |

