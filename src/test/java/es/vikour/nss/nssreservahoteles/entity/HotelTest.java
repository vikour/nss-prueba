package es.vikour.nss.nssreservahoteles.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HotelTest {
	
	public static Validator validator;
	
	@BeforeAll
	public static void setUp() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}


	private void assertExistsValidationMessage(Set<ConstraintViolation<Hotel>> validate, String message) {
		boolean exists = validate.stream()
			.anyMatch((v) -> v.getMessage().equals(message));
		
		if (!exists) 
			fail(String.format("Expected message validation : \"%s\"", message));
	}
	
	@Test
	@DisplayName("Cuando el Id es un valor nulo, entonces error")
	void testId_whenNull_thenError() {
		Hotel hotel = new Hotel();
		hotel.setId(null);
		hotel.setName("Hotel");
		hotel.setCategory(1);
		Set<ConstraintViolation<Hotel>> validate = validator.validate(hotel);
		assertFalse(validate.isEmpty());
		assertEquals(1, validate.size());
		assertExistsValidationMessage(validate, EntityValidationConstants.ERROR_HOTEL_ID_NULO);
	}
	
	@Test
	@DisplayName("Cuando el Id es un valor entero, entonces correcto")
	void testId_whenAnInteger_thenCorrect() {
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("Hotel");
		hotel.setCategory(1);
		Set<ConstraintViolation<Hotel>> validate = validator.validate(hotel);
		assertTrue(validate.isEmpty());
		assertEquals(1, hotel.getId());
	}
	
	@Test
	@DisplayName("Cuando el nombre es nulo, entonces error")
	void testName_whenNull_thenError() {
		Hotel hotel = new Hotel();
		hotel.setName(null);
		hotel.setId(1);
		hotel.setCategory(1);
		Set<ConstraintViolation<Hotel>> validate = validator.validate(hotel);
		assertFalse(validate.isEmpty());
		assertEquals(1, validate.size());
		assertExistsValidationMessage(validate, EntityValidationConstants.ERROR_HOTEL_NAME);
	}
	
	@Test
	@DisplayName("Cuando el nombre esta vacío, entonces error")
	void testName_whenEmpty_thenError() {
		Hotel hotel = new Hotel();
		hotel.setName("");
		hotel.setCategory(1);
		hotel.setId(1);
		Set<ConstraintViolation<Hotel>> validate = validator.validate(hotel);
		assertFalse(validate.isEmpty());
		assertEquals(1, validate.size());
		assertExistsValidationMessage(validate, EntityValidationConstants.ERROR_HOTEL_NAME);
	}
	
	@Test
	@DisplayName("Cuando el nombre es valor no nulo y no vacío, entonces correcto")
	void testId_whenNotEmptyOrNull_thenCorrect() {
		Hotel hotel = new Hotel();
		hotel.setName("Hotel");
		hotel.setId(1);
		hotel.setCategory(1);
		Set<ConstraintViolation<Hotel>> validate = validator.validate(hotel);
		assertTrue(validate.isEmpty());
		assertEquals("Hotel", hotel.getName());
	}
	
	@Test
	@DisplayName("Cuando la categoría es nula, entonces error")
	void testCategory_whenNull_thenError() {
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("Hotel");
		hotel.setCategory(null);
		Set<ConstraintViolation<Hotel>> validate = validator.validate(hotel);
		assertFalse(validate.isEmpty());
		assertEquals(1, validate.size());
		assertExistsValidationMessage(validate, EntityValidationConstants.ERROR_HOTEL_CATEGORY);
	}
	
	@Test
	@DisplayName("Cuando la categoría es un entero, entonces correcto")
	void testCategory_whenAnInteger_thenCorrect() {
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("Hotel");
		hotel.setCategory(1);
		Set<ConstraintViolation<Hotel>> validate = validator.validate(hotel);
		assertTrue(validate.isEmpty());
		assertEquals(1, hotel.getCategory());
	}
	
	

}
