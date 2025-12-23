package com.yoho.service;

import com.yoho.dto.CategoryDTO;
import com.yoho.dto.SalonDTO;
import com.yoho.dto.ServiceDTO;
import com.yoho.model.ServiceOffering;
import jdk.jfr.Category;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);
    ServiceOffering updateService(Long serviceId, ServiceOffering serviceOffering) throws Exception;
    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);
    Set<ServiceOffering> getServicesByIds(Set<Long> ids);
    ServiceOffering getServiceById(Long id) throws Exception;
}
