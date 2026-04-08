# Real Estate — Compliance Notes

**Version**: 1.0 | **Date**: 2026-04-04

## Regulatory Context
Real-estate records commonly intersect with:
- Land registry and cadastral governance
- Property valuation and taxation rules
- Building permit and usage restriction compliance
- Environmental and structural risk reporting
- Personal-data handling for owner/agent fields

## Sensitive Data Areas
### Financial
- Current market value, purchase/rental/cost/tax values
- Yield and assessment scores

### Legal
- Cadastral/land registry identifiers
- Encumbrances and permits

### Personal
- Owner name
- Responsible agent

When personal fields identify natural persons, GDPR principles apply (purpose limitation, minimization, controlled access).

## Compliance Recommendations
- Keep cadastral/registry numbers normalized and unique
- Keep valuation date and legal/maintenance dates current and auditable
- Use compliance/risk flags consistently for auditability
- Restrict visibility of sensitive valuation/legal fields by role in future iterations

## Current Gaps / Next Steps
- Authentication/authorization not yet enforced
- Expanded field-level audit history not yet implemented
- External registry/valuation integrations not yet implemented

