package com.sdr.ams.repository;

import com.sdr.ams.model.intangible.Trademark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrademarkRepository extends JpaRepository<Trademark, Long> {

    @Query("""
        select t
        from Trademark t
        where (:markType is null or t.markType = :markType)
          and (:legalStatus is null or t.legalStatus = :legalStatus)
          and (:ownerType is null or t.ownerType = :ownerType)
          and (:licensingStatus is null or t.licensingStatus = :licensingStatus)
        order by t.updatedAt desc, t.markName asc
        """)
    List<Trademark> search(
        @Param("markType") Trademark.MarkType markType,
        @Param("legalStatus") Trademark.LegalStatus legalStatus,
        @Param("ownerType") Trademark.OwnerType ownerType,
        @Param("licensingStatus") Trademark.LicensingStatus licensingStatus
    );

    @Query("""
        select case when count(t) > 0 then true else false end
        from Trademark t
        where upper(t.name) = upper(:trademarkId)
          and (:excludeId is null or t.id <> :excludeId)
        """)
    boolean existsTrademarkId(@Param("trademarkId") String trademarkId, @Param("excludeId") Long excludeId);

    @Query("""
        select case when count(t) > 0 then true else false end
        from Trademark t
        where upper(t.applicationNumber) = upper(:applicationNumber)
          and (:excludeId is null or t.id <> :excludeId)
        """)
    boolean existsApplicationNumber(
        @Param("applicationNumber") String applicationNumber,
        @Param("excludeId") Long excludeId
    );

    @Query("""
        select case when count(t) > 0 then true else false end
        from Trademark t
        where t.registrationNumber is not null
          and upper(t.registrationNumber) = upper(:registrationNumber)
          and (:excludeId is null or t.id <> :excludeId)
        """)
    boolean existsRegistrationNumber(
        @Param("registrationNumber") String registrationNumber,
        @Param("excludeId") Long excludeId
    );
}
