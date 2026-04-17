package com.sdr.ams.repository;

import com.sdr.ams.model.intangible.Patent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PatentRepository extends JpaRepository<Patent, Long> {

    @Query("select p from Patent p where p.expiryDate is not null and p.expiryDate <= :cutoff order by p.expiryDate asc")
    List<Patent> findExpiringBefore(@Param("cutoff") LocalDate cutoff);

    @Query("""
        select p
        from Patent p
        where (:patentType is null or p.patentType = :patentType)
          and (:legalStatus is null or p.legalStatus = :legalStatus)
          and (:technologyField is null or upper(p.technologyField) like concat('%', upper(:technologyField), '%'))
          and (:assigneeOwner is null or upper(p.assigneeOwner) like concat('%', upper(:assigneeOwner), '%'))
        order by p.updatedAt desc, p.title asc
        """)
    List<Patent> search(
        @Param("patentType") Patent.PatentType patentType,
        @Param("legalStatus") Patent.LegalStatus legalStatus,
        @Param("technologyField") String technologyField,
        @Param("assigneeOwner") String assigneeOwner
    );

    @Query("""
        select case when count(p) > 0 then true else false end
        from Patent p
        where upper(p.name) = upper(:patentId)
          and (:excludeId is null or p.id <> :excludeId)
        """)
    boolean existsPatentId(@Param("patentId") String patentId, @Param("excludeId") Long excludeId);

    @Query("""
        select case when count(p) > 0 then true else false end
        from Patent p
        where upper(p.applicationNumber) = upper(:applicationNumber)
          and (:excludeId is null or p.id <> :excludeId)
        """)
    boolean existsApplicationNumber(@Param("applicationNumber") String applicationNumber, @Param("excludeId") Long excludeId);

    @Query("""
        select case when count(p) > 0 then true else false end
        from Patent p
        where p.publicationNumber is not null
          and upper(p.publicationNumber) = upper(:publicationNumber)
          and (:excludeId is null or p.id <> :excludeId)
        """)
    boolean existsPublicationNumber(@Param("publicationNumber") String publicationNumber, @Param("excludeId") Long excludeId);

    @Query("""
        select case when count(p) > 0 then true else false end
        from Patent p
        where p.grantNumber is not null
          and upper(p.grantNumber) = upper(:grantNumber)
          and (:excludeId is null or p.id <> :excludeId)
        """)
    boolean existsGrantNumber(@Param("grantNumber") String grantNumber, @Param("excludeId") Long excludeId);
}
