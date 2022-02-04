package es.vikour.nss.nssreservahoteles.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import es.vikour.nss.nssreservahoteles.service.exceptions.HotelNotFoundException;
import es.vikour.nss.nssreservahoteles.web.dto.error.ApiError;
import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class GlobalErrorControllerHandler {

	@ExceptionHandler({HotelNotFoundException.class})
	public ResponseEntity<ApiError> hotelNotFoundException(HotelNotFoundException ex) {
		log.warn(ex);
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "No se ha encontrado el hotel", ex);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> validationErrorException(MethodArgumentNotValidException ex) {
		log.warn("Error de validación: {}", ex.getBindingResult().getAllErrors());
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Error de validación", ex);
		apiError.addValidationErrors(ex.getBindingResult());
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<ApiError>(apiError, apiError.getHttpStatus());
	}
	
}
