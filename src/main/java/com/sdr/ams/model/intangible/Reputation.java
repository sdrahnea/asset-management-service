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
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "reputation_id", nullable = false, length = 100))
@Table(
	name = "reputation",
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_reputation_id", columnNames = "reputation_id"),
		@UniqueConstraint(name = "uk_reputation_subject", columnNames = {"entity_type", "entity_id"})
	}
)
public class Reputation extends CoreEntity {

	public enum EntityType { PERSON, COMPANY, BRAND, PRODUCT, ORGANIZATION, OTHER }
	public enum GeographicScope { LOCAL, NATIONAL, REGIONAL, INTERNATIONAL, GLOBAL }
	public enum MediaCoverageTone { POSITIVE, NEUTRAL, NEGATIVE, MIXED }
	public enum EventType { AWARD, SCANDAL, LAWSUIT, PRODUCT_LAUNCH, CRISIS, ACHIEVEMENT, OTHER }
	public enum TrendDirection { IMPROVING, STABLE, DECLINING, VOLATILE }
	public enum CompetitivePosition { LEADER, CHALLENGER, NICHE, DECLINING, OTHER }
	public enum RegulatoryEnvironment { STRICT, MODERATE, LENIENT, VOLATILE, OTHER }

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

	@NotBlank(message = "Reputation ID is required")
	@Size(max = 100, message = "Reputation ID must be at most 100 characters")
	public String getReputationId() {
		return getName();
	}

	public void setReputationId(String reputationId) {
		setName(reputationId);
	}

	@NotNull(message = "Entity type is required")
	@Enumerated(EnumType.STRING)
	@Column(name = "entity_type", nullable = false, length = 30)
	private EntityType entityType;

	@NotBlank(message = "Entity ID is required")
	@Size(max = 100, message = "Entity ID must be at most 100 characters")
	@Column(name = "entity_id", nullable = false, length = 100)
	private String entityId;

	@NotBlank(message = "Display name is required")
	@Size(max = 255, message = "Display name must be at most 255 characters")
	@Column(name = "display_name", nullable = false, length = 255)
	private String displayName;

	@Size(max = 200, message = "Industry must be at most 200 characters")
	@Column(length = 200)
	private String industry;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private GeographicScope geographicScope;

