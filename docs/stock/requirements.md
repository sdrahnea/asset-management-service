# Stock — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

## Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create/list/view/edit/delete stock records | ✅ |
| FR-002 | Capture stock identity and listing information | ✅ |
| FR-003 | Capture market trading fields | ✅ |
| FR-004 | Capture financial fundamentals | ✅ |
| FR-005 | Capture governance and corporate-event fields | ✅ |
| FR-006 | Capture market context and risk indicators | ✅ |
| FR-007 | Capture documentation/evidence fields | ✅ |
| FR-008 | Support list filters by ticker, exchange, sector, company type | ✅ |

## Non-Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | Normalize identifiers before validation/uniqueness checks | ✅ |
| NFR-002 | Enforce uniqueness constraints for stock identity keys | ✅ |
| NFR-003 | Enforce day/52-week range consistency | ✅ |
| NFR-004 | Enforce decimal precision/range constraints by field | ✅ |
| NFR-005 | Return service business errors as global form errors | ✅ |

## Out of Scope (v1)
- Live market feed integration
- Automated corporate-action ingestion
- Forecasting/alpha models
- Role-based authorization

