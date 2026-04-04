package com.sdr.ams.repository;

import com.sdr.ams.model.financial.Bond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BondRepository extends JpaRepository<Bond, Long> {

    @Query("""
        select b
        from Bond b
        where (:issuer is null or upper(b.issuer) like concat('%', upper(:issuer), '%'))
          and (:bondType is null or b.bondType = :bondType)
          and (:tradingStatus is null or b.tradingStatus = :tradingStatus)
          and (:currency is null or upper(b.currency) = upper(:currency))
        order by b.updatedAt desc, b.title asc
        """)
    List<Bond> search(
        @Param("issuer") String issuer,
        @Param("bondType") Bond.BondType bondType,
        @Param("tradingStatus") Bond.TradingStatus tradingStatus,
        @Param("currency") String currency
    );

    @Query("""
        select case when count(b) > 0 then true else false end
        from Bond b
        where upper(b.name) = upper(:bondId)
          and (:excludeId is null or b.id <> :excludeId)
        """)
    boolean existsBondId(@Param("bondId") String bondId, @Param("excludeId") Long excludeId);

    @Query("""
        select case when count(b) > 0 then true else false end
        from Bond b
        where b.isin is not null
          and upper(b.isin) = upper(:isin)
          and (:excludeId is null or b.id <> :excludeId)
        """)
    boolean existsIsin(@Param("isin") String isin, @Param("excludeId") Long excludeId);

    @Query("""
        select case when count(b) > 0 then true else false end
        from Bond b
        where b.cusipSedol is not null
          and upper(b.cusipSedol) = upper(:cusipSedol)
          and (:excludeId is null or b.id <> :excludeId)
        """)
    boolean existsCusipSedol(@Param("cusipSedol") String cusipSedol, @Param("excludeId") Long excludeId);
}
