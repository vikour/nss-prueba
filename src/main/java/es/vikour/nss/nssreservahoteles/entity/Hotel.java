package es.vikour.nss.nssreservahoteles.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static es.vikour.nss.nssreservahoteles.entity.EntityValidationConstants.*;

@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "hotels")
public class Hotel {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message= ERROR_HOTEL_ID_NULO)
	private Integer id;
	
	@NotEmpty(message = ERROR_HOTEL_NAME)
	@Size(max = 100, message = ERROR_HOTEL_NAME_SIZE)
	private String name;
	
	@NotNull(message = ERROR_HOTEL_CATEGORY)
	private Integer category;

}
