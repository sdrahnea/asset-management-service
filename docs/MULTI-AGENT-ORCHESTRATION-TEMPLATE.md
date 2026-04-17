# Multi-Agent Orchestration Template

Use this template to split work across specialized agents with minimal conflicts.

Scope is tailored to this repo:
- Spring Boot MVC monolith (`controller -> service -> repository -> templates`)
- 14 entities with rich and generic tiers (see `AGENTS.md`)
- Domain rules are sourced from `docs/<entity>/`

## 1) Kickoff Checklist

- [ ] Pick one feature from `docs/FEATURE-PROPOSALS.md`
- [ ] Define acceptance criteria and non-goals
- [ ] Assign file ownership zones (module/layer)
- [ ] Create branches per stream
- [ ] Run architecture pass before implementation streams

## 2) Task Board Template (copy/paste)

| ID | Stream | Owner Agent File | Feature | Scope Boundary | Inputs | Output Artifact | Files Owned | Depends On | Status |
|---|---|---|---|---|---|---|---|---|---|
| ARC-01 | Architecture | `engineering-backend-architect.md` | <feature> | contracts only | spec + existing code | ADR + contract notes | `docs/adr/*` | none | TODO |
| DATA-01 | Persistence | `engineering-database-optimizer.md` | <feature> | schema/query only | ARC-01 | migration/query plan | `src/main/java/com/sdr/ams/repository/*`, `src/main/resources/*` | ARC-01 | TODO |
| APP-01 | Implementation A | `engineering-senior-developer.md` | <feature> | controller/service | ARC-01 | working code + tests | `src/main/java/com/sdr/ams/controller/*`, `src/main/java/com/sdr/ams/service/*` | ARC-01 | TODO |
| APP-02 | Implementation B | `engineering-minimal-change-engineer.md` | <feature> | template/UI only | ARC-01 | list/form/detail updates | `src/main/resources/templates/*` | ARC-01 | TODO |
| SEC-01 | Security | `engineering-security-engineer.md` | <feature> | security review only | APP-01/02 | risk report + fixes | focused files only | APP-01, APP-02 | TODO |
| REV-01 | Review | `engineering-code-reviewer.md` | <feature> | correctness/perf review | all above | findings list | PR comments/report | APP-01, APP-02 | TODO |
| DOC-01 | Docs | `engineering-technical-writer.md` | <feature> | docs only | merged code | README/AGENTS/docs updates | `README.md`, `AGENTS.md`, `docs/*` | REV-01 | TODO |

Recommended status values: `TODO`, `IN_PROGRESS`, `BLOCKED`, `READY_FOR_REVIEW`, `DONE`.

## 3) File Ownership Matrix (conflict prevention)

| Zone | Primary Owner | Secondary Reviewer |
|---|---|---|
| `src/main/java/com/sdr/ams/controller` | Implementation stream | Code reviewer |
| `src/main/java/com/sdr/ams/service` | Implementation stream | Backend architect |
| `src/main/java/com/sdr/ams/repository` | Data stream | Database optimizer |
| `src/main/resources/templates` | UI stream | Code reviewer |
| `src/main/resources/application.yaml` | DevOps/Data stream | Security engineer |
| `docs/*` + `README.md` + `AGENTS.md` | Technical writer | Feature owner |

Rule: one active owner per file path at a time.

## 4) Per-Agent Prompt Cards (copy/paste)

### A) Planning/decomposition
Agent file: `project-manager-senior.md` (or `product-sprint-prioritizer.md`)

Prompt template:
```
Goal: Break feature <name> into 5-10 executable tasks.
Constraints: Keep scope to current Spring MVC monolith, no speculative integrations.
Inputs: docs/FEATURE-PROPOSALS.md, AGENTS.md, docs/<entity>/*.
Output: Task list with effort, dependencies, and acceptance criteria per task.
```

### B) Architecture contract
Agent file: `engineering-backend-architect.md`

Prompt template:
```
Goal: Define architecture contract for <feature>.
Include: controller/service/repository boundaries, data model changes, failure handling.
Constraints: Keep compatibility with existing rich vs generic tier pattern.
Output: Contract doc + impacted files list + migration notes.
```

### C) Code implementation
Agent file: `engineering-senior-developer.md`

Prompt template:
```
Goal: Implement <feature> according to architecture contract.
Constraints: Follow existing package structure under com.sdr.ams.
Validation: Run mvnw tests and keep behavior backward compatible.
Output: Code changes + tests + brief change summary.
```

### D) Minimal-risk hotfix
Agent file: `engineering-minimal-change-engineer.md`

Prompt template:
```
Goal: Fix <bug> with minimal diff.
Constraints: no refactors, no unrelated formatting changes.
Output: targeted patch + root cause note + verification steps.
```

### E) Security hardening
Agent file: `engineering-security-engineer.md`

Prompt template:
```
Goal: Review <feature> for authz/input validation/data exposure risks.
Scope: changed files only.
Output: findings ranked by severity + required remediations.
```

### F) Database/query performance
Agent file: `engineering-database-optimizer.md`

Prompt template:
```
Goal: Optimize persistence for <feature>.
Include: indexes, query patterns, potential N+1 risks, count/filter performance.
Output: concrete repository/query/index recommendations.
```

### G) Final review gate
Agent file: `engineering-code-reviewer.md`

Prompt template:
```
Goal: Perform merge-readiness review for <feature>.
Focus: correctness, regression risk, maintainability, test coverage gaps.
Output: must-fix vs nice-to-have findings.
```

### H) Documentation and release notes
Agent file: `engineering-technical-writer.md`

Prompt template:
```
Goal: Update README.md, AGENTS.md, and docs/<entity>/ tasks/status.
Constraints: only factual repo-backed statements.
Output: concise user-facing and developer-facing updates.
```

## 5) Branch and Merge Protocol

## Branch naming
- `feat/<feature>-contract`
- `feat/<feature>-impl-a`
- `feat/<feature>-impl-b`
- `feat/<feature>-security`
- `feat/<feature>-docs`

## Merge order
1. Contract PR (`-contract`)
2. Data/implementation PRs (`-impl-*`) rebased on contract
3. Security/review fixes
4. Docs PR

## Required PR sections
- Scope
- Files changed
- Acceptance criteria checklist
- Verification evidence
- Risks and rollback notes

## 6) Quality Gates (must pass before merge)

- [ ] Domain rules checked against `docs/<entity>/validation-rules.md`
- [ ] Controller/service/repository boundaries follow `AGENTS.md` conventions
- [ ] No route regressions for `/`, rich routes, or generic routes
- [ ] No template model-contract breaks on list/form/detail pages
- [ ] Tests pass
- [ ] Known caveat check (example: `RealEstateController#delete` redirect)

PowerShell verification commands:
```powershell
./mvnw.cmd test
./mvnw.cmd package
```

## 7) Example Assignment for "Auth + RBAC"

| ID | Agent file | Deliverable |
|---|---|---|
| ARC-SEC-01 | `engineering-backend-architect.md` | auth boundaries + role matrix |
| APP-SEC-01 | `engineering-senior-developer.md` | Spring Security config + login flow |
| APP-SEC-02 | `engineering-minimal-change-engineer.md` | route guards on sensitive actions |
| REV-SEC-01 | `engineering-security-engineer.md` | threat review + hardening fixes |
| DOC-SEC-01 | `engineering-technical-writer.md` | setup and role behavior docs |

## 8) Handoff Template

Use this at the end of each stream:

```markdown
### Handoff
- Stream ID:
- Summary of changes:
- Files touched:
- Acceptance criteria met:
- Known risks:
- Next stream expected input:
```

