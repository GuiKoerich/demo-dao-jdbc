create dabatase jdbc;

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