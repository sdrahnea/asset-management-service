package com.sdr.ams.service;

import com.sdr.ams.model.intangible.Trademark;
import com.sdr.ams.repository.TrademarkRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class TrademarkService {

    private final TrademarkRepository trademarkRepository;

    public TrademarkService(TrademarkRepository trademarkRepository) {
        this.trademarkRepository = trademarkRepository;
    }

    @Transactional(readOnly = true)
    public List<Trademark> findAll() {
        return trademarkRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Trademark> findAll(
        Trademark.MarkType markType,
        Trademark.LegalStatus legalStatus,
        Trademark.OwnerType ownerType,
        Trademark.LicensingStatus licensingStatus
    ) {
        return trademarkRepository.search(markType, legalStatus, ownerType, licensingStatus);
    }

    @Transactional(readOnly = true)
    public Trademark findById(Long id) {
        return trademarkRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Trademark not found for id: " + id));
    }

    public Trademark create(Trademark trademark) {
        normalizeAndValidate(trademark, null);
        return trademarkRepository.save(trademark);
    }

    public Trademark update(Long id, Trademark input) {
        Trademark existing = findById(id);
        BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt", "updatedBy");
        normalizeAndValidate(existing, id);
        return trademarkRepository.save(existing);
    }

    public void delete(Long id) {
        trademarkRepository.deleteById(id);
    }

    private void normalizeAndValidate(Trademark trademark, Long excludeId) {
        trademark.setTrademarkId(normalizeIdentifier(trademark.getTrademarkId()));
        trademark.setApplicationNumber(normalizeIdentifier(trademark.getApplicationNumber()));
        trademark.setRegistrationNumber(normalizeIdentifier(trademark.getRegistrationNumber()));
        trademark.setPriorityNumber(normalizeIdentifier(trademark.getPriorityNumber()));

        trademark.setMarkName(trimToNull(trademark.getMarkName()));
        trademark.setMarkDescription(trimToNull(trademark.getMarkDescription()));
        trademark.setNiceClasses(trimToNull(trademark.getNiceClasses()));
        trademark.setViennaCodes(trimToNull(trademark.getViennaCodes()));
        trademark.setIndustrySector(trimToNull(trademark.getIndustrySector()));
        trademark.setTags(trimToNull(trademark.getTags()));
        trademark.setOwnerName(trimToNull(trademark.getOwnerName()));
        trademark.setOwnerAddress(trimToNull(trademark.getOwnerAddress()));
        trademark.setRepresentative(trimToNull(trademark.getRepresentative()));
        trademark.setAssignmentHistory(trimToNull(trademark.getAssignmentHistory()));
        trademark.setCoOwners(trimToNull(trademark.getCoOwners()));
        trademark.setRenewalHistory(trimToNull(trademark.getRenewalHistory()));
        trademark.setOppositions(trimToNull(trademark.getOppositions()));
        trademark.setCancellationActions(trimToNull(trademark.getCancellationActions()));
        trademark.setJurisdictionsFiled(trimToNull(trademark.getJurisdictionsFiled()));
        trademark.setJurisdictionsRegistered(trimToNull(trademark.getJurisdictionsRegistered()));
        trademark.setRegionalDesignations(trimToNull(trademark.getRegionalDesignations()));
        trademark.setGoodsAndServices(trimToNull(trademark.getGoodsAndServices()));
        trademark.setSpecimen(trimToNull(trademark.getSpecimen()));
        trademark.setLimitations(trimToNull(trademark.getLimitations()));
        trademark.setCoexistenceAgreements(trimToNull(trademark.getCoexistenceAgreements()));
        trademark.setLicensees(trimToNull(trademark.getLicensees()));
        trademark.setComplianceFlags(trimToNull(trademark.getComplianceFlags()));
        trademark.setOfficialDocuments(trimToNull(trademark.getOfficialDocuments()));
        trademark.setRepresentations(trimToNull(trademark.getRepresentations()));
        trademark.setSearchReports(trimToNull(trademark.getSearchReports()));
        trademark.setLegalOpinions(trimToNull(trademark.getLegalOpinions()));
        trademark.setInternalNotes(trimToNull(trademark.getInternalNotes()));
        trademark.setDataSource(trimToNull(trademark.getDataSource()));
        trademark.setResponsibleAgent(trimToNull(trademark.getResponsibleAgent()));
        trademark.setNotes(trimToNull(trademark.getNotes()));

        validateMandatoryFields(trademark);
        if (!trademark.isPriorityClaimed()) {
            trademark.setPriorityNumber(null);
            trademark.setPriorityDate(null);
        }
        validateBusinessRules(trademark);
        validateUniqueness(trademark, excludeId);
    }

    private void validateMandatoryFields(Trademark trademark) {
        requireText(trademark.getTrademarkId(), "Trademark ID is required");
        requireText(trademark.getMarkName(), "Mark name is required");
        requireText(trademark.getOwnerName(), "Owner name is required");
        requireText(trademark.getApplicationNumber(), "Application number is required");
        requireText(trademark.getDataSource(), "Data source is required");

        if (trademark.getMarkType() == null) {
            throw new IllegalArgumentException("Mark type is required");
        }
        if (trademark.getOwnerType() == null) {
            throw new IllegalArgumentException("Owner type is required");
        }
        if (trademark.getLegalStatus() == null) {
            throw new IllegalArgumentException("Legal status is required");
        }
        if (trademark.getApplicationDate() == null) {
            throw new IllegalArgumentException("Application date is required");
        }
    }

    private void validateBusinessRules(Trademark trademark) {
        if (trademark.isPriorityClaimed()) {
            requireText(trademark.getPriorityNumber(), "Priority number is required when priority is claimed");
            if (trademark.getPriorityDate() == null) {
                throw new IllegalArgumentException("Priority date is required when priority is claimed");
            }
        }

        if (trademark.getPriorityDate() != null
            && trademark.getApplicationDate() != null
            && trademark.getPriorityDate().isAfter(trademark.getApplicationDate())) {
            throw new IllegalArgumentException("Priority date must be on or before application date");
        }

        if (trademark.getRegistrationDate() != null
            && trademark.getApplicationDate() != null
            && trademark.getRegistrationDate().isBefore(trademark.getApplicationDate())) {
            throw new IllegalArgumentException("Registration date must be on or after application date");
        }

        if (trademark.getExpirationDate() != null
            && trademark.getRegistrationDate() != null
            && trademark.getExpirationDate().isBefore(trademark.getRegistrationDate())) {
            throw new IllegalArgumentException("Expiration date must be on or after registration date");
        }

        if (trademark.getFirstUseDate() != null
            && trademark.getFirstUseInCommerceDate() != null
            && trademark.getFirstUseInCommerceDate().isBefore(trademark.getFirstUseDate())) {
            throw new IllegalArgumentException("First use in commerce date must be on or after first use date");
        }
    }

    private void validateUniqueness(Trademark trademark, Long excludeId) {
        if (trademarkRepository.existsTrademarkId(trademark.getTrademarkId(), excludeId)) {
            throw new IllegalArgumentException("Trademark ID must be unique");
        }
        if (trademarkRepository.existsApplicationNumber(trademark.getApplicationNumber(), excludeId)) {
            throw new IllegalArgumentException("Application number must be unique");
        }
        if (trademark.getRegistrationNumber() != null
            && trademarkRepository.existsRegistrationNumber(trademark.getRegistrationNumber(), excludeId)) {
            throw new IllegalArgumentException("Registration number must be unique");
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
