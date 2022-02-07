# NSS Reserva Hoteles

Este proyecto contiene una API rest para gestionar las reservas en hoteles. 
Ofrece la siguientes funcionalidades:

- Listar hoteles
- Listar hoteles con disponibilidad entre fechas
- Abrir disponibilidad en un hotel
- Hacer reservas
- Cancelar reservas
- Ver qué reservas se han realizado pora un hotel en unas determinadas fechas
- Ver los datos de una reserva

Todas estas funcionalidades se han documentado por medio de OpenApi para poder probarlas.

## Puesta en marcha

Este es un ejemplo de cómo ejecutar el proyecto localmente. 
> Al desplegar el proyecto en local usará una base de datos en memoria H2 para facilitar su puesta en marcha y las pruebas. De este modo, no es necesario configurar ninguna base de datos, ya que se encuentra embebida. Se ha creado una configuración de docker con POSTGRES descrita más adelante en el que si tiene persistencia de datos.

### Prerequisitos
Se necesita tener instalado lo siguiente:

- JDK de java 11 o superior - [OpenJDK-11](https://openjdk.java.net/projects/jdk/11/)
- Git - [Git](https://git-scm.com/downloads)

### Instalación
A continuación se listaran una serie de pasos para ejecutar el proyecto en local. 

1. Clonar el repositorio y entramos enl directorio generado

```sh
git clone https://github.com/vikour/nss-prueba.git && cd nss-prueba
```
2. Compilar el programa.

```sh
./mvnw clean install
```
### Uso

Una vez compilado e instalado el programa, iniciar el servicio con el siguiente comando:
```
java -jar target/nss-reserva-hoteles-0.8.1-SNAPSHOT.jar
```
Al ejecutarlo se iniciará el servicio REST en la siguietne URL: [http://localhost:8080](http://localhost:8080). Por ejemplo, podrá listar los hoteles existentes a través de la siguiente URL: [http://localhost:8080/hotels](http://localhost:8080/hotels)

La documentación del API REST la puede encontrar en la siguiente dirección : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Puesta en marcha - Imagen Docker

Este es un ejemplo de cómo ejecutar el proyecto dockerizado. Usa persistencia de datos con un servicio de postgres en un red interna proporcionada por docker. Se ha utilizado la herramienta _docker compose_ para orquestar la puesta en marcha.

### Prerequisitos
Se necesita tener instalado lo siguiente:

- Docker - [URL cómo instalar docker](https://docs.docker.com/engine/install/)
- Docker Compose - [URL cómo instalar docker-compose](https://docs.docker.com/compose/install/)

### Instalación
A continuación se listaran una serie de pasos para ejecutar el proyecto en docker.

1. Clonar el repositorio y entramos enl directorio generado

```sh
git clone https://github.com/vikour/nss-prueba.git && cd nss-prueba
```
2. Compilar la imagen en el docker local.

```sh
./mvnw clean package jib:dockerBuild
```
### Uso

Una vez compilado e instalada la imagen de docker poner el marcha a traves de _docker compose_:
```sh
docker-compose up
```
Alternativamente se puede iniciar como servicio en _background_:
```sh
docker-compose up -d
```

Al ejecutarlo se iniciará el servicio REST en la siguietne URL: [http://localhost:8080](http://localhost:8080). Por ejemplo, podrá listar los hoteles existentes a través de la siguiente URL: [http://localhost:8080/hotels](http://localhost:8080/hotels)

La documentación del API REST la puede encontrar en la siguiente dirección : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Descripción de la imagen docker


## Contact

Víctor Manuel Ortiz Guardeño - [linkedin](https://www.linkedin.com/in/vikour)