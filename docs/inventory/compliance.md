# Inventory — Compliance Notes

**Version**: 1.0 | **Date**: 2026-04-04

---

## 1. Regulatory Framework

This service operates with awareness of equipment, operational, and financial regulations.

### Applicable Frameworks

| Framework | Scope | Impact |
|-----------|-------|--------|
| **OSHA** (US) | Occupational Safety | Equipment safety classifications, certification requirements |
| **ATEX/IEC** (EU) | Hazardous equipment | Equipment in hazardous areas (explosive atmospheres) |
| **GDPR** (EU 2016/679) | Personal data processing | Responsible analyst names, custodian names |
| **Machinery Directive 2006/42/EC** | Equipment safety | CE marking, conformity assessment |
| **Energy-related Products Directive** | Efficiency standards | Power rating, environmental impact |
| **Hazardous Substances Regulations** | Toxic materials | Equipment containing hazardous materials |
| **Insurance Regulations** | Asset valuation | Insurance value tracking, depreciation |
| **Tax Law** | Financial reporting | Depreciation methods, asset classification |

---

## 2. Data Classification

| Field | Classification | Reason |
|-------|----------------|--------|
| `inventoryId` | Internal Reference | System identifier |
| `category`, `subcategory` | Non-sensitive | Operational classification |
| `manufacturer`, `model` | Non-sensitive | Public product information |
| `serialNumber` | Internal Reference | Asset identifier |
| `yearOfManufacture` | Non-sensitive | Public information |
| `technical specs` | Non-sensitive | Public specifications |
| `currentLocation` | Non-sensitive | Internal facility location |
| `owner` | Potentially Personal | If individual name |
| `custodian` | Personal Data (GDPR Art. 4) | Identifies responsible person |
| `usageType` | Internal Classification | Operational model |
| `conditionStatus` | Internal Assessment | Equipment condition |
| `operatingHours` | Internal Operational | Operating metrics |
| `maintenanceSchedule` | Internal Operational | Maintenance plan |
| `lastMaintenanceDate` | Internal Operational | Service history |
| `maintenanceHistory` | Sensitive | Operational details |
| `warrantyStatus` | Non-sensitive | Contract status |
| `serviceProvider` | Business Reference | Contractor name |
| `purchaseDate` | Non-sensitive | Historical data |
| `purchasePrice` | Sensitive Business Data | Cost information |
| `bookValue` | Sensitive Financial Data | Accounting value |
| `depreciationMethod` | Non-sensitive | Accounting choice |
| `depreciationRate` | Sensitive Financial Data | Rate percentage |
| `residualValue` | Sensitive Financial Data | Salvage value |
| `insuranceValue` | Sensitive Financial Data | Coverage amount |
| `operatingCost`, `maintenanceCost` | Sensitive Financial Data | Expense tracking |
| `certificates` | Non-sensitive | Safety certifications |
| `inspectionReports` | Sensitive | Compliance audit details |
| `operatingManuals` | Non-sensitive | Reference links |
| `permitsLicenses` | Non-sensitive | Regulatory status |
| `regulatoryClassification` | Internal Classification | Compliance category |
| `failureRiskScore` | Internal Assessment | Risk score |
| `safetyRiskScore` | Internal Assessment | Safety risk |
| `operationalCriticality` | Internal Assessment | Operational importance |
| `downtimeHours` | Internal Operational | Outage tracking |
| `utilizationRate` | Internal Operational | Usage percentage |
| `environmentalImpactIndicators` | Internal Assessment | Environmental metrics |
| `complianceFlags` | Sensitive | Regulatory issues |
| `dataSource` | Non-sensitive | Data origin |
| `responsibleAnalyst` | Personal Data (GDPR Art. 4) | Accountable person name |
| `tagsLabels` | Internal Classification | Categorization |
| `notesComments` | Potentially Sensitive | May contain personal or sensitive info |

---

## 3. Prohibited Data (Never Store)

| Data Type | Risk | Applicable Framework |
|-----------|------|---------------------|
| Personal identification numbers (for custodian) | GDPR violation | GDPR Art. 9 |
| Banking/payment details | PCI-DSS burden | Financial regulations |
| Full names of responsible persons without consent | GDPR violation | GDPR Art. 5 |
| Detailed litigation records | Legally privileged | Corporate confidentiality |

---

## 4. Equipment Safety & Compliance

### Safety Certifications
- Store certificate names/references (e.g., "ISO 9001", "CE Mark")
- Do NOT store full certificate documents (link instead)
- OSHA compliance status should be flagged in complianceFlags

### Hazardous Equipment
- ATEX/IEC classification tracked via `regulatoryClassification` enum
- Hazardous material indicator in `regulatoryClassification`
- Material Safety Data Sheets (MSDS) referenced, not embedded

### Maintenance & Inspection
- Maintenance history tracked in `maintenanceHistory` field
- Inspection reports referenced in `inspectionReports` field
- Safety-critical maintenance dates in `lastMaintenanceDate`, `nextMaintenanceDue`

---

## 5. Financial Data Confidentiality

### Sensitive Financial Fields
- **purchasePrice**: Original cost is business-sensitive
- **bookValue**: Accounting value is financial-sensitive
- **depreciationRate**: Tax/accounting strategy is confidential
- **residualValue**: Salvage estimate is financial
- **insuranceValue**: Coverage amount is insurance-sensitive
- **operatingCost, maintenanceCost**: Expense tracking is operational-sensitive

### Masking Recommendations
- Mask financial fields in list view
- Show full values only on detail page with role-based access (Finance role)
- Recommend encryption for financial fields in v2

---

## 6. Depreciation & Accounting

### Supported Methods
- `STRAIGHT_LINE` – Simple, linear depreciation
- `DECLINING_BALANCE` – Accelerated depreciation
- `OTHER` – Custom method (documented in notes)

### Compliance
- Depreciation method per tax authority requirements
- Residual value per tax law
- Book value tracking for financial statements

---

## 7. GDPR Subject Rights

If responsible analyst or custodian names refer to natural persons:

| Right | Impact |
|-------|--------|
| Right of access (Art. 15) | User can request all records where they are analyst/custodian |
| Right to erasure (Art. 17) | Deleted records must be permanently removed |
| Right to rectification (Art. 16) | Names can be updated via edit form |
| Right to data portability (Art. 20) | Export capability not yet implemented (recommend for v2) |

---

## 8. Access Control

| Control | Current State | Recommendation |
|---------|---------------|----------------|
| Authentication | ❌ Not implemented | Add Spring Security |
| Authorization | ❌ Not implemented | Restrict to authorized roles |
| Audit logging | ⚠️ Partial | Integrate with authenticated user |
| Financial data masking | ❌ Not implemented | Restrict to Finance role (v2) |

---

## 9. Data Retention

### Recommendations

**For active equipment**:
- Retain for duration of equipment life + 1 year post-disposal

**For disposed equipment**:
- **Tax/audit purposes**: 7 years retention (per most tax authorities)
- **Warranty/guarantee period**: +5 years post-expiration for dispute resolution

---

## 10. Compliance Checklist (v1)

| Requirement | Status | Notes |
|-------------|--------|-------|
| GDPR Art. 5 (lawfulness) | ✅ Compliant | Data for equipment assessment |
| GDPR Art. 5 (purpose limitation) | ✅ Compliant | Used for operations/assessment |
| GDPR Art. 5 (data minimization) | ✅ Compliant | Only necessary fields |
| GDPR Art. 5 (integrity & confidentiality) | ⚠️ Partial | Infrastructure encryption; column-level recommended |
| GDPR Art. 32 (security) | ⚠️ Partial | Access control, audit logging not fully implemented |
| OSHA compliance | ✅ Applicable | Safety classifications tracked |
| Machinery Directive | ✅ Applicable | CE marking tracked |
| Tax compliance | ✅ Applicable | Depreciation methods supported |

---

## 11. Next Steps (v2)

- [ ] Implement Spring Security for access control
- [ ] Add role-based access (Finance, Operations, Maintenance)
- [ ] Column-level encryption for financial fields
- [ ] Soft-delete with 7-year retention
- [ ] API-level masking for sensitive data
- [ ] Field-level audit trail
- [ ] GDPR data export functionality

