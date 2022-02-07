package es.vikour.nss.nssreservahoteles.web.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.vikour.nss.nssreservahoteles.web.dto.HotelDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/hotels")
@Tag(name = "Hoteles")
public interface HotelController extends ApiController {

	@Operation(
		summary = "Lista los hoteles",
		description = "Lista los hoteles que hay en el sistema")
	@ApiResponse(responseCode = "200", description = "Listado correctamente, se devuelve la lista de hoteles")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<HotelDto>> findAllHotels();

}