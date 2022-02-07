package es.vikour.nss.nssreservahoteles.service.requests;

import static es.vikour.nss.nssreservahoteles.service.requests.Constants.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class OpenAvailavilityRequest extends HotelDateIntervalRequest {
	
	@NotNull(message = MSG_NUMERO_HABITACIONES_OBLIGATORIO)
	@Min(value = 1, message = MSG_NUMERO_HABITACIONES_MINIMO)
	private Integer numberRooms;

}
