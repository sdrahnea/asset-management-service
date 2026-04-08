# Trademark — Data Model

**Version**: 1.0 | **Date**: 2026-04-04 | **Table**: `trademark`

`Trademark` extends `CoreEntity`; base `name` maps to `trademark_id`.

## Unique Constraints
- `trademark_id`
- `application_number`
- `registration_number`

## Field Groups
### Identity/Classification
- `trademarkId`, `markName`, `markType`, `markDescription`, `niceClasses`, `viennaCodes`, `industrySector`, `tags`

### Ownership
- `ownerName`, `ownerType`, `ownerAddress`, `representative`, `assignmentHistory`, `coOwners`

### Legal Lifecycle
- `applicationNumber`, `applicationDate`, `registrationNumber`, `registrationDate`, `priorityClaimed`, `priorityNumber`, `priorityDate`, `legalStatus`, `expirationDate`, `renewalHistory`, `oppositions`, `cancellationActions`

### Geographic
- `jurisdictionsFiled`, `jurisdictionsRegistered`, `internationalFilingSystem`, `regionalDesignations`

### Rights/Usage
- `goodsAndServices`, `usageStatus`, `firstUseDate`, `firstUseInCommerceDate`, `specimen`, `limitations`, `coexistenceAgreements`

### Financial/Commercial
- `licensingStatus`, `licensees`, `royaltyIncome`, `valuation`, `maintenanceCosts`, `commercialImportance`

### Risk/Assessment
- `distinctivenessScore`, `litigationRiskScore`, `infringementRiskScore`, `marketRelevanceScore`, `renewalRiskScore`, `overallAssessmentScore`, `complianceFlags`

### Evidence/Metadata
- `officialDocuments`, `representations`, `searchReports`, `legalOpinions`, `internalNotes`, `dataSource`, `responsibleAgent`, `notes`

## Key Rules
- Mandatory fields per service: trademarkId, markName, ownerName, applicationNumber, dataSource + required enums/dates
- Priority claim rule requires both number/date
- Date ordering rules enforced for priority/application/registration/expiration/first-use fields

