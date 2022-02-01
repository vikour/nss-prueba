package es.vikour.nss.nssreservahoteles.web.converter;

import java.util.List;

import org.mapstruct.Mapper;

import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.web.dto.HotelDto;

@Mapper(componentModel = "spring")
public interface HotelToDtoConverter {

	HotelDto hotelToDto(Hotel hotel);
	
	List<HotelDto> hotelToDto(List<Hotel> hotelList);
	
}
