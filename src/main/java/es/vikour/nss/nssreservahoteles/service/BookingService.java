package es.vikour.nss.nssreservahoteles.service;

import javax.validation.Valid;

import es.vikour.nss.nssreservahoteles.entity.Booking;
import es.vikour.nss.nssreservahoteles.service.exceptions.BookingNotAvailableInDatesException;
import es.vikour.nss.nssreservahoteles.service.exceptions.HotelNotFoundException;
import es.vikour.nss.nssreservahoteles.service.requests.OpenBookingRequest;

/**
 * Servicio para realizar las gestiones necesarias con las reservas.
 * 
 * @author Víctor Manuel Ortiz Guardeño
  */

public interface BookingService {
	
	/**
	 * Intenta hacer una reserva en una fecha de inicio y fin, para un correo electrónico en un hotel concreto. Esta
	 * información se encuentra en el objeto request
	 * 
	 * @param request	Una solicitud de reserva
	 * @return	
	 * 		Devuelve la reserva que se ha generado
	 * @throws HotelNotFoundException
	 * 		Si el hotel de la solicitud no existiera
	 * @throws BookingNotAvailableInDatesException
	 * 		Si el hotel de la solicitud no tuviera disponibilidad con las fechas pasadas.
	 */
	
	Booking openBooking(@Valid OpenBookingRequest request)
		throws HotelNotFoundException, BookingNotAvailableInDatesException;

}
