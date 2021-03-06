package es.vikour.nss.nssreservahoteles.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import es.vikour.nss.nssreservahoteles.service.exceptions.BookingNotAvailableInDatesException;
import es.vikour.nss.nssreservahoteles.service.exceptions.BookingNotFoundException;
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

	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseEntity<ApiError> bookingNotFoundException(BookingNotFoundException ex) {
		log.warn(ex);
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "No se ha encontrado la reserva", ex);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler({BookingNotAvailableInDatesException.class})
	public ResponseEntity<ApiError> bookingNotAvaliable(BookingNotAvailableInDatesException ex) {
		log.warn(ex);
		ApiError apiError = new ApiError(HttpStatus.CONFLICT, "No se ha podido realizar la reserva", ex);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> validationErrorException(MethodArgumentNotValidException ex) {
		log.warn("Error de validaci??n: {}", ex.getBindingResult().getAllErrors());
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Error de validaci??n", ex);
		apiError.addValidationErrors(ex.getBindingResult());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiError> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		String msg = String.format("El campo '%s' con el valor '%s' es incorrecto", ex.getName(), ex.getValue());
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, msg, ex);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiError> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Se esperaba el par??metro " + ex.getParameterName(), ex);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> httpMessageNotReadable(HttpMessageNotReadableException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "JSON mal formado", ex);
		return buildResponseEntity(apiError);
	}


	private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
	}

}
