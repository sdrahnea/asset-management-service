# Real Estate — Data Model

**Version**: 1.0 | **Date**: 2026-04-04 | **Table**: `real_estate`

## Unique Constraints
- `cadastral_number`
- `land_registry_number`

## Major Field Groups
### Identity/Classification
- `title`, `propertyType`, `subType`, `description`, `yearBuilt`, `constructionType`, `condition`

### Location/Land
- `address`, `latitude`, `longitude`, `zoningType`, `landArea`, `landShape`, `topography`, `accessRoadType`, `utilitiesAvailability`

### Building/Features
- `builtArea`, `usableArea`, `floors`, `rooms`, `bathrooms`, `balconies`, `heatingType`, `energyEfficiencyClass`, `parking`, `amenities`

### Legal/Ownership
- `encumbrances`, `buildingPermits`, `usageRestrictions`, `ownershipType`, `ownerName`, `cadastralNumber`, `landRegistryNumber`

### Financial/Valuation
- `currentMarketValue`, `valuationDate`, `purchasePrice`, `rentalIncome`, `operatingCosts`, `taxes`, `insuranceValue`, `yieldIndicators`, `depreciationMethod`

### Neighborhood/Market
- `neighborhoodType`, `proximityToTransport`, `nearbyFacilities`, `marketTrends`, `environmentalRisks`

### Maintenance/Risk
- `maintenanceStatus`, `lastRenovationDate`, `renovationDetails`, `structuralIssues`, `expectedCapex`
- `legalRiskScore`, `marketRiskScore`, `structuralRiskScore`, `environmentalRiskScore`, `overallAssessmentScore`, `complianceFlags`

### Metadata
- `dataSource`, `responsibleAgent`, `tags`, `notes`

## Key Rules
- Required by service: title, address, ownerName, cadastralNumber, landRegistryNumber, dataSource, propertyType, ownershipType, landArea, currentMarketValue, valuationDate
- Year/date/area/coordinate consistency checks enforced in service

