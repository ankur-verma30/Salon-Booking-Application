package com.salon.user.controller;

import com.salon.user.dto.SalonDTO;
import com.salon.user.entity.Category;
import com.salon.user.service.CategoryService;
import com.salon.user.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;
    private final SalonFeignClient salonFeignClient;


    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization")String jwt) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        Category savedCategory = categoryService.saveCategory(category,salonDTO);
        return ResponseEntity.ok(savedCategory);
    }

    @GetMapping("/salon/{salonId}/category/{id}")
    public ResponseEntity<Category> getCategoriesByIdAndSalon(@PathVariable Long id,
                                                                   @PathVariable Long salonId) throws Exception {
        Category category = categoryService.findByIdAndSalonId(id, salonId);
        return ResponseEntity.ok(category);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id,
                                                 @RequestHeader("Authorization")String jwt) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(jwt).getBody();
        categoryService.deleteCategoryById(id, salonDTO.getId());
        return ResponseEntity.ok("Category Deleted Successfully");
    }

}
