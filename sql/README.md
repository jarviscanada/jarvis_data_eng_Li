# Introduction

# SQL Queries

###### Table Setup (DDL)

```
CREATE table cd.members (
  memid integer NOT NULL, 
  surname varchar(200) NOT NULL, 
  firstname varchar(200) NOT NULL, 
  address varchar(300) NOT NULL, 
  zipcode integer NOT NULL, 
  telephone varchar(20) NOT NULL, 
  recommendedby integer, 
  joindate timestamp NOT NULL, 
  CONSTRAINT members_pk PRIMARY KEY (memid), 
  CONSTRAINT members_recommendedby_fk FOREIGN KEY (recommendedby) REFERENCES cd.members(memid) ON DELETE 
  SET 
    NULL
);
```
```
CREATE TABLE cd.bookings (
  bookid int NOT NULL, 
  facid int NOT NULL, 
  memid int NOT NULL, 
  starttime timestamp NOT NULL, 
  slots int NOT NULL, 
  CONSTRAINT bookings_pk PRIMARY KEY (bookid), 
  CONSTRAINT bookings_facid_fk FOREIGN KEY (facid) REFERENCES cd.facilities(facid), 
  CONSTRAINT bookings_memid_fk FOREIGN KEY (memid) REFERENCES cd.members(memid)
);

```
```
CREATE TABLE cd.facilities (
  facid int NOT NULL, 
  name varchar(100) NOT NULL, 
  membercost numeric NOT NULL, 
  guestcost numeric NOT NULL, 
  initialoutlay numeric NOT NULL, 
  monthlymaintenance numeric NOT NULL, 
  CONSTRAINT facilities_pk PRIMARY KEY (facid)
);
```

###### Question 1: Show all members 

select * from cd.members;

###### Question 2: Add row of data in facilities table

insert into cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
values (9,'Spa',20,30,100000,800);

###### Question 3:

insert into cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
values ((select max(facid) + 1 from cd.facilities),'Spa', 20, 30, 100000, 800);







