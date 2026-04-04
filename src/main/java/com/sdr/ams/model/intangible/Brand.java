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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "brand_id", nullable = false, length = 100))
@Table(
	name = "brand",
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_brand_brand_id", columnNames = "brand_id"),
		@UniqueConstraint(name = "uk_brand_tm_registration_number", columnNames = "trademark_registration_number")
	}
)
public class Brand extends CoreEntity {

	public enum TrademarkType { WORDMARK, FIGURATIVE, COMBINED }
	public enum BrandStatus { ACTIVE, EXPIRED, PENDING, DISPUTED }
	public enum SentimentLevel { POSITIVE, NEUTRAL, NEGATIVE }

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

	@NotBlank(message = "Brand ID is required")
	@Size(max = 100, message = "Brand ID must be at most 100 characters")
	@Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Brand ID must be alphanumeric")
	public String getBrandId() {
		return getName();
	}

	public void setBrandId(String brandId) {
		setName(brandId);
	}

	// Core identity information
	@NotBlank(message = "Brand name is required")
	@Size(max = 255, message = "Brand name must be at most 255 characters")
	@Column(nullable = false, length = 255)
	private String brandName;

	@Size(max = 500, message = "Logo reference must be at most 500 characters")
	@Column(length = 500)
	private String logoReference;

	@Size(max = 255, message = "Tagline/slogan must be at most 255 characters")
	@Column(length = 255)
	private String taglineSlogan;

	@Size(max = 1000, message = "Short description must be at most 1000 characters")
	@Column(length = 1000)
	private String shortDescription;

	@Size(max = 4000, message = "Long description must be at most 4000 characters")
	@Column(length = 4000)
	private String longDescription;

	@Size(max = 100, message = "Brand category must be at most 100 characters")
	@Column(length = 100)
	private String brandCategory;

	@NotBlank(message = "Parent company/owner is required")
	@Size(max = 255, message = "Parent company/owner must be at most 255 characters")
	@Column(nullable = false, length = 255)
	private String parentCompanyOwner;

	@NotBlank(message = "Country of origin is required")
	@Size(max = 100, message = "Country of origin must be at most 100 characters")
	@Column(nullable = false, length = 100)
	private String countryOfOrigin;

	// Legal and ownership attributes
	@Size(max = 100, message = "Trademark registration number must be at most 100 characters")
	@Column(length = 100, unique = true)
	private String trademarkRegistrationNumber;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private TrademarkType trademarkType;

	@Size(max = 500, message = "Jurisdictions covered must be at most 500 characters")
	@Column(length = 500)
	private String jurisdictionsCovered;

	@Column
	private LocalDate registrationDate;

	@Column
	private LocalDate expirationDate;

