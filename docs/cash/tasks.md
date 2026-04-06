# Cash — Implementation Tasks

**Version**: 1.0 | **Date**: 2026-04-04

---

## Legend

| Symbol | Meaning |
|--------|---------|
| ✅ | Completed and in codebase |
| 🔲 | Planned / not yet implemented |
| ⚠️ | Partial or needs review |

---

## Phase 1 — Core CRUD (v1) ✅ Complete

### Data Model
- [x] ✅ TASK-001: Create `Cash` entity extending `CoreEntity`
- [x] ✅ TASK-002: Add core required fields: `cashId`, `amount`, `currency`, `valuationDate`, `cashType`, `dataSource`
- [x] ✅ TASK-003: Add location/ownership fields: `holder`, `holdingAccountReference`, `institution`, `jurisdiction`
- [x] ✅ TASK-004: Add movement fields: `openingBalance`, `closingBalance`, `cashInflows`, `cashOutflows`, `netCashMovement`, `cashForecast`, `reconciliationStatus`
- [x] ✅ TASK-005: Add risk fields: `counterpartyRisk`, `concentrationRisk`, `volatility`, `complianceFlags`
- [x] ✅ TASK-006: Add metadata fields: `responsibleAnalyst`, `tags`, `notes`
- [x] ✅ TASK-007: Define enums: `CashType`, `ReconciliationStatus`, `RiskLevel`
- [x] ✅ TASK-008: Add `@AttributeOverride` mapping `name` → `cashId`
- [x] ✅ TASK-009: Add bean validation annotations (`@NotBlank`, `@NotNull`, `@Size`, `@DecimalMin`)
- [x] ✅ TASK-010: Add unique constraint on `cashId`

### Repository
- [x] ✅ TASK-020: Create `CashRepository` extending `JpaRepository<Cash, Long>`
- [x] ✅ TASK-021: Add `existsCashId(cashId, excludeId)` query for uniqueness check
- [x] ✅ TASK-022: Add optional `findByCashType(...)` for filtering (Phase 2)
- [x] ✅ TASK-023: Add optional `findByReconciliationStatus(...)` for filtering (Phase 2)

### Service
- [x] ✅ TASK-030: Create `CashService` with `findAll()`, `findById()`, `create()`, `update()`, `delete()`
- [x] ✅ TASK-031: Implement `normalizeAndValidate(Cash, excludeId)` with normalization pipeline
- [x] ✅ TASK-032: Implement cashId normalization (uppercase, trim whitespace, empty → null)
- [x] ✅ TASK-033: Implement decimal amount validation (≥ 0)
- [x] ✅ TASK-034: Implement currency validation (3-letter ISO code)
- [x] ✅ TASK-035: Implement required field checks (cashId, amount, currency, valuationDate, cashType, dataSource)
- [x] ✅ TASK-036: Implement uniqueness validation on cashId (with excludeId)
- [x] ✅ TASK-037: Implement explicit field-copy update pattern
- [x] ✅ TASK-038: Throw `IllegalArgumentException` on business/validation violations

### Controller
- [x] ✅ TASK-040: Create `CashController` extending `CoreEntityCrudController<Cash>` or standalone
- [x] ✅ TASK-041: Implement routes: list, create form, create POST, edit form, update POST, delete
- [x] ✅ TASK-042: Populate enum model attributes for dropdowns
- [x] ✅ TASK-043: Set default values on new form (cashType=OPERATING, currency=EUR, reconciliationStatus=PENDING, counterpartyRisk=MEDIUM)
- [x] ✅ TASK-044: Handle `@Valid` + `BindingResult` for bean validation errors
- [x] ✅ TASK-045: Catch `IllegalArgumentException` from service and surface as global form error

### Templates
- [x] ✅ TASK-050: Create `cash/list.html` with columns: cashId, amount, currency, cashType, institution, valuationDate
- [x] ✅ TASK-051: Create `cash/form.html` with all fields organized by section (Core, Location, Movement, Risk, Metadata)
- [x] ✅ TASK-052: Add field-level error display
- [x] ✅ TASK-053: Add global error display for business rule violations
- [x] ✅ TASK-054: Add audit read-only section (createdAt, createdBy, updatedAt, updatedBy)
- [x] ✅ TASK-055: Link shared `templates.css` stylesheet
- [x] ✅ TASK-056: Add Create, View (planned), Edit, Delete action buttons on list

---

## Phase 2 — Enhancements (Planned)

