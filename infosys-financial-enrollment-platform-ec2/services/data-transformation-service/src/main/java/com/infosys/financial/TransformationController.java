package com.infosys.financial;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/transform")
public class TransformationController {
    
    @PostMapping
    public Map<String, Object> transform(@RequestBody Map<String, Object> request) {
        String tier = String.valueOf(request.getOrDefault("newTier", "PREF"));
        
        Map<String, String> tierCodes = Map.of(
            "SPWS", "01",
            "SPCS/PINN", "02",
            "PLAT", "03",
            "GOLD", "04",
            "PREF", "05"
        );
        
        return Map.of(
            "tier", tier,
            "tierCode", tierCodes.getOrDefault(tier, "05"),
            "transformed", true
        );
    }
}
