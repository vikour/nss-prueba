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

CREATE TABLE bookings (
	id 			INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	hotel_id 	INT NOT NULL,
	date_from  	DATE NOT NULL,
	date_to 	DATE NOT NULL,
	email  		VARCHAR(100) NOT NULL,
	FOREIGN KEY (id) REFERENCES hotels(id)
);

/*
CREATE VIEW view_availavility_updated AS 
SELECT 
	a.hotel_id, 
	a.date, 
	(a.rooms - count(b.id)) as rooms_available
FROM availavility a 
	INNER JOIN hotels h ON 
		a.hotel_id = h.id
	LEFT JOIN bookings b ON 
		h.id = b.hotel_id AND 
		a.date between b.date_from and b.date_to
GROUP BY a.hotel_id, a.date, a.rooms;*/
