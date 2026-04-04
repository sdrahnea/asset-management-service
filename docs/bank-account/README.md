# Bank Account — Specification-Driven Development

This folder contains all specification, requirements, and task documents
for the **Bank Account** feature of the Asset Management Service.

## Document Index

| File | Purpose |
|------|---------|
| [requirements.md](requirements.md) | Functional and non-functional requirements |
| [data-model.md](data-model.md) | Full field-level entity specification |
| [validation-rules.md](validation-rules.md) | Field, business, and uniqueness validation rules |
| [user-stories.md](user-stories.md) | User stories by persona |
| [tasks.md](tasks.md) | Implementation task breakdown with status |
| [test-scenarios.md](test-scenarios.md) | Service, controller, and UI test cases |
| [compliance.md](compliance.md) | GDPR, PCI-DSS, and banking regulatory notes |
| [api-surface.md](api-surface.md) | Route map and HTTP contract |

## Feature Summary

**Domain**: Financial Assets  
**Entity**: `BankAccount` (package `com.sdr.ams.model.financial`)  
**Route prefix**: `/bank-accounts`  
**Status**: ✅ Implemented (v1)

## Source of Truth

- Domain specification: [`docs/bank-account.md`](bank-account.md)
- Entity: `src/main/java/com/sdr/ams/model/financial/BankAccount.java`
- Service: `src/main/java/com/sdr/ams/service/BankAccountService.java`
- Repository: `src/main/java/com/sdr/ams/repository/BankAccountRepository.java`
- Controller: `src/main/java/com/sdr/ams/controller/BankAccountController.java`
- Templates: `src/main/resources/templates/bank-accounts/`

