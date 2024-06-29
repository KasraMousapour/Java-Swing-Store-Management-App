CREATE DATABASE swing_store_database;
USE swing_store_database;

CREATE TABLE User(
    username VARCHAR(255),
	full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    access_level VARCHAR(255) NOT NULL,
    address VARCHAR(400),
    inventory FLOAT DEFAULT 0,
    phoneNumber VARCHAR(255),
    PRIMARY KEY(username)
);

CREATE TABLE Product(
	id INTEGER AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    category VARCHAR(255),
    price VARCHAR(255) NOT NULL,
    number INTEGER NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE Sale(
    id INTEGER AUTO_INCREMENT,
    total_cost FLOAT NOT NULL,
    seller_username VARCHAR(255),
    date DATETIME NOT NULL,
    PRIMARY KEY(id)
);

-- DROP TABLE User;
-- DROP TABLE Product;
-- DROP TABLE Sale;

INSERT INTO User VALUES("admin", "ADMIN", "test", "ADMINTEST@hotmail.com", "Administrator");
INSERT INTO User VALUES("acaminha", "Alexandre", "test123", "alexandrencaminha@gmail.com", "Manager");
INSERT INTO User VALUES("cacaminha", "Caio", "test123", "caioncaminha@gmail.com", "Attendant","Unit 3, Floor 1, No 559, Moosavi alley, after Ferdousi Sq, Enghelab Ave",50,"09578454215");

INSERT INTO Product VALUES(null, "Cheeseburger", "Burger", "9,99", "50");
INSERT INTO Product VALUES(null, "Coca-Cola", "Drink", "3,99", "50");
INSERT INTO Product VALUES(null, "Hamburger", "Burger", "15,84", "50");
INSERT INTO Product VALUES(null, "Quarter-Pounder", "Burger", "13,74", "50");
INSERT INTO Product VALUES(null, "Chicken-Nuggets", "Burger", "11,45", "50");
INSERT INTO Product VALUES(null, "Iced-Coffee", "Drink", "8,89", "50");
INSERT INTO Product VALUES(null, "Egg-Muffin", "Burger", "10,45", "50");
INSERT INTO Product VALUES(null, "Sausage-Burrito", "Burger", "13,45", "50");
INSERT INTO Product VALUES(null, "Fanta-Orange", "Drink", "4,5", "50");



INSERT INTO Sale VALUES(null, 9.99, "Alexandre", '2022-12-16',3);

SELECT * FROM User;
SELECT * FROM Product;
SELECT * FROM Sale;
