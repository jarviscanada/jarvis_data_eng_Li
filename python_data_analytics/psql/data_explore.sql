-- Show table schema 
\d+ retail;

-- Show first 10 rows
SELECT * FROM retail limit 10;

-- Check # of records
select count(invoice_no) from retail;

-- number of clients (e.g. unique client ID)
select count(distinct customer_id) from retail;

-- invoice date range (e.g. max/min dates)
select max(invoice_date), min(invoice_date) from retail;

-- number of SKU/merchants (e.g. unique stock code)
select count(distinct stock_code) from retail;

-- Calculate average invoice amount excluding invoices with a negative amount (e.g. canceled orders have negative amount)
select avg(invoice_tot) as avg 
from (select invoice_no, sum(quantity * unit_price) as invoice_tot from retail group by 1 having sum(quantity * unit_price) > 0) as total; 

-- Calculate total revenue (e.g. sum of unit_price * quantity)
select sum(quantity * unit_price) from retail;

-- Calculate total revenue by YYYYMM
select to_char(invoice_date,'YYYYMM'), sum(quantity * unit_price) from retail group by 1 order by 1 asc;
