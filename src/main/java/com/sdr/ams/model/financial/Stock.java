package com.sdr.ams.model.financial;

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
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "stock_id", nullable = false, length = 100))
@Table(
    name = "stock",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_stock_stock_id", columnNames = "stock_id"),
        @UniqueConstraint(name = "uk_stock_ticker_exchange", columnNames = {"ticker_symbol", "exchange_code"}),
        @UniqueConstraint(name = "uk_stock_isin", columnNames = "isin"),
        @UniqueConstraint(name = "uk_stock_cusip", columnNames = "cusip")
    }
)
public class Stock extends CoreEntity {

    public enum CompanyType { PUBLIC, ADR, DUAL_LISTED, OTHER }
    public enum CompetitivePosition { LEADER, CHALLENGER, NICHE, FOLLOWER, OTHER }

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

    @NotBlank(message = "Stock ID is required")
    @Size(max = 100, message = "Stock ID must be at most 100 characters")
    public String getStockId() {
        return getName();
    }

    public void setStockId(String stockId) {
        setName(stockId);
    }

    @NotBlank(message = "Ticker symbol is required")
    @Size(max = 20, message = "Ticker symbol must be at most 20 characters")
    @Column(name = "ticker_symbol", nullable = false, length = 20)
    private String tickerSymbol;

    @NotBlank(message = "Exchange is required")
    @Size(max = 30, message = "Exchange must be at most 30 characters")
    @Column(name = "exchange_code", nullable = false, length = 30)
    private String exchange;

    @Size(max = 12, message = "ISIN must be at most 12 characters")
    @Column(length = 12)
    private String isin;

    @Size(max = 20, message = "CUSIP/SEDOL must be at most 20 characters")
    @Column(length = 20)
    private String cusip;

    @NotBlank(message = "Company name is required")
    @Size(max = 255, message = "Company name must be at most 255 characters")
    @Column(nullable = false, length = 255)
    private String companyName;

    @Size(max = 120, message = "Industry must be at most 120 characters")
    @Column(length = 120)
    private String industry;

    @Size(max = 120, message = "Sector must be at most 120 characters")
    @Column(length = 120)
    private String sector;

    @Size(max = 100, message = "Country of listing must be at most 100 characters")
    @Column(length = 100)
    private String countryOfListing;

