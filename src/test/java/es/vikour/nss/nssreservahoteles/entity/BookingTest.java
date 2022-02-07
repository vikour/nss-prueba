package es.vikour.nss.nssreservahoteles.entity;

import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.*;
import static es.vikour.nss.nssreservahoteles.support.AssertionUtilities.assertExistsValidationMessage;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BookingTest {
	
	public static Validator validator;
	
	// Test case
	private Booking booking;
	
	@BeforeAll
	public static void setUp() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	@BeforeEach
	public void setUpTest() {
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setCategory(1);
		hotel.setName("Test hotel");
		// se configura una reserva válida.
		booking = Booking.builder()
				.hotel(hotel)
				.dateFrom(LocalDate.of(2022, 02, 05))
				.dateTo(LocalDate.of(2022, 02, 05))
				.email("testing@gmail.com")
				.build();
	}

	@Test
	@DisplayName("Test validación: reserva válida")
	void test_whenValid_thenCorrect() {
		// Se prueba la reserva configurada en el setUpTest() que debería ser válida
		Set<ConstraintViolation<Booking>> errorsViolations = validator.validate(booking);
		assertTrue(errorsViolations.isEmpty());
	}

	@Test
	@DisplayName("El hotel debería de ser obligatorio")
	void test_whenHotelNull_thenIncorrect() {
		booking.setHotel(null);
		// Se prueba la reserva configurada en el setUpTest() que debería ser válida
		Set<ConstraintViolation<Booking>> errorsViolations = validator.validate(booking);
		assertFalse(errorsViolations.isEmpty());
		assertExistsValidationMessage(errorsViolations, ERROR_HOTEL_MANDATORY);
	}

	@Test
	@DisplayName("El fecha inicio debe de ser obligatoria")
	void test_whenDateFromNull_thenIncorrect() {
		booking.setDateFrom(null);
		// Se prueba la reserva configurada en el setUpTest() que debería ser válida
		Set<ConstraintViolation<Booking>> errorsViolations = validator.validate(booking);
		assertFalse(errorsViolations.isEmpty());
		assertExistsValidationMessage(errorsViolations, ERROR_START_DATE_MANDATORY);
	}

	@Test
	@DisplayName("El fecha fin debe de ser obligatoria")
	void test_whenDateToNull_thenIncorrect() {
		booking.setDateTo(null);
		// Se prueba la reserva configurada en el setUpTest() que debería ser válida
		Set<ConstraintViolation<Booking>> errorsViolations = validator.validate(booking);
		assertFalse(errorsViolations.isEmpty());
		assertExistsValidationMessage(errorsViolations, ERROR_END_DATE_MANDATORY);
	}
	
	@Test
	@DisplayName("El email tiene como máximo 100 carácteres")
	void test_whenEmailSizeIncorrect_thenError() {
		String email100long = "12345678901234567890123456789012345678@0123456789012345678901234567890123456789012345678901234567890";
		String email101long = "12345678901234567890123456789012345678@01234567890123456789012345678901234567890123456789012345678901";

		booking.setEmail(email101long);
		Set<ConstraintViolation<Booking>> errorsViolations = validator.validate(booking);
		assertFalse(errorsViolations.isEmpty());
		assertExistsValidationMessage(errorsViolations, ERROR_EMAIL_SIZE);
		
		// Longitud exacta
		booking.setEmail(email100long);
		errorsViolations = validator.validate(booking);
		assertTrue(errorsViolations.isEmpty());
	}
	
	static Stream<Arguments> emailFormatIncorrectProvider() {
		return Stream.of(
				Arguments.of("@test.com", ERROR_EMAIL_FORMAT),
				Arguments.of("ddatest.com", ERROR_EMAIL_FORMAT),
				Arguments.of("ffftestom", ERROR_EMAIL_FORMAT),
				Arguments.of("ddjda@", ERROR_EMAIL_FORMAT),
				Arguments.of("addaad.ddd@", ERROR_EMAIL_FORMAT),
				Arguments.of("@", ERROR_EMAIL_FORMAT)
			);
	}

	@DisplayName("El formato del email debería de ser correcto")
	@ParameterizedTest(name = "{index} el email \"{0}\" es incorrecto")
	@MethodSource("emailFormatIncorrectProvider")
	void test_whenEmailIncorrect_thenIncorrect(String email, String errorMsg) {
		booking.setEmail(email);
		// Se prueba la reserva configurada en el setUpTest() que debería ser válida
		Set<ConstraintViolation<Booking>> errorsViolations = validator.validate(booking);
		assertFalse(errorsViolations.isEmpty());
		assertExistsValidationMessage(errorsViolations, errorMsg);
	}
	
	
	static Stream<Arguments> dateOrderTestProvider() {
		return Stream.of(
			// (startDate, endDate, messageError (if any))
			Arguments.of(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 2, 1), null),
			Arguments.of(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 2, 2), null),
			Arguments.of(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 1, 1), ERROR_START_GT_END_DATE),
			Arguments.of(LocalDate.of(2022, 2, 2), LocalDate.of(2022, 2, 1), ERROR_START_GT_END_DATE),
			Arguments.of(LocalDate.of(2022, 2, 1), LocalDate.of(2021, 2, 1), ERROR_START_GT_END_DATE)
		);
	}


	@DisplayName("La fecha de inicio debe ser menor o igual que la fin")
	@ParameterizedTest
	@MethodSource("dateOrderTestProvider")
	void test_whenDateOrderIncorrect_thenIncorrect(LocalDate dateFrom, LocalDate dateTo, String errorMsg) {
		booking.setDateFrom(dateFrom);
		booking.setDateTo(dateTo);
		// Se prueba la reserva configurada en el setUpTest() que debería ser válida
		Set<ConstraintViolation<Booking>> errorsViolations = validator.validate(booking);
		
		if (errorMsg != null) {
			assertFalse(errorsViolations.isEmpty());
			assertExistsValidationMessage(errorsViolations, errorMsg);
		}
		else 
			assertTrue(errorsViolations.isEmpty());
	}

}
