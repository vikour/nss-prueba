package es.vikour.nss.nssreservahoteles.web.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
info = @Info(
	title = "API reserva de hoteles",
	version = "0.8.2-SNAPSHOT",
	description = "Esta API permite gestionar la disponibilidad de los hoteles y las reservas",
	contact = @Contact(name = "Victor Manuel", email = "vikour92@gmail.com")

))
public interface ApiController {

}