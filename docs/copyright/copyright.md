# Copyright — Comprehensive Specification

**Version**: 1.0 | **Date**: 2026-04-04

---

## Overview

The `Copyright` entity models a copyright/intellectual property right asset within an asset-management system. It captures:
- **Work identification**: title, type, creation/publication dates, description
- **Ownership & authorship**: authors, copyright owners, percentages, country of origin
- **Legal metadata**: registration number, status, protection dates, jurisdiction
- **Rights & restrictions**: granted rights, usage restrictions, exclusivity, derivatives, DRM
- **Licensing & agreements**: license type, licensees, dates, permitted uses, royalty terms
- **Financial data**: royalty rate, revenue, advance payments, fees, valuation
- **Risk indicators**: infringement, ownership disputes, expiration, compliance flags, market relevance
- **Operational metadata**: information source, reviewer, document references, tags, notes

This entity supports IP management, copyright compliance, licensing tracking, financial assessment, and portfolio risk evaluation.

---

## Regulatory & Compliance Context

This service operates with awareness of global intellectual property and financial regulation.

### Applicable Frameworks
- **Berne Convention** – International copyright protection standards
- **TRIPS Agreement** – WTO trade-related IP rights (applies globally)
- **EU Copyright Directive 2006/115/EC** – European harmonization
- **GDPR** (EU 2016/679) – Personal data in author/owner/reviewer fields
- **Local IP Offices**: OSIM (Romania), EUIPO, USPTO (US), WIPO (international)
- **Financial Regulations**: Tax implications of IP valuation, royalty tracking

### Data Minimization Principle
Per **GDPR Article 5(1)(c)**, only data necessary for assessment and licensing is stored:
- Author/owner names (not personal ID, address unless legally required)
- Registration numbers and status (public records)
- No full legal contract text (link/reference instead)
- No detailed royalty ledgers (summary metrics only)
- Compliance flags as summary, not detailed investigation log

---

## Entity Inheritance & Mapping

```
CoreEntity (base)
  ├─ id (Long, primary key)
  ├─ name → remapped to copyrightId via @AttributeOverride
  ├─ createdAt, createdBy
  ├─ updatedAt, updatedBy
  │
  └─ Copyright (extends CoreEntity)
       ├─ Work Identity: copyrightId (unique), title, workType, descriptionAbstract, 
       │                 versionEdition, creationDate, publicationReleaseDate
       ├─ Ownership: authors, copyrightOwners, ownershipPercentage, moralRightsHolder, 
       │             countryOfOrigin
       ├─ Legal: copyrightNotice, registrationNumber (unique), registrationAuthority, 
       │         registrationDate, copyrightStatus, protectionStartDate, protectionEndDate, 
       │         jurisdictionsCovered
       ├─ Rights: rightsGranted, rightsReserved, usageRestrictions, exclusivityType,
       │          derivativeWorksAllowed, drmProtectionMeasures
       ├─ Licensing: licenseType, licensees, licenseStartDate, licenseEndDate, permittedUses,
       │             royaltyTerms, contractReferences
       ├─ Financial: royaltyRate, revenueGenerated, advancePayments, licensingFees, valuation
       ├─ Risk: infringementRisk, ownershipDisputesRisk, expirationRisk, complianceFlags,
       │        marketRelevanceScore, portfolioImpactScore
       └─ Metadata: sourceOfInformation, responsibleReviewer, documentReferences, 
                    tagsCategories, notes
```

---

## Data Model — Detailed Field Specification

### Enums

| Enum | Purpose | Values |
|------|---------|--------|
| `WorkType` | Classifies the type of creative work | TEXT, IMAGE, SOFTWARE, MUSIC, VIDEO, DESIGN, DATABASE, OTHER |
| `CopyrightStatus` | Tracks legal status of the copyright | ACTIVE, EXPIRED, PUBLIC_DOMAIN, PENDING |
| `ExclusivityType` | Indicates exclusive or non-exclusive rights | EXCLUSIVE, NON_EXCLUSIVE |
| `LicenseType` | Categorizes the license agreement type | EXCLUSIVE, NON_EXCLUSIVE, OPEN_SOURCE, CREATIVE_COMMONS, CUSTOM_CONTRACT |
| `RiskLevel` | Quantifies risk on a scale | LOW, MEDIUM, HIGH |

