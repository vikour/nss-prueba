package es.vikour.nss.nssreservahoteles.web.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;

@Data
@Schema(name = "Booking",description = "Reserva hecha en un hotel")
public class BookingDto {

	@Schema(description = "Identificador de la reserva", accessMode = AccessMode.READ_ONLY, example = "1")
	private Integer id;

	@Schema(description = "Identificador del hotel en el que se ha hecho la reserva", accessMode = AccessMode.READ_ONLY, example = "1")
	private Integer hotelId;
	
	@Schema(description = "Fecha inicio de la reserva", accessMode = AccessMode.READ_ONLY, example = "2022-02-05")
	private LocalDate dateFrom;
	
	@Schema(description = "Fecha fin de la reserva", accessMode = AccessMode.READ_ONLY, example = "2022-02-06")
	private LocalDate dateTo;
	
	@Schema(description = "Email de la persona que ha hecho la reserva", accessMode = AccessMode.READ_ONLY, example = "test@nss.prueba.es")
	private String email;
}
