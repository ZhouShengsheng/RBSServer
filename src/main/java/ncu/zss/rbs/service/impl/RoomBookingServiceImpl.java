package ncu.zss.rbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncu.zss.rbs.dao.RoomBookingGroupMapper;
import ncu.zss.rbs.dao.RoomBookingInfoMapper;
import ncu.zss.rbs.dao.RoomBookingMapper;
import ncu.zss.rbs.dao.RoomMapper;
import ncu.zss.rbs.dao.StudentBookingGroupMapper;
import ncu.zss.rbs.model.RoomBooking;
import ncu.zss.rbs.model.RoomBookingGroup;
import ncu.zss.rbs.model.RoomBookingInfo;
import ncu.zss.rbs.model.StudentBookingGroup;
import ncu.zss.rbs.service.RoomBookingService;

@Service("roomBookingServiceImpl")
public class RoomBookingServiceImpl implements RoomBookingService {
	
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	RoomMapper roomMapper;
	
	@Autowired
	RoomBookingMapper roomBookingMapper;
	
	@Autowired
	RoomBookingGroupMapper roomBookingGroupMapper;
	
	@Autowired
	RoomBookingInfoMapper roomBookingInfoMapper;
	
	@Autowired
	StudentBookingGroupMapper studentBookingGroupMapper;

	@Override
	public List<RoomBooking> getRoomBookingListForRoom(String roomBuilding, String roomNumber) {
		return roomBookingMapper.selectRoomBookingListForRoom(roomBuilding, roomNumber);
	}

	@Override
	public List<RoomBooking> getRoomBookingListForApplicant(String applicantType, String applicantId) {
		return roomBookingMapper.selectRoomBookingListForApplicant(applicantType, applicantId);
	}

	@Override
	public String bookRoom(String roomBuilding, String roomNumber, String applicantType, String applicantId,
			ArrayList<ArrayList<Date>> timeIntervalList, String bookReason, String facultyId) {
		String groupId = UUID.randomUUID().toString();
		String status = applicantType.equals("student") ? "created" : "faculty_approved";
		logger.info("groupId: " + groupId);
		logger.info("status: " + status);
		logger.info("timeIntervalCount: " + timeIntervalList.size());
		
		for (ArrayList<Date> timeInterval : timeIntervalList) {
			roomBookingMapper.insertBookRoom(groupId, roomBuilding, roomNumber, 
					applicantType, applicantId, 
					timeInterval.get(0), timeInterval.get(1), bookReason, status, facultyId);
		}
		
		return groupId;
	}

	@Override
	public List<RoomBookingGroup> getProcessingList(String applicantType, String applicantId, Integer fromIndex) {
		return roomBookingGroupMapper.selectProcessingList(applicantType, applicantId, fromIndex);
	}

	@Override
	public List<RoomBookingGroup> getApprovedList(String applicantType, String applicantId, Integer fromIndex) {
		return roomBookingGroupMapper.selectApprovedList(applicantType, applicantId, fromIndex);
	}

	@Override
	public List<RoomBookingGroup> getDeclinedList(String applicantType, String applicantId, Integer fromIndex) {
		return roomBookingGroupMapper.selectDeclinedList(applicantType, applicantId, fromIndex);
	}

	@Override
	public RoomBookingInfo getRoomBookingInfo(String groupId) {
		return roomBookingInfoMapper.selectRoomBookingInfo(groupId);
	}

	@Override
	public void cancelBooking(String groupId) {
		roomBookingMapper.updateToCancelBooking(groupId);
	}

	@Override
	public List<RoomBookingGroup> getHistoryList(String applicantType, String applicantId, Integer fromIndex) {
		return roomBookingGroupMapper.selectHistoryList(applicantType, applicantId, fromIndex);
	}

	@Override
	public List<StudentBookingGroup> getStudentBookingList(String facultyId, Integer fromIndex) {
		return studentBookingGroupMapper.selectWithFacultyId(facultyId, fromIndex);
	}
	
	@Override
	public List<RoomBookingGroup> getAdminProcessingList(Integer fromIndex) {
		return roomBookingGroupMapper.selectAdminProcessingList(fromIndex);
	}

	@Override
	public List<RoomBookingGroup> getAdminProcessedList(Integer fromIndex) {
		return roomBookingGroupMapper.selectAdminProcessedList(fromIndex);
	}

	@Override
	public void supervisorApprove(String groupId) {
		roomBookingMapper.updateToSupervisorApprove(groupId);
	}

	@Override
	public void supervisorDecline(String groupId, String declineReason) {
		roomBookingMapper.updateToSupervisorDecline(groupId, declineReason);
	}

	@Override
	public void adminApprove(String groupId) {
		roomBookingMapper.updateToAdminApprove(groupId);
	}

	@Override
	public void adminDecline(String groupId, String declineReason) {
		roomBookingMapper.updateToAdminDecline(groupId, declineReason);
	}
	
}
