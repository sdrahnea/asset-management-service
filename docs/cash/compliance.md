# Cash — Compliance Notes

**Version**: 1.0 | **Date**: 2026-04-04

---

## 1. Regulatory Framework

This service operates in the context of Romanian and Moldovan financial regulation.
The following frameworks apply to the Cash module.

| Framework | Scope | Impact |
|-----------|-------|--------|
| **GDPR** (EU 2016/679) | Personal data processing | Holder name, responsible analyst, ownership data are personal data |
| **PSD2** (EU 2015/2366) | Payment services and bank account access | Applies if this service requests account/holding data from banks via API |
| **BNR Regulations** (Banca Națională a României) | Banking secrecy, cash position reporting | Applies to Romanian entities holding or managing cash |
| **BNM Regulations** (Banca Națională a Moldovei) | Banking secrecy, cash position reporting | Applies to Moldovan entities |
| **AML/CFT** (EU Directive 2015/849) | Anti-money laundering, counter-financing of terrorism | Compliance flags field used to track AML/KYC issues |

---

## 2. Data Classification

| Field | Classification | Reason |
|-------|----------------|--------|
| `cashId` | Internal Reference | Identifier within assessment system, not tied to individual |
| `amount` | Sensitive Financial Data | Cash position amount is confidential |
| `currency` | Non-sensitive | Standard operational field |
| `valuationDate` | Non-sensitive | Operational timestamp |
| `cashType` | Internal Classification | System categorization |
| `holder` | Personal Data (GDPR Art. 4) | Identifies the person or entity holding the cash |
| `holdingAccountReference` | Confidential Financial Data | Bank/payment provider reference is sensitive under BNR/BNM |
| `institution` | Internal Reference | Name of bank/custodian; lower sensitivity than account number |
| `jurisdiction` | Non-sensitive | Country classification for tax/compliance purposes |
| `openingBalance`, `closingBalance` | Sensitive Financial Data | Cash position snapshots |
| `cashInflows`, `cashOutflows`, `netCashMovement` | Sensitive Financial Data | Detailed movement data |
| `cashForecast` | Internal Assessment | Forward-looking narrative or structured forecast |
| `reconciliationStatus` | Internal Operational | Tracking and control status |
| `counterpartyRisk` | Internal Risk Score | Assessment score tied to the holding institution |
| `concentrationRisk` | Internal Assessment | Narrative risk analysis |
| `volatility` | Internal Assessment | Operational volatility narrative |
| `complianceFlags` | Special Category | AML/KYC issues, transaction irregularities — highly sensitive |
| `dataSource` | Internal Operational | How data entered the system |
| `responsibleAnalyst` | Personal Data (GDPR Art. 4) | Name or ID of responsible person |
| `tags` | Internal Classification | System labels |
| `notes` | Mixed | May contain personal or sensitive information depending on content |

---

## 3. Prohibited Data (Never Store)

The following data types are explicitly excluded from the `Cash` model
and must never be added without formal legal and security review:

| Data Type | Risk | Applicable Framework |
|-----------|------|---------------------|
| Full bank account numbers (not IBAN-equivalent) | PSD2, BNR confidentiality | PSD2, BNR |
| PIN codes or online banking credentials | Authentication credential | GDPR, PSD2 |
| Full transaction history (detailed ledger) | Data minimization violation | GDPR Art. 5(1)(c) |
| Credit card numbers | PCI-DSS violation | PCI-DSS |
| CVV / CVC codes | Card fraud risk | PCI-DSS |
| Identity documents (ID card, passport scans) | Special category under GDPR | GDPR Art. 9 |
| Biometric data | Special category | GDPR Art. 9 |
| Detailed AML investigation records | Legally privileged | Depends on local regulation |

---

## 4. Data Minimization

Per **GDPR Article 5(1)(c)** — "data minimisation" principle:

> Personal data shall be adequate, relevant, and limited to what is necessary in relation to
> the purposes for which they are processed.

Applied to Cash entity:
- Only the holder **name** (not ID number, address, contact details) is stored for identification
- Account reference is the minimum necessary identifier for the holding location
- No transaction history, balance change ledger, or raw bank feed data is stored
- `complianceFlags` is a **summary flag** (e.g., "AML review pending") not a detailed investigation log
- `responsibleAnalyst` identifies the accountable person, not the full audit trail