### Detail Page & Rich Views
- [ ] 🔲 TASK-100: Create `cash/detail.html` with all fields, risk indicator badges, audit trail
- [ ] 🔲 TASK-101: Add collapsible sections for optional field groups (Movement, Risk, Metadata)
- [ ] 🔲 TASK-102: Display reconciliation status with color coding (RECONCILED=green, PENDING=yellow, DISPUTED=red)

### Search and Filtering
- [ ] 🔲 TASK-110: Add filter parameters to `CashController#list()` (cashType, reconciliationStatus, currency, institution)
- [ ] 🔲 TASK-111: Add `search(...)` query to `CashRepository` with optional filter parameters
- [ ] 🔲 TASK-112: Add filter form to `cash/list.html`
- [ ] 🔲 TASK-113: Support date range filtering (valuationDate between/after/before)

### Data Quality
- [ ] 🔲 TASK-120: Add sorting options on list page (by cashId, amount, currency, valuationDate, cashType)
- [ ] 🔲 TASK-121: Add pagination to list page for large datasets (e.g., 50 items/page)
- [ ] 🔲 TASK-122: Add item count display ("Showing X of Y cash positions")

### Movement & Risk Indicators
- [ ] 🔲 TASK-130: Add dashboard/summary view showing aggregate cash statistics (total amount by currency, total by cashType)
- [ ] 🔲 TASK-131: Add risk summary (number of HIGH-risk positions, concentration warnings)
- [ ] 🔲 TASK-132: Implement counterparty risk recommendations based on institution concentration

### Lifecycle & Archiving
- [ ] 🔲 TASK-140: Implement soft-delete (add `deletedAt` field; exclude soft-deleted from all queries)
- [ ] 🔲 TASK-141: Add archive quick-action button on list/detail pages
- [ ] 🔲 TASK-142: Implement 7-year retention policy for archived records

### Audit and Compliance
- [ ] 🔲 TASK-150: Allow `createdBy` and `updatedBy` to be populated from logged-in user (Spring Security integration)
- [ ] 🔲 TASK-151: Add field-level audit trail (`AuditLog` entity) tracking changes to sensitive fields
- [ ] 🔲 TASK-152: Implement access control (restrict edit/delete to authorized roles)
- [ ] 🔲 TASK-153: Mask account references in list view; show full only on detail page

### Testing
- [ ] 🔲 TASK-160: Write unit tests for `CashService` (normalization, validation, uniqueness, field copy)
- [ ] 🔲 TASK-161: Write `@WebMvcTest` tests for `CashController` (form submission, validation errors, redirect on success)
- [ ] 🔲 TASK-162: Write integration test for full create → read → update → delete cycle
- [ ] 🔲 TASK-163: Test service behavior with null/empty optional fields
- [ ] 🔲 TASK-164: Test uniqueness checking with excludeId during update

---

## Phase 3 — Advanced Features (Future)

### REST API
- [ ] 🔲 TASK-200: Create `@RestController` for JSON endpoints
- [ ] 🔲 TASK-201: Implement `GET /api/cash` (list)
- [ ] 🔲 TASK-202: Implement `GET /api/cash/{id}` (detail)
- [ ] 🔲 TASK-203: Implement `POST /api/cash` (create)
- [ ] 🔲 TASK-204: Implement `PUT /api/cash/{id}` (update)
- [ ] 🔲 TASK-205: Implement `DELETE /api/cash/{id}` (delete)
- [ ] 🔲 TASK-206: Add pagination and filtering query parameters
- [ ] 🔲 TASK-207: Add OpenAPI/Swagger documentation

### Bank Integration
- [ ] 🔲 TASK-220: Integrate with bank APIs (PSD2-compliant)
- [ ] 🔲 TASK-221: Implement real-time balance sync from bank feeds
- [ ] 🔲 TASK-222: Add reconciliation automation (compare book balance vs. bank balance)
- [ ] 🔲 TASK-223: Implement webhook listener for bank notifications

### Advanced Risk & Monitoring
- [ ] 🔲 TASK-230: Implement automated concentration risk scoring
- [ ] 🔲 TASK-231: Add volatility calculation from historical movement data
- [ ] 🔲 TASK-232: Implement AML transaction monitoring alerts
- [ ] 🔲 TASK-233: Add cash position forecasting model

### Export & Reporting
- [ ] 🔲 TASK-240: Implement CSV export for cash list
- [ ] 🔲 TASK-241: Implement PDF report generation (summary by currency, cashType, risk)
- [ ] 🔲 TASK-242: Add email export functionality
- [ ] 🔲 TASK-243: Implement scheduled report generation (daily, weekly, monthly)

### Multi-Currency & Consolidation
- [ ] 🔲 TASK-250: Add FX rate lookup and currency conversion calculations
- [ ] 🔲 TASK-251: Support consolidated cash views (total across all currencies)
- [ ] 🔲 TASK-252: Add currency gain/loss tracking

---

## Task Dependencies & Ordering

**Critical Path (for Phase 1 to be complete)**:
- TASK-001 → TASK-010 (Entity definition)
- TASK-020 → TASK-023 (Repository)
- TASK-030 → TASK-038 (Service)
- TASK-040 → TASK-045 (Controller)
- TASK-050 → TASK-056 (Templates)

**Phase 2 prerequisites**:
- All Phase 1 tasks must be ✅ Complete
- Unit tests (TASK-160) should be in place before Phase 2 detail view (TASK-100)
- Filtering (TASK-110) requires TASK-111 repository queries

**Phase 3 prerequisites**:
- Phase 2 must be substantially complete
- Security framework (Spring Security) required for access control (TASK-152)
- PSD2 legal review required before bank integration (TASK-220)

---

## Estimation & Effort

| Phase | Scope | Est. Effort | Status |
|-------|-------|-------------|--------|
| **Phase 1** | Core CRUD, basic form/list UI, validation | 40 hours | ✅ Complete |
| **Phase 2** | Detail page, filtering, sorting, soft-delete, audit trail, tests | 60 hours | 🔲 Not started |
| **Phase 3** | REST API, bank integration, risk modeling, reporting | 80+ hours | 🔲 Future |

---

## Quality Gates

Before moving to the next phase:

### Phase 1 → Phase 2
- [x] All Phase 1 tasks marked ✅
- [x] No compilation errors or runtime warnings
- [x] Manual testing: create, edit, delete workflows
- [x] Validation and error handling working correctly

### Phase 2 → Phase 3
- [ ] Unit test coverage ≥ 80% for service layer
- [ ] Integration test coverage ≥ 60% for controller
- [ ] No outstanding security/compliance issues
- [ ] Performance testing on 10K+ cash records
- [ ] Soft-delete and retention policies documented and tested

---

## Known Risks & Mitigations

| Risk | Impact | Mitigation |
|------|--------|-----------|
| `cashId` uniqueness constraint too strict | May block legitimate updates if case/whitespace varies | Normalization pipeline handles case/whitespace; uniqueness check uses normalized value |
| Decimal precision loss on `amount` | Financial data accuracy | Use `DECIMAL(22,2)` (supports ±999,999,999,999,999,999.99) |
| No authentication/authorization in v1 | Any user can modify all cash records | Document security gap; implement Spring Security in Phase 2 |
| Soft-delete not implemented | Hard-delete irreversible; no 7-year retention policy | Soft-delete planned for Phase 2; document manual backup strategy for v1 |
| Manual field-copy update pattern | Prone to missing fields on schema changes | Code review checklist; consider switching to BeanUtils if service grows |

---

## Review Checklist

### For Phase 1 Review
- [ ] Entity class follows `CoreEntity` pattern
- [ ] All enums properly defined with realistic values
- [ ] `@NotNull`, `@Size`, `@DecimalMin` annotations applied
- [ ] `@AttributeOverride` mapping `name` → `cashId` working
- [ ] Repository queries tested (list, findById, existsCashId)
- [ ] Service normalization and validation logic sound
- [ ] Controller catches exceptions and re-renders form on error
- [ ] Templates match bank-account/other entity patterns
- [ ] Validation error messages user-friendly
- [ ] No SQL injection or XSS vulnerabilities

### For Phase 2 Review
- [ ] Detail page shows all fields with appropriate formatting
- [ ] Filter form and repository queries working
- [ ] Pagination and sorting working for large datasets
- [ ] Unit tests passing (≥80% coverage on service)
- [ ] Integration tests for controller workflow
- [ ] Soft-delete flag properly excluded from queries
- [ ] Audit trail accurate and tamper-resistant

---

## Delivery Milestones

| Milestone | Target Date | Deliverables |
|-----------|-------------|--------------|
| **v1.0** | 2026-04-04 | Core CRUD, basic UI, validation, list/form templates |
| **v1.1** | 2026-05-15 | Detail page, filtering, sorting, pagination |
| **v2.0** | 2026-Q3 | Unit tests, soft-delete, audit trail, Spring Security |
| **v3.0** | 2026-Q4 | REST API, bank integration, advanced features |

