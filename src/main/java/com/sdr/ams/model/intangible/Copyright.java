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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "copyright_id", nullable = false, length = 100))
@Table(
	name = "copyright",
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_copyright_copyright_id", columnNames = "copyright_id"),
		@UniqueConstraint(name = "uk_copyright_registration_number", columnNames = "registration_number")
	}
)
public class Copyright extends CoreEntity {

	public enum WorkType { TEXT, IMAGE, SOFTWARE, MUSIC, VIDEO, DESIGN, DATABASE, OTHER }
	public enum CopyrightStatus { ACTIVE, EXPIRED, PUBLIC_DOMAIN, PENDING }
	public enum ExclusivityType { EXCLUSIVE, NON_EXCLUSIVE }
	public enum LicenseType { EXCLUSIVE, NON_EXCLUSIVE, OPEN_SOURCE, CREATIVE_COMMONS, CUSTOM_CONTRACT }
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

	@NotBlank(message = "Copyright ID is required")
	@Size(max = 100, message = "Copyright ID must be at most 100 characters")
	@Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Copyright ID must be alphanumeric")
	public String getCopyrightId() {
		return getName();
	}

	public void setCopyrightId(String copyrightId) {
		setName(copyrightId);
	}

	// Core work identification
	@NotBlank(message = "Title is required")
	@Size(max = 255, message = "Title must be at most 255 characters")
	@Column(nullable = false, length = 255)
	private String title;

