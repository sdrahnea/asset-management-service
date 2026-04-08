# Copyright — Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

---

## 1. Service Tests (`CopyrightService`)

### 1.1 Field Normalization

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-001 | copyrightId pattern validated | `"cop-2026-001"` | Alphanumeric with dashes/underscores accepted |
| TS-S-002 | copyrightId pattern rejected | `"cop 2026 001"` (spaces) | `IllegalArgumentException: "Copyright ID must be alphanumeric"` |
| TS-S-003 | copyrightId whitespace trimmed | `"  COP-001  "` | Trimmed to `"COP-001"` |
| TS-S-004 | All string fields trimmed | Various | All whitespace trimmed |

### 1.2 Required Field Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-010 | Valid copyright record created | All required fields present | No exception |
| TS-S-011 | Missing copyrightId | `copyrightId=null` | `IllegalArgumentException: "Copyright ID is required"` |
| TS-S-012 | Missing title | `title=null` | `IllegalArgumentException: "Title is required"` |
| TS-S-013 | Missing workType | `workType=null` | `IllegalArgumentException: "Type of work is required"` |
| TS-S-014 | Missing authors | `authors=null` | `IllegalArgumentException: "Author(s) is required"` |
| TS-S-015 | Missing copyrightOwners | `copyrightOwners=null` | `IllegalArgumentException: "Copyright owner(s) is required"` |
| TS-S-016 | Missing countryOfOrigin | `countryOfOrigin=null` | `IllegalArgumentException: "Country of origin is required"` |
| TS-S-017 | Missing copyrightStatus | `copyrightStatus=null` | `IllegalArgumentException: "Copyright status is required"` |
| TS-S-018 | Missing sourceOfInformation | `sourceOfInformation=null` | `IllegalArgumentException: "Source of information is required"` |

### 1.3 Temporal Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-020 | Creation date in future rejected | `creationDate` = tomorrow | `IllegalArgumentException: "Creation date must be today or earlier"` |
| TS-S-021 | Creation date today accepted | `creationDate` = today | No exception |
| TS-S-022 | Publication before creation rejected | `publicationReleaseDate` < `creationDate` | `IllegalArgumentException: "Publication date must be >= creation date"` |
| TS-S-023 | Publication equal to creation accepted | Both same date | No exception |
| TS-S-024 | Registration date in future rejected | `registrationDate` = tomorrow | `IllegalArgumentException: "Registration date must be today or earlier"` |
| TS-S-025 | Protection end before start rejected | `protectionEndDate` < `protectionStartDate` | `IllegalArgumentException: "Protection end date must be >= start date"` |
| TS-S-026 | License end before start rejected | `licenseEndDate` < `licenseStartDate` | `IllegalArgumentException: "License end date must be >= start date"` |

### 1.4 Decimal Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-030 | Ownership percentage at 0 accepted | `ownershipPercentage=0.00` | No exception |
| TS-S-031 | Ownership percentage at 100 accepted | `ownershipPercentage=100.00` | No exception |
| TS-S-032 | Ownership percentage > 100 rejected | `ownershipPercentage=101.00` | Validation error |
| TS-S-033 | Royalty rate at 50 accepted | `royaltyRate=50.00` | No exception |
| TS-S-034 | Market relevance score accepted | `marketRelevanceScore=75.50` | No exception |

### 1.5 Enum Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-040 | Valid WorkType accepted | `workType=TEXT` | No exception |
| TS-S-041 | All WorkTypes accepted | TEXT, IMAGE, SOFTWARE, MUSIC, VIDEO, DESIGN, DATABASE, OTHER | All 8 accepted |
| TS-S-042 | Valid CopyrightStatus accepted | `copyrightStatus=ACTIVE` | No exception |
| TS-S-043 | Valid RiskLevel accepted | `infringementRisk=HIGH` | No exception |

### 1.6 Uniqueness Validation

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-050 | Duplicate copyrightId on create rejected | Same copyrightId as existing | `IllegalArgumentException: "Copyright ID must be unique"` |
| TS-S-051 | Same copyrightId on update (same record) accepted | Updating own record | No exception (excludeId applied) |
| TS-S-052 | Duplicate registrationNumber rejected | Same registrationNumber | `IllegalArgumentException: "Registration number must be unique"` |
| TS-S-053 | Null registrationNumber bypasses uniqueness | `registrationNumber=null` | No exception |

### 1.7 Optional Field Handling

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-060 | Null optional fields accepted | Optional fields = null | No exception |
| TS-S-061 | Empty optional fields converted to null | Optional strings = "" | Converted to `null` |

### 1.8 Update Behavior

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-S-070 | Update with all fields | Valid payload | All fields updated |
| TS-S-071 | Update partial fields | Only some fields | Only provided updated; others unchanged |

---

## 2. Controller Tests (`CopyrightController`)

### 2.1 GET Endpoints

