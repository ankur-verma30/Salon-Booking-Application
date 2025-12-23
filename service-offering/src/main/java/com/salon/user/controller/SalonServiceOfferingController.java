package com.salon.user.controller;

import com.salon.user.dto.CategoryDTO;
import com.salon.user.dto.SalonDTO;
import com.salon.user.dto.ServiceDTO;
import com.salon.user.entity.ServiceOffering;
import com.salon.user.service.ServiceOfferingService;
import com.salon.user.service.client.CategoryFeignClient;
import com.salon.user.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-offerings/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;
    private final SalonFeignClient salonFeignClient;
    private final CategoryFeignClient categoryFeignClient;

    @PostMapping
    public ResponseEntity<ServiceOffering> createService(@RequestBody ServiceDTO serviceDTO,
                                                         @RequestHeader("Authorization")String jwt) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        CategoryDTO categoryDTO = categoryFeignClient.getCategoriesByIdAndSalon(serviceDTO.getCategory(),salonDTO.getId()).getBody();


        ServiceOffering serviceOffering = serviceOfferingService.createService(salonDTO,serviceDTO,categoryDTO);
        return ResponseEntity.ok(serviceOffering);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService(@PathVariable Long id, @RequestBody ServiceOffering serviceOffering) throws Exception {
        ServiceOffering serviceOffering1 = serviceOfferingService.updateService(id, serviceOffering);
        return ResponseEntity.ok(serviceOffering1);
    }

}
