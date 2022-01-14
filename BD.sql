CREATE DATABASE FACULTATE;

CREATE TABLE FACULTATE.PERSON (
	personID int PRIMARY KEY,
	firstName varchar(50) NOT NULL,
	lastName varchar(50) NOT NULL
);

CREATE TABLE FACULTATE.STUDENT (
	studentID int PRIMARY KEY,
	totalCredits int NOT NULL,
	personID int NOT NULL,
    foreign key (personID) references PERSON(personID)
);

CREATE TABLE FACULTATE.KURS (
	kursID int PRIMARY KEY,
	name varchar(50) NOT NULL,
	lehrerID int NOT NULL,
    maxEnrollment int NOT NULL,
    credits int NOT NULL
);

CREATE TABLE FACULTATE.LEHRER (
	lehrerID int PRIMARY KEY,
	personID int NOT NULL,
    foreign key (personID) references PERSON(personID)
);

CREATE TABLE FACULTATE.ENROLLED (
	studentID int NOT NULL,
	kursID int NOT NULL,
    foreign key (studentID) references STUDENT(studentID),
    foreign key (kursID) references KURS(kursID)
);

INSERT INTO FACULTATE.PERSON(personID, firstName, lastName)
VALUES (1, 'Georgiana', 'Iepan');
INSERT INTO FACULTATE.PERSON(personID, firstName, lastName)
VALUES (2, 'Ana', 'Pop');
INSERT INTO FACULTATE.PERSON(personID, firstName, lastName)
VALUES (3, 'Andrei', 'Popescu');
INSERT INTO FACULTATE.PERSON(personID, firstName, lastName)
VALUES (4, 'Maria', 'Gheorghiu');

INSERT INTO FACULTATE.STUDENT(studentID, totalCredits, personID)
VALUES (1, 30, 1);
INSERT INTO FACULTATE.STUDENT(studentID, totalCredits, personID)
VALUES (2, 30, 3);

INSERT INTO FACULTATE.LEHRER(lehrerID, personID)
VALUES (1, 2);
INSERT INTO FACULTATE.LEHRER(lehrerID, personID)
VALUES (2, 4);

INSERT INTO FACULTATE.KURS(kursID, name, lehrerID, maxEnrollment, credits)
VALUES (1, 'BD', 1, 30, 6);
INSERT INTO FACULTATE.KURS(kursID, name, lehrerID, maxEnrollment, credits)
VALUES (2, 'MAP', 2, 25, 5);




