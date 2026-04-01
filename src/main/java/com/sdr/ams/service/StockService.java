package com.sdr.ams.service;

import com.sdr.ams.model.financial.Stock;
import com.sdr.ams.repository.StockRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional(readOnly = true)
    public List<Stock> findAll(
        String tickerSymbol,
        String exchange,
        String sector,
        Stock.CompanyType companyType
    ) {
        return stockRepository.search(trimToNull(tickerSymbol), trimToNull(exchange), trimToNull(sector), companyType);
    }

    @Transactional(readOnly = true)
    public Stock findById(Long id) {
        return stockRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Stock not found for id: " + id));
    }

    public Stock create(Stock stock) {
        normalizeAndValidate(stock, null);
        return stockRepository.save(stock);
    }

    public Stock update(Long id, Stock input) {
        Stock existing = findById(id);
        BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt", "updatedBy");
        normalizeAndValidate(existing, id);
        return stockRepository.save(existing);
    }

    public void delete(Long id) {
        stockRepository.deleteById(id);
    }

    private void normalizeAndValidate(Stock stock, Long excludeId) {
        stock.setStockId(normalizeIdentifier(stock.getStockId()));
        stock.setTickerSymbol(normalizeIdentifier(stock.getTickerSymbol()));
        stock.setExchange(normalizeIdentifier(stock.getExchange()));
        stock.setIsin(normalizeIdentifier(stock.getIsin()));
        stock.setCusip(normalizeIdentifier(stock.getCusip()));
        stock.setCountryOfListing(normalizeIdentifier(stock.getCountryOfListing()));

        stock.setCompanyName(trimToNull(stock.getCompanyName()));
        stock.setIndustry(trimToNull(stock.getIndustry()));
        stock.setSector(trimToNull(stock.getSector()));
        stock.setBoardMembers(trimToNull(stock.getBoardMembers()));
        stock.setExecutiveTeam(trimToNull(stock.getExecutiveTeam()));
        stock.setMajorShareholders(trimToNull(stock.getMajorShareholders()));
        stock.setOwnershipStructure(trimToNull(stock.getOwnershipStructure()));
        stock.setRegulatoryFlags(trimToNull(stock.getRegulatoryFlags()));
        stock.setDividendAnnouncements(trimToNull(stock.getDividendAnnouncements()));
        stock.setEarningsReports(trimToNull(stock.getEarningsReports()));
        stock.setStockSplits(trimToNull(stock.getStockSplits()));
        stock.setMergersAcquisitions(trimToNull(stock.getMergersAcquisitions()));
        stock.setSpinOffs(trimToNull(stock.getSpinOffs()));
        stock.setShareBuybacks(trimToNull(stock.getShareBuybacks()));
        stock.setGuidanceUpdates(trimToNull(stock.getGuidanceUpdates()));
        stock.setNotableNewsEvents(trimToNull(stock.getNotableNewsEvents()));
        stock.setIndustryTrends(trimToNull(stock.getIndustryTrends()));
        stock.setMacroeconomicIndicators(trimToNull(stock.getMacroeconomicIndicators()));
        stock.setGeopoliticalExposure(trimToNull(stock.getGeopoliticalExposure()));
        stock.setSupplyChainRisks(trimToNull(stock.getSupplyChainRisks()));
        stock.setComplianceFlags(trimToNull(stock.getComplianceFlags()));
        stock.setFinancialReports(trimToNull(stock.getFinancialReports()));
        stock.setAnalystReports(trimToNull(stock.getAnalystReports()));
        stock.setNewsReferences(trimToNull(stock.getNewsReferences()));
        stock.setRegulatoryFilings(trimToNull(stock.getRegulatoryFilings()));
        stock.setInternalNotes(trimToNull(stock.getInternalNotes()));
        stock.setDataSource(trimToNull(stock.getDataSource()));
        stock.setResponsibleAgent(trimToNull(stock.getResponsibleAgent()));
        stock.setTags(trimToNull(stock.getTags()));
        stock.setNotes(trimToNull(stock.getNotes()));

        validateMandatoryFields(stock);
        validateBusinessRules(stock);
        validateUniqueness(stock, excludeId);
    }

    private void validateMandatoryFields(Stock stock) {
        requireText(stock.getStockId(), "Stock ID is required");
        requireText(stock.getTickerSymbol(), "Ticker symbol is required");
        requireText(stock.getExchange(), "Exchange is required");
        requireText(stock.getCompanyName(), "Company name is required");
        requireText(stock.getDataSource(), "Data source is required");
    }

    private void validateBusinessRules(Stock stock) {
        if (stock.getDayHigh() != null && stock.getDayLow() != null && stock.getDayHigh().compareTo(stock.getDayLow()) < 0) {
            throw new IllegalArgumentException("Day high must be greater than or equal to day low");
        }

        if (stock.getFiftyTwoWeekHigh() != null
            && stock.getFiftyTwoWeekLow() != null
            && stock.getFiftyTwoWeekHigh().compareTo(stock.getFiftyTwoWeekLow()) < 0) {
            throw new IllegalArgumentException("52-week high must be greater than or equal to 52-week low");
        }

        if (stock.getCurrentPrice() != null && stock.getDayLow() != null && stock.getCurrentPrice().compareTo(stock.getDayLow()) < 0) {
            throw new IllegalArgumentException("Current price must be between day low and day high");
        }
        if (stock.getCurrentPrice() != null && stock.getDayHigh() != null && stock.getCurrentPrice().compareTo(stock.getDayHigh()) > 0) {
            throw new IllegalArgumentException("Current price must be between day low and day high");
        }
    }

    private void validateUniqueness(Stock stock, Long excludeId) {
        if (stockRepository.existsStockId(stock.getStockId(), excludeId)) {
            throw new IllegalArgumentException("Stock ID must be unique");
        }
        if (stockRepository.existsTickerSymbolOnExchange(stock.getTickerSymbol(), stock.getExchange(), excludeId)) {
            throw new IllegalArgumentException("Ticker symbol must be unique per exchange");
        }
        if (stock.getIsin() != null && stockRepository.existsIsin(stock.getIsin(), excludeId)) {
            throw new IllegalArgumentException("ISIN must be unique");
        }
        if (stock.getCusip() != null && stockRepository.existsCusip(stock.getCusip(), excludeId)) {
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
