package es.vikour.nss.nssreservahoteles.web.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import es.vikour.nss.nssreservahoteles.service.requests.OpenBookingRequest;
import es.vikour.nss.nssreservahoteles.web.dto.OpenBookingRequestDto;

@Mapper
public interface OpenBookingRequestToDtoConverter {

	@Mappings({
		@Mapping(target = "hotelId", source = "hotelId"),
		@Mapping(target = "startDate", source = "dto.startDate"),
		@Mapping(target = "endDate", source="dto.endDate"),
		@Mapping(target = "email", source="dto.email")
	})
	OpenBookingRequest toEntity(Integer hotelId, OpenBookingRequestDto dto);
}
