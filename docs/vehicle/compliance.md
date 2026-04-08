# Vehicle — Compliance Notes

**Version**: 1.0 | **Date**: 2026-04-04

## Regulatory Context
Vehicle records commonly intersect with:
- Registration, inspection, insurance, and tax compliance regimes
- Safety and homologation/certification requirements
- Fleet governance and maintenance audit controls
- Data protection for owner/driver/responsible-agent fields

## Sensitive Data Areas
### Legal/identity
- VIN and registration identifiers
- Plate-country combinations and registration documents

### Financial/operational
- Purchase/valuation/cost fields
- Risk/compliance indicators and maintenance history

### Personal data
- `ownerName`, `driverAssignments`, `responsibleAgent`

If personal data is present, GDPR principles apply (purpose limitation, minimization, controlled access).

## Compliance Recommendations
- Keep identifiers normalized and uniqueness constraints strict
- Keep lifecycle dates and service intervals auditable
- Preserve inspection/insurance/tax evidence traceability
- Restrict sensitive legal/financial narratives by role in future authorization model

## Current Gaps / Next Steps
- Role-based authorization not yet enforced
- Automated compliance/service reminder workflows not implemented
- External registration/inspection integration not implemented

