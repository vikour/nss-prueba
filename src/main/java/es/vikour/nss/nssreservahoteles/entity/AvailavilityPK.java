package es.vikour.nss.nssreservahoteles.entity;

import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_DATE_MANDATORY;
import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_HOTEL_MANDATORY;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Embeddable
public class AvailavilityPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = ERROR_DATE_MANDATORY)
	private LocalDate date;

	@NotNull(message = ERROR_HOTEL_MANDATORY)
	@ManyToOne
	@JoinColumn(name = "hotel_id", updatable = false, insertable = false)
	private Hotel hotel;

}
