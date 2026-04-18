# Roadmap Execution Board

This board converts `docs/FEATURE-PROPOSALS.md` into executable parallel streams using the agent catalog in `.github/agents/`.

## Execution Principles

- Work contract-first, then parallel implementation.
- Keep file ownership exclusive during active work.
- Validate every domain change against `docs/<entity>/` specs and `AGENTS.md` patterns.
- Merge in thin vertical slices that leave the application runnable.

## Phase 1 — NOW (sellability + demo safety)

### Initiative A: Authentication + RBAC

**Business goal**
- Remove the biggest enterprise adoption blocker.

**Definition of done**
- Login is required for protected routes.
- Roles exist: `ADMIN`, `MANAGER`, `VIEWER`.
- Read-only users cannot create/update/delete/export.
- README + AGENTS documentation updated.

| ID | Stream | Agent file | Deliverable | Files/Areas | Depends On | Status |
|---|---|---|---|---|---|---|
| AUTH-ARC-01 | Architecture | `engineering-backend-architect.md` | auth design, route protection matrix, role model | `docs/`, security design notes | none | TODO |
| AUTH-APP-01 | Implementation | `engineering-senior-developer.md` | Spring Security config, login page, session flow | `pom.xml`, `src/main/java/.../config`, controllers | AUTH-ARC-01 | TODO |
| AUTH-APP-02 | UI/minimal diff | `engineering-minimal-change-engineer.md` | hide/disable unsafe actions by role in templates | `src/main/resources/templates/**` | AUTH-ARC-01 | TODO |
| AUTH-SEC-01 | Security review | `engineering-security-engineer.md` | authz review and hardening checklist | changed auth files | AUTH-APP-01, AUTH-APP-02 | TODO |
| AUTH-REV-01 | Final review | `engineering-code-reviewer.md` | merge-readiness review | PR diff | AUTH-SEC-01 | TODO |
| AUTH-DOC-01 | Docs | `engineering-technical-writer.md` | setup, roles, access matrix docs | `README.md`, `AGENTS.md`, `docs/*` | AUTH-REV-01 | TODO |

### Initiative B: Persistent DB + Migrations + Demo Deployment

**Business goal**
- Make the application deployable for pilots and evaluations.

**Definition of done**
- H2 remains available for local development.
- Postgres profile is supported.
- Schema versioning exists.
- Demo environment can run with one documented command flow.

| ID | Stream | Agent file | Deliverable | Files/Areas | Depends On | Status |
|---|---|---|---|---|---|---|
| DB-ARC-01 | Architecture | `engineering-backend-architect.md` | profile/migration strategy | `docs/`, config notes | none | TODO |
| DB-DATA-01 | Persistence | `engineering-database-optimizer.md` | DB profile config, migration layout, index review | `pom.xml`, `src/main/resources/application*.yaml`, migrations | DB-ARC-01 | TODO |
| DB-OPS-01 | Packaging | `engineering-devops-automator.md` | Docker Compose and run instructions | deployment files, docs | DB-DATA-01 | TODO |
| DB-REV-01 | Review | `engineering-code-reviewer.md` | config/reliability review | infra + config changes | DB-DATA-01, DB-OPS-01 | TODO |
| DB-DOC-01 | Docs | `engineering-technical-writer.md` | environment matrix and startup guide | `README.md`, `AGENTS.md` | DB-REV-01 | TODO |

### Initiative C: Approval Queue Pilot

**Pilot scope**
- Start with one rich entity and one generic entity.
- Recommended: `RealEstate` + `Cash`.

**Business goal**
- Differentiate on governance, not only CRUD.

**Definition of done**
- Sensitive changes enter pending state.
- Approver can accept/reject with traceable decision.
- Pilot works end-to-end on selected entities.

