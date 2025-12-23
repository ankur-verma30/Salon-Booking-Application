package com.yoho.controller;

import com.yoho.dto.CategoryDTO;
import com.yoho.dto.SalonDTO;
import com.yoho.dto.ServiceDTO;
import com.yoho.model.ServiceOffering;
import com.yoho.service.ServiceOfferingService;
import com.yoho.service.client.CategoryFeignClient;
import com.yoho.service.client.SalonFeignClient;
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
