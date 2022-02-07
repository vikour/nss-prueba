package es.vikour.nss.nssreservahoteles.web.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.service.HotelService;
import es.vikour.nss.nssreservahoteles.web.controller.HotelController;
import es.vikour.nss.nssreservahoteles.web.converter.HotelToDtoConverter;
import es.vikour.nss.nssreservahoteles.web.dto.HotelDto;

/**
 * API para obtener información acerca de los hoteles.
 *
 * @author Víctor Manuel Ortiz Guardeño
 */

@RestController
public class HotelControllerImp implements HotelController {

	@Autowired
	private HotelService hotelService;

	@Autowired
	private HotelToDtoConverter hotelDtoConverter;

	@Override
	public ResponseEntity<List<HotelDto>> findAllHotels() {
		List<Hotel> hotelList = hotelService.findAll();
		List<HotelDto> hotelDtoList = hotelDtoConverter.hotelToDto(hotelList);
		return ResponseEntity.ok(hotelDtoList);
	}



}
