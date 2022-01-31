package es.vikour.nss.nssreservahoteles.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.vikour.nss.nssreservahoteles.entity.Hotel;
import es.vikour.nss.nssreservahoteles.repository.HotelRepository;
import es.vikour.nss.nssreservahoteles.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	public HotelRepository hotelRepository;

	@Override
	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}

}
