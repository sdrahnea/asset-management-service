# Reputation — Validation Rules

**Version**: 1.0 | **Date**: 2026-04-04

## Mandatory
| Field | Rule | Error |
|-------|------|-------|
| `reputationId` | Required, max 100 | `"Reputation ID is required"` |
| `entityType` | Required enum | `"Entity type is required"` |
| `entityId` | Required, max 100 | `"Entity ID is required"` |
| `displayName` | Required, max 255 | `"Display name is required"` |
| `dataSource` | Required, max 100 | `"Data source is required"` |

## Uniqueness
| Rule | Error |
|------|-------|
| `reputationId` unique | `"Reputation ID must be unique"` |
| `(entityType, entityId)` unique | `"Entity type + entity ID combination must be unique"` |

## Business Rules
| Rule | Error |
|------|-------|
| `eventDate <= today` | `"Event date cannot be in the future"` |
| If event detail provided, `eventType` required | `"Event type is required when event details are provided"` |

## Numeric Constraints
- `publicSentiment`, `socialMediaSentiment`, `eventImpactScore`: -100..100
- `customerFeedbackScore`, `employeeFeedbackScore`, `reputationScore`, `trustScore`, `credibilityScore`, `riskScore`, `volatilityIndex`: 0..100
- `mediaCoverageVolume`: >= 0