	@NotNull(message = "Type of work is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private WorkType workType;

	@Size(max = 4000, message = "Description must be at most 4000 characters")
	@Column(length = 4000)
	private String descriptionAbstract;

	@Size(max = 100, message = "Version or edition must be at most 100 characters")
	@Column(length = 100)
	private String versionEdition;

	@Column
	private LocalDate creationDate;

	@Column
	private LocalDate publicationReleaseDate;

	// Ownership and authorship
	@NotBlank(message = "Author(s) is required")
	@Size(max = 1000, message = "Author(s) must be at most 1000 characters")
	@Column(nullable = false, length = 1000)
	private String authors;

	@NotBlank(message = "Copyright owner(s) is required")
	@Size(max = 1000, message = "Copyright owner(s) must be at most 1000 characters")
	@Column(nullable = false, length = 1000)
	private String copyrightOwners;

	@DecimalMin(value = "0.0", message = "Ownership percentage must be greater than or equal to 0")
	@DecimalMax(value = "100.0", message = "Ownership percentage must be less than or equal to 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal ownershipPercentage;

	@Size(max = 255, message = "Moral rights holder must be at most 255 characters")
	@Column(length = 255)
	private String moralRightsHolder;

	@NotBlank(message = "Country of origin is required")
	@Size(max = 100, message = "Country of origin must be at most 100 characters")
	@Column(nullable = false, length = 100)
	private String countryOfOrigin;

	// Legal metadata
	@Size(max = 255, message = "Copyright notice must be at most 255 characters")
	@Column(length = 255)
	private String copyrightNotice;

	@Size(max = 100, message = "Registration number must be at most 100 characters")
	@Column(length = 100, unique = true)
	private String registrationNumber;

	@Size(max = 100, message = "Registration authority must be at most 100 characters")
	@Column(length = 100)
	private String registrationAuthority;

	@Column
	private LocalDate registrationDate;

	@NotNull(message = "Copyright status is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private CopyrightStatus copyrightStatus;

	@Column
	private LocalDate protectionStartDate;

	@Column
	private LocalDate protectionEndDate;

	@Size(max = 500, message = "Jurisdictions covered must be at most 500 characters")
	@Column(length = 500)
	private String jurisdictionsCovered;

	// Rights and restrictions
	@Size(max = 2000, message = "Rights granted must be at most 2000 characters")
	@Column(length = 2000)
	private String rightsGranted;

	@Size(max = 2000, message = "Rights reserved must be at most 2000 characters")
	@Column(length = 2000)
	private String rightsReserved;

	@Size(max = 2000, message = "Usage restrictions must be at most 2000 characters")
	@Column(length = 2000)
	private String usageRestrictions;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private ExclusivityType exclusivityType;

	@Column
	private Boolean derivativeWorksAllowed;

	@Size(max = 1000, message = "DRM/technical protection measures must be at most 1000 characters")
	@Column(length = 1000)
	private String drmProtectionMeasures;

	// Licensing and agreements
	@Enumerated(EnumType.STRING)
	@Column(length = 40)
	private LicenseType licenseType;

	@Size(max = 1000, message = "Licensee(s) must be at most 1000 characters")
	@Column(length = 1000)
	private String licensees;

	@Column
	private LocalDate licenseStartDate;

	@Column
	private LocalDate licenseEndDate;

	@Size(max = 2000, message = "Permitted uses must be at most 2000 characters")
	@Column(length = 2000)
	private String permittedUses;

	@Size(max = 2000, message = "Royalty terms must be at most 2000 characters")
	@Column(length = 2000)
	private String royaltyTerms;

	@Size(max = 1000, message = "Contract references must be at most 1000 characters")
	@Column(length = 1000)
	private String contractReferences;

	// Financial and commercial
	@DecimalMin(value = "0.0", message = "Royalty rate must be greater than or equal to 0")
	@DecimalMax(value = "100.0", message = "Royalty rate must be less than or equal to 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal royaltyRate;

	@DecimalMin(value = "0.0", message = "Revenue generated must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal revenueGenerated;

	@DecimalMin(value = "0.0", message = "Advance payments must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal advancePayments;

	@DecimalMin(value = "0.0", message = "Licensing fees must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal licensingFees;

	@DecimalMin(value = "0.0", message = "Valuation must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal valuation;

	// Assessment and risk indicators
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RiskLevel infringementRisk;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RiskLevel ownershipDisputesRisk;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RiskLevel expirationRisk;

	@Size(max = 1000, message = "Compliance flags must be at most 1000 characters")
	@Column(length = 1000)
	private String complianceFlags;

	@DecimalMin(value = "0.0", message = "Market relevance score must be greater than or equal to 0")
	@DecimalMax(value = "100.0", message = "Market relevance score must be less than or equal to 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal marketRelevanceScore;

	@DecimalMin(value = "0.0", message = "Portfolio impact score must be greater than or equal to 0")
	@DecimalMax(value = "100.0", message = "Portfolio impact score must be less than or equal to 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal portfolioImpactScore;

	// Operational metadata
	@NotBlank(message = "Source of information is required")
	@Size(max = 100, message = "Source of information must be at most 100 characters")
	@Column(nullable = false, length = 100)
	private String sourceOfInformation;

	@Size(max = 100, message = "Responsible reviewer must be at most 100 characters")
	@Column(length = 100)
	private String responsibleReviewer;

	@Size(max = 2000, message = "Document references must be at most 2000 characters")
	@Column(length = 2000)
	private String documentReferences;

	@Size(max = 500, message = "Tags/categories must be at most 500 characters")
	@Column(length = 500)
	private String tagsCategories;

	@Size(max = 2000, message = "Notes must be at most 2000 characters")
	@Column(length = 2000)
	private String notes;

	@AssertTrue(message = "Creation date must be today or earlier")
	public boolean isCreationDateValid() {
		return creationDate == null || !creationDate.isAfter(LocalDate.now());
	}

	@AssertTrue(message = "Publication/release date must be today or earlier")
	public boolean isPublicationReleaseDateValid() {
		return publicationReleaseDate == null || !publicationReleaseDate.isAfter(LocalDate.now());
	}

	@AssertTrue(message = "Publication/release date must be greater than or equal to creation date")
	public boolean isPublicationAfterCreationValid() {
		return creationDate == null || publicationReleaseDate == null || !publicationReleaseDate.isBefore(creationDate);
	}

	@AssertTrue(message = "Registration date must be today or earlier")
	public boolean isRegistrationDateValid() {
		return registrationDate == null || !registrationDate.isAfter(LocalDate.now());
	}

	@AssertTrue(message = "Protection end date must be greater than or equal to protection start date")
	public boolean isProtectionTermValid() {
		return protectionStartDate == null || protectionEndDate == null || !protectionEndDate.isBefore(protectionStartDate);
	}

	@AssertTrue(message = "License end date must be greater than or equal to license start date")
	public boolean isLicenseTermValid() {
		return licenseStartDate == null || licenseEndDate == null || !licenseEndDate.isBefore(licenseStartDate);
	}

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public WorkType getWorkType() { return workType; }
	public void setWorkType(WorkType workType) { this.workType = workType; }
	public String getDescriptionAbstract() { return descriptionAbstract; }
	public void setDescriptionAbstract(String descriptionAbstract) { this.descriptionAbstract = descriptionAbstract; }
	public String getVersionEdition() { return versionEdition; }
	public void setVersionEdition(String versionEdition) { this.versionEdition = versionEdition; }
	public LocalDate getCreationDate() { return creationDate; }
	public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }
	public LocalDate getPublicationReleaseDate() { return publicationReleaseDate; }
	public void setPublicationReleaseDate(LocalDate publicationReleaseDate) { this.publicationReleaseDate = publicationReleaseDate; }
	public String getAuthors() { return authors; }
	public void setAuthors(String authors) { this.authors = authors; }
	public String getCopyrightOwners() { return copyrightOwners; }
	public void setCopyrightOwners(String copyrightOwners) { this.copyrightOwners = copyrightOwners; }
	public BigDecimal getOwnershipPercentage() { return ownershipPercentage; }
	public void setOwnershipPercentage(BigDecimal ownershipPercentage) { this.ownershipPercentage = ownershipPercentage; }
	public String getMoralRightsHolder() { return moralRightsHolder; }
	public void setMoralRightsHolder(String moralRightsHolder) { this.moralRightsHolder = moralRightsHolder; }
	public String getCountryOfOrigin() { return countryOfOrigin; }
	public void setCountryOfOrigin(String countryOfOrigin) { this.countryOfOrigin = countryOfOrigin; }
	public String getCopyrightNotice() { return copyrightNotice; }
	public void setCopyrightNotice(String copyrightNotice) { this.copyrightNotice = copyrightNotice; }
	public String getRegistrationNumber() { return registrationNumber; }
	public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
	public String getRegistrationAuthority() { return registrationAuthority; }
	public void setRegistrationAuthority(String registrationAuthority) { this.registrationAuthority = registrationAuthority; }
	public LocalDate getRegistrationDate() { return registrationDate; }
	public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
	public CopyrightStatus getCopyrightStatus() { return copyrightStatus; }
	public void setCopyrightStatus(CopyrightStatus copyrightStatus) { this.copyrightStatus = copyrightStatus; }
	public LocalDate getProtectionStartDate() { return protectionStartDate; }
	public void setProtectionStartDate(LocalDate protectionStartDate) { this.protectionStartDate = protectionStartDate; }
	public LocalDate getProtectionEndDate() { return protectionEndDate; }
	public void setProtectionEndDate(LocalDate protectionEndDate) { this.protectionEndDate = protectionEndDate; }
	public String getJurisdictionsCovered() { return jurisdictionsCovered; }
	public void setJurisdictionsCovered(String jurisdictionsCovered) { this.jurisdictionsCovered = jurisdictionsCovered; }
	public String getRightsGranted() { return rightsGranted; }
	public void setRightsGranted(String rightsGranted) { this.rightsGranted = rightsGranted; }
	public String getRightsReserved() { return rightsReserved; }
	public void setRightsReserved(String rightsReserved) { this.rightsReserved = rightsReserved; }
	public String getUsageRestrictions() { return usageRestrictions; }
	public void setUsageRestrictions(String usageRestrictions) { this.usageRestrictions = usageRestrictions; }
	public ExclusivityType getExclusivityType() { return exclusivityType; }
	public void setExclusivityType(ExclusivityType exclusivityType) { this.exclusivityType = exclusivityType; }
	public Boolean getDerivativeWorksAllowed() { return derivativeWorksAllowed; }
	public void setDerivativeWorksAllowed(Boolean derivativeWorksAllowed) { this.derivativeWorksAllowed = derivativeWorksAllowed; }
	public String getDrmProtectionMeasures() { return drmProtectionMeasures; }
	public void setDrmProtectionMeasures(String drmProtectionMeasures) { this.drmProtectionMeasures = drmProtectionMeasures; }
	public LicenseType getLicenseType() { return licenseType; }
	public void setLicenseType(LicenseType licenseType) { this.licenseType = licenseType; }
	public String getLicensees() { return licensees; }
	public void setLicensees(String licensees) { this.licensees = licensees; }
	public LocalDate getLicenseStartDate() { return licenseStartDate; }
	public void setLicenseStartDate(LocalDate licenseStartDate) { this.licenseStartDate = licenseStartDate; }
	public LocalDate getLicenseEndDate() { return licenseEndDate; }
	public void setLicenseEndDate(LocalDate licenseEndDate) { this.licenseEndDate = licenseEndDate; }
	public String getPermittedUses() { return permittedUses; }
	public void setPermittedUses(String permittedUses) { this.permittedUses = permittedUses; }
	public String getRoyaltyTerms() { return royaltyTerms; }
	public void setRoyaltyTerms(String royaltyTerms) { this.royaltyTerms = royaltyTerms; }
	public String getContractReferences() { return contractReferences; }
	public void setContractReferences(String contractReferences) { this.contractReferences = contractReferences; }
	public BigDecimal getRoyaltyRate() { return royaltyRate; }
	public void setRoyaltyRate(BigDecimal royaltyRate) { this.royaltyRate = royaltyRate; }
	public BigDecimal getRevenueGenerated() { return revenueGenerated; }
	public void setRevenueGenerated(BigDecimal revenueGenerated) { this.revenueGenerated = revenueGenerated; }
	public BigDecimal getAdvancePayments() { return advancePayments; }
	public void setAdvancePayments(BigDecimal advancePayments) { this.advancePayments = advancePayments; }
	public BigDecimal getLicensingFees() { return licensingFees; }
	public void setLicensingFees(BigDecimal licensingFees) { this.licensingFees = licensingFees; }
	public BigDecimal getValuation() { return valuation; }
	public void setValuation(BigDecimal valuation) { this.valuation = valuation; }
	public RiskLevel getInfringementRisk() { return infringementRisk; }
	public void setInfringementRisk(RiskLevel infringementRisk) { this.infringementRisk = infringementRisk; }
	public RiskLevel getOwnershipDisputesRisk() { return ownershipDisputesRisk; }
	public void setOwnershipDisputesRisk(RiskLevel ownershipDisputesRisk) { this.ownershipDisputesRisk = ownershipDisputesRisk; }
	public RiskLevel getExpirationRisk() { return expirationRisk; }
	public void setExpirationRisk(RiskLevel expirationRisk) { this.expirationRisk = expirationRisk; }
	public String getComplianceFlags() { return complianceFlags; }
	public void setComplianceFlags(String complianceFlags) { this.complianceFlags = complianceFlags; }
	public BigDecimal getMarketRelevanceScore() { return marketRelevanceScore; }
	public void setMarketRelevanceScore(BigDecimal marketRelevanceScore) { this.marketRelevanceScore = marketRelevanceScore; }
	public BigDecimal getPortfolioImpactScore() { return portfolioImpactScore; }
	public void setPortfolioImpactScore(BigDecimal portfolioImpactScore) { this.portfolioImpactScore = portfolioImpactScore; }
	public String getSourceOfInformation() { return sourceOfInformation; }
	public void setSourceOfInformation(String sourceOfInformation) { this.sourceOfInformation = sourceOfInformation; }
	public String getResponsibleReviewer() { return responsibleReviewer; }
	public void setResponsibleReviewer(String responsibleReviewer) { this.responsibleReviewer = responsibleReviewer; }
	public String getDocumentReferences() { return documentReferences; }
	public void setDocumentReferences(String documentReferences) { this.documentReferences = documentReferences; }
	public String getTagsCategories() { return tagsCategories; }
	public void setTagsCategories(String tagsCategories) { this.tagsCategories = tagsCategories; }
	public String getNotes() { return notes; }
	public void setNotes(String notes) { this.notes = notes; }
}
