# Cash — Specification-Driven Development

| File | Purpose |
|------|---------|
| [cash.md](cash.md) | Comprehensive specification overview & entity reference |
| [requirements.md](requirements.md) | Functional and non-functional requirements |
| [data-model.md](data-model.md) | Field-level entity specification & API contract |
| [validation-rules.md](validation-rules.md) | Validation rules summary table |
| [compliance.md](compliance.md) | Regulatory framework, data classification, GDPR impact |
| [user-stories.md](user-stories.md) | User stories and test scenarios |
| [tasks.md](tasks.md) | Implementation tasks (Phase 1–3 roadmap) |
| [test-scenarios.md](test-scenarios.md) | Comprehensive test cases and acceptance criteria |
| [api-surface.md](api-surface.md) | Routes, request/response contracts, form layout |

**Domain**: Tangible Assets | **Entity**: `Cash` (`com.sdr.ams.model.tangible`) | **Route**: `/cash` | **Status**: ✅ Implemented (v1)

**Sources**: [`docs/cash.md`](cash.md) (high-level overview) · `Cash.java` · `CashService.java` · `CashRepository.java` · `CashController.java` · `templates/cash/`

---

## Quick Navigation

- **Start here**: [cash.md](cash.md) for comprehensive entity overview
- **Implementation**: [tasks.md](tasks.md) for Phase 1–3 roadmap
- **Testing**: [test-scenarios.md](test-scenarios.md) for all test cases
- **Compliance**: [compliance.md](compliance.md) for regulatory notes
- **Web routes**: [api-surface.md](api-surface.md) for controller contracts
- **Business rules**: [requirements.md](requirements.md) for functional spec

---

## SDD Pattern Overview

This folder follows Specification-Driven Development (SDD) structure:

1. **Specification** (`cash.md`, `requirements.md`) – What should be built
2. **Design** (`data-model.md`, `api-surface.md`) – How it's structured and accessed
3. **Validation** (`validation-rules.md`) – Data integrity rules
4. **Tasks** (`tasks.md`) – Implementation roadmap
5. **Testing** (`test-scenarios.md`, `user-stories.md`) – Acceptance criteria
6. **Compliance** (`compliance.md`) – Legal and regulatory requirements

All documents maintain a single source of truth; when entity changes, all docs updated together.

