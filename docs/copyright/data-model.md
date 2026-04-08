# Copyright — Data Model & Validation

**Version**: 1.0 | **Date**: 2026-04-04 | **Table**: `copyright`

`Copyright` extends `CoreEntity`. `name` overridden to `copyright_id` via `@AttributeOverride`.

---

## Enums
| Enum | Values |
|------|--------|
| `WorkType` | TEXT, IMAGE, SOFTWARE, MUSIC, VIDEO, DESIGN, DATABASE, OTHER |
| `CopyrightStatus` | ACTIVE, EXPIRED, PUBLIC_DOMAIN, PENDING |
| `ExclusivityType` | EXCLUSIVE, NON_EXCLUSIVE |
| `LicenseType` | EXCLUSIVE, NON_EXCLUSIVE, OPEN_SOURCE, CREATIVE_COMMONS, CUSTOM_CONTRACT |
| `RiskLevel` | LOW, MEDIUM, HIGH |

---

## Key Fields by Category

### Work Identification
| Field | Column | Type | Required |
|-------|--------|------|----------|
| `copyrightId` | `copyright_id` | VARCHAR(100) | ✅ (unique) |
| `title` | `title` | VARCHAR(255) | ✅ |
| `workType` | `work_type` | ENUM | ✅ |
| `descriptionAbstract` | `description_abstract` | VARCHAR(4000) | ❌ |
| `versionEdition` | `version_edition` | VARCHAR(100) | ❌ |
| `creationDate` | `creation_date` | DATE | ❌ |
| `publicationReleaseDate` | `publication_release_date` | DATE | ❌ |

### Ownership & Authorship
| Field | Column | Type | Required |
|-------|--------|------|----------|
| `authors` | `authors` | VARCHAR(1000) | ✅ |
| `copyrightOwners` | `copyright_owners` | VARCHAR(1000) | ✅ |
| `ownershipPercentage` | `ownership_percentage` | DECIMAL(7,2) | ❌ |
| `moralRightsHolder` | `moral_rights_holder` | VARCHAR(255) | ❌ |
| `countryOfOrigin` | `country_of_origin` | VARCHAR(100) | ✅ |

### Legal Metadata
| Field | Column | Type | Required |
|-------|--------|------|----------|
| `copyrightNotice` | `copyright_notice` | VARCHAR(255) | ❌ |
| `registrationNumber` | `registration_number` | VARCHAR(100) | ❌ (unique) |
| `registrationAuthority` | `registration_authority` | VARCHAR(100) | ❌ |
| `registrationDate` | `registration_date` | DATE | ❌ |
| `copyrightStatus` | `copyright_status` | ENUM | ✅ |
| `protectionStartDate` | `protection_start_date` | DATE | ❌ |
| `protectionEndDate` | `protection_end_date` | DATE | ❌ |
| `jurisdictionsCovered` | `jurisdictions_covered` | VARCHAR(500) | ❌ |

### Rights & Restrictions
| Field | Column | Type | Required |
|-------|--------|------|----------|
| `rightsGranted` | `rights_granted` | VARCHAR(2000) | ❌ |
| `rightsReserved` | `rights_reserved` | VARCHAR(2000) | ❌ |
| `usageRestrictions` | `usage_restrictions` | VARCHAR(2000) | ❌ |
| `exclusivityType` | `exclusivity_type` | ENUM | ❌ |
| `derivativeWorksAllowed` | `derivative_works_allowed` | BOOLEAN | ❌ |
| `drmProtectionMeasures` | `drm_protection_measures` | VARCHAR(1000) | ❌ |

### Licensing & Agreements
| Field | Column | Type | Required |
|-------|--------|------|----------|
| `licenseType` | `license_type` | ENUM | ❌ |
| `licensees` | `licensees` | VARCHAR(1000) | ❌ |
| `licenseStartDate` | `license_start_date` | DATE | ❌ |
| `licenseEndDate` | `license_end_date` | DATE | ❌ |
| `permittedUses` | `permitted_uses` | VARCHAR(2000) | ❌ |
| `royaltyTerms` | `royalty_terms` | VARCHAR(2000) | ❌ |
| `contractReferences` | `contract_references` | VARCHAR(1000) | ❌ |

