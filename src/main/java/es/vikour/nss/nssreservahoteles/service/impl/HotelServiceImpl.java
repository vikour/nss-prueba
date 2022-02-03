package es.vikour.nss.nssreservahoteles.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.vikour.nss.nssreservahoteles.entity.Availavility;
import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.repository.AvailavilityRepository;
import es.vikour.nss.nssreservahoteles.repository.HotelRepository;
import es.vikour.nss.nssreservahoteles.service.HotelAvailavilityService;
import es.vikour.nss.nssreservahoteles.service.HotelService;
import es.vikour.nss.nssreservahoteles.service.exceptions.HotelNotFoundException;
import es.vikour.nss.nssreservahoteles.service.requests.OpenAvailavilityRequest;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class HotelServiceImpl implements HotelService, HotelAvailavilityService {
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private AvailavilityRepository availavilityRepository;

	
	@Override
	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}

	@Override
	public void openAvailavility(@Valid OpenAvailavilityRequest request) throws HotelNotFoundException {
		log.traceEntry();
		
		log.info("Buscando hotel...");
		Hotel hotel = hotelRepository
				.findById(request.getHotelId())
				.orElseThrow(() -> new HotelNotFoundException(request.getHotelId()));
		
		LocalDate currentDate = request.getStartDate();
		LocalDate endDate = request.getEndDate();
		log.info("Hotel encontrado, se busca disponibilidad entre {} y {}", currentDate, endDate );
		
		List<Availavility> availavilitesInRange = availavilityRepository.findByHotelAndBetweenDates(hotel, currentDate, endDate);
		
		do {
			boolean exists = false;
			for (Availavility availavilityBD : availavilitesInRange) {
				exists = availavilityBD.getAvailavilityPK().getDate().equals(currentDate) || exists;
			}
			
			if (!exists ) {
				Availavility aux = new Availavility(currentDate, hotel, request.getNumberRooms()); 
				availavilityRepository.save(aux);
				log.debug("Abriendo disponibiliad para el día {} con {} habitaciones", currentDate, request.getNumberRooms());
			}
			else {
				log.info("No se sobreescribe la disponbilidad para la fecha {}", currentDate);
			}
			
			currentDate = currentDate.plusDays(1);
		} while (currentDate.compareTo(endDate) <= 0);
		
		log.debug("Disponiblidad abierta satisfactoriamente");
		
		log.traceExit();
	}

}
