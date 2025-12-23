package com.salon.user.service;

import com.salon.user.dto.CategoryDTO;
import com.salon.user.dto.SalonDTO;
import com.salon.user.dto.ServiceDTO;
import com.salon.user.entity.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);
    ServiceOffering updateService(Long serviceId, ServiceOffering serviceOffering) throws Exception;
    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);
    Set<ServiceOffering> getServicesByIds(Set<Long> ids);
    ServiceOffering getServiceById(Long id) throws Exception;
}
