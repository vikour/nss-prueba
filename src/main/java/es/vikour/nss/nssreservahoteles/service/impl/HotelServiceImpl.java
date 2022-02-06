package es.vikour.nss.nssreservahoteles.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import es.vikour.nss.nssreservahoteles.entity.Availavility;
import es.vikour.nss.nssreservahoteles.entity.Booking;
import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.repository.AvailavilityRepository;
import es.vikour.nss.nssreservahoteles.repository.BookingRepository;
import es.vikour.nss.nssreservahoteles.repository.HotelRepository;
import es.vikour.nss.nssreservahoteles.service.BookingService;
import es.vikour.nss.nssreservahoteles.service.HotelAvailavilityService;
import es.vikour.nss.nssreservahoteles.service.HotelService;
import es.vikour.nss.nssreservahoteles.service.exceptions.BookingNotAvailableInDatesException;
import es.vikour.nss.nssreservahoteles.service.exceptions.HotelNotFoundException;
import es.vikour.nss.nssreservahoteles.service.requests.HotelDateIntervalRequest;
import es.vikour.nss.nssreservahoteles.service.requests.OpenAvailavilityRequest;
import es.vikour.nss.nssreservahoteles.service.requests.OpenBookingRequest;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class HotelServiceImpl implements HotelService, HotelAvailavilityService, BookingService {
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private AvailavilityRepository availavilityRepository;
	
	@Autowired
	private BookingRepository bookingRepository;

	
	@Override
	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}
	
	// Busca un htel por Id y si no lo encuentra lanza una excepción
	private Hotel requireHotelOrThrowException(int hotelId) {
		log.info("Buscando hotel...");
		return hotelRepository
				.findById(hotelId)
				.orElseThrow(() -> new HotelNotFoundException(hotelId));
	}

	@Override
	@Transactional
	public void openAvailavility(@Valid OpenAvailavilityRequest request) throws HotelNotFoundException {
		log.traceEntry();
		
		Hotel hotel = requireHotelOrThrowException(request.getHotelId());
		
		LocalDate currentDate = request.getStartDate();
		LocalDate endDate = request.getEndDate();
		log.info("Hotel encontrado, se busca disponibilidad entre {} y {}", currentDate, endDate );

		// Se buscan las fechas que tienen disponibilidad...
		List<LocalDate> datesReserved = 
				availavilityRepository.findByHotelAndBetweenDates(hotel, currentDate, endDate).stream()
				.map((av) -> av.getAvailavilityPK().getDate())
				.sorted().collect(Collectors.toList());
		
		// Índice usado para recorrer las fechas
		int index = 0;
		
		// Mientras no hallamos repasado todas las fechas...
		while (currentDate.compareTo(endDate) <= 0) {

			// Si no hay más fechas reservadas (en BD) o la fecha actual no es igual a la fecha reservada...
			if ( index >= datesReserved.size() || 
				!(datesReserved.get(index).equals(currentDate)) ) 
			{
				// Se escribe en el respositorio la disponibilidad para esa fecha.
				Availavility aux = new Availavility(currentDate, hotel, request.getNumberRooms()); 
				availavilityRepository.save(aux);
				log.debug("Abriendo disponibiliad para el día {} con {} habitaciones", currentDate, request.getNumberRooms());
			}
			else { // currentDate == datesResserved(i).date
				/// Si la fecha actual ya está en el respositorio no se sobreescribe.
				index++; // se pasa al a siguiente
				log.info("No se sobreescribe la disponbilidad para la fecha {}", currentDate);
			}
			
			// Se trata el siguiente día
			currentDate = currentDate.plusDays(1);
		}
		
		log.debug("Disponiblidad abierta satisfactoriamente");
		log.traceExit();
	}

	@Override
	public List<Hotel> findAllWithRoomsAvailableBetweenDates(LocalDate dateFrom, LocalDate dateTo) {
		Objects.requireNonNull(dateFrom, "La fecha inicio es obligatoria");
		Objects.requireNonNull(dateTo, "La fecha fin es obligatoria");
		
		return hotelRepository.findAllWithRoomsAvailable(dateFrom, dateTo).stream()
			// Se filtra por aquellos hoteles que no tienen dias sin disponibilidad entre fechas
			.filter((h) -> hotelHasAllDaysAvailables(h, dateFrom, dateTo))
			.collect(Collectors.toList());
	}

	
	/**
	 * Comprueba que el hotel tenga disponibilidad todos los dias entre las fechas pasadas como argumento
	 * 
	 * @param hotel		Un hotel
	 * @param dateFrom	Fecha inicio
	 * @param dateTo	Fecha fin
	 * 
	 * @return <code>true</code> si tiene disponibilidad todos los días entre las fechas, <code>false</code>
	 * 		   en otro caso
	 */
	private boolean hotelHasAllDaysAvailables(Hotel hotel, LocalDate dateFrom, LocalDate dateTo) {
		boolean allDaysAvailables = true;
		
		List<LocalDate> datesAvailables = 
				availavilityRepository.findByHotelAndBetweenDatesAndRoomsAvailable(hotel, dateFrom, dateTo)
				.stream().map( av -> av.getAvailavilityPK().getDate())
				.collect(Collectors.toList());
		
		LocalDate currentDate = dateFrom;
		
		while ( allDaysAvailables && currentDate.compareTo(dateTo) <= 0) {
			allDaysAvailables = datesAvailables.contains(currentDate);
			currentDate = currentDate.plusDays(1);
		}
		
		return allDaysAvailables;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Booking openBooking(@Valid OpenBookingRequest request)
			throws HotelNotFoundException, BookingNotAvailableInDatesException {

		// Se valida qu exista el hotel
		Integer hotelId = request.getHotelId();
		Hotel hotel = requireHotelOrThrowException(hotelId);
		
		// Se valida que hay habitaciones todo el intervalo de días
		LocalDate dateFrom = request.getStartDate();
		LocalDate dateTo = request.getEndDate();
		
		if (!hotelHasAllDaysAvailables(hotel, dateFrom, dateTo))
			throw new BookingNotAvailableInDatesException(dateFrom, dateTo , hotelId);

		// Update rooms in hotels
		for (Availavility availavility : availavilityRepository.findByHotelAndBetweenDates(hotel, dateFrom, dateTo)) {
			int rooms = availavility.getRooms();
			availavility.setRooms(rooms - 1);
			availavilityRepository.save(availavility);
		}
		
		// Se crea la reserva
		Booking booking = Booking.builder()
				.hotel(hotel)
				.dateFrom(dateFrom)
				.dateTo(dateTo)
				.email(request.getEmail())
				.build();
		
		bookingRepository.save(booking);
		
		return booking;
	}

	@Override
	public List<Booking> queryHotelBooking(@Valid HotelDateIntervalRequest request) {
		log.traceEntry();
		Hotel hotel = requireHotelOrThrowException(request.getHotelId());
		log.info("Buscando reservas entre {} y {}", request.getStartDate(), request.getEndDate());
		List<Booking> bookingInHotel = bookingRepository.findAllByHotelAndBetweenDates(hotel, request.getStartDate(), request.getEndDate());
		log.info("Se han encontrado {} registros", bookingInHotel.size());
		return log.traceExit(bookingInHotel);
	}
}
