package com.sdr.ams.model.intangible;

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
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "trademark_id", nullable = false, length = 100))
@Table(
    name = "trademark",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_trademark_id", columnNames = "trademark_id"),
        @UniqueConstraint(name = "uk_trademark_application_number", columnNames = "application_number"),
        @UniqueConstraint(name = "uk_trademark_registration_number", columnNames = "registration_number")
    }
)
public class Trademark extends CoreEntity {

    public enum MarkType { WORDMARK, FIGURATIVE, COMBINED, THREE_D, SOUND, COLOR, HOLOGRAM, OTHER }
    public enum OwnerType { PERSON, CORPORATION, PARTNERSHIP, NGO, OTHER }
    public enum LegalStatus { FILED, PUBLISHED, OPPOSED, REGISTERED, EXPIRED, CANCELLED, REFUSED }
    public enum InternationalFilingSystem { WIPO_MADRID, EUIPO, NATIONAL, OTHER }
    public enum UsageStatus { IN_USE, INTENDED_USE, DISCONTINUED }
    public enum LicensingStatus { NOT_LICENSED, LICENSED, EXCLUSIVE, NON_EXCLUSIVE }
    public enum DistinctivenessScore { ARBITRARY, SUGGESTIVE, DESCRIPTIVE, GENERIC }
    public enum CommercialImportance { CORE_BRAND, SUB_BRAND, LEGACY_MARK, OTHER }

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

    @NotBlank(message = "Trademark ID is required")
    @Size(max = 100, message = "Trademark ID must be at most 100 characters")
    public String getTrademarkId() {
        return getName();
    }

    public void setTrademarkId(String trademarkId) {
        setName(trademarkId);
    }

    @NotBlank(message = "Mark name is required")
    @Size(max = 255, message = "Mark name must be at most 255 characters")
    @Column(nullable = false, length = 255)
    private String markName;

