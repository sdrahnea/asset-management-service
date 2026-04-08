# Trademark — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

## Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create/list/view/edit/delete trademark records | ✅ |
| FR-002 | Capture trademark identity and classification | ✅ |
| FR-003 | Capture owner/applicant and assignment details | ✅ |
| FR-004 | Capture filing/registration lifecycle data | ✅ |
| FR-005 | Capture territorial and usage/scope data | ✅ |
| FR-006 | Capture financial/commercial fields | ✅ |
| FR-007 | Capture risk and assessment indicators | ✅ |
| FR-008 | Capture evidence/documentation metadata | ✅ |
| FR-009 | Support list filters by mark/legal/owner/licensing status | ✅ |

## Non-Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | Normalize identifiers before validation/uniqueness | ✅ |
| NFR-002 | Enforce uniqueness for key legal identifiers | ✅ |
| NFR-003 | Enforce lifecycle date consistency rules | ✅ |
| NFR-004 | Enforce numeric score/range constraints | ✅ |
| NFR-005 | Surface business violations as global form errors | ✅ |

## Out of Scope (v1)
- External trademark office synchronization
- Automated renewal reminders
- Litigation workflow automation
- Role-based authorization

