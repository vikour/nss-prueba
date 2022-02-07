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

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Información acerca del error que se ha producido")
public class ApiError {
	
	@Schema(description = "Error HTTP devuelto", enumAsRef = true, example = "404")
	private HttpStatus httpStatus;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	@Schema(description = "Fecha y hora del error", pattern = "dd-MM-yyyy hh:mm:ss", example = "2022-02-05 14:33:02")
	private LocalDateTime timestamp;
	
	@Schema(description = "Mensaje del error producido", example = "Mensaje de error")
	private String message;
	
	@Schema(description = "Mensaje exacto del error que se ha producido en el servidor, por motivo de depuración", example = "DEBUG mensaje de error")
	private String debugMessage;
	
	@JsonInclude(Include.NON_EMPTY)
	@Schema(description = "Errores anidados al principal que extienden la información", subTypes = {ApiValidationError.class})
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