### Work Identification

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `copyrightId` | `copyright_id` | VARCHAR(100) | ✅ | Unique, pattern: alphanumeric | Maps to `name` via `@AttributeOverride`; pattern: `^[A-Za-z0-9_-]+$` |
| `title` | `title` | VARCHAR(255) | ✅ | Not blank | Descriptive title of the creative work |
| `workType` | `work_type` | ENUM(30) | ✅ | One of WorkType | TEXT, IMAGE, SOFTWARE, MUSIC, VIDEO, DESIGN, DATABASE, OTHER |
| `descriptionAbstract` | `description_abstract` | VARCHAR(4000) | ❌ | Max 4000 chars | Narrative description or abstract of the work |
| `versionEdition` | `version_edition` | VARCHAR(100) | ❌ | Max 100 chars | Version number or edition identifier |
| `creationDate` | `creation_date` | DATE | ❌ | ≤ today | Date the work was created |
| `publicationReleaseDate` | `publication_release_date` | DATE | ❌ | ≤ today, ≥ creationDate | Date the work was published or released |

### Ownership & Authorship

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `authors` | `authors` | VARCHAR(1000) | ✅ | Not blank | Names of all authors/creators (comma-separated if multiple) |
| `copyrightOwners` | `copyright_owners` | VARCHAR(1000) | ✅ | Not blank | Names of copyright holders (may differ from authors) |
| `ownershipPercentage` | `ownership_percentage` | DECIMAL(7,2) | ❌ | 0–100 | Percentage ownership for multi-owner works |
| `moralRightsHolder` | `moral_rights_holder` | VARCHAR(255) | ❌ | Max 255 chars | Person/entity holding moral rights (if applicable) |
| `countryOfOrigin` | `country_of_origin` | VARCHAR(100) | ✅ | Not blank | Country where work was created |

### Legal Metadata

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `copyrightNotice` | `copyright_notice` | VARCHAR(255) | ❌ | Max 255 chars | e.g., "© 2026 Author Name" |
| `registrationNumber` | `registration_number` | VARCHAR(100) | ❌ | Unique | Official registration number (if registered) |
| `registrationAuthority` | `registration_authority` | VARCHAR(100) | ❌ | Max 100 chars | OSIM (Romania), EUIPO, USPTO, WIPO, etc. |
| `registrationDate` | `registration_date` | DATE | ❌ | ≤ today | Date registered with authority |
| `copyrightStatus` | `copyright_status` | ENUM(30) | ✅ | One of CopyrightStatus | ACTIVE, EXPIRED, PUBLIC_DOMAIN, PENDING |
| `protectionStartDate` | `protection_start_date` | DATE | ❌ | None | Legal protection start date |
| `protectionEndDate` | `protection_end_date` | DATE | ❌ | ≥ protectionStartDate | Legal protection end date |
| `jurisdictionsCovered` | `jurisdictions_covered` | VARCHAR(500) | ❌ | Max 500 chars | List of countries/regions covered |

### Rights & Restrictions

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `rightsGranted` | `rights_granted` | VARCHAR(2000) | ❌ | Max 2000 chars | Narrative: reproduction, distribution, adaptation, public performance, etc. |
| `rightsReserved` | `rights_reserved` | VARCHAR(2000) | ❌ | Max 2000 chars | Rights retained by copyright owner |
| `usageRestrictions` | `usage_restrictions` | VARCHAR(2000) | ❌ | Max 2000 chars | Territorial, time-limited, media-specific, geographic restrictions |
| `exclusivityType` | `exclusivity_type` | ENUM(30) | ❌ | One of ExclusivityType | EXCLUSIVE or NON_EXCLUSIVE rights |
| `derivativeWorksAllowed` | `derivative_works_allowed` | BOOLEAN | ❌ | None | True if derivative works permitted |
| `drmProtectionMeasures` | `drm_protection_measures` | VARCHAR(1000) | ❌ | Max 1000 chars | DRM, encryption, technical protection details |