    @NotNull(message = "Mark type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MarkType markType;

    @Column(length = 2000)
    private String markDescription;

    @Size(max = 500, message = "Nice classes must be at most 500 characters")
    @Column(length = 500)
    private String niceClasses;

    @Size(max = 500, message = "Vienna codes must be at most 500 characters")
    @Column(length = 500)
    private String viennaCodes;

    @Size(max = 200, message = "Industry sector must be at most 200 characters")
    @Column(length = 200)
    private String industrySector;

    @Size(max = 500, message = "Tags must be at most 500 characters")
    @Column(length = 500)
    private String tags;

    @NotBlank(message = "Owner name is required")
    @Size(max = 255, message = "Owner name must be at most 255 characters")
    @Column(nullable = false, length = 255)
    private String ownerName;

    @NotNull(message = "Owner type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OwnerType ownerType;

    @Column(length = 500)
    private String ownerAddress;

    @Column(length = 255)
    private String representative;

    @Column(length = 2000)
    private String assignmentHistory;

    @Column(length = 1000)
    private String coOwners;

    @NotBlank(message = "Application number is required")
    @Size(max = 100, message = "Application number must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String applicationNumber;

    @NotNull(message = "Application date is required")
    @Column(nullable = false)
    private LocalDate applicationDate;

    @Size(max = 100, message = "Registration number must be at most 100 characters")
    @Column(length = 100)
    private String registrationNumber;

    @Column
    private LocalDate registrationDate;

    @Column(nullable = false)
    private boolean priorityClaimed;

    @Size(max = 100, message = "Priority number must be at most 100 characters")
    @Column(length = 100)
    private String priorityNumber;

    @Column
    private LocalDate priorityDate;

    @NotNull(message = "Legal status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private LegalStatus legalStatus;

    @Column
    private LocalDate expirationDate;

    @Column(length = 2000)
    private String renewalHistory;

    @Column(length = 2000)
    private String oppositions;

    @Column(length = 2000)
    private String cancellationActions;

    @Column(length = 1000)
    private String jurisdictionsFiled;

    @Column(length = 1000)
    private String jurisdictionsRegistered;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private InternationalFilingSystem internationalFilingSystem;

    @Column(length = 500)
    private String regionalDesignations;

    @Column(length = 4000)
    private String goodsAndServices;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UsageStatus usageStatus;

    @Column
    private LocalDate firstUseDate;

    @Column
    private LocalDate firstUseInCommerceDate;

    @Column(length = 2000)
    private String specimen;

    @Column(length = 2000)
    private String limitations;

    @Column(length = 2000)
    private String coexistenceAgreements;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private LicensingStatus licensingStatus;

    @Column(length = 1000)
    private String licensees;

    @DecimalMin(value = "0.0", message = "Royalty income must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal royaltyIncome;

    @DecimalMin(value = "0.0", message = "Valuation must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal valuation;

    @DecimalMin(value = "0.0", message = "Maintenance costs must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal maintenanceCosts;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CommercialImportance commercialImportance;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private DistinctivenessScore distinctivenessScore;

    @DecimalMin(value = "0.0", message = "Litigation risk score must be >= 0")
    @DecimalMax(value = "100.0", message = "Litigation risk score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal litigationRiskScore;

    @DecimalMin(value = "0.0", message = "Infringement risk score must be >= 0")
    @DecimalMax(value = "100.0", message = "Infringement risk score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal infringementRiskScore;

    @DecimalMin(value = "0.0", message = "Market relevance score must be >= 0")
    @DecimalMax(value = "100.0", message = "Market relevance score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal marketRelevanceScore;

    @DecimalMin(value = "0.0", message = "Renewal risk score must be >= 0")
    @DecimalMax(value = "100.0", message = "Renewal risk score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal renewalRiskScore;

    @DecimalMin(value = "0.0", message = "Overall assessment score must be >= 0")
    @DecimalMax(value = "100.0", message = "Overall assessment score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal overallAssessmentScore;

    @Column(length = 2000)
    private String complianceFlags;

    @Column(length = 2000)
    private String officialDocuments;

    @Column(length = 2000)
    private String representations;

    @Column(length = 2000)
    private String searchReports;

    @Column(length = 2000)
    private String legalOpinions;

    @Column(length = 4000)
    private String internalNotes;

    @NotBlank(message = "Data source is required")
    @Size(max = 100, message = "Data source must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String dataSource;

    @Size(max = 100, message = "Responsible agent must be at most 100 characters")
    @Column(length = 100)
    private String responsibleAgent;

    @Column(length = 4000)
    private String notes;

    @AssertTrue(message = "Priority number and date are required when priority is claimed")
    public boolean isPriorityClaimValid() {
        if (!priorityClaimed) {
            return true;
        }
        return priorityNumber != null && !priorityNumber.isBlank() && priorityDate != null;
    }

    @AssertTrue(message = "Priority date must be on or before application date")
    public boolean isPriorityDateValid() {
        return priorityDate == null || applicationDate == null || !priorityDate.isAfter(applicationDate);
    }

    @AssertTrue(message = "Registration date must be on or after application date")
    public boolean isRegistrationDateValid() {
        return registrationDate == null || applicationDate == null || !registrationDate.isBefore(applicationDate);
    }

    @AssertTrue(message = "Expiration date must be on or after registration date")
    public boolean isExpirationDateValid() {
        return expirationDate == null || registrationDate == null || !expirationDate.isBefore(registrationDate);
    }

    @AssertTrue(message = "First use in commerce date must be on or after first use date")
    public boolean isFirstUseInCommerceDateValid() {
        return firstUseDate == null || firstUseInCommerceDate == null || !firstUseInCommerceDate.isBefore(firstUseDate);
    }

    public String getMarkName() { return markName; }
    public void setMarkName(String markName) { this.markName = markName; }
    public MarkType getMarkType() { return markType; }
    public void setMarkType(MarkType markType) { this.markType = markType; }
    public String getMarkDescription() { return markDescription; }
    public void setMarkDescription(String markDescription) { this.markDescription = markDescription; }
    public String getNiceClasses() { return niceClasses; }
    public void setNiceClasses(String niceClasses) { this.niceClasses = niceClasses; }
    public String getViennaCodes() { return viennaCodes; }
    public void setViennaCodes(String viennaCodes) { this.viennaCodes = viennaCodes; }
    public String getIndustrySector() { return industrySector; }
    public void setIndustrySector(String industrySector) { this.industrySector = industrySector; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public OwnerType getOwnerType() { return ownerType; }
    public void setOwnerType(OwnerType ownerType) { this.ownerType = ownerType; }
    public String getOwnerAddress() { return ownerAddress; }
    public void setOwnerAddress(String ownerAddress) { this.ownerAddress = ownerAddress; }
    public String getRepresentative() { return representative; }
    public void setRepresentative(String representative) { this.representative = representative; }
    public String getAssignmentHistory() { return assignmentHistory; }
    public void setAssignmentHistory(String assignmentHistory) { this.assignmentHistory = assignmentHistory; }
    public String getCoOwners() { return coOwners; }
    public void setCoOwners(String coOwners) { this.coOwners = coOwners; }
    public String getApplicationNumber() { return applicationNumber; }
    public void setApplicationNumber(String applicationNumber) { this.applicationNumber = applicationNumber; }
    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }
    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    public boolean isPriorityClaimed() { return priorityClaimed; }
    public void setPriorityClaimed(boolean priorityClaimed) { this.priorityClaimed = priorityClaimed; }
    public String getPriorityNumber() { return priorityNumber; }
    public void setPriorityNumber(String priorityNumber) { this.priorityNumber = priorityNumber; }
    public LocalDate getPriorityDate() { return priorityDate; }
    public void setPriorityDate(LocalDate priorityDate) { this.priorityDate = priorityDate; }
    public LegalStatus getLegalStatus() { return legalStatus; }
    public void setLegalStatus(LegalStatus legalStatus) { this.legalStatus = legalStatus; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    public String getRenewalHistory() { return renewalHistory; }
    public void setRenewalHistory(String renewalHistory) { this.renewalHistory = renewalHistory; }
    public String getOppositions() { return oppositions; }
    public void setOppositions(String oppositions) { this.oppositions = oppositions; }
    public String getCancellationActions() { return cancellationActions; }
    public void setCancellationActions(String cancellationActions) { this.cancellationActions = cancellationActions; }
    public String getJurisdictionsFiled() { return jurisdictionsFiled; }
    public void setJurisdictionsFiled(String jurisdictionsFiled) { this.jurisdictionsFiled = jurisdictionsFiled; }
    public String getJurisdictionsRegistered() { return jurisdictionsRegistered; }
    public void setJurisdictionsRegistered(String jurisdictionsRegistered) { this.jurisdictionsRegistered = jurisdictionsRegistered; }
    public InternationalFilingSystem getInternationalFilingSystem() { return internationalFilingSystem; }
    public void setInternationalFilingSystem(InternationalFilingSystem internationalFilingSystem) { this.internationalFilingSystem = internationalFilingSystem; }
    public String getRegionalDesignations() { return regionalDesignations; }
    public void setRegionalDesignations(String regionalDesignations) { this.regionalDesignations = regionalDesignations; }
    public String getGoodsAndServices() { return goodsAndServices; }
    public void setGoodsAndServices(String goodsAndServices) { this.goodsAndServices = goodsAndServices; }
    public UsageStatus getUsageStatus() { return usageStatus; }
    public void setUsageStatus(UsageStatus usageStatus) { this.usageStatus = usageStatus; }
    public LocalDate getFirstUseDate() { return firstUseDate; }
    public void setFirstUseDate(LocalDate firstUseDate) { this.firstUseDate = firstUseDate; }
    public LocalDate getFirstUseInCommerceDate() { return firstUseInCommerceDate; }
    public void setFirstUseInCommerceDate(LocalDate firstUseInCommerceDate) { this.firstUseInCommerceDate = firstUseInCommerceDate; }
    public String getSpecimen() { return specimen; }
    public void setSpecimen(String specimen) { this.specimen = specimen; }
    public String getLimitations() { return limitations; }
    public void setLimitations(String limitations) { this.limitations = limitations; }
    public String getCoexistenceAgreements() { return coexistenceAgreements; }
    public void setCoexistenceAgreements(String coexistenceAgreements) { this.coexistenceAgreements = coexistenceAgreements; }
    public LicensingStatus getLicensingStatus() { return licensingStatus; }
    public void setLicensingStatus(LicensingStatus licensingStatus) { this.licensingStatus = licensingStatus; }
    public String getLicensees() { return licensees; }
    public void setLicensees(String licensees) { this.licensees = licensees; }
    public BigDecimal getRoyaltyIncome() { return royaltyIncome; }
    public void setRoyaltyIncome(BigDecimal royaltyIncome) { this.royaltyIncome = royaltyIncome; }
    public BigDecimal getValuation() { return valuation; }
    public void setValuation(BigDecimal valuation) { this.valuation = valuation; }
    public BigDecimal getMaintenanceCosts() { return maintenanceCosts; }
    public void setMaintenanceCosts(BigDecimal maintenanceCosts) { this.maintenanceCosts = maintenanceCosts; }
    public CommercialImportance getCommercialImportance() { return commercialImportance; }
    public void setCommercialImportance(CommercialImportance commercialImportance) { this.commercialImportance = commercialImportance; }
    public DistinctivenessScore getDistinctivenessScore() { return distinctivenessScore; }
    public void setDistinctivenessScore(DistinctivenessScore distinctivenessScore) { this.distinctivenessScore = distinctivenessScore; }
    public BigDecimal getLitigationRiskScore() { return litigationRiskScore; }
    public void setLitigationRiskScore(BigDecimal litigationRiskScore) { this.litigationRiskScore = litigationRiskScore; }
    public BigDecimal getInfringementRiskScore() { return infringementRiskScore; }
    public void setInfringementRiskScore(BigDecimal infringementRiskScore) { this.infringementRiskScore = infringementRiskScore; }
    public BigDecimal getMarketRelevanceScore() { return marketRelevanceScore; }
    public void setMarketRelevanceScore(BigDecimal marketRelevanceScore) { this.marketRelevanceScore = marketRelevanceScore; }
    public BigDecimal getRenewalRiskScore() { return renewalRiskScore; }
    public void setRenewalRiskScore(BigDecimal renewalRiskScore) { this.renewalRiskScore = renewalRiskScore; }
    public BigDecimal getOverallAssessmentScore() { return overallAssessmentScore; }
    public void setOverallAssessmentScore(BigDecimal overallAssessmentScore) { this.overallAssessmentScore = overallAssessmentScore; }
    public String getComplianceFlags() { return complianceFlags; }
    public void setComplianceFlags(String complianceFlags) { this.complianceFlags = complianceFlags; }
    public String getOfficialDocuments() { return officialDocuments; }
    public void setOfficialDocuments(String officialDocuments) { this.officialDocuments = officialDocuments; }
    public String getRepresentations() { return representations; }
    public void setRepresentations(String representations) { this.representations = representations; }
    public String getSearchReports() { return searchReports; }
    public void setSearchReports(String searchReports) { this.searchReports = searchReports; }
    public String getLegalOpinions() { return legalOpinions; }
    public void setLegalOpinions(String legalOpinions) { this.legalOpinions = legalOpinions; }
    public String getInternalNotes() { return internalNotes; }
    public void setInternalNotes(String internalNotes) { this.internalNotes = internalNotes; }
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
    public String getResponsibleAgent() { return responsibleAgent; }
    public void setResponsibleAgent(String responsibleAgent) { this.responsibleAgent = responsibleAgent; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
