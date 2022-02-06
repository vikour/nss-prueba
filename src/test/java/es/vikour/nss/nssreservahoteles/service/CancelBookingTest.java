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
import org.mockito.ArgumentCaptor;
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
import es.vikour.nss.nssreservahoteles.service.exceptions.BookingNotFoundException;
import es.vikour.nss.nssreservahoteles.service.exceptions.HotelNotFoundException;
import es.vikour.nss.nssreservahoteles.service.impl.HotelServiceImpl;
import es.vikour.nss.nssreservahoteles.service.requests.HotelDateIntervalRequest;

@ExtendWith(SpringExtension.class)
class CancelBookingTest {

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
	public void testCancelBooking_whenNull_thenError() {
		assertThrows(NullPointerException.class,() -> {
			bookingService.cancelBooking(null);
		});
		verify(bookRepository, never()).findById(1);
	}
	
	@Test
	@DisplayName("Cuando la reserva no existe, entonces se devolvería excepción")
	public void testCancelBooking_whenNotFound_thenException() {
		
		when(bookRepository.findById(1)).thenReturn(Optional.empty());
		
		assertThrows(BookingNotFoundException.class, () -> {
			bookingService.cancelBooking(1);
		});
		
		verify(bookRepository).findById(1);
	}
	
	@Test
	@DisplayName("Cuando la reserva existe, entonces se borra y se actualiza la disponibilidad")
	public void testCancelBooking_whenFound_thenDeletedAndUpdatedAvailavility() {
		// DB data
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setCategory(1);
		hotel.setName("Hotel");
		
		LocalDate date5Feb = LocalDate.of(2022, 2, 5);
		LocalDate date6Feb = LocalDate.of(2022, 2, 6);
		
		Booking booking = Booking.builder()
				.id(1)
				.hotel(hotel)
				.dateFrom(date5Feb)
				.dateTo(date6Feb)
				.email("test@test.es")
				.build();
		
		Availavility av5Feb = new Availavility(date5Feb, hotel, 0);
		Availavility av6Feb = new Availavility(date6Feb, hotel, 0);
		List<Availavility> availavilitiesDB = Lists.list(av5Feb, av6Feb);
		
		// Configure Mocks
		when(bookRepository.findById(1)).thenReturn(Optional.of(booking));
		when(availavilityRepository.findByHotelAndBetweenDates(hotel, date5Feb, date6Feb))
			.thenReturn(availavilitiesDB);
		
		// Test
		bookingService.cancelBooking(1);
		
		
		// Assert
		verify(bookRepository).findById(1);
		verify(bookRepository).delete(booking);
		verify(availavilityRepository).findByHotelAndBetweenDates(hotel, date5Feb, date6Feb);
		
		ArgumentCaptor<Availavility> availavilityCatpor = ArgumentCaptor.forClass(Availavility.class);
		verify(availavilityRepository, times(2)).save(availavilityCatpor.capture());
		
		for (Availavility av : availavilityCatpor.getAllValues())
			assertEquals(1, av.getRooms()); // Antes era 0, ahora tendría que ser 1
		
	}
}
