package es.vikour.nss.nssreservahoteles.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.*;

import java.time.LocalDate;

/**
 * Representa la disponibilidad de un hotel. Se quiere conocer 
 * la fecha, el hotel y el número de habitaciones.
 * 
 * @author Víctor Manuel Ortiz Guardeño
 */

@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "availavility")
public class Availavility {
	
	@EmbeddedId
	private AvailavilityPK availavilityPK;
	
	@Min(value = 1, message = ERROR_ROOMS_GT_1)
	@NotNull(message = ERROR_ROOMS_MANDATORY)
	private Integer rooms;
	
	protected Availavility() {}
	
	public Availavility(LocalDate date, Hotel hotel, Integer rooms) {
		this.availavilityPK = new AvailavilityPK();
		this.availavilityPK.setDate(date);
		this.availavilityPK.setHotel(hotel);
		this.rooms = rooms;
	}

}
