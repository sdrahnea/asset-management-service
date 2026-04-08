# Copyright — Application Surface / Route Map

**Version**: 1.0 | **Date**: 2026-04-04

---

## 1. Route Summary

All routes are served by `CopyrightController` under the prefix `/copyrights`.

| Method | Path | Handler | Description |
|--------|------|---------|-------------|
| `GET` | `/copyrights` | `list()` | Render list of all copyrights |
| `GET` | `/copyrights/new` | `createForm()` | Render empty create form |
| `POST` | `/copyrights` | `create()` | Submit new copyright |
| `GET` | `/copyrights/{id}` | `detail()` | Render detail view (planned Phase 2) |
| `GET` | `/copyrights/{id}/edit` | `editForm()` | Render pre-filled edit form |
| `POST` | `/copyrights/{id}` | `update()` | Submit update |
| `POST` | `/copyrights/{id}/delete` | `delete()` | Delete copyright |

---

## 2. Request / Response Contracts

### 2.1 GET `/copyrights` — List

**Response**: `200 OK`, renders `copyrights/list.html`

**Model attributes**:

| Attribute | Type | Description |
|-----------|------|-------------|
| `items` | `List<Copyright>` | All copyright records |
| `singularTitle` | `String` | `"Copyright"` |
| `pluralTitle` | `String` | `"Copyrights"` |
| `basePath` | `String` | `"/copyrights"` |

---

### 2.2 GET `/copyrights/new` — Create Form

**Response**: `200 OK`, renders `copyrights/form.html`

**Model attributes**:

| Attribute | Type | Description |
|-----------|------|-------------|
| `item` | `Copyright` | Empty object with defaults |
| `isEdit` | `Boolean` | `false` |
| `singularTitle` | `String` | `"Copyright"` |
| `pluralTitle` | `String` | `"Copyrights"` |
| `basePath` | `String` | `"/copyrights"` |
| `workTypes` | `WorkType[]` | Enum values for dropdown |
| `copyrightStatuses` | `CopyrightStatus[]` | Enum values |
| `riskLevels` | `RiskLevel[]` | Enum values |
| `exclusivityTypes` | `ExclusivityType[]` | Enum values |
| `licenseTypes` | `LicenseType[]` | Enum values |

**Default values**:
- `workType` → `OTHER`
- `copyrightStatus` → `PENDING`
- `sourceOfInformation` → `"Manual Entry"`
- `infringementRisk` → `MEDIUM`
- `ownershipDisputesRisk` → `MEDIUM`
- `expirationRisk` → `MEDIUM`

---

### 2.3 POST `/copyrights` — Create

**Request**: `application/x-www-form-urlencoded`

**Form fields** (40+ fields):

**Work Identity**:
| Field | Type | Required |
|-------|------|----------|
| `name` (remapped to `copyrightId`) | String | ✅ |
| `title` | String | ✅ |
| `workType` | String (enum) | ✅ |
| `descriptionAbstract` | String | ❌ |
| `versionEdition` | String | ❌ |
| `creationDate` | Date | ❌ |
| `publicationReleaseDate` | Date | ❌ |

**Ownership**:
| Field | Type | Required |
|-------|------|----------|
| `authors` | String | ✅ |
| `copyrightOwners` | String | ✅ |
| `ownershipPercentage` | Decimal | ❌ |
| `moralRightsHolder` | String | ❌ |
| `countryOfOrigin` | String | ✅ |

**Legal & Registration**:
| Field | Type | Required |
|-------|------|----------|
| `copyrightNotice` | String | ❌ |
| `registrationNumber` | String | ❌ |
| `registrationAuthority` | String | ❌ |
| `registrationDate` | Date | ❌ |
| `copyrightStatus` | String (enum) | ✅ |
| `protectionStartDate` | Date | ❌ |
| `protectionEndDate` | Date | ❌ |
| `jurisdictionsCovered` | String | ❌ |

**Rights & Restrictions**:
| Field | Type | Required |
|-------|------|----------|
| `rightsGranted` | String | ❌ |
| `rightsReserved` | String | ❌ |
| `usageRestrictions` | String | ❌ |
| `exclusivityType` | String (enum) | ❌ |
| `derivativeWorksAllowed` | Boolean | ❌ |
| `drmProtectionMeasures` | String | ❌ |

