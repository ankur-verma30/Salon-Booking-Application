package com.salon.user.service;

import com.salon.user.dto.SalonDTO;
import com.salon.user.entity.Category;

import java.util.Set;

public interface CategoryService {

    Category saveCategory(Category category, SalonDTO salonDTO);
    Set<Category> getAllCategoriesBySalon(Long id);
    Category getCategoryById(Long id) throws Exception;
    void deleteCategoryById(Long id, Long salonId) throws Exception;
    Category findByIdAndSalonId(Long id, Long salonId) throws Exception;
}
