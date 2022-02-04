package es.vikour.nss.nssreservahoteles.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import es.vikour.nss.nssreservahoteles.service.exceptions.HotelNotFoundException;
import es.vikour.nss.nssreservahoteles.service.requests.OpenAvailavilityRequest;

@Service
public interface HotelAvailavilityService {
	
	/**
	 * Intenta abrir disponibilidad en el hotel para un rango de fechas con un número de habitaciones.
	 * 
	 * @param request 	La petición de apertura, que contiene el id del hotel, la fecha inicio, fecha fin y 
	 * 					número de habitaciones.
	 * 
	 * @exception	HotelNotFoundException	Si el Hotel no existiera en la base de datos.
	 */
	
	void openAvailavility(@Valid OpenAvailavilityRequest request)
	throws HotelNotFoundException;
	
}
