# Reputation — Compliance Notes

**Version**: 1.0 | **Date**: 2026-04-04

## Regulatory Context
Reputation records often intersect with:
- Data protection and profiling considerations
- Risk/compliance oversight workflows
- Public-source and media-derived information governance
- Internal auditability of scoring and event changes

## Sensitive Data Areas
### Personal/identity
- Subject identification fields (`entityType`, `entityId`, `displayName` depending on type)
- `responsibleAgent`

### Risk/compliance
- `legalIssues`, `ethicalConcerns`, `complianceFlags`, `crisisHistory`

### Strategic/commercial
- Competitive positioning and benchmark fields
- Internal notes and analyst observations

When personal data is present, GDPR principles apply (purpose limitation, minimization, access controls).

## Compliance Recommendations
- Maintain traceable event sourcing and timestamped updates
- Enforce uniqueness and normalization for subject references
- Keep risk/compliance statements auditable and evidence-backed
- Restrict sensitive narrative fields by role in future authorization model

## Current Gaps / Next Steps
- Role-based authorization not yet enforced
- Automated external data-source provenance checks not implemented
- Expanded audit log for field-level score changes not implemented

