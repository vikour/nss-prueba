package es.vikour.nss.nssreservahoteles.service.exceptions;

/**
 * Clase lanzada cuando no se encuentra un hotel
 * 
 * @author Víctor Manuel Ortiz Guardeño
 */

public class HotelNotFoundException extends HotelBookingRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public HotelNotFoundException(int hotelIdNotFound) {
		this("No existe el hotel con id : \"" + hotelIdNotFound + "\"");
	}

	public HotelNotFoundException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
