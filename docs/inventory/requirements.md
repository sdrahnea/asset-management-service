# Inventory — Requirements

**Version**: 1.0 | **Date**: 2026-04-04 | **Status**: Implemented

## Functional Requirements

| ID | Requirement | Status |
|----|-------------|--------|
| FR-001 | Create, list, edit, delete inventory | ✅ Done |
| FR-002 | Track identity (ID, category, manufacturer, model, serial) | ✅ Done |
| FR-003 | Record technical specifications | ✅ Done |
| FR-004 | Track location and ownership | ✅ Done |
| FR-005 | Monitor condition and maintenance | ✅ Done |
| FR-006 | Record financial data (cost, depreciation, value) | ✅ Done |
| FR-007 | Track compliance (certificates, permits, regulatory) | ✅ Done |
| FR-008 | Record risk indicators (failure, safety, criticality) | ✅ Done |
| FR-009 | Manage operational metadata (source, analyst, tags) | ✅ Done |

## Non-Functional Requirements

| ID | Requirement | Status |
|----|-------------|--------|
| NFR-001 | inventoryId normalized and validated | ✅ Done |
| NFR-002 | Unique constraints on inventoryId, serialNumber | ✅ Done |
| NFR-003 | Temporal validation (year <= current) | ✅ Done |
| NFR-004 | Decimal constraints (scores 0–100) | ✅ Done |
| NFR-005 | Support 50+ fields with 8 enums | ✅ Done |


