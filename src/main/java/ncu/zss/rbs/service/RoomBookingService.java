package ncu.zss.rbs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ncu.zss.rbs.model.RoomBooking;
import ncu.zss.rbs.model.RoomBookingGroup;
import ncu.zss.rbs.model.RoomBookingInfo;
import ncu.zss.rbs.model.StudentBookingGroup;

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
	 * 
	 * @return groupId
	 */
	String bookRoom(String roomBuilding, String roomNumber, String applicantType, String applicantId, 
			ArrayList<ArrayList<Date>> timeIntervalList, String bookReason, String facultyId);
	
	/**
	 * Get room booking processing list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @return
	 */
	List<RoomBookingGroup> getProcessingList(String applicantType, String applicantId, Integer fromIndex);
	
	/**
	 * Get room booking approved list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @return
	 */
	List<RoomBookingGroup> getApprovedList(String applicantType, String applicantId, Integer fromIndex);
	
	/**
	 * Get room booking declined list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @return
	 */
	List<RoomBookingGroup> getDeclinedList(String applicantType, String applicantId, Integer fromIndex);
	
	/**
	 * Get room booking detailed info.
	 * 
	 * @param groupId
	 * @return
	 */
	RoomBookingInfo getRoomBookingInfo(String groupId);
	
	/**
	 * Cancel booking room.
	 * 
	 * @param groupId
	 * @return
	 */
	void cancelBooking(String groupId);
	
	/**
	 * Get room booking history list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @return
	 */
	List<RoomBookingGroup> getHistoryList(String applicantType, String applicantId, Integer fromIndex);
	
	/**
	 * Get student booking list.
	 * 
	 * @param facultyId
	 * @param fromIndex
	 * @return
	 */
	List<StudentBookingGroup> getStudentBookingList(String facultyId, Integer fromIndex);
	
	/**
	 * Get admin processing list.
	 * 
	 * @param fromIndex
	 * @return
	 */
	List<RoomBookingGroup> getAdminProcessingList(Integer fromIndex);
	
	/**
	 * Get admin processed list.
	 * 
	 * @param fromIndex
	 * @return
	 */
	List<RoomBookingGroup> getAdminProcessedList(Integer fromIndex);
	
	/**
	 * Supervisor approve the booking group.
	 * 
	 * @param groupId
	 */
	void supervisorApprove(String groupId);
	
	/**
	 * Supervisor decline the booking group.
	 * 
	 * @param groupId
	 * @param declineReason
	 */
	void supervisorDecline(String groupId, String declineReason);
	
	/**
	 * Admin approve the booking group.
	 * 
	 * @param groupId
	 */
	void adminApprove(String groupId);
	
	/**
	 * Admin decline the booking group.
	 * 
	 * @param groupId
	 * @param declineReason
	 */
	void adminDecline(String groupId, String declineReason);
	
}
