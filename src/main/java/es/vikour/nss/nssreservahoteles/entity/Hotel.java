package es.vikour.nss.nssreservahoteles.entity;

import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_HOTEL_CATEGORY;
import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_HOTEL_NAME;
import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.ERROR_HOTEL_NAME_SIZE;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Esta clase representa a un Hotel en el que se podrá hacer reservas y
 * definir qué disponibilidad de habitaciones tendrá.
 *
 * @author Víctor Manuel Ortiz Guardeño
 */

@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "hotels")
public class Hotel {

	/**
	 * Identificador del Hotel
	 */

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Nombre del hotel
	 */

	@NotEmpty(message = ERROR_HOTEL_NAME)
	@Size(max = 100, message = ERROR_HOTEL_NAME_SIZE)
	private String name;

	/**
	 * Categoría del hotel
	 */

	@NotNull(message = ERROR_HOTEL_CATEGORY)
	private Integer category;

	/**
	 * Lista de disponibilidades
	 */

	@OneToMany(mappedBy = "availavilityPK.hotel")
	private List<Availavility> availavilities;

}
