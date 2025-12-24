package com.salon.offerings.controller;

import com.salon.offerings.entity.ServiceOffering;
import com.salon.offerings.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offerings")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServicesBySalonId(@PathVariable Long salonId, @RequestParam(required = false) Long categoryId){
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySalonId(salonId, categoryId);
        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceById(@PathVariable Long id) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingService.getServiceById(id);
        return ResponseEntity.ok(serviceOffering);
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServicesByIds(@PathVariable Set<Long> ids){
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getServicesByIds(ids);
        return ResponseEntity.ok(serviceOfferings);
    }


}
