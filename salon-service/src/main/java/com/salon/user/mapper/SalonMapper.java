package com.salon.user.mapper;

import com.salon.user.entity.Salon;
import com.salon.user.payload.dto.SalonDTO;

public class SalonMapper {

    public static SalonDTO mapToDTO(Salon salon){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salon.getId());
        salonDTO.setName(salon.getName());
        salonDTO.setEmail(salon.getEmail());
        salonDTO.setAddress(salon.getAddress());
        salonDTO.setCity(salon.getCity());
        salonDTO.setImages(salon.getImages());
        salonDTO.setOpenTime(salon.getOpenTime());
        salonDTO.setCloseTime(salon.getCloseTime());
        salonDTO.setPhoneNumber(salon.getPhoneNumber());
        salonDTO.setOwnerId(salon.getOwnerId());
        return salonDTO;
    }
}
