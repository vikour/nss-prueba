package es.vikour.nss.nssreservahoteles.entity;

/**
 * Esta clase contiene constantes relacionadas con la validación de las entidades
 * 
 * @author Víctor Manuel Ortiz Guardeño
 */

class EntityValidationConstants {
	
	static final String ERROR_HOTEL_NAME = "El nombre del hotel debe de estar relleno";
	static final String ERROR_HOTEL_NAME_SIZE = "El nombre del hotel tiene más de 100 carácteres";
	static final String ERROR_HOTEL_CATEGORY = "La categoría del hotel no debe de ser nula";
	static final String ERROR_HOTEL_MANDATORY = "El hotel es obligatorio";
	
	static final String ERROR_DATE_MANDATORY = "La fecha es obligatoria";
	static final String ERROR_START_DATE_MANDATORY = "La fecha inicio es obligatoria";
	static final String ERROR_END_DATE_MANDATORY = "La fecha fin es obligatoria";
	static final String ERROR_START_GT_END_DATE = "La fecha inicio debe de ser menor o igual que la final";
	
	static final String ERROR_ROOMS_MANDATORY = "El número de habitaciones es obligatorio";
	static final String ERROR_ROOMS_GT_1 = "El número de habitaciones es como mínimo 1";

	static final String ERROR_EMAIL_MANDATORY = "El email es obligatorio";
	static final String ERROR_EMAIL_FORMAT = "El email no tienen un formato correcto";
	static final String ERROR_EMAIL_SIZE = "El email no puede tener una longitud superior a 100 carácteres";
}
