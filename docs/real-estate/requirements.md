# Real Estate — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

## Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create/list/view/edit/delete real-estate records | ✅ |
| FR-002 | Capture identity and classification fields | ✅ |
| FR-003 | Capture legal ownership and cadastral data | ✅ |
| FR-004 | Capture location/land/building characteristics | ✅ |
| FR-005 | Capture valuation and financial indicators | ✅ |
| FR-006 | Capture maintenance and structural-risk data | ✅ |
| FR-007 | Capture neighborhood/market context fields | ✅ |
| FR-008 | Filter list by property/ownership/neighborhood/date/maintenance | ✅ |

## Non-Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | Normalize text/identifiers before validation | ✅ |
| NFR-002 | Enforce uniqueness on cadastral and land registry numbers | ✅ |
| NFR-003 | Enforce date and area business rules | ✅ |
| NFR-004 | Calculate yield indicator when inputs exist | ✅ |
| NFR-005 | Return service business violations as form global errors | ✅ |

## Out of Scope (v1)
- File upload/document lifecycle management
- Automated valuation feeds
- Role-based authorization
- Notification workflow for overdue maintenance

