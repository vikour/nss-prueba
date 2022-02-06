package es.vikour.nss.nssreservahoteles.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import es.vikour.nss.nssreservahoteles.entity.Booking;
import es.vikour.nss.nssreservahoteles.service.exceptions.BookingNotAvailableInDatesException;
import es.vikour.nss.nssreservahoteles.service.exceptions.BookingNotFoundException;
import es.vikour.nss.nssreservahoteles.service.exceptions.HotelNotFoundException;
import es.vikour.nss.nssreservahoteles.service.requests.HotelDateIntervalRequest;
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
	
	/**
	 * Busca en la base de datos las reservas hechas en el hotel pasado como argumento entre las fechas. En 
	 * el resultado se incluiran aquellas reservas que contengan días entre las fechas pasadas. 
	 * 
	 * @param request	Petición de consulta de reservas
	 * 
	 * @return	Un listado con las reservas en el hotel con días entre las fechas proporcionadas.
	 */

	List<Booking> queryHotelBooking(@Valid HotelDateIntervalRequest request);
	
	/**
	 * Permite obtener una reserva por su ID
	 * 
	 * @param bookingId	El id de la reserva buscada
	 * @return Una reserva con el ID pasado como argumento 
	 * 
	 * @throws BookingNotFoundException
	 * 	Si no existiera la reserva con ID.
	 */
	
	Booking queryBooking(@NotNull Integer bookingId) throws BookingNotFoundException;
	
}
