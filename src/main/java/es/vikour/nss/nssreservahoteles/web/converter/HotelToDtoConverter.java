package es.vikour.nss.nssreservahoteles.web.converter;

import java.util.List;

import org.mapstruct.Mapper;

import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.web.dto.HotelDto;

/**
 * Esta clase se encarga de convertir la entidad de hoteles a DTO y viceversa.
 * 
 * @author Víctor Manuel Ortiz Guardeño
 */

@Mapper(componentModel = "spring")
public interface HotelToDtoConverter {

	/**
	 * Convierte una entidad <code>Hotel</code> a su DTO <code>HotelDto</code>
	 * 
	 * @param hotel	Una entidad hotel
	 * @return El DTO resultante de la conversión.
	 */
	
	HotelDto hotelToDto(Hotel hotel);

	/**
	 * Convierte una lista de entidades <code>Hotel</code> a otra de DTOs <code>HotelDto</code>
	 * 
	 * @param hotelList	Una lista de entidades <code>Hotel</code>
	 * @return Una lista resultante de la conversión de la lista en DTO
	 */
	
	List<HotelDto> hotelToDto(List<Hotel> hotelList);
	
}
