package com.dtt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dtt.model.PaymentHistory;

@Repository
public interface PaymentHistoryRepo extends JpaRepository<PaymentHistory, Long> {

	@Query("""
			SELECT p FROM PaymentHistory p
			WHERE p.status IN ('SUCCESS', 'FAILED', 'IN_PROGRESS')
			""")
	List<PaymentHistory> findFinalAndInProgressPayments();

	// Get all records for an organization
	//List<PaymentHistory> findByOrganizationId(String organizationId);

	@Query("""
			    SELECT p FROM PaymentHistory p
			    WHERE p.organizationId = :organizationId
			    AND p.status IN ('SUCCESS', 'FAILED')
			""")
	List<PaymentHistory> findSuccessAndFailedByOrganizationId(@Param("organizationId") String organizationId);

	@Query("""
			SELECT p FROM PaymentHistory p
			WHERE p.status IN ('SUCCESS', 'FAILED')
			""")
	List<PaymentHistory> findPaymentHistorySuccessAndFailed();
}
