package es.vikour.nss.nssreservahoteles.web.dto;

import static es.vikour.nss.nssreservahoteles.web.dto.ValidationMessageErrorContants.*;

import java.time.LocalDate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode
public class DateIntervalRequestDto {

	@NotNull(message = MSG_FECHA_INICIO_OBLIGATORIA)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "Fecha de inicio", example = "2022-02-05", pattern = "yyyy-MM-dd")
	protected LocalDate startDate;
	
	@NotNull(message = MSG_FECHA_FIN_OBLIGATORIA)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "Fecha de fin", example = "2022-02-05", pattern = "yyyy-MM-dd")
	protected LocalDate endDate;

	@JsonIgnore
	@AssertTrue(message = MSG_FECHA_INICIO_FIN_INVALIDAS)
	public boolean isStartDateIsLessOrEqualsThanEndDate() {
		return startDate != null && 
			   endDate != null &&
			   startDate.compareTo(endDate) <= 0;
	}

}