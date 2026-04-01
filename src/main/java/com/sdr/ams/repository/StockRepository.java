package com.sdr.ams.repository;

import com.sdr.ams.model.financial.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("""
        select s
        from Stock s
        where (:tickerSymbol is null or upper(s.tickerSymbol) like concat('%', upper(:tickerSymbol), '%'))
          and (:exchange is null or upper(s.exchange) = upper(:exchange))
          and (:sector is null or upper(s.sector) = upper(:sector))
          and (:companyType is null or s.companyType = :companyType)
        order by s.updatedAt desc, s.companyName asc
        """)
    List<Stock> search(
        @Param("tickerSymbol") String tickerSymbol,
        @Param("exchange") String exchange,
        @Param("sector") String sector,
        @Param("companyType") Stock.CompanyType companyType
    );

    @Query("""
        select case when count(s) > 0 then true else false end
        from Stock s
        where upper(s.name) = upper(:stockId)
          and (:excludeId is null or s.id <> :excludeId)
        """)
    boolean existsStockId(@Param("stockId") String stockId, @Param("excludeId") Long excludeId);

    @Query("""
        select case when count(s) > 0 then true else false end
        from Stock s
        where upper(s.tickerSymbol) = upper(:tickerSymbol)
          and upper(s.exchange) = upper(:exchange)
          and (:excludeId is null or s.id <> :excludeId)
        """)
    boolean existsTickerSymbolOnExchange(
        @Param("tickerSymbol") String tickerSymbol,
        @Param("exchange") String exchange,
        @Param("excludeId") Long excludeId
    );

    @Query("""
        select case when count(s) > 0 then true else false end
        from Stock s
        where s.isin is not null
          and upper(s.isin) = upper(:isin)
          and (:excludeId is null or s.id <> :excludeId)
        """)
    boolean existsIsin(@Param("isin") String isin, @Param("excludeId") Long excludeId);

    @Query("""
        select case when count(s) > 0 then true else false end
        from Stock s
        where s.cusip is not null
          and upper(s.cusip) = upper(:cusip)
          and (:excludeId is null or s.id <> :excludeId)
        """)
    boolean existsCusip(@Param("cusip") String cusip, @Param("excludeId") Long excludeId);
}
