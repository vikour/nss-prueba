package es.vikour.nss.nssreservahoteles.web.controller.impl;

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
import es.vikour.nss.nssreservahoteles.web.controller.AvailableController;
import es.vikour.nss.nssreservahoteles.web.converter.OpenAvailiavilityRequestToDto;
import es.vikour.nss.nssreservahoteles.web.dto.OpenAvailavilityRequestDto;
import es.vikour.nss.nssreservahoteles.web.dto.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
public class AvailavilityControllerImp implements AvailableController {

	@Autowired
	private HotelAvailavilityService hotelAvailavilityService;
	
	@Autowired
	private OpenAvailiavilityRequestToDto openAvailavilityConverter;

	@Override
	public String openHotelAvailavility(int hotelId, OpenAvailavilityRequestDto request) {

		OpenAvailavilityRequest openAvailivityRequest = openAvailavilityConverter.toEntity(hotelId, request);
		hotelAvailavilityService.openAvailavility(openAvailivityRequest);
		
		return "Disponibilidad creada satisfactoriamente";
	}
	
}
