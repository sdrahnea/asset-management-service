# 🚀 Feature Proposals — Asset Management Service

> Generated: 2026-04-17

## Current State
Full CRUD for 14 asset types (Financial, Tangible, Intangible) with Thymeleaf UI, H2, Spring Boot 4. No auth, no API, no persistence beyond in-memory.

---

## High-Impact Features to Increase Sellability

### 🔐 1. Authentication & Multi-Tenancy *(Critical for any sale)*
- Spring Security login (username/password, optionally OAuth2/Keycloak)
- Role-based access: `ADMIN`, `MANAGER`, `VIEWER`
- Tenant isolation — each organization sees only its own assets
- **Why it sells:** No enterprise buyer will purchase a tool with no auth

### 📊 2. Dashboard & Portfolio Summary *(High perceived value)*
- Landing page with KPI cards: total asset count by category, total portfolio value, recent activity
- Breakdown charts (pie/bar) using Chart.js: assets by type, value distribution
- Alerts panel: overdue invoices, expiring patents/trademarks, vehicles due for inspection
- **Why it sells:** Executives want a single-screen overview — this is the first thing shown in demos

### 💾 3. Persistent Database (PostgreSQL/MySQL) *(Required for production)*
- Replace H2 in-memory with configurable JDBC (Postgres default, H2 for dev)
- Docker Compose file with DB + app
- Flyway/Liquibase migration scripts for schema versioning
- **Why it sells:** Buyers need data to survive restarts; this unlocks self-hosted/cloud deployments

### 📤 4. Export & Reporting *(Frequently requested in procurement)*
- Export any entity list to CSV / Excel (Apache POI)
- PDF report per entity detail (iText or JasperReports)
- Invoice PDF generation (printable, professional layout)
- **Why it sells:** Auditors, accountants, and compliance teams require offline reports

### 🔌 5. REST API Layer *(Opens integration market)*
- `@RestController` endpoints for all 14 entities (`GET`, `POST`, `PUT`, `DELETE`)
- JSON responses with pagination (`Pageable`)
- OpenAPI/Swagger UI at `/swagger-ui`
- API key or JWT bearer token auth
- **Why it sells:** Unlocks SaaS integrations, mobile apps, ERP connectors

### 🔔 6. Notifications & Alerts *(Sticky feature)*
- Email alerts (Spring Mail + SMTP) for: invoice due dates, patent expiry, bond maturity
- In-app notification badge in nav bar
- Configurable alert lead times per asset type
- **Why it sells:** Reduces manual tracking — users feel the system is "working for them"

### 📁 7. Document Attachments *(Compliance requirement)*
- Attach PDF/images to RealEstate, Vehicle, Patent, Trademark, Invoice entities
- Store on local filesystem or S3-compatible storage (MinIO)
- Preview/download links on detail pages
- **Why it sells:** Legal and compliance teams need document evidence alongside asset records

### 🔍 8. Advanced Search & Filtering *(UX differentiator)*
- Global search bar across all entities
- Saved filter presets per user
- Date range pickers, multi-select dropdowns on all list pages
- **Why it sells:** Users managing hundreds of records need fast retrieval

### 📈 9. Valuation History & Audit Trail *(Compliance + analytics)*
- Track `currentMarketValue` changes over time per asset (versioned snapshots)
- Full audit log: who changed what and when
- Timeline view on detail page
- **Why it sells:** Regulators and auditors require change history; differentiates from basic CRUD

### 🌐 10. Localization & Multi-Currency *(International market)*
- i18n support (English + Romanian to start, extensible)
- Currency conversion display (static rates or ECB live feed)
- Date/number format per locale
- **Why it sells:** Opens the product to non-English markets without code changes

### 🏷️ 11. Tagging, Labels & Custom Fields *(Flexibility)*
- Free-form tags on any entity (already partially modeled in some entities)
- User-defined custom fields per asset type (key-value metadata)
- Filter/search by tag
- **Why it sells:** Every buyer has unique data — custom fields prevent "we need to customize this"

### 📦 12. Import from CSV/Excel *(Onboarding accelerator)*
- Bulk import wizard for each entity
- Column mapping UI
- Validation report before commit
- **Why it sells:** Buyers migrating from spreadsheets (the majority) need bulk load — this removes the #1 adoption barrier

---

## Quick Wins (1–3 days each, high demo impact)

| Feature | Effort | Impact |
|---|---|---|
| Dashboard with KPI cards | 2 days | ⭐⭐⭐⭐⭐ |
| CSV export on all list pages | 1 day | ⭐⭐⭐⭐ |
| Persistent DB + Docker Compose | 2 days | ⭐⭐⭐⭐⭐ |
| Spring Security basic login | 2 days | ⭐⭐⭐⭐⭐ |
| Invoice PDF generation | 2 days | ⭐⭐⭐⭐ |
| Swagger/OpenAPI docs | 1 day | ⭐⭐⭐ |
| Global search bar | 1 day | ⭐⭐⭐⭐ |

---

## Recommended Priority Order for MVP Sale

1. **Auth (Spring Security)** — table stakes, block everything else
2. **Persistent DB + Docker** — deploy anywhere
3. **Dashboard** — demo hook, first impression
4. **Export CSV/PDF** — closes procurement objections
5. **REST API + Swagger** — opens integration conversations
6. **Notifications** — retention / repeat engagement

