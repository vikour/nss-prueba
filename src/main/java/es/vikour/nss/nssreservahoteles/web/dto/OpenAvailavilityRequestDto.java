package es.vikour.nss.nssreservahoteles.web.dto;

import java.time.LocalDate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.OpenAvailaivlityRequestConstants.*;

@Data
@ToString
@EqualsAndHashCode
public class OpenAvailavilityRequestDto {
	
	@NotNull(message = MSG_FECHA_INICIO_OBLIGATORIA)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
	@NotNull(message = MSG_FECHA_FIN_OBLIGATORIA)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	@NotNull(message = MSG_NUMERO_HABITACIONES_OBLIGATORIO)
	@Min(value = 1, message = MSG_NUMERO_HABITACIONES_MINIMO)
	private Integer numberRooms;
	
	@JsonIgnore
	@AssertTrue(message = MSG_FECHA_INICIO_FIN_INVALIDAS)
	public boolean isStartDateIsLessOrEqualsThanEndDate() {
		return startDate != null && 
			   endDate != null &&
			   startDate.compareTo(endDate) <= 0;
	}
	
}
