# Reputation — Comprehensive Specification

**Version**: 1.0 | **Date**: 2026-04-04

## Overview
`Reputation` captures perception signals, event impacts, quantified indicators, contextual factors, and supporting evidence for a referenced subject (person/company/brand/product/organization).

`Reputation` extends `CoreEntity` and remaps `name` to `reputation_id` with `@AttributeOverride`.

## Enums
- `EntityType`: PERSON, COMPANY, BRAND, PRODUCT, ORGANIZATION, OTHER
- `GeographicScope`: LOCAL, NATIONAL, REGIONAL, INTERNATIONAL, GLOBAL
- `MediaCoverageTone`: POSITIVE, NEUTRAL, NEGATIVE, MIXED
- `EventType`: AWARD, SCANDAL, LAWSUIT, PRODUCT_LAUNCH, CRISIS, ACHIEVEMENT, OTHER
- `TrendDirection`: IMPROVING, STABLE, DECLINING, VOLATILE
- `CompetitivePosition`: LEADER, CHALLENGER, NICHE, DECLINING, OTHER
- `RegulatoryEnvironment`: STRICT, MODERATE, LENIENT, VOLATILE, OTHER

## Required Fields
- `reputationId`
- `entityType`
- `entityId`
- `displayName`
- `dataSource`

## Uniqueness
- `reputationId` unique
- `(entityType, entityId)` unique

## Business Rules
- Identifier normalization: trim, remove internal whitespace, uppercase (`reputationId`, `entityId`)
- `eventDate` cannot be in the future
- If any event detail exists (`eventDate`, `eventDescription`, `eventImpactScore`, `eventSource`), `eventType` is required
- Sentiment and score fields constrained by configured ranges/precision

## Data Sections
1. Identity and context
2. Reputation signals
3. Reputation events
4. Quantitative indicators
5. Risk and compliance factors
6. External context
7. Supporting evidence
8. Operational metadata

