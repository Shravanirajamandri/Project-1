package com.infosys.financial;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/segment")
public class SegmentationController {
    
    @PostMapping
    public Map<String, Object> segment(@RequestBody Map<String, Object> request) {
        double assetValue = Double.parseDouble(String.valueOf(request.getOrDefault("assetValue", 0)));
        
        String tier;
        if (assetValue >= 10000000) {
            tier = "SPWS";
        } else if (assetValue >= 1000000) {
            tier = "SPCS/PINN";
        } else if (assetValue >= 250000) {
            tier = "PLAT";
        } else if (assetValue >= 100000) {
            tier = "GOLD";
        } else {
            tier = "PREF";
        }
        
        return Map.of("newTier", tier, "assetValue", assetValue);
    }
}
