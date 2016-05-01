package ncu.zss.rbs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ncu.zss.rbs.model.RoomBooking;

@Service
public interface RoomBookingService {
	
	/**
	 * Get room booking list for specific room.
	 * 
	 * @param roomBuilding
	 * @param roomNumber
	 * @return
	 */
	List<RoomBooking> getRoomBookingListForRoom(String roomBuilding, String roomNumber);
	
	/**
	 * Get room booking list for specific applicant.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @return
	 */
	List<RoomBooking> getRoomBookingListForApplicant(String applicantType, String applicantId);
	
	/**
	 * Book room.
	 * 
	 * @param roomBuilding
	 * @param roomNumber
	 * @param applicantType
	 * @param applicantId
	 * @param timeIntervalList
	 * @param bookReason
	 * @param facultyId
	 */
	void bookRoom(String roomBuilding, String roomNumber, String applicantType, String applicantId, 
			ArrayList<ArrayList<Date>> timeIntervalList, String bookReason, String facultyId);
	
}
