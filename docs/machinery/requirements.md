# Machinery — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

## Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create, list, edit, delete machinery records | ✅ |
| FR-002 | Capture identity/classification (ID, category, model, serial, year) | ✅ |
| FR-003 | Capture technical specifications | ✅ |
| FR-004 | Track location and ownership | ✅ |
| FR-005 | Track maintenance and condition | ✅ |
| FR-006 | Track valuation and cost fields | ✅ |
| FR-007 | Store compliance documentation metadata | ✅ |
| FR-008 | Track operational and safety risk indicators | ✅ |
| FR-009 | Store operational metadata (source, analyst, tags, notes) | ✅ |

## Non-Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | `machineId` pattern/length validation | ✅ |
| NFR-002 | Global uniqueness on `machineId` and `serialNumber` | ✅ |
| NFR-003 | Year validation (`yearOfManufacture <= current year`) | ✅ |
| NFR-004 | Decimal/range constraints for rates and scores | ✅ |
| NFR-005 | Consistent normalization before validation | ✅ |

## Out of Scope (v1)
- External ERP/IoT synchronization
- Predictive maintenance analytics
- Automated depreciation engine
- Role-based authorization

