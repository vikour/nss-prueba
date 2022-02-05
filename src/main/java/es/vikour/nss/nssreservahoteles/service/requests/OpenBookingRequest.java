package es.vikour.nss.nssreservahoteles.service.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpenBookingRequest extends HotelDateIntervalRequest {
	
	@Email
	@Size(max = 100)
	@NotNull()
	private String email;

}
