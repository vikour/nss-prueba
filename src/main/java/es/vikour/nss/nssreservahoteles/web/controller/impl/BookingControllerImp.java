package es.vikour.nss.nssreservahoteles.web.controller.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.vikour.nss.nssreservahoteles.entity.Booking;
import es.vikour.nss.nssreservahoteles.service.BookingService;
import es.vikour.nss.nssreservahoteles.service.requests.HotelDateIntervalRequest;
import es.vikour.nss.nssreservahoteles.service.requests.OpenBookingRequest;
import es.vikour.nss.nssreservahoteles.web.controller.BookingController;
import es.vikour.nss.nssreservahoteles.web.converter.BookingToDtoConverter;
import es.vikour.nss.nssreservahoteles.web.converter.OpenBookingRequestToDtoConverter;
import es.vikour.nss.nssreservahoteles.web.dto.BookingDto;
import es.vikour.nss.nssreservahoteles.web.dto.OpenBookingRequestDto;
import es.vikour.nss.nssreservahoteles.web.dto.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class BookingControllerImp implements BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private OpenBookingRequestToDtoConverter openBookingRequestConverter;
	
	@Autowired
	private BookingToDtoConverter bookingToDtoConverter;

	@Override
	public ResponseEntity<BookingDto> openBooking(Integer hotelId, OpenBookingRequestDto request) 
	{
		OpenBookingRequest requestService = openBookingRequestConverter.toEntity(hotelId, request);
		Booking bookingCreated = bookingService.openBooking(requestService);
		BookingDto dto = bookingToDtoConverter.toDto(bookingCreated);
		return new ResponseEntity<BookingDto>(dto, HttpStatus.CREATED);
	}
	
	
	@Override
	public ResponseEntity<List<BookingDto>> queryHotelBooking(Integer hotelId, LocalDate startDate, LocalDate endDate) 
	{
		log.traceEntry();
		log.info("Contestando petición de consulta de reservas para el hotel {} desde {} hasta {}", hotelId, startDate, endDate);
		HotelDateIntervalRequest request = new HotelDateIntervalRequest();
		request.setHotelId(hotelId);
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		
		List<Booking> hotelBookingList = bookingService.queryHotelBooking(request);
		List<BookingDto> dtoList = bookingToDtoConverter.toDto(hotelBookingList);
		return log.traceExit(ResponseEntity.ok(dtoList));
	}
	

	@Override
	public ResponseEntity<BookingDto> queryBooking(Integer bookingId) 
	{
		Objects.requireNonNull(bookingId, "El id de la reserva es obligatorio");
		Booking booking = bookingService.queryBooking(bookingId);
		BookingDto dto = bookingToDtoConverter.toDto(booking);
		return ResponseEntity.ok(dto);
	}
	

	@Override
	public ResponseEntity<String> cancelBooking( Integer bookingId) 
	{
		Objects.requireNonNull(bookingId, "El id de la reserva es obligatorio");
		bookingService.cancelBooking(bookingId);
		return ResponseEntity.ok("Reserva cancelada");
	}

}
