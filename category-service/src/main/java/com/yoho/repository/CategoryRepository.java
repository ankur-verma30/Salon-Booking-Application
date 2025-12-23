package com.yoho.repository;

import com.yoho.dto.SalonDTO;
import com.yoho.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Set<Category> findBySalonId(Long salonId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Category c WHERE c.id = :id AND c.salonId = :salonId")
    void deleteByIdAndSalonId(@Param("id") Long id, @Param("salonId") Long salonId);

    Category findByIdAndSalonId(Long id, Long salonId);
}
