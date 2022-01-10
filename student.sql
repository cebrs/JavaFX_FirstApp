DROP DATABASE student;
CREATE DATABASE IF NOT EXISTS  student;
USE student;

CREATE TABLE IF NOT EXISTS studenttable
(id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(45),
gender VARCHAR(45),
dateofbirth DATE,
photo varchar(500),
mark DOUBLE,
comments VARCHAR(45),
primary key (id)
);

-- insert Student--
insert into `student`.`studenttable` (`name`,`gender`,`dateofbirth`,`mark`,`comments`) values ('Celia','Female', '2021-11-08', 13, 'demo');
insert into `student`.`studenttable` (`name`,`gender`) values ('Jules','Male');
insert into `student`.`studenttable` (`name`,`gender`) values ('Thomas','Male');
insert into `student`.`studenttable` (`name`,`gender`) values ('Alex','Female');

select * from student.studenttable