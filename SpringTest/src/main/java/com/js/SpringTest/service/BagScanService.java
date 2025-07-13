package com.js.SpringTest.service;

import com.js.SpringTest.model.BagScan;
import com.js.SpringTest.repository.BagScanRepository;
import com.js.SpringTest.dto.GateBagCountProjection;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BagScanService {

    private final BagScanRepository repository;

    public BagScanService(BagScanRepository repository) {
        this.repository = repository;
    }

    public BagScan save(BagScan scan) {
        return repository.save(scan);
    }

    public List<BagScan> getScansByGate(String gate) {
        return repository.findByDestinationGateOrderByScanTimeDesc(gate);
    }

    public Optional<BagScan> getLatestScanByBag(String bagId) {
        return repository.findByBagTagIdOrderByScanTimeDesc(bagId).stream().findFirst();
    }

    public List<Map<String, Object>> getUniqueBagsInLastNMinutes(String gate, int minutes) {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(minutes);
        List<BagScan> recentScans = repository.findRecentScansByGate(gate, cutoff);

        // ✅ THIS LINE IS IMPORTANT
        Map<String, BagScan> latestScanPerBag = new LinkedHashMap<>();

        for (BagScan scan : recentScans) {
            latestScanPerBag.putIfAbsent(scan.getBagTagId(), scan);
        }

        return latestScanPerBag.values().stream()
//            .map(scan -> Map.of(
//                "bag_tag_id", scan.getBagTagId(),
//                "last_scan_at", scan.getScanTime(),
//                "last_location", scan.getLocationScanned()
//            ))
//            .collect(Collectors.toList());
//    }

//    public List<Map<String, Object>> getBagCountsPerGate(int minutes) {
//        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(minutes);
//        List<GateBagCountProjection> stats = repository.countUniqueBagsPerGateSince(cutoff);
//
//        return stats.stream()
//            .map(row -> Map.of(
//                "destination_gate", row.getDestinationGate(),
//                "unique_bag_count", row.getUniqueBagCount()
//            ))
//            .collect(Collectors.toList());
//    }
//}