package com.sdr.ams.service;

import com.sdr.ams.model.intangible.Patent;
import com.sdr.ams.repository.PatentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class PatentService {

    private final PatentRepository patentRepository;

    public PatentService(PatentRepository patentRepository) {
        this.patentRepository = patentRepository;
    }

    @Transactional(readOnly = true)
    public List<Patent> findAll(
        Patent.PatentType patentType,
        Patent.LegalStatus legalStatus,
        String technologyField,
        String assigneeOwner
    ) {
        return patentRepository.search(patentType, legalStatus, trimToNull(technologyField), trimToNull(assigneeOwner));
    }

    @Transactional(readOnly = true)
    public Patent findById(Long id) {
        return patentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Patent not found for id: " + id));
    }

    public Patent create(Patent patent) {
        normalizeAndValidate(patent, null);
        return patentRepository.save(patent);
    }

    public Patent update(Long id, Patent input) {
        Patent existing = findById(id);
        BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt", "updatedBy");
        normalizeAndValidate(existing, id);
        return patentRepository.save(existing);
    }

    public void delete(Long id) {
        patentRepository.deleteById(id);
    }

    private void normalizeAndValidate(Patent patent, Long excludeId) {
        patent.setPatentId(normalizeIdentifier(patent.getPatentId()));
        patent.setApplicationNumber(normalizeIdentifier(patent.getApplicationNumber()));
        patent.setPublicationNumber(normalizeIdentifier(patent.getPublicationNumber()));
        patent.setGrantNumber(normalizeIdentifier(patent.getGrantNumber()));
        patent.setPatentFamilyId(normalizeIdentifier(patent.getPatentFamilyId()));

        patent.setTitle(trimToNull(patent.getTitle()));
        patent.setAbstractSummary(trimToNull(patent.getAbstractSummary()));
        patent.setTechnologyField(trimToNull(patent.getTechnologyField()));
        patent.setIpcCpcCodes(trimToNull(patent.getIpcCpcCodes()));
        patent.setKeywordsTags(trimToNull(patent.getKeywordsTags()));
        patent.setInventors(trimToNull(patent.getInventors()));
        patent.setAssigneeOwner(trimToNull(patent.getAssigneeOwner()));
        patent.setAssignmentHistory(trimToNull(patent.getAssignmentHistory()));
        patent.setCountryOfOrigin(trimToNull(patent.getCountryOfOrigin()));
        patent.setPriorityNumbers(trimToNull(patent.getPriorityNumbers()));
        patent.setPriorityDates(trimToNull(patent.getPriorityDates()));
        patent.setJurisdictions(trimToNull(patent.getJurisdictions()));
        patent.setClaimsText(trimToNull(patent.getClaimsText()));
        patent.setDescriptionSpecification(trimToNull(patent.getDescriptionSpecification()));
        patent.setDrawingsReferences(trimToNull(patent.getDrawingsReferences()));
        patent.setScopeOfProtection(trimToNull(patent.getScopeOfProtection()));
        patent.setLimitationsDisclaimers(trimToNull(patent.getLimitationsDisclaimers()));
        patent.setCountriesFiled(trimToNull(patent.getCountriesFiled()));
        patent.setCountriesGranted(trimToNull(patent.getCountriesGranted()));
        patent.setNationalPhaseEntries(trimToNull(patent.getNationalPhaseEntries()));
        patent.setRenewalDates(trimToNull(patent.getRenewalDates()));
        patent.setMaintenanceFeeSchedule(trimToNull(patent.getMaintenanceFeeSchedule()));
        patent.setLegalDeadlines(trimToNull(patent.getLegalDeadlines()));
        patent.setOppositionLitigationHistory(trimToNull(patent.getOppositionLitigationHistory()));
        patent.setFtoConcerns(trimToNull(patent.getFtoConcerns()));
        patent.setOfficialDocuments(trimToNull(patent.getOfficialDocuments()));
        patent.setSupportingDocuments(trimToNull(patent.getSupportingDocuments()));
        patent.setDataSource(trimToNull(patent.getDataSource()));
        patent.setResponsibleAnalyst(trimToNull(patent.getResponsibleAnalyst()));
        patent.setNotesComments(trimToNull(patent.getNotesComments()));

        validateMandatoryFields(patent);
        validateBusinessRules(patent);
        validateUniqueness(patent, excludeId);
    }

    private void validateMandatoryFields(Patent patent) {
        requireText(patent.getPatentId(), "Patent ID is required");
        requireText(patent.getTitle(), "Title is required");
        requireText(patent.getAssigneeOwner(), "Assignee/owner is required");
        requireText(patent.getApplicationNumber(), "Application number is required");
        requireText(patent.getDataSource(), "Data source is required");

        if (patent.getPatentType() == null) {
            throw new IllegalArgumentException("Patent type is required");
        }
        if (patent.getApplicationDate() == null) {
            throw new IllegalArgumentException("Application date is required");
        }
        if (patent.getLegalStatus() == null) {
            throw new IllegalArgumentException("Legal status is required");
        }
    }

    private void validateBusinessRules(Patent patent) {
        if (patent.getPublicationDate() != null && patent.getApplicationDate() != null
            && patent.getPublicationDate().isBefore(patent.getApplicationDate())) {
            throw new IllegalArgumentException("Publication date must be on or after application date");
        }

        if (patent.getGrantDate() != null && patent.getApplicationDate() != null
            && patent.getGrantDate().isBefore(patent.getApplicationDate())) {
            throw new IllegalArgumentException("Grant date must be on or after application date");
        }

        if (patent.getExpiryDate() != null && patent.getGrantDate() != null
            && patent.getExpiryDate().isBefore(patent.getGrantDate())) {
            throw new IllegalArgumentException("Expiry date must be on or after grant date");
        }

        if (patent.getLegalStatus() == Patent.LegalStatus.GRANTED) {
            requireText(patent.getGrantNumber(), "Grant number is required when legal status is GRANTED");
            if (patent.getGrantDate() == null) {
                throw new IllegalArgumentException("Grant date is required when legal status is GRANTED");
            }
        }
    }

    private void validateUniqueness(Patent patent, Long excludeId) {
        if (patentRepository.existsPatentId(patent.getPatentId(), excludeId)) {
            throw new IllegalArgumentException("Patent ID must be unique");
        }
        if (patentRepository.existsApplicationNumber(patent.getApplicationNumber(), excludeId)) {
            throw new IllegalArgumentException("Application number must be unique");
        }
        if (patent.getPublicationNumber() != null
            && patentRepository.existsPublicationNumber(patent.getPublicationNumber(), excludeId)) {
            throw new IllegalArgumentException("Publication number must be unique");
        }
        if (patent.getGrantNumber() != null
            && patentRepository.existsGrantNumber(patent.getGrantNumber(), excludeId)) {
            throw new IllegalArgumentException("Grant number must be unique");
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
