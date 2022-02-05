package es.vikour.nss.nssreservahoteles.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenBookingRequestDto extends DateIntervalRequestDto {
	
	
	@Email(message = MSG_EMAIL_FORMAT)
	@Size(max=100, message = MSG_EMAIL_SIZE)
	@NotNull(message = MSG_EMAIL_MANDATORY)
	private String email;

}