| ID | Scenario | Endpoint | Expected Response |
|----|----------|----------|-------------------|
| TS-C-001 | List page loads | `GET /copyrights` | `200 OK`, renders `copyrights/list` |
| TS-C-002 | Create form loads with defaults | `GET /copyrights/new` | `200 OK`, model contains `item` with defaults |
| TS-C-003 | Edit form loads for valid ID | `GET /copyrights/1/edit` | `200 OK`, form pre-filled with data |

### 2.2 POST — Create

| ID | Scenario | Input | Expected Response |
|----|----------|-------|-------------------|
| TS-C-010 | Valid create redirects to list | Valid payload | `302 redirect to /copyrights` |
| TS-C-011 | Missing required field re-renders form | Missing field | `200 OK`, form with field error |
| TS-C-012 | Invalid enum value re-renders form | `workType="INVALID"` | `200 OK`, form with error |
| TS-C-013 | Duplicate copyrightId re-renders form | Duplicate ID | `200 OK`, form with global error |

### 2.3 POST — Update

| ID | Scenario | Input | Expected Response |
|----|----------|-------|-------------------|
| TS-C-020 | Valid update redirects to list | Valid payload | `302 redirect to /copyrights` |
| TS-C-021 | Validation failure re-renders form | Missing required | `200 OK`, form with errors, `isEdit=true` |

### 2.4 POST — Delete

| ID | Scenario | Input | Expected Response |
|----|----------|--------|-------------------|
| TS-C-030 | Delete valid record redirects | `POST /copyrights/1/delete` | `302 redirect to /copyrights` |

---

## 3. UI / Template Scenarios

### 3.1 List Page

| ID | Scenario | Steps | Expected Result |
|----|----------|-------|-----------------|
| TS-U-001 | List columns visible | Open `/copyrights` | Columns: copyrightId, title, authors, workType, copyrightStatus shown |
| TS-U-002 | Empty list message | No data | `"No copyrights found"` message |

### 3.2 Form Page

| ID | Scenario | Steps | Expected Result |
|----|----------|-------|-----------------|
| TS-U-010 | Form fields grouped by section | Open form | Sections: Work Identity, Ownership, Legal, Rights, Licensing, Financial, Risk, Metadata |
| TS-U-011 | Required fields marked | Open form | Required fields have `*` indicator |
| TS-U-012 | Defaults populated on create | `GET /copyrights/new` | workType=OTHER, copyrightStatus=PENDING, risk levels=MEDIUM |

---

## 4. Edge Cases & Boundary Conditions

| ID | Scenario | Input | Expected Result |
|----|----------|-------|-----------------|
| TS-E-001 | copyrightId at max length (100 chars) | 100 chars | Accepted |
| TS-E-002 | copyrightId exceeds max (101 chars) | 101+ chars | Validation error |
| TS-E-003 | Title at max length (255 chars) | 255 chars | Accepted |
| TS-E-004 | Date fields with valid format | `YYYY-MM-DD` | Accepted |
| TS-E-005 | Large decimal value | `9223372036854775807.99` | Accepted (DECIMAL(19,2) max) |
| TS-E-006 | Negative ownership percentage | `-10.00` | Validation error |

---

## 5. Regression Test Suite (Post-Implementation)

### Critical Path
- [ ] TS-S-010 through TS-S-053 (Service validation logic)
- [ ] TS-C-010, TS-C-013, TS-C-020, TS-C-030 (Controller CRUD)
- [ ] TS-U-001 through TS-U-012 (UI basics)

### Regular Suite
- [ ] All TS-S-* (service tests)
- [ ] All TS-C-* (controller tests)
- [ ] All TS-U-* (UI tests)

---

## Test Data Fixtures

**Valid Copyright Record**:
```
copyrightId: COP-2026-001
title: Advanced Data Analysis Framework
workType: SOFTWARE
authors: Jane Smith, John Doe
copyrightOwners: Tech Corp Ltd
countryOfOrigin: Romania
copyrightStatus: ACTIVE
sourceOfInformation: Company Registry
creationDate: 2020-03-15
publicationReleaseDate: 2020-06-01
registrationNumber: RO-2020-0045123
registrationAuthority: OSIM
registrationDate: 2020-06-15
protectionStartDate: 2020-06-15
protectionEndDate: 2090-06-15
workType: SOFTWARE
licenseType: NON_EXCLUSIVE
royaltyRate: 12.50
valuation: 500000.00
```

---

## Manual Test Checklist

Before Phase 1 sign-off:

### Create & Edit
- [ ] Can create a new copyright
- [ ] Can list all created copyrights
- [ ] Can edit existing copyright
- [ ] Can delete copyright
- [ ] Deleted copyright no longer in list

### Validation
- [ ] Required fields enforce
- [ ] Temporal validation works
- [ ] Decimal constraints work
- [ ] Uniqueness checks work
- [ ] Error messages are clear

### Form Functionality
- [ ] All 40+ fields save correctly
- [ ] Defaults applied on create
- [ ] Enums dropdown functional
- [ ] Date pickers work
- [ ] Decimal fields format correctly