---

## 5. Account Reference Security Practices

- Account references (`holdingAccountReference`) in list views **masked** when longer than 20 chars
  - Display format: `XXXXXX***...XXXX` (first 6, last 4 visible)
- Full account reference shown **only on detail pages** for authorized users
- References are **normalized** (uppercase, spaces removed) to prevent duplicates
- No encryption at-rest is currently applied at the application layer (handled by infrastructure)

> **Recommendation for v2**: Apply column-level encryption to `holdingAccountReference`
> if the service handles accounts from regulated financial entities.

---

## 6. Personal Data Handling

### Fields Containing Personal Data
- `holder` – May be a natural person (individual) or legal entity (company)
- `responsibleAnalyst` – Natural person responsible for the record

### GDPR Principles Applied

| Principle | Application |
|-----------|------------|
| **Lawfulness, fairness, transparency** | Data collected only for asset assessment; users informed via privacy notice |
| **Purpose limitation** | Data used only for cash position tracking and asset assessment; not for marketing |
| **Data minimization** | Only name, not ID/address/contact details |
| **Accuracy** | Stored as provided; users responsible for accuracy |
| **Storage limitation** | No automated deletion; retention per organizational policy |
| **Integrity and confidentiality** | Access logs; infrastructure-level encryption |

---

## 7. Compliance Flags (AML/KYC)

The `complianceFlags` field is used to flag cash positions with AML/KYC issues:
- Format: Free-form text, up to 1000 characters
- Examples: `"AML review pending"`, `"Unusual transaction pattern"`, `"Jurisdiction review required"`
- **Not a detailed investigation log** – summarizes key issues only
- Access control: Should be restricted to compliance officers (currently not enforced; recommend for v2)

---

## 8. Access Control (Current State and Recommendations)

| Control | Current State | Recommendation |
|---------|---------------|----------------|
| Authentication | ❌ Not implemented | Add Spring Security with role-based access |
| Authorization | ❌ Not implemented | Restrict create/edit/delete to authorized roles (e.g., Treasury, Finance) |
| Audit logging | ⚠️ Partial (`createdBy`/`updatedBy` stored, default to `"system"`) | Integrate with authenticated user principal |
| Account ref masking | ⚠️ List template masks references | Add API-level masking for REST endpoints |
| Compliance flags visibility | ❌ No restriction | Restrict to compliance roles in v2 |

---

## 9. Data Retention

No automated data retention policy is currently implemented.

### Recommendations

**For active operating cash:**
- Retain for duration of asset ownership + 1 fiscal year (default: indefinite)

**For closed/deleted cash positions:**
- **7-year retention** per Romanian law (Law 571/2003 on Tax Code) and Moldovan equivalent
- Implement soft-delete with archive flag; purge after 7 years via scheduled job

**For reconciliation disputes:**
- Keep until resolved, then follow general retention; dispute documentation separately retained per legal hold

---

## 10. GDPR Subject Rights Impact

If cash holder names or responsible analyst names refer to natural persons, the following GDPR rights apply:

| Right | Impact on Cash Entity |
|-------|----------------------|
| Right of access (Art. 15) | User can request all records where they are the holder or analyst |
| Right to erasure (Art. 17) | Deleted records must be permanently removed (hard-delete currently implemented; no soft-delete) |
| Right to rectification (Art. 16) | Holder name, analyst, and other fields can be updated via edit form |
| Right to data portability (Art. 20) | Export capability currently not implemented; recommend for v2 |
| Right to object (Art. 21) | Currently not supported; recommend adding opt-out mechanism for v2 |

---

## 11. Data Sharing & Third-Party Integrations

Currently, the Cash module does not share data with external parties.

### Planned Integrations (Phase 2+)
- **Bank API feeds** – Real-time balance/movement data from banks via PSD2-compliant APIs
  - **Impact**: PSD2 consent and authorization required; data processing agreement with bank
  - **Compliance**: Implement API-level encryption and audit logging
  
