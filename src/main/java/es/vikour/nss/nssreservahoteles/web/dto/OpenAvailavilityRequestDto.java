package es.vikour.nss.nssreservahoteles.web.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OpenAvailavilityRequestDto extends DateIntervalRequestDto {
	
	@NotNull(message = MSG_NUMERO_HABITACIONES_OBLIGATORIO)
	@Min(value = 1, message = MSG_NUMERO_HABITACIONES_MINIMO)
	private Integer numberRooms;
	
	
}
