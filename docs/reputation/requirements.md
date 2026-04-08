# Reputation — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

## Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create/list/view/edit/delete reputation records | ✅ |
| FR-002 | Link record to subject (`entityType`, `entityId`, display name) | ✅ |
| FR-003 | Capture sentiment and feedback signals | ✅ |
| FR-004 | Capture event-driven changes and impact | ✅ |
| FR-005 | Capture composite/trend/risk indicators | ✅ |
| FR-006 | Capture legal/ethical/compliance concerns | ✅ |
| FR-007 | Capture context and evidence fields | ✅ |
| FR-008 | Filter list by entityId/entityType/trend/competitive position | ✅ |

## Non-Functional Requirements
| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | Normalize IDs before validation/uniqueness checks | ✅ |
| NFR-002 | Enforce unique `reputationId` and unique `(entityType, entityId)` | ✅ |
| NFR-003 | Enforce event-date and event-type consistency rules | ✅ |
| NFR-004 | Enforce score/sentiment decimal ranges and precision | ✅ |
| NFR-005 | Surface service business errors as form global errors | ✅ |

## Out of Scope (v1)
- Automated sentiment ingestion pipelines
- External media API synchronization
- Reputation trend forecasting engine
- Role-based authorization

