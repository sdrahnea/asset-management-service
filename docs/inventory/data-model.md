# Inventory — Data Model

**Version**: 1.0 | **Date**: 2026-04-04 | **Table**: `inventory`

`Inventory` extends `CoreEntity`. `name` overridden to `inventory_id` via `@AttributeOverride`.

---

## Enums
| Enum | Values |
|------|--------|
| `Category` | CONSTRUCTION, AGRICULTURAL, INDUSTRIAL, MANUFACTURING, OTHER |
| `Subcategory` | EXCAVATOR, CNC_MACHINE, CONVEYOR, COMPRESSOR, PUMP, CRANE, WELDER, DRILL, LATHE, SAWMILL, OTHER |
| `FuelType` | DIESEL, ELECTRIC, HYBRID, PETROL, LPG, OTHER |
| `MaintenanceSchedule` | PREVENTIVE, PREDICTIVE, CORRECTIVE |
| `ConditionStatus` | EXCELLENT, GOOD, FAIR, POOR, CRITICAL |
| `UsageType` | OWNED, LEASED, RENTED, SUBCONTRACTED |
| `DepreciationMethod` | STRAIGHT_LINE, DECLINING_BALANCE, OTHER |
| `OperationalCriticality` | LOW, MEDIUM, HIGH |
| `RegulatoryClassification` | HAZARDOUS, HEAVY_EQUIPMENT, PRESSURE_VESSEL, OTHER |

---

## Key Fields (50+)

### Identity & Classification
| Field | Type | Required |
|-------|------|----------|
| `inventoryId` | VARCHAR(100) | ✅ (unique) |
| `category` | ENUM | ✅ |
| `subcategory` | ENUM | ❌ |
| `manufacturer` | VARCHAR(100) | ✅ |
| `model` | VARCHAR(100) | ✅ |
| `serialNumber` | VARCHAR(100) | ✅ (unique) |
| `yearOfManufacture` | INTEGER | ✅ |
| `versionConfiguration` | VARCHAR(100) | ❌ |
| `description` | VARCHAR(1000) | ❌ |

### Technical Specifications (9 fields)
| Field | Type | Required |
|-------|------|----------|
| `powerRating` | VARCHAR(50) | ❌ |
| `voltageElectricalRequirements` | VARCHAR(50) | ❌ |
| `capacity` | VARCHAR(100) | ❌ |
| `dimensions` | VARCHAR(100) | ❌ |
| `weight` | DECIMAL(12,2) | ❌ |
| `fuelType` | ENUM | ❌ |
| `performanceMetrics` | VARCHAR(500) | ❌ |
| `safetyClassCertifications` | VARCHAR(100) | ❌ |
| `operatingEnvironmentRequirements` | VARCHAR(200) | ❌ |

### Location & Ownership (5 fields)
| Field | Type | Required |
|-------|------|----------|
| `currentLocation` | VARCHAR(200) | ❌ |
| `previousLocations` | VARCHAR(1000) | ❌ |
| `owner` | VARCHAR(100) | ❌ |
| `custodian` | VARCHAR(100) | ❌ |
| `usageType` | ENUM | ❌ |

### Condition & Maintenance (8 fields)
| Field | Type | Required |
|-------|------|----------|
| `conditionStatus` | ENUM | ❌ |
| `operatingHours` | BIGINT | ❌ |
| `maintenanceSchedule` | ENUM | ❌ |
| `lastMaintenanceDate` | DATE | ❌ |
| `nextMaintenanceDue` | DATE | ❌ |
| `maintenanceHistory` | VARCHAR(4000) | ❌ |
| `warrantyStatus` | VARCHAR(100) | ❌ |
| `serviceProvider` | VARCHAR(100) | ❌ |

### Financial Data (9 fields)
| Field | Type | Required |
|-------|------|----------|
| `purchaseDate` | DATE | ❌ |
| `purchasePrice` | DECIMAL(19,2) | ❌ |
| `bookValue` | DECIMAL(19,2) | ❌ |
| `depreciationMethod` | ENUM | ❌ |
| `depreciationRate` | DECIMAL(7,2) | ❌ |
| `residualValue` | DECIMAL(19,2) | ❌ |
| `insuranceValue` | DECIMAL(19,2) | ❌ |
| `operatingCost` | DECIMAL(19,2) | ❌ |
| `maintenanceCost` | DECIMAL(19,2) | ❌ |

### Compliance & Documentation (5 fields)
| Field | Type | Required |
|-------|------|----------|
| `certificates` | VARCHAR(500) | ❌ |
| `inspectionReports` | VARCHAR(1000) | ❌ |
| `operatingManuals` | VARCHAR(500) | ❌ |
| `permitsLicenses` | VARCHAR(500) | ❌ |
| `regulatoryClassification` | ENUM | ❌ |

### Risk & Assessment (7 fields)
| Field | Type | Required |
|-------|------|----------|
| `failureRiskScore` | DECIMAL(7,2) | ❌ |
| `safetyRiskScore` | DECIMAL(7,2) | ❌ |
| `operationalCriticality` | ENUM | ❌ |
| `downtimeHours` | BIGINT | ❌ |
| `utilizationRate` | DECIMAL(7,2) | ❌ |
| `environmentalImpactIndicators` | VARCHAR(200) | ❌ |
| `complianceFlags` | VARCHAR(1000) | ❌ |

### Operational Metadata (4 fields)
| Field | Type | Required |
|-------|------|----------|
| `dataSource` | VARCHAR(100) | ✅ |
| `responsibleAnalyst` | VARCHAR(100) | ❌ |
| `tagsLabels` | VARCHAR(500) | ❌ |
| `notesComments` | VARCHAR(2000) | ❌ |

---

## Tasks

### Phase 1 ✅ Done
- [x] 50+ field entity with 8 enums
- [x] Unique constraints (inventoryId, serialNumber)
- [x] Validation annotations
- [x] Repository queries
- [x] Service CRUD
- [x] Controller routes
- [x] Templates (list, form)

### Phase 2 (Planned)
- [ ] Detail page
- [ ] Filtering
- [ ] Tests

---

## API Surface

| Method | Path | Description |
|--------|------|-------------|
| GET | `/inventories` | List |
| GET | `/inventories/new` | Create form |
| POST | `/inventories` | Submit create |
| GET | `/inventories/{id}/edit` | Edit form |
| POST | `/inventories/{id}` | Submit update |
| POST | `/inventories/{id}/delete` | Delete |
| GET | `/inventories/{id}` | Detail (planned) |

