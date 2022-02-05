package es.vikour.nss.nssreservahoteles.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.vikour.nss.nssreservahoteles.entity.Booking;
import es.vikour.nss.nssreservahoteles.service.BookingService;
import es.vikour.nss.nssreservahoteles.service.requests.OpenBookingRequest;
import es.vikour.nss.nssreservahoteles.web.converter.BookingToDtoConverter;
import es.vikour.nss.nssreservahoteles.web.converter.OpenBookingRequestToDtoConverter;
import es.vikour.nss.nssreservahoteles.web.dto.BookingDto;
import es.vikour.nss.nssreservahoteles.web.dto.OpenBookingRequestDto;
import es.vikour.nss.nssreservahoteles.web.dto.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private OpenBookingRequestToDtoConverter openBookingRequestConverter;
	
	@Autowired
	private BookingToDtoConverter bookingToDtoConverter;

	@Operation(
			summary = "Hace una reserva en un hotel",
			description = "Intenta hacer una reserva en un Hotel para una fechas concretas")
		@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Reserva creada satisfactoriamente"),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el hotel",
					content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))}),
			@ApiResponse(responseCode = "400", description = "Error de validación",
					content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))}),
			@ApiResponse(responseCode = "409", description = "No es posible hacer la reserva para el hotel en el rango de fechas proporcionados, porque no hay disponibilidad",
					content = {@Content(mediaType = "appliaction/json", schema = @Schema(implementation = ApiError.class))})
		})
	@PostMapping(path = "hotels/{hotelId}/booking",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookingDto> openBooking(
		@PathVariable("hotelId") Integer hotelId,
		@Valid @RequestBody OpenBookingRequestDto request) 
	{
		OpenBookingRequest requestService = openBookingRequestConverter.toEntity(hotelId, request);
		Booking bookingCreated = bookingService.openBooking(requestService);
		BookingDto dto = bookingToDtoConverter.toDto(bookingCreated);
		return new ResponseEntity<BookingDto>(dto, HttpStatus.CREATED);
	}

}
