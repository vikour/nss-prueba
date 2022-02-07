package es.vikour.nss.nssreservahoteles.entity;

import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_EMAIL_FORMAT;
import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_EMAIL_MANDATORY;
import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_EMAIL_SIZE;
import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_END_DATE_MANDATORY;
import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_HOTEL_MANDATORY;
import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_START_DATE_MANDATORY;
import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_START_GT_END_DATE;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa una reserva desde una fecha hasta otra, por una persona identificada por
 * el correo y en un hotel.
 *
 * @author Víctor Manuel Ortiz Guardeño
 *
 */

@Getter
@Setter(value = AccessLevel.MODULE) // No se debería modificar la reserva
@EqualsAndHashCode
@Entity
@Table(name = "bookings")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Privado, para el builder
public class Booking {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "hotel_id")
	@NotNull(message = ERROR_HOTEL_MANDATORY)
	private Hotel hotel;

	@NotNull(message = ERROR_START_DATE_MANDATORY)
	@Column(name = "date_from")
	private LocalDate dateFrom;

	@NotNull(message = ERROR_END_DATE_MANDATORY)
	@Column(name = "date_to")
	private LocalDate dateTo;

	@NotNull(message = ERROR_EMAIL_MANDATORY)
	@Email(message = ERROR_EMAIL_FORMAT)
	@Size(max = 100, message = ERROR_EMAIL_SIZE)
	private String email;

	protected Booking() {} // protegido, para que pueda construirlo hibernate

	@Transient
	@AssertTrue(message = ERROR_START_GT_END_DATE)
	public boolean isDateFromLessOrEqualThanDateTo() {
		return dateFrom != null &&
				dateTo != null &&
				dateFrom.compareTo(dateTo) <= 0;
	}


}