### Financial & Commercial
| Field | Column | Type | Required |
|-------|--------|------|----------|
| `royaltyRate` | `royalty_rate` | DECIMAL(7,2) | ❌ |
| `revenueGenerated` | `revenue_generated` | DECIMAL(19,2) | ❌ |
| `advancePayments` | `advance_payments` | DECIMAL(19,2) | ❌ |
| `licensingFees` | `licensing_fees` | DECIMAL(19,2) | ❌ |
| `valuation` | `valuation` | DECIMAL(19,2) | ❌ |

### Risk & Assessment
| Field | Column | Type | Required |
|-------|--------|------|----------|
| `infringementRisk` | `infringement_risk` | ENUM | ❌ |
| `ownershipDisputesRisk` | `ownership_disputes_risk` | ENUM | ❌ |
| `expirationRisk` | `expiration_risk` | ENUM | ❌ |
| `complianceFlags` | `compliance_flags` | VARCHAR(1000) | ❌ |
| `marketRelevanceScore` | `market_relevance_score` | DECIMAL(7,2) | ❌ |
| `portfolioImpactScore` | `portfolio_impact_score` | DECIMAL(7,2) | ❌ |

### Operational Metadata
| Field | Column | Type | Required |
|-------|--------|------|----------|
| `sourceOfInformation` | `source_of_information` | VARCHAR(100) | ✅ |
| `responsibleReviewer` | `responsible_reviewer` | VARCHAR(100) | ❌ |
| `documentReferences` | `document_references` | VARCHAR(2000) | ❌ |
| `tagsCategories` | `tags_categories` | VARCHAR(500) | ❌ |
| `notes` | `notes` | VARCHAR(2000) | ❌ |

---

## Validation Rules

| Field | Rule | Error |
|-------|------|-------|
| `copyrightId` | Required, max 100, alphanumeric | `"Copyright ID is required"` |
| `title` | Required, max 255 | `"Title is required"` |
| `workType` | Required enum | `"Type of work is required"` |
| `authors` | Required, max 1000 | `"Author(s) is required"` |
| `copyrightOwners` | Required, max 1000 | `"Copyright owner(s) is required"` |
| `countryOfOrigin` | Required, max 100 | `"Country of origin is required"` |
| `copyrightStatus` | Required enum | `"Copyright status is required"` |
| `sourceOfInformation` | Required, max 100 | `"Source of information is required"` |
| `ownershipPercentage` | If present, 0–100 | Validation error |
| `royaltyRate` | If present, 0–100 | Validation error |
| `creationDate` | If present, ≤ today | `"Creation date must be today or earlier"` |
| `publicationReleaseDate` | If present, ≤ today & ≥ creationDate | `"Publication date must be >= creation date"` |
| `registrationDate` | If present, ≤ today | `"Registration date must be today or earlier"` |
| `protectionEndDate` | If present, ≥ protectionStartDate | `"Protection end date must be >= start date"` |
| `licenseEndDate` | If present, ≥ licenseStartDate | `"License end date must be >= start date"` |
| `copyrightId` | Globally unique | `"Copyright ID must be unique"` |
| `registrationNumber` | If provided, unique | `"Registration number must be unique"` |

---

## Tasks
### Phase 1 ✅ Done
- [x] Copyright entity, enums, constraints, @AttributeOverride for copyrightId
- [x] CopyrightRepository with uniqueness queries
- [x] CopyrightService with normalize/validate
- [x] CopyrightController full CRUD
- [x] `copyrights/list.html`, `copyrights/form.html`

### Phase 2 (Planned)
- [ ] Detail page `copyrights/detail.html`
- [ ] Service unit tests
- [ ] Filter by workType, copyrightStatus, countryOfOrigin

---

## API Surface

| Method | Path | Description |
|--------|------|-------------|
| GET | `/copyrights` | List |
| GET | `/copyrights/new` | Create form |
| POST | `/copyrights` | Submit create |
| GET | `/copyrights/{id}` | Detail (planned) |
| GET | `/copyrights/{id}/edit` | Edit form |
| POST | `/copyrights/{id}` | Submit update |
| POST | `/copyrights/{id}/delete` | Delete |

