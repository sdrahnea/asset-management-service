package com.sdr.ams.service;

import com.sdr.ams.model.financial.Bond;
import com.sdr.ams.repository.BondRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class BondService {

    private final BondRepository bondRepository;

    public BondService(BondRepository bondRepository) {
        this.bondRepository = bondRepository;
    }

    @Transactional(readOnly = true)
    public List<Bond> findAll(
        String issuer,
        Bond.BondType bondType,
        Bond.TradingStatus tradingStatus,
        String currency
    ) {
        return bondRepository.search(trimToNull(issuer), bondType, tradingStatus, trimToNull(currency));
    }

    @Transactional(readOnly = true)
    public Bond findById(Long id) {
        return bondRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Bond not found for id: " + id));
    }

    public Bond create(Bond bond) {
        normalizeAndValidate(bond, null);
        return bondRepository.save(bond);
    }

    public Bond update(Long id, Bond input) {
        Bond existing = findById(id);
        BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt", "updatedBy");
        normalizeAndValidate(existing, id);
        return bondRepository.save(existing);
    }

    public void delete(Long id) {
        bondRepository.deleteById(id);
    }

    private void normalizeAndValidate(Bond bond, Long excludeId) {
        bond.setBondId(normalizeIdentifier(bond.getBondId()));
        bond.setIsin(normalizeIdentifier(bond.getIsin()));
        bond.setCusipSedol(normalizeIdentifier(bond.getCusipSedol()));
        bond.setCurrency(normalizeIdentifier(bond.getCurrency()));

        bond.setTitle(trimToNull(bond.getTitle()));
        bond.setIssuer(trimToNull(bond.getIssuer()));
        bond.setIssuerCountry(trimToNull(bond.getIssuerCountry()));
        bond.setAmortizationSchedule(trimToNull(bond.getAmortizationSchedule()));
        bond.setCollateral(trimToNull(bond.getCollateral()));
        bond.setCovenants(trimToNull(bond.getCovenants()));
        bond.setProspectusReference(trimToNull(bond.getProspectusReference()));
        bond.setRating(trimToNull(bond.getRating()));
        bond.setTradingVenue(trimToNull(bond.getTradingVenue()));
        bond.setStressTestResults(trimToNull(bond.getStressTestResults()));
        bond.setPortfolioReference(trimToNull(bond.getPortfolioReference()));
        bond.setAssessmentHistory(trimToNull(bond.getAssessmentHistory()));
        bond.setDocuments(trimToNull(bond.getDocuments()));
        bond.setTags(trimToNull(bond.getTags()));
        bond.setAnalystNotes(trimToNull(bond.getAnalystNotes()));
        bond.setDataSource(trimToNull(bond.getDataSource()));
        bond.setResponsibleAgent(trimToNull(bond.getResponsibleAgent()));
        bond.setNotes(trimToNull(bond.getNotes()));

        validateMandatoryFields(bond);
        validateBusinessRules(bond);
        validateUniqueness(bond, excludeId);
    }

    private void validateMandatoryFields(Bond bond) {
        requireText(bond.getBondId(), "Bond ID is required");
        requireText(bond.getTitle(), "Bond title is required");
        requireText(bond.getIssuer(), "Issuer is required");
        requireText(bond.getCurrency(), "Currency is required");
        requireText(bond.getDataSource(), "Data source is required");
        if (bond.getBondType() == null) {
            throw new IllegalArgumentException("Bond type is required");
        }
    }

    private void validateBusinessRules(Bond bond) {
        if (bond.getIssueDate() != null && bond.getMaturityDate() != null && bond.getMaturityDate().isBefore(bond.getIssueDate())) {
            throw new IllegalArgumentException("Maturity date must be on or after issue date");
        }

        if (bond.getIssueDate() != null && bond.getCallDate() != null && bond.getCallDate().isBefore(bond.getIssueDate())) {
            throw new IllegalArgumentException("Call date must be on or after issue date");
        }

        if (bond.getIssueDate() != null && bond.getPutDate() != null && bond.getPutDate().isBefore(bond.getIssueDate())) {
            throw new IllegalArgumentException("Put date must be on or after issue date");
        }

        if (bond.getMaturityDate() != null && bond.getCallDate() != null && bond.getCallDate().isAfter(bond.getMaturityDate())) {
            throw new IllegalArgumentException("Call date must be on or before maturity date");
        }

        if (bond.getMaturityDate() != null && bond.getPutDate() != null && bond.getPutDate().isAfter(bond.getMaturityDate())) {
            throw new IllegalArgumentException("Put date must be on or before maturity date");
        }
    }

    private void validateUniqueness(Bond bond, Long excludeId) {
        if (bondRepository.existsBondId(bond.getBondId(), excludeId)) {
            throw new IllegalArgumentException("Bond ID must be unique");
        }
        if (bond.getIsin() != null && bondRepository.existsIsin(bond.getIsin(), excludeId)) {
            throw new IllegalArgumentException("ISIN must be unique");
        }
        if (bond.getCusipSedol() != null && bondRepository.existsCusipSedol(bond.getCusipSedol(), excludeId)) {
            throw new IllegalArgumentException("CUSIP/SEDOL must be unique");
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
