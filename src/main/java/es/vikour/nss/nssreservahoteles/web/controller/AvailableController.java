package es.vikour.nss.nssreservahoteles.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.vikour.nss.nssreservahoteles.web.dto.HotelDto;
import es.vikour.nss.nssreservahoteles.web.dto.OpenAvailavilityRequestDto;
import es.vikour.nss.nssreservahoteles.web.dto.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Disponibilidad")
public interface AvailableController extends ApiController{

	@Operation(
			summary = "Abre disponibilidad para un hotel",
			description = "Abre disponibilidad para el hotel, fechas y número de habitaciones indicados. Si existía una disponibilidad para un día entre las fechas, " +
			              "no se sobreescribirá",
			parameters = @Parameter(name = "hotelId", description = "El código del hotel"))
	@ApiResponse(responseCode = "201", description = "Disponibilidad abierta satisfactoriamente")
	@ApiResponse(responseCode = "404", description = "No se ha encontrado el hotel",
			content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class, example = "example"))})
	@ApiResponse(responseCode = "400", description = "Error de validación",
			content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))})
	@PostMapping(
				path = "/hotels/{hotelId}/availavility",
				consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	String openHotelAvailavility(
			@PathVariable("hotelId") int hotelId,
			@RequestBody @Valid OpenAvailavilityRequestDto request);


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
	@GetMapping(path = "/availavility",
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<HotelDto>> findAllHotelsWithAvailavility(
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate);

}