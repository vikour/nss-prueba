package es.vikour.nss.nssreservahoteles.web.dto.error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 	Esta clase representa un error del Api. Del error interesa conocer el estado HTTP resultante,
 * 	la hora a la que ocurrió, el mensaje y si tiene sub errores.
 * <p>
 * 	Un caso ejemplo de sub errores se produce cuando hay un error de validación. En este caso, se incluyen más
 *  campos para indicar el motivo.  
 * 
 * @author vikour
 * @see ApiValidationError
 */

@Data
@JsonInclude(content = Include.NON_NULL)
public class ApiError {
	
	private HttpStatus httpStatus;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;
	@JsonInclude(Include.NON_EMPTY)
	private List<ApiSubError> subErrors;
	
	

	public ApiError(HttpStatus httpStatus, String message, String debugMessage) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
		this.timestamp = LocalDateTime.now();
		this.subErrors = new ArrayList<ApiSubError>();
		this.debugMessage= debugMessage;
	}

	public ApiError(HttpStatus status, Throwable ex) {
		this(status, "Error inesperado", ex.getLocalizedMessage());
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex) {
		this(status, message, ex.getLocalizedMessage());
	}

	public void addValidationErrors(BindingResult bindingResult) {
		
		for (FieldError error : bindingResult.getFieldErrors()) 
			addValidationErrors(error);
				
	}
	
	public void addValidationErrors(FieldError fieldError) {
		ApiValidationError apiValidationError = ApiValidationError.builder()
			.object(fieldError.getObjectName())
			.field(fieldError.getField())
			.rejectedValue(fieldError.getRejectedValue())
			.message(fieldError.getDefaultMessage())
			.build();
		subErrors.add(apiValidationError);
	}
	

}
