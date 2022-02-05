package es.vikour.nss.nssreservahoteles.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.vikour.nss.nssreservahoteles.entity.Availavility;
import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.repository.AvailavilityRepository;
import es.vikour.nss.nssreservahoteles.repository.HotelRepository;
import es.vikour.nss.nssreservahoteles.service.exceptions.HotelNotFoundException;
import es.vikour.nss.nssreservahoteles.service.impl.HotelServiceImpl;
import es.vikour.nss.nssreservahoteles.service.requests.OpenAvailavilityRequest;

@ExtendWith(SpringExtension.class)
class OpenAvailavilityServiceTest {

	@Autowired
	private HotelAvailavilityService availavilityService;
	
	@MockBean
	private HotelRepository hotelRepository;
	
	@MockBean
	private AvailavilityRepository availavilityRepository;
		
	// Testing data
	private static Hotel hotelSelected;
	private static final int HOTEL_ID = 1;
	
	
	@TestConfiguration
	public static class HotelServiceTestConfig {
		
		@Bean
		public HotelService hotelService() {
			return new HotelServiceImpl();
		}
		
	}
	
	@BeforeAll
	public static void setUpClass() {
		hotelSelected = new Hotel();
		hotelSelected.setId(HOTEL_ID);
		hotelSelected.setName("Testing hotel");
		hotelSelected.setCategory(1);
	}
	
	@BeforeEach
	public void setUp() {
		Mockito.reset(hotelRepository);
		Mockito.reset(availavilityRepository);
		
		Mockito.when(hotelRepository.findById(HOTEL_ID)).thenReturn(Optional.of(hotelSelected));
	}
	
	@Test
	@DisplayName("Al abrir disponibilidad el hotel debería existir")
	void testOpenAvailavility_whenHotelNotFound_thenError() {
		OpenAvailavilityRequest request = new OpenAvailavilityRequest();
		request.setHotelId(HOTEL_ID + 1);
		request.setStartDate(LocalDate.of(2022, 2, 1));
		request.setEndDate(LocalDate.of(2022, 2, 2));
		request.setNumberRooms(3);
		
		assertThrows(HotelNotFoundException.class, () -> {
			availavilityService.openAvailavility(request);
		});
		
		Mockito.verify(hotelRepository).findById(HOTEL_ID + 1);
	}
	
	@Test
	@DisplayName("Al abrir disponibilidad la petición debe de ser obligatoria")
	void testOpenAvailavility_whenRequestNull_thenError() {
		assertThrows(NullPointerException.class, () -> {
			availavilityService.openAvailavility(null);
		});
	}
	
	/*
	 * Generador de pruebas para las fechas
	 */
	
