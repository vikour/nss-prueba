package es.vikour.nss.nssreservahoteles.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.vikour.nss.nssreservahoteles.entity.Booking;
import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.repository.AvailavilityRepository;
import es.vikour.nss.nssreservahoteles.repository.BookingRepository;
import es.vikour.nss.nssreservahoteles.repository.HotelRepository;
import es.vikour.nss.nssreservahoteles.service.exceptions.BookingNotFoundException;
import es.vikour.nss.nssreservahoteles.service.impl.HotelServiceImpl;

@ExtendWith(SpringExtension.class)
class QueryHotelBookingTest {

	@Autowired
	private BookingService bookingService;

	@MockBean
	private HotelRepository hotelRepository;

	@MockBean
	private AvailavilityRepository availavilityRepository;

	@MockBean
	private BookingRepository bookRepository;


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
	}

	@Test
	@DisplayName("El ID de la reserva no debería de ser nulo")
	public void testQueryBooking_whenNull_thenError() {
		assertThrows(NullPointerException.class,() -> {
			bookingService.queryBooking(null);
		});
		verify(bookRepository, never()).findById(1);
	}

	@Test
	@DisplayName("Cuando se busca por una reserva que no existe, debería de devolver excepción")
	public void testQueryBooking_whenNoBookingFound_thenError() {
		when(bookRepository.findById(1)).thenReturn(Optional.empty());

		// test
		assertThrows(BookingNotFoundException.class,() -> {
			bookingService.queryBooking(1);
		});

		verify(bookRepository).findById(1);
	}

	@Test
	@DisplayName("Cuando se busca una reserva que existe, debería de devolver esa reserva")
	public void testQueryBooking_whenBookingFound_thenReturnIt() {
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setCategory(1);
		hotel.setName("Hotel");

		Booking expectedBooking = Booking.builder()
				.hotel(hotel)
				.dateFrom(LocalDate.of(2022, 1, 5))
				.dateTo(LocalDate.of(2022, 1, 6))
				.email("test@test.com")
				.build();

		when(bookRepository.findById(1)).thenReturn(Optional.of(expectedBooking));

		// test
		Booking booking = bookingService.queryBooking(1);

		// Assert
		verify(bookRepository).findById(1);
		assertEquals(expectedBooking, booking);
	}

}
