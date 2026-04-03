package com.sdr.ams.model.tangible;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sdr.ams.model.core.CoreEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "cash_id", nullable = false, length = 100))
@Table(
	name = "cash",
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_cash_cash_id", columnNames = "cash_id")
	}
)
public class Cash extends CoreEntity {

	public enum CashType { OPERATING, RESTRICTED, PETTY, INVESTMENT, ESCROW, OTHER }
	public enum ReconciliationStatus { RECONCILED, PENDING, DISPUTED }
	public enum RiskLevel { LOW, MEDIUM, HIGH }

	@Override
	@JsonIgnore
	public String getName() {
		return super.getName();
	}

	@Override
	@JsonIgnore
	public void setName(String name) {
		super.setName(name);
	}

	@NotBlank(message = "Cash ID is required")
	@Size(max = 100, message = "Cash ID must be at most 100 characters")
	@Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Cash ID must be alphanumeric")
	public String getCashId() {
		return getName();
	}

	public void setCashId(String cashId) {
		setName(cashId);
	}

	// Core cash attributes
	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.0", message = "Amount must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;

	@NotBlank(message = "Currency is required")
	@Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter ISO code")
	@Column(nullable = false, length = 3)
	private String currency;

	@NotNull(message = "Valuation date is required")
	@Column(nullable = false)
	private LocalDate valuationDate;

