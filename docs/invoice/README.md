# Invoice - Specification-Driven Development

This folder contains specification, requirements, and task documents for the **Invoice** feature.

## Document Index

| File | Purpose |
|------|---------|
| [requirements.md](requirements.md) | Functional and non-functional requirements |
| [data-model.md](data-model.md) | Full field-level entity specification |
| [validation-rules.md](validation-rules.md) | Field, business, and uniqueness validation rules |
| [user-stories.md](user-stories.md) | User stories by persona |
| [tasks.md](tasks.md) | Implementation task breakdown with status |
| [test-scenarios.md](test-scenarios.md) | Service, controller, and UI test cases |
| [api-surface.md](api-surface.md) | Route map and HTTP contract |

## Feature Summary

**Domain**: Financial Documents
**Entity**: `Invoice` (package `com.sdr.ams.model.financial`)
**Route prefix**: `/invoices`
**Status**: ✅ Implemented (v1 — rich tier)

## Source of Truth

- Domain specification: [`invoice.md`](invoice.md)
- Entity: `src/main/java/com/sdr/ams/model/financial/Invoice.java`
- Supporting types: `InvoiceParty.java`, `InvoiceItem.java`
- Service: `src/main/java/com/sdr/ams/service/InvoiceService.java`
- Repository: `src/main/java/com/sdr/ams/repository/InvoiceRepository.java`
- Controller: `src/main/java/com/sdr/ams/controller/InvoiceController.java`
- Templates: `src/main/resources/templates/invoices/`

