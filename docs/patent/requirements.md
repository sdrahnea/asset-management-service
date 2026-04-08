# Patent — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

## Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create/list/view/edit/delete patent records | ✅ |
| FR-002 | Capture patent identity/classification fields | ✅ |
| FR-003 | Capture ownership and assignee fields | ✅ |
| FR-004 | Capture legal filing and registration fields | ✅ |
| FR-005 | Capture claims/scope/legal status fields | ✅ |
| FR-006 | Capture geographic coverage fields | ✅ |
| FR-007 | Capture financial/commercial fields | ✅ |
| FR-008 | Capture lifecycle deadlines and expiry fields | ✅ |
| FR-009 | Capture risk/competitive indicators | ✅ |
| FR-010 | Capture document and metadata fields | ✅ |
| FR-011 | Support list filters: type, legal status, technology, assignee | ✅ |

## Non-Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | Normalize identifiers before validation | ✅ |
| NFR-002 | Enforce uniqueness on key legal identifiers | ✅ |
| NFR-003 | Enforce chronological legal date rules | ✅ |
| NFR-004 | Enforce decimal score/rate constraints | ✅ |
| NFR-005 | Return domain/business violations via controller form errors | ✅ |

## Out of Scope (v1)
- External patent office API synchronization
- Automated deadline notification engine
- Litigation workflow automation
- Role-based authorization

