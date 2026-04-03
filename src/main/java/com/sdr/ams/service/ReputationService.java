package com.sdr.ams.service;

import com.sdr.ams.model.intangible.Reputation;
import com.sdr.ams.repository.ReputationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class ReputationService {

    private final ReputationRepository reputationRepository;

    public ReputationService(ReputationRepository reputationRepository) {
        this.reputationRepository = reputationRepository;
    }

    @Transactional(readOnly = true)
    public List<Reputation> findAll(
        String entityId,
        Reputation.EntityType entityType,
        Reputation.TrendDirection trendDirection,
        Reputation.CompetitivePosition competitivePosition
    ) {
        return reputationRepository.search(trimToNull(entityId), entityType, trendDirection, competitivePosition);
    }

    @Transactional(readOnly = true)
    public Reputation findById(Long id) {
        return reputationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Reputation not found for id: " + id));
    }

    public Reputation create(Reputation reputation) {
        normalizeAndValidate(reputation, null);
        return reputationRepository.save(reputation);
    }

    public Reputation update(Long id, Reputation input) {
        Reputation existing = findById(id);
        BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt", "updatedBy");
        normalizeAndValidate(existing, id);
        return reputationRepository.save(existing);
    }

    public void delete(Long id) {
        reputationRepository.deleteById(id);
    }

    private void normalizeAndValidate(Reputation reputation, Long excludeId) {
        reputation.setReputationId(normalizeIdentifier(reputation.getReputationId()));
        reputation.setEntityId(normalizeIdentifier(reputation.getEntityId()));

        reputation.setDisplayName(trimToNull(reputation.getDisplayName()));
        reputation.setIndustry(trimToNull(reputation.getIndustry()));
        reputation.setStakeholderFeedback(trimToNull(reputation.getStakeholderFeedback()));
        reputation.setEventDescription(trimToNull(reputation.getEventDescription()));
        reputation.setEventSource(trimToNull(reputation.getEventSource()));
        reputation.setLegalIssues(trimToNull(reputation.getLegalIssues()));
        reputation.setEthicalConcerns(trimToNull(reputation.getEthicalConcerns()));
        reputation.setComplianceFlags(trimToNull(reputation.getComplianceFlags()));
        reputation.setCrisisHistory(trimToNull(reputation.getCrisisHistory()));
        reputation.setRecoveryPerformance(trimToNull(reputation.getRecoveryPerformance()));
        reputation.setMarketReputationBenchmark(trimToNull(reputation.getMarketReputationBenchmark()));
        reputation.setCulturalFactors(trimToNull(reputation.getCulturalFactors()));
        reputation.setReferencesData(trimToNull(reputation.getReferencesData()));
        reputation.setReviewSamples(trimToNull(reputation.getReviewSamples()));
        reputation.setMediaArticles(trimToNull(reputation.getMediaArticles()));
        reputation.setSurveyData(trimToNull(reputation.getSurveyData()));
        reputation.setInternalNotes(trimToNull(reputation.getInternalNotes()));
        reputation.setDataSource(trimToNull(reputation.getDataSource()));
        reputation.setResponsibleAgent(trimToNull(reputation.getResponsibleAgent()));
        reputation.setTags(trimToNull(reputation.getTags()));
        reputation.setNotes(trimToNull(reputation.getNotes()));

        validateMandatoryFields(reputation);
        validateBusinessRules(reputation);
        validateUniqueness(reputation, excludeId);
    }

    private void validateMandatoryFields(Reputation reputation) {
        requireText(reputation.getReputationId(), "Reputation ID is required");
        requireText(reputation.getEntityId(), "Entity ID is required");
        requireText(reputation.getDisplayName(), "Display name is required");
        requireText(reputation.getDataSource(), "Data source is required");
        if (reputation.getEntityType() == null) {
            throw new IllegalArgumentException("Entity type is required");
        }
    }

    private void validateBusinessRules(Reputation reputation) {
        if (reputation.getEventDate() != null && reputation.getEventDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Event date cannot be in the future");
        }

        boolean hasEventContent = reputation.getEventDate() != null
            || reputation.getEventDescription() != null
            || reputation.getEventImpactScore() != null
            || reputation.getEventSource() != null;
        if (hasEventContent && reputation.getEventType() == null) {
            throw new IllegalArgumentException("Event type is required when event details are provided");
        }
    }

    private void validateUniqueness(Reputation reputation, Long excludeId) {
        if (reputationRepository.existsReputationId(reputation.getReputationId(), excludeId)) {
            throw new IllegalArgumentException("Reputation ID must be unique");
        }
        if (reputationRepository.existsEntityReference(reputation.getEntityType(), reputation.getEntityId(), excludeId)) {
            throw new IllegalArgumentException("Entity type + entity ID combination must be unique");
        }
    }

    private String normalizeIdentifier(String value) {
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
