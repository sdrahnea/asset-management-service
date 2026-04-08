# Copyright — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

## Field Validation
| Field | Rule | Error |
|-------|------|-------|
| `copyrightId` | Required, max 100, alphanumeric | `"Copyright ID is required"` |
| `title` | Required, max 255 | `"Title is required"` |
| `workType` | Required enum | `"Type of work is required"` |
| `authors` | Required, max 1000 | `"Author(s) is required"` |
| `copyrightOwners` | Required, max 1000 | `"Copyright owner(s) is required"` |
| `countryOfOrigin` | Required, max 100 | `"Country of origin is required"` |
| `copyrightStatus` | Required enum | `"Copyright status is required"` |
| `sourceOfInformation` | Required, max 100 | `"Source of information is required"` |
| `creationDate` | If present, ≤ today | `"Creation date must be today or earlier"` |
| `publicationReleaseDate` | If present, ≤ today & ≥ creationDate | `"Publication date must be >= creation date"` |
| `registrationDate` | If present, ≤ today | `"Registration date must be today or earlier"` |
| `protectionEndDate` | If present, ≥ protectionStartDate | `"Protection end date must be >= start date"` |
| `licenseEndDate` | If present, ≥ licenseStartDate | `"License end date must be >= start date"` |
| `ownershipPercentage` | If present, 0–100 | Validation error |
| `royaltyRate` | If present, 0–100 | Validation error |
| `marketRelevanceScore` | If present, 0–100 | Validation error |
| `portfolioImpactScore` | If present, 0–100 | Validation error |

## Uniqueness
| Rule | Error |
|------|-------|
| `copyrightId` globally unique | `"Copyright ID must be unique"` |
| `registrationNumber` unique if provided | `"Registration number must be unique"` |

