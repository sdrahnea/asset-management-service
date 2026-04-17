package com.sdr.ams.repository;

import com.sdr.ams.model.tangible.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("select v from Vehicle v where v.inspectionExpiryDate is not null and v.inspectionExpiryDate <= :cutoff order by v.inspectionExpiryDate asc")
    List<Vehicle> findInspectionDueBefore(@Param("cutoff") LocalDate cutoff);

    @Query("""
        select v
        from Vehicle v
        where (:vehicleType is null or v.vehicleType = :vehicleType)
          and (:registrationStatus is null or v.registrationStatus = :registrationStatus)
          and (:ownershipType is null or v.ownershipType = :ownershipType)
        order by v.updatedAt desc, v.name asc
        """)
    List<Vehicle> search(
        @Param("vehicleType") Vehicle.VehicleType vehicleType,
        @Param("registrationStatus") Vehicle.RegistrationStatus registrationStatus,
        @Param("ownershipType") Vehicle.OwnershipType ownershipType
    );

    @Query("""
        select case when count(v) > 0 then true else false end
        from Vehicle v
        where upper(v.name) = upper(:vehicleId)
          and (:excludeId is null or v.id <> :excludeId)
        """)
    boolean existsVehicleId(@Param("vehicleId") String vehicleId, @Param("excludeId") Long excludeId);

    @Query("""
        select case when count(v) > 0 then true else false end
        from Vehicle v
        where upper(v.vin) = upper(:vin)
          and (:excludeId is null or v.id <> :excludeId)
        """)
    boolean existsVin(@Param("vin") String vin, @Param("excludeId") Long excludeId);

    @Query("""
        select case when count(v) > 0 then true else false end
        from Vehicle v
        where upper(v.licensePlate) = upper(:licensePlate)
          and upper(coalesce(v.registrationCountry, '')) = upper(coalesce(:registrationCountry, ''))
          and (:excludeId is null or v.id <> :excludeId)
        """)
    boolean existsLicensePlateInCountry(
        @Param("licensePlate") String licensePlate,
        @Param("registrationCountry") String registrationCountry,
        @Param("excludeId") Long excludeId
    );
}
