package es.vikour.nss.nssreservahoteles.web.controller.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.service.HotelService;
import es.vikour.nss.nssreservahoteles.web.controller.HotelController;
import es.vikour.nss.nssreservahoteles.web.converter.HotelToDtoConverter;
import es.vikour.nss.nssreservahoteles.web.dto.HotelDto;
import es.vikour.nss.nssreservahoteles.web.dto.error.ApiError;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
		return buildResponseEntity(hotelList);
	}

	@Override
	public ResponseEntity<List<HotelDto>> findAllHotelsWithAvailavility(LocalDate startDate, LocalDate endDate) {
		List<Hotel> hotelList = hotelService.findAllWithRoomsAvailableBetweenDates(startDate, endDate);
		return buildResponseEntity(hotelList);
	}

	private ResponseEntity<List<HotelDto>> buildResponseEntity(List<Hotel> hotelList) {
		List<HotelDto> hotelDtoList = hotelDtoConverter.hotelToDto(hotelList);
		return ResponseEntity.ok(hotelDtoList);
	}
	
	
	
}
