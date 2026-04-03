package com.sdr.ams.model.intangible;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "patent_id", nullable = false, length = 100))
@Table(
    name = "patent",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_patent_id", columnNames = "patent_id"),
        @UniqueConstraint(name = "uk_patent_application_number", columnNames = "application_number"),
        @UniqueConstraint(name = "uk_patent_publication_number", columnNames = "publication_number"),
        @UniqueConstraint(name = "uk_patent_grant_number", columnNames = "grant_number")
    }
)
public class Patent extends CoreEntity {

    public enum PatentType { UTILITY, DESIGN, PLANT, MODEL, OTHER }
    public enum LegalStatus { PENDING, GRANTED, REJECTED, EXPIRED, WITHDRAWN }
    public enum RegionalSystem { NONE, EPO, WIPO_PCT, ARIPO, OTHER }
    public enum InfringementRisk { LOW, MEDIUM, HIGH, CRITICAL }
    public enum BlockingPotential { LOW, MEDIUM, HIGH, STRATEGIC }

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

    @NotBlank(message = "Patent ID is required")
    @Size(max = 100, message = "Patent ID must be at most 100 characters")
    public String getPatentId() {
        return getName();
    }

    public void setPatentId(String patentId) {
        setName(patentId);
    }

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "abstract_summary", length = 4000)
    private String abstractSummary;

    @NotNull(message = "Patent type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PatentType patentType;

    @Size(max = 150, message = "Technology field must be at most 150 characters")
    @Column(length = 150)
    private String technologyField;

    @Column(length = 500)
    private String ipcCpcCodes;

    @Column(length = 500)
    private String keywordsTags;

    @Column(length = 1000)
    private String inventors;

    @NotBlank(message = "Assignee/owner is required")
    @Size(max = 255, message = "Assignee/owner must be at most 255 characters")
    @Column(nullable = false, length = 255)
    private String assigneeOwner;

    @DecimalMin(value = "0.0", message = "Ownership percentage must be >= 0")
    @DecimalMax(value = "100.0", message = "Ownership percentage must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal ownershipPercentage;

    @Column(length = 2000)
    private String assignmentHistory;

    @Column(length = 100)
    private String countryOfOrigin;

    @NotBlank(message = "Application number is required")
    @Size(max = 100, message = "Application number must be at most 100 characters")
    @Column(nullable = false, length = 100, name = "application_number")
    private String applicationNumber;

    @NotNull(message = "Application date is required")
    @Column(nullable = false)
    private LocalDate applicationDate;

    @Size(max = 100, message = "Publication number must be at most 100 characters")
    @Column(length = 100, name = "publication_number")
    private String publicationNumber;

    @Column
    private LocalDate publicationDate;

    @Size(max = 100, message = "Grant number must be at most 100 characters")
    @Column(length = 100, name = "grant_number")
    private String grantNumber;

    @Column
    private LocalDate grantDate;

    @Column(length = 500)
    private String priorityNumbers;

    @Column(length = 500)
    private String priorityDates;

    @Column(length = 1000)
    private String jurisdictions;

    @Column(length = 100)
    private String patentFamilyId;

    @Column(length = 4000)
    private String claimsText;

    @Column(length = 8000)
    private String descriptionSpecification;

    @Column(length = 2000)
    private String drawingsReferences;

    @Column(length = 4000)
    private String scopeOfProtection;

    @Column(length = 2000)
    private String limitationsDisclaimers;

    @NotNull(message = "Legal status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private LegalStatus legalStatus;

    @Column(length = 1000)
    private String countriesFiled;

    @Column(length = 1000)
    private String countriesGranted;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RegionalSystem regionalSystem;

    @Column(length = 500)
    private String nationalPhaseEntries;

    @DecimalMin(value = "0.0", message = "Filing fees must be >= 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal filingFees;

    @DecimalMin(value = "0.0", message = "Maintenance/renewal fees must be >= 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal maintenanceRenewalFees;

    @DecimalMin(value = "0.0", message = "Licensing revenue must be >= 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal licensingRevenue;

    @DecimalMin(value = "0.0", message = "Royalty rates must be >= 0")
    @DecimalMax(value = "100.0", message = "Royalty rates must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal royaltyRates;

    @DecimalMin(value = "0.0", message = "Valuation must be >= 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal valuation;

    @DecimalMin(value = "0.0", message = "Cost of prosecution must be >= 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal costOfProsecution;

    @Column(length = 1000)
    private String renewalDates;

    @Column(length = 2000)
    private String maintenanceFeeSchedule;

    @Column(length = 2000)
    private String legalDeadlines;

    @Column(length = 4000)
    private String oppositionLitigationHistory;

    @Column
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private InfringementRisk infringementRisk;

    @Column(length = 2000)
    private String ftoConcerns;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private BlockingPotential blockingPotential;

    @DecimalMin(value = "0.0", message = "Technological relevance score must be >= 0")
    @DecimalMax(value = "100.0", message = "Technological relevance score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal technologicalRelevanceScore;

    @DecimalMin(value = "0.0", message = "Market relevance score must be >= 0")
    @DecimalMax(value = "100.0", message = "Market relevance score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal marketRelevanceScore;

    @DecimalMin(value = "0.0", message = "Patent strength score must be >= 0")
    @DecimalMax(value = "100.0", message = "Patent strength score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal patentStrengthScore;

    @Min(value = 0, message = "Citations received must be >= 0")
    private Integer citationsReceived;

    @Min(value = 0, message = "Citations made must be >= 0")
    private Integer citationsMade;

    @Column(length = 2000)
    private String officialDocuments;

    @Column(length = 2000)
    private String supportingDocuments;

    @NotBlank(message = "Data source is required")
    @Size(max = 100, message = "Data source must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String dataSource;

    @Column(length = 100)
    private String responsibleAnalyst;

    @Column(length = 4000)
    private String notesComments;

    @AssertTrue(message = "Publication date must be on or after application date")
    public boolean isPublicationDateValid() {
        return publicationDate == null || applicationDate == null || !publicationDate.isBefore(applicationDate);
    }

    @AssertTrue(message = "Grant date must be on or after application date")
    public boolean isGrantDateValid() {
        return grantDate == null || applicationDate == null || !grantDate.isBefore(applicationDate);
    }

    @AssertTrue(message = "Expiry date must be on or after grant date")
    public boolean isExpiryDateValid() {
        return expiryDate == null || grantDate == null || !expiryDate.isBefore(grantDate);
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAbstractSummary() { return abstractSummary; }
    public void setAbstractSummary(String abstractSummary) { this.abstractSummary = abstractSummary; }
    public PatentType getPatentType() { return patentType; }
    public void setPatentType(PatentType patentType) { this.patentType = patentType; }
    public String getTechnologyField() { return technologyField; }
    public void setTechnologyField(String technologyField) { this.technologyField = technologyField; }
    public String getIpcCpcCodes() { return ipcCpcCodes; }
    public void setIpcCpcCodes(String ipcCpcCodes) { this.ipcCpcCodes = ipcCpcCodes; }
    public String getKeywordsTags() { return keywordsTags; }
    public void setKeywordsTags(String keywordsTags) { this.keywordsTags = keywordsTags; }
    public String getInventors() { return inventors; }
    public void setInventors(String inventors) { this.inventors = inventors; }
    public String getAssigneeOwner() { return assigneeOwner; }
    public void setAssigneeOwner(String assigneeOwner) { this.assigneeOwner = assigneeOwner; }
    public BigDecimal getOwnershipPercentage() { return ownershipPercentage; }
    public void setOwnershipPercentage(BigDecimal ownershipPercentage) { this.ownershipPercentage = ownershipPercentage; }
    public String getAssignmentHistory() { return assignmentHistory; }
    public void setAssignmentHistory(String assignmentHistory) { this.assignmentHistory = assignmentHistory; }
    public String getCountryOfOrigin() { return countryOfOrigin; }
    public void setCountryOfOrigin(String countryOfOrigin) { this.countryOfOrigin = countryOfOrigin; }
    public String getApplicationNumber() { return applicationNumber; }
    public void setApplicationNumber(String applicationNumber) { this.applicationNumber = applicationNumber; }
    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }
    public String getPublicationNumber() { return publicationNumber; }
    public void setPublicationNumber(String publicationNumber) { this.publicationNumber = publicationNumber; }
    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }
    public String getGrantNumber() { return grantNumber; }
    public void setGrantNumber(String grantNumber) { this.grantNumber = grantNumber; }
    public LocalDate getGrantDate() { return grantDate; }
    public void setGrantDate(LocalDate grantDate) { this.grantDate = grantDate; }
    public String getPriorityNumbers() { return priorityNumbers; }
    public void setPriorityNumbers(String priorityNumbers) { this.priorityNumbers = priorityNumbers; }
    public String getPriorityDates() { return priorityDates; }
    public void setPriorityDates(String priorityDates) { this.priorityDates = priorityDates; }
    public String getJurisdictions() { return jurisdictions; }
    public void setJurisdictions(String jurisdictions) { this.jurisdictions = jurisdictions; }
    public String getPatentFamilyId() { return patentFamilyId; }
    public void setPatentFamilyId(String patentFamilyId) { this.patentFamilyId = patentFamilyId; }
    public String getClaimsText() { return claimsText; }
    public void setClaimsText(String claimsText) { this.claimsText = claimsText; }
    public String getDescriptionSpecification() { return descriptionSpecification; }
    public void setDescriptionSpecification(String descriptionSpecification) { this.descriptionSpecification = descriptionSpecification; }
    public String getDrawingsReferences() { return drawingsReferences; }
    public void setDrawingsReferences(String drawingsReferences) { this.drawingsReferences = drawingsReferences; }
    public String getScopeOfProtection() { return scopeOfProtection; }
    public void setScopeOfProtection(String scopeOfProtection) { this.scopeOfProtection = scopeOfProtection; }
    public String getLimitationsDisclaimers() { return limitationsDisclaimers; }
    public void setLimitationsDisclaimers(String limitationsDisclaimers) { this.limitationsDisclaimers = limitationsDisclaimers; }
    public LegalStatus getLegalStatus() { return legalStatus; }
    public void setLegalStatus(LegalStatus legalStatus) { this.legalStatus = legalStatus; }
    public String getCountriesFiled() { return countriesFiled; }
    public void setCountriesFiled(String countriesFiled) { this.countriesFiled = countriesFiled; }
    public String getCountriesGranted() { return countriesGranted; }
    public void setCountriesGranted(String countriesGranted) { this.countriesGranted = countriesGranted; }
    public RegionalSystem getRegionalSystem() { return regionalSystem; }
    public void setRegionalSystem(RegionalSystem regionalSystem) { this.regionalSystem = regionalSystem; }
    public String getNationalPhaseEntries() { return nationalPhaseEntries; }
    public void setNationalPhaseEntries(String nationalPhaseEntries) { this.nationalPhaseEntries = nationalPhaseEntries; }
    public BigDecimal getFilingFees() { return filingFees; }
    public void setFilingFees(BigDecimal filingFees) { this.filingFees = filingFees; }
    public BigDecimal getMaintenanceRenewalFees() { return maintenanceRenewalFees; }
    public void setMaintenanceRenewalFees(BigDecimal maintenanceRenewalFees) { this.maintenanceRenewalFees = maintenanceRenewalFees; }
    public BigDecimal getLicensingRevenue() { return licensingRevenue; }
    public void setLicensingRevenue(BigDecimal licensingRevenue) { this.licensingRevenue = licensingRevenue; }
    public BigDecimal getRoyaltyRates() { return royaltyRates; }
    public void setRoyaltyRates(BigDecimal royaltyRates) { this.royaltyRates = royaltyRates; }
    public BigDecimal getValuation() { return valuation; }
    public void setValuation(BigDecimal valuation) { this.valuation = valuation; }
    public BigDecimal getCostOfProsecution() { return costOfProsecution; }
    public void setCostOfProsecution(BigDecimal costOfProsecution) { this.costOfProsecution = costOfProsecution; }
    public String getRenewalDates() { return renewalDates; }
    public void setRenewalDates(String renewalDates) { this.renewalDates = renewalDates; }
    public String getMaintenanceFeeSchedule() { return maintenanceFeeSchedule; }
    public void setMaintenanceFeeSchedule(String maintenanceFeeSchedule) { this.maintenanceFeeSchedule = maintenanceFeeSchedule; }
    public String getLegalDeadlines() { return legalDeadlines; }
    public void setLegalDeadlines(String legalDeadlines) { this.legalDeadlines = legalDeadlines; }
    public String getOppositionLitigationHistory() { return oppositionLitigationHistory; }
    public void setOppositionLitigationHistory(String oppositionLitigationHistory) { this.oppositionLitigationHistory = oppositionLitigationHistory; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public InfringementRisk getInfringementRisk() { return infringementRisk; }
    public void setInfringementRisk(InfringementRisk infringementRisk) { this.infringementRisk = infringementRisk; }
    public String getFtoConcerns() { return ftoConcerns; }
    public void setFtoConcerns(String ftoConcerns) { this.ftoConcerns = ftoConcerns; }
    public BlockingPotential getBlockingPotential() { return blockingPotential; }
    public void setBlockingPotential(BlockingPotential blockingPotential) { this.blockingPotential = blockingPotential; }
    public BigDecimal getTechnologicalRelevanceScore() { return technologicalRelevanceScore; }
    public void setTechnologicalRelevanceScore(BigDecimal technologicalRelevanceScore) { this.technologicalRelevanceScore = technologicalRelevanceScore; }
    public BigDecimal getMarketRelevanceScore() { return marketRelevanceScore; }
    public void setMarketRelevanceScore(BigDecimal marketRelevanceScore) { this.marketRelevanceScore = marketRelevanceScore; }
    public BigDecimal getPatentStrengthScore() { return patentStrengthScore; }
    public void setPatentStrengthScore(BigDecimal patentStrengthScore) { this.patentStrengthScore = patentStrengthScore; }
    public Integer getCitationsReceived() { return citationsReceived; }
    public void setCitationsReceived(Integer citationsReceived) { this.citationsReceived = citationsReceived; }
    public Integer getCitationsMade() { return citationsMade; }
    public void setCitationsMade(Integer citationsMade) { this.citationsMade = citationsMade; }
    public String getOfficialDocuments() { return officialDocuments; }
    public void setOfficialDocuments(String officialDocuments) { this.officialDocuments = officialDocuments; }
    public String getSupportingDocuments() { return supportingDocuments; }
    public void setSupportingDocuments(String supportingDocuments) { this.supportingDocuments = supportingDocuments; }
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
    public String getResponsibleAnalyst() { return responsibleAnalyst; }
    public void setResponsibleAnalyst(String responsibleAnalyst) { this.responsibleAnalyst = responsibleAnalyst; }
    public String getNotesComments() { return notesComments; }
    public void setNotesComments(String notesComments) { this.notesComments = notesComments; }
}
