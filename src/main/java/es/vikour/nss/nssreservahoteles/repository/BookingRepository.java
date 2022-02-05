package es.vikour.nss.nssreservahoteles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.vikour.nss.nssreservahoteles.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{

}
