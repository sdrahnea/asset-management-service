package com.sdr.ams.repository;

import com.sdr.ams.model.intangible.Reputation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReputationRepository extends JpaRepository<Reputation, Long> {

	@Query("""
		select r
		from Reputation r
		where (:entityId is null or upper(r.entityId) like concat('%', upper(:entityId), '%'))
		  and (:entityType is null or r.entityType = :entityType)
		  and (:trendDirection is null or r.trendDirection = :trendDirection)
		  and (:competitivePosition is null or r.competitivePosition = :competitivePosition)
		order by r.updatedAt desc, r.displayName asc
		""")
	List<Reputation> search(
		@Param("entityId") String entityId,
		@Param("entityType") Reputation.EntityType entityType,
		@Param("trendDirection") Reputation.TrendDirection trendDirection,
		@Param("competitivePosition") Reputation.CompetitivePosition competitivePosition
	);

	@Query("""
		select case when count(r) > 0 then true else false end
		from Reputation r
		where upper(r.name) = upper(:reputationId)
		  and (:excludeId is null or r.id <> :excludeId)
		""")
	boolean existsReputationId(@Param("reputationId") String reputationId, @Param("excludeId") Long excludeId);

	@Query("""
		select case when count(r) > 0 then true else false end
		from Reputation r
		where r.entityType = :entityType
		  and upper(r.entityId) = upper(:entityId)
		  and (:excludeId is null or r.id <> :excludeId)
		""")
	boolean existsEntityReference(
		@Param("entityType") Reputation.EntityType entityType,
		@Param("entityId") String entityId,
		@Param("excludeId") Long excludeId
	);
}
