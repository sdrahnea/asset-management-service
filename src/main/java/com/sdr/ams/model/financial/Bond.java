package com.sdr.ams.model.financial;

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
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "bond_id", nullable = false, length = 100))
@Table(
    name = "bond",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_bond_bond_id", columnNames = "bond_id"),
        @UniqueConstraint(name = "uk_bond_isin", columnNames = "isin"),
        @UniqueConstraint(name = "uk_bond_cusip_sedol", columnNames = "cusip_sedol")
    }
)
public class Bond extends CoreEntity {

    public enum BondType { GOVERNMENT, CORPORATE, MUNICIPAL, COVERED, GREEN, SUPRANATIONAL, OTHER }
    public enum CouponType { FIXED, FLOATING, ZERO_COUPON }
    public enum CouponFrequency { ANNUAL, SEMIANNUAL, QUARTERLY, MONTHLY }
    public enum DayCountConvention { THIRTY_360, ACT_365, ACT_ACT }
    public enum RedemptionType { BULLET, AMORTIZING, SINKING_FUND }
    public enum Seniority { SENIOR_SECURED, SENIOR_UNSECURED, SUBORDINATED, JUNIOR_SUBORDINATED, OTHER }
    public enum TradingStatus { ACTIVE, SUSPENDED, DELISTED, MATURED }

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

    @NotBlank(message = "Bond ID is required")
    @Size(max = 100, message = "Bond ID must be at most 100 characters")
    public String getBondId() {
        return getName();
    }

    public void setBondId(String bondId) {
        setName(bondId);
    }

    @NotBlank(message = "Bond title is required")
    @Size(max = 255, message = "Bond title must be at most 255 characters")
    @Column(nullable = false, length = 255)
    private String title;

    @Size(max = 12, message = "ISIN must be at most 12 characters")
    @Column(length = 12)
    private String isin;

    @Size(max = 20, message = "CUSIP/SEDOL must be at most 20 characters")
    @Column(name = "cusip_sedol", length = 20)
    private String cusipSedol;

    @NotBlank(message = "Issuer is required")
    @Size(max = 255, message = "Issuer must be at most 255 characters")
    @Column(nullable = false, length = 255)
    private String issuer;

    @Size(max = 100, message = "Issuer country must be at most 100 characters")
    @Column(length = 100)
    private String issuerCountry;

