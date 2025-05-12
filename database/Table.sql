---------------------------------------------------------------

--12 provides the max limit but we need to put seperate checks too
/*
Explanation of Each Part
1. CHECK (...)
This is a table constraint that ensures certain conditions are met for the values in the column.

If the condition inside the parentheses evaluates to FALSE, Oracle will reject the row.

2. REGEXP_LIKE(column, 'pattern')
REGEXP_LIKE is an Oracle SQL function that checks if a column value matches a specified regular expression pattern.

It returns TRUE if the value matches the pattern, FALSE otherwise.

3. aadhar_no
This is the column name being checked (your Aadhaar number).

4. '^[2-9][0-9]{11}$'
This is the regular expression pattern. Let’s break it down:

^	---->          Start of the string
[2-9]	---->      The first character must be a digit from 2 to 9
[0-9]{11}	---->  The next 11 characters must each be a digit from 0 to 9 (any digit)
$	-------------> End of the string
*/

create table Aadhar --Parent Table
(
    aadhar_no VARCHAR(12) PRIMARY KEY CHECK (REGEXP_LIKE(aadhar_no, '^[2-9][0-9]{11}$')),
    name varchar(20),
    passkey VARCHAR(10)
);

select * from Aadhar;
drop table Aadhar;

---------------------------------------------------------------

create table Account --Child Table
(   acc_no VARCHAR(20) PRIMARY KEY,
    name VARCHAR2(25) not null,
    aadhar_no VARCHAR(12) not null,  --foreign key of this table references primary key of the Aadhar table
    balance number(7,2) default 0.00,
    pin number(3,0) CHECK(pin BETWEEN 100 AND 999),
    FOREIGN KEY (aadhar_no) REFERENCES Aadhar(aadhar_no)
);

select * from Account;
drop table Account;
commit;

--------------------------------------------------------------------