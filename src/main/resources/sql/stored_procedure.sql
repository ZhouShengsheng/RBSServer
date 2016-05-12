/**
 *	RBS stored procedure.
 */

use rbs;

drop procedure sp_roomBookingInfo;

delimiter $$
CREATE PROCEDURE sp_roomBookingInfo(
	in p_groupId char(36)
)
BEGIN
	-- declare varibles
    declare v_applicantType char(36);
    declare v_applicantId char(36);
    
	-- Sql error handler.
	declare exit handler for sqlexception
	begin
        select 'sqlexception occurs.' as message;
	end;

	-- Sql warning handler.
	declare exit handler for sqlwarning
	begin
        select 'sqlwarning occurs.' as message;
	end;
    
    -- Get applicant type and id.
    select applicantType, applicantId into v_applicantType, v_applicantId
		from View_RoomBookingGroup
        where groupId = p_groupId;
    
    -- Get detailed info.
    case v_applicantType
		when 'faculty' then
			select R.*, RBGA.*
				from (select RBG.*, F.`name` as facultyName, F.designation as facultyDesignation,
							F.gender as facultyGender, F.office as facultyOffice, F.phone as facultyPhone,
							A.`name` as adminName, A.designation as adminDesignation,
							A.gender as adminGender, A.office as adminOffice, A.phone as adminPhone
						from (select *
								from View_RoomBookingGroup as VRBG
								where groupId = p_groupId
									and VRBG.applicantId = v_applicantId) as RBG
						join Faculty as F
						on RBG.applicantId = F.id
						join Faculty as A
						on RBG.adminId = A.id) as RBGA
				join Room as R
				on RBGA.roomBuilding = R.building
					and RBGA.roomNumber = R.`number`;
		when 'student' then
			select R.*, DIA.*
				from (select DI.*, F.`name` as facultyName, F.designation as facultyDesignation,
							F.gender as facultyGender, F.office as facultyOffice, F.phone as facultyPhone,
							A.`name` as adminName, A.designation as adminDesignation,
							A.gender as adminGender, A.office as adminOffice, A.phone as adminPhone
						from (select RBG.*, S.`name` as studentName, S.className as studentClassName,
									S.gender as studentGender, S.dormRoomNumber as studentDormRoomNumber, 
									S.phone as studentPhone
								from (select *
										from View_RoomBookingGroup as VRBG
										where groupId = p_groupId
											and VRBG.applicantId = v_applicantId) as RBG
								join Student as S
								on RBG.applicantId = S.id) as DI
						join Faculty as F
						on DI.facultyId = F.id
						join Faculty as A
						on DI.adminId = A.id) as DIA
				join Room as R
                on DIA.roomBuilding = R.building
					and DIA.roomNumber = R.`number`;
        else
			begin
            end;
	end case;
end$$
delimiter ;

call sp_roomBookingInfo('06958308-36c1-4353-ac19-6c4dac37fe9c');
call sp_roomBookingInfo('fb62bac7-3822-4632-8f7b-7eb0af8c4113');