    @NotNull(message = "Bond type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BondType bondType;

    @DecimalMin(value = "0.0", message = "Face value must be greater than or equal to 0")
    @Digits(integer = 18, fraction = 2)
    @Column(precision = 20, scale = 2)
    private BigDecimal faceValue;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CouponType couponType;

    @DecimalMin(value = "0.0", message = "Coupon rate must be greater than or equal to 0")
    @DecimalMax(value = "1000.0", message = "Coupon rate must be at most 1000")
    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal couponRate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CouponFrequency couponFrequency;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private DayCountConvention couponCalculationMethod;

    @DecimalMin(value = "0.0", message = "Current yield must be greater than or equal to 0")
    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal currentYield;

    @DecimalMin(value = "0.0", message = "Yield to maturity must be greater than or equal to 0")
    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal yieldToMaturity;

    @DecimalMin(value = "0.0", message = "Yield to call must be greater than or equal to 0")
    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal yieldToCall;

    @NotBlank(message = "Currency is required")
    @Size(max = 3, message = "Currency must be a 3-letter ISO code")
    @Column(nullable = false, length = 3)
    private String currency;

    @Column
    private LocalDate issueDate;

    @Column
    private LocalDate maturityDate;

    @Column
    private LocalDate callDate;

    @Column
    private LocalDate putDate;

    @Column(length = 1000)
    private String amortizationSchedule;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RedemptionType redemptionType;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Seniority seniority;

    @Column(length = 1000)
    private String collateral;

    @Column(length = 2000)
    private String covenants;

    @Column(length = 500)
    private String prospectusReference;

    @Size(max = 50, message = "Rating must be at most 50 characters")
    @Column(length = 50)
    private String rating;

    @DecimalMin(value = "0.0", message = "Clean price must be greater than or equal to 0")
    @Digits(integer = 16, fraction = 4)
    @Column(precision = 20, scale = 4)
    private BigDecimal cleanPrice;

    @DecimalMin(value = "0.0", message = "Dirty price must be greater than or equal to 0")
    @Digits(integer = 16, fraction = 4)
    @Column(precision = 20, scale = 4)
    private BigDecimal dirtyPrice;

    @DecimalMin(value = "0.0", message = "Accrued interest must be greater than or equal to 0")
    @Digits(integer = 16, fraction = 4)
    @Column(precision = 20, scale = 4)
    private BigDecimal accruedInterest;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TradingStatus tradingStatus;

    @Size(max = 100, message = "Trading venue must be at most 100 characters")
    @Column(length = 100)
    private String tradingVenue;

    @DecimalMin(value = "0.0", message = "Bid-ask spread must be greater than or equal to 0")
    @Digits(integer = 12, fraction = 4)
    @Column(precision = 16, scale = 4)
    private BigDecimal bidAskSpread;

    @DecimalMin(value = "0.0", message = "Trading volume must be greater than or equal to 0")
    @Digits(integer = 18, fraction = 2)
    @Column(precision = 20, scale = 2)
    private BigDecimal tradingVolume;

    @DecimalMin(value = "0.0", message = "Turnover must be greater than or equal to 0")
    @Digits(integer = 18, fraction = 2)
    @Column(precision = 20, scale = 2)
    private BigDecimal turnover;

    @DecimalMin(value = "0.0", message = "Risk score must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Risk score must be at most 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal riskScore;

    @DecimalMin(value = "0.0", message = "Macaulay duration must be greater than or equal to 0")
    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal durationMacaulay;

    @DecimalMin(value = "0.0", message = "Modified duration must be greater than or equal to 0")
    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal durationModified;

    @Digits(integer = 12, fraction = 6)
    @Column(precision = 18, scale = 6)
    private BigDecimal convexity;

    @DecimalMin(value = "0.0", message = "Probability of default must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Probability of default must be at most 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal probabilityOfDefault;

    @DecimalMin(value = "0.0", message = "Loss given default must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Loss given default must be at most 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal lossGivenDefault;

    @Digits(integer = 16, fraction = 4)
    @Column(precision = 20, scale = 4)
    private BigDecimal valueAtRisk;

    @Column(length = 2000)
    private String stressTestResults;

    @DecimalMin(value = "0.0", message = "ESG score must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "ESG score must be at most 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal esgScore;

    @Size(max = 255, message = "Portfolio reference must be at most 255 characters")
    @Column(length = 255)
    private String portfolioReference;

    @Column(length = 4000)
    private String assessmentHistory;

    @Column(length = 2000)
    private String documents;

    @Size(max = 500, message = "Tags must be at most 500 characters")
    @Column(length = 500)
    private String tags;

    @Column(length = 4000)
    private String analystNotes;

    @NotBlank(message = "Data source is required")
    @Size(max = 100, message = "Data source must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String dataSource;

    @Size(max = 100, message = "Responsible agent must be at most 100 characters")
    @Column(length = 100)
    private String responsibleAgent;

    @Column(length = 4000)
    private String notes;

    @AssertTrue(message = "Maturity date must be on or after issue date")
    public boolean isLifecycleRangeValid() {
        return issueDate == null || maturityDate == null || !maturityDate.isBefore(issueDate);
    }

    @AssertTrue(message = "Call date must be on or after issue date")
    public boolean isCallDateAfterIssueDate() {
        return issueDate == null || callDate == null || !callDate.isBefore(issueDate);
    }

    @AssertTrue(message = "Put date must be on or after issue date")
    public boolean isPutDateAfterIssueDate() {
        return issueDate == null || putDate == null || !putDate.isBefore(issueDate);
    }

    @AssertTrue(message = "Call date must be on or before maturity date")
    public boolean isCallDateBeforeMaturityDate() {
        return maturityDate == null || callDate == null || !callDate.isAfter(maturityDate);
    }

    @AssertTrue(message = "Put date must be on or before maturity date")
    public boolean isPutDateBeforeMaturityDate() {
        return maturityDate == null || putDate == null || !putDate.isAfter(maturityDate);
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIsin() { return isin; }
    public void setIsin(String isin) { this.isin = isin; }
    public String getCusipSedol() { return cusipSedol; }
    public void setCusipSedol(String cusipSedol) { this.cusipSedol = cusipSedol; }
    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }
    public String getIssuerCountry() { return issuerCountry; }
    public void setIssuerCountry(String issuerCountry) { this.issuerCountry = issuerCountry; }
    public BondType getBondType() { return bondType; }
    public void setBondType(BondType bondType) { this.bondType = bondType; }
    public BigDecimal getFaceValue() { return faceValue; }
    public void setFaceValue(BigDecimal faceValue) { this.faceValue = faceValue; }
    public CouponType getCouponType() { return couponType; }
    public void setCouponType(CouponType couponType) { this.couponType = couponType; }
    public BigDecimal getCouponRate() { return couponRate; }
    public void setCouponRate(BigDecimal couponRate) { this.couponRate = couponRate; }
    public CouponFrequency getCouponFrequency() { return couponFrequency; }
    public void setCouponFrequency(CouponFrequency couponFrequency) { this.couponFrequency = couponFrequency; }
    public DayCountConvention getCouponCalculationMethod() { return couponCalculationMethod; }
    public void setCouponCalculationMethod(DayCountConvention couponCalculationMethod) { this.couponCalculationMethod = couponCalculationMethod; }
    public BigDecimal getCurrentYield() { return currentYield; }
    public void setCurrentYield(BigDecimal currentYield) { this.currentYield = currentYield; }
    public BigDecimal getYieldToMaturity() { return yieldToMaturity; }
    public void setYieldToMaturity(BigDecimal yieldToMaturity) { this.yieldToMaturity = yieldToMaturity; }
    public BigDecimal getYieldToCall() { return yieldToCall; }
    public void setYieldToCall(BigDecimal yieldToCall) { this.yieldToCall = yieldToCall; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    public LocalDate getMaturityDate() { return maturityDate; }
    public void setMaturityDate(LocalDate maturityDate) { this.maturityDate = maturityDate; }
    public LocalDate getCallDate() { return callDate; }
    public void setCallDate(LocalDate callDate) { this.callDate = callDate; }
    public LocalDate getPutDate() { return putDate; }
    public void setPutDate(LocalDate putDate) { this.putDate = putDate; }
    public String getAmortizationSchedule() { return amortizationSchedule; }
    public void setAmortizationSchedule(String amortizationSchedule) { this.amortizationSchedule = amortizationSchedule; }
    public RedemptionType getRedemptionType() { return redemptionType; }
    public void setRedemptionType(RedemptionType redemptionType) { this.redemptionType = redemptionType; }
    public Seniority getSeniority() { return seniority; }
    public void setSeniority(Seniority seniority) { this.seniority = seniority; }
    public String getCollateral() { return collateral; }
    public void setCollateral(String collateral) { this.collateral = collateral; }
    public String getCovenants() { return covenants; }
    public void setCovenants(String covenants) { this.covenants = covenants; }
    public String getProspectusReference() { return prospectusReference; }
    public void setProspectusReference(String prospectusReference) { this.prospectusReference = prospectusReference; }
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
    public BigDecimal getCleanPrice() { return cleanPrice; }
    public void setCleanPrice(BigDecimal cleanPrice) { this.cleanPrice = cleanPrice; }
    public BigDecimal getDirtyPrice() { return dirtyPrice; }
    public void setDirtyPrice(BigDecimal dirtyPrice) { this.dirtyPrice = dirtyPrice; }
    public BigDecimal getAccruedInterest() { return accruedInterest; }
    public void setAccruedInterest(BigDecimal accruedInterest) { this.accruedInterest = accruedInterest; }
    public TradingStatus getTradingStatus() { return tradingStatus; }
    public void setTradingStatus(TradingStatus tradingStatus) { this.tradingStatus = tradingStatus; }
    public String getTradingVenue() { return tradingVenue; }
    public void setTradingVenue(String tradingVenue) { this.tradingVenue = tradingVenue; }
    public BigDecimal getBidAskSpread() { return bidAskSpread; }
    public void setBidAskSpread(BigDecimal bidAskSpread) { this.bidAskSpread = bidAskSpread; }
    public BigDecimal getTradingVolume() { return tradingVolume; }
    public void setTradingVolume(BigDecimal tradingVolume) { this.tradingVolume = tradingVolume; }
    public BigDecimal getTurnover() { return turnover; }
    public void setTurnover(BigDecimal turnover) { this.turnover = turnover; }
    public BigDecimal getRiskScore() { return riskScore; }
    public void setRiskScore(BigDecimal riskScore) { this.riskScore = riskScore; }
    public BigDecimal getDurationMacaulay() { return durationMacaulay; }
    public void setDurationMacaulay(BigDecimal durationMacaulay) { this.durationMacaulay = durationMacaulay; }
    public BigDecimal getDurationModified() { return durationModified; }
    public void setDurationModified(BigDecimal durationModified) { this.durationModified = durationModified; }
    public BigDecimal getConvexity() { return convexity; }
    public void setConvexity(BigDecimal convexity) { this.convexity = convexity; }
    public BigDecimal getProbabilityOfDefault() { return probabilityOfDefault; }
    public void setProbabilityOfDefault(BigDecimal probabilityOfDefault) { this.probabilityOfDefault = probabilityOfDefault; }
    public BigDecimal getLossGivenDefault() { return lossGivenDefault; }
    public void setLossGivenDefault(BigDecimal lossGivenDefault) { this.lossGivenDefault = lossGivenDefault; }
    public BigDecimal getValueAtRisk() { return valueAtRisk; }
    public void setValueAtRisk(BigDecimal valueAtRisk) { this.valueAtRisk = valueAtRisk; }
    public String getStressTestResults() { return stressTestResults; }
    public void setStressTestResults(String stressTestResults) { this.stressTestResults = stressTestResults; }
    public BigDecimal getEsgScore() { return esgScore; }
    public void setEsgScore(BigDecimal esgScore) { this.esgScore = esgScore; }
    public String getPortfolioReference() { return portfolioReference; }
    public void setPortfolioReference(String portfolioReference) { this.portfolioReference = portfolioReference; }
    public String getAssessmentHistory() { return assessmentHistory; }
    public void setAssessmentHistory(String assessmentHistory) { this.assessmentHistory = assessmentHistory; }
    public String getDocuments() { return documents; }
    public void setDocuments(String documents) { this.documents = documents; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public String getAnalystNotes() { return analystNotes; }
    public void setAnalystNotes(String analystNotes) { this.analystNotes = analystNotes; }
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
    public String getResponsibleAgent() { return responsibleAgent; }
    public void setResponsibleAgent(String responsibleAgent) { this.responsibleAgent = responsibleAgent; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
