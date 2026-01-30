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
```
select * from cd.members;
```
###### Question 2: Add row of data in facilities table
```
insert into cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
values (9,'Spa',20,30,100000,800);
```
###### Question 3: Insert calculated data into a table
```
insert into cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
values ((select max(facid) + 1 from cd.facilities),'Spa', 20, 30, 100000, 800);
```
###### Question 4: Update some existing data
```
update cd.facilities
set initialoutlay = 10000
where name = 'Tennis Court 2';
```
###### Question 5: Update a row based on the contents of another row
```
update cd.facilities
set membercost = (select membercost * 1.1 from cd.facilities where facid = 0),
guestcost = (select guestcost * 1.1 from cd.facilities where facid = 0)
where facid = 1;
```
###### Question 6: Delete all bookings
```
delete from cd.bookings
where bookid is not null;
```
###### Question 7: Delete a member from the cd.members table
```
delete from cd.members
where memid = 37;
```
###### Question 8: Control which rows are retrieved - part 2
```
select facid, name, membercost, monthlymaintenance from cd.facilities
where membercost < monthlymaintenance / 50.0
and membercost > 0;
```
###### Question 9: Basic string searches
```
select * from cd.facilities 
where name like '%Tennis%';
```
###### Question 10: Matching against multiple possible values
```
select * from cd.facilities
where facid in (1,5);
```
###### Question 11: Working with dates
```
select memid, surname, firstname, joindate from cd.members
where joindate >= '2012-09-01';
```
###### Question 12: Combining results from multiple queries
```
select surname from cd.members union select name from cd.facilities;
```
###### Question 13: Retrieve the start times of members' bookings
```
select b.starttime 
from cd.bookings b
left join cd.members m on b.memid = m.memid
where firstname = 'David'
and surname = 'Farrell';
```
###### Question 14: Work out the start times of bookings for tennis courts
```
select b.starttime as start, f.name
from cd.bookings b
left join cd.facilities f on b.facid = f.facid
where name in ('Tennis Court 1','Tennis Court 2')
and starttime between '2012-09-21' and '2012-09-22'
order by start;
```
###### Question 15: Produce a list of all members, along with their recommender
```
select m.firstname as memfname, m.surname as memsname, r.firstname as recfname, r.surname as recsname
from cd.members m
left join cd.members r on r.memid = m.recommendedby
order by 2,1;
```
###### Question 16: Produce a list of all members who have recommended another member
```
select distinct r.firstname, r.surname
from cd.members m
inner join cd.members r on m.recommendedby = r.memid 
order by r.surname, r.firstname;
```
###### Question 18: Produce a list of all members, along with their recommender, using no joins.
```
select distinct concat(m.firstname,' ', m.surname) as member,
(select distinct concat(r.firstname,' ', r.surname) as recommender from cd.members r where r.memid = m.recommendedby)
from cd.members m
order by 1;
```
###### Question 19: Count the number of recommendations each member makes.
```
select recommendedby, count(*) as count
from cd.members
where recommendedby is not null
group by 1
order by 1;
```
###### Question 20: List the total slots booked per facility

```
select f.facid, sum(b.slots) as "Total Slots"
from cd.facilities f
left join cd.bookings b on f.facid = b.facid
group by 1
order by 1;
```
###### Question 21: List the total slots booked per facility in a given month
```
select f.facid, sum(b.slots) as "Total Slots"
from cd.facilities f
left join cd.bookings b on f.facid = b.facid
where starttime between '2012-09-01' and '2012-10-01'
group by 1
order by 2;
```
###### Question 22: List the total slots booked per facility per month
```
select f.facid, extract(month from starttime) as month, sum(b.slots) as "Total Slots"
from cd.facilities f
left join cd.bookings b on f.facid = b.facid
where starttime between '2012-01-01' and '2013-01-01'
group by 1,2
order by 1,2;
```
###### Question 23: Find the count of members who have made at least one booking
```
select count(distinct memid)
from cd.bookings;
```
###### Question 24: List each member's first booking after September 1st 2012
```
select surname, firstname, m.memid, min(b.starttime) as starttime
from cd.bookings b
left join cd.members m on m.memid = b.memid
where starttime >= '2012-09-01'
group by 1,2,3
order by 3;
```
###### Question 25: Produce a list of member names, with each row containing the total member count
```
select (select count(*) from cd.members) as count, firstname, surname
from cd.members
order by joindate;
```
###### Question 26: Produce a numbered list of members
```
select row_number() over(order by joindate), firstname, surname
from cd.members
order by joindate;
```
###### Question 27: Output the facility id that has the highest number of slots booked, again
```
select facid, total from (select facid, sum(slots) as total, rank() over (order by sum(slots)desc) as rank
			   from cd.bookings
			   group by facid) as ranked
			   where rank = 1;
```
###### Question 28: Format the names of members
```
select concat(surname, ', ', firstname) as name from cd.members;
```
###### Question 29: Find telephone numbers with parentheses
```
select memid, telephone from cd.members where telephone similar to '%[()]%';
```
###### Question 30: Count the number of members whose surname starts with each letter of the alphabet
```
select substring(surname, 1, 1) as letter, count(*) as count
from cd.members
group by 1
order by 1;
```

