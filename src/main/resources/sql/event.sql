/**
 *	RBS events.
 *
 *	Author: Zhou Shengsheng
 *	Date: 05/05/2016
 */

use rbs;
show events;

/**
 *	Validate the time of RoomBooking table.
 */
delimiter $$
create event if not exists event_validateRoomBookingTime
on schedule every 10 minute
do
begin
update rbs.`RoomBooking`
	set expired = 1
	where toTime < now();
end$$
delimiter ;
