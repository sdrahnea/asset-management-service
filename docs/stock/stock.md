# Stock — Comprehensive Specification

**Version**: 1.0 | **Date**: 2026-04-04

## Overview
`Stock` captures equity asset identity, trading data, fundamentals, governance, events, market context, risk indicators, evidence, and operational metadata.

`Stock` extends `CoreEntity` and remaps `name` to `stock_id` using `@AttributeOverride`.

## Enums
- `CompanyType`: PUBLIC, ADR, DUAL_LISTED, OTHER
- `CompetitivePosition`: LEADER, CHALLENGER, NICHE, FOLLOWER, OTHER

## Required Fields
- `stockId`
- `tickerSymbol`
- `exchange`
- `companyName`
- `dataSource`

## Uniqueness
- `stockId` unique
- `(tickerSymbol, exchange)` unique
- `isin` unique when present
- `cusip` unique when present

## Business Rules
- IDs and market identifiers are normalized to uppercase without spaces (`stockId`, `tickerSymbol`, `exchange`, `isin`, `cusip`, `countryOfListing`)
- `dayHigh >= dayLow`
- `fiftyTwoWeekHigh >= fiftyTwoWeekLow`
- `currentPrice` must be within day range when day bounds exist

## Main Data Sections
1. Identity and classification
2. Market data/trading
3. Financial fundamentals
4. Corporate governance
5. Corporate actions/events
6. Market/economic context
7. Risk/assessment indicators
8. Evidence/documentation
9. Operational metadata

