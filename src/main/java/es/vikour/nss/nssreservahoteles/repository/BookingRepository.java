package es.vikour.nss.nssreservahoteles.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.vikour.nss.nssreservahoteles.entity.Booking;
import es.vikour.nss.nssreservahoteles.entity.Hotel;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{

	@Query("SELECT b FROM Booking b WHERE b.hotel = :hotel AND :endDate >= b.dateFrom AND :startDate <= b.dateTo")
	List<Booking> findAllByHotelAndBetweenDates(Hotel hotel, LocalDate startDate, LocalDate endDate);
	
}