	@NotNull(message = "Status is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private BrandStatus status;

	@Size(max = 2000, message = "Associated legal documents must be at most 2000 characters")
	@Column(length = 2000)
	private String associatedLegalDocuments;

	// Market presence and positioning
	@Size(max = 150, message = "Industry/sector must be at most 150 characters")
	@Column(length = 150)
	private String industrySector;

	@Size(max = 1000, message = "Target audience must be at most 1000 characters")
	@Column(length = 1000)
	private String targetAudience;

	@Size(max = 2000, message = "Brand positioning statement must be at most 2000 characters")
	@Column(length = 2000)
	private String brandPositioningStatement;

	@Size(max = 2000, message = "Value proposition must be at most 2000 characters")
	@Column(length = 2000)
	private String valueProposition;

	@Size(max = 2000, message = "Competitors must be at most 2000 characters")
	@Column(length = 2000)
	private String competitors;

	@Size(max = 500, message = "Geographic markets served must be at most 500 characters")
	@Column(length = 500)
	private String geographicMarketsServed;

	// Performance and perception indicators
	@DecimalMin(value = "0.0", message = "Brand equity score must be greater than or equal to 0")
	@DecimalMax(value = "100.0", message = "Brand equity score must be less than or equal to 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal brandEquityScore;

	@Size(max = 100, message = "Brand awareness must be at most 100 characters")
	@Column(length = 100)
	private String brandAwareness;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private SentimentLevel brandSentiment;

	@DecimalMin(value = "0.0", message = "Market share must be greater than or equal to 0")
	@DecimalMax(value = "100.0", message = "Market share must be less than or equal to 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal marketShare;

	@Size(max = 1000, message = "Customer loyalty indicators must be at most 1000 characters")
	@Column(length = 1000)
	private String customerLoyaltyIndicators;

	@Min(value = -100, message = "NPS must be greater than or equal to -100")
	@Max(value = 100, message = "NPS must be less than or equal to 100")
	@Column
	private Integer netPromoterScore;

	// Digital footprint
	@Size(max = 500, message = "Official website must be at most 500 characters")
	@Column(length = 500)
	private String officialWebsite;

	@Size(max = 2000, message = "Social media accounts must be at most 2000 characters")
	@Column(length = 2000)
	private String socialMediaAccounts;

	@Size(max = 1000, message = "Follower counts must be at most 1000 characters")
	@Column(length = 1000)
	private String followerCounts;

	@Size(max = 1000, message = "Engagement metrics must be at most 1000 characters")
	@Column(length = 1000)
	private String engagementMetrics;

	@Size(max = 1000, message = "SEO visibility indicators must be at most 1000 characters")
	@Column(length = 1000)
	private String seoVisibilityIndicators;

	// Operational metadata
	@Size(max = 2000, message = "Assessment history must be at most 2000 characters")
	@Column(length = 2000)
	private String assessmentHistory;

	@Size(max = 100, message = "Assigned analyst/reviewer must be at most 100 characters")
	@Column(length = 100)
	private String assignedAnalystReviewer;

	@Size(max = 500, message = "Tags/classification labels must be at most 500 characters")
	@Column(length = 500)
	private String tagsClassificationLabels;

	// Optional financial attributes
	@DecimalMin(value = "0.0", message = "Revenue attributed to brand must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal revenueAttributedToBrand;

	@Size(max = 1000, message = "Profitability metrics must be at most 1000 characters")
	@Column(length = 1000)
	private String profitabilityMetrics;

	@DecimalMin(value = "0.0", message = "Licensing income must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal licensingIncome;

	@DecimalMin(value = "0.0", message = "Brand valuation must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal brandValuation;

	@DecimalMin(value = "0.0", message = "Cost of brand maintenance must be greater than or equal to 0")
	@Digits(integer = 17, fraction = 2)
	@Column(precision = 19, scale = 2)
	private BigDecimal costOfBrandMaintenance;

	@AssertTrue(message = "Registration date must be today or earlier")
	public boolean isRegistrationDateValid() {
		return registrationDate == null || !registrationDate.isAfter(LocalDate.now());
	}

	@AssertTrue(message = "Expiration date must be greater than or equal to registration date")
	public boolean isExpirationDateRangeValid() {
		return registrationDate == null || expirationDate == null || !expirationDate.isBefore(registrationDate);
	}

	public String getBrandName() { return brandName; }
	public void setBrandName(String brandName) { this.brandName = brandName; }
	public String getLogoReference() { return logoReference; }
	public void setLogoReference(String logoReference) { this.logoReference = logoReference; }
	public String getTaglineSlogan() { return taglineSlogan; }
	public void setTaglineSlogan(String taglineSlogan) { this.taglineSlogan = taglineSlogan; }
	public String getShortDescription() { return shortDescription; }
	public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
	public String getLongDescription() { return longDescription; }
	public void setLongDescription(String longDescription) { this.longDescription = longDescription; }
	public String getBrandCategory() { return brandCategory; }
	public void setBrandCategory(String brandCategory) { this.brandCategory = brandCategory; }
	public String getParentCompanyOwner() { return parentCompanyOwner; }
	public void setParentCompanyOwner(String parentCompanyOwner) { this.parentCompanyOwner = parentCompanyOwner; }
	public String getCountryOfOrigin() { return countryOfOrigin; }
	public void setCountryOfOrigin(String countryOfOrigin) { this.countryOfOrigin = countryOfOrigin; }
	public String getTrademarkRegistrationNumber() { return trademarkRegistrationNumber; }
	public void setTrademarkRegistrationNumber(String trademarkRegistrationNumber) { this.trademarkRegistrationNumber = trademarkRegistrationNumber; }
	public TrademarkType getTrademarkType() { return trademarkType; }
	public void setTrademarkType(TrademarkType trademarkType) { this.trademarkType = trademarkType; }
	public String getJurisdictionsCovered() { return jurisdictionsCovered; }
	public void setJurisdictionsCovered(String jurisdictionsCovered) { this.jurisdictionsCovered = jurisdictionsCovered; }
	public LocalDate getRegistrationDate() { return registrationDate; }
	public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
	public LocalDate getExpirationDate() { return expirationDate; }
	public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
	public BrandStatus getStatus() { return status; }
	public void setStatus(BrandStatus status) { this.status = status; }
	public String getAssociatedLegalDocuments() { return associatedLegalDocuments; }
	public void setAssociatedLegalDocuments(String associatedLegalDocuments) { this.associatedLegalDocuments = associatedLegalDocuments; }
	public String getIndustrySector() { return industrySector; }
	public void setIndustrySector(String industrySector) { this.industrySector = industrySector; }
	public String getTargetAudience() { return targetAudience; }
	public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; }
	public String getBrandPositioningStatement() { return brandPositioningStatement; }
	public void setBrandPositioningStatement(String brandPositioningStatement) { this.brandPositioningStatement = brandPositioningStatement; }
	public String getValueProposition() { return valueProposition; }
	public void setValueProposition(String valueProposition) { this.valueProposition = valueProposition; }
	public String getCompetitors() { return competitors; }
	public void setCompetitors(String competitors) { this.competitors = competitors; }
	public String getGeographicMarketsServed() { return geographicMarketsServed; }
	public void setGeographicMarketsServed(String geographicMarketsServed) { this.geographicMarketsServed = geographicMarketsServed; }
	public BigDecimal getBrandEquityScore() { return brandEquityScore; }
	public void setBrandEquityScore(BigDecimal brandEquityScore) { this.brandEquityScore = brandEquityScore; }
	public String getBrandAwareness() { return brandAwareness; }
	public void setBrandAwareness(String brandAwareness) { this.brandAwareness = brandAwareness; }
	public SentimentLevel getBrandSentiment() { return brandSentiment; }
	public void setBrandSentiment(SentimentLevel brandSentiment) { this.brandSentiment = brandSentiment; }
	public BigDecimal getMarketShare() { return marketShare; }
	public void setMarketShare(BigDecimal marketShare) { this.marketShare = marketShare; }
	public String getCustomerLoyaltyIndicators() { return customerLoyaltyIndicators; }
	public void setCustomerLoyaltyIndicators(String customerLoyaltyIndicators) { this.customerLoyaltyIndicators = customerLoyaltyIndicators; }
	public Integer getNetPromoterScore() { return netPromoterScore; }
	public void setNetPromoterScore(Integer netPromoterScore) { this.netPromoterScore = netPromoterScore; }
	public String getOfficialWebsite() { return officialWebsite; }
	public void setOfficialWebsite(String officialWebsite) { this.officialWebsite = officialWebsite; }
	public String getSocialMediaAccounts() { return socialMediaAccounts; }
	public void setSocialMediaAccounts(String socialMediaAccounts) { this.socialMediaAccounts = socialMediaAccounts; }
	public String getFollowerCounts() { return followerCounts; }
	public void setFollowerCounts(String followerCounts) { this.followerCounts = followerCounts; }
	public String getEngagementMetrics() { return engagementMetrics; }
	public void setEngagementMetrics(String engagementMetrics) { this.engagementMetrics = engagementMetrics; }
	public String getSeoVisibilityIndicators() { return seoVisibilityIndicators; }
	public void setSeoVisibilityIndicators(String seoVisibilityIndicators) { this.seoVisibilityIndicators = seoVisibilityIndicators; }
	public String getAssessmentHistory() { return assessmentHistory; }
	public void setAssessmentHistory(String assessmentHistory) { this.assessmentHistory = assessmentHistory; }
	public String getAssignedAnalystReviewer() { return assignedAnalystReviewer; }
	public void setAssignedAnalystReviewer(String assignedAnalystReviewer) { this.assignedAnalystReviewer = assignedAnalystReviewer; }
	public String getTagsClassificationLabels() { return tagsClassificationLabels; }
	public void setTagsClassificationLabels(String tagsClassificationLabels) { this.tagsClassificationLabels = tagsClassificationLabels; }
	public BigDecimal getRevenueAttributedToBrand() { return revenueAttributedToBrand; }
	public void setRevenueAttributedToBrand(BigDecimal revenueAttributedToBrand) { this.revenueAttributedToBrand = revenueAttributedToBrand; }
	public String getProfitabilityMetrics() { return profitabilityMetrics; }
	public void setProfitabilityMetrics(String profitabilityMetrics) { this.profitabilityMetrics = profitabilityMetrics; }
	public BigDecimal getLicensingIncome() { return licensingIncome; }
	public void setLicensingIncome(BigDecimal licensingIncome) { this.licensingIncome = licensingIncome; }
	public BigDecimal getBrandValuation() { return brandValuation; }
	public void setBrandValuation(BigDecimal brandValuation) { this.brandValuation = brandValuation; }
	public BigDecimal getCostOfBrandMaintenance() { return costOfBrandMaintenance; }
	public void setCostOfBrandMaintenance(BigDecimal costOfBrandMaintenance) { this.costOfBrandMaintenance = costOfBrandMaintenance; }
}
