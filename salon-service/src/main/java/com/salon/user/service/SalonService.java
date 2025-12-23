package com.salon.user.service;

import com.salon.user.entity.Salon;
import com.salon.user.payload.dto.SalonDTO;
import com.salon.user.payload.dto.UserDTO;

import java.util.List;

public interface SalonService {

    Salon createSalon(SalonDTO salon, UserDTO user);

    Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception;
    //UserDTO is provided here to check if the user who is trying to update and the owner are same or not.
    //If they are same then only allow them to update else we will not allow them to update.
    List<Salon> getAllSalons();

    Salon getSalonById(Long salonId) throws Exception;

    Salon getSalonByOwnerId(Long ownerId);

    List<Salon> searchSalonByCity(String city);
}
