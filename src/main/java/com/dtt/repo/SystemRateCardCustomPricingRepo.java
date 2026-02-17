package com.dtt.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dtt.model.SystemRateCardCustomPricing;

@Repository
public interface SystemRateCardCustomPricingRepo extends JpaRepository<SystemRateCardCustomPricing, Long> {

	@Query("SELECT cp FROM SystemRateCardCustomPricing cp " + "WHERE cp.systemService.id = :serviceId "
			+ "  AND cp.stakeHolderId = :stakeHolderId " + "  AND cp.systemService.isActive = true "
			+ "  AND cp.isActive = true " + "ORDER BY cp.createdOn DESC, cp.updatedOn DESC")
	List<SystemRateCardCustomPricing> findActiveBySystemServiceIdAndStakeHolderId(@Param("serviceId") Long serviceId,
			@Param("stakeHolderId") String stakeHolderId);

	@Query("""
			    SELECT c FROM SystemRateCardCustomPricing c
			    WHERE c.systemService.id = :serviceId
			""")
	List<SystemRateCardCustomPricing> findActiveCustomPricing(@Param("serviceId") Long serviceId);

	boolean existsBySystemServiceIdAndStakeHolderTypeAndStakeHolderId(Long systemServiceId, String stakeHolderType,
			String stakeHolderId);

	List<SystemRateCardCustomPricing> findBySystemServiceId(Long systemServiceId);

	List<SystemRateCardCustomPricing> findByStakeHolderId(String stakeHolderId);

	@Query("""
			    SELECT scp
			    FROM SystemRateCardCustomPricing scp
			    JOIN scp.systemService s
			    WHERE scp.isActive = true
			      AND (:organizationId IS NULL OR scp.stakeHolderId = :organizationId)
			      AND (:serviceName IS NULL OR LOWER(s.serviceName) LIKE LOWER(CONCAT('%', :serviceName, '%')))
			    ORDER BY s.serviceName ASC
			""")
	Page<SystemRateCardCustomPricing> searchCustomPricing(@Param("organizationId") String organizationId,
			@Param("serviceName") String serviceName, Pageable pageable);
}
