package com.infosys.financial;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {
    
    @PostMapping
    public Map<String, Object> notify(@RequestBody Map<String, Object> request) {
        String processingId = String.valueOf(request.getOrDefault("processingId", "N/A"));
        
        return Map.of(
            "status", "SENT",
            "targetSystem", "Transaction Processing System",
            "processingId", processingId
        );
    }
}
