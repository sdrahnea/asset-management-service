# Real Estate — Comprehensive Specification

**Version**: 1.0 | **Date**: 2026-04-04

## Overview
`RealEstate` models property assets with legal identity, physical characteristics, valuation, neighborhood context, maintenance, and risk indicators.

`RealEstate` extends `CoreEntity` and uses `name` as title-style identity (no `@AttributeOverride` in this entity).

## Key Enums
- `PropertyType`: LAND, APARTMENT, HOUSE, COMMERCIAL, INDUSTRIAL, MIXED_USE
- `ConstructionType`: BRICK, CONCRETE, WOOD, STEEL
- `Condition`: NEW, RENOVATED, GOOD, NEEDS_RENOVATION, DERELICT
- `ZoningType`: RESIDENTIAL, COMMERCIAL, AGRICULTURAL, MIXED
- `LandShape`: REGULAR, IRREGULAR
- `Topography`: FLAT, SLOPED, HILLSIDE
- `AccessRoadType`: ASPHALT, GRAVEL, DIRT
- `OwnershipType`: INDIVIDUAL, COMPANY, CO_OWNED, CONCESSION
- `HeatingType`: CENTRAL, GAS_BOILER, HEAT_PUMP, DISTRICT_HEATING
- `EnergyEfficiencyClass`: A, B, C, D, E, F, G
- `NeighborhoodType`: URBAN, SUBURBAN, RURAL
- `MaintenanceStatus`: UP_TO_DATE, OVERDUE, UNKNOWN

## Mandatory Fields
- `title` (from `name`)
- `propertyType`
- `address`
- `landArea`
- `ownershipType`
- `ownerName`
- `cadastralNumber`
- `landRegistryNumber`
- `currentMarketValue`
- `valuationDate`
- `dataSource`

## Uniqueness
- `cadastralNumber` unique
- `landRegistryNumber` unique

## Business Rules
- `cadastralNumber` and `landRegistryNumber` must be uppercase alphanumeric after normalization
- Latitude/longitude must be provided together
- `yearBuilt <= current year`
- `valuationDate <= today`
- `lastRenovationDate <= today`
- `builtArea >= usableArea` when both exist
- `yieldIndicators` is computed from `rentalIncome` and `purchasePrice` when possible

## Service Behavior
- Normalize text via trim and empty-to-null
- Normalize identifiers by removing spaces + uppercase
- Normalize utility/amenity sets by trimming + de-dup
- Throw `IllegalArgumentException` on business/uniqueness violations

## Known Caveat
- Current controller delete flow redirects to `/bank-accounts` (code behavior).

