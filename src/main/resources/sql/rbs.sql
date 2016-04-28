use rbs;

select * from rbs.Room;
select * from rbs.Student;
select * from rbs.Faculty;

desc Student;

select concat('rbs_', id) from Faculty;
update Faculty set password=sha1(concat('rbs_', id));
update Student set password=sha1(concat('rbs_', id));

-- Login.
select * from rbs.Student;
select * from rbs.Faculty;

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
