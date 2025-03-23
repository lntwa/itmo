CREATE TABLE "People" (
  "Person_ID" integer PRIMARY KEY,
  "Name" varchar,
  "Passport" integer,
  "Job" varchar
);

CREATE TABLE "Violations" (
  "Violation_ID" integer PRIMARY KEY,
  "Violation_type" varchar,
  "Fine_amount" float,
  "Status" boolean,
  "Person_ID" integer
);

CREATE TABLE "Car" (
  "Car_ID" integer PRIMARY KEY,
  "Car_brand" varchar,
  "Color" varchar,
  "Car_number" integer
);

CREATE TABLE "Parking_Spot" (
  "Parking_spot_ID" integer PRIMARY KEY,
  "Spot_number" integer,
  "Spot_type" varchar,
  "Is_Occupied" boolean
);

CREATE TABLE "Car_Parking" (
  "Parking_spot_ID" integer,
  "Person_ID" integer,
  "Start_time" timestamp,
  "End_time" timestamp,
  PRIMARY KEY ("Parking_spot_ID", "Person_ID")
);

CREATE TABLE "Booking" (
  "Room_ID" integer,
  "Person_ID" integer,
  "Check_in_date" timestamp,
  "Check_out_date" timestamp,
  PRIMARY KEY ("Room_ID", "Person_ID")
);

CREATE TABLE "Hotel_Room" (
  "Room_ID" integer PRIMARY KEY,
  "Price" integer,
  "Floor" integer,
  "Room_type" varchar
);



INSERT INTO "People" ("Person_ID", "Name", "Passport", "Job") VALUES
(1, 'Иван Петров', 123456789, 'Инженер'),
(2, 'Мария Смирнова', 987654321, 'Программист'),
(3, 'Алексей Иванов', 456123789, 'Врач');

INSERT INTO "Violations" ("Violation_ID", "Violation_type", "Fine_amount", "Status", "Person_ID") VALUES
(1, 'Парковка в неположенном месте', 500.00, true, 1),
(2, 'Превышение скорости', 1500.50, false, 2),
(3, 'Положил вещи на чужую парковку', 2000.00, true, 3);

INSERT INTO "Car" ("Person_ID", "Car_brand", "Color", "Car_number") VALUES
(1, 'Toyota', 'Синий', 12345),
(2, 'BMW', 'Черный', 67890),
(3, 'Audi', 'Белый', 11223);

INSERT INTO "Parking_Spot" ("Parking_spot_ID", "Spot_number", "Spot_type", "Is_Occupied") VALUES
(1, 101, 'Одиночная', false),
(2, 102, 'Двойная', true),
(3, 103, 'Одиночная', false);

INSERT INTO "Car_Parking" ("Parking_spot_ID", "Person_ID", "Start_time", "End_time") VALUES
(1, 1, '2025-02-26 08:00:00', '2025-02-26 18:00:00'),
(2, 2, '2025-02-26 09:30:00', '2025-02-26 19:00:00'),
(3, 3, '2025-02-26 07:00:00', '2025-02-26 16:30:00');

INSERT INTO "Hotel_Room" ("Room_ID", "Price", "Floor", "Room_type") VALUES
(1, 5000, 2, 'Стандарт'),
(2, 7500, 3, 'Люкс'),
(3, 3000, 1, 'Эконом');

INSERT INTO "Booking" ("Room_ID", "Person_ID", "Check_in_date", "Check_out_date") VALUES
(1, 1, '2025-02-25 14:00:00', '2025-02-27 12:00:00'),
(2, 2, '2025-02-24 15:00:00', '2025-02-26 11:00:00'),
(3, 3, '2025-02-23 13:00:00', '2025-02-25 10:00:00');
