package es.vikour.nss.nssreservahoteles.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.vikour.nss.nssreservahoteles.entity.Availavility;
import es.vikour.nss.nssreservahoteles.entity.AvailavilityPK;
import es.vikour.nss.nssreservahoteles.entity.Hotel;

@Repository
public interface AvailavilityRepository extends JpaRepository<Availavility, AvailavilityPK> {
	
	@Query("SELECT a FROM Availavility a WHERE a.availavilityPK.hotel = :hotel AND a.availavilityPK.date BETWEEN :startDate AND :endDate")
	List<Availavility> findByHotelAndBetweenDates(Hotel hotel, LocalDate startDate, LocalDate endDate);
	
}
