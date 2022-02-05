package es.vikour.nss.nssreservahoteles.service.requests;

import static es.vikour.nss.nssreservahoteles.service.requests.Constants.MSG_FECHA_FIN_OBLIGATORIA;
import static es.vikour.nss.nssreservahoteles.service.requests.Constants.MSG_FECHA_INICIO_FIN_INVALIDAS;
import static es.vikour.nss.nssreservahoteles.service.requests.Constants.MSG_FECHA_INICIO_OBLIGATORIA;
import static es.vikour.nss.nssreservahoteles.service.requests.Constants.MSG_HOTEL_ID_OBLIGATORIO;

import java.time.LocalDate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class HotelDateIntervalRequest {

	@NotNull(message = MSG_HOTEL_ID_OBLIGATORIO)
	private Integer hotelId;
	@NotNull(message = MSG_FECHA_INICIO_OBLIGATORIA)
	private LocalDate startDate;
	@NotNull(message = MSG_FECHA_FIN_OBLIGATORIA)
	private LocalDate endDate;

	@AssertTrue(message = MSG_FECHA_INICIO_FIN_INVALIDAS)
	public boolean isStartDateIsLessOrEqualsThanEndDate() {
		return startDate != null && 
			   endDate != null &&
			   startDate.compareTo(endDate) <= 0;
	}

}