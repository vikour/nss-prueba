package es.vikour.nss.nssreservahoteles.web.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HotelDto {

	private int id;
	
	@NotEmpty
	@Size(max = 100)
	private String name;
	
	private int category;
	
	
}
