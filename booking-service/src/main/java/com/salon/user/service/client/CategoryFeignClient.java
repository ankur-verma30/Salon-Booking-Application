package com.salon.user.service.client;

import com.salon.user.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CATEGORY-SERVICE")
public interface CategoryFeignClient {

    @GetMapping("/api/categories/salon-owner/salon/{salonId}/category/{id}")
    public ResponseEntity<CategoryDTO> getCategoriesByIdAndSalon(@PathVariable("id") Long id,
                                                              @PathVariable("salonId") Long salonId) throws Exception;
}
