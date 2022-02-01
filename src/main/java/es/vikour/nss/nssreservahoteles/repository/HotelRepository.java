package es.vikour.nss.nssreservahoteles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
