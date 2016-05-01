package ncu.zss.rbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncu.zss.rbs.dao.RoomBookingMapper;
import ncu.zss.rbs.dao.RoomMapper;
import ncu.zss.rbs.model.RoomBooking;
import ncu.zss.rbs.service.RoomBookingService;

@Service("roomBookingServiceImpl")
public class RoomBookingServiceImpl implements RoomBookingService {
	
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	RoomMapper roomMapper;
	
	@Autowired
	RoomBookingMapper roomBookingMapper;

	@Override
	public List<RoomBooking> getRoomBookingListForRoom(String roomBuilding, String roomNumber) {
		return roomBookingMapper.selectRoomBookingListForRoom(roomBuilding, roomNumber);
	}

	@Override
	public List<RoomBooking> getRoomBookingListForApplicant(String applicantType, String applicantId) {
		return roomBookingMapper.selectRoomBookingListForApplicant(applicantType, applicantId);
	}

	@Override
	public void bookRoom(String roomBuilding, String roomNumber, String applicantType, String applicantId,
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
	}
	
}
