package es.vikour.nss.nssreservahoteles.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import es.vikour.nss.nssreservahoteles.web.dto.BookingDto;
import es.vikour.nss.nssreservahoteles.web.dto.OpenBookingRequestDto;
import es.vikour.nss.nssreservahoteles.web.dto.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reservas")
public interface BookingController extends ApiController {

	@Operation(
			summary = "Hace una reserva en un hotel",
			description = "Intenta hacer una reserva en un Hotel para una fechas concretas")

	@ApiResponse(responseCode = "201", description = "Reserva creada satisfactoriamente")
	@ApiResponse(responseCode = "404", description = "No se ha encontrado el hotel",
		content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))})
	@ApiResponse(responseCode = "400", description = "Error de validación",
		content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))})
	@ApiResponse(responseCode = "409", description = "No es posible hacer la reserva para el hotel en el rango de fechas proporcionados, porque no hay disponibilidad",
		content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))})
	@PostMapping(path = "hotels/{hotelId}/booking",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BookingDto> openBooking(
			@PathVariable("hotelId") Integer hotelId,
			@Valid @RequestBody OpenBookingRequestDto request);


	@Operation(
		summary = "Consulta las reservas para un hotel en unas fechas",
		description = "Consulta qué reservas hay realizadas para un hotel entre fechas. En el resultado aparecerán aquellas reservas en las que haya días " +
		              "que coincidan con el rango de fechas proporcionado",
		parameters = {
		  @Parameter(name = "hotelId", description = "El identificador del hotel", example = "1"),
	      @Parameter(name = "startDate", description = "Fecha inicio (formato: yyyy-MM-dd)", example = "2022-04-02"),
	      @Parameter(name = "endDate", description = "Fecha fin (formato: yyyy-MM-dd)", example = "2022-04-05")
		})
	@ApiResponse(responseCode = "200", description = "Se devuelve el listado de reservas")
	@ApiResponse(responseCode = "404", description = "No se ha encontrado el hotel",
		content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))})
	@ApiResponse(responseCode = "400", description = "Error de validación",
		content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))})
	@GetMapping(path = "hotels/{hotelId}/booking", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<BookingDto>> queryHotelBooking(
			@PathVariable("hotelId") Integer hotelId,
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate);

	@Operation(
		summary = "Consulta los datos de una reserva",
		description = "Devuelve todos los datos de una reserva por ID",
		parameters = {
		  @Parameter(name = "bookingId", description = "El identificador de la reserva", example = "1")
	 })
	@ApiResponse(responseCode = "200", description = "Se devuelve los datos de la reserva")
	@ApiResponse(responseCode = "404", description = "No se ha encontrado la reserva con el ID proporcionado",
		content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))})
	@GetMapping(path = "booking/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BookingDto> queryBooking(@PathVariable("bookingId") Integer bookingId);


	@Operation(
		summary = "Cancela una reserva",
		description = "Permite cancelar una reserva proporcionando el ID",
		parameters = {
		  @Parameter(name = "bookingId", description = "El identificador de la reserva", example = "1")
		})

	@ApiResponse(responseCode = "200", description = "Se devuelve los datos de la reserva")
	@ApiResponse(responseCode = "404", description = "No se ha encontrado la reserva con el ID proporcionado",
					content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))})
	@DeleteMapping(path = "booking/{bookingId}")
	ResponseEntity<String> cancelBooking(@PathVariable("bookingId") Integer bookingId);

}