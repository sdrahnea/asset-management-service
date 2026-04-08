# Copyright — Compliance Notes

**Version**: 1.0 | **Date**: 2026-04-04

---

## 1. Regulatory Framework

This service operates in the context of international and regional intellectual property, copyright, and financial regulations.

### Applicable Frameworks

| Framework | Scope | Impact |
|-----------|-------|--------|
| **Berne Convention** | International copyright protection (150+ countries) | Minimum rights & term of protection standards |
| **TRIPS Agreement** | WTO trade-related IP rights | Enforcement mechanisms, minimum standards globally |
| **EU Copyright Directive 2006/115/EC** | Rental & lending rights | Applies to EU-based users, term extensions (life + 70 years) |
| **EU Digital Single Market Directive 2019/790** | Modern copyright rules | Applies to EU; exceptions for text/data mining, cloud backup |
| **GDPR** (EU 2016/679) | Personal data processing | Author, owner, reviewer names are personal data |
| **National IP Offices** | Local registration & enforcement | OSIM (Romania), EUIPO, USPTO (US), WIPO (international) |
| **Local Tax Laws** | IP asset valuation and royalty taxation | Tax implications of IP transfers, licensing |
| **Bankruptcy & IP Transfer Rules** | IP asset treatment in insolvency | Copyright ownership in corporate restructuring |

---

## 2. Data Classification

| Field | Classification | Reason |
|-------|----------------|--------|
| `copyrightId` | Internal Reference | Identifier within system, not tied to individual |
| `title` | Potentially Personal | Title may identify a work linked to natural person |
| `workType` | Non-sensitive | Standard classification |
| `descriptionAbstract` | Potentially Sensitive | May contain proprietary or confidential information |
| `versionEdition` | Non-sensitive | Standard operational field |
| `creationDate`, `publicationReleaseDate` | Non-sensitive | Public information typically |
| `authors` | Personal Data (GDPR Art. 4) | Identifies person(s) as creators; may be public or private |
| `copyrightOwners` | Personal Data (GDPR Art. 4) | Identifies owner; may be corporation or individual |
| `ownershipPercentage` | Sensitive Business Data | Reveals ownership structure |
| `moralRightsHolder` | Personal Data (GDPR Art. 4) | Name of person with moral rights |
| `countryOfOrigin` | Non-sensitive | Geographic classification |
| `copyrightNotice` | Non-sensitive | Public copyright statement |
| `registrationNumber` | Semi-sensitive | Public record but identifies specific work |
| `registrationAuthority` | Non-sensitive | Office name |
| `registrationDate` | Non-sensitive | Public information |
| `copyrightStatus` | Non-sensitive | Status classification |
| `protectionStartDate`, `protectionEndDate` | Non-sensitive | Legal dates (often public) |
| `jurisdictionsCovered` | Non-sensitive | Geographic scope |
| `rightsGranted`, `rightsReserved` | Sensitive | Reveals IP strategy and ownership boundaries |
| `usageRestrictions` | Sensitive | Business confidential |
| `exclusivityType` | Sensitive Business Data | Reveals licensing model |
| `derivativeWorksAllowed` | Sensitive Business Data | IP policy |
| `drmProtectionMeasures` | Sensitive Technical Data | Reveals security/protection strategy |
| `licenseType` | Sensitive Business Data | Reveals business model |
| `licensees` | Potentially Personal | Names of companies/individuals with rights |
| `licenseStartDate`, `licenseEndDate` | Sensitive Business Data | Reveals business relationships |
| `permittedUses` | Sensitive Business Data | Licensed rights are confidential |
| `royaltyTerms`, `royaltyRate` | Highly Sensitive Business Data | Financial terms confidential |
| `contractReferences` | Sensitive Business Data | References to contracts (often confidential) |
| `revenueGenerated`, `advancePayments`, `licensingFees` | Sensitive Financial Data | Revenue information confidential |
| `valuation` | Sensitive Financial Data | Asset valuation typically confidential |
| `infringementRisk`, `ownershipDisputesRisk`, `expirationRisk` | Internal Assessment | Risk scores based on internal analysis |
| `complianceFlags` | Sensitive | Legal/compliance issues |
| `marketRelevanceScore`, `portfolioImpactScore` | Internal Assessment | Internal scoring |
| `sourceOfInformation` | Non-sensitive | Operational metadata |
| `responsibleReviewer` | Personal Data (GDPR Art. 4) | Name/ID of responsible person |
| `documentReferences` | Potentially Sensitive | May reference confidential contracts |
| `tagsCategories` | Internal Classification | System labels |
| `notes` | Potentially Sensitive | May contain business or personal information |

---

## 3. Prohibited Data (Never Store)

The following data types are explicitly excluded from the `Copyright` model
and must never be added without formal legal and security review:

| Data Type | Risk | Applicable Framework |
|-----------|------|---------------------|
| Full legal contract text | Size/confidentiality; storage burden | Data minimization (GDPR Art. 5) |
| Personal identification numbers (SSN, ID card number) | PII; regulatory violation | GDPR Art. 9 |
| Banking/payment details for royalty recipients | PCI-DSS or equivalent; compliance burden | Financial regulations |
| Detailed litigation/dispute case details | Legally privileged; confidential | Local litigation privilege laws |
| Email/phone of authors/licensees without consent | Personal contact data without legal basis | GDPR Art. 5, 6 |
| Biometric data | Special category under GDPR | GDPR Art. 9 |
| Trade secrets in plaintext | Competitive advantage; breach risk | Corporate confidentiality laws |

---

## 4. Data Minimization

Per **GDPR Article 5(1)(c)** — "data minimisation" principle:

> Personal data shall be adequate, relevant, and limited to what is necessary in relation to
> the purposes for which they are processed.

Applied to Copyright entity:
- Only author/owner **names** (not ID numbers, addresses, contact details)
- Registration **number** (not full registration certificate content)
- Contract **reference** or **summary** (not full contract text)
- Royalty **summary** (not detailed ledger)
- **Risk assessment** (not detailed investigation)
- **Compliance flags** (not full compliance documentation)

---

## 5. Sensitive Data Handling

### Copyright Notice & Registration Data
- Registration number and status are typically **public** (office records)
- Registration date and jurisdiction information are **public**
- Full copyright registration certificate (if scanned) should be linked, not embedded

### Rights & Licensing Information
- **Restrictive**: Usage restrictions are business confidential
- **Restrictive**: License agreements should be referenced (link/ID), not stored in full
- **Confidential**: Royalty terms are business sensitive
- **Confidential**: Licensees and permitted uses reveal business relationships

### Financial Information
- **Highly confidential**: Valuation, revenue, advance payments, licensing fees
- Access should be restricted to finance/management roles (recommend role-based access in v2)
- No export to non-confidential formats without approval

### Author/Owner Information
- **Personal data**: Author and copyright owner names
- **Basis**: Necessary for IP ownership verification and legal assessment
- **Safeguard**: Only store what's necessary (typically from public registration or contract)
- **Rights**: If individuals are identified, GDPR subject rights apply (access, erasure, rectification)

---

## 6. Intellectual Property Rights

### Copyright Ownership in the System
The Copyright entity itself describes IP owned by **others** (the copyrights being tracked).
The organization using this system is the **assessor**, not necessarily the owner.

- Data stored is for **assessment**, **licensing management**, **portfolio tracking**
- Copyright owners retain all rights to their works
- System provides **metadata** only, not distribution

### DRM & Technical Protection Measures
- Store DRM **configuration** or **presence** (not encryption keys or bypass information)
- References to DRM implementation are for rights management only
- Compliance with DMCA (US) or similar laws: system does not facilitate circumvention

---

## 7. Geographic & Jurisdictional Considerations

