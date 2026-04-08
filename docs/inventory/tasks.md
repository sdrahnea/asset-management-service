# Inventory — Implementation Tasks

**Version**: 1.0 | **Date**: 2026-04-04

---

## Phase 1 — Core CRUD (v1) ✅ Complete

### Data Model
- [x] ✅ TASK-001: Create `Inventory` entity extending `CoreEntity`
- [x] ✅ TASK-002: Add core required fields: inventoryId, category, manufacturer, model, serialNumber, yearOfManufacture, dataSource
- [x] ✅ TASK-003: Add identity fields: subcategory, versionConfiguration, description
- [x] ✅ TASK-004: Add technical specs: powerRating, voltageElectricalRequirements, capacity, dimensions, weight, fuelType, performanceMetrics, safetyClassCertifications, operatingEnvironmentRequirements
- [x] ✅ TASK-005: Add location fields: currentLocation, previousLocations, owner, custodian, usageType
- [x] ✅ TASK-006: Add condition fields: conditionStatus, operatingHours, maintenanceSchedule, lastMaintenanceDate, nextMaintenanceDue, maintenanceHistory, warrantyStatus, serviceProvider
- [x] ✅ TASK-007: Add financial fields: purchaseDate, purchasePrice, bookValue, depreciationMethod, depreciationRate, residualValue, insuranceValue, operatingCost, maintenanceCost
- [x] ✅ TASK-008: Add compliance fields: certificates, inspectionReports, operatingManuals, permitsLicenses, regulatoryClassification
- [x] ✅ TASK-009: Add risk fields: failureRiskScore, safetyRiskScore, operationalCriticality, downtimeHours, utilizationRate, environmentalImpactIndicators, complianceFlags
- [x] ✅ TASK-010: Add metadata fields: responsibleAnalyst, tagsLabels, notesComments
- [x] ✅ TASK-011: Define 8 enums (Category, Subcategory, FuelType, MaintenanceSchedule, ConditionStatus, UsageType, DepreciationMethod, OperationalCriticality, RegulatoryClassification)
- [x] ✅ TASK-012: Add @AttributeOverride for inventoryId
- [x] ✅ TASK-013: Add validation annotations (@NotBlank, @NotNull, @Size, @PositiveOrZero, @Digits, @DecimalMin, @DecimalMax)
- [x] ✅ TASK-014: Add @AssertTrue validator for yearOfManufacture ≤ current year
- [x] ✅ TASK-015: Add unique constraints on inventoryId and serialNumber

### Repository
- [x] ✅ TASK-020: Create `InventoryRepository` extending `JpaRepository<Inventory, Long>`
- [x] ✅ TASK-021: Add `existsInventoryId(inventoryId, excludeId)` query
- [x] ✅ TASK-022: Add `existsSerialNumber(serialNumber, excludeId)` query

### Service
- [x] ✅ TASK-030: Create `InventoryService` with CRUD operations
- [x] ✅ TASK-031: Implement `normalizeAndValidate(Inventory, excludeId)` with normalization pipeline
- [x] ✅ TASK-032: Implement inventoryId pattern validation (alphanumeric)
- [x] ✅ TASK-033: Implement required field checks
- [x] ✅ TASK-034: Implement decimal validation (risk scores, utilization 0–100)
- [x] ✅ TASK-035: Implement temporal validation (yearOfManufacture ≤ current year)
- [x] ✅ TASK-036: Implement uniqueness validation (inventoryId, serialNumber)
- [x] ✅ TASK-037: Implement explicit field-copy update pattern
- [x] ✅ TASK-038: Throw `IllegalArgumentException` on validation violations

### Controller
- [x] ✅ TASK-040: Create `InventoryController` with list, create, edit, update, delete
- [x] ✅ TASK-041: Populate enum model attributes
- [x] ✅ TASK-042: Set default values (category=OTHER, conditionStatus=GOOD, etc.)
- [x] ✅ TASK-043: Handle @Valid + BindingResult
- [x] ✅ TASK-044: Catch IllegalArgumentException and surface as global error

### Templates
- [x] ✅ TASK-050: Create `inventories/list.html`
- [x] ✅ TASK-051: Create `inventories/form.html`
- [x] ✅ TASK-052: Add field-level error display
- [x] ✅ TASK-053: Add global error display
- [x] ✅ TASK-054: Add audit read-only section
- [x] ✅ TASK-055: Link stylesheet
- [x] ✅ TASK-056: Add CRUD action buttons

---

## Phase 2 — Enhancements (Planned)

### Detail Page & Views
- [ ] 🔲 TASK-100: Create `inventories/detail.html`
- [ ] 🔲 TASK-101: Add collapsible sections (Technical, Location, Financial, Risk)
- [ ] 🔲 TASK-102: Display condition status with color coding

### Search and Filtering
- [ ] 🔲 TASK-110: Add filter parameters (category, condition, criticality)
- [ ] 🔲 TASK-111: Add `search(...)` repository query
- [ ] 🔲 TASK-112: Add filter form to list

### Data Quality
- [ ] 🔲 TASK-120: Add sorting options
- [ ] 🔲 TASK-121: Add pagination

### Risk & Monitoring
- [ ] 🔲 TASK-130: Add maintenance alert dashboard
- [ ] 🔲 TASK-131: Add failure risk summary
- [ ] 🔲 TASK-132: Implement depreciation calculation

### Testing
- [ ] 🔲 TASK-160: Write unit tests for service
- [ ] 🔲 TASK-161: Write WebMvcTest for controller
- [ ] 🔲 TASK-162: Write integration tests

---

## Phase 3 — Advanced (Future)

### REST API
- [ ] 🔲 TASK-200: Create REST endpoints

### ERP Integration
- [ ] 🔲 TASK-220: Integrate with ERP systems

### Monitoring
- [ ] 🔲 TASK-230: Implement maintenance alerts
- [ ] 🔲 TASK-231: Failure prediction

---

## Estimation & Effort

| Phase | Scope | Est. Effort |
|-------|-------|-------------|
| **Phase 1** | Core CRUD, 50+ fields, 8 enums | 60 hours |
| **Phase 2** | Detail page, filtering, tests | 80 hours |
| **Phase 3** | REST API, ERP integration | 100+ hours |

---

## Quality Gates

### Phase 1 → Phase 2
- [x] All Phase 1 tasks marked ✅
- [x] No compilation errors
- [x] Manual testing: CRUD workflows
- [x] Validation working
- [x] All 50+ fields saving/loading

### Phase 2 → Phase 3
- [ ] Unit test coverage ≥ 80%
- [ ] Integration test coverage ≥ 60%
- [ ] No outstanding security issues
- [ ] Performance tested

---

## Delivery Milestones

| Milestone | Target | Deliverables |
|-----------|--------|--------------|
| **v1.0** | 2026-04-04 | Core CRUD, basic UI, validation |
| **v1.1** | 2026-05-30 | Detail page, filtering, pagination |
| **v2.0** | 2026-Q3 | Unit tests, soft-delete, audit trail |
| **v3.0** | 2026-Q4 | REST API, ERP integration |