| ID | Stream | Agent file | Deliverable | Files/Areas | Depends On | Status |
|---|---|---|---|---|---|---|
| APR-ARC-01 | Architecture | `engineering-software-architect.md` | approval model, state machine, audit events | `docs/`, model notes | none | TODO |
| APR-PROD-01 | Product spec | `product-manager.md` | define sensitive fields and UX rules | `docs/FEATURE-PROPOSALS.md`, `docs/<entity>/` | none | TODO |
| APR-APP-01 | Implementation | `engineering-senior-developer.md` | approval entity/service/controller flow | `src/main/java/...` | APR-ARC-01, APR-PROD-01 | TODO |
| APR-APP-02 | UI | `engineering-minimal-change-engineer.md` | pending queue + approve/reject screens | `src/main/resources/templates/**` | APR-ARC-01 | TODO |
| APR-SEC-01 | Security review | `engineering-security-engineer.md` | access-control validation for approver actions | changed files | APR-APP-01, APR-APP-02 | TODO |
| APR-REV-01 | Final review | `engineering-code-reviewer.md` | merge gate review | PR diff | APR-SEC-01 | TODO |

### Initiative D: Stability + Test Coverage Sprint

**Business goal**
- Reduce demo failure risk and improve implementation confidence.

**Definition of done**
- Known redirect bug fixed.
- Focused controller/service tests added for highest-risk paths.
- Smoke checks documented.

| ID | Stream | Agent file | Deliverable | Files/Areas | Depends On | Status |
|---|---|---|---|---|---|---|
| STAB-ONB-01 | Discovery | `engineering-codebase-onboarding-engineer.md` | defect/risk inventory | key controllers, templates, services | none | DONE |
| STAB-FIX-01 | Bugfix | `engineering-minimal-change-engineer.md` | fix known route and consistency defects | affected controllers/templates | STAB-ONB-01 | DONE |
| STAB-VAL-01 | Validation runtime | `engineering-senior-developer.md` | restore provider-backed Bean Validation and prove invalid form posts stay on form | `pom.xml`, `src/test/java/**`, docs | STAB-ONB-01 | DONE |
| STAB-TEST-01 | Test expansion | `engineering-senior-developer.md` | focused tests for home/rich entities/export flow | `src/test/java/**` | STAB-ONB-01 | DONE |
| STAB-REV-01 | Review | `engineering-code-reviewer.md` | regression review | changed files | STAB-FIX-01, STAB-TEST-01 | DONE |

## Phase 2 — NEXT (integration + onboarding + insight)

| Initiative | Primary agent | Outcome |
|---|---|---|
| REST API + OpenAPI | `engineering-backend-architect.md` + `engineering-senior-developer.md` | integration-ready platform |
| Import from CSV/Excel | `engineering-senior-developer.md` + `engineering-database-optimizer.md` | faster spreadsheet migration |
| Valuation history + audit trail | `engineering-software-architect.md` | compliance and analytics differentiation |
| Data Quality Center + global search | `product-manager.md` + `engineering-senior-developer.md` | operational productivity and trust |

## Phase 3 — LATER (retention + expansion)

| Initiative | Primary agent | Outcome |
|---|---|---|
| Notifications center | `product-manager.md` + `engineering-senior-developer.md` | proactive engagement |
| Localization + multi-currency | `product-manager.md` + `engineering-technical-writer.md` | international fit |
| Cross-entity relationship analysis | `engineering-software-architect.md` | stronger due diligence story |

## Weekly Operating Cadence

### Monday
- Contract review for new streams
- File ownership confirmation
- Risk register update

### Wednesday
- Mid-stream integration check
- Rebase and conflict review
- Demo of partial vertical slices

### Friday
- Merge readiness review
- Test/package run
- Docs and release note sync

## Merge Rules

1. No implementation PR merges before architecture contract is accepted.
2. No shared file may be edited by two active streams at the same time.
3. Every merged feature must update docs if user-visible or architecture-visible.
4. Every domain rule change must cite the relevant `docs/<entity>/` source.

## Verification Commands

```powershell
./mvnw.cmd test
./mvnw.cmd package
```

## MCP Execution Note

The current external JetBrains MCP config at `C:\Users\Sergiu\AppData\Local\github-copilot\intellij\mcp.json` contains an empty `servers` object, so there are currently **no configured MCP servers to launch** from that file yet.

That means:
- the orchestration board is ready,
- the repo-side agent mapping is ready,
- but external MCP-based execution still requires at least one real server definition.