### Data Processing Agreements (DPA)
When integrating with external services, ensure:
- Written Data Processing Agreement in place
- Clear definition of processor vs. controller roles
- Data transfer safeguards (if outside EU/EEA)
- Sub-processor notifications

---

## 12. Incident Response & Breach Notification

No automated incident response plan is currently in place.

### Recommendations

**For data breaches involving:**
- **Personal data** (holder, analyst names): Notify supervisory authority within 72 hours (GDPR Art. 33) if breach likely to cause harm
- **Confidential financial data**: Notify BNR/BNM and affected entities per banking regulations
- **Compliance flags**: Escalate immediately to compliance/legal team

**Steps**:
1. Isolate affected records
2. Assess impact (scope, sensitivity)
3. Notify relevant authorities and users
4. Implement corrective measures
5. Document incident with timeline and root cause

---

## 13. Encryption & Security Best Practices

### At-Rest Encryption
- Currently: Infrastructure-level (database encrypted by hosting provider)
- Recommended v2: Column-level encryption for `holdingAccountReference`, `complianceFlags`

### In-Transit Encryption
- HTTPS enforced for all HTTP routes (external layer)

### Key Management
- Keys stored securely; rotation every 90 days (recommended policy)

### Masking & Anonymization
- Account references masked in list view
- No full PII export without authorization
- Personal data fields not indexed for search

---

## 14. Audit Trail & Logging

Current state:
- `createdAt`, `createdBy`, `updatedAt`, `updatedBy` stored on entity
- No detailed transaction log (who changed what field, when)

Recommended enhancements:
- [ ] Implement `AuditLog` entity tracking field-level changes
- [ ] Store sensitive data changes (e.g., holder, complianceFlags) in encrypted audit table
- [ ] Enable Spring Data JPA Auditing with authenticated user principal
- [ ] Export audit logs for regulatory review

---

## 15. Compliance Checklist (v1 Assessment)

| Requirement | Status | Notes |
|-------------|--------|-------|
| GDPR Art. 5 (lawfulness, fairness, transparency) | ✅ Compliant | Data stored for stated purpose (asset assessment) |
| GDPR Art. 5 (purpose limitation) | ✅ Compliant | Data used only for cash tracking; no secondary use |
| GDPR Art. 5 (data minimization) | ✅ Compliant | Only necessary fields collected |
| GDPR Art. 5 (integrity & confidentiality) | ⚠️ Partial | Infrastructure-level encryption in place; column-level encryption recommended |
| GDPR Art. 6 (lawful basis) | ⚠️ Unclear | Basis (consent, contract, legal obligation) not documented; clarify in privacy notice |
| GDPR Art. 13/14 (transparency) | ⚠️ Partial | Privacy notice needed when data collected from individuals |
| GDPR Art. 32 (security measures) | ⚠️ Partial | Access control, audit logging not fully implemented |
| GDPR Art. 33/34 (breach notification) | ❌ Not implemented | Incident response plan needed |
| PSD2 (payment service rules) | ✅ N/A | Not applicable in v1 (no real-time bank API integration) |
| BNR Regulations (financial data) | ✅ Compliant | Data stored as confidential per banking standards |
| AML/CFT compliance | ✅ Compliant | Compliance flags field supports AML flagging; no transaction monitoring yet |

---

## 16. Next Steps

**For v1.0 (Current)**:
- ✅ Data model and field definitions in place
- ✅ Compliance fields (`complianceFlags`, `dataSource`, `responsibleAnalyst`) included
- ⚠️ Add privacy notice/documentation for data subjects

**For v2.0 (Recommended)**:
- [ ] Implement Spring Security for authentication/authorization
- [ ] Add role-based access (Treasury, Finance, Compliance)
- [ ] Column-level encryption for sensitive fields
- [ ] Soft-delete with 7-year retention policy
- [ ] API-level masking for account references
- [ ] Field-level audit trail
- [ ] GDPR data export functionality

**For v3.0+ (Optional)**:
- [ ] Bank API integration (PSD2-compliant)
- [ ] Real-time reconciliation monitoring
- [ ] Advanced AML/transaction monitoring
- [ ] Data Processing Agreement templates

