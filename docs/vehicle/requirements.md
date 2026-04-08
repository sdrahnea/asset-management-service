# Vehicle — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

## Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create/list/view/edit/delete vehicle records | ✅ |
| FR-002 | Capture vehicle identity/classification fields | ✅ |
| FR-003 | Capture technical specification fields | ✅ |
| FR-004 | Capture registration/legal status fields | ✅ |
| FR-005 | Capture ownership and usage fields | ✅ |
| FR-006 | Capture maintenance and condition fields | ✅ |
| FR-007 | Capture financial and valuation fields | ✅ |
| FR-008 | Capture risk and compliance indicator fields | ✅ |
| FR-009 | Capture documentation/evidence and metadata fields | ✅ |
| FR-010 | Support list filters by vehicle type, registration status, ownership type | ✅ |

## Non-Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | Normalize identifiers before validation/uniqueness checks | ✅ |
| NFR-002 | Enforce uniqueness (`vehicleId`, `vin`, plate+country) | ✅ |
| NFR-003 | Enforce temporal rules (year/date/service ranges) | ✅ |
| NFR-004 | Enforce decimal/range constraints by field annotations | ✅ |
| NFR-005 | Return business violations as global form errors | ✅ |

## Out of Scope (v1)
- Telematics/IoT feed synchronization
- Automated service reminders
- External registration API integration
- Role-based authorization