### Multi-Jurisdiction Copyright Complexity
- Berne Convention provides minimum protection (life + 50 years typically)
- EU extends to life + 70 years
- Some countries differ (term, exceptions, moral rights)
- System tracks jurisdiction coverage but cannot enforce across borders

### Data Transfer (if service uses cloud or multi-region)
- Copyright data may be sensitive/confidential
- Transfers outside origin country may require legal review
- **Recommendation for v2**: Implement data residency controls per jurisdiction

---

## 8. GDPR Subject Rights Impact

If author/owner/reviewer names refer to natural persons, the following GDPR rights apply:

| Right | Impact on Copyright Entity |
|-------|---------------------------|
| Right of access (Art. 15) | User can request all records where they are author/owner/reviewer |
| Right to erasure (Art. 17) | Deleted records must be permanently removed; hard-delete currently implemented |
| Right to rectification (Art. 16) | Author/owner/reviewer names can be updated via edit form |
| Right to data portability (Art. 20) | Export capability currently not implemented (recommend for v2) |
| Right to object (Art. 21) | Not currently supported; recommend adding opt-out mechanism for v2 |

---

## 9. Copyright Term & Expiration Tracking

### Importance for Compliance
- Copyright terms vary by jurisdiction (life + 50 to 70+ years)
- **expiration** marks transition to public domain
- Public domain works can be used freely (with exceptions per jurisdiction)
- System tracks `protectionEndDate` for monitoring

### Risk Indicators
- `expirationRisk` field flags copyrights approaching end of protection term
- Planned Phase 2: automated alerts for approaching expiration dates

---

## 10. Compliance Flags & Licensing

### AML/Compliance Considerations
- `complianceFlags` field captures legal/regulatory issues
- Examples: "Disputed ownership", "License terms violation", "Unpaid royalties"
- **Not a detailed investigation log** – summary flags only
- Detailed disputes/investigations should be in separate legal/compliance system

### License Compliance
- Track permitted uses vs. actual usage (recommend Phase 3 feature)
- Monitor license expiration dates
- Flag non-compliance automatically (recommend Phase 3)

---

## 11. Access Control (Current State and Recommendations)

| Control | Current State | Recommendation |
|---------|---------------|----------------|
| Authentication | ❌ Not implemented | Add Spring Security with role-based access |
| Authorization | ❌ Not implemented | Restrict view/create/edit/delete to authorized roles |
| Audit logging | ⚠️ Partial (`createdBy`/`updatedBy` stored, default to `"system"`) | Integrate with authenticated user principal |
| Sensitive data masking | ❌ Not implemented | Mask financial fields in list view; full access on detail page |
| Financial data access | ❌ Not restricted | Restrict to finance/management roles (v2) |

---

## 12. Data Retention

No automated data retention policy is currently implemented.

### Recommendations

**For active copyrights:**
- Retain for duration of protection + 1 year (often indefinite for works in protection)

**For expired/public domain works:**
- Retain for 3–7 years per organizational policy
- May continue retaining as historical/portfolio reference indefinitely

**For deleted records:**
- Currently hard-delete (permanent removal)
- **Recommendation for v2**: Implement soft-delete with 7-year archive period

---

## 13. Incident Response & Breach Notification

No automated incident response plan is currently in place.

### Recommendations

**For data breaches involving:**
- **Personal data** (author, owner, reviewer names): Notify supervisory authority within 72 hours (GDPR Art. 33) if breach likely to cause harm
- **Confidential business data** (valuation, royalty terms): Notify organization leadership and legal team immediately
- **Compliance flags**: Escalate to compliance/legal team

**Steps**:
1. Isolate affected records
2. Assess impact (scope, sensitivity)
3. Notify relevant authorities and users
4. Implement corrective measures
5. Document incident with timeline and root cause

---

## 14. Encryption & Security Best Practices

### At-Rest Encryption
- Currently: Infrastructure-level (database encrypted by hosting provider)
- **Recommended v2**: Column-level encryption for valuation, royalty terms, revenue data

### In-Transit Encryption
- HTTPS enforced for all HTTP routes (external layer)

