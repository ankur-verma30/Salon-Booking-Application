package com.salon.payment.service.client;

import com.salon.payment.payload.response.dto.ServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient("SERVICE-OFFERING")
public interface ServiceOfferingFeignClient {


    @GetMapping("/api/service-offerings/list/{ids}")
    public ResponseEntity<Set<ServiceDTO>> getServicesByIds(@PathVariable Set<Long> ids);
}
