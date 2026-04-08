# Trademark — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

## Mandatory
| Field | Rule | Error |
|-------|------|-------|
| `trademarkId` | Required, max 100 | `"Trademark ID is required"` |
| `markName` | Required, max 255 | `"Mark name is required"` |
| `markType` | Required enum | `"Mark type is required"` |
| `ownerName` | Required, max 255 | `"Owner name is required"` |
| `ownerType` | Required enum | `"Owner type is required"` |
| `applicationNumber` | Required, max 100 | `"Application number is required"` |
| `applicationDate` | Required date | `"Application date is required"` |
| `legalStatus` | Required enum | `"Legal status is required"` |
| `dataSource` | Required, max 100 | `"Data source is required"` |

## Uniqueness
| Rule | Error |
|------|-------|
| `trademarkId` unique | `"Trademark ID must be unique"` |
| `applicationNumber` unique | `"Application number must be unique"` |
| `registrationNumber` unique (if present) | `"Registration number must be unique"` |

## Business Rules
| Rule | Error |
|------|-------|
| Priority claimed -> number required | `"Priority number is required when priority is claimed"` |
| Priority claimed -> date required | `"Priority date is required when priority is claimed"` |
| `priorityDate <= applicationDate` | `"Priority date must be on or before application date"` |
| `registrationDate >= applicationDate` | `"Registration date must be on or after application date"` |
| `expirationDate >= registrationDate` | `"Expiration date must be on or after registration date"` |
| `firstUseInCommerceDate >= firstUseDate` | `"First use in commerce date must be on or after first use date"` |

## Numeric Constraints
- `litigationRiskScore`, `infringementRiskScore`, `marketRelevanceScore`, `renewalRiskScore`, `overallAssessmentScore`: 0..100
- `royaltyIncome`, `valuation`, `maintenanceCosts`: >= 0