### Licensing & Agreements

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `licenseType` | `license_type` | ENUM(40) | ❌ | One of LicenseType | EXCLUSIVE, NON_EXCLUSIVE, OPEN_SOURCE, CREATIVE_COMMONS, CUSTOM_CONTRACT |
| `licensees` | `licensees` | VARCHAR(1000) | ❌ | Max 1000 chars | Names/entities holding licenses |
| `licenseStartDate` | `license_start_date` | DATE | ❌ | None | License agreement effective date |
| `licenseEndDate` | `license_end_date` | DATE | ❌ | ≥ licenseStartDate | License expiration date |
| `permittedUses` | `permitted_uses` | VARCHAR(2000) | ❌ | Max 2000 chars | Narrative: specific uses allowed under license |
| `royaltyTerms` | `royalty_terms` | VARCHAR(2000) | ❌ | Max 2000 chars | Payment terms, frequency, structure |
| `contractReferences` | `contract_references` | VARCHAR(1000) | ❌ | Max 1000 chars | Links or IDs to full contracts |

### Financial & Commercial

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `royaltyRate` | `royalty_rate` | DECIMAL(7,2) | ❌ | 0–100 | Percentage royalty rate (e.g., 12.5%) |
| `revenueGenerated` | `revenue_generated` | DECIMAL(19,2) | ❌ | ≥ 0 | Total revenue to date |
| `advancePayments` | `advance_payments` | DECIMAL(19,2) | ❌ | ≥ 0 | Upfront/advance payments received |
| `licensingFees` | `licensing_fees` | DECIMAL(19,2) | ❌ | ≥ 0 | Fees from licensing agreements |
| `valuation` | `valuation` | DECIMAL(19,2) | ❌ | ≥ 0 | Assessed value of copyright asset |

### Risk & Assessment

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `infringementRisk` | `infringement_risk` | ENUM(20) | ❌ | One of RiskLevel | LOW, MEDIUM, HIGH risk of infringement |
| `ownershipDisputesRisk` | `ownership_disputes_risk` | ENUM(20) | ❌ | One of RiskLevel | LOW, MEDIUM, HIGH risk of disputes |
| `expirationRisk` | `expiration_risk` | ENUM(20) | ❌ | One of RiskLevel | LOW, MEDIUM, HIGH risk of expiration |
| `complianceFlags` | `compliance_flags` | VARCHAR(1000) | ❌ | Max 1000 chars | AML, licensing compliance issues, legal holds |
| `marketRelevanceScore` | `market_relevance_score` | DECIMAL(7,2) | ❌ | 0–100 | Market relevance/impact score |
| `portfolioImpactScore` | `portfolio_impact_score` | DECIMAL(7,2) | ❌ | 0–100 | Impact on overall portfolio score |

### Operational Metadata

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `sourceOfInformation` | `source_of_information` | VARCHAR(100) | ✅ | Not blank | Manual entry, registration office, contract system, audit |
| `responsibleReviewer` | `responsible_reviewer` | VARCHAR(100) | ❌ | Max 100 chars | Name/ID of person responsible for record |
| `documentReferences` | `document_references` | VARCHAR(2000) | ❌ | Max 2000 chars | Links to registration certificates, contracts, etc. |
| `tagsCategories` | `tags_categories` | VARCHAR(500) | ❌ | Max 500 chars | Comma-separated labels (e.g., "software,patent-related,licensed") |
| `notes` | `notes` | VARCHAR(2000) | ❌ | Max 2000 chars | Free-form comments, audit trail, context |

