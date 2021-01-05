Create Table library(
id int NOT NULL AUTO_INCREMENT,
book_id int NOT NULL,
user_id int NOT NULL,
created_by varchar (20) NOT NULL,
updated_by varchar (20),
created_at TIMESTAMP DEFAULT NOW(),
update_at TIMESTAMP,
PRIMARY KEY (id)
);