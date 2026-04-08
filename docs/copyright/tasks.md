# Copyright — Implementation Tasks

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
- [x] ✅ TASK-001: Create `Copyright` entity extending `CoreEntity`
- [x] ✅ TASK-002: Add core required fields: `copyrightId`, `title`, `workType`, `authors`, `copyrightOwners`, `countryOfOrigin`, `copyrightStatus`, `sourceOfInformation`
- [x] ✅ TASK-003: Add work identification fields: `descriptionAbstract`, `versionEdition`, `creationDate`, `publicationReleaseDate`
- [x] ✅ TASK-004: Add ownership fields: `ownershipPercentage`, `moralRightsHolder`
- [x] ✅ TASK-005: Add legal metadata fields: `copyrightNotice`, `registrationNumber`, `registrationAuthority`, `registrationDate`, `protectionStartDate`, `protectionEndDate`, `jurisdictionsCovered`
- [x] ✅ TASK-006: Add rights/restrictions fields: `rightsGranted`, `rightsReserved`, `usageRestrictions`, `exclusivityType`, `derivativeWorksAllowed`, `drmProtectionMeasures`
- [x] ✅ TASK-007: Add licensing fields: `licenseType`, `licensees`, `licenseStartDate`, `licenseEndDate`, `permittedUses`, `royaltyTerms`, `contractReferences`
- [x] ✅ TASK-008: Add financial fields: `royaltyRate`, `revenueGenerated`, `advancePayments`, `licensingFees`, `valuation`
- [x] ✅ TASK-009: Add risk fields: `infringementRisk`, `ownershipDisputesRisk`, `expirationRisk`, `complianceFlags`, `marketRelevanceScore`, `portfolioImpactScore`
- [x] ✅ TASK-010: Add metadata fields: `responsibleReviewer`, `documentReferences`, `tagsCategories`, `notes`
- [x] ✅ TASK-011: Define enums: `WorkType` (8 values), `CopyrightStatus` (4 values), `ExclusivityType` (2 values), `LicenseType` (5 values), `RiskLevel` (3 values)
- [x] ✅ TASK-012: Add `@AttributeOverride` mapping `name` → `copyrightId`
- [x] ✅ TASK-013: Add bean validation annotations (`@NotBlank`, `@NotNull`, `@Size`, `@DecimalMin`, `@DecimalMax`, `@Pattern`)
- [x] ✅ TASK-014: Add `@AssertTrue` validators for temporal ordering (creation → publication, registration date, protection term, license term)
- [x] ✅ TASK-015: Add unique constraint on `copyrightId` and `registrationNumber`

### Repository
- [x] ✅ TASK-020: Create `CopyrightRepository` extending `JpaRepository<Copyright, Long>`
- [x] ✅ TASK-021: Add `existsCopyrightId(copyrightId, excludeId)` query for copyrightId uniqueness check
- [x] ✅ TASK-022: Add `existsRegistrationNumber(registrationNumber, excludeId)` query for registration number uniqueness check

### Service
- [x] ✅ TASK-030: Create `CopyrightService` with `findAll()`, `findById()`, `create()`, `update()`, `delete()`
- [x] ✅ TASK-031: Implement `normalizeAndValidate(Copyright, excludeId)` with normalization pipeline
- [x] ✅ TASK-032: Implement copyrightId normalization (pattern check: alphanumeric, underscores, dashes)
- [x] ✅ TASK-033: Implement required field checks (copyrightId, title, workType, authors, copyrightOwners, countryOfOrigin, copyrightStatus, sourceOfInformation)
- [x] ✅ TASK-034: Implement decimal validation (ownership %, royalty rate, scores all 0–100)
- [x] ✅ TASK-035: Implement temporal validation (creation ≤ publication, registration ≤ today, protection end ≥ start, license end ≥ start)
- [x] ✅ TASK-036: Implement uniqueness validation on copyrightId and registrationNumber (with excludeId)
- [x] ✅ TASK-037: Implement explicit field-copy update pattern
- [x] ✅ TASK-038: Throw `IllegalArgumentException` on business/validation violations

### Controller
- [x] ✅ TASK-040: Create `CopyrightController` with routes for list, create form, create POST, edit form, update POST, delete
- [x] ✅ TASK-041: Populate enum model attributes for dropdowns
- [x] ✅ TASK-042: Set default values on new form (workType=OTHER, copyrightStatus=PENDING, sourceOfInformation="Manual Entry", risk levels=MEDIUM)
- [x] ✅ TASK-043: Handle `@Valid` + `BindingResult` for bean validation errors
- [x] ✅ TASK-044: Catch `IllegalArgumentException` from service and surface as global form error

### Templates
- [x] ✅ TASK-050: Create `copyrights/list.html` with columns: copyrightId, title, authors, workType, copyrightStatus, registrationNumber, countryOfOrigin
- [x] ✅ TASK-051: Create `copyrights/form.html` with all fields organized by section (Work Identity, Ownership, Legal, Rights, Licensing, Financial, Risk, Metadata)
- [x] ✅ TASK-052: Add field-level error display
- [x] ✅ TASK-053: Add global error display for business rule violations
- [x] ✅ TASK-054: Add audit read-only section (createdAt, createdBy, updatedAt, updatedBy)
- [x] ✅ TASK-055: Link shared `templates.css` stylesheet
- [x] ✅ TASK-056: Add Create, Edit, Delete action buttons on list

---

## Phase 2 — Enhancements (Planned)

### Detail Page & Rich Views
- [ ] 🔲 TASK-100: Create `copyrights/detail.html` with all fields, risk badges, expiration warnings
- [ ] 🔲 TASK-101: Add collapsible sections for optional field groups (Work Identity, Rights, Licensing, Financial, Risk, Metadata)
- [ ] 🔲 TASK-102: Display copyright status with color coding (ACTIVE=green, EXPIRED=red, PUBLIC_DOMAIN=gray, PENDING=yellow)
- [ ] 🔲 TASK-103: Add expiration countdown timer (days until protection ends)

### Search and Filtering
- [ ] 🔲 TASK-110: Add filter parameters to `CopyrightController#list()` (workType, copyrightStatus, countryOfOrigin, copyright owners)
- [ ] 🔲 TASK-111: Add `search(...)` query to `CopyrightRepository` with optional filter parameters
- [ ] 🔲 TASK-112: Add filter form to `copyrights/list.html`
- [ ] 🔲 TASK-113: Support date range filtering (registration date, protection dates, license dates)

### Data Quality
- [ ] 🔲 TASK-120: Add sorting options on list page (by copyrightId, title, copyrightStatus, registrationDate, protectionEndDate)
- [ ] 🔲 TASK-121: Add pagination to list page for large datasets (e.g., 50 items/page)
- [ ] 🔲 TASK-122: Add item count display ("Showing X of Y copyrights")

### Risk & Portfolio Indicators
- [ ] 🔲 TASK-130: Add dashboard/summary view showing aggregate copyright statistics
- [ ] 🔲 TASK-131: Add expiration risk summary (number of copyrights expiring in next N years)
- [ ] 🔲 TASK-132: Add portfolio impact summary (total valuation, revenue, by status/type)
- [ ] 🔲 TASK-133: Implement copyright expiration alerts (automated email/notification)

### Lifecycle & Archiving
- [ ] 🔲 TASK-140: Implement soft-delete (add `deletedAt` field; exclude soft-deleted from all queries)
- [ ] 🔲 TASK-141: Add archive quick-action button on list/detail pages
- [ ] 🔲 TASK-142: Implement retention policy for expired/archived copyrights (e.g., 7 years)

### Audit and Compliance
- [ ] 🔲 TASK-150: Allow `createdBy` and `updatedBy` to be populated from logged-in user (Spring Security integration)
- [ ] 🔲 TASK-151: Add field-level audit trail (`AuditLog` entity) tracking changes to sensitive fields
- [ ] 🔲 TASK-152: Implement access control (restrict edit/delete to authorized roles)
- [ ] 🔲 TASK-153: Mask financial data in list view; show full only on detail page

### Testing
- [ ] 🔲 TASK-160: Write unit tests for `CopyrightService` (normalization, validation, uniqueness, temporal ordering)
- [ ] 🔲 TASK-161: Write `@WebMvcTest` tests for `CopyrightController` (form submission, validation errors, redirect on success)
- [ ] 🔲 TASK-162: Write integration test for full create → read → update → delete cycle
- [ ] 🔲 TASK-163: Test temporal validation (@AssertTrue validators)
- [ ] 🔲 TASK-164: Test decimal constraints (ownership %, royalty rate, scores)

---

## Phase 3 — Advanced Features (Future)

### REST API
- [ ] 🔲 TASK-200: Create `@RestController` for JSON endpoints
- [ ] 🔲 TASK-201: Implement `GET /api/copyrights` (list with filtering, pagination)
- [ ] 🔲 TASK-202: Implement `GET /api/copyrights/{id}` (detail)
- [ ] 🔲 TASK-203: Implement `POST /api/copyrights` (create)
- [ ] 🔲 TASK-204: Implement `PUT /api/copyrights/{id}` (update)
- [ ] 🔲 TASK-205: Implement `DELETE /api/copyrights/{id}` (delete)
- [ ] 🔲 TASK-206: Add OpenAPI/Swagger documentation

### IP Office Integration
- [ ] 🔲 TASK-220: Integrate with IP office APIs (OSIM, EUIPO, USPTO, WIPO)
- [ ] 🔲 TASK-221: Implement copyright status sync from registration offices
- [ ] 🔲 TASK-222: Add registration number validation against official registers
- [ ] 🔲 TASK-223: Implement webhook listener for office notifications

### Advanced Monitoring
- [ ] 🔲 TASK-230: Implement copyright expiration monitoring with automated alerts
- [ ] 🔲 TASK-231: Add infringement risk scoring based on type, status, jurisdiction
- [ ] 🔲 TASK-232: Add royalty calculation automation (based on revenue, rate, term)
- [ ] 🔲 TASK-233: Implement portfolio risk analysis (concentration by type, jurisdiction, owner)

### Export & Reporting
- [ ] 🔲 TASK-240: Implement CSV export for copyright list
- [ ] 🔲 TASK-241: Implement PDF report generation (portfolio summary by type, status, expiration)
- [ ] 🔲 TASK-242: Add email report generation (expiring copyrights, high-risk items)
- [ ] 🔲 TASK-243: Implement scheduled report generation (daily, weekly, monthly)

### Multi-Jurisdiction Support
- [ ] 🔲 TASK-250: Add jurisdiction-specific term length reference (Berne min, EU life+70, etc.)
- [ ] 🔲 TASK-251: Implement multi-language support for work types and status
- [ ] 🔲 TASK-252: Add tax & financial implications by jurisdiction

---

## Task Dependencies & Ordering

**Critical Path (for Phase 1 to be complete)**:
- TASK-001 → TASK-015 (Entity definition)
- TASK-020 → TASK-022 (Repository)
- TASK-030 → TASK-038 (Service)
- TASK-040 → TASK-044 (Controller)
- TASK-050 → TASK-056 (Templates)

**Phase 2 prerequisites**:
- All Phase 1 tasks must be ✅ Complete
- Unit tests (TASK-160) should be in place before Phase 2 detail view (TASK-100)
- Filtering (TASK-110) requires TASK-111 repository queries

**Phase 3 prerequisites**:
- Phase 2 must be substantially complete
- Security framework (Spring Security) required for access control (TASK-152)
- IP office legal review required before integration (TASK-220)

---

## Estimation & Effort

| Phase | Scope | Est. Effort | Status |
|-------|-------|-------------|--------|
| **Phase 1** | Core CRUD, forms, validation, 40+ fields | 50 hours | ✅ Complete |
| **Phase 2** | Detail page, filtering, soft-delete, audit trail, tests | 70 hours | 🔲 Not started |
| **Phase 3** | REST API, IP office integration, monitoring, reporting | 90+ hours | 🔲 Future |

---

## Quality Gates

Before moving to the next phase:

### Phase 1 → Phase 2
- [x] All Phase 1 tasks marked ✅
- [x] No compilation errors or runtime warnings
- [x] Manual testing: create, edit, delete workflows
- [x] Validation and error handling working correctly
- [x] All 40+ fields saving/loading correctly
- [x] Temporal validation (@AssertTrue) working

### Phase 2 → Phase 3
- [ ] Unit test coverage ≥ 80% for service layer
- [ ] Integration test coverage ≥ 60% for controller
- [ ] No outstanding security/compliance issues
- [ ] Performance testing on 10K+ copyrights
- [ ] Soft-delete and retention policies tested
- [ ] Financial data masking working

---

## Known Risks & Mitigations

| Risk | Impact | Mitigation |
|------|--------|-----------|
| Complex temporal validation (5 @AssertTrue rules) | Incorrect date ordering accepted | Test each rule independently; validate in integration tests |
| Large form with 40+ fields | User confusion, form abandonment | Section collapsible groups in v2; progressive disclosure |
| Financial data confidentiality | Unauthorized access to valuation/royalty data | Implement role-based access (Finance role) in v2 |
| Decimal precision on financial fields | Loss of precision in valuation | Use DECIMAL(19,2) (supports up to ±9,223,372,036,854,775,807.99) |
| Uniqueness checks (copyrightId, registrationNumber) | Concurrent creation race condition | Database unique constraints + service-level excludeId check |
| No authentication in v1 | Any user can modify all copyrights | Document security gap; implement Spring Security in v2 |

---

## Review Checklist

### For Phase 1 Review
- [ ] Entity class extends CoreEntity correctly
- [ ] All 5 enums properly defined
- [ ] All 40+ fields with correct types and constraints
- [ ] `@AttributeOverride` mapping `name` → `copyrightId` working
- [ ] Temporal validators (@AssertTrue) covering all date ordering rules
- [ ] Repository queries tested (list, findById, exists checks)
- [ ] Service normalization and validation logic sound
- [ ] Controller catches exceptions and re-renders form on error
- [ ] Templates match other entity patterns
- [ ] Validation error messages user-friendly
- [ ] No SQL injection or XSS vulnerabilities

### For Phase 2 Review
- [ ] Detail page shows all 40+ fields appropriately
- [ ] Filter form and repository queries working
- [ ] Pagination and sorting working for large datasets
- [ ] Unit tests passing (≥80% coverage on service)
- [ ] Integration tests for controller workflow
- [ ] Soft-delete flag properly excluded from queries
- [ ] Audit trail accurate and tamper-resistant
- [ ] Financial data masking working

---

## Delivery Milestones

| Milestone | Target Date | Deliverables |
|-----------|-------------|--------------|
| **v1.0** | 2026-04-04 | Core CRUD, basic UI, validation, list/form templates |
| **v1.1** | 2026-05-30 | Detail page, filtering, sorting, pagination |
| **v2.0** | 2026-Q3 | Unit tests, soft-delete, audit trail, Spring Security |
| **v3.0** | 2026-Q4 | REST API, IP office integration, monitoring, reporting |

