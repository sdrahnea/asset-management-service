# Inventory — User Stories

**Version**: 1.0 | **Date**: 2026-04-04

---

## User Stories

### US-001 — Create Inventory Record
**As a** Equipment Manager, **I want to** create an inventory record **so that** equipment can be tracked and assessed.
- [ ] Mandatory: inventoryId, category, manufacturer, model, serialNumber, yearOfManufacture, dataSource
- [ ] inventoryId unique; serialNumber unique
- [ ] yearOfManufacture ≤ current year

### US-002 — View Inventory List
**As a** Facilities Manager, **I want to** see all inventories with key details **so that** I can manage the fleet.
- [ ] List at `/inventories` with inventoryId, manufacturer, model, category, condition, location

### US-003 — Edit Inventory Record
**As a** Equipment Manager, **I want to** update equipment details **so that** records stay current.
- [ ] All 50+ fields editable

### US-004 — Track Maintenance
**As a** Maintenance Coordinator, **I want to** record maintenance schedule, dates, and history **so that** equipment is properly maintained.
- [ ] Maintenance fields editable
- [ ] Dates tracked

### US-005 — Monitor Equipment Condition
**As a** Operations Manager, **I want to** record condition status and risk scores **so that** I know equipment health.
- [ ] Condition, risk fields editable

### US-006 — Manage Financial Data
**As a** Finance Manager, **I want to** track depreciation, valuation, and costs **so that** asset value is accurate.
- [ ] Financial fields editable

### US-007 — Ensure Compliance
**As a** Compliance Officer, **I want to** record certifications, permits, and regulatory status **so that** equipment is compliant.
- [ ] Compliance fields editable

---

## Test Scenarios

| ID | Scenario | Expected |
|----|----------|----------|
| TS-001 | Valid inventory created | No exception |
| TS-002 | Missing inventoryId | Error |
| TS-003 | Missing category | Error |
| TS-004 | Missing manufacturer | Error |
| TS-005 | Missing serialNumber | Error |
| TS-006 | Missing yearOfManufacture | Error |
| TS-007 | yearOfManufacture in future | Error |
| TS-008 | Duplicate inventoryId | Error |
| TS-009 | Duplicate serialNumber | Error |
| TS-010 | Form defaults applied | defaults shown |
| TS-011 | All fields save | data persists |
| TS-012 | Edit pre-populates | current values shown |
| TS-013 | Delete removes | no longer in list |

