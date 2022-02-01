package es.vikour.nss.nssreservahoteles.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.vikour.nss.nssreservahoteles.service.HotelAvailavilityService;
import es.vikour.nss.nssreservahoteles.service.requests.OpenAvailavilityRequest;
import es.vikour.nss.nssreservahoteles.web.converter.OpenAvailiavilityRequestToDto;
import es.vikour.nss.nssreservahoteles.web.dto.OpenAvailavilityRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/hotels/{hotelId}")
public class AvailavilityController {

	@Autowired
	private HotelAvailavilityService hotelAvailavilityService;
	
	@Autowired
	private OpenAvailiavilityRequestToDto openAvailavilityConverter;

	@Operation(
		summary = "Abre disponibilidad para un hotel",
		description = "Abre disponibilidad para el hotel, fechas y número de habitaciones indicados. Si existía una disponibilidad para un día entre las fechas, " + 
		              "no se sobreescribirá")
	@ApiResponses(
		@ApiResponse(responseCode = "201", description = "Disponibilidad abierta satisfactoriamente"))
	@PostMapping(
			path = "/availavility",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public String openHotelAvailavility(
			@PathVariable("hotelId") int hotelId,
			@RequestBody @Valid OpenAvailavilityRequestDto request) {

		OpenAvailavilityRequest openAvailivityRequest = openAvailavilityConverter.toEntity(hotelId, request);
		hotelAvailavilityService.openAvailavility(openAvailivityRequest);
		
		return "Disponibilidad creada satisfactoriamente";
	}
	
}
