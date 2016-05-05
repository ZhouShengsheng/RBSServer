package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.RoomBookingInfo;

public interface RoomBookingInfoMapper {
	
	/**
	 * Select room booking detailed info.
	 * 
	 * @param groupId
	 * @return
	 */
	RoomBookingInfo selectRoomBookingInfo(String groupId);
	
}