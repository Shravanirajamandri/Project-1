package com.infosys.financial;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityController {
    
    @PostMapping
    public Map<String, Object> eligibility(@RequestBody Map<String, Object> request) {
        double assets = Double.parseDouble(String.valueOf(request.getOrDefault("assetValue", 0)));
        String status = String.valueOf(request.getOrDefault("status", ""));
        
        boolean eligible = assets >= 100000 && "OPEN".equalsIgnoreCase(status);
        
        return Map.of(
            "eligible", eligible,
            "reason", eligible ? "Meets asset and account-status criteria" : "Does not meet eligibility criteria"
        );
    }
}
