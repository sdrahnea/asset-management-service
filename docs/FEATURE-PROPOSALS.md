# 🚀 Feature Proposals — Asset Management Service

> Updated: 2026-04-17

## Current State
Full CRUD for 14 asset types (Financial, Tangible, Intangible) with Thymeleaf UI, H2, Spring Boot 4, demo-data seeding, dashboard, filter-aware exports, and rich-detail PDF reporting. No auth, no REST API layer, and no persistence beyond in-memory H2.

---

## Status Snapshot (Implemented vs Planned)

| Area | Status | Notes |
|---|---|---|
| Dashboard & portfolio summary | Implemented | KPI cards, category/entity charts, recent activity, alerts on `/` |
| Export & reporting | Implemented | CSV/Excel list export + PDF detail export for rich entities |
| Authentication & RBAC | Planned | No Spring Security dependency/config currently present |
| Persistent database profile | Planned | H2 in-memory only (`jdbc:h2:mem:assetdb`) |
| REST API + OpenAPI | Planned | MVC/Thymeleaf controllers only |
| Notifications | Planned | Alerts currently on dashboard only (no email/in-app inbox) |

---

## High-Impact Features to Increase Sellability

### 🔐 1. Authentication & Access Control *(Critical for any sale)* — Planned
- Add Spring Security login and role-based access (`ADMIN`, `MANAGER`, `VIEWER`)
- Restrict sensitive actions by role (create/update/delete/export)
- Optional second phase: SSO (OIDC/Keycloak)
- **Why it sells:** Enterprise buyers require controlled access and accountability

### 💾 2. Persistent Database + Migrations *(Required for production)* — Planned
- Add profile-based persistence (`postgres` default, `h2` for local dev)
- Add schema versioning with Flyway or Liquibase
- Add Docker Compose for app + database demo deployment
- **Why it sells:** Data survives restarts and supports production rollout

### 🔌 3. REST API Layer + OpenAPI *(Integration enabler)* — Planned
- Introduce `@RestController` endpoints for all 14 entities
- Add pagination/filter parameters for list endpoints
- Publish OpenAPI docs (`/swagger-ui`) for partners and integrators
- **Why it sells:** Enables ERP/BI/mobile integrations without UI automation

### 📦 4. Import from CSV/Excel *(Onboarding accelerator)* — Planned
- Bulk import wizard per entity
- Column mapping and preview validation before commit
- Row-level error report export for failed imports
- **Why it sells:** Removes spreadsheet migration friction during onboarding

### 📈 5. Valuation History & Audit Trail *(Compliance + analytics)* — Planned
- Versioned snapshots for key valuation fields
- Field-level audit log timeline (who changed what, when)
- Trend view on detail pages for high-value assets
- **Why it sells:** Improves audit readiness and portfolio governance

### 🔍 6. Global Search & Saved Filters *(Productivity)* — Planned
- One search entry point across all entity modules
- Persist per-user filter presets for repeated workflows
- Expand date-range and multi-select filter UX where missing
- **Why it sells:** Better usability at larger data volumes

### 🔔 7. Notification Delivery Layer *(Retention feature)* — Planned
- Email alerts for due/expiry events already detected by dashboard logic
- Configurable lead times per asset type
- In-app notification center (read/unread)
- **Why it sells:** Makes the system proactive, not just a passive registry

---

## New Proposals (Delta)

### 🧭 A. Asset Lifecycle Workflow Engine — Proposed (Now)
- Add explicit lifecycle states and allowed transitions per entity type
- Enforce transition rules in service layer with validation messages
- **Business value:** Governance-grade workflows beyond basic CRUD

### ✅ B. Approval Queue for Sensitive Changes — Proposed (Now/Next)
- Route high-impact updates (valuation/legal ownership/status) for approval
- Add pending-approval queue and decision audit trail
- **Business value:** Stronger internal control for enterprise governance

### 🧪 C. Data Quality Center — Proposed (Next)
- Surface missing mandatory fields, stale records, and duplicate-risk items
- Add remediation shortcuts to affected entity forms/details
- **Business value:** Improves trust in data used for reporting and decisions

### 📊 D. Portfolio Snapshots & Trend Analytics — Proposed (Next)
- Periodic snapshots for value, category mix, and risk exposure
- Trend widgets on dashboard with period comparison
- **Business value:** Management visibility for portfolio movement over time

### 🔗 E. Cross-Entity Relationship View — Proposed (Next)
- Visualize links (owners, linked entities, invoice parties, legal references)
- Add relationship panel on relevant detail pages
- **Business value:** Better due diligence and impact analysis

### 🌍 F. Localization & Multi-Currency — Proposed (Later)
- i18n message bundles and locale-aware date/number rendering
- Portfolio display conversion layer for selected reporting currency
- **Business value:** Supports broader regional adoption

---

## Quick Wins (1-3 days each, high demo impact)

| Feature | Effort | Impact | Status |
|---|---|---|---|
| Spring Security basic login | 2 days | ⭐⭐⭐⭐⭐ | Planned |
| Persistent DB profile + Docker Compose | 2-3 days | ⭐⭐⭐⭐⭐ | Planned |
| Global search entry page | 1-2 days | ⭐⭐⭐⭐ | Planned |
| Approval queue (single entity pilot) | 2-3 days | ⭐⭐⭐⭐ | Proposed |
| Data quality KPI panel on dashboard | 1-2 days | ⭐⭐⭐⭐ | Proposed |
| OpenAPI/Swagger docs (API-first phase) | 1 day | ⭐⭐⭐ | Planned |

---

## Recommended Priority Order for MVP Sale

### Now
1. **Auth + RBAC** — table stakes for enterprise adoption
2. **Persistent DB + migrations + Docker** — production readiness
3. **Approval queue (pilot)** — immediate governance differentiator
4. **Fix known flow issues and broaden tests** — reduce demo/POC risk

### Next
5. **REST API + OpenAPI** — integration path for buyers
6. **Import from CSV/Excel** — faster customer onboarding
7. **Valuation history + audit timeline** — compliance and analytics value
8. **Data quality center + global search** — operational productivity

### Later
9. **Notifications delivery center (email + in-app inbox)**
10. **Localization + multi-currency experience**
11. **Advanced cross-entity relationship analysis**