**Licensing & Agreements**:
| Field | Type | Required |
|-------|------|----------|
| `licenseType` | String (enum) | ❌ |
| `licensees` | String | ❌ |
| `licenseStartDate` | Date | ❌ |
| `licenseEndDate` | Date | ❌ |
| `permittedUses` | String | ❌ |
| `royaltyTerms` | String | ❌ |
| `contractReferences` | String | ❌ |

**Financial & Commercial**:
| Field | Type | Required |
|-------|------|----------|
| `royaltyRate` | Decimal | ❌ |
| `revenueGenerated` | Decimal | ❌ |
| `advancePayments` | Decimal | ❌ |
| `licensingFees` | Decimal | ❌ |
| `valuation` | Decimal | ❌ |

**Risk & Assessment**:
| Field | Type | Required |
|-------|------|----------|
| `infringementRisk` | String (enum) | ❌ |
| `ownershipDisputesRisk` | String (enum) | ❌ |
| `expirationRisk` | String (enum) | ❌ |
| `complianceFlags` | String | ❌ |
| `marketRelevanceScore` | Decimal | ❌ |
| `portfolioImpactScore` | Decimal | ❌ |

**Metadata**:
| Field | Type | Required |
|-------|------|----------|
| `sourceOfInformation` | String | ✅ |
| `responsibleReviewer` | String | ❌ |
| `documentReferences` | String | ❌ |
| `tagsCategories` | String | ❌ |
| `notes` | String | ❌ |

**Success response**: `302 redirect to /copyrights`

**Failure response**: `200 OK`, form re-rendered with validation errors

---

### 2.4 GET `/copyrights/{id}/edit` — Edit Form

**Path param**: `id` — Long

**Response**: `200 OK`, renders `copyrights/form.html`

**Model attributes**: Same as create form (§2.2) with:
- `item` pre-populated
- `isEdit` set to `true`

---

### 2.5 POST `/copyrights/{id}` — Update

**Path param**: `id` — Long

**Request fields**: Same as create (see §2.3)

**Success response**: `302 redirect to /copyrights`

**Failure response**: `200 OK`, form re-rendered with errors, `isEdit=true`

---

### 2.6 POST `/copyrights/{id}/delete` — Delete

**Path param**: `id` — Long

**Response**: `302 redirect to /copyrights`

---

## 3. Form Field Organization (UI Layout)

### Work Identity Section
```
[Copyright ID *]        [Title *]
[Type *]                [Creation Date]
[Version/Edition]       [Publication Date]
[Description (large text area)]
```

### Ownership & Authorship Section
```
[Authors *]             [Copyright Owners *]
[Ownership %]           [Moral Rights Holder]
[Country of Origin *]
```

### Legal & Registration Section
```
[Copyright Notice]      [Registration Number]
[Registration Authority] [Registration Date]
[Copyright Status *]    [Protection Start Date]
[Protection End Date]   [Jurisdictions Covered]
```

### Rights & Restrictions Section
```
[Rights Granted (large)]           [Rights Reserved (large)]
[Usage Restrictions (large)]       [Exclusivity]
[Derivative Works Allowed]         [DRM/Protection Measures]
```

### Licensing & Agreements Section
```
[License Type]          [Licensees]
[License Start Date]    [License End Date]
[Permitted Uses (large)]
[Royalty Terms (large)] [Contract References]
```

### Financial & Commercial Section
```
[Royalty Rate]          [Revenue Generated]
[Advance Payments]      [Licensing Fees]
[Valuation]
```

### Risk & Assessment Section
```
[Infringement Risk]     [Ownership Disputes Risk]
[Expiration Risk]       [Compliance Flags (large)]
[Market Relevance Score] [Portfolio Impact Score]
```

### Metadata Section
```
[Source of Information *] [Responsible Reviewer]
[Document References (large)]
[Tags/Categories]       
[Notes (large)]
```

### Audit Section (Read-Only)
```
[Created At (RO)]     [Created By (RO)]
[Updated At (RO)]     [Updated By (RO)]
```

---

## 4. Enum Dropdown Options

