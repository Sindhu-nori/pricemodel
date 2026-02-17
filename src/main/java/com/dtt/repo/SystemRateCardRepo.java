package com.dtt.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dtt.model.ClientProjects;
import com.dtt.model.SystemRateCard;

@Repository
public interface SystemRateCardRepo extends JpaRepository<SystemRateCard, Long> {

	List<SystemRateCard> findByIsActiveTrue();

	//List<SystemRateCard> findByProjectIdAndIsActiveTrue(String projectId);
	
	//List<SystemRateCard> findByProjectIdAndIsActiveTrue(ClientProjects project);
	
	List<SystemRateCard> findByProjectAndIsActiveTrue(ClientProjects project);

	Optional<SystemRateCard> findByServiceCode(String serviceCode);

	@Query("""
			    SELECT rc FROM SystemRateCard rc
			    WHERE rc.isActive = true
			""")
	List<SystemRateCard> findActiveRateCards();

	@Query("""
			    SELECT s FROM SystemRateCard s
			    WHERE s.id = :serviceId
			      AND s.isActive = true
			""")
	Optional<SystemRateCard> findActiveRateCardByServiceId(@Param("serviceId") Long serviceId);

}
