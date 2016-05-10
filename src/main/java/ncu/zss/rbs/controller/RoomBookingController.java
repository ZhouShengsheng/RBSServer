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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ncu.zss.rbs.model.RoomBooking;
import ncu.zss.rbs.model.RoomBookingGroup;
import ncu.zss.rbs.model.RoomBookingInfo;
import ncu.zss.rbs.service.FavoriteRoomService;
import ncu.zss.rbs.service.RoomBookingService;
import ncu.zss.rbs.service.RoomService;
import ncu.zss.rbs.service.SupervisorService;
import ncu.zss.rbs.service.UserService;
import ncu.zss.rbs.util.JsonUtil;
import ncu.zss.rbs.util.TimeIntervalUtil;

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
	 * @param roomBuilding
	 * @param roomNumber
	 * @param applicantType
	 * @param applicantId
	 * @param timeIntervals
	 * @param bookReason
	 * @param facultyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public String bookRoom(String roomBuilding, String roomNumber, String applicantType, String applicantId,
			String timeIntervals, String bookReason, String facultyId) {
		logger.info("timeIntervals: " + timeIntervals);
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
		ArrayList<ArrayList<Date>> timeIntervalList = TimeIntervalUtil.getTimeIntervalListFromJsonString(timeIntervals);

		// Check time overlaps.
		List<RoomBooking> roomBookingList = roomBookingService.getRoomBookingListForRoom(roomBuilding, roomNumber);
		ArrayList<ArrayList<Date>> overlappedTimeIntervalList = new ArrayList<>();
		boolean timeIntervalOverlapped = TimeIntervalUtil.checkTimeIntervalOverlaps(roomBookingList, timeIntervalList, true, overlappedTimeIntervalList);
		
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
	
	/**
	 * Get room booking processing list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @param fromIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/processing_list", method = RequestMethod.POST)
	public String getProcessingList(String applicantType, String applicantId, @RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) {
		// Check parameters.
		if (applicantType == null) {
			return JsonUtil.parameterMissingResponse("applicantType");
		}
		if (applicantId == null) {
			return JsonUtil.parameterMissingResponse("applicantId");
		}
		
		List<RoomBookingGroup> roomBookingGroupList = roomBookingService.getProcessingList(applicantType, applicantId, fromIndex);
		if (roomBookingGroupList == null) {
			return JsonUtil.emptyArrayResponse();
		}
		
		return JsonUtil.objectToJsonString(roomBookingGroupList);
	}
	
	/**
	 * Get room booking approved list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @param fromIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/approved_list", method = RequestMethod.POST)
	public String getApprovedList(String applicantType, String applicantId, @RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) {
		// Check parameters.
		if (applicantType == null) {
			return JsonUtil.parameterMissingResponse("applicantType");
		}
		if (applicantId == null) {
			return JsonUtil.parameterMissingResponse("applicantId");
		}
		
		List<RoomBookingGroup> roomBookingGroupList = roomBookingService.getApprovedList(applicantType, applicantId, fromIndex);
		if (roomBookingGroupList == null) {
			return JsonUtil.emptyArrayResponse();
		}
		
		return JsonUtil.objectToJsonString(roomBookingGroupList);
	}
	
	/**
	 * Get room booking declined list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @param fromIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/declined_list", method = RequestMethod.POST)
	public String getDeclinedList(String applicantType, String applicantId, @RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) {
		// Check parameters.
		if (applicantType == null) {
			return JsonUtil.parameterMissingResponse("applicantType");
		}
		if (applicantId == null) {
			return JsonUtil.parameterMissingResponse("applicantId");
		}
		
		List<RoomBookingGroup> roomBookingGroupList = roomBookingService.getDeclinedList(applicantType, applicantId, fromIndex);
		if (roomBookingGroupList == null) {
			return JsonUtil.emptyArrayResponse();
		}
		
		return JsonUtil.objectToJsonString(roomBookingGroupList);
	}
	
	/**
	 * Get room booking detailed info.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @param fromIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public String getInfo(String groupId) {
		// Check parameters.
		if (groupId == null) {
			return JsonUtil.parameterMissingResponse("groupId");
		}
		
		RoomBookingInfo info = roomBookingService.getRoomBookingInfo(groupId);
		if (info == null) {
			return JsonUtil.simpleMessageResponse("Group not found.");
		}
		
		return JsonUtil.objectToJsonString(info);
	}
	
	/**
	 * Cancel booking.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @param fromIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancelBooking(String groupId) {
		// Check parameters.
		if (groupId == null) {
			return JsonUtil.parameterMissingResponse("groupId");
		}
		
		RoomBookingInfo info = roomBookingService.getRoomBookingInfo(groupId);
		if (info == null) {
			return JsonUtil.simpleMessageResponse("Group not found.");
		}
		
		roomBookingService.cancelBooking(groupId);
		
		return JsonUtil.simpleMessageResponse("Successfully canceled booking.");
	}
	
	/**
	 * Get room booking history list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @param fromIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/history_list", method = RequestMethod.POST)
	public String getHistoryList(String applicantType, String applicantId, @RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) {
		// Check parameters.
		if (applicantType == null) {
			return JsonUtil.parameterMissingResponse("applicantType");
		}
		if (applicantId == null) {
			return JsonUtil.parameterMissingResponse("applicantId");
		}
		
		List<RoomBookingGroup> roomBookingGroupList = roomBookingService.getHistoryList(applicantType, applicantId, fromIndex);
		if (roomBookingGroupList == null) {
			return JsonUtil.emptyArrayResponse();
		}
		
		return JsonUtil.objectToJsonString(roomBookingGroupList);
	}
	
}
