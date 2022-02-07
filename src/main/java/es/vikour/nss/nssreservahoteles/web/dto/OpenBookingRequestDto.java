package es.vikour.nss.nssreservahoteles.web.dto;

import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.MSG_EMAIL_FORMAT;
import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.MSG_EMAIL_MANDATORY;
import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.MSG_EMAIL_SIZE;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "OpenBookingRequest",description = "Solicitud para abrir una reserva en un hotel")
public class OpenBookingRequestDto extends DateIntervalRequestDto {


	@Email(message = MSG_EMAIL_FORMAT)
	@Size(max=100, message = MSG_EMAIL_SIZE)
	@NotNull(message = MSG_EMAIL_MANDATORY)
	@Schema(description = "Email de la persona que va ha hacer la reserva", example = "test@nss.prueba.es")
	private String email;

}
