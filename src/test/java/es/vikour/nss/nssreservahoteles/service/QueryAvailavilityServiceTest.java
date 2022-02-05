package es.vikour.nss.nssreservahoteles.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
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
import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.repository.AvailavilityRepository;
import es.vikour.nss.nssreservahoteles.repository.BookingRepository;
import es.vikour.nss.nssreservahoteles.repository.HotelRepository;
import es.vikour.nss.nssreservahoteles.service.impl.HotelServiceImpl;

@ExtendWith(SpringExtension.class)
class QueryAvailavilityServiceTest {

	@Autowired
	private HotelService hotelService;
	
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
	}
	
	@Test
	@DisplayName("La fecha inicio debe de ser no nula")
	public void testQueryAvailavility_whenNullStartDate_thenError() {
		assertThrows(NullPointerException.class,() -> {
			hotelService.findAllWithRoomsAvailableBetweenDates(null, LocalDate.now());
		});
	}
	
	@Test
	@DisplayName("La fecha fin debe de ser no nula")
	public void testQueryAvailavility_whenNullEndDate_thenError() {
		assertThrows(NullPointerException.class,() -> {
			hotelService.findAllWithRoomsAvailableBetweenDates(LocalDate.now(), null);
		});
	}
	
	@Test
	@DisplayName("Cuando no hay hoteles, entonces no debería devolver nada") 
	public void testQueryAvailavility_whenNoHotelAvaible_thenEmpty() {
		LocalDate startDate = LocalDate.of(2022, 02, 05);
		LocalDate endDate = LocalDate.of(2022, 02, 06);
		
		when(hotelRepository.findAllWithRoomsAvailable(startDate, endDate)).thenReturn(Lists.newArrayList());
		
		// Check
		List<Hotel> hotels = hotelService.findAllWithRoomsAvailableBetweenDates(startDate, endDate);
		
		// Assert
		assertTrue(hotels.isEmpty());
		
		verify(hotelRepository).findAllWithRoomsAvailable(startDate, endDate);
		verify(availavilityRepository, never()).findByHotelAndBetweenDatesAndRoomsAvailable(any(), eq(startDate), eq(endDate));
	}
	

	/*
	 * Generador de pruebas para disponibilidad de un hotel
	 */
	
	public static Stream<Arguments> providerOneHotelWithAvailavility() {
		
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setCategory(1);
		hotel.setName("Hotel");
		
		Availavility av3Feb = new Availavility(LocalDate.of(2022, 2, 3), hotel, 1);
		Availavility av4Feb = new Availavility(LocalDate.of(2022, 2, 4), hotel, 2);
		Availavility av6Feb = new Availavility(LocalDate.of(2022, 2, 6), hotel, 2);
		
		return Stream.of(
			Arguments.of(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 2, 1), 
					Lists.newArrayList(), Lists.newArrayList(),
					Lists.newArrayList()), // Expected
			
			Arguments.of(LocalDate.of(2022, 2, 2), LocalDate.of(2022, 2, 3), 
					Lists.list(hotel), Lists.list(av3Feb),
					Lists.newArrayList()), // Expected, vacio, no tiene todos los días
			
			Arguments.of(LocalDate.of(2022, 2, 3), LocalDate.of(2022, 2, 3), 
					Lists.list(hotel), Lists.list(av3Feb),
					Lists.list(hotel)), // Expected, cuando coincide la fecha, entonces devuevle el hotel
			
			Arguments.of(LocalDate.of(2022, 2, 2), LocalDate.of(2022, 2, 4), 
					Lists.list(hotel), Lists.list(av3Feb),
					Lists.newArrayList()), // Si no coincide todos los días vacío
			
			Arguments.of(LocalDate.of(2022, 2, 3), LocalDate.of(2022, 2, 4), 
					Lists.list(hotel), Lists.list(av3Feb, av4Feb),
					Lists.list(hotel)),
			
			Arguments.of(LocalDate.of(2022, 2, 6), LocalDate.of(2022, 2, 6), 
					Lists.list(hotel), Lists.list(av6Feb),
					Lists.list(hotel)),
			
			Arguments.of(LocalDate.of(2022, 2, 3), LocalDate.of(2022, 2, 6), 
					Lists.list(hotel), Lists.list(av3Feb, av4Feb, av6Feb),
					Lists.newArrayList()),// Si no coincide todos los días vacío
			
			Arguments.of(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 2, 7), 
					Lists.list(hotel), Lists.list(av3Feb, av4Feb, av6Feb),
					Lists.newArrayList())// Si no coincide todos los días vacío
			
		);
	}
	
	@ParameterizedTest(name="Disponibilidad desde {0}-{1} con disponibilidad {3} entonces se espera: {4}")
	@MethodSource("providerOneHotelWithAvailavility")
	@DisplayName("Cuando hay un hotel, entonces lo devuelve si para todos los días de esa fecha tiene disponibilidad") 
	public void testQueryAvailavility_whenOneHotelAvaible_thenCorrect(
			LocalDate startDate, LocalDate endDate,
			List<Hotel> hotelReturnedDB, List<Availavility> availavilitiesHotelDB,
			List<Hotel> hotelExpectedList) 
	{
		when(hotelRepository.findAllWithRoomsAvailable(startDate, endDate)).thenReturn(hotelReturnedDB);
		
		if (!hotelReturnedDB.isEmpty()) {
			when(availavilityRepository.findByHotelAndBetweenDatesAndRoomsAvailable(hotelReturnedDB.get(0), startDate, endDate))
				.thenReturn(availavilitiesHotelDB);
		}
		
		// Check
		List<Hotel> hotels = hotelService.findAllWithRoomsAvailableBetweenDates(startDate, endDate);
		
		// Assert
		assertNotNull(hotels);
		assertEquals(hotelExpectedList, hotels);
	}

}
