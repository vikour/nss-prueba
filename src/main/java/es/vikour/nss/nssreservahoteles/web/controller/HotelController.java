package es.vikour.nss.nssreservahoteles.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.vikour.nss.nssreservahoteles.web.dto.HotelDto;
import es.vikour.nss.nssreservahoteles.web.dto.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/hotels")
public interface HotelController extends ApiController {

	@Operation(
		summary = "Lista los hoteles",
		description = "Lista los hoteles que hay en el sistema")
	@ApiResponse(responseCode = "200", description = "Listado correctamente, se devuelve la lista de hoteles")
	@Tag(name = "Hoteles")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<HotelDto>> findAllHotels();

	
	@Operation(
		summary = "Lista los hoteles con disponibilidad entre fechas",
		description = "Dadas una fecha inicio y otra fin, lista qué hoteles tienen habitaciones disponibles para una reserva",
		parameters = {
				@Parameter(name = "startDate", description = "Fecha inicio (formato: yyyy-MM-dd)", example = "2022-04-02"),
				@Parameter(name = "endDate", description = "Fecha fin (formato: yyyy-MM-dd)", example = "2022-04-05")
		})
	@ApiResponse(responseCode = "200", description = "Listado correctamente, se devuelve la lista de hoteles", 
				content = @Content(mediaType = "application/json"))
	@ApiResponse(responseCode = "400", description = "Error de validación",
			content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))})
	@Tag(name = "Disponibilidad")
	@GetMapping(path = "/availavility",
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<HotelDto>> findAllHotelsWithAvailavility(
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate);

}