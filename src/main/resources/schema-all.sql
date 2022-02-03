create table hotels (
	id 		INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
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