package com.salon.category.controller;

import com.salon.category.dto.SalonDTO;
import com.salon.category.entity.Category;
import com.salon.category.service.CategoryService;
import com.salon.category.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;
    private final SalonFeignClient salonFeignClient;


    @PostMapping("/create")
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
        categoryService.deleteCategoryById(id, salonDTO.getId());//check for the nullability
        return ResponseEntity.status(HttpStatus.OK).body("Category Deleted Successfully");
    }

}
