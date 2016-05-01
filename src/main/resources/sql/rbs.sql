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

select * from FavoriteRoom;

select * from Supervisor;

insert into StudentFacultyMapping(studentId, facultyId)
	values('8000112164', '10002');
    
rename table StudentFacultyMapping to Supervisor;

select * from Class;
update Class set facultyId = '10003';

select F.*
	from Faculty as F
    join (select C.facultyId
			from Class as C
			join (select className
					from Student
					where id = '8000112164') as SC
			on C.`name` = SC.className) as FC
	on F.id = FC.facultyId;

select now();

desc RoomBooking;
select * from RoomBooking;
select uuid();
insert into RoomBooking (groupId, roomBuilding, roomNumber, 
		applicantType, applicantId, fromTime, toTime,
        bookReason, `status`, adminId, facultyId)
	values (uuid(), '软件楼', '108',
		'student', '8000112164', '2016-05-02 19:00:00', '2016-05-02 20:30:00',
        'test', 'created', (select id from Admin), '10003');

select UNIX_TIMESTAMP(now());
select groupId, roomBuilding, roomNumber, 
		applicantType, applicantId, fromTime, toTime,
        bookReason, `status`, adminId, facultyId
	from RoomBooking
    where roomBuilding = '软件楼'
		and roomNumber = '108';

