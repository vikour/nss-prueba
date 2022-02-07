package es.vikour.nss.nssreservahoteles.web.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Representa a un <code>Hotel</code>. Se quiere conocer su Id, nombre y categoria.
 * 
 * @author Víctor Manuel Ortiz Guardeño
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(name = "Hotel",description = "Un hotel es un lugar donde se hacen reservas y se habre disponibilidad")
public class HotelDto {

	@Schema(description = "Identificador del hotel", accessMode = AccessMode.READ_ONLY, example = "1")
	private int id;
	
	@NotEmpty
	@Size(max = 100)
	@Schema(description = "Nombre del hotel", accessMode = AccessMode.READ_ONLY, example = "Hotel A")
	private String name;
	
	@Schema(description = "Categoria del hotel", accessMode = AccessMode.READ_ONLY, example = "1")
	private int category;
	
	
}