	@DecimalMin(value = "-100.0", message = "Public sentiment must be >= -100")
	@DecimalMax(value = "100.0", message = "Public sentiment must be <= 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal publicSentiment;

	@DecimalMin(value = "-100.0", message = "Social sentiment must be >= -100")
	@DecimalMax(value = "100.0", message = "Social sentiment must be <= 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal socialMediaSentiment;

	@DecimalMin(value = "0.0", message = "Customer feedback score must be >= 0")
	@DecimalMax(value = "100.0", message = "Customer feedback score must be <= 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal customerFeedbackScore;

	@DecimalMin(value = "0.0", message = "Employee feedback score must be >= 0")
	@DecimalMax(value = "100.0", message = "Employee feedback score must be <= 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal employeeFeedbackScore;

	@Column(length = 2000)
	private String stakeholderFeedback;

	@DecimalMin(value = "0", message = "Media coverage volume must be >= 0")
	@Column
	private Long mediaCoverageVolume;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private MediaCoverageTone mediaCoverageTone;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private EventType eventType;

	@Column
	private LocalDate eventDate;

	@Column(length = 2000)
	private String eventDescription;

	@DecimalMin(value = "-100.0", message = "Event impact score must be >= -100")
	@DecimalMax(value = "100.0", message = "Event impact score must be <= 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal eventImpactScore;

	@Size(max = 500, message = "Event source must be at most 500 characters")
	@Column(name = "event_source", length = 500)
	private String eventSource;

	@DecimalMin(value = "0.0", message = "Reputation score must be >= 0")
	@DecimalMax(value = "100.0", message = "Reputation score must be <= 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal reputationScore;

	@DecimalMin(value = "0.0", message = "Trust score must be >= 0")
	@DecimalMax(value = "100.0", message = "Trust score must be <= 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal trustScore;

	@DecimalMin(value = "0.0", message = "Credibility score must be >= 0")
	@DecimalMax(value = "100.0", message = "Credibility score must be <= 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal credibilityScore;

	@DecimalMin(value = "0.0", message = "Risk score must be >= 0")
	@DecimalMax(value = "100.0", message = "Risk score must be <= 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal riskScore;

	@DecimalMin(value = "0.0", message = "Volatility index must be >= 0")
	@DecimalMax(value = "100.0", message = "Volatility index must be <= 100")
	@Digits(integer = 5, fraction = 2)
	@Column(precision = 7, scale = 2)
	private BigDecimal volatilityIndex;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private TrendDirection trendDirection;

	@Column(length = 2000)
	private String legalIssues;

	@Column(length = 2000)
	private String ethicalConcerns;

	@Column(length = 2000)
	private String complianceFlags;

	@Column(length = 2000)
	private String crisisHistory;

	@Column(length = 2000)
	private String recoveryPerformance;

	@Column(length = 2000)
	private String marketReputationBenchmark;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private CompetitivePosition competitivePosition;

	@Column(length = 2000)
	private String culturalFactors;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RegulatoryEnvironment regulatoryEnvironment;

	@Column(name = "references_data", length = 2000)
	private String referencesData;

	@Column(length = 2000)
	private String reviewSamples;

	@Column(length = 2000)
	private String mediaArticles;

	@Column(length = 2000)
	private String surveyData;

	@Column(length = 4000)
	private String internalNotes;

	@NotBlank(message = "Data source is required")
	@Size(max = 100, message = "Data source must be at most 100 characters")
	@Column(nullable = false, length = 100)
	private String dataSource;

	@Size(max = 100, message = "Responsible agent must be at most 100 characters")
	@Column(length = 100)
	private String responsibleAgent;

	@Size(max = 500, message = "Tags must be at most 500 characters")
	@Column(length = 500)
	private String tags;

	@Column(length = 4000)
	private String notes;

	public EntityType getEntityType() { return entityType; }
	public void setEntityType(EntityType entityType) { this.entityType = entityType; }
	public String getEntityId() { return entityId; }
	public void setEntityId(String entityId) { this.entityId = entityId; }
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	public String getIndustry() { return industry; }
	public void setIndustry(String industry) { this.industry = industry; }
	public GeographicScope getGeographicScope() { return geographicScope; }
	public void setGeographicScope(GeographicScope geographicScope) { this.geographicScope = geographicScope; }
	public BigDecimal getPublicSentiment() { return publicSentiment; }
	public void setPublicSentiment(BigDecimal publicSentiment) { this.publicSentiment = publicSentiment; }
	public BigDecimal getSocialMediaSentiment() { return socialMediaSentiment; }
	public void setSocialMediaSentiment(BigDecimal socialMediaSentiment) { this.socialMediaSentiment = socialMediaSentiment; }
	public BigDecimal getCustomerFeedbackScore() { return customerFeedbackScore; }
	public void setCustomerFeedbackScore(BigDecimal customerFeedbackScore) { this.customerFeedbackScore = customerFeedbackScore; }
	public BigDecimal getEmployeeFeedbackScore() { return employeeFeedbackScore; }
	public void setEmployeeFeedbackScore(BigDecimal employeeFeedbackScore) { this.employeeFeedbackScore = employeeFeedbackScore; }
	public String getStakeholderFeedback() { return stakeholderFeedback; }
	public void setStakeholderFeedback(String stakeholderFeedback) { this.stakeholderFeedback = stakeholderFeedback; }
	public Long getMediaCoverageVolume() { return mediaCoverageVolume; }
	public void setMediaCoverageVolume(Long mediaCoverageVolume) { this.mediaCoverageVolume = mediaCoverageVolume; }
	public MediaCoverageTone getMediaCoverageTone() { return mediaCoverageTone; }
	public void setMediaCoverageTone(MediaCoverageTone mediaCoverageTone) { this.mediaCoverageTone = mediaCoverageTone; }
	public EventType getEventType() { return eventType; }
	public void setEventType(EventType eventType) { this.eventType = eventType; }
	public LocalDate getEventDate() { return eventDate; }
	public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
	public String getEventDescription() { return eventDescription; }
	public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }
	public BigDecimal getEventImpactScore() { return eventImpactScore; }
	public void setEventImpactScore(BigDecimal eventImpactScore) { this.eventImpactScore = eventImpactScore; }
	public String getEventSource() { return eventSource; }
	public void setEventSource(String eventSource) { this.eventSource = eventSource; }
	public BigDecimal getReputationScore() { return reputationScore; }
	public void setReputationScore(BigDecimal reputationScore) { this.reputationScore = reputationScore; }
	public BigDecimal getTrustScore() { return trustScore; }
	public void setTrustScore(BigDecimal trustScore) { this.trustScore = trustScore; }
	public BigDecimal getCredibilityScore() { return credibilityScore; }
	public void setCredibilityScore(BigDecimal credibilityScore) { this.credibilityScore = credibilityScore; }
	public BigDecimal getRiskScore() { return riskScore; }
	public void setRiskScore(BigDecimal riskScore) { this.riskScore = riskScore; }
	public BigDecimal getVolatilityIndex() { return volatilityIndex; }
	public void setVolatilityIndex(BigDecimal volatilityIndex) { this.volatilityIndex = volatilityIndex; }
	public TrendDirection getTrendDirection() { return trendDirection; }
	public void setTrendDirection(TrendDirection trendDirection) { this.trendDirection = trendDirection; }
	public String getLegalIssues() { return legalIssues; }
	public void setLegalIssues(String legalIssues) { this.legalIssues = legalIssues; }
	public String getEthicalConcerns() { return ethicalConcerns; }
	public void setEthicalConcerns(String ethicalConcerns) { this.ethicalConcerns = ethicalConcerns; }
	public String getComplianceFlags() { return complianceFlags; }
	public void setComplianceFlags(String complianceFlags) { this.complianceFlags = complianceFlags; }
	public String getCrisisHistory() { return crisisHistory; }
	public void setCrisisHistory(String crisisHistory) { this.crisisHistory = crisisHistory; }
	public String getRecoveryPerformance() { return recoveryPerformance; }
	public void setRecoveryPerformance(String recoveryPerformance) { this.recoveryPerformance = recoveryPerformance; }
	public String getMarketReputationBenchmark() { return marketReputationBenchmark; }
	public void setMarketReputationBenchmark(String marketReputationBenchmark) { this.marketReputationBenchmark = marketReputationBenchmark; }
	public CompetitivePosition getCompetitivePosition() { return competitivePosition; }
	public void setCompetitivePosition(CompetitivePosition competitivePosition) { this.competitivePosition = competitivePosition; }
	public String getCulturalFactors() { return culturalFactors; }
	public void setCulturalFactors(String culturalFactors) { this.culturalFactors = culturalFactors; }
	public RegulatoryEnvironment getRegulatoryEnvironment() { return regulatoryEnvironment; }
	public void setRegulatoryEnvironment(RegulatoryEnvironment regulatoryEnvironment) { this.regulatoryEnvironment = regulatoryEnvironment; }
	public String getReferencesData() { return referencesData; }
	public void setReferencesData(String referencesData) { this.referencesData = referencesData; }
	public String getReviewSamples() { return reviewSamples; }
	public void setReviewSamples(String reviewSamples) { this.reviewSamples = reviewSamples; }
	public String getMediaArticles() { return mediaArticles; }
	public void setMediaArticles(String mediaArticles) { this.mediaArticles = mediaArticles; }
	public String getSurveyData() { return surveyData; }
	public void setSurveyData(String surveyData) { this.surveyData = surveyData; }
	public String getInternalNotes() { return internalNotes; }
	public void setInternalNotes(String internalNotes) { this.internalNotes = internalNotes; }
	public String getDataSource() { return dataSource; }
	public void setDataSource(String dataSource) { this.dataSource = dataSource; }
	public String getResponsibleAgent() { return responsibleAgent; }
	public void setResponsibleAgent(String responsibleAgent) { this.responsibleAgent = responsibleAgent; }
	public String getTags() { return tags; }
	public void setTags(String tags) { this.tags = tags; }
	public String getNotes() { return notes; }
	public void setNotes(String notes) { this.notes = notes; }
}
