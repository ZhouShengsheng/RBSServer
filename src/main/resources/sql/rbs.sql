use rbs;

select * from rbs.Room;
select * from rbs.Student;
select * from rbs.Faculty;

desc StudentFacultyMapping;

select concat('rbs_', id) from Faculty;
update Faculty set password=sha1(concat('rbs_', id));
update Student set password=sha1(concat('rbs_', id));
update Faculty
	set `name` = '教室管理员'
    where id = '10001';

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

select id from Admin limit 0,1;

desc `RoomBooking`;
select * from rbs.`RoomBooking`
        where toTime < now();

update rbs.`RoomBooking`
		set `status` = 'created'
        where `status` = 'expired';

update RoomBooking
	set expired = 0;

alter table RoomBooking add column declineReason varchar(1024);
alter table RoomBooking 
	change column expired expired tinyint not null default 0;
alter table RoomBooking 
	change column `status` `status` enum('created','canceled','faculty_declined','faculty_approved','admin_declined','admin_approved')
		not null default 'created';
alter table RoomBooking 
	add column creationDate datetime not null default now();
alter table RoomBooking 
	change column creationDate creationTime datetime not null default now();

create index index_expired on RoomBooking(expired);
create index index_creationDate on RoomBooking(creationDate);


-- Room booking.
-- Applying.
select *
	from View_RoomBookingGroup
	where applicantType = 'student'
		and applicantId = '8000112164'
        and (`status` = 'created'
			or `status` = 'faculty_approved');

-- Approved.
select RA.*, F.`name` as facultyName, F.designation as facultyDesignation,
		F.gender as facultyGender, F.office as facultyOffice, F.phone as facultyPhone
	from (select R.*, A.`name` as adminName, A.designation as adminDesignation, 
				A.gender as adminGender, A.office as adminOffice, A.phone as adminPhone
			from (select *
					from View_RoomBookingGroup
					where `status` = 'admin_approved') as R
			join Faculty as A
			on R.adminId = A.id) as RA
    join Faculty as F
    on RA.facultyId = F.id;

-- Declined.
select *
	from View_RoomBookingGroup
    where `status` = 'faculty_declined'
		or `status` = 'admin_declined';

desc Faculty;
select * from Faculty;
update Faculty set password=sha1('12345') where id='10001';

desc RoomBooking;

select * from RoomBooking;
update RoomBooking set `status` = 'admin_approved' where id = 15 or id = 16;

select * from Room
	where concat(building, `number`) regexp '.*软件楼101.*';

select * from Room
	where concat(building, `number`) like '%软件楼101%';

select * from Supervisor;

select R.*
	from Room as R
    join (select roomBuilding, roomNumber 
			from FavoriteRoom
			where personType = 'student'
				and personId = '8000112164') as F
	on R.building = F.roomBuilding
		and R.`number` = F.roomNumber;

select * from `Student`;

desc RoomBooking;
select * from RoomBooking;

desc View_RoomBookingGroup;

select RB.groupId, RB.roomBuilding, RB.roomNumber, RB.timeIntervals, RB.bookReason,
		RB.adminId, RB.facultyId, RB.groupId, RB.declineReason, creationTime, 
        S.id as studentId, S.`name` as studentName, S.className as studentClassName,
        S.gender as studentGender, S.dormRoomNumber as studentDormRoomNumber,
        S.phone as studentPhone
	from (select *
			from View_RoomBookingGroup
			where `status` = 'created'
				and expired != 1) as RB
	join Student as S
	on RB.applicantId = S.id;

select *
	from View_RoomBookingGroup
	where groupId = '639a1401-e0a7-42ce-a512-bbf8a771187d';

desc RoomBooking;

select * from View_StudentBookingGroup;

update RoomBooking
	set bookReason = '测试';

select * from RoomBooking;

update RoomBooking
	set `status` = 'admin_approved'
    where id in (34, 35, 36);

desc PushNotification;

drop table PushNotification;

CREATE TABLE IF NOT EXISTS `rbs`.`PushNotification` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userType` ENUM('admin', 'faculty', 'student') NOT NULL,
  `userId` VARCHAR(20) NOT NULL,
  `apnToken` VARCHAR(128) NOT NULL,
  unique(`userType`, `userId`),
  PRIMARY KEY (`id`));

insert into PushNotification(userType, userId, apnToken)
	values('faculty', '10003', '78b61e89b8104b54c74dae56b0144d3de217f12659d7e38743d9486130e96619');

insert into PushNotification(userType, userId, apnToken)
	values ('faculty', '10003', '78b61e89b8104b54c74dae56b0144d3de217f12659d7e38743d9486130e96619')
	on duplicate key update
	apnToken = values(apnToken);

select * from PushNotification;