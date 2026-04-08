# Inventory — Test Scenarios

**Version**: 1.0 | **Date**: 2026-04-04

---

## Service Tests (50+ scenarios)

### Required Field Validation
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-001 | Valid inventory record | No exception |
| TS-S-002 | Missing inventoryId | Validation error |
| TS-S-003 | Missing category | Validation error |
| TS-S-004 | Missing manufacturer | Validation error |
| TS-S-005 | Missing model | Validation error |
| TS-S-006 | Missing serialNumber | Validation error |
| TS-S-007 | Missing yearOfManufacture | Validation error |
| TS-S-008 | Missing dataSource | Validation error |

### Pattern & Temporal Validation
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-010 | inventoryId pattern valid | Accepted |
| TS-S-011 | inventoryId pattern invalid | Validation error |
| TS-S-012 | yearOfManufacture in future | Validation error |
| TS-S-013 | yearOfManufacture current year | Accepted |
| TS-S-014 | yearOfManufacture past year | Accepted |

### Decimal & Range Validation
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-020 | failureRiskScore = 50 | Accepted |
| TS-S-021 | failureRiskScore = 0 | Accepted |
| TS-S-022 | failureRiskScore = 100 | Accepted |
| TS-S-023 | failureRiskScore > 100 | Validation error |
| TS-S-024 | failureRiskScore < 0 | Validation error |
| TS-S-025 | utilizationRate = 75.5 | Accepted |
| TS-S-026 | weight = 0 | Accepted |
| TS-S-027 | weight < 0 | Validation error |

### Uniqueness Validation
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-030 | Duplicate inventoryId | Validation error |
| TS-S-031 | Duplicate serialNumber | Validation error |
| TS-S-032 | Same ID on update self | No exception (excludeId) |
| TS-S-033 | Same serialNumber on update self | No exception (excludeId) |

### Optional Field Handling
| ID | Scenario | Expected |
|----|----------|----------|
| TS-S-040 | Null optional fields | Accepted |
| TS-S-041 | Empty optional strings | Converted to null |

---

## Controller Tests (30+ scenarios)

### GET Endpoints
| ID | Scenario | Expected |
|----|----------|----------|
| TS-C-001 | GET /inventories | 200 OK, list rendered |
| TS-C-002 | GET /inventories/new | 200 OK, form with defaults |
| TS-C-003 | GET /inventories/{id}/edit | 200 OK, form pre-populated |

### POST Operations
| ID | Scenario | Expected |
|----|----------|----------|
| TS-C-010 | Valid create POST | 302 redirect to /inventories |
| TS-C-011 | Missing required field | 200 OK, form with error |
| TS-C-020 | Valid update POST | 302 redirect to /inventories |
| TS-C-030 | Delete POST | 302 redirect to /inventories |

---

## UI Tests (20+ scenarios)

### List Page
| ID | Scenario | Expected |
|----|----------|----------|
| TS-U-001 | Columns visible | inventoryId, manufacturer, model, category, condition |
| TS-U-002 | Empty list message | "No inventories found" |

### Form Page
| ID | Scenario | Expected |
|----|----------|----------|
| TS-U-010 | Form sections | Identity, Technical, Location, Condition, Financial, Compliance, Risk, Metadata |
| TS-U-011 | Required fields marked | * indicator shown |
| TS-U-012 | Defaults applied | category=OTHER, conditionStatus=GOOD |

---

## Edge Cases (15+ scenarios)

| ID | Scenario | Expected |
|----|----------|----------|
| TS-E-001 | inventoryId at max length (100) | Accepted |
| TS-E-002 | inventoryId over max (101) | Validation error |
| TS-E-003 | serialNumber at max length | Accepted |
| TS-E-004 | Large decimal amounts | Accepted (DECIMAL(19,2)) |
| TS-E-005 | Date in future | Depends on field validation |
| TS-E-006 | Negative prices (invalid) | Validation error |

---

## Manual Test Checklist

Before Phase 1 sign-off:

### Create & Edit
- [ ] Can create inventory
- [ ] Can edit inventory
- [ ] Can delete inventory
- [ ] All 50+ fields save correctly

### Validation
- [ ] Required fields enforced
- [ ] Pattern validation works
- [ ] Temporal validation works
- [ ] Decimal constraints work
- [ ] Uniqueness checks work
- [ ] Error messages clear

### Form Functionality
- [ ] All enum dropdowns work
- [ ] All fields populate
- [ ] Defaults apply on create
- [ ] Data persists on update

