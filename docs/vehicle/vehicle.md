# Vehicle — Comprehensive Specification

**Version**: 1.0 | **Date**: 2026-04-04

## Overview
`Vehicle` models movable tangible assets with identity, technical specs, legal/registration status, ownership/usage, maintenance, valuation, risk indicators, and evidence metadata.

`Vehicle` extends `CoreEntity` and remaps `name` to `vehicle_id` using `@AttributeOverride`.

## Enums
- `VehicleType`: CAR, TRUCK, VAN, MOTORCYCLE, BUS, TRACTOR, TRAILER, OTHER
- `EngineType`: PETROL, DIESEL, HYBRID, ELECTRIC, LPG, OTHER
- `Transmission`: MANUAL, AUTOMATIC, CVT, OTHER
- `Drivetrain`: FWD, RWD, AWD, FOUR_BY_FOUR, OTHER
- `RegistrationStatus`: ACTIVE, EXPIRED, SUSPENDED
- `VerificationStatus`: VALID, EXPIRED
- `TaxStatus`: PAID, OVERDUE
- `OwnershipType`: INDIVIDUAL, COMPANY, LEASED, RENTED
- `UsageCategory`: PERSONAL, COMMERCIAL, INDUSTRIAL, AGRICULTURAL
- `OperatingRegion`: URBAN, RURAL, MIXED
- `ConditionStatus`: EXCELLENT, GOOD, FAIR, POOR
- `MaintenanceSchedule`: PERIODIC, MILEAGE_BASED
- `DepreciationMethod`: STRAIGHT_LINE, DECLINING_BALANCE, OTHER

## Required Fields
- `vehicleId`
- `vin`
- `licensePlate`
- `vehicleType`
- `make`
- `model`
- `yearOfManufacture`
- `engineType`
- `dataSource`

## Uniqueness
- `vehicleId` unique
- `vin` unique
- `(licensePlate, registrationCountry)` unique

## Business Rules
- Identifier normalization (uppercase/no spaces): `vehicleId`, `vin`, `licensePlate`, `registrationCountry`
- `yearOfManufacture <= current year`
- `registrationDate`, `purchaseDate`, `valuationDate` cannot be in the future
- `nextServiceDue >= lastServiceDate`

## Main Data Sections
1. Vehicle identity and classification
2. Technical specifications
3. Registration and legal information
4. Ownership and usage
5. Maintenance and condition
6. Financial and valuation attributes
7. Risk and assessment indicators
8. Documentation and evidence
9. Operational metadata

