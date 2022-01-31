package es.vikour.nss.nssreservahoteles.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.service.HotelService;
import es.vikour.nss.nssreservahoteles.web.converter.HotelToDtoConverter;
import es.vikour.nss.nssreservahoteles.web.dto.HotelDto;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private HotelToDtoConverter hotelDtoConverter;
	
	@GetMapping
	public ResponseEntity<List<HotelDto>> findAllHotels() {
		List<Hotel> hotelList = hotelService.findAll();
		List<HotelDto> hotelDtoList = hotelDtoConverter.hotelToDto(hotelList);
		return ResponseEntity.ok(hotelDtoList);
	}
	
}
