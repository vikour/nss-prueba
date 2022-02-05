package es.vikour.nss.nssreservahoteles.web.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingDto {

	private Integer id;
	
	private Integer hotelId;

	private LocalDate dateFrom;

	private LocalDate dateTo;
	
	private String email;
}
