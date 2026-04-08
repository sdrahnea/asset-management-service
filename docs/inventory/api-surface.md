# Inventory — Application Surface / Route Map

**Version**: 1.0 | **Date**: 2026-04-04

---

## Route Summary

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/inventories` | List all inventories |
| `GET` | `/inventories/new` | Create form |
| `POST` | `/inventories` | Submit create |
| `GET` | `/inventories/{id}/edit` | Edit form |
| `POST` | `/inventories/{id}` | Submit update |
| `POST` | `/inventories/{id}/delete` | Delete |
| `GET` | `/inventories/{id}` | Detail (Phase 2) |

---

## Request/Response Contracts

### GET `/inventories` — List

**Response**: `200 OK`, renders `inventories/list.html`

**Model**:
- `items` – List<Inventory>
- `singularTitle` – "Inventory"
- `pluralTitle` – "Inventories"
- `basePath` – "/inventories"

---

### GET `/inventories/new` — Create Form

**Response**: `200 OK`, renders `inventories/form.html`

**Model**:
- `item` – Empty Inventory with defaults
- `isEdit` – false
- Enum dropdowns for: Category, Subcategory, FuelType, MaintenanceSchedule, ConditionStatus, UsageType, DepreciationMethod, OperationalCriticality, RegulatoryClassification

**Defaults**:
- `category` → OTHER
- `conditionStatus` → GOOD
- `usageType` → OWNED
- `depreciationMethod` → STRAIGHT_LINE
- `maintenanceSchedule` → PREVENTIVE
- `operationalCriticality` → MEDIUM

---

### POST `/inventories` — Create

**Form Fields** (50+ fields):

**Identity**:
- `name` (remapped to `inventoryId`) ✅
- `category` ✅
- `subcategory` ❌
- `manufacturer` ✅
- `model` ✅
- `serialNumber` ✅
- `yearOfManufacture` ✅
- `versionConfiguration` ❌
- `description` ❌

**Technical**:
- `powerRating` ❌
- `voltageElectricalRequirements` ❌
- `capacity` ❌
- `dimensions` ❌
- `weight` ❌
- `fuelType` ❌
- `performanceMetrics` ❌
- `safetyClassCertifications` ❌
- `operatingEnvironmentRequirements` ❌

**Location**:
- `currentLocation` ❌
- `previousLocations` ❌
- `owner` ❌
- `custodian` ❌
- `usageType` ❌

**Condition**:
- `conditionStatus` ❌
- `operatingHours` ❌
- `maintenanceSchedule` ❌
- `lastMaintenanceDate` ❌
- `nextMaintenanceDue` ❌
- `maintenanceHistory` ❌
- `warrantyStatus` ❌
- `serviceProvider` ❌

**Financial**:
- `purchaseDate` ❌
- `purchasePrice` ❌
- `bookValue` ❌
- `depreciationMethod` ❌
- `depreciationRate` ❌
- `residualValue` ❌
- `insuranceValue` ❌
- `operatingCost` ❌
- `maintenanceCost` ❌

**Compliance**:
- `certificates` ❌
- `inspectionReports` ❌
- `operatingManuals` ❌
- `permitsLicenses` ❌
- `regulatoryClassification` ❌

**Risk**:
- `failureRiskScore` ❌
- `safetyRiskScore` ❌
- `operationalCriticality` ❌
- `downtimeHours` ❌
- `utilizationRate` ❌
- `environmentalImpactIndicators` ❌
- `complianceFlags` ❌

**Metadata**:
- `dataSource` ✅
- `responsibleAnalyst` ❌
- `tagsLabels` ❌
- `notesComments` ❌

**Success**: `302 redirect to /inventories`  
**Failure**: `200 OK`, form re-rendered with errors

---

### GET `/inventories/{id}/edit` — Edit Form

**Model**: Same as create form with `item` pre-populated, `isEdit=true`

---

### POST `/inventories/{id}` — Update

**Fields**: Same as create

**Success**: `302 redirect to /inventories`  
**Failure**: `200 OK`, form re-rendered

---

### POST `/inventories/{id}/delete` — Delete

**Response**: `302 redirect to /inventories`

---

## Form Organization (by section)

```
IDENTITY & CLASSIFICATION
├─ Inventory ID *
├─ Category *
├─ Subcategory
├─ Manufacturer *
├─ Model *
├─ Serial Number *
├─ Year of Manufacture *
├─ Version/Configuration
└─ Description

TECHNICAL SPECIFICATIONS
├─ Power Rating
├─ Voltage/Electrical Requirements
├─ Capacity
├─ Dimensions
├─ Weight
├─ Fuel Type
├─ Performance Metrics
├─ Safety Class/Certifications
└─ Operating Environment

LOCATION & OWNERSHIP
├─ Current Location
├─ Previous Locations
├─ Owner
├─ Custodian
└─ Usage Type

CONDITION & MAINTENANCE
├─ Condition Status
├─ Operating Hours
├─ Maintenance Schedule
├─ Last Maintenance Date
├─ Next Maintenance Due
├─ Maintenance History
├─ Warranty Status
└─ Service Provider

FINANCIAL & VALUATION
├─ Purchase Date
├─ Purchase Price
├─ Book Value
├─ Depreciation Method
├─ Depreciation Rate
├─ Residual Value
├─ Insurance Value
├─ Operating Cost
└─ Maintenance Cost

COMPLIANCE & DOCUMENTATION
├─ Certificates
├─ Inspection Reports
├─ Operating Manuals
├─ Permits/Licenses
└─ Regulatory Classification

RISK & ASSESSMENT
├─ Failure Risk Score
├─ Safety Risk Score
├─ Operational Criticality
├─ Downtime Hours
├─ Utilization Rate
├─ Environmental Impact Indicators
└─ Compliance Flags

METADATA
├─ Data Source *
├─ Responsible Analyst
├─ Tags/Labels
└─ Notes/Comments

AUDIT (Read-Only)
├─ Created At
├─ Created By
├─ Updated At
└─ Updated By
```

---

## Enum Options

| Enum | Values |
|------|--------|
| **Category** | CONSTRUCTION, AGRICULTURAL, INDUSTRIAL, MANUFACTURING, OTHER |
| **Subcategory** | EXCAVATOR, CNC_MACHINE, CONVEYOR, COMPRESSOR, PUMP, CRANE, WELDER, DRILL, LATHE, SAWMILL, OTHER |
| **FuelType** | DIESEL, ELECTRIC, HYBRID, PETROL, LPG, OTHER |
| **MaintenanceSchedule** | PREVENTIVE, PREDICTIVE, CORRECTIVE |
| **ConditionStatus** | EXCELLENT, GOOD, FAIR, POOR, CRITICAL |
| **UsageType** | OWNED, LEASED, RENTED, SUBCONTRACTED |
| **DepreciationMethod** | STRAIGHT_LINE, DECLINING_BALANCE, OTHER |
| **OperationalCriticality** | LOW, MEDIUM, HIGH |
| **RegulatoryClassification** | HAZARDOUS, HEAVY_EQUIPMENT, PRESSURE_VESSEL, OTHER |

---

## Validation Error Messages

| Field | Error |
|-------|-------|
| `inventoryId` | `"Inventory ID is required"` |
| `category` | `"Category is required"` |
| `manufacturer` | `"Manufacturer is required"` |
| `model` | `"Model is required"` |
| `serialNumber` | `"Serial number is required"` |
| `yearOfManufacture` | `"Year of manufacture is required"` |
| `dataSource` | `"Data source is required"` |
| `yearOfManufacture > current` | `"Year must be <= current year"` |
| Duplicate `inventoryId` | `"Inventory ID must be unique"` |
| Duplicate `serialNumber` | `"Serial number must be unique"` |
| Risk scores > 100 | Validation error |
| `utilizationRate` > 100 | Validation error |
| Weight < 0 | Validation error |

---

## Quick Reference

| Endpoint | Purpose |
|----------|---------|
| `/inventories` | List all |
| `/inventories/new` | Create form |
| `/inventories/{id}/edit` | Edit form |
| `/inventories/{id}` | Detail (Phase 2) |

