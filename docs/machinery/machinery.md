# Machinery — Comprehensive Specification

**Version**: 1.0 | **Date**: 2026-04-04

## Overview
`Machinery` models high-value equipment assets for operational, financial, maintenance, and compliance assessment.

## Core Model Structure
`Machinery` extends `CoreEntity` and remaps `name` to `machine_id` using `@AttributeOverride`.

### Required fields
- `machineId` (unique, alphanumeric)
- `category`
- `manufacturer`
- `model`
- `serialNumber` (unique)
- `yearOfManufacture`
- `dataSource`

### Major sections
- Identity and classification
- Technical specifications
- Location and ownership
- Maintenance and condition
- Financial and valuation
- Compliance and documentation
- Risk and assessment indicators
- Operational metadata

## Enums
- `Category`: CONSTRUCTION, AGRICULTURAL, INDUSTRIAL, MANUFACTURING, OTHER
- `Subcategory`: EXCAVATOR, CNC_MACHINE, CONVEYOR, COMPRESSOR, PUMP, CRANE, WELDER, DRILL, LATHE, SAWMILL, OTHER
- `FuelType`: DIESEL, ELECTRIC, HYBRID, PETROL, LPG, OTHER
- `MaintenanceSchedule`: PREVENTIVE, PREDICTIVE, CORRECTIVE
- `ConditionStatus`: EXCELLENT, GOOD, FAIR, POOR, CRITICAL
- `UsageType`: OWNED, LEASED, RENTED, SUBCONTRACTED
- `DepreciationMethod`: STRAIGHT_LINE, DECLINING_BALANCE, OTHER
- `OperationalCriticality`: LOW, MEDIUM, HIGH
- `RegulatoryClassification`: HAZARDOUS, HEAVY_EQUIPMENT, PRESSURE_VESSEL, OTHER

## Key Business Rules
- `machineId` must match `^[A-Za-z0-9_-]+$`
- `yearOfManufacture` must be less than or equal to current year
- `utilizationRate` must be 0..100
- `purchasePrice`, `bookValue`, `residualValue`, `insuranceValue`, `operatingCost`, `maintenanceCost` must be >= 0
- `operatingHours` and `downtimeHours` must be >= 0
- `machineId` and `serialNumber` are globally unique

## Service Behavior
- Normalize string input (trim, empty-to-null)
- Validate required fields and ranges
- Validate uniqueness with excludeId behavior for updates
- Throw `IllegalArgumentException` for business/uniqueness issues

## Phase Summary
### Phase 1 (implemented)
- Full entity + constraints + CRUD + form/list templates

### Phase 2 (planned)
- Detail page, filtering, sorting, pagination, focused tests

### Phase 3 (planned)
- API endpoints, integrations, monitoring/alerting

