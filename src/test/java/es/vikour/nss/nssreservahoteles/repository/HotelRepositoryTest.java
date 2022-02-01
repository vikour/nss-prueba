package es.vikour.nss.nssreservahoteles.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import es.vikour.nss.nssreservahoteles.entity.Hotel;

@SpringBootTest
class HotelRepositoryTest {
	
	@Autowired
	private HotelRepository repo;

	@Test
	void test() {
		Hotel hotelA = new Hotel();
		hotelA.setName("Hotel A");
		hotelA.setCategory(1);
		
		repo.save(hotelA);
		
		repo.findAll().stream()
		.forEach(System.out::println);
	}

}
