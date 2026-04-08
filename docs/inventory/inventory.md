# Inventory — Comprehensive Specification

**Version**: 1.0 | **Date**: 2026-04-04

---

## Overview

The `Inventory` entity models tangible inventory/equipment assets within an asset-management system. It captures:
- **Identity & classification**: inventoryId, category, subcategory, manufacturer, model, serial number
- **Technical specifications**: power rating, voltage, capacity, dimensions, weight, fuel type, performance metrics
- **Location & ownership**: current location, previous locations, owner, custodian, usage type
- **Condition & maintenance**: condition status, operating hours, maintenance schedule, warranty, service provider
- **Financial data**: purchase price, book value, depreciation method, residual value, insurance value, operating/maintenance costs
- **Compliance & safety**: certifications, permits, regulatory classification, safety class, environmental impact
- **Risk indicators**: failure risk, safety risk, operational criticality, downtime hours, utilization rate
- **Operational metadata**: data source, responsible analyst, tags, notes

This entity supports equipment tracking, maintenance management, financial assessment, compliance tracking, and operational risk evaluation.

---

## Entity Inheritance & Mapping

```
CoreEntity (base)
  ├─ id (Long, primary key)
  ├─ name → remapped to inventoryId via @AttributeOverride
  ├─ createdAt, createdBy
  ├─ updatedAt, updatedBy
  │
  └─ Inventory (extends CoreEntity)
       ├─ Identity: inventoryId (unique), category, subcategory, manufacturer, model, serialNumber (unique),
       │            yearOfManufacture, versionConfiguration, description
       ├─ Technical: powerRating, voltageElectricalRequirements, capacity, dimensions, weight, fuelType,
       │             performanceMetrics, safetyClassCertifications, operatingEnvironmentRequirements
       ├─ Location: currentLocation, previousLocations, owner, custodian, usageType
       ├─ Condition: conditionStatus, operatingHours, maintenanceSchedule, lastMaintenanceDate,
       │             nextMaintenanceDue, maintenanceHistory, warrantyStatus, serviceProvider
       ├─ Financial: purchaseDate, purchasePrice, bookValue, depreciationMethod, depreciationRate,
       │             residualValue, insuranceValue, operatingCost, maintenanceCost
       ├─ Compliance: certificates, inspectionReports, operatingManuals, permitsLicenses,
       │              regulatoryClassification
       ├─ Risk: failureRiskScore, safetyRiskScore, operationalCriticality, downtimeHours,
       │        utilizationRate, environmentalImpactIndicators, complianceFlags
       └─ Metadata: dataSource, responsibleAnalyst, tagsLabels, notesComments
```

---

## Data Model — Detailed Field Specification

### Enums

| Enum | Purpose | Values |
|------|---------|--------|
| `Category` | Main equipment category | CONSTRUCTION, AGRICULTURAL, INDUSTRIAL, MANUFACTURING, OTHER |
| `Subcategory` | Detailed equipment type | EXCAVATOR, CNC_MACHINE, CONVEYOR, COMPRESSOR, PUMP, CRANE, WELDER, DRILL, LATHE, SAWMILL, OTHER |
| `FuelType` | Power source for equipment | DIESEL, ELECTRIC, HYBRID, PETROL, LPG, OTHER |
| `MaintenanceSchedule` | Maintenance approach | PREVENTIVE, PREDICTIVE, CORRECTIVE |
| `ConditionStatus` | Equipment condition | EXCELLENT, GOOD, FAIR, POOR, CRITICAL |
| `UsageType` | Ownership/usage model | OWNED, LEASED, RENTED, SUBCONTRACTED |
| `DepreciationMethod` | Accounting method | STRAIGHT_LINE, DECLINING_BALANCE, OTHER |
| `OperationalCriticality` | Importance to operations | LOW, MEDIUM, HIGH |
| `RegulatoryClassification` | Compliance category | HAZARDOUS, HEAVY_EQUIPMENT, PRESSURE_VESSEL, OTHER |

### Identity & Classification

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `inventoryId` | `inventory_id` | VARCHAR(100) | ✅ | Unique, alphanumeric | Maps to `name` via `@AttributeOverride`; pattern: `^[A-Za-z0-9_-]+$` |
| `category` | `category` | ENUM(50) | ✅ | One of Category | Main classification |
| `subcategory` | `subcategory` | ENUM(50) | ❌ | One of Subcategory | Detailed type |
| `manufacturer` | `manufacturer` | VARCHAR(100) | ✅ | Not blank | Equipment maker |
| `model` | `model` | VARCHAR(100) | ✅ | Not blank | Model designation |
| `serialNumber` | `serial_number` | VARCHAR(100) | ✅ | Unique, not blank | Unique identifier from manufacturer |
| `yearOfManufacture` | `year_of_manufacture` | INTEGER | ✅ | ≤ current year | Year produced |
| `versionConfiguration` | `version_configuration` | VARCHAR(100) | ❌ | Max 100 chars | Variant or configuration |
| `description` | `description` | VARCHAR(1000) | ❌ | Max 1000 chars | Detailed description |

### Technical Specifications

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `powerRating` | `power_rating` | VARCHAR(50) | ❌ | Max 50 chars | e.g., "500 kW", "75 HP" |
| `voltageElectricalRequirements` | `voltage_electrical_requirements` | VARCHAR(50) | ❌ | Max 50 chars | e.g., "480V 3-phase" |
| `capacity` | `capacity` | VARCHAR(100) | ❌ | Max 100 chars | e.g., "10 tons", "500 L/min" |
| `dimensions` | `dimensions` | VARCHAR(100) | ❌ | Max 100 chars | Length × Width × Height |
| `weight` | `weight` | DECIMAL(12,2) | ❌ | ≥ 0 | In kg or lbs |
| `fuelType` | `fuel_type` | ENUM(30) | ❌ | One of FuelType | Power source |
| `performanceMetrics` | `performance_metrics` | VARCHAR(500) | ❌ | Max 500 chars | Speed, efficiency, output |
| `safetyClassCertifications` | `safety_class_certifications` | VARCHAR(100) | ❌ | Max 100 chars | Safety ratings |
| `operatingEnvironmentRequirements` | `operating_environment_requirements` | VARCHAR(200) | ❌ | Max 200 chars | Temperature, humidity, conditions |

### Location & Ownership

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `currentLocation` | `current_location` | VARCHAR(200) | ❌ | Max 200 chars | Facility, building, section |
| `previousLocations` | `previous_locations` | VARCHAR(1000) | ❌ | Max 1000 chars | History of locations |
| `owner` | `owner` | VARCHAR(100) | ❌ | Max 100 chars | Company or individual |
| `custodian` | `custodian` | VARCHAR(100) | ❌ | Max 100 chars | Person responsible |
| `usageType` | `usage_type` | ENUM(30) | ❌ | One of UsageType | OWNED, LEASED, RENTED, SUBCONTRACTED |

### Condition & Maintenance

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `conditionStatus` | `condition_status` | ENUM(20) | ❌ | One of ConditionStatus | EXCELLENT to CRITICAL |
| `operatingHours` | `operating_hours` | BIGINT | ❌ | ≥ 0 | Cumulative hours |
| `maintenanceSchedule` | `maintenance_schedule` | ENUM(30) | ❌ | One of MaintenanceSchedule | Maintenance approach |
| `lastMaintenanceDate` | `last_maintenance_date` | DATE | ❌ | ≤ today | Last service date |
| `nextMaintenanceDue` | `next_maintenance_due` | DATE | ❌ | None | Scheduled next service |
| `maintenanceHistory` | `maintenance_history` | VARCHAR(4000) | ❌ | Max 4000 chars | Service records |
| `warrantyStatus` | `warranty_status` | VARCHAR(100) | ❌ | Max 100 chars | Active/Expired/Voided |
| `serviceProvider` | `service_provider` | VARCHAR(100) | ❌ | Max 100 chars | Maintenance contractor |

### Financial Data

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `purchaseDate` | `purchase_date` | DATE | ❌ | ≤ today | Acquisition date |
| `purchasePrice` | `purchase_price` | DECIMAL(19,2) | ❌ | ≥ 0 | Original cost |
| `bookValue` | `book_value` | DECIMAL(19,2) | ❌ | ≥ 0 | Accounting value |
| `depreciationMethod` | `depreciation_method` | ENUM(30) | ❌ | One of DepreciationMethod | Accounting method |
| `depreciationRate` | `depreciation_rate` | DECIMAL(7,2) | ❌ | ≥ 0 | Annual % |
| `residualValue` | `residual_value` | DECIMAL(19,2) | ❌ | ≥ 0 | Salvage value |
| `insuranceValue` | `insurance_value` | DECIMAL(19,2) | ❌ | ≥ 0 | Coverage amount |
| `operatingCost` | `operating_cost` | DECIMAL(19,2) | ❌ | ≥ 0 | Annual operating expense |
| `maintenanceCost` | `maintenance_cost` | DECIMAL(19,2) | ❌ | ≥ 0 | Annual maintenance expense |

### Compliance & Documentation

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `certificates` | `certificates` | VARCHAR(500) | ❌ | Max 500 chars | ISO, CE, UL, etc. |
| `inspectionReports` | `inspection_reports` | VARCHAR(1000) | ❌ | Max 1000 chars | Audit/inspection results |
| `operatingManuals` | `operating_manuals` | VARCHAR(500) | ❌ | Max 500 chars | Manual references |
| `permitsLicenses` | `permits_licenses` | VARCHAR(500) | ❌ | Max 500 chars | Regulatory permits |
| `regulatoryClassification` | `regulatory_classification` | ENUM(50) | ❌ | One of RegulatoryClassification | HAZARDOUS, HEAVY_EQUIPMENT, etc. |

### Risk & Assessment

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `failureRiskScore` | `failure_risk_score` | DECIMAL(7,2) | ❌ | 0–100 | Failure probability |
| `safetyRiskScore` | `safety_risk_score` | DECIMAL(7,2) | ❌ | 0–100 | Safety hazard score |
| `operationalCriticality` | `operational_criticality` | ENUM(20) | ❌ | One of OperationalCriticality | LOW, MEDIUM, HIGH |
| `downtimeHours` | `downtime_hours` | BIGINT | ❌ | ≥ 0 | Cumulative downtime |
| `utilizationRate` | `utilization_rate` | DECIMAL(7,2) | ❌ | 0–100 | % time in use |
| `environmentalImpactIndicators` | `environmental_impact_indicators` | VARCHAR(200) | ❌ | Max 200 chars | Emissions, waste, efficiency |
| `complianceFlags` | `compliance_flags` | VARCHAR(1000) | ❌ | Max 1000 chars | Regulatory issues |

### Operational Metadata

| Field | Column | Type | Required | Constraints | Notes |
|-------|--------|------|----------|-------------|-------|
| `dataSource` | `data_source` | VARCHAR(100) | ✅ | Not blank | Manual entry, ERP, scanner |
| `responsibleAnalyst` | `responsible_analyst` | VARCHAR(100) | ❌ | Max 100 chars | Accountable person |
| `tagsLabels` | `tags_labels` | VARCHAR(500) | ❌ | Max 500 chars | Categorization labels |
| `notesComments` | `notes_comments` | VARCHAR(2000) | ❌ | Max 2000 chars | Free-form notes |

---

## Validation Rules

### Field-Level Rules

| Field | Rule | Error Message |
|-------|------|---------------|
| `inventoryId` | Required, max 100, alphanumeric | `"Inventory ID is required"` |
| `category` | Required enum | `"Category is required"` |
| `manufacturer` | Required, max 100 | `"Manufacturer is required"` |
| `model` | Required, max 100 | `"Model is required"` |
| `serialNumber` | Required, max 100 | `"Serial number is required"` |
| `yearOfManufacture` | Required, ≤ current year | `"Year of manufacture must be <= current year"` |
| `dataSource` | Required, max 100 | `"Data source is required"` |
| `weight` | If present, ≥ 0 | `"Weight must be >= 0"` |
| `operatingHours` | If present, ≥ 0 | `"Operating hours must be >= 0"` |
| `depreciationRate` | If present, ≥ 0 | `"Depreciation rate must be >= 0"` |
| `failureRiskScore`, `safetyRiskScore` | If present, 0–100 | Validation error |
| `utilizationRate` | If present, 0–100 | Validation error |

### Uniqueness Rules

| Rule | Error Message | Notes |
|------|---------------|-------|
| `inventoryId` globally unique | `"Inventory ID must be unique"` | Checked on create and update (excludeId) |
| `serialNumber` globally unique | `"Serial number must be unique"` | Mandatory, unique per manufacturer |

### Business Rules

- **Normalization**: `inventoryId` is normalized (alphanumeric check, trim) before validation
- **Temporal validation**: `yearOfManufacture` ≤ current year
- **Decimal constraints**: Risk scores, utilization rate, depreciation rate all 0–100 if present
- **Optional rich fields**: All fields except core identity and dataSource are optional

---

## Service-Layer Behavior

### Normalization Pipeline

Before validation, the service normalizes:
1. Trim all string fields
2. Validate inventoryId pattern (alphanumeric)
3. Convert empty strings to `null`

### Error Handling

Service throws `IllegalArgumentException` with descriptive message on:
- Missing required fields
- Uniqueness violation (inventoryId, serialNumber)
- Validation failure (year, decimals)

Controllers catch these exceptions and add them as global form errors.

---

## Default Values & Enums

### Defaults on Create Form
- `category` → `OTHER`
- `conditionStatus` → `GOOD`
- `usageType` → `OWNED`
- `depreciationMethod` → `STRAIGHT_LINE`
- `maintenanceSchedule` → `PREVENTIVE`
- `operationalCriticality` → `MEDIUM`
- `dataSource` → `"Manual Entry"`

---

## Phase-Based Development

### Phase 1 ✅ Completed (v1)
- [x] Inventory entity with 50+ fields
- [x] 8 enums with detailed values
- [x] @AttributeOverride for inventoryId
- [x] InventoryRepository with uniqueness queries
- [x] InventoryService with normalize/validate/CRUD
- [x] InventoryController with list, create, edit, update, delete
- [x] `inventories/list.html` and `inventories/form.html` templates
- [x] Validation error handling

### Phase 2 (Planned)
- [ ] Detail page `inventories/detail.html`
- [ ] Filtering by category, condition, criticality
- [ ] Sorting and pagination
- [ ] Service unit tests

---

## Known Limitations & Out of Scope (v1)

- No real-time maintenance alert generation
- No ERP system integration
- No automated depreciation calculation
- No failure prediction modeling
- No soft-delete; hard delete only
- No authentication/authorization

---

## Sources & References

- `docs/inventory.md` – High-level inventory overview
- `src/main/java/com/sdr/ams/model/tangible/Inventory.java` – Entity definition
- `src/main/java/com/sdr/ams/repository/InventoryRepository.java` – Data access
- `src/main/java/com/sdr/ams/service/InventoryService.java` – Business logic
- `src/main/java/com/sdr/ams/controller/InventoryController.java` – Web routes
- `src/main/resources/templates/inventories/` – UI templates

