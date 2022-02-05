package es.vikour.nss.nssreservahoteles.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.vikour.nss.nssreservahoteles.entity.Hotel;

/**
 * Repositorio de hoteles. Esta clase se encarga de obtener hoteles desde
 * el origen de datos.
 * 
 * @author Víctor Manuel Ortiz Guardeño
 *  
 */

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer>{
	
	
	@Query("SELECT h FROM Hotel h WHERE EXISTS("
			+ "SELECT av FROM Availavility av "
			+ "WHERE h.id = av.availavilityPK.hotel AND av.availavilityPK.date BETWEEN :dateFrom AND :dateTo AND av.rooms > 0)")
	List<Hotel> findAllWithRoomsAvailable(LocalDate dateFrom, LocalDate dateTo);

}
