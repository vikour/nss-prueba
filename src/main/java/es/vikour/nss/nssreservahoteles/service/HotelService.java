package es.vikour.nss.nssreservahoteles.service;

import java.util.List;

import es.vikour.nss.nssreservahoteles.entity.Hotel;

/**
 * Servicio de Hoteles. Este servicio ofrece información acerca de 
 * los hoteles.
 * 
 * @author Víctor Manuel Ortiz Guardeño
 */

public interface HotelService {
	
	/**
	 * Devuelve un listado de los hoteles almacenados en el origen de datos.
	 * 
	 * @return Una Lista de hoteles
	 */
	
	public List<Hotel> findAll();

}
