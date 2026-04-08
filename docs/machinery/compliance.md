# Machinery — Compliance Notes

**Version**: 1.0 | **Date**: 2026-04-04

## Regulatory Context
Applicable areas for machinery asset records:
- Workplace safety and machinery operation standards
- Hazardous equipment classifications
- Financial/tax treatment of depreciation and valuation
- Data protection for personal fields (analyst/custodian names)

## Data Handling
### Sensitive fields (internal/financial)
- Purchase/book/residual/insurance values
- Operating and maintenance costs
- Risk scores and compliance flags
- Inspection/maintenance narratives

### Personal fields
- `custodian`
- `responsibleAnalyst`

When these identify individuals, GDPR principles apply (minimization, purpose limitation, access controls).

## Operational Compliance Recommendations
- Keep certification and permit references current
- Track inspection and maintenance records consistently
- Use `regulatoryClassification` + `complianceFlags` for audit traceability
- Restrict financial fields to authorized users in future role-based access

## Current Gaps / Next Steps
- Authentication/authorization not yet enforced
- Field-level audit logging can be expanded
- Add retention/export policies in later phase

