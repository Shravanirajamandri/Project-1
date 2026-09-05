package com.infosys.financial;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    
    private final EnrollmentRepository repo;
    
    public EnrollmentController(EnrollmentRepository repo) {
        this.repo = repo;
    }
    
    @PostMapping
    public Map<String, Object> enroll(@RequestBody Map<String, Object> request) {
        Enrollment enrollment = new Enrollment();
        enrollment.setProcessingId(String.valueOf(request.getOrDefault("processingId", "PROC-MANUAL")));
        enrollment.setCustomerId(String.valueOf(request.getOrDefault("customerId", "UNKNOWN")));
        enrollment.setAccountId(String.valueOf(request.getOrDefault("accountId", "UNKNOWN")));
        enrollment.setCurrentTier(String.valueOf(request.getOrDefault("currentTier", "PREF")));
        enrollment.setNewTier(String.valueOf(request.getOrDefault("newTier", "PREF")));
        
        // Determine action
        String action;
        if (enrollment.getCurrentTier().equals(enrollment.getNewTier())) {
            action = "NO_CHANGE";
        } else if (enrollment.getCurrentTier().equals("GOLD") && enrollment.getNewTier().equals("PLAT")) {
            action = "UPTIER";
        } else {
            action = "UPDATE";
        }
        enrollment.setAction(action);
        
        repo.save(enrollment);
        
        return Map.of(
            "processingId", enrollment.getProcessingId(),
            "status", "ENROLLED",
            "action", enrollment.getAction(),
            "newTier", enrollment.getNewTier()
        );
    }
    
    @GetMapping("/{processingId}")
    public Object get(@PathVariable String processingId) {
        return repo.findByProcessingId(processingId)
            .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + processingId));
    }
}
