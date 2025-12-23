package com.yoho.service;

import com.yoho.dto.SalonDTO;
import com.yoho.model.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    Category saveCategory(Category category, SalonDTO salonDTO);
    Set<Category> getAllCategoriesBySalon(Long id);
    Category getCategoryById(Long id) throws Exception;
    void deleteCategoryById(Long id, Long salonId) throws Exception;
    Category findByIdAndSalonId(Long id, Long salonId) throws Exception;
}
