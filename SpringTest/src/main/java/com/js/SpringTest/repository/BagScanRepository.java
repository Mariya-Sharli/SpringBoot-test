package com.js.SpringTest.repository;

import com.js.SpringTest.model.BagScan;
import com.js.SpringTest.dto.GateBagCountProjection;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface BagScanRepository extends JpaRepository<BagScan, Long> {

    List<BagScan> findByDestinationGateOrderByScanTimeDesc(String gate);

    List<BagScan> findByBagTagIdOrderByScanTimeDesc(String bagTagId);

    @Query("SELECT b FROM BagScan b WHERE b.destinationGate = :gate AND b.scanTime >= :cutoff ORDER BY b.scanTime DESC")
    List<BagScan> findRecentScansByGate(@Param("gate") String gate, @Param("cutoff") LocalDateTime cutoff);

    @Query("SELECT b.destinationGate AS destinationGate, COUNT(DISTINCT b.bagTagId) AS uniqueBagCount FROM BagScan b WHERE b.scanTime >= :cutoff GROUP BY b.destinationGate")
    List<GateBagCountProjection> countUniqueBagsPerGateSince(@Param("cutoff") LocalDateTime cutoff);
}
