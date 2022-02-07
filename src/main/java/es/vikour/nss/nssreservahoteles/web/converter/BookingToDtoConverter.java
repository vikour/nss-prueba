package es.vikour.nss.nssreservahoteles.web.converter;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import es.vikour.nss.nssreservahoteles.entity.Booking;
import es.vikour.nss.nssreservahoteles.web.dto.BookingDto;

@Mapper
public interface BookingToDtoConverter {
	

	@Mappings({
		@Mapping(target = "id", source = "id"),
		@Mapping(target = "hotelId", source = "hotel.id"),
		@Mapping(target = "dateFrom", source="dateFrom"),
		@Mapping(target = "dateTo", source="dateTo"),
		@Mapping(target = "email", source="email")
	})
	BookingDto toDto(Booking booking);
	

	default List<BookingDto> toDto(List<Booking> bookingList) {
		List<BookingDto> bookingDtoList = new ArrayList<>();

		for (Booking booking : bookingList)
			bookingDtoList.add(toDto(booking));
		
		return bookingDtoList;
	}

}
