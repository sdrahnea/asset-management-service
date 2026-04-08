# Trademark — Comprehensive Specification

**Version**: 1.0 | **Date**: 2026-04-04

## Overview
`Trademark` models trademark assets with identity/classification, ownership, filing lifecycle, territorial scope, usage, financial attributes, risk indicators, and evidence.

`Trademark` extends `CoreEntity` and remaps `name` to `trademark_id` using `@AttributeOverride`.

## Enums
- `MarkType`: WORDMARK, FIGURATIVE, COMBINED, THREE_D, SOUND, COLOR, HOLOGRAM, OTHER
- `OwnerType`: PERSON, CORPORATION, PARTNERSHIP, NGO, OTHER
- `LegalStatus`: FILED, PUBLISHED, OPPOSED, REGISTERED, EXPIRED, CANCELLED, REFUSED
- `InternationalFilingSystem`: WIPO_MADRID, EUIPO, NATIONAL, OTHER
- `UsageStatus`: IN_USE, INTENDED_USE, DISCONTINUED
- `LicensingStatus`: NOT_LICENSED, LICENSED, EXCLUSIVE, NON_EXCLUSIVE
- `DistinctivenessScore`: ARBITRARY, SUGGESTIVE, DESCRIPTIVE, GENERIC
- `CommercialImportance`: CORE_BRAND, SUB_BRAND, LEGACY_MARK, OTHER

## Required Fields
- `trademarkId`
- `markName`
- `markType`
- `ownerName`
- `ownerType`
- `applicationNumber`
- `applicationDate`
- `legalStatus`
- `dataSource`

## Uniqueness
- `trademarkId` unique
- `applicationNumber` unique
- `registrationNumber` unique when present

## Business Rules
- IDs are normalized (trim, remove spaces, uppercase): `trademarkId`, `applicationNumber`, `registrationNumber`, `priorityNumber`
- If `priorityClaimed` is true, `priorityNumber` and `priorityDate` are required
- `priorityDate <= applicationDate`
- `registrationDate >= applicationDate`
- `expirationDate >= registrationDate`
- `firstUseInCommerceDate >= firstUseDate`

## Main Data Sections
1. Identity and classification
2. Ownership and applicant info
3. Filing/registration/legal status
4. Geographic coverage
5. Rights/usage/scope
6. Financial/commercial attributes
7. Risk and assessment indicators
8. Documentation/evidence
9. Operational metadata

