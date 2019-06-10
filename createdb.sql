create database jdbc;

\c jdbc;

create table if not exists department (
    id serial primary key,
    name varchar(100) not null
);

create table if not exists seller (
    id serial primary key,
    name varchar(100) not null,
    email varchar(200) not null,
    birthDate timestamp not null,
    baseSalary float not null,
    departmentId int not null,

    constraint seller_department_fkey foreign key (departmentId) references department(id) match simple 
    on update no action on delete no action
);

INSERT INTO department(name) VALUES('Computers'),('Eletronics'),('Fashion'),('Books');
INSERT INTO seller(name, email, birthDate, baseSalary, departmentId) VALUES('Bob Brown','bob@gmail.com','1998-04-21 00:00:00.000000',1000,1),('Maria Green','maria@gmail.com','1979-12-31 00:00:00.000000',3500,2),('Alex Grey','alex@gmail.com','1988-01-15 00:00:00.000000',2200,1),('Martha Red','martha@gmail.com','1993-11-30 00:00:00.000000',3000,4),('Donald Blue','donald@gmail.com','2000-01-09 00:00:00.000000',4000,3),('Alex Pink','bob@gmail.com','1997-03-04 00:00:00.000000',3000,2);