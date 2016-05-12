package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.RoomBookingInfo;

public interface RoomBookingInfoMapper {
    
	/**
	 * Select detailed room booking info.
	 * 
	 * @param groupId
	 * @return
	 */
	RoomBookingInfo selectRoomBookingInfo(String groupId);
	
}