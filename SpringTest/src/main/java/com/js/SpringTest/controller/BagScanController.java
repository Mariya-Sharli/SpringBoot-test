package com.js.SpringTest.controller;

import com.js.SpringTest.model.BagScan;
import com.js.SpringTest.service.BagScanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/baggage")
public class BagScanController {

    private final BagScanService service;

    public BagScanController(BagScanService service) {
        this.service = service;
    }

    @PostMapping("/scan")
    public Map<String, Object> logScan(@RequestBody BagScan scan) {
        BagScan saved = service.save(scan);
        return Map.of("scan_internal_id", saved.getId(), "status", "logged");
    }

    @GetMapping("/scans/gate/{gate}")
    public List<BagScan> getScansByGate(@PathVariable String gate) {
        return service.getScansByGate(gate);
    }

    @GetMapping("/scans/bag/{bagId}")
    public ResponseEntity<?> getLatestByBag(@PathVariable String bagId, @RequestParam Optional<Boolean> latest) {
        if (latest.orElse(false)) {
            return service.getLatestScanByBag(bagId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.ok(service.getScansByGate(bagId));
    }

    @GetMapping("/active/gate/{destinationGate}")
    public List<Map<String, Object>> getActiveBagsByGate(@PathVariable String destinationGate,
                                                         @RequestParam(defaultValue = "10") int since_minutes) {
        return service.getUniqueBagsInLastNMinutes(destinationGate, since_minutes);
    }

    @GetMapping("/stats/gate-counts")
    public List<Map<String, Object>> getBagStats(@RequestParam(defaultValue = "10") int since_minutes) {
        return service.getBagCountsPerGate(since_minutes);
    }
}
