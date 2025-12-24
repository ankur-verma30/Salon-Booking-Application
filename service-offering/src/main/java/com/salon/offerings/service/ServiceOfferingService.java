package com.salon.offerings.service;

import com.salon.offerings.dto.CategoryDTO;
import com.salon.offerings.dto.SalonDTO;
import com.salon.offerings.dto.ServiceDTO;
import com.salon.offerings.entity.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);

    ServiceOffering updateService(Long serviceId, ServiceOffering serviceOffering) throws Exception;

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId) ;

    Set<ServiceOffering> getServicesByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long id) throws Exception;
}
