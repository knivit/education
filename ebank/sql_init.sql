-- Init script

create table Department (
  DepartmentId int not null primary key,
  Name         varchar(64) not null
);

create table Employee (
  EmployeeId   int not null primary key,
  DepartmentId int not null references Department(DepartmentId),
  ChiefId      int null references Employee(EmployeeID),
  Name         varchar(64),
  Salary       numeric(18,2)
);

insert into Department values (1, 'Dep #1');
insert into Department values (2, 'Dep #2');
insert into Department values (3, 'Dep #3');
insert into Department values (4, 'Dep #4');
insert into Department values (5, 'Dep #5');

insert into Employee values(1, 1, null,  'Chief #1 Dep #1         [10 000]', 10000);
insert into Employee values(2, 1,    1,  'Empl #1 Chief #1 Dep #1 [ 9 000]', 9000);
insert into Employee values(3, 1,    1,  'Empl #2 Chief #1 Dep #1 [11 000]', 11000);
insert into Employee values(4, 1, null,  'Chief #2 Dep #1         [ 9 000]', 9000);

insert into Employee values(5, 2, null,  'Chief #1 Dep #2         [20 000]', 20000);
insert into Employee values(6, 2,    5,  'Empl #1 Chief #1 Dep #2 [19 000]', 19000);
insert into Employee values(7, 2,    5,  'Empl #2 Chief #1 Dep #2 [12 000]', 12000);
insert into Employee values(8, 2,    5,  'Empl #3 Chief #1 Dep #2 [ 7 000]', 7000);
insert into Employee values(9, 2,    5,  'Empl #4 Chief #1 Dep #2 [ 3 000]', 3000);

insert into Employee values(10, 3, null, 'Chief #1 Dep #3         [10 000]', 10000);
insert into Employee values(11, 3,   10, 'Empl #1 Chief #1 Dep #3 [ 2 000]', 2000);
insert into Employee values(12, 3,   10, 'Empl #2 Chief #1 Dep #3 [ 6 000]', 6000);
insert into Employee values(13, 3,   10, 'Empl #3 Chief #1 Dep #3 [ 5 000]', 5000);

insert into Employee values(14, 4, null, 'Chief #1 Dep #4         [30 000]', 30000);
insert into Employee values(15, 4,   14, 'Empl #1 Chief #1 Dep #4 [32 000]', 32000);
insert into Employee values(16, 4, null, 'Chief #2 Dep #4         [ 1 000]', 1000);
insert into Employee values(17, 4,   16, 'Empl #1 Chief #2 Dep #4 [ 3 000]', 3000);
