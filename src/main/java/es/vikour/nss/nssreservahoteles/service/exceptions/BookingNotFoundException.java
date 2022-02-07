package es.vikour.nss.nssreservahoteles.service.exceptions;

/**
 * Clase lanzada cuando no se encuentra un hotel
 * 
 * @author Víctor Manuel Ortiz Guardeño
 */

public class BookingNotFoundException extends HotelBookingRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BookingNotFoundException(int bookingIdNotFound) {
		this("No existe la resrva con id : \"" + bookingIdNotFound + "\"");
	}

	public BookingNotFoundException(String msg) {
		super(msg);
	}

}
