# Stock — Compliance Notes

**Version**: 1.0 | **Date**: 2026-04-04

## Regulatory Context
Stock records commonly intersect with:
- Financial reporting and market-disclosure obligations
- Internal valuation and risk-governance controls
- Data lineage/provenance for market and analyst inputs
- Data protection for responsible-agent fields

## Sensitive Data Areas
### Financial/market
- Price series and volume metrics
- Revenue/net-income and margins
- Risk and assessment scores

### Governance/strategy
- Board/executive/shareholder narratives
- Regulatory flags and macro/geopolitical exposure

### Personal fields
- `responsibleAgent`

If personal data is present, GDPR principles apply (purpose limitation, minimization, access controls).

## Compliance Recommendations
- Keep identifier normalization and uniqueness controls strict
- Ensure score/range validations remain auditable
- Keep evidence fields (`analystReports`, `regulatoryFilings`, `newsReferences`) traceable
- Restrict sensitive governance/risk narratives by role in future authorization model

## Current Gaps / Next Steps
- Role-based authorization not yet enforced
- External feed provenance controls not automated
- Expanded audit trail for score/indicator updates not implemented