### WorkType (8 options)
```
TEXT – Written works, books, articles
IMAGE – Photos, graphics, artwork
SOFTWARE – Source code, applications
MUSIC – Compositions and recordings
VIDEO – Films, animations, documentaries
DESIGN – Graphic and industrial design
DATABASE – Data compilations
OTHER – Other creative works
```

### CopyrightStatus (4 options)
```
ACTIVE – Currently protected
EXPIRED – Protection term ended
PUBLIC_DOMAIN – No longer protected
PENDING – Registration in progress
```

### ExclusivityType (2 options)
```
EXCLUSIVE – Single licensee
NON_EXCLUSIVE – Multiple licensees
```

### LicenseType (5 options)
```
EXCLUSIVE – Exclusive license
NON_EXCLUSIVE – Non-exclusive license
OPEN_SOURCE – Open source (GPL, MIT, etc.)
CREATIVE_COMMONS – Creative Commons license
CUSTOM_CONTRACT – Custom agreement
```

### RiskLevel (3 options)
```
LOW – Minimal risk
MEDIUM – Moderate risk
HIGH – Elevated risk
```

---

## 5. Validation Error Messages

| Validation | Error Message |
|-----------|---------------|
| Missing copyrightId | `"Copyright ID is required"` |
| copyrightId too long (>100) | `"Copyright ID must be at most 100 characters"` |
| copyrightId invalid pattern | `"Copyright ID must be alphanumeric"` |
| Missing title | `"Title is required"` |
| Missing workType | `"Type of work is required"` |
| Missing authors | `"Author(s) is required"` |
| Missing copyrightOwners | `"Copyright owner(s) is required"` |
| Missing countryOfOrigin | `"Country of origin is required"` |
| Missing copyrightStatus | `"Copyright status is required"` |
| Missing sourceOfInformation | `"Source of information is required"` |
| Creation date in future | `"Creation date must be today or earlier"` |
| Publication before creation | `"Publication/release date must be >= creation date"` |
| Registration date in future | `"Registration date must be today or earlier"` |
| Protection end before start | `"Protection end date must be >= protection start date"` |
| License end before start | `"License end date must be >= license start date"` |
| Ownership % out of range | `"Ownership percentage must be 0–100"` |
| Royalty rate out of range | `"Royalty rate must be 0–100"` |
| Duplicate copyrightId | `"Copyright ID must be unique"` |
| Duplicate registrationNumber | `"Registration number must be unique"` |
| Invalid enum value | `"Invalid selection for [field name]"` |

---

## 6. Success & Feedback Messages

| Action | Message | Type |
|--------|---------|------|
| Create | `"Copyright created successfully."` | success |
| Update | `"Copyright updated successfully."` | success |
| Delete | `"Copyright deleted successfully."` | success |
| Validation error | `"Please correct the errors below."` | error |
| Unexpected error | `"An unexpected error occurred. Please try again."` | error |

---

## 7. Navigation

| Page | Links |
|------|-------|
| List | Create Copyright, Back to Home |
| Create Form | Back to List, Back to Home |
| Edit Form | Back to List, Back to Home |
| Detail (v2) | Back to List, Edit, Back to Home |

---

## 8. Quick Reference

| Endpoint | Method | Purpose | Template |
|----------|--------|---------|----------|
| `/copyrights` | GET | List all | `copyrights/list.html` |
| `/copyrights/new` | GET | Create form | `copyrights/form.html` |
| `/copyrights` | POST | Create | `copyrights/form.html` (on error) |
| `/copyrights/{id}/edit` | GET | Edit form | `copyrights/form.html` |
| `/copyrights/{id}` | POST | Update | `copyrights/form.html` (on error) |
| `/copyrights/{id}/delete` | POST | Delete | Redirect to `/copyrights` |
| `/copyrights/{id}` | GET | Detail | `copyrights/detail.html` (Phase 2) |

---

## 9. Planned REST API (Future — v3)

| Method | Path | Purpose |
|--------|------|---------|
| `GET` | `/api/copyrights` | JSON list |
| `GET` | `/api/copyrights/{id}` | JSON detail |
| `POST` | `/api/copyrights` | JSON create |
| `PUT` | `/api/copyrights/{id}` | JSON update |
| `DELETE` | `/api/copyrights/{id}` | JSON delete |


