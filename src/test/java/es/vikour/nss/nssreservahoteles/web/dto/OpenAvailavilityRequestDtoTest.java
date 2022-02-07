package es.vikour.nss.nssreservahoteles.web.dto;

import static es.vikour.nss.nssreservahoteles.support.AssertionUtilities.assertExistsValidationMessage;
import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.MSG_FECHA_FIN_OBLIGATORIA;
import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.MSG_FECHA_INICIO_FIN_INVALIDAS;
import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.MSG_FECHA_INICIO_OBLIGATORIA;
import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.MSG_NUMERO_HABITACIONES_MINIMO;
import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.MSG_NUMERO_HABITACIONES_OBLIGATORIO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OpenAvailavilityRequestDtoTest {

	// Parámetros válidos para las comprobaciones
	private static final int VALID_NUMBER_ROOMS = 2;
	private static final LocalDate VALID_END_DATE = LocalDate.of(2022, 1, 17);
	private static final LocalDate VALID_START_DATE = LocalDate.of(2022, 1, 15);

	public static Validator validator;
	public OpenAvailavilityRequestDto validRequest;

	@BeforeAll
	public static void setUp() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@BeforeEach
	public void setUpTest() {
		// Se configura una petición válida para después ir alterandola en cada test
		validRequest = new OpenAvailavilityRequestDto();
		validRequest.setStartDate(VALID_START_DATE);
		validRequest.setEndDate(VALID_END_DATE);
		validRequest.setNumberRooms(VALID_NUMBER_ROOMS);
	}

	@Test
	@DisplayName("Validación petición válida")
	public void testValidRequest_thenCorrect() {
		Set<ConstraintViolation<OpenAvailavilityRequestDto>> validations = validator.validate(validRequest);

		assertTrue(validations.isEmpty());
		assertEquals(VALID_NUMBER_ROOMS, validRequest.getNumberRooms());
		assertEquals(VALID_START_DATE, validRequest.getStartDate());
		assertEquals(VALID_END_DATE, validRequest.getEndDate());
	}

	@Test
	@DisplayName("El número de habitaciones no debería de ser nulo")
	public void testNumberRooms_whenNull_thenError() {
		validRequest.setNumberRooms(null);

		Set<ConstraintViolation<OpenAvailavilityRequestDto>> validations = validator.validate(validRequest);

		assertExistsValidationMessage(validations, MSG_NUMERO_HABITACIONES_OBLIGATORIO);
	}


	@Test
	@DisplayName("El número de habitaciones no debería de ser menor que 1")
	public void testNumberRooms_whenZero_thenError() {
		// Se prueba con 0
		validRequest.setNumberRooms(0);
		Set<ConstraintViolation<OpenAvailavilityRequestDto>> validations = validator.validate(validRequest);
		assertExistsValidationMessage(validations, MSG_NUMERO_HABITACIONES_MINIMO);

		// se prueba con -2
		validRequest.setNumberRooms(-2);
		validations = validator.validate(validRequest);
		assertExistsValidationMessage(validations, MSG_NUMERO_HABITACIONES_MINIMO);
	}

	@Test
	@DisplayName("La fecha de inicio debería ser no nula")
	public void testStartDate_whenNull_thenError() {
		validRequest.setStartDate(null);
		Set<ConstraintViolation<OpenAvailavilityRequestDto>> validations = validator.validate(validRequest);
		assertExistsValidationMessage(validations, MSG_FECHA_INICIO_OBLIGATORIA);
	}

	@Test
	@DisplayName("La fecha fin debería ser no nula")
	public void testEndDate_whenNull_thenError() {
		validRequest.setEndDate(null);
		Set<ConstraintViolation<OpenAvailavilityRequestDto>> validations = validator.validate(validRequest);
		assertExistsValidationMessage(validations, MSG_FECHA_FIN_OBLIGATORIA);
	}

	@Test
	@DisplayName("La fecha inicio debería ser menor que la fin")
	public void testStartDate_whenNotLessThanEndDate_thenError() {
		// Test 1
		validRequest.setStartDate(LocalDate.of(2022, 5, 1));
		validRequest.setEndDate(LocalDate.of(2022, 4, 1));
		Set<ConstraintViolation<OpenAvailavilityRequestDto>> validations = validator.validate(validRequest);
		assertExistsValidationMessage(validations, MSG_FECHA_INICIO_FIN_INVALIDAS);

		// Test 2
		validRequest.setStartDate(LocalDate.of(2022, 5, 1));
		validRequest.setEndDate(LocalDate.of(2021, 6, 1));
		validations = validator.validate(validRequest);
		assertExistsValidationMessage(validations, MSG_FECHA_INICIO_FIN_INVALIDAS);

		// Test 3
		validRequest.setStartDate(LocalDate.of(2022, 5, 5));
		validRequest.setEndDate(LocalDate.of(2022, 5, 4));
		validations = validator.validate(validRequest);
		assertExistsValidationMessage(validations, MSG_FECHA_INICIO_FIN_INVALIDAS);
	}

	@Test
	@DisplayName("La fecha inicio y fin pueden ser iguales")
	public void testEndDate_whenNotHigherThanEndDate_thenError() {
		validRequest.setStartDate(LocalDate.of(2022, 4, 1));
		validRequest.setEndDate(LocalDate.of(2022, 4, 1));
		Set<ConstraintViolation<OpenAvailavilityRequestDto>> validations = validator.validate(validRequest);
		assertTrue(validations.isEmpty());
	}

}