---

## Validation Rules

### Field-Level Rules

| Field | Rule | Error Message |
|-------|------|---------------|
| `copyrightId` | Required, max 100 chars, alphanumeric | `"Copyright ID is required"` |
| `title` | Required, max 255 chars | `"Title is required"` |
| `workType` | Required enum | `"Type of work is required"` |
| `authors` | Required, max 1000 chars | `"Author(s) is required"` |
| `copyrightOwners` | Required, max 1000 chars | `"Copyright owner(s) is required"` |
| `countryOfOrigin` | Required, max 100 chars | `"Country of origin is required"` |
| `copyrightStatus` | Required enum | `"Copyright status is required"` |
| `sourceOfInformation` | Required, max 100 chars | `"Source of information is required"` |
| `ownershipPercentage` | If present, must be 0–100 | `"Ownership percentage must be 0–100"` |
| `royaltyRate` | If present, must be 0–100 | `"Royalty rate must be 0–100"` |
| `creationDate` | If present, must be ≤ today | `"Creation date must be today or earlier"` |
| `publicationReleaseDate` | If present, must be ≤ today & ≥ creationDate | `"Publication date must be after creation date"` |
| `registrationDate` | If present, must be ≤ today | `"Registration date must be today or earlier"` |
| `protectionEndDate` | If present, must be ≥ protectionStartDate | `"Protection end date must be >= start date"` |
| `licenseEndDate` | If present, must be ≥ licenseStartDate | `"License end date must be >= start date"` |

### Uniqueness Rules

| Rule | Error Message | Notes |
|------|---------------|-------|
| `copyrightId` globally unique | `"Copyright ID must be unique"` | Checked on create and update (excludeId pattern) |
| `registrationNumber` globally unique (if provided) | `"Registration number must be unique"` | Optional field; uniqueness applies when present |

### Business Rules

- **Normalization**: `copyrightId` is normalized (alphanumeric check, trim) before validation
- **Temporal ordering**: 
  - creationDate must be ≤ today
  - publicationReleaseDate must be ≥ creationDate
  - registrationDate must be ≤ today
  - protectionEndDate must be ≥ protectionStartDate
  - licenseEndDate must be ≥ licenseStartDate
- **Optional rich fields**: All fields except copyrightId, title, workType, authors, copyrightOwners, countryOfOrigin, copyrightStatus, sourceOfInformation are optional
- **Decimal constraints**: ownershipPercentage, royaltyRate, marketRelevanceScore, portfolioImpactScore must be 0–100 if present

---

## Service-Layer Behavior

### Normalization Pipeline

Before validation, the service normalizes:
1. Trim all string fields
2. Validate copyrightId pattern (alphanumeric, dashes, underscores only)
3. Convert empty strings to `null`
4. No field remapping

### Error Handling

Service throws `IllegalArgumentException` with a descriptive message on:
- Missing required fields
- Uniqueness violation (copyrightId, registrationNumber)
- Validation failure (temporal ordering, decimal ranges)

Controllers catch these exceptions and add them as global form errors via `BindingResult.reject(...)`.

### Update Pattern

The `CopyrightService` uses **explicit field-copy** update pattern:
```java
// Copy all fields explicitly, respecting null handling
entity.setCopyrightId(dto.getCopyrightId());
entity.setTitle(dto.getTitle());
// ... etc for all 40+ fields
entity.setUpdatedAt(LocalDateTime.now());
entity.setUpdatedBy(currentUser);
```

---

## Default Values & Enums

### Defaults on Create Form
- `workType` → `OTHER`
- `copyrightStatus` → `PENDING`
- `sourceOfInformation` → `"Manual Entry"`
- `infringementRisk` → `MEDIUM`
- `ownershipDisputesRisk` → `MEDIUM`
- `expirationRisk` → `MEDIUM`

### Enum Values