    @DecimalMin(value = "0.0", message = "Current price must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 4)
    @Column(precision = 21, scale = 4)
    private BigDecimal currentPrice;

    @DecimalMin(value = "0.0", message = "Open price must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 4)
    @Column(precision = 21, scale = 4)
    private BigDecimal openPrice;

    @DecimalMin(value = "0.0", message = "Close price must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 4)
    @Column(precision = 21, scale = 4)
    private BigDecimal closePrice;

    @DecimalMin(value = "0.0", message = "Day high must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 4)
    @Column(precision = 21, scale = 4)
    private BigDecimal dayHigh;

    @DecimalMin(value = "0.0", message = "Day low must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 4)
    @Column(precision = 21, scale = 4)
    private BigDecimal dayLow;

    @DecimalMin(value = "0.0", message = "52-week high must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 4)
    @Column(name = "fifty_two_week_high", precision = 21, scale = 4)
    private BigDecimal fiftyTwoWeekHigh;

    @DecimalMin(value = "0.0", message = "52-week low must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 4)
    @Column(name = "fifty_two_week_low", precision = 21, scale = 4)
    private BigDecimal fiftyTwoWeekLow;

    @Column
    private Long volume;

    @Column(name = "average_volume")
    private Long averageVolume;

    @DecimalMin(value = "0.0", message = "Market cap must be greater than or equal to 0")
    @Digits(integer = 20, fraction = 2)
    @Column(precision = 22, scale = 2)
    private BigDecimal marketCap;

    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal beta;

    @Digits(integer = 10, fraction = 4)
    @Column(precision = 14, scale = 4)
    private BigDecimal priceChange;

    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal priceChangePercent;

    @Digits(integer = 10, fraction = 4)
    @Column(name = "earnings_per_share", precision = 14, scale = 4)
    private BigDecimal earningsPerShare;

    @DecimalMin(value = "0.0", message = "P/E must be greater than or equal to 0")
    @Digits(integer = 8, fraction = 4)
    @Column(name = "price_to_earnings", precision = 12, scale = 4)
    private BigDecimal priceToEarnings;

    @DecimalMin(value = "0.0", message = "P/B must be greater than or equal to 0")
    @Digits(integer = 8, fraction = 4)
    @Column(name = "price_to_book", precision = 12, scale = 4)
    private BigDecimal priceToBook;

    @DecimalMin(value = "0.0", message = "Dividend yield must be >= 0")
    @DecimalMax(value = "1000.0", message = "Dividend yield must be <= 1000")
    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal dividendYield;

    @DecimalMin(value = "0.0", message = "Dividend per share must be >= 0")
    @Digits(integer = 10, fraction = 4)
    @Column(precision = 14, scale = 4)
    private BigDecimal dividendPerShare;

    @DecimalMin(value = "0.0", message = "Payout ratio must be >= 0")
    @DecimalMax(value = "1000.0", message = "Payout ratio must be <= 1000")
    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal payoutRatio;

    @Digits(integer = 20, fraction = 2)
    @Column(precision = 22, scale = 2)
    private BigDecimal revenue;

    @Digits(integer = 20, fraction = 2)
    @Column(precision = 22, scale = 2)
    private BigDecimal netIncome;

    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal operatingMargin;

    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal profitMargin;

    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal returnOnEquity;

    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal returnOnAssets;

    @Digits(integer = 8, fraction = 4)
    @Column(precision = 12, scale = 4)
    private BigDecimal debtToEquity;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CompanyType companyType;

    @Column(length = 2000)
    private String boardMembers;

    @Column(length = 2000)
    private String executiveTeam;

    @Column(length = 2000)
    private String majorShareholders;

    @Column(length = 2000)
    private String ownershipStructure;

    @DecimalMin(value = "0.0", message = "Corporate governance score must be >= 0")
    @DecimalMax(value = "100.0", message = "Corporate governance score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal corporateGovernanceScore;

    @Column(length = 2000)
    private String regulatoryFlags;

    @Column(length = 2000)
    private String dividendAnnouncements;

    @Column(length = 2000)
    private String earningsReports;

    @Column(length = 2000)
    private String stockSplits;

    @Column(length = 2000)
    private String mergersAcquisitions;

    @Column(length = 2000)
    private String spinOffs;

    @Column(length = 2000)
    private String shareBuybacks;

    @Column(length = 2000)
    private String guidanceUpdates;

    @Column(length = 4000)
    private String notableNewsEvents;

    @Column(length = 2000)
    private String industryTrends;

    @Column(length = 2000)
    private String macroeconomicIndicators;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CompetitivePosition competitivePosition;

    @Column(length = 2000)
    private String geopoliticalExposure;

    @Column(length = 2000)
    private String supplyChainRisks;

    @DecimalMin(value = "0.0", message = "Volatility score must be >= 0")
    @DecimalMax(value = "100.0", message = "Volatility score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal volatilityScore;

    @DecimalMin(value = "0.0", message = "Liquidity risk score must be >= 0")
    @DecimalMax(value = "100.0", message = "Liquidity risk score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal liquidityRiskScore;

    @DecimalMin(value = "0.0", message = "Financial health score must be >= 0")
    @DecimalMax(value = "100.0", message = "Financial health score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal financialHealthScore;

    @DecimalMin(value = "0.0", message = "Market risk score must be >= 0")
    @DecimalMax(value = "100.0", message = "Market risk score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal marketRiskScore;

    @DecimalMin(value = "0.0", message = "Governance risk score must be >= 0")
    @DecimalMax(value = "100.0", message = "Governance risk score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal governanceRiskScore;

    @DecimalMin(value = "0.0", message = "Overall assessment score must be >= 0")
    @DecimalMax(value = "100.0", message = "Overall assessment score must be <= 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal overallAssessmentScore;

    @Column(length = 2000)
    private String complianceFlags;

    @Column(length = 2000)
    private String financialReports;

    @Column(length = 2000)
    private String analystReports;

    @Column(length = 2000)
    private String newsReferences;

    @Column(length = 2000)
    private String regulatoryFilings;

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

    @AssertTrue(message = "Day high must be greater than or equal to day low")
    public boolean isDayRangeValid() {
        return dayHigh == null || dayLow == null || dayHigh.compareTo(dayLow) >= 0;
    }

    @AssertTrue(message = "52-week high must be greater than or equal to 52-week low")
    public boolean isFiftyTwoWeekRangeValid() {
        return fiftyTwoWeekHigh == null || fiftyTwoWeekLow == null || fiftyTwoWeekHigh.compareTo(fiftyTwoWeekLow) >= 0;
    }

    @AssertTrue(message = "Current price must be between day low and day high")
    public boolean isCurrentPriceInsideDayRange() {
        if (currentPrice == null || dayLow == null || dayHigh == null) {
            return true;
        }
        return currentPrice.compareTo(dayLow) >= 0 && currentPrice.compareTo(dayHigh) <= 0;
    }

    public String getTickerSymbol() { return tickerSymbol; }
    public void setTickerSymbol(String tickerSymbol) { this.tickerSymbol = tickerSymbol; }
    public String getExchange() { return exchange; }
    public void setExchange(String exchange) { this.exchange = exchange; }
    public String getIsin() { return isin; }
    public void setIsin(String isin) { this.isin = isin; }
    public String getCusip() { return cusip; }
    public void setCusip(String cusip) { this.cusip = cusip; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    public String getCountryOfListing() { return countryOfListing; }
    public void setCountryOfListing(String countryOfListing) { this.countryOfListing = countryOfListing; }
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }
    public BigDecimal getOpenPrice() { return openPrice; }
    public void setOpenPrice(BigDecimal openPrice) { this.openPrice = openPrice; }
    public BigDecimal getClosePrice() { return closePrice; }
    public void setClosePrice(BigDecimal closePrice) { this.closePrice = closePrice; }
    public BigDecimal getDayHigh() { return dayHigh; }
    public void setDayHigh(BigDecimal dayHigh) { this.dayHigh = dayHigh; }
    public BigDecimal getDayLow() { return dayLow; }
    public void setDayLow(BigDecimal dayLow) { this.dayLow = dayLow; }
    public BigDecimal getFiftyTwoWeekHigh() { return fiftyTwoWeekHigh; }
    public void setFiftyTwoWeekHigh(BigDecimal fiftyTwoWeekHigh) { this.fiftyTwoWeekHigh = fiftyTwoWeekHigh; }
    public BigDecimal getFiftyTwoWeekLow() { return fiftyTwoWeekLow; }
    public void setFiftyTwoWeekLow(BigDecimal fiftyTwoWeekLow) { this.fiftyTwoWeekLow = fiftyTwoWeekLow; }
    public Long getVolume() { return volume; }
    public void setVolume(Long volume) { this.volume = volume; }
    public Long getAverageVolume() { return averageVolume; }
    public void setAverageVolume(Long averageVolume) { this.averageVolume = averageVolume; }
    public BigDecimal getMarketCap() { return marketCap; }
    public void setMarketCap(BigDecimal marketCap) { this.marketCap = marketCap; }
    public BigDecimal getBeta() { return beta; }
    public void setBeta(BigDecimal beta) { this.beta = beta; }
    public BigDecimal getPriceChange() { return priceChange; }
    public void setPriceChange(BigDecimal priceChange) { this.priceChange = priceChange; }
    public BigDecimal getPriceChangePercent() { return priceChangePercent; }
    public void setPriceChangePercent(BigDecimal priceChangePercent) { this.priceChangePercent = priceChangePercent; }
    public BigDecimal getEarningsPerShare() { return earningsPerShare; }
    public void setEarningsPerShare(BigDecimal earningsPerShare) { this.earningsPerShare = earningsPerShare; }
    public BigDecimal getPriceToEarnings() { return priceToEarnings; }
    public void setPriceToEarnings(BigDecimal priceToEarnings) { this.priceToEarnings = priceToEarnings; }
    public BigDecimal getPriceToBook() { return priceToBook; }
    public void setPriceToBook(BigDecimal priceToBook) { this.priceToBook = priceToBook; }
    public BigDecimal getDividendYield() { return dividendYield; }
    public void setDividendYield(BigDecimal dividendYield) { this.dividendYield = dividendYield; }
    public BigDecimal getDividendPerShare() { return dividendPerShare; }
    public void setDividendPerShare(BigDecimal dividendPerShare) { this.dividendPerShare = dividendPerShare; }
    public BigDecimal getPayoutRatio() { return payoutRatio; }
    public void setPayoutRatio(BigDecimal payoutRatio) { this.payoutRatio = payoutRatio; }
    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }
    public BigDecimal getNetIncome() { return netIncome; }
    public void setNetIncome(BigDecimal netIncome) { this.netIncome = netIncome; }
    public BigDecimal getOperatingMargin() { return operatingMargin; }
    public void setOperatingMargin(BigDecimal operatingMargin) { this.operatingMargin = operatingMargin; }
    public BigDecimal getProfitMargin() { return profitMargin; }
    public void setProfitMargin(BigDecimal profitMargin) { this.profitMargin = profitMargin; }
    public BigDecimal getReturnOnEquity() { return returnOnEquity; }
    public void setReturnOnEquity(BigDecimal returnOnEquity) { this.returnOnEquity = returnOnEquity; }
    public BigDecimal getReturnOnAssets() { return returnOnAssets; }
    public void setReturnOnAssets(BigDecimal returnOnAssets) { this.returnOnAssets = returnOnAssets; }
    public BigDecimal getDebtToEquity() { return debtToEquity; }
    public void setDebtToEquity(BigDecimal debtToEquity) { this.debtToEquity = debtToEquity; }
    public CompanyType getCompanyType() { return companyType; }
    public void setCompanyType(CompanyType companyType) { this.companyType = companyType; }
    public String getBoardMembers() { return boardMembers; }
    public void setBoardMembers(String boardMembers) { this.boardMembers = boardMembers; }
    public String getExecutiveTeam() { return executiveTeam; }
    public void setExecutiveTeam(String executiveTeam) { this.executiveTeam = executiveTeam; }
    public String getMajorShareholders() { return majorShareholders; }
    public void setMajorShareholders(String majorShareholders) { this.majorShareholders = majorShareholders; }
    public String getOwnershipStructure() { return ownershipStructure; }
    public void setOwnershipStructure(String ownershipStructure) { this.ownershipStructure = ownershipStructure; }
    public BigDecimal getCorporateGovernanceScore() { return corporateGovernanceScore; }
    public void setCorporateGovernanceScore(BigDecimal corporateGovernanceScore) { this.corporateGovernanceScore = corporateGovernanceScore; }
    public String getRegulatoryFlags() { return regulatoryFlags; }
    public void setRegulatoryFlags(String regulatoryFlags) { this.regulatoryFlags = regulatoryFlags; }
    public String getDividendAnnouncements() { return dividendAnnouncements; }
    public void setDividendAnnouncements(String dividendAnnouncements) { this.dividendAnnouncements = dividendAnnouncements; }
    public String getEarningsReports() { return earningsReports; }
    public void setEarningsReports(String earningsReports) { this.earningsReports = earningsReports; }
    public String getStockSplits() { return stockSplits; }
    public void setStockSplits(String stockSplits) { this.stockSplits = stockSplits; }
    public String getMergersAcquisitions() { return mergersAcquisitions; }
    public void setMergersAcquisitions(String mergersAcquisitions) { this.mergersAcquisitions = mergersAcquisitions; }
    public String getSpinOffs() { return spinOffs; }
    public void setSpinOffs(String spinOffs) { this.spinOffs = spinOffs; }
    public String getShareBuybacks() { return shareBuybacks; }
    public void setShareBuybacks(String shareBuybacks) { this.shareBuybacks = shareBuybacks; }
    public String getGuidanceUpdates() { return guidanceUpdates; }
    public void setGuidanceUpdates(String guidanceUpdates) { this.guidanceUpdates = guidanceUpdates; }
    public String getNotableNewsEvents() { return notableNewsEvents; }
    public void setNotableNewsEvents(String notableNewsEvents) { this.notableNewsEvents = notableNewsEvents; }
    public String getIndustryTrends() { return industryTrends; }
    public void setIndustryTrends(String industryTrends) { this.industryTrends = industryTrends; }
    public String getMacroeconomicIndicators() { return macroeconomicIndicators; }
    public void setMacroeconomicIndicators(String macroeconomicIndicators) { this.macroeconomicIndicators = macroeconomicIndicators; }
    public CompetitivePosition getCompetitivePosition() { return competitivePosition; }
    public void setCompetitivePosition(CompetitivePosition competitivePosition) { this.competitivePosition = competitivePosition; }
    public String getGeopoliticalExposure() { return geopoliticalExposure; }
    public void setGeopoliticalExposure(String geopoliticalExposure) { this.geopoliticalExposure = geopoliticalExposure; }
    public String getSupplyChainRisks() { return supplyChainRisks; }
    public void setSupplyChainRisks(String supplyChainRisks) { this.supplyChainRisks = supplyChainRisks; }
    public BigDecimal getVolatilityScore() { return volatilityScore; }
    public void setVolatilityScore(BigDecimal volatilityScore) { this.volatilityScore = volatilityScore; }
    public BigDecimal getLiquidityRiskScore() { return liquidityRiskScore; }
    public void setLiquidityRiskScore(BigDecimal liquidityRiskScore) { this.liquidityRiskScore = liquidityRiskScore; }
    public BigDecimal getFinancialHealthScore() { return financialHealthScore; }
    public void setFinancialHealthScore(BigDecimal financialHealthScore) { this.financialHealthScore = financialHealthScore; }
    public BigDecimal getMarketRiskScore() { return marketRiskScore; }
    public void setMarketRiskScore(BigDecimal marketRiskScore) { this.marketRiskScore = marketRiskScore; }
    public BigDecimal getGovernanceRiskScore() { return governanceRiskScore; }
    public void setGovernanceRiskScore(BigDecimal governanceRiskScore) { this.governanceRiskScore = governanceRiskScore; }
    public BigDecimal getOverallAssessmentScore() { return overallAssessmentScore; }
    public void setOverallAssessmentScore(BigDecimal overallAssessmentScore) { this.overallAssessmentScore = overallAssessmentScore; }
    public String getComplianceFlags() { return complianceFlags; }
    public void setComplianceFlags(String complianceFlags) { this.complianceFlags = complianceFlags; }
    public String getFinancialReports() { return financialReports; }
    public void setFinancialReports(String financialReports) { this.financialReports = financialReports; }
    public String getAnalystReports() { return analystReports; }
    public void setAnalystReports(String analystReports) { this.analystReports = analystReports; }
    public String getNewsReferences() { return newsReferences; }
    public void setNewsReferences(String newsReferences) { this.newsReferences = newsReferences; }
    public String getRegulatoryFilings() { return regulatoryFilings; }
    public void setRegulatoryFilings(String regulatoryFilings) { this.regulatoryFilings = regulatoryFilings; }
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
