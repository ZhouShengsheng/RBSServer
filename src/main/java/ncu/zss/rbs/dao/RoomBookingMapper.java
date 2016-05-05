package ncu.zss.rbs.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import ncu.zss.rbs.model.RoomBooking;

public interface RoomBookingMapper {
    
	/**
	 * Select room booking list for specific room.
	 * 
	 * @param roomBuilding
	 * @param roomNumber
	 * @return
	 */
	List<RoomBooking> selectRoomBookingListForRoom(@Param("roomBuilding") String roomBuilding, @Param("roomNumber") String roomNumber);
	
	/**
	 * Select room booking list for specific applicant.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @return
	 */
	List<RoomBooking> selectRoomBookingListForApplicant(@Param("applicantType") String applicantType, @Param("applicantId") String applicantId);
	
	/**
	 * Insert book room.
	 * 
	 * @param roomBuilding
	 * @param roomNumber
	 * @param applicantType
	 * @param applicantId
	 * @param from
	 * @param to
	 * @param bookReason
	 * @param status
	 * @param facultyId
	 */
	void insertBookRoom(@Param("groupId") String groupId,
			@Param("roomBuilding") String roomBuilding,
			@Param("roomNumber") String roomNumber,
			@Param("applicantType") String applicantType,
			@Param("applicantId") String applicantId, 
			@Param("fromTime") Date fromTime,
			@Param("toTime") Date toTime,
			@Param("bookReason") String bookReason,
			@Param("status") String status,
			@Param("facultyId") String facultyId);
	
	/**
	 * Cancel booking room.
	 * 
	 * @param groupId
	 * @return
	 */
	void updateToCancelBooking(String groupId);
	
}