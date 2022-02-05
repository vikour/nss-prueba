package es.vikour.nss.nssreservahoteles.service.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static es.vikour.nss.nssreservahoteles.service.requests.Constants.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenBookingRequest extends HotelDateIntervalRequest {
	
	@Email(message = MSG_EMAIL_FORMAT)
	@Size(max = 100, message = MSG_EMAIL_SIZE)
	@NotNull(message = MSG_EMAIL_MANDATORY)
	private String email;

}