**WorkType**:
- `TEXT` – Written works (books, articles, papers)
- `IMAGE` – Visual works (photos, artwork)
- `SOFTWARE` – Source code, applications
- `MUSIC` – Musical compositions, recordings
- `VIDEO` – Film, documentary, animation
- `DESIGN` – Graphic design, industrial design
- `DATABASE` – Databases and data compilations
- `OTHER` – Other creative works

**CopyrightStatus**:
- `ACTIVE` – Currently protected
- `EXPIRED` – Protection term ended
- `PUBLIC_DOMAIN` – No longer protected
- `PENDING` – Registration in progress

**ExclusivityType**:
- `EXCLUSIVE` – Only one licensee
- `NON_EXCLUSIVE` – Multiple licensees permitted

**LicenseType**:
- `EXCLUSIVE` – Exclusive license agreement
- `NON_EXCLUSIVE` – Non-exclusive license
- `OPEN_SOURCE` – Open source (GPL, MIT, Apache, etc.)
- `CREATIVE_COMMONS` – CC license variant
- `CUSTOM_CONTRACT` – Custom agreement

**RiskLevel**:
- `LOW` – Minimal risk
- `MEDIUM` – Moderate risk (default)
- `HIGH` – Elevated risk requiring attention

---

## CRUD Operations

### Create
- Form at `/copyrights/new`
- POST to `/copyrights` with required + optional fields
- Service normalizes → validates → saves
- Redirects to `/copyrights` on success; re-renders form on error

### Read
- List at `/copyrights` with pagination or filtering (planned Phase 2)
- Detail at `/copyrights/{id}` (planned Phase 2)

### Update
- Form at `/copyrights/{id}/edit` pre-populated
- POST to `/copyrights/{id}` with same fields as create
- Service applies normalization, validates, checks uniqueness (excludeId)
- Redirects to `/copyrights` on success; re-renders form on error

### Delete
- POST to `/copyrights/{id}/delete`
- Permanent hard-delete from database
- Redirects to `/copyrights`

---

## Phase-Based Development

### Phase 1 ✅ Completed (v1)
- [x] Copyright entity with all 40+ fields
- [x] 5 enums (WorkType, CopyrightStatus, ExclusivityType, LicenseType, RiskLevel)
- [x] `@AttributeOverride` for copyrightId
- [x] @AssertTrue validators for temporal ordering
- [x] CopyrightRepository with uniqueness queries
- [x] CopyrightService with normalize/validate/CRUD
- [x] CopyrightController with list, create, edit, update, delete
- [x] `copyrights/list.html` and `copyrights/form.html` templates
- [x] Validation error handling and display

### Phase 2 (Planned)
- [ ] Detail page `copyrights/detail.html`
- [ ] Filtering by workType, copyrightStatus, country
- [ ] Sorting and pagination on list
- [ ] Service unit tests
- [ ] Controller integration tests

### Phase 3+ (Optional)
- [ ] REST API endpoints (`/api/copyrights/...`)
- [ ] Copyright expiration monitoring alerts
- [ ] Royalty calculation automation
- [ ] Portfolio risk scoring
- [ ] License compliance reporting

---

## Known Limitations & Out of Scope (v1)

- No real-time copyright office API integration
- No automated royalty calculations
- No dispute/litigation tracking
- No infringement monitoring integration
- No soft-delete; hard delete only
- No authentication/authorization (planned for next phase)
- No automated alerts for expiration dates

---

## Sources & References

- `docs/copyright.md` – High-level copyright overview
- `src/main/java/com/sdr/ams/model/intangible/Copyright.java` – Entity definition
- `src/main/java/com/sdr/ams/repository/CopyrightRepository.java` – Data access
- `src/main/java/com/sdr/ams/service/CopyrightService.java` – Business logic
- `src/main/java/com/sdr/ams/controller/CopyrightController.java` – Web routes
- `src/main/resources/templates/copyrights/` – UI templates

