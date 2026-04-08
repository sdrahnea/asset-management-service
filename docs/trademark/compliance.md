# Trademark — Compliance Notes

**Version**: 1.0 | **Date**: 2026-04-04

## Regulatory Context
Trademark records commonly intersect with:
- National/regional trademark office lifecycle rules
- International filing systems (Madrid/EUIPO/national paths)
- Ownership and assignment legal traceability
- Data protection for owner/agent personal data

## Sensitive Data Areas
### Legal/identity
- Application and registration identifiers
- Priority claims and lifecycle dates
- Opposition/cancellation records

### Financial/commercial
- Royalty income, valuation, maintenance costs
- Commercial importance and licensing status

### Personal data
- `ownerName`, `ownerAddress`, `representative`, `responsibleAgent`

When personal data is present, GDPR principles apply (purpose limitation, minimization, controlled access).

## Compliance Recommendations
- Keep legal identifiers normalized and unique
- Keep date-lifecycle transitions auditable
- Preserve assignment/history and evidence traceability
- Restrict sensitive legal/financial narratives by role in future authorization model

## Current Gaps / Next Steps
- Role-based authorization not yet enforced
- Automated deadline/renewal reminders not implemented
- External office synchronization not implemented

