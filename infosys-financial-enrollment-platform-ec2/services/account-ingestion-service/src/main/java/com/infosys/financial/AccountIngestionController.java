package com.infosys.financial;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
@RestController
@RequestMapping("/api/accounts")
public class AccountIngestionController {
 private final StringRedisTemplate redis;
 public AccountIngestionController(StringRedisTemplate redis){this.redis=redis;}
 @PostMapping
 public ResponseEntity<Map<String,Object>> ingest(@RequestBody Map<String,Object> request){
  String id="PROC-"+String.format("%08d",ThreadLocalRandom.current().nextInt(100000000));
  Map<String,Object> response=new LinkedHashMap<>(); response.put("processingId",id); response.put("status","RECEIVED"); response.put("nextService","data-validation-service");
  redis.opsForValue().set("account:"+id, "RECEIVED", Duration.ofMinutes(30));
  redis.opsForHash().putAll("account-data:"+id, toStringMap(request)); redis.expire("account-data:"+id,Duration.ofMinutes(30));
  return ResponseEntity.accepted().body(response);
 }
 @GetMapping("/{processingId}/status")
 public Map<String,Object> status(@PathVariable String processingId){Object s=redis.opsForValue().get("account:"+processingId); return Map.of("processingId",processingId,"status",s==null?"NOT_FOUND":s);}
 private Map<String,String> toStringMap(Map<String,Object> m){Map<String,String> x=new HashMap<>();m.forEach((k,v)->x.put(k,String.valueOf(v)));return x;}
}
