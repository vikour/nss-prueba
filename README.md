# NSS Reserva Hoteles

Este proyecto contiene una API rest para gestionar las reservas en hoteles. 
Ofrece la siguientes funcionalidades:

- Listar hoteles
- Listar hoteles con disponibilidad entre fechas
- Abrir disponibilidad en un hotel
- Hacer reservas
- Cancelar reservas
- Ver las reservas que se han realizado para unn hotel en unas determinadas fechas
- Ver los datos de una reserva

Todas estas funcionalidades se han documentado por medio de OpenApi para poder probarlas, y estarán disponibles una vez se haya iniciado la aplicación.

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
> usar el siguiente comando en caso de estar en Windows
> ```sh
> mvnw.cmd clean install
> ```
### Uso

Una vez compilado e instalado el programa, iniciar el servicio con el siguiente comando:
```
java -jar target/nss-reserva-hoteles-0.8.2-SNAPSHOT.jar
```
Al ejecutarlo se iniciará el servicio REST en la siguietne URL: [http://localhost:8080](http://localhost:8080). Por ejemplo, podrá listar los hoteles existentes a través de la siguiente URL: [http://localhost:8080/hotels](http://localhost:8080/hotels)

La documentación del API REST la puede encontrar en la siguiente dirección : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html). A través de la esta URL también se puede probar el programa.

También se ha incluido una colección de POSTMAN en la siguiente ruta: **_src/it/Reserva Hoteles.postman_collection.json_**

### Acceso a la base de datos temporal

Si se quiere saber qué está escribiendo el programa en la base de datos, realice los siguientes pasos con la aplicación iniciada.

1. Acceder a la consola por medio del siguiente enlace [H2 Console](http://localhost:8080/h2-console).

2. Establecer la siguiente configuración:

    - **Driver class**: org.h2.Driver
    - **JDBC URL**:     jdbc:h2:mem:nss-hoteles-db
    - **User name**:    sa
    - **Password**:     _(contraseña vacía no introducir nada)_

Una vez realizados los pasos anteriores se mostrará la consola de H2 para ejecutar consultas SQL.

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
> usar el siguiente comando en caso de estar en Windows
> ```sh
> mvnw.cmd clean package jib:dockerBuild
> ```
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

La imagen de docker contiene las siguientes propiedades de entorno que le son necesarias para acceder al origen de datos:

- DATASOURCE_URL - JDBC URL hacia la base de datos
> ej.: jdbc:postgresql://nss-db/postgres
- DATASOURCE_DRIVER - Driver de conexión con la base de datos
> ej.: org.postgresql.Driver
- DATASOURCE_USER - Usuario de acceso a la base de datos
> ej.: user
- DATASOURCE_PASS - Contraseña de acceso a la base de datos
> ej.: password
- DATASOURCE_DIALECT - Dialecto usado en la base de datos
> ej.: org.hibernate.dialect.PostgreSQLDialect

La imagen ofrecerá servicio por el puerto 8080, pero puede ser modificado por el contenedor hacia otro puerto local.

## Contact

Víctor Manuel Ortiz Guardeño - [linkedin](https://www.linkedin.com/in/vikour)