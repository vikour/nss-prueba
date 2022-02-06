create table hotels (
	id 		SERIAL PRIMARY KEY,
	name 	VARCHAR(100) NOT NULL,
	category INT NOT NULL
);

create table availavility (
	date 		DATE,
	hotel_id	INT,
	rooms		INT NOT NULL,
	PRIMARY KEY(date, hotel_id),
	FOREIGN KEY (hotel_id) REFERENCES hotels(id)
);

CREATE TABLE bookings (
	id 			SERIAL PRIMARY KEY,
	hotel_id 	INT NOT NULL,
	date_from  	DATE NOT NULL,
	date_to 	DATE NOT NULL,
	email  		VARCHAR(100) NOT NULL,
	FOREIGN KEY (id) REFERENCES hotels(id)
);

INSERT INTO hotels(name, category) VALUES ('Hotel Córdoba Center', 4);
INSERT INTO hotels(name, category) VALUES ('Eurostars Palace', 4);
INSERT INTO hotels(name, category) VALUES ('Soho Boutique Córdoba', 4);
INSERT INTO hotels(name, category) VALUES ('Ítaca Colón', 3);
