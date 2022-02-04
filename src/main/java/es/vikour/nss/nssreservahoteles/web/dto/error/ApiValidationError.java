package es.vikour.nss.nssreservahoteles.web.dto.error;

import lombok.Builder;
import lombok.Data;

/**
 * Representa un error de validación. Del error es interesante conocer el objeto en el que se produjo el 
 * error, el campo, el valor invalido y el mensaje de error.
 * 
 * @author Víctor Manuel Ortiz Guardeño
 */

@Data
@Builder
public class ApiValidationError implements ApiSubError {
	
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;
	
}
