package es.vikour.nss.nssreservahoteles.service.exceptions;

import org.springframework.core.NestedRuntimeException;

public class HotelBookingRuntimeException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HotelBookingRuntimeException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
