# Patent — Comprehensive Specification

**Version**: 1.0 | **Date**: 2026-04-04

## Overview
`Patent` captures legal, technical, commercial, lifecycle, and risk information for patent assets. It supports legal compliance, portfolio valuation, and competitive intelligence.

`Patent` extends `CoreEntity` and remaps `name` to `patent_id` using `@AttributeOverride`.

## Enums
- `PatentType`: UTILITY, DESIGN, PLANT, MODEL, OTHER
- `LegalStatus`: PENDING, GRANTED, REJECTED, EXPIRED, WITHDRAWN
- `RegionalSystem`: NONE, EPO, WIPO_PCT, ARIPO, OTHER
- `InfringementRisk`: LOW, MEDIUM, HIGH, CRITICAL
- `BlockingPotential`: LOW, MEDIUM, HIGH, STRATEGIC

## Required Fields
- `patentId`
- `title`
- `patentType`
- `assigneeOwner`
- `applicationNumber`
- `applicationDate`
- `legalStatus`
- `dataSource`

## Uniqueness
- `patentId` unique
- `applicationNumber` unique
- `publicationNumber` unique when present
- `grantNumber` unique when present

## Core Business Rules
- Publication date must be on/after application date
- Grant date must be on/after application date
- Expiry date must be on/after grant date
- If legal status is `GRANTED`, `grantNumber` and `grantDate` are mandatory
- Identifier normalization in service: trim, remove internal spaces, uppercase

## Main Data Sections
1. Identity/classification
2. Inventorship/ownership
3. Filing/registration lifecycle
4. Claims/scope/legal status
5. Geographic coverage
6. Financial/commercial
7. Lifecycle deadlines/maintenance
8. Risk/assessment indicators
9. Documentation/metadata

