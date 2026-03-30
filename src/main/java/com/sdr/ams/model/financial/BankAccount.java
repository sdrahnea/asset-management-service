package com.sdr.ams.model.financial;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.sdr.ams.model.core.CoreEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(
    name = "bank_account",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_bank_account_iban", columnNames = "iban"),
        @UniqueConstraint(
            name = "uk_bank_account_local_number",
            columnNames = {"local_account_number", "bank_name", "branch_name"}
        )
    }
)
public class BankAccount extends CoreEntity {
    public enum AccountType {
        CURRENT,
        SAVINGS,
        CORPORATE,
        ESCROW,
        DEPOSIT,
        OTHER
    }
    public enum Status {
        ACTIVE,
        CLOSED,
        SUSPENDED
    }
    public enum OwnershipType {
        INDIVIDUAL,
        JOINT,
        CORPORATE
    }
    @NotNull(message = "Account type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType accountType;
    @NotBlank(message = "Bank name is required")
    @Size(max = 200, message = "Bank name must be at most 200 characters")
    @Column(nullable = false, length = 200)
    private String bankName;
    @NotBlank(message = "IBAN is required")
    @Size(max = 34, message = "IBAN must be at most 34 characters")
    @Pattern(
        regexp = "^[A-Za-z]{2}[0-9]{2}[A-Za-z0-9]{11,30}$",
        message = "IBAN format is invalid"
    )
    @Column(nullable = false, length = 34)
    private String iban;
    @Pattern(
        regexp = "^$|^[A-Za-z]{4}[A-Za-z]{2}[A-Za-z0-9]{2}([A-Za-z0-9]{3})?$",
        message = "SWIFT/BIC format is invalid"
    )
    @Size(max = 11, message = "SWIFT/BIC must be at most 11 characters")
    @Column(length = 11)
    private String swiftCode;
    @Size(max = 64, message = "Local account number must be at most 64 characters")
    @Column(length = 64)
    private String localAccountNumber;
    @Size(max = 200, message = "Branch name must be at most 200 characters")
    @Column(length = 200)
    private String branchName;
    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "^[A-Za-z]{3}$", message = "Currency must be a 3-letter ISO code")
    @Column(nullable = false, length = 3)
    private String currency;
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;
    @NotNull(message = "Ownership type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OwnershipType ownershipType;
    @Column
    private LocalDate openedAt;
    @Column
    private LocalDate closedAt;
    @Size(max = 255, message = "Account purpose must be at most 255 characters")
    @Column(length = 255)
    private String accountPurpose;
    @Column(precision = 12, scale = 2)
    private BigDecimal assessmentScore;
    @Size(max = 50, message = "Verification status must be at most 50 characters")
    @Column(length = 50)
    private String verificationStatus;
    @Column
    private LocalDate lastReviewDate;
    @Size(max = 255, message = "Linked entity must be at most 255 characters")
    @Column(length = 255)
    private String linkedEntity;
    @AssertTrue(message = "Closed date must be greater than or equal to opened date")
    public boolean isDateRangeValid() {
        return openedAt == null || closedAt == null || !closedAt.isBefore(openedAt);
    }
    public String getMaskedIban() {
        if (iban == null || iban.isBlank()) {
            return "";
        }
        String normalized = iban.replaceAll("\\s+", "");
        if (normalized.length() <= 4) {
            return normalized;
        }
        return "*".repeat(normalized.length() - 4) + normalized.substring(normalized.length() - 4);
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }
    public String getSwiftCode() {
        return swiftCode;
    }
    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
    public String getLocalAccountNumber() {
        return localAccountNumber;
    }
    public void setLocalAccountNumber(String localAccountNumber) {
        this.localAccountNumber = localAccountNumber;
    }
    public String getBranchName() {
        return branchName;
    }
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public OwnershipType getOwnershipType() {
        return ownershipType;
    }
    public void setOwnershipType(OwnershipType ownershipType) {
        this.ownershipType = ownershipType;
    }
    public LocalDate getOpenedAt() {
        return openedAt;
    }
    public void setOpenedAt(LocalDate openedAt) {
        this.openedAt = openedAt;
    }
    public LocalDate getClosedAt() {
        return closedAt;
    }
    public void setClosedAt(LocalDate closedAt) {
        this.closedAt = closedAt;
    }
    public String getAccountPurpose() {
        return accountPurpose;
    }
    public void setAccountPurpose(String accountPurpose) {
        this.accountPurpose = accountPurpose;
    }
    public BigDecimal getAssessmentScore() {
        return assessmentScore;
    }
    public void setAssessmentScore(BigDecimal assessmentScore) {
        this.assessmentScore = assessmentScore;
    }
    public String getVerificationStatus() {
        return verificationStatus;
    }
    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
    public LocalDate getLastReviewDate() {
        return lastReviewDate;
    }
    public void setLastReviewDate(LocalDate lastReviewDate) {
        this.lastReviewDate = lastReviewDate;
    }
    public String getLinkedEntity() {
        return linkedEntity;
    }
    public void setLinkedEntity(String linkedEntity) {
        this.linkedEntity = linkedEntity;
    }
}
