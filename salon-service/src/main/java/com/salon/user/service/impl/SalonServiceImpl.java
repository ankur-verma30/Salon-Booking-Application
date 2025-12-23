package com.salon.user.service.impl;

import com.salon.user.entity.Salon;
import com.salon.user.payload.dto.SalonDTO;
import com.salon.user.payload.dto.UserDTO;
import com.salon.user.repository.SalonRepository;
import com.salon.user.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {
        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setImages(req.getImages());
        salon.setOwnerId(user.getId());
        salon.setOpenTime(req.getOpenTime());
        salon.setCloseTime(req.getCloseTime());
        salon.setPhoneNumber(req.getPhoneNumber());
        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception{
        Salon existingSalon = salonRepository.findById(salonId).orElseThrow(null);
        if(!salon.getOwnerId().equals(user.getId())){
            throw new Exception("You don't have permission to update this salon");
        }
        if(existingSalon != null){
            existingSalon.setCity(salon.getCity());
            existingSalon.setName(salon.getName());
            existingSalon.setAddress(salon.getAddress());
            existingSalon.setEmail(salon.getEmail());
            existingSalon.setImages(salon.getImages());
            existingSalon.setOpenTime(salon.getOpenTime());
            existingSalon.setPhoneNumber(salon.getPhoneNumber());
            existingSalon.setCloseTime(salon.getCloseTime());
            existingSalon.setOwnerId(user.getId());
            return salonRepository.save(existingSalon);
        }
        throw new Exception("Salon does not exist.");
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId).orElseThrow(null);
        if(salon == null){
            throw new Exception("Salon does not exist.");
        }
        return salon;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalons(city);
    }
}
