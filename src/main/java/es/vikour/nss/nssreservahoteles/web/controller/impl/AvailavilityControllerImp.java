package es.vikour.nss.nssreservahoteles.web.controller.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.service.HotelAvailavilityService;
import es.vikour.nss.nssreservahoteles.service.HotelService;
import es.vikour.nss.nssreservahoteles.service.requests.OpenAvailavilityRequest;
import es.vikour.nss.nssreservahoteles.web.controller.AvailableController;
import es.vikour.nss.nssreservahoteles.web.converter.HotelToDtoConverter;
import es.vikour.nss.nssreservahoteles.web.converter.OpenAvailiavilityRequestToDto;
import es.vikour.nss.nssreservahoteles.web.dto.HotelDto;
import es.vikour.nss.nssreservahoteles.web.dto.OpenAvailavilityRequestDto;

@RestController
public class AvailavilityControllerImp implements AvailableController {

	@Autowired
	private HotelAvailavilityService hotelAvailavilityService;

	@Autowired
	private OpenAvailiavilityRequestToDto openAvailavilityConverter;

	@Autowired
	private HotelService hotelService;

	@Autowired
	private HotelToDtoConverter hotelDtoConverter;

	@Override
	public String openHotelAvailavility(int hotelId, OpenAvailavilityRequestDto request) {

		OpenAvailavilityRequest openAvailivityRequest = openAvailavilityConverter.toEntity(hotelId, request);
		hotelAvailavilityService.openAvailavility(openAvailivityRequest);

		return "Disponibilidad creada satisfactoriamente";
	}

	@Override
	public ResponseEntity<List<HotelDto>> findAllHotelsWithAvailavility(LocalDate startDate, LocalDate endDate) {
		List<Hotel> hotelList = hotelService.findAllWithRoomsAvailableBetweenDates(startDate, endDate);
		List<HotelDto> hotelDtoList = hotelDtoConverter.hotelToDto(hotelList);
		return ResponseEntity.ok(hotelDtoList);
	}
}
