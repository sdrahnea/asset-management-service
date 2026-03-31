package com.sdr.ams.repository;
import java.time.LocalDate;
import com.sdr.ams.model.tangible.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {
    @Query("""
        select r
        from RealEstate r
        where (:propertyType is null or r.propertyType = :propertyType)
          and (:ownershipType is null or r.ownershipType = :ownershipType)
          and (:neighborhoodType is null or r.neighborhoodType = :neighborhoodType)
          and (:valuationDate is null or r.valuationDate = :valuationDate)
          and (:maintenanceStatus is null or r.maintenanceStatus = :maintenanceStatus)
        order by r.valuationDate desc, r.name asc
        """)
    java.util.List<RealEstate> search(
        @Param("propertyType") RealEstate.PropertyType propertyType,
        @Param("ownershipType") RealEstate.OwnershipType ownershipType,
        @Param("neighborhoodType") RealEstate.NeighborhoodType neighborhoodType,
        @Param("valuationDate") LocalDate valuationDate,
        @Param("maintenanceStatus") RealEstate.MaintenanceStatus maintenanceStatus
    );
    @Query("""
        select case when count(r) > 0 then true else false end
        from RealEstate r
        where upper(r.cadastralNumber) = upper(:cadastralNumber)
          and (:excludeId is null or r.id <> :excludeId)
        """)
    boolean existsCadastralNumber(@Param("cadastralNumber") String cadastralNumber, @Param("excludeId") Long excludeId);
    @Query("""
        select case when count(r) > 0 then true else false end
        from RealEstate r
        where upper(r.landRegistryNumber) = upper(:landRegistryNumber)
          and (:excludeId is null or r.id <> :excludeId)
        """)
    boolean existsLandRegistryNumber(@Param("landRegistryNumber") String landRegistryNumber, @Param("excludeId") Long excludeId);
}
