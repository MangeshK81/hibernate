
drop table if exists Employee;
create table if not exists Employee (

emp_id  int primary key auto_increment,
ename  varchar not null
);

drop table if exists Address;
create table if not exists Address (
  address_id int primary key auto_increment,
  description varchar not null,
  emp_id int not null,
  foreign key (emp_id) references Employee(emp_id)
)