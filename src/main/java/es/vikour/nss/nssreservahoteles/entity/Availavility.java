package es.vikour.nss.nssreservahoteles.entity;

import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_ROOMS_GT_1;
import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_ROOMS_MANDATORY;

import java.time.LocalDate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa la disponibilidad de un hotel. Se quiere conocer
 * la fecha, el hotel y el número de habitaciones.
 *
 * @author Víctor Manuel Ortiz Guardeño
 */

@Data
@EqualsAndHashCode
@Entity
@Table(name = "availavility")
public class Availavility {

	@EmbeddedId
	private AvailavilityPK availavilityPK;

	@Min(value = 0, message = ERROR_ROOMS_GT_1)
	@NotNull(message = ERROR_ROOMS_MANDATORY)
	private Integer rooms;

	protected Availavility() {}

	public Availavility(LocalDate date, Hotel hotel, Integer rooms) {
		this.availavilityPK = new AvailavilityPK();
		this.availavilityPK.setDate(date);
		this.availavilityPK.setHotel(hotel);
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return "Availavility(" + availavilityPK.getDate() + ", rooms=" + rooms + ", hotel_id=" + availavilityPK.getHotel().getId() + ")";
	}

}
