package com.salon.offerings.controller;

import com.salon.offerings.dto.CategoryDTO;
import com.salon.offerings.dto.SalonDTO;
import com.salon.offerings.dto.ServiceDTO;
import com.salon.offerings.entity.ServiceOffering;
import com.salon.offerings.service.ServiceOfferingService;
import com.salon.offerings.service.client.CategoryFeignClient;
import com.salon.offerings.service.client.SalonFeignClient;
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

    @PostMapping("/create")
    public ResponseEntity<ServiceOffering> createService(@RequestBody ServiceDTO serviceDTO,
                                                         @RequestHeader("Authorization")String jwt) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        CategoryDTO categoryDTO = categoryFeignClient.getCategoriesByIdAndSalon(serviceDTO.getCategory(),
                salonDTO.getId()).getBody(); //check for the null pointer exception
        ServiceOffering serviceOffering = serviceOfferingService.createService(salonDTO,serviceDTO,categoryDTO);
        return ResponseEntity.ok(serviceOffering);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceOffering> updateService(@PathVariable Long id, @RequestBody ServiceOffering serviceOffering) throws Exception {
        ServiceOffering serviceOffering1 = serviceOfferingService.updateService(id, serviceOffering);
        return ResponseEntity.ok(serviceOffering1);
    }

}
