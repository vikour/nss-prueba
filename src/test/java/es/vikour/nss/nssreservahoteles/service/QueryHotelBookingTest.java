package es.vikour.nss.nssreservahoteles.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.vikour.nss.nssreservahoteles.entity.Availavility;
import es.vikour.nss.nssreservahoteles.entity.Booking;
import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.repository.AvailavilityRepository;
import es.vikour.nss.nssreservahoteles.repository.BookingRepository;
import es.vikour.nss.nssreservahoteles.repository.HotelRepository;
import es.vikour.nss.nssreservahoteles.service.exceptions.HotelNotFoundException;
import es.vikour.nss.nssreservahoteles.service.impl.HotelServiceImpl;
import es.vikour.nss.nssreservahoteles.service.requests.HotelDateIntervalRequest;

@ExtendWith(SpringExtension.class)
class QueryHotelBookingTest {

	private static final LocalDate END_DATE = LocalDate.of(2022, 1, 10);

	private static final LocalDate START_DATE = LocalDate.of(2022, 1, 5);

	private static final int HOTEL_ID = 1;

	@Autowired
	private BookingService bookingService;
	
	@MockBean
	private HotelRepository hotelRepository;
	
	@MockBean
	private AvailavilityRepository availavilityRepository;
	
	@MockBean
	private BookingRepository bookRepository;
	
	// Test data
	private HotelDateIntervalRequest request;
	private Hotel hotelSelected;
	
	@TestConfiguration
	public static class HotelServiceTestConfig {
		
		@Bean
		public HotelService hotelService() {
			return new HotelServiceImpl();
		}
		
	}
	
	@BeforeEach
	public void setUp() {
		reset(hotelRepository);
		reset(availavilityRepository);
		reset(bookRepository);
		
		// set up valid request
		request = new HotelDateIntervalRequest();
		request.setHotelId(HOTEL_ID);
		request.setStartDate(START_DATE);
		request.setEndDate(END_DATE);
		
		// set up hotel
		hotelSelected = new Hotel();
		hotelSelected.setId(HOTEL_ID);
		hotelSelected.setName("Hotel");
		hotelSelected.setCategory(1);
		
	}
	
	@Test
	@DisplayName("La solicitud no debería de ser nula")
	public void testQueryBooking_whenNull_thenError() {
		assertThrows(NullPointerException.class,() -> {
			bookingService.queryHotelBooking(null);
		});
	}

	
	@Test
	@DisplayName("Cuando el hotel pasado no existe, entonces error")
	public void testQueryHotelBooking_whenHotelNotfound_thenError() {
		// set up
		when(hotelRepository.findById(request.getHotelId())).thenReturn(Optional.empty());
		
		// Test
		assertThrows(HotelNotFoundException.class, () -> {
			bookingService.queryHotelBooking(request);
		});
		
		// assert
		verify(hotelRepository).findById(request.getHotelId());
		verify(bookRepository, never()).findAllByHotelAndBetweenDates(any(), any(), any());
	}

	
	@Test
	@DisplayName("Cuando la base de datos no devuevle nada, entonces empty list")
	public void testQueryHotelBooking_whenHotelNoBooking_thenReturnEmptyList() {
		// set up
		when(hotelRepository.findById(HOTEL_ID)).thenReturn(Optional.of(hotelSelected));
		when(bookRepository.findAllByHotelAndBetweenDates(hotelSelected, START_DATE, END_DATE)).thenReturn(Lists.newArrayList());

		// check
		List<Booking> bookingList = bookingService.queryHotelBooking(request);
		
		// assert
		verify(hotelRepository).findById(request.getHotelId());
		verify(bookRepository).findAllByHotelAndBetweenDates(hotelSelected, START_DATE, END_DATE);
		
		assertNotNull(bookingList);
		assertTrue(bookingList.isEmpty());
	}

	@Test
	@DisplayName("Cuando la base de datos devuelve una lista, entonces se devuelve esa lista")
	public void testQueryHotelBooking_whenHotelHasBooking_thenReturnIt() {
		// set up
		List<Booking> bookingListExpected = Lists.list(
				Booking.builder()
				.hotel(hotelSelected)
				.dateFrom(START_DATE)
				.dateTo(END_DATE)
				.email("test@email.com")
				.build(), 
				Booking.builder()
				.hotel(hotelSelected)
				.dateFrom(START_DATE)
				.dateTo(END_DATE)
				.email("test2@email.com")
				.build());
		
		when(hotelRepository.findById(HOTEL_ID)).thenReturn(Optional.of(hotelSelected));
		when(bookRepository.findAllByHotelAndBetweenDates(hotelSelected, START_DATE, END_DATE))
			.thenReturn(bookingListExpected);

		// check
		List<Booking> bookingList = bookingService.queryHotelBooking(request);
		
		// assert
		verify(hotelRepository).findById(request.getHotelId());
		verify(bookRepository).findAllByHotelAndBetweenDates(hotelSelected, START_DATE, END_DATE);
		
		assertNotNull(bookingList);
		assertFalse(bookingList.isEmpty());
		assertEquals(bookingListExpected, bookingList);
	}
}
