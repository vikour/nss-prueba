package es.vikour.nss.nssreservahoteles.service.requests;

import java.time.LocalDate;

import static es.vikour.nss.nssreservahoteles.service.requests.Constants.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class OpenAvailavilityRequest {
	
	@NotNull(message = MSG_HOTEL_ID_OBLIGATORIO)
	private Integer hotelId;
	
	@NotNull(message = MSG_FECHA_INICIO_OBLIGATORIA)
	private LocalDate startDate;
	
	@NotNull(message = MSG_FECHA_FIN_OBLIGATORIA)
	private LocalDate endDate;
	
	@NotNull(message = MSG_NUMERO_HABITACIONES_OBLIGATORIO)
	@Min(value = 1, message = MSG_NUMERO_HABITACIONES_MINIMO)
	private Integer numberRooms;
	
	@AssertTrue(message = MSG_FECHA_INICIO_FIN_INVALIDAS)
	public boolean isStartDateIsLessOrEqualsThanEndDate() {
		return startDate != null && 
			   endDate != null &&
			   startDate.compareTo(endDate) <= 0;
	}

}
