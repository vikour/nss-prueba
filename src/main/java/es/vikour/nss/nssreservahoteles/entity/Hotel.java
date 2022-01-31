package es.vikour.nss.nssreservahoteles.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.*;

@Data
@ToString
@EqualsAndHashCode
public class Hotel {
	
	@NotNull(message= ERROR_HOTEL_ID_NULO)
	private Integer id;
	
	@NotEmpty(message = ERROR_HOTEL_NAME)
	private String name;
	
	@NotNull(message = ERROR_HOTEL_CATEGORY)
	private Integer category;

}
