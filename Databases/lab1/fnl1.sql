DROP TABLE IF EXISTS Car_parking;
DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Parking_spot;
DROP TABLE IF EXISTS Car;
DROP TABLE IF EXISTS Violations;
DROP TABLE IF EXISTS Hotel_Room;
DROP TABLE IF EXISTS People;

CREATE TABLE People (
    Person_ID SERIAL PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Passport INTEGER UNIQUE NOT NULL,
    Job VARCHAR(100)
);

CREATE TABLE Hotel_Room (
    Room_ID SERIAL PRIMARY KEY,
    Price INTEGER NOT NULL,
    Room_type VARCHAR(50) NOT NULL,
    Floor INTEGER NOT NULL
);

CREATE TABLE Violations (
    Violation_ID SERIAL PRIMARY KEY,
    Violation_type VARCHAR(100) NOT NULL,
    Fine_amount INTEGER NOT NULL CHECK (Fine_amount > 100),
    Status VARCHAR(50) NOT NULL,
    Person_ID INTEGER REFERENCES People(Person_ID) ON DELETE RESTRICT
);

CREATE TABLE Car (
    Owner_ID SERIAL PRIMARY KEY,
    Car_brand VARCHAR(100) NOT NULL,
    Color VARCHAR(50) NOT NULL,
    Car_number VARCHAR(20) UNIQUE NOT NULL,
    Person_ID INTEGER REFERENCES People(Person_ID) ON DELETE CASCADE
);

CREATE TABLE Parking_spot (
    Parking_spot_ID SERIAL PRIMARY KEY,
    Spot_number INTEGER UNIQUE NOT NULL,
    Is_occupied BOOLEAN NOT NULL DEFAULT FALSE,
    Person_ID INTEGER REFERENCES People(Person_ID) ON DELETE SET NULL
);

CREATE TABLE Booking (
    Check_in_date TIMESTAMP NOT NULL,
    Check_out_date TIMESTAMP NOT NULL,
    Room_ID INTEGER REFERENCES Hotel_Room(Room_ID) ON DELETE CASCADE,
    Person_ID INTEGER REFERENCES People(Person_ID) ON DELETE CASCADE,
    PRIMARY KEY (Room_ID, Person_ID, Check_in_date)
);

CREATE TABLE Car_parking (
    Start_time TIMESTAMP NOT NULL,
    End_time TIMESTAMP NOT NULL,
    Parking_spot_ID INTEGER REFERENCES Parking_spot(Parking_spot_ID) ON DELETE CASCADE,
    Owner_ID INTEGER REFERENCES Car(Owner_ID) ON DELETE CASCADE,
    PRIMARY KEY (Parking_spot_ID, Owner_ID, Start_time)
);

INSERT INTO People (Name, Passport, Job) VALUES
('Арина Леонтьева', 123456, 'Предприниматель'),
('Рогович Машка', 654321, 'Инструктор по дайвингу'),
('Шилова Варя', 112233, 'Дизайнер');

INSERT INTO Hotel_Room (Price, Room_type, Floor) VALUES
(100, 'Стандарт', 1),
(100, 'Стандарт', 2),
(100, 'Стандарт', 3),
(150, 'Комфорт', 4),
(150, 'Комфорт', 5),
(200, 'Люкс', 6);

INSERT INTO Violations (Violation_type, Fine_amount, Status, Person_ID) VALUES
('Положил вещи на чужое место', 500, 'Оплачено', 1),
('Парковка в неположенном месте', 300, 'Не оплачено', 2);

INSERT INTO Car (Car_brand, Color, Car_number, Person_ID) VALUES
('Toyota', 'Красный', 'A123BC', 1),
('Ford', 'Синий', 'B456DE', 2);

INSERT INTO Parking_spot (Spot_number, Is_occupied, Person_ID) VALUES
(1, TRUE, 1),
(2, FALSE, NULL),
(3, TRUE, 2),
(4, FALSE, NULL);

INSERT INTO Booking (Check_in_date, Check_out_date, Room_ID, Person_ID) VALUES
('2023-10-01 14:00:00', '2023-10-05 12:00:00', 1, 1),
('2023-10-02 15:00:00', '2023-10-06 11:00:00', 2, 2);

INSERT INTO Car_parking (Start_time, End_time, Parking_spot_ID, Owner_ID) VALUES
('2023-10-01 14:00:00', '2023-10-01 18:00:00', 1, 1),
('2023-10-02 09:00:00', '2023-10-02 17:00:00', 2, 2),
('2023-10-02 09:00:00', '2023-10-02 17:00:00', 3, 1),
('2023-10-02 09:00:00', '2023-10-02 17:00:00', 4, 2);

SELECT * FROM People;
SELECT * FROM Hotel_Room;
SELECT * FROM Violations;
SELECT * FROM Car;
SELECT * FROM Parking_spot;
SELECT * FROM Booking;
SELECT * FROM Car_parking;

SELECT * FROM Hotel_Room
WHERE Room_type = 'Стандарт';

SELECT Is_occupied, COUNT(Parking_spot_ID) AS Spot_Count
FROM Parking_spot
GROUP BY Is_occupied;

SELECT People.Name, Violations.Violation_type, Violations.Status
FROM Violations
JOIN People ON Violations.Person_ID = People.Person_ID;