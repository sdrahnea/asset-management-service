# Patent — Data Model

**Version**: 1.0 | **Date**: 2026-04-04 | **Table**: `patent`

`Patent` extends `CoreEntity`; `name` maps to `patent_id`.

## Unique Constraints
- `patent_id`
- `application_number`
- `publication_number`
- `grant_number`

## Field Groups
### Identity and Classification
- `patentId`, `title`, `abstractSummary`, `patentType`, `technologyField`, `ipcCpcCodes`, `keywordsTags`

### Inventorship and Ownership
- `inventors`, `assigneeOwner`, `ownershipPercentage`, `assignmentHistory`, `countryOfOrigin`

### Legal Filing and Registration
- `applicationNumber`, `applicationDate`, `publicationNumber`, `publicationDate`, `grantNumber`, `grantDate`, `priorityNumbers`, `priorityDates`, `jurisdictions`, `patentFamilyId`

### Rights and Scope
- `claimsText`, `descriptionSpecification`, `drawingsReferences`, `scopeOfProtection`, `limitationsDisclaimers`, `legalStatus`

### Geographic Coverage
- `countriesFiled`, `countriesGranted`, `regionalSystem`, `nationalPhaseEntries`

### Financial and Commercial
- `filingFees`, `maintenanceRenewalFees`, `licensingRevenue`, `royaltyRates`, `valuation`, `costOfProsecution`

### Lifecycle and Maintenance
- `renewalDates`, `maintenanceFeeSchedule`, `legalDeadlines`, `oppositionLitigationHistory`, `expiryDate`

### Risk and Competitive Indicators
- `infringementRisk`, `ftoConcerns`, `blockingPotential`, `technologicalRelevanceScore`, `marketRelevanceScore`, `patentStrengthScore`, `citationsReceived`, `citationsMade`

### Documentation and Metadata
- `officialDocuments`, `supportingDocuments`, `dataSource`, `responsibleAnalyst`, `notesComments`

## Key Validation/Business Rules
- Mandatory: patentId, title, patentType, assigneeOwner, applicationNumber, applicationDate, legalStatus, dataSource
- If status GRANTED, grantNumber and grantDate required
- Chronology: publication/grant >= application; expiry >= grant
- Range checks: percentages/scores 0..100 where applicable; counts >= 0

