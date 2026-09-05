package com.infosys.financial;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/validate")
public class ValidationController {
    
    @PostMapping
    public Map<String, Object> validate(@RequestBody Map<String, Object> request) {
        List<String> requiredFields = List.of("customerId", "accountId", "accountType", "status", "assetValue");
        List<String> missingFields = new ArrayList<>();
        
        for (String field : requiredFields) {
            if (!request.containsKey(field)) {
                missingFields.add(field);
            }
        }
        
        boolean isValid = missingFields.isEmpty() && 
                         "OPEN".equalsIgnoreCase(String.valueOf(request.get("status")));
        
        return Map.of(
            "valid", isValid,
            "missingFields", missingFields,
            "message", isValid ? "Validation passed" : "Validation failed"
        );
    }
}
