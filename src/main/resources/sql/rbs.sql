select * from rbs.Room;
select * from rbs.Student;
select * from rbs.Faculty;

desc Student;

select concat('rbs_', id) from Faculty;
update Faculty set password=sha1(concat('rbs_', id));

update Student set password=sha1(concat('rbs_', id));