package ncu.zss.rbs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import ncu.zss.rbs.model.RoomBooking;
import ncu.zss.rbs.service.FavoriteRoomService;
import ncu.zss.rbs.service.RoomBookingService;
import ncu.zss.rbs.service.RoomService;
import ncu.zss.rbs.service.SupervisorService;
import ncu.zss.rbs.service.UserService;
import ncu.zss.rbs.util.JsonUtil;

/**
 * APIs related to room.
 *
 */
@Controller
@RequestMapping(value = "/room_booking", produces = "text/plain;charset=UTF-8")
@Scope("prototype")
public class RoomBookingController {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@Autowired
	@Qualifier("roomServiceImpl")
	RoomService roomService;
	
	@Autowired
	@Qualifier("supervisorServiceImpl")
	SupervisorService supervisorService;
	
	@Autowired
	@Qualifier("favoriteRoomServiceImpl")
	FavoriteRoomService favoriteRoomService;
	
	@Autowired
	@Qualifier("roomBookingServiceImpl")
	RoomBookingService roomBookingService;
	
	/**
	 * Book room.
	 * 
	 * @param fromIndex -1 to retrieve all rooms. Otherwise, retrieve 20 rooms from fromIndex.
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public String bookRoom(String roomBuilding, String roomNumber, String applicantType, String applicantId,
			String timeIntervals, String bookReason, String facultyId) {
		// Check parameters.
		if (roomBuilding == null) {
			return JsonUtil.parameterMissingResponse("roomBuilding");
		}
		if (roomNumber == null) {
			return JsonUtil.parameterMissingResponse("roomNumber");
		}
		if (applicantType == null) {
			return JsonUtil.parameterMissingResponse("applicantType");
		}
		if (applicantId == null) {
			return JsonUtil.parameterMissingResponse("applicantId");
		}
		if (timeIntervals == null) {
			return JsonUtil.parameterMissingResponse("timeIntervals");
		}
		
		if (applicantType.equals("student")) {
			if (facultyId == null) {
				return JsonUtil.parameterMissingResponse("facultyId");
			} else {
				// Check is supervisor.
				if (!supervisorService.isClassSupervisor(applicantId, facultyId) && 
						!supervisorService.isSupervisor(applicantId, facultyId)) {
					return JsonUtil.simpleMessageResponse("Faculty is not your supervisor.");
				}
			}
		}
		
		// Get time intervals.
		ArrayList<ArrayList<Date>> timeIntervalList = new ArrayList<>();
		JSONArray times = JSONArray.parseArray(timeIntervals);
		for (Object object : times) {
			JSONArray timeIntervalObject = (JSONArray)object;
			ArrayList<Date> timeInterval = new ArrayList<>();
			timeInterval.add(timeIntervalObject.getDate(0));
			timeInterval.add(timeIntervalObject.getDate(1));
			timeIntervalList.add(timeInterval);
		}
		// Convert to timestamps.
		ArrayList<ArrayList<Long>> timestampIntervalList = new ArrayList<>();
		for (ArrayList<Date> timeInterval : timeIntervalList) {
			ArrayList<Long> timestampInterval = new ArrayList<>();
			timestampInterval.add(timeInterval.get(0).getTime());
			timestampInterval.add(timeInterval.get(1).getTime());
			timestampIntervalList.add(timestampInterval);
		}

		// Check time overlaps.
		List<RoomBooking> roomBookingList = roomBookingService.getRoomBookingListForRoom(roomBuilding, roomNumber);
		for (RoomBooking roomBooking : roomBookingList) {
			roomBooking.setFromTimestamp(roomBooking.getFromtime().getTime());
			roomBooking.setToTimestamp(roomBooking.getTotime().getTime());
		}
		boolean timeIntervalOverlapped = false;
		ArrayList<ArrayList<Date>> overlappedTimeIntervalList = new ArrayList<>();
		int timeIntervalCount = timestampIntervalList.size();
		for (int i = 0; i < timeIntervalCount; i++) {
			ArrayList<Long> timestampInterval = timestampIntervalList.get(i);
			for (RoomBooking roomBooking : roomBookingList) {
				long fromTimestamp = timestampInterval.get(0);
				if (fromTimestamp >= roomBooking.getToTimestamp()) {
					continue;
				}
				
				long toTimestamp = timestampInterval.get(1);
				if (toTimestamp <= roomBooking.getFromTimestamp()) {
					continue;
				}
				
				timeIntervalOverlapped = true;
				overlappedTimeIntervalList.add(timeIntervalList.get(i));
			}
		}
		
		// Time interval overlapped.
		if (timeIntervalOverlapped) {
			Map<String, Object> result = new HashMap<>();
			result.put("message", "Time interval overlaps.");
			result.put("overlappedIntervals", overlappedTimeIntervalList);
			return JsonUtil.objectToJsonString(result);
		}
		
		// Book room.
		roomBookingService.bookRoom(roomBuilding, roomNumber, applicantType, applicantId, timeIntervalList, bookReason, facultyId);
		
		return JsonUtil.simpleMessageResponse("Successfully booked.");
	}
	
}
