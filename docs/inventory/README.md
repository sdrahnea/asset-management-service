# Inventory — Specification-Driven Development

| File | Purpose |
|------|---------|
| [inventory.md](inventory.md) | Comprehensive specification overview & entity reference |
| [requirements.md](requirements.md) | Functional and non-functional requirements |
| [data-model.md](data-model.md) | Field-level entity specification & API contract |
| [validation-rules.md](validation-rules.md) | Validation rules summary table |
| [compliance.md](compliance.md) | Regulatory framework, data classification, operational compliance |
| [user-stories.md](user-stories.md) | User stories and test scenarios |
| [tasks.md](tasks.md) | Implementation tasks (Phase 1–3 roadmap) |
| [test-scenarios.md](test-scenarios.md) | Comprehensive test cases and acceptance criteria |
| [api-surface.md](api-surface.md) | Routes, request/response contracts, form layout |

**Domain**: Tangible Assets | **Entity**: `Inventory` (`com.sdr.ams.model.tangible`) | **Route**: `/inventories` | **Status**: ✅ Implemented (v1)

**Sources**: [`docs/inventory.md`](../inventory-info/inventory.md) (high-level overview) · `Inventory.java` · `InventoryService.java` · `InventoryRepository.java` · `InventoryController.java` · `templates/inventories/`

---

## Quick Navigation

- **Start here**: [inventory.md](inventory.md) for comprehensive entity overview
- **Implementation**: [tasks.md](tasks.md) for Phase 1–3 roadmap
- **Testing**: [test-scenarios.md](test-scenarios.md) for all test cases
- **Compliance**: [compliance.md](compliance.md) for regulatory and operational notes
- **Web routes**: [api-surface.md](api-surface.md) for controller contracts
- **Business rules**: [requirements.md](requirements.md) for functional spec

---

## SDD Pattern Overview

This folder follows Specification-Driven Development (SDD) structure:

1. **Specification** (`inventory.md`, `requirements.md`) – What should be built
2. **Design** (`data-model.md`, `api-surface.md`) – How it's structured and accessed
3. **Validation** (`validation-rules.md`) – Data integrity rules
4. **Tasks** (`tasks.md`) – Implementation roadmap
5. **Testing** (`test-scenarios.md`, `user-stories.md`) – Acceptance criteria
6. **Compliance** (`compliance.md`) – Legal, regulatory, and operational requirements

All documents maintain a single source of truth; when entity changes, all docs updated together.

