package com.salon.user.controller;

import com.salon.user.mapper.SalonMapper;
import com.salon.user.entity.Salon;
import com.salon.user.payload.dto.SalonDTO;
import com.salon.user.payload.dto.UserDTO;
import com.salon.user.service.SalonService;
import com.salon.user.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salon")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;
    private final UserFeignClient userFeignClient;

    @PostMapping
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO,
                                                @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();
        Salon salon = salonService.createSalon(salonDTO, userDTO);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);

    }

    @PatchMapping("/{salonId}")
    public ResponseEntity<SalonDTO> updateSalon(@PathVariable Long salonId, @RequestBody SalonDTO salonDTO,
                                                @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();

        System.out.println("-------"+salonId+"email "+ salonDTO.getEmail());

        Salon salon = salonService.updateSalon(salonDTO, userDTO, salonId);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);

    }

    @GetMapping
    public ResponseEntity<List<SalonDTO>> getSalons(){
        List<Salon> salons = salonService.getAllSalons();
        List<SalonDTO> salonDTOS = salons.stream().map((salon) -> {
            SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
            return salonDTO1;
        }).toList();
        return ResponseEntity.ok(salonDTOS);

    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long salonId) throws Exception {
        Salon salon = salonService.getSalonById(salonId);
        SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO);
    }

    //http:localhost5002/api/salons/search?city=mumbai
    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> getSearchSalons(@RequestParam("city") String city){
        List<Salon> salons = salonService.searchSalonByCity(city);
        List<SalonDTO> salonDTOS = salons.stream().map((salon) -> {
            SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
            return salonDTO1;
        }).toList();
        return ResponseEntity.ok(salonDTOS);

    }

    @GetMapping("/owner")
    public ResponseEntity<SalonDTO> getSalonByOwnerId(@RequestHeader("Authorization")String jwt) throws Exception {
        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();

        if(userDTO == null){
            throw new Exception("User not found from jwt...");
        }

        Salon salon = salonService.getSalonByOwnerId(userDTO.getId());
        SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO);
    }


}