### Key Management
- Keys stored securely; rotation every 90 days (recommended policy)

### Masking & Anonymization
- Financial fields (valuation, revenue, fees) masked in list view
- Full financial data visible only on detail page (with role-based access in v2)
- Royalty rate masked/partially obscured in list

---

## 15. Copyright Registration Office Integration (Phase 3)

When integrating with copyright/IP offices (OSIM, EUIPO, USPTO, WIPO):

- **Data validation**: Verify registration numbers with official offices
- **Automated sync**: Pull registration status, dates, jurisdictions
- **API security**: Use OAuth 2.0 or similar for office API access
- **Data freshness**: Cache office data with TTL; don't store raw responses
- **Compliance**: Ensure office APIs comply with data protection standards

---

## 16. Compliance Checklist (v1 Assessment)

| Requirement | Status | Notes |
|-------------|--------|-------|
| GDPR Art. 5 (lawfulness, fairness, transparency) | ✅ Compliant | Data stored for stated purpose (IP assessment) |
| GDPR Art. 5 (purpose limitation) | ✅ Compliant | Data used for IP/copyright tracking; no secondary use |
| GDPR Art. 5 (data minimization) | ✅ Compliant | Only necessary fields collected |
| GDPR Art. 5 (integrity & confidentiality) | ⚠️ Partial | Infrastructure-level encryption; column-level encryption recommended |
| GDPR Art. 6 (lawful basis) | ⚠️ Unclear | Basis (consent, contract, legal obligation) not documented; clarify in privacy notice |
| GDPR Art. 13/14 (transparency) | ⚠️ Partial | Privacy notice needed when data collected from individuals |
| GDPR Art. 32 (security measures) | ⚠️ Partial | Access control, audit logging not fully implemented |
| GDPR Art. 33/34 (breach notification) | ❌ Not implemented | Incident response plan needed |
| Berne Convention compliance | ✅ Applicable | System accommodates international copyright standards |
| TRIPS compliance | ✅ Applicable | System supports global IP tracking |
| EU Copyright Directive 2006/115/EC | ✅ Applicable | Rental/lending rights tracked if applicable |
| Local IP Office compliance | ✅ Applicable | Registration numbers and authority tracked |

---

## 17. Next Steps

**For v1.0 (Current)**:
- ✅ Data model and field definitions in place
- ✅ Copyright registration metadata fields included
- ⚠️ Add privacy notice/documentation for data subjects

**For v2.0 (Recommended)**:
- [ ] Implement Spring Security for authentication/authorization
- [ ] Add role-based access (IP Management, Finance, Legal)
- [ ] Column-level encryption for financial & sensitive fields
- [ ] Soft-delete with 7-year retention policy
- [ ] API-level masking for sensitive data
- [ ] Field-level audit trail
- [ ] GDPR data export functionality

**For v3.0+ (Optional)**:
- [ ] IP office API integration (OSIM, EUIPO, USPTO)
- [ ] Copyright expiration monitoring & alerts
- [ ] Automated royalty calculation
- [ ] License compliance tracking & reporting
- [ ] Portfolio risk scoring based on expiration, disputes, valuation
- [ ] Data Processing Agreement templates for external IP office use

---

## 18. Key Takeaways

1. **Copyright data is complex & sensitive** – Ownership, rights, valuation are often confidential
2. **Multiple jurisdictions apply** – Berne Convention, TRIPS, EU directives, local law
3. **Personal data considerations** – Author/owner names trigger GDPR rules
4. **Financial confidentiality** – Royalty rates and valuation are business-sensitive
5. **Public records exist** – Registration numbers, dates, status are often public
6. **Expiration matters** – Copyright terms vary; tracking is critical
7. **Licensing requires care** – License agreements reveal business relationships & terms
8. **No authentication yet** – v2 should implement role-based access control

**Recommendation**: Use v2 security enhancements (encryption, access control, audit trail) before exposing this system to external users or managing high-value copyrights.

