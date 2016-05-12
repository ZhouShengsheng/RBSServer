/**
 *	RBS events.
 *
 *	Author: Zhou Shengsheng
 *	Date: 05/05/2016
 */

use rbs;

/**
 *	Room booking info grouped by groupId.
 */
create view View_RoomBookingGroup as
select groupId, roomBuilding, roomNumber, applicantType, applicantId,
		concat('[', group_concat(concat('[\"', fromTime, ',', toTime, '\"]') separator ','), ']') as timeIntervals,
        bookReason, `status`, adminId, facultyId, declineReason, expired, creationTime
	from RoomBooking
    group by groupId
    order by creationTime desc;

/**
 *	Room booking grouped info with admin info and faculty info.
 */
create view View_RoomBookingInfo as
select R.*, DIA.*
	from (select DI.*, F.`name` as facultyName, F.designation as facultyDesignation,
				F.gender as facultyGender, F.office as facultyOffice, F.phone as facultyPhone,
				A.`name` as adminName, A.designation as adminDesignation,
							A.gender as adminGender, A.office as adminOffice, A.phone as adminPhone
			from (select RBG.*, S.`name` as studentName, S.className as studentClassName,
						S.gender as studentGender, S.dormRoomNumber as studentDormRoomNumber, 
						S.phone as studentPhone
					from (select *
							from View_RoomBookingGroup as VRBG) as RBG
					join Student as S
					on RBG.applicantId = S.id) as DI
			join Faculty as F
			on DI.facultyId = F.id
			join Faculty as A
			on DI.adminId = A.id) as DIA
	join Room as R
    on DIA.roomBuilding = R.building
		and DIA.roomNumber = R.`number`;

/**
 *	Student room booking group.
 */
create view View_StudentBookingGroup as
select RB.groupId, RB.roomBuilding, RB.roomNumber, RB.timeIntervals, RB.bookReason,
		RB.adminId, RB.facultyId, RB.declineReason, creationTime, 
        S.id as studentId, S.`name` as studentName, S.className as studentClassName,
        S.gender as studentGender, S.dormRoomNumber as studentDormRoomNumber,
        S.phone as studentPhone
	from (select *
			from View_RoomBookingGroup
			where `status` = 'created'
				and expired != 1) as RB
	join Student as S
	on RB.applicantId = S.id;