	@NotNull(message = "Cash type is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private CashType cashType;

	// Location and ownership details
	@NotBlank(message = "Holder/owner is required")
	@Size(max = 255, message = "Holder/owner must be at most 255 characters")
	@Column(nullable = false, length = 255)
	private String holderOwner;

	@Size(max = 100, message = "Holding account reference must be at most 100 characters")
	@Column(length = 100)
	private String holdingAccountReference;

	@Size(max = 150, message = "Institution must be at most 150 characters")
	@Column(length = 150)
	private String institution;

	@Size(max = 100, message = "Jurisdiction must be at most 100 characters")
	@Column(length = 100)
	private String jurisdiction;

	// Lifecycle and movement
	@DecimalMin(value = "0.0", message = "Opening balance must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal openingBalance;

	@DecimalMin(value = "0.0", message = "Closing balance must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal closingBalance;

	@DecimalMin(value = "0.0", message = "Cash inflows must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal cashInflows;

	@DecimalMin(value = "0.0", message = "Cash outflows must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal cashOutflows;

	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal netCashMovement;

	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal cashForecastExpectedMovements;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ReconciliationStatus reconciliationStatus;

	// Assessment and risk indicators
	@DecimalMin(value = "0.0", message = "Current assets input must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal liquidityRatioCurrentAssets;

	@DecimalMin(value = "0.0", message = "Current liabilities input must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal liquidityRatioCurrentLiabilities;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RiskLevel cashConcentrationRisk;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RiskLevel counterpartyRisk;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RiskLevel cashVolatility;

	@Size(max = 2000, message = "Compliance flags must be at most 2000 characters")
	@Column(length = 2000)
	private String complianceFlags;

	// Operational metadata
	@NotBlank(message = "Source of data is required")
	@Size(max = 100, message = "Source of data must be at most 100 characters")
	@Column(nullable = false, length = 100)
	private String sourceOfData;

	@Size(max = 100, message = "Responsible analyst must be at most 100 characters")
	@Column(length = 100)
	private String responsibleAnalyst;

	@Size(max = 2000, message = "Notes/comments must be at most 2000 characters")
	@Column(length = 2000)
	private String notesComments;

	@Size(max = 500, message = "Tags/categories must be at most 500 characters")
	@Column(length = 500)
	private String tagsCategories;

	// Optional extensions
	@Size(max = 500, message = "Cash pooling structure must be at most 500 characters")
	@Column(length = 500)
	private String cashPoolingStructure;

	@Size(max = 1000, message = "Intercompany cash positions must be at most 1000 characters")
	@Column(length = 1000)
	private String intercompanyCashPositions;

	@Size(max = 1000, message = "Hedging information must be at most 1000 characters")
	@Column(length = 1000)
	private String hedgingInformation;

	@DecimalMin(value = "0.0", message = "Cash available for investment must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal cashAvailableForInvestment;

	@DecimalMin(value = "0.0", message = "Settlement cash must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal settlementCash;

	@DecimalMin(value = "0.0", message = "Margin cash must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal marginCash;

	@Size(max = 2000, message = "Cash source documentation must be at most 2000 characters")
	@Column(length = 2000)
	private String cashSourceDocumentation;

	@Size(max = 2000, message = "Transaction-level AML flags must be at most 2000 characters")
	@Column(length = 2000)
	private String transactionLevelAmlFlags;

	@Size(max = 2000, message = "Restrictions/legal holds must be at most 2000 characters")
	@Column(length = 2000)
	private String restrictionsLegalHolds;

	@AssertTrue(message = "Valuation date must be today or earlier")
	public boolean isValuationDateValid() {
		return valuationDate == null || !valuationDate.isAfter(LocalDate.now());
	}

	@AssertTrue(message = "Net cash movement must equal cash inflows minus cash outflows")
	public boolean isNetCashMovementValid() {
		if (cashInflows == null || cashOutflows == null || netCashMovement == null) {
			return true;
		}
		return cashInflows.subtract(cashOutflows).compareTo(netCashMovement) == 0;
	}

	@AssertTrue(message = "Closing balance must equal opening balance plus net cash movement")
	public boolean isClosingBalanceValid() {
		if (openingBalance == null || netCashMovement == null || closingBalance == null) {
			return true;
		}
		return openingBalance.add(netCashMovement).compareTo(closingBalance) == 0;
	}

	public BigDecimal getAmount() { return amount; }
	public void setAmount(BigDecimal amount) { this.amount = amount; }
	public String getCurrency() { return currency; }
	public void setCurrency(String currency) { this.currency = currency; }
	public LocalDate getValuationDate() { return valuationDate; }
	public void setValuationDate(LocalDate valuationDate) { this.valuationDate = valuationDate; }
	public CashType getCashType() { return cashType; }
	public void setCashType(CashType cashType) { this.cashType = cashType; }
	public String getHolderOwner() { return holderOwner; }
	public void setHolderOwner(String holderOwner) { this.holderOwner = holderOwner; }
	public String getHoldingAccountReference() { return holdingAccountReference; }
	public void setHoldingAccountReference(String holdingAccountReference) { this.holdingAccountReference = holdingAccountReference; }
	public String getInstitution() { return institution; }
	public void setInstitution(String institution) { this.institution = institution; }
	public String getJurisdiction() { return jurisdiction; }
	public void setJurisdiction(String jurisdiction) { this.jurisdiction = jurisdiction; }
	public BigDecimal getOpeningBalance() { return openingBalance; }
	public void setOpeningBalance(BigDecimal openingBalance) { this.openingBalance = openingBalance; }
	public BigDecimal getClosingBalance() { return closingBalance; }
	public void setClosingBalance(BigDecimal closingBalance) { this.closingBalance = closingBalance; }
	public BigDecimal getCashInflows() { return cashInflows; }
	public void setCashInflows(BigDecimal cashInflows) { this.cashInflows = cashInflows; }
	public BigDecimal getCashOutflows() { return cashOutflows; }
	public void setCashOutflows(BigDecimal cashOutflows) { this.cashOutflows = cashOutflows; }
	public BigDecimal getNetCashMovement() { return netCashMovement; }
	public void setNetCashMovement(BigDecimal netCashMovement) { this.netCashMovement = netCashMovement; }
	public BigDecimal getCashForecastExpectedMovements() { return cashForecastExpectedMovements; }
	public void setCashForecastExpectedMovements(BigDecimal cashForecastExpectedMovements) { this.cashForecastExpectedMovements = cashForecastExpectedMovements; }
	public ReconciliationStatus getReconciliationStatus() { return reconciliationStatus; }
	public void setReconciliationStatus(ReconciliationStatus reconciliationStatus) { this.reconciliationStatus = reconciliationStatus; }
	public BigDecimal getLiquidityRatioCurrentAssets() { return liquidityRatioCurrentAssets; }
	public void setLiquidityRatioCurrentAssets(BigDecimal liquidityRatioCurrentAssets) { this.liquidityRatioCurrentAssets = liquidityRatioCurrentAssets; }
	public BigDecimal getLiquidityRatioCurrentLiabilities() { return liquidityRatioCurrentLiabilities; }
	public void setLiquidityRatioCurrentLiabilities(BigDecimal liquidityRatioCurrentLiabilities) { this.liquidityRatioCurrentLiabilities = liquidityRatioCurrentLiabilities; }
	public RiskLevel getCashConcentrationRisk() { return cashConcentrationRisk; }
	public void setCashConcentrationRisk(RiskLevel cashConcentrationRisk) { this.cashConcentrationRisk = cashConcentrationRisk; }
	public RiskLevel getCounterpartyRisk() { return counterpartyRisk; }
	public void setCounterpartyRisk(RiskLevel counterpartyRisk) { this.counterpartyRisk = counterpartyRisk; }
	public RiskLevel getCashVolatility() { return cashVolatility; }
	public void setCashVolatility(RiskLevel cashVolatility) { this.cashVolatility = cashVolatility; }
	public String getComplianceFlags() { return complianceFlags; }
	public void setComplianceFlags(String complianceFlags) { this.complianceFlags = complianceFlags; }
	public String getSourceOfData() { return sourceOfData; }
	public void setSourceOfData(String sourceOfData) { this.sourceOfData = sourceOfData; }
	public String getResponsibleAnalyst() { return responsibleAnalyst; }
	public void setResponsibleAnalyst(String responsibleAnalyst) { this.responsibleAnalyst = responsibleAnalyst; }
	public String getNotesComments() { return notesComments; }
	public void setNotesComments(String notesComments) { this.notesComments = notesComments; }
	public String getTagsCategories() { return tagsCategories; }
	public void setTagsCategories(String tagsCategories) { this.tagsCategories = tagsCategories; }
	public String getCashPoolingStructure() { return cashPoolingStructure; }
	public void setCashPoolingStructure(String cashPoolingStructure) { this.cashPoolingStructure = cashPoolingStructure; }
	public String getIntercompanyCashPositions() { return intercompanyCashPositions; }
	public void setIntercompanyCashPositions(String intercompanyCashPositions) { this.intercompanyCashPositions = intercompanyCashPositions; }
	public String getHedgingInformation() { return hedgingInformation; }
	public void setHedgingInformation(String hedgingInformation) { this.hedgingInformation = hedgingInformation; }
	public BigDecimal getCashAvailableForInvestment() { return cashAvailableForInvestment; }
	public void setCashAvailableForInvestment(BigDecimal cashAvailableForInvestment) { this.cashAvailableForInvestment = cashAvailableForInvestment; }
	public BigDecimal getSettlementCash() { return settlementCash; }
	public void setSettlementCash(BigDecimal settlementCash) { this.settlementCash = settlementCash; }
	public BigDecimal getMarginCash() { return marginCash; }
	public void setMarginCash(BigDecimal marginCash) { this.marginCash = marginCash; }
	public String getCashSourceDocumentation() { return cashSourceDocumentation; }
	public void setCashSourceDocumentation(String cashSourceDocumentation) { this.cashSourceDocumentation = cashSourceDocumentation; }
	public String getTransactionLevelAmlFlags() { return transactionLevelAmlFlags; }
	public void setTransactionLevelAmlFlags(String transactionLevelAmlFlags) { this.transactionLevelAmlFlags = transactionLevelAmlFlags; }
	public String getRestrictionsLegalHolds() { return restrictionsLegalHolds; }
	public void setRestrictionsLegalHolds(String restrictionsLegalHolds) { this.restrictionsLegalHolds = restrictionsLegalHolds; }
}
