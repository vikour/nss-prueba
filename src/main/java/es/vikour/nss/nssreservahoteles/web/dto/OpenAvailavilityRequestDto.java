package es.vikour.nss.nssreservahoteles.web.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "OpenAvailavilityRequest",description = "Solicitud para abrir disponibilidad en un hotel")
public class OpenAvailavilityRequestDto extends DateIntervalRequestDto {
	
	@NotNull(message = MSG_NUMERO_HABITACIONES_OBLIGATORIO)
	@Min(value = 1, message = MSG_NUMERO_HABITACIONES_MINIMO)
	@Schema(description = "Número de habitaciones que se quieren hacer disponibles", example = "2")
	private Integer numberRooms;
	
	
}
