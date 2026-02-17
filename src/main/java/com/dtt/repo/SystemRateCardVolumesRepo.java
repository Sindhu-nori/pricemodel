package com.dtt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dtt.model.SystemRateCardVolumes;

import jakarta.transaction.Transactional;

@Repository
public interface SystemRateCardVolumesRepo extends JpaRepository<SystemRateCardVolumes, Long> {

    @Query("""
        SELECT r FROM SystemRateCardVolumes r
        WHERE r.systemService.id = :serviceId
          AND r.systemService.isActive = true
        ORDER BY r.minVolume ASC
    """)
    List<SystemRateCardVolumes> findActiveAndSystemServiceId(@Param("serviceId") Long serviceId);

//    @Query("""
//        SELECT v FROM SystemRateCardVolumes v
//        WHERE v.systemService.id = :serviceId
//        ORDER BY v.minVolume ASC
//    """)
//    List<SystemRateCardVolumes> findVolumes(@Param("serviceId") Long serviceId);

    @Query("""
    SELECT v FROM SystemRateCardVolumes v
    WHERE v.systemService.id = :serviceId
      AND v.status = 'ACTIVE'
    ORDER BY v.minVolume ASC
""")
    List<SystemRateCardVolumes> findActiveVolumes(@Param("serviceId") Long serviceId);


    @Modifying
    @Transactional
    @Query("""
        DELETE FROM SystemRateCardVolumes v
        WHERE v.systemService.id = :serviceId
    """)
    int deleteBySystemServiceId(@Param("serviceId") Long serviceId);


    @Modifying
    @Transactional
    @Query("""
    UPDATE SystemRateCardVolumes v
    SET v.status = :status,
        v.updatedOn = CURRENT_TIMESTAMP
    WHERE v.systemService.id = :serviceId
""")
    int updateStatusBySystemServiceId(@Param("serviceId") Long serviceId,
                                      @Param("status") String status);


    // ✅ Correct derived query
   // List<SystemRateCardVolumes> findBySystemService_IdOrderByMinVolumeAsc(Long serviceId);
}

