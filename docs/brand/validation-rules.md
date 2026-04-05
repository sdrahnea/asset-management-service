# Brand — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

## Field-Level Validation

| Field | Rule | Error |
|-------|------|-------|
| `brandId` | Required, max 100 chars | `"Brand ID is required"` |
| `brandName` | Required, max 255 chars | `"Brand name is required"` |
| `parentCompany` | Required, max 255 chars | `"Parent company is required"` |
| `brandStatus` | Required enum | `"Brand status is required"` |
| `dataSource` | Required, max 100 chars | `"Data source is required"` |
| `brandEquityScore`, `marketShare`, `customerLoyaltyScore` | 0–100 | Range error messages |
| `nps` | -100–100 | NPS range error |

## Business Rules

| ID | Rule | Error |
|----|------|-------|
| BR-001 | `trademarkExpirationDate >= trademarkRegistrationDate` when both set | `"Expiration date must be on or after registration date"` |

## Uniqueness Rules

| ID | Rule | Error |
|----|------|-------|
| UQ-001 | `brandId` globally unique | `"Brand ID must be unique"` |
| UQ-002 | `trademarkRegistrationNumber` globally unique when non-null | `"Trademark registration number must be unique"` |

