package es.vikour.nss.nssreservahoteles.service.exceptions;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class BookingNotAvailableInDatesException extends HotelBookingRuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private int hotelId;

	public BookingNotAvailableInDatesException(LocalDate dateFrom, LocalDate dateTo, int hotelId) {
		super("El hotel " + hotelId + " no tiene disponibilidad en las fechas " + dateFrom + "-" + dateTo);
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.hotelId = hotelId;
	}

}
