# Stock — Data Model

**Version**: 1.0 | **Date**: 2026-04-04 | **Table**: `stock`

`Stock` extends `CoreEntity`; base `name` maps to `stock_id`.

## Unique Constraints
- `stock_id`
- `(ticker_symbol, exchange_code)`
- `isin`
- `cusip`

## Field Groups
### Identity
- `stockId`, `tickerSymbol`, `exchange`, `isin`, `cusip`, `companyName`, `industry`, `sector`, `countryOfListing`

### Market Data
- `currentPrice`, `openPrice`, `closePrice`, `dayHigh`, `dayLow`, `fiftyTwoWeekHigh`, `fiftyTwoWeekLow`, `volume`, `averageVolume`, `marketCap`, `beta`, `priceChange`, `priceChangePercent`

### Fundamentals
- `earningsPerShare`, `priceToEarnings`, `priceToBook`, `dividendYield`, `dividendPerShare`, `payoutRatio`, `revenue`, `netIncome`, `operatingMargin`, `profitMargin`, `returnOnEquity`, `returnOnAssets`, `debtToEquity`

### Corporate/Governance
- `companyType`, `boardMembers`, `executiveTeam`, `majorShareholders`, `ownershipStructure`, `corporateGovernanceScore`, `regulatoryFlags`

### Events
- `dividendAnnouncements`, `earningsReports`, `stockSplits`, `mergersAcquisitions`, `spinOffs`, `shareBuybacks`, `guidanceUpdates`, `notableNewsEvents`

### Context
- `industryTrends`, `macroeconomicIndicators`, `competitivePosition`, `geopoliticalExposure`, `supplyChainRisks`

### Risk/Assessment
- `volatilityScore`, `liquidityRiskScore`, `financialHealthScore`, `marketRiskScore`, `governanceRiskScore`, `overallAssessmentScore`, `complianceFlags`

### Evidence/Metadata
- `financialReports`, `analystReports`, `newsReferences`, `regulatoryFilings`, `internalNotes`, `dataSource`, `responsibleAgent`, `tags`, `notes`

## Key Rules
- Mandatory: stockId, tickerSymbol, exchange, companyName, dataSource
- Day range: dayHigh >= dayLow
- 52-week range: fiftyTwoWeekHigh >= fiftyTwoWeekLow
- Current price must stay within day range when provided

