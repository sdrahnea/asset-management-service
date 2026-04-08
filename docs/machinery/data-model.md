# Machinery — Data Model

**Version**: 1.0 | **Date**: 2026-04-04 | **Table**: `machinery`

`Machinery` extends `CoreEntity`. Base `name` is overridden as `machine_id`.

## Enums
| Enum | Values |
|------|--------|
| Category | CONSTRUCTION, AGRICULTURAL, INDUSTRIAL, MANUFACTURING, OTHER |
| Subcategory | EXCAVATOR, CNC_MACHINE, CONVEYOR, COMPRESSOR, PUMP, CRANE, WELDER, DRILL, LATHE, SAWMILL, OTHER |
| FuelType | DIESEL, ELECTRIC, HYBRID, PETROL, LPG, OTHER |
| MaintenanceSchedule | PREVENTIVE, PREDICTIVE, CORRECTIVE |
| ConditionStatus | EXCELLENT, GOOD, FAIR, POOR, CRITICAL |
| UsageType | OWNED, LEASED, RENTED, SUBCONTRACTED |
| DepreciationMethod | STRAIGHT_LINE, DECLINING_BALANCE, OTHER |
| OperationalCriticality | LOW, MEDIUM, HIGH |
| RegulatoryClassification | HAZARDOUS, HEAVY_EQUIPMENT, PRESSURE_VESSEL, OTHER |

## Key Fields
### Identity
- `machineId` (required, unique, pattern)
- `category` (required)
- `subcategory`
- `manufacturer` (required)
- `model` (required)
- `serialNumber` (required, unique)
- `yearOfManufacture` (required)
- `versionConfiguration`
- `description`

### Technical
- `powerRating`, `voltageElectricalRequirements`, `capacity`, `dimensions`, `weight`, `fuelType`
- `performanceMetrics`, `safetyClassCertifications`, `operatingEnvironmentRequirements`

### Location/Ownership
- `currentLocation`, `previousLocations`, `owner`, `custodian`, `usageType`

### Maintenance/Condition
- `conditionStatus`, `operatingHours`, `maintenanceSchedule`
- `lastMaintenanceDate`, `nextMaintenanceDue`, `maintenanceHistory`
- `warrantyStatus`, `serviceProvider`

### Financial
- `purchaseDate`, `purchasePrice`, `bookValue`, `depreciationMethod`, `depreciationRate`
- `residualValue`, `insuranceValue`, `operatingCost`, `maintenanceCost`

### Compliance
- `certificates`, `inspectionReports`, `operatingManuals`, `permitsLicenses`, `regulatoryClassification`

### Risk
- `failureRiskScore`, `safetyRiskScore`, `operationalCriticality`, `downtimeHours`, `utilizationRate`
- `environmentalImpactIndicators`, `complianceFlags`

### Metadata
- `dataSource` (required), `responsibleAnalyst`, `tagsLabels`, `notesComments`

## Constraints
- Unique: `machine_id`, `serial_number`
- AssertTrue: year must be <= current year
- Multiple decimal non-negative constraints on valuation/cost fields

