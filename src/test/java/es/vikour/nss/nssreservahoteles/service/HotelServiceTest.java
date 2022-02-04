package es.vikour.nss.nssreservahoteles.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.repository.AvailavilityRepository;
import es.vikour.nss.nssreservahoteles.repository.HotelRepository;
import es.vikour.nss.nssreservahoteles.service.impl.HotelServiceImpl;

@ExtendWith(SpringExtension.class)
class HotelServiceTest {
	
	@Autowired
	private HotelService hotelService;
	
	@MockBean
	private HotelRepository hotelRepository;

	@MockBean
	private AvailavilityRepository availavilityRepository;
	
	@TestConfiguration
	public static class HotelServiceTestConfig {
		
		@Bean
		public HotelService hotelService() {
			return new HotelServiceImpl();
		}
		
	}
	
	@BeforeEach
	public void setup() {
		Mockito.reset(hotelRepository);
	}

	@Test
	void testFindAll_whenThereIsNoHotel_thenEmptyList() {
		ArrayList<Hotel> expectedList = Lists.newArrayList();
		
		// Setting up...
		Mockito.when(hotelRepository.findAll()).thenReturn(expectedList);
		
		// Test
		List<Hotel> list = hotelService.findAll();
		
		// Check
		assertEquals(expectedList, list);
	}

	@Test
	void testFindAll_whenThereAreHotels_thenListThem() {
		ArrayList<Hotel> expectedList = Lists.newArrayList();
		
		// Setting up...
		Hotel hotel = new Hotel();
		hotel.setName("Hotel A");
		hotel.setId(1);
		hotel.setCategory(1);
		expectedList.add(hotel);
		
		hotel = new Hotel();
		hotel.setName("Hotel B");
		hotel.setId(2);
		hotel.setCategory(1);
		expectedList.add(hotel);

		hotel = new Hotel();
		hotel.setName("Hotel C");
		hotel.setId(2);
		hotel.setCategory(1);
		expectedList.add(hotel);
		
		Mockito.when(hotelRepository.findAll()).thenReturn(expectedList);
		
		// Test
		List<Hotel> list = hotelService.findAll();
		
		// Check
		assertEquals(expectedList, list);
	}

}