	public static Stream<Arguments> providerInRangeTestArguments() {
		
		Availavility avDb1 = new Availavility(LocalDate.of(2022, 2, 2), hotelSelected, 1);
		Availavility avDb2 = new Availavility(LocalDate.of(2022, 2, 3), hotelSelected, 1);
		Availavility avDb3 = new Availavility(LocalDate.of(2022, 2, 4), hotelSelected, 1);
		
		// Casos de disponibilidad en BD
		List<Availavility> emptyDB = Lists.newArrayList();
		List<Availavility> oneAvailDB = Lists.list(avDb1);
		List<Availavility> twoAvailDB = Lists.list(avDb1, avDb2);
		List<Availavility> availDbWithGap = Lists.list(avDb1, avDb3);
		
		return Stream.of(
			Arguments.of(emptyDB,LocalDate.of(2022, 2, 5),LocalDate.of(2022, 2, 6), 2,
					Lists.list(
							new Availavility(LocalDate.of(2022, 2, 5),hotelSelected, 2),
							   new Availavility(LocalDate.of(2022, 2, 6),hotelSelected, 2)
							)),

			Arguments.of(emptyDB, LocalDate.of(2022, 2, 5), LocalDate.of(2022, 2, 5), 2,
					Lists.list(new Availavility(LocalDate.of(2022, 2, 5),hotelSelected, 2))),

			
			// ============ With one data returned (2022/02/02) =======================
			// - Prueba con fechas en rango por debajo del devuelto por el repositorio 
			Arguments.of(oneAvailDB,
					LocalDate.of(2022, 2, 1),LocalDate.of(2022, 2, 2), 2,
					Lists.list(
							new Availavility(LocalDate.of(2022, 2, 1),hotelSelected, 2)
					)),
			// - Prueba con fechas el mismo rango que el devuelto por el repositorio
			Arguments.of(oneAvailDB,
					LocalDate.of(2022, 2, 2),LocalDate.of(2022, 2, 2), 2,
					Lists.newArrayList()),
			// - Prueba con fechas que incluyen y sobrepasan todo el rango devuelto por el repositorio.
			//    ej: se busca abrir por 2022/02/01 y 2022/02/03 y en la base de datos existe la fecha 2022/02/02
			Arguments.of(oneAvailDB,
					LocalDate.of(2022, 2, 1),LocalDate.of(2022, 2, 3), 2,
					Lists.list(
							new Availavility(LocalDate.of(2022, 2, 1),hotelSelected, 2),
							new Availavility(LocalDate.of(2022, 2, 3),hotelSelected, 2)
					)),
			// - Prueba con fechas en rango por encima del devuelto por la base de datos:
			Arguments.of(oneAvailDB,
					LocalDate.of(2022, 2, 2),LocalDate.of(2022, 2, 4), 2,
					Lists.list(
							new Availavility(LocalDate.of(2022, 2, 3),hotelSelected, 2),
							new Availavility(LocalDate.of(2022, 2, 4),hotelSelected, 2)
					)),
			
			
			// ============ Con dos disponibiliades devueltas desde la BD (2022/02/02-2022/02/03) =======================

			// - Prueba con fechas en rango por debajo del devuelto por el repositorio
			Arguments.of(twoAvailDB,
					LocalDate.of(2022, 1, 31),LocalDate.of(2022, 2, 3), 2,
					Lists.list(
							new Availavility(LocalDate.of(2022, 1, 31),hotelSelected, 2),
							new Availavility(LocalDate.of(2022, 2, 1),hotelSelected, 2)
					)),
			// - Prueba con fechas el mismo rango que el devuelto por el repositorio
			Arguments.of(twoAvailDB,
					LocalDate.of(2022, 2, 2),LocalDate.of(2022, 2, 3), 2,
					Lists.newArrayList()),
			// - Prueba con fechas que incluyen y sobrepasan todo el rango devuelto por el repositorio.
			Arguments.of(twoAvailDB,
					LocalDate.of(2022, 1, 31),LocalDate.of(2022, 2, 5), 2,
					Lists.list(
							new Availavility(LocalDate.of(2022, 1, 31),hotelSelected, 2),
							new Availavility(LocalDate.of(2022, 2, 1),hotelSelected, 2),
							new Availavility(LocalDate.of(2022, 2, 4),hotelSelected, 2),
							new Availavility(LocalDate.of(2022, 2, 5),hotelSelected, 2)
					)),
			// - Prueba con fechas en rango por encima del devuelto por la base de datos:
			Arguments.of(twoAvailDB,
					LocalDate.of(2022, 2, 2),LocalDate.of(2022, 2, 4), 2,
					Lists.list(
							new Availavility(LocalDate.of(2022, 2, 4),hotelSelected, 2)
					)),
			
			// ============ Con dos disponibiliades devueltas y un hueco desde la BD (2022/02/02-(nada)-2022/02/04) =======================

			// - Prueba con fechas en rango por debajo del devuelto por el repositorio
			Arguments.of(availDbWithGap,
					LocalDate.of(2022, 1, 31),LocalDate.of(2022, 2, 4), 2,
					Lists.list(
							new Availavility(LocalDate.of(2022, 1, 31),hotelSelected, 2),
							new Availavility(LocalDate.of(2022, 2, 1),hotelSelected, 2),
							new Availavility(LocalDate.of(2022, 2, 3),hotelSelected, 2) // En el hueco
					)),
			
			// - Prueba con fechas el mismo rango que el devuelto por el repositorio
			Arguments.of(availDbWithGap,
					LocalDate.of(2022, 2, 2),LocalDate.of(2022, 2, 4), 2,
					Lists.list(new Availavility(LocalDate.of(2022, 2, 3),hotelSelected, 2)), // En el hueco
			// - Prueba con fechas que incluyen y sobrepasan todo el rango devuelto por el repositorio.
			Arguments.of(availDbWithGap,
					LocalDate.of(2022, 1, 31),LocalDate.of(2022, 2, 5), 2,
					Lists.list(
							new Availavility(LocalDate.of(2022, 1, 31),hotelSelected, 2),
							new Availavility(LocalDate.of(2022, 2, 1),hotelSelected, 2),
							new Availavility(LocalDate.of(2022, 2, 3),hotelSelected, 2), // Hueco
							new Availavility(LocalDate.of(2022, 2, 5),hotelSelected, 2)
					)),
			// - Prueba con fechas en rango por encima del devuelto por la base de datos:
			Arguments.of(availDbWithGap,
					LocalDate.of(2022, 2, 2),LocalDate.of(2022, 2, 5), 2,
					Lists.list(
							new Availavility(LocalDate.of(2022, 2, 3),hotelSelected, 2), // En el hueco
							new Availavility(LocalDate.of(2022, 2, 5),hotelSelected, 2)
					))
		));
	}
	
		
	@MethodSource("providerInRangeTestArguments")
	@DisplayName("Las aperturas con fechas en las que ya existan disponibilidades, no deberán de sobreescribir las existentes")
	@ParameterizedTest(name = "{index} - Desde {1} hasta {2} con {3} habitaciones y {0} en devuelto por BD, debería de insertar {4}")
	public void testOpenAvailavility_inRange_thenCorrect(List<Availavility> avalInRangeDb, LocalDate startDate, LocalDate endDate, Integer rooms, List<Availavility> expectedInsert) {

		OpenAvailavilityRequest request = new OpenAvailavilityRequest();
		request.setHotelId(HOTEL_ID);
		request.setStartDate(startDate); 
		request.setEndDate(endDate);
		request.setNumberRooms(rooms);
		
		Mockito.when(availavilityRepository.findByHotelAndBetweenDates(hotelSelected, startDate, endDate))
			.thenReturn(avalInRangeDb);
		
		// Test
		availavilityService.openAvailavility(request);
			
		// Assert
		
		// 1. se debe de haber llamado al repositorio de hotel para comprobar si el hotel existe
		Mockito.verify(hotelRepository).findById(HOTEL_ID);
		Mockito.verify(availavilityRepository).findByHotelAndBetweenDates(hotelSelected, startDate, endDate);
			
		// 2. Se debe de haber llamado a guardar las disponibilidades, ya que se deberían de haber creado 
		ArgumentCaptor<Availavility> captor = ArgumentCaptor.forClass(Availavility.class);
		Mockito.verify(availavilityRepository, Mockito.times(expectedInsert.size())).save(captor.capture());
		assertEquals(expectedInsert, captor.getAllValues());
	}
	
	
	
	

}
