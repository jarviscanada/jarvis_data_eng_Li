-- =========================
-- Table Setup (DDL)
-- =========================

CREATE SCHEMA IF NOT EXISTS cd;

CREATE TABLE cd.members (
    memid INTEGER NOT NULL,
    surname VARCHAR(200) NOT NULL,
    firstname VARCHAR(200) NOT NULL,
    address VARCHAR(300) NOT NULL,
    zipcode INTEGER NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    recommendedby INTEGER,
    joindate TIMESTAMP NOT NULL,
    CONSTRAINT members_pk PRIMARY KEY (memid),
    CONSTRAINT members_recommendedby_fk FOREIGN KEY (recommendedby)
        REFERENCES cd.members(memid)
        ON DELETE SET NULL
);

CREATE TABLE cd.facilities (
    facid INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    membercost NUMERIC NOT NULL,
    guestcost NUMERIC NOT NULL,
    initialoutlay NUMERIC NOT NULL,
    monthlymaintenance NUMERIC NOT NULL,
    CONSTRAINT facilities_pk PRIMARY KEY (facid)
);

CREATE TABLE cd.bookings (
    bookid INT NOT NULL,
    facid INT NOT NULL,
    memid INT NOT NULL,
    starttime TIMESTAMP NOT NULL,
    slots INT NOT NULL,
    CONSTRAINT bookings_pk PRIMARY KEY (bookid),
    CONSTRAINT bookings_facid_fk FOREIGN KEY (facid) REFERENCES cd.facilities(facid),
    CONSTRAINT bookings_memid_fk FOREIGN KEY (memid) REFERENCES cd.members(memid)
);

-- =========================
-- SQL Queries / Exercises
-- =========================

-- Question 1: Show all members
SELECT * FROM cd.members;

-- Question 2: Add row of data in facilities table
INSERT INTO cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
VALUES (9,'Spa',20,30,100000,800);

-- Question 3: Insert calculated data into a table
INSERT INTO cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
VALUES ((SELECT MAX(facid)+1 FROM cd.facilities), 'Spa', 20, 30, 100000, 800);

-- Question 4: Update some existing data
UPDATE cd.facilities
SET initialoutlay = 10000
WHERE name = 'Tennis Court 2';

-- Question 5: Update a row based on the contents of another row
UPDATE cd.facilities
SET membercost = (SELECT membercost*1.1 FROM cd.facilities WHERE facid = 0),
    guestcost = (SELECT guestcost*1.1 FROM cd.facilities WHERE facid = 0)
WHERE facid = 1;

-- Question 6: Delete all bookings
DELETE FROM cd.bookings
WHERE bookid IS NOT NULL;

-- Question 7: Delete a member from cd.members
DELETE FROM cd.members
WHERE memid = 37;

-- Question 8: Control which rows are retrieved
SELECT facid, name, membercost, monthlymaintenance
FROM cd.facilities
WHERE membercost < monthlymaintenance / 50.0
  AND membercost > 0;

-- Question 9: Basic string searches
SELECT * FROM cd.facilities
WHERE name LIKE '%Tennis%';

-- Question 10: Matching against multiple values
SELECT * FROM cd.facilities
WHERE facid IN (1,5);

-- Question 11: Working with dates
SELECT memid, surname, firstname, joindate
FROM cd.members
WHERE joindate >= '2012-09-01';

-- Question 12: Combining results from multiple queries
SELECT surname FROM cd.members
UNION
SELECT name FROM cd.facilities;

-- Question 13: Retrieve start times of members' bookings
SELECT b.starttime
FROM cd.bookings b
LEFT JOIN cd.members m ON b.memid = m.memid
WHERE firstname='David' AND surname='Farrell';

-- Question 14: Work out start times for tennis court bookings
SELECT b.starttime AS start, f.name
FROM cd.bookings b
LEFT JOIN cd.facilities f ON b.facid = f.facid
WHERE name IN ('Tennis Court 1','Tennis Court 2')
  AND starttime BETWEEN '2012-09-21' AND '2012-09-22'
ORDER BY start;

-- Question 15: Produce list of all members along with recommender
SELECT m.firstname AS memfname, m.surname AS memsname,
       r.firstname AS recfname, r.surname AS recsname
FROM cd.members m
LEFT JOIN cd.members r ON r.memid = m.recommendedby
ORDER BY 2,1;

-- Continue adding remaining queries in same format...
