# Vehicle — Data Model

**Version**: 1.0 | **Date**: 2026-04-04 | **Table**: `vehicle`

`Vehicle` extends `CoreEntity`; base `name` maps to `vehicle_id`.

## Unique Constraints
- `vehicle_id`
- `vin`
- `(license_plate, registration_country)`

## Field Groups
### Identity/Classification
- `vehicleId`, `vin`, `licensePlate`, `vehicleType`, `make`, `model`, `trimVariant`, `yearOfManufacture`, `bodyType`, `color`

### Technical
- `engineType`, `engineCapacity`, `powerOutput`, `torque`, `transmission`, `drivetrain`, `fuelConsumption`, `batteryCapacity`, `electricRangeKm`, `emissionStandard`, `dimensions`, `grossWeight`, `payloadCapacity`

### Registration/Legal
- `registrationCountry`, `registrationDate`, `registrationStatus`, `inspectionStatus`, `inspectionExpiryDate`, `insuranceStatus`, `insuranceExpiryDate`, `taxStatus`, `homologationDocuments`, `complianceCertificates`

### Ownership/Usage
- `ownerName`, `ownershipType`, `leasingDetails`, `usageCategory`, `currentMileage`, `averageAnnualMileage`, `operatingRegion`, `driverAssignments`

### Maintenance/Condition
- `conditionStatus`, `maintenanceSchedule`, `lastServiceDate`, `nextServiceDue`, `serviceHistory`, `accidentHistory`, `faultCodes`, `warrantyStatus`

### Financial/Valuation
- `purchasePrice`, `purchaseDate`, `currentMarketValue`, `valuationDate`, `depreciationMethod`, `depreciationRate`, `operatingCosts`, `insuranceCost`, `taxCost`

### Risk/Assessment
- `mechanicalRiskScore`, `accidentRiskScore`, `operationalRiskScore`, `complianceRiskScore`, `environmentalImpactScore`, `overallAssessmentScore`, `complianceFlags`

### Evidence/Metadata
- `registrationDocuments`, `insuranceDocuments`, `inspectionReports`, `serviceInvoices`, `accidentReports`, `photos`, `internalNotes`, `dataSource`, `responsibleAgent`, `notes`

## Key Rules
- Mandatory: vehicleId, vin, licensePlate, vehicleType, make, model, yearOfManufacture, engineType, dataSource
- Temporal: year/date/service ordering rules enforced in service
- Uniqueness: ID/VIN/plate-country enforced in repository and service

