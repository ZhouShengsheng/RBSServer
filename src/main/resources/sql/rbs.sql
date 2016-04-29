use rbs;

select * from rbs.Room;
select * from rbs.Student;
select * from rbs.Faculty;

desc StudentFacultyMapping;

select concat('rbs_', id) from Faculty;
update Faculty set password=sha1(concat('rbs_', id));
update Student set password=sha1(concat('rbs_', id));

-- Login.
select * from rbs.Student;
select * from rbs.Faculty where idDigest='6c447a8fe7677ddc4c4cd2efddcfe650e4e6c706';

alter table Faculty
	change id_digest
		idDigest char(40) not null;

-- change order
alter table Student
	change idDigest
		idDigest char(40) not null after id;

select *
  		from Room
  		where building = '软件楼'
  		order by number
  		limit 0, 10;

select @@lower_case_table_names;

desc Faculty;
desc Student;
