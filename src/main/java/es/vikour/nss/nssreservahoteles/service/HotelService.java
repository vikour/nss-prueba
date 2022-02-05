package es.vikour.nss.nssreservahoteles.service;

import java.time.LocalDate;
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
	
	/**
	 * Devuelve un listado de todos los hoteles que tienen disponibilidad para las fechas 
	 * pasadas como argumento.
	 * <p>
	 * Nota: Se espera que <code>dateFrom</code> sea menor o igual que <code>dateTo</code>, si no
	 * se cumple esta precondición, puede que no devuelva los resultados esperados.
	 * 
	 * @param dateFrom	Fecha inicio de disponibilidad
	 * @param dateTo	Fecha de fin de disponibilidad
	 * 
	 * @return	Un listado con los hoteles con posiblidad de reservar
	 */
	
	public List<Hotel> findAllWithRoomsAvailableBetweenDates(LocalDate dateFrom, LocalDate dateTo);


}
