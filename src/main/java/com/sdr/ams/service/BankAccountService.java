package com.sdr.ams.service;
import java.util.List;
import java.util.Locale;
import com.sdr.ams.model.financial.BankAccount;
import com.sdr.ams.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class BankAccountService {
    private static final String IBAN_PATTERN = "^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$";
    private static final String SWIFT_PATTERN = "^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?$";
    private final BankAccountRepository bankAccountRepository;
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }
    @Transactional(readOnly = true)
    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }
    @Transactional(readOnly = true)
    public BankAccount findById(Long id) {
        return bankAccountRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("BankAccount not found for id: " + id));
    }
    public BankAccount create(BankAccount bankAccount) {
        normalizeAndValidate(bankAccount, null);
        return bankAccountRepository.save(bankAccount);
    }
    public BankAccount update(Long id, BankAccount input) {
        BankAccount existing = findById(id);
        existing.setName(input.getName());
        existing.setAccountType(input.getAccountType());
        existing.setBankName(input.getBankName());
        existing.setIban(input.getIban());
        existing.setSwiftCode(input.getSwiftCode());
        existing.setLocalAccountNumber(input.getLocalAccountNumber());
        existing.setBranchName(input.getBranchName());
        existing.setCurrency(input.getCurrency());
        existing.setStatus(input.getStatus());
        existing.setOwnershipType(input.getOwnershipType());
        existing.setOpenedAt(input.getOpenedAt());
        existing.setClosedAt(input.getClosedAt());
        existing.setAccountPurpose(input.getAccountPurpose());
        existing.setAssessmentScore(input.getAssessmentScore());
        existing.setVerificationStatus(input.getVerificationStatus());
        existing.setLastReviewDate(input.getLastReviewDate());
        existing.setLinkedEntity(input.getLinkedEntity());
        normalizeAndValidate(existing, existing.getId());
        return bankAccountRepository.save(existing);
    }
    public void delete(Long id) {
        bankAccountRepository.deleteById(id);
    }
    private void normalizeAndValidate(BankAccount bankAccount, Long excludeId) {
        bankAccount.setName(trimToNull(bankAccount.getName()));
        bankAccount.setBankName(trimToNull(bankAccount.getBankName()));
        bankAccount.setBranchName(trimToNull(bankAccount.getBranchName()));
        bankAccount.setLocalAccountNumber(trimToNull(bankAccount.getLocalAccountNumber()));
        bankAccount.setAccountPurpose(trimToNull(bankAccount.getAccountPurpose()));
        bankAccount.setVerificationStatus(trimToNull(bankAccount.getVerificationStatus()));
        bankAccount.setLinkedEntity(trimToNull(bankAccount.getLinkedEntity()));
        bankAccount.setIban(normalizeCode(bankAccount.getIban()));
        bankAccount.setSwiftCode(normalizeCode(bankAccount.getSwiftCode()));
        bankAccount.setCurrency(normalizeCode(bankAccount.getCurrency()));
        validateMandatoryFields(bankAccount);
        if (bankAccount.getStatus() != BankAccount.Status.CLOSED) {
            bankAccount.setClosedAt(null);
        }
        if (bankAccount.getOpenedAt() != null
            && bankAccount.getClosedAt() != null
            && bankAccount.getClosedAt().isBefore(bankAccount.getOpenedAt())) {
            throw new IllegalArgumentException("Closed date must be greater than or equal to opened date");
        }
        validateIban(bankAccount.getIban());
        validateSwift(bankAccount.getSwiftCode());
        validateUniqueness(bankAccount, excludeId);
    }
    private void validateMandatoryFields(BankAccount bankAccount) {
        requireText(bankAccount.getName(), "Account holder name is required");
        requireText(bankAccount.getBankName(), "Bank name is required");
        requireText(bankAccount.getIban(), "IBAN is required");
        requireText(bankAccount.getCurrency(), "Currency is required");
        if (bankAccount.getAccountType() == null) {
            throw new IllegalArgumentException("Account type is required");
        }
        if (bankAccount.getStatus() == null) {
            throw new IllegalArgumentException("Status is required");
        }
        if (bankAccount.getOwnershipType() == null) {
            throw new IllegalArgumentException("Ownership type is required");
        }
    }
    private void validateUniqueness(BankAccount bankAccount, Long excludeId) {
        if (bankAccountRepository.existsIban(bankAccount.getIban(), excludeId)) {
            throw new IllegalArgumentException("IBAN must be unique");
        }
        if (bankAccount.getLocalAccountNumber() == null) {
            return;
        }
        if (bankAccountRepository.existsLocalAccountForBankAndBranch(
            bankAccount.getLocalAccountNumber(),
            bankAccount.getBankName(),
            bankAccount.getBranchName(),
            excludeId
        )) {
            throw new IllegalArgumentException(
                "Local account number must be unique for the same bank and branch"
            );
        }
    }
    private void validateIban(String iban) {
        if (!iban.matches(IBAN_PATTERN)) {
            throw new IllegalArgumentException("IBAN format is invalid");
        }
        if (!isIbanChecksumValid(iban)) {
            throw new IllegalArgumentException("IBAN checksum is invalid");
        }
    }
    private void validateSwift(String swiftCode) {
        if (swiftCode == null || swiftCode.isBlank()) {
            return;
        }
        if (!swiftCode.matches(SWIFT_PATTERN)) {
            throw new IllegalArgumentException("SWIFT/BIC format is invalid");
        }
    }
    private boolean isIbanChecksumValid(String iban) {
        String rearranged = iban.substring(4) + iban.substring(0, 4);
        int remainder = 0;
        for (char c : rearranged.toCharArray()) {
            if (Character.isDigit(c)) {
                remainder = (remainder * 10 + (c - '0')) % 97;
            } else {
                int value = Character.toUpperCase(c) - 'A' + 10;
                remainder = (remainder * 10 + (value / 10)) % 97;
                remainder = (remainder * 10 + (value % 10)) % 97;
            }
        }
        return remainder == 1;
    }
    private String normalizeCode(String value) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            return null;
        }
        return trimmed.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
    }
    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
    private void requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
