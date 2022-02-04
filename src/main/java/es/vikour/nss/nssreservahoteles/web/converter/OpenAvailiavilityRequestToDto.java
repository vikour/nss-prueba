package es.vikour.nss.nssreservahoteles.web.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import es.vikour.nss.nssreservahoteles.service.requests.OpenAvailavilityRequest;
import es.vikour.nss.nssreservahoteles.web.dto.OpenAvailavilityRequestDto;

@Mapper
public interface OpenAvailiavilityRequestToDto {

	@Mappings({
		@Mapping(target = "hotelId", source = "hotelId"),
		@Mapping(target = "startDate", source = "dto.startDate"),
		@Mapping(target = "endDate", source="dto.endDate"),
		@Mapping(target = "numberRooms", source="dto.numberRooms")
	})
	OpenAvailavilityRequest toEntity(Integer hotelId, OpenAvailavilityRequestDto dto);
	
}
