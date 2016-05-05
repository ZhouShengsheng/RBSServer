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