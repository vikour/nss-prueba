package es.vikour.nss.nssreservahoteles.support;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Set;

import javax.validation.ConstraintViolation;

import es.vikour.nss.nssreservahoteles.entity.Hotel;

/**
 * Clase de soporte que contiene funcionalidades para la comprobacion
 * en los test.
 *   
 * @author Víctor Manuel Ortiz Guardeño
 */

public final class AssertionUtilities {
	
	private AssertionUtilities() {
		throw new IllegalStateException("No se debería de instanciar, es una clase de soporte");
	}

	/**
	 * Comprueba que exista un mensaje en el conjunto de violaciones de resetricciones. Si no encontrara el mensaje
	 * forzaría un fallo de JUnit.
	 * 
	 * @param setConstraintViolations Conjunto de errores tras una validación.
	 * @param expectedMessage	El mensaje buscado dentro del conjunto de errores.
	 */

	public static <T> void assertExistsValidationMessage(Set<ConstraintViolation<T>> setConstraintViolations, String expectedMessage) {
		boolean exists = setConstraintViolations.stream()
			.anyMatch((v) -> v.getMessage().equals(expectedMessage));
		
		if (!exists) 
			fail(String.format("Expected message validation : \"%s\"", expectedMessage));
	}
	
}
