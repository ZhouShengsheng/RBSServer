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

import ncu.zss.rbs.db.manager.RedisManager;
import ncu.zss.rbs.model.Faculty;
import ncu.zss.rbs.model.RoomBooking;
import ncu.zss.rbs.model.RoomBookingGroup;
import ncu.zss.rbs.model.RoomBookingInfo;
import ncu.zss.rbs.model.Student;
import ncu.zss.rbs.model.StudentBookingGroup;
import ncu.zss.rbs.service.FavoriteRoomService;
import ncu.zss.rbs.service.PushNotificationService;
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
	
	@Autowired
	@Qualifier("pushNotificationServiceImpl")
	PushNotificationService pushNotificationService;
	
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
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public String bookRoom(String roomBuilding, String roomNumber, String applicantType, String applicantId,
			String timeIntervals, String bookReason, String facultyId) throws Exception {
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
		
		String pushUserType;
		
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
			pushUserType = "faculty";
		} else {
			pushUserType = "admin";
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
		String groupId = roomBookingService.bookRoom(roomBuilding, roomNumber, applicantType, applicantId, timeIntervalList, bookReason, facultyId);
		
		// Send push notification.
		if (pushUserType.equals("faculty")) {
			Student student = userService.getStudentById(applicantId);
			String message = String.format("教室申请通知！教室: %s%s, 申请人: %s。", roomBuilding, roomNumber, student.toString());
			pushNotificationService.sendPushNotification(pushUserType, facultyId, message, "roomBooking", groupId, "created");
		} else {
			Faculty faculty = userService.getFacultyById(applicantId);
			String message = String.format("教室申请通知！教室: %s%s, 申请人: %s。", roomBuilding, roomNumber, faculty.toString());
			pushNotificationService.sendPushNotification(pushUserType, userService.getDefaultAdminId(), message, "roomBooking", groupId, "faculty_approved");
		}
		
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
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancelBooking(String groupId) throws Exception {
		// Check parameters.
		if (groupId == null) {
			return JsonUtil.parameterMissingResponse("groupId");
		}
		
		RoomBookingInfo roomBookingInfo = roomBookingService.getRoomBookingInfo(groupId);
		if (roomBookingInfo == null) {
			return JsonUtil.simpleMessageResponse("Group not found.");
		}
		
		// Cancel.
		roomBookingService.cancelBooking(groupId);
		
		String status = roomBookingInfo.getStatus();
		if (status.equals("created")) {
			// Send push notification to supervisor.
			String message = String.format("学生申请已取消！申请编号: %s。", groupId);
			pushNotificationService.sendPushNotification("faculty", roomBookingInfo.getFacultyid(), message, "roomBooking", groupId, "canceled");
		} else {
			// Send push notification to admin.
			String message = String.format("申请已取消！申请编号: %s。", groupId);
			pushNotificationService.sendPushNotification("admin", userService.getDefaultAdminId(), message, "roomBooking", groupId, "canceled");
		}
		
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
	
	@ResponseBody
	@RequestMapping(value = "/admin_processing_list", method = RequestMethod.POST)
	public String getAdminProcessingList(String idDigest, @RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) {
		// Check is admin.
		if (!userService.isAdmin(idDigest)) {
			return JsonUtil.simpleMessageResponse("You do not have the privilege.");
		}
		
		List<RoomBookingGroup> roomBookingGroupList = roomBookingService.getAdminProcessingList(fromIndex);
		if (roomBookingGroupList == null) {
			return JsonUtil.emptyArrayResponse();
		}
		
		return JsonUtil.objectToJsonString(roomBookingGroupList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin_processed_list", method = RequestMethod.POST)
	public String getAdminProccessedList(String idDigest, @RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) {
		// Check is admin.
		if (!userService.isAdmin(idDigest)) {
			return JsonUtil.simpleMessageResponse("You do not have the privilege.");
		}
		
		List<RoomBookingGroup> roomBookingGroupList = roomBookingService.getAdminProcessedList(fromIndex);
		if (roomBookingGroupList == null) {
			return JsonUtil.emptyArrayResponse();
		}
		
		return JsonUtil.objectToJsonString(roomBookingGroupList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/student_booking_list", method = RequestMethod.POST)
	public String getStudentBookingyList(String facultyId, @RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) {
		// Check parameters.
		if (facultyId == null) {
			return JsonUtil.parameterMissingResponse("facultyId");
		}
		
		List<StudentBookingGroup> studentBookingGroupList = roomBookingService.getStudentBookingList(facultyId, fromIndex);
		if (studentBookingGroupList == null) {
			return JsonUtil.emptyArrayResponse();
		}
		
		return JsonUtil.objectToJsonString(studentBookingGroupList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/approve", method = RequestMethod.POST)
	public String approveRoomBooking(String personType, String personId, String groupId) throws Exception {
		// Check parameters.
		if (personType == null) {
			return JsonUtil.parameterMissingResponse("personType");
		}
		if (personId == null) {
			return JsonUtil.parameterMissingResponse("personId");
		}
		
		// Check group id.
		RoomBookingInfo roomBookingInfo = roomBookingService.getRoomBookingInfo(groupId);
		if (roomBookingInfo == null) {
			return JsonUtil.simpleMessageResponse("Group not found.");
		}
		
		switch (personType) {
			case "admin": {
				if (!personId.equals(roomBookingInfo.getAdminid())) {
					return JsonUtil.simpleMessageResponse("You do not have the privilege.");
				}
				roomBookingService.adminApprove(groupId);
				// Flush room list redis db.
				RedisManager.flushDB(RedisManager.DB_ROOM_LIST);
				
				// Send push notification.
				String applicantType = roomBookingInfo.getApplicanttype();
				String applicantId = roomBookingInfo.getApplicantid();
				String message = String.format("您的教室申请已通过审核！申请编号: %s。", groupId);
				pushNotificationService.sendPushNotification(applicantType, applicantId, message, "roomBooking", groupId, "admin_approved");
				
				return JsonUtil.simpleMessageResponse("Approved.");
			}
			case "faculty": {
				if (!personId.equals(roomBookingInfo.getFacultyid())) {
					return JsonUtil.simpleMessageResponse("You do not have the privilege.");
				}
				roomBookingService.supervisorApprove(groupId);
				
				// Send push notification to applicant.
				String applicantType = roomBookingInfo.getApplicanttype();
				String applicantId = roomBookingInfo.getApplicantid();
				String message = String.format("您的教室申请已通过上级审核！申请编号: %s。", groupId);
				pushNotificationService.sendPushNotification(applicantType, applicantId, message, "roomBooking", groupId, "faculty_approved");
				
				// Send push notification to admin.
				Student student = userService.getStudentById(applicantId);
				message = String.format("教室申请通知！教室: %s%s, 申请人: %s。", 
						roomBookingInfo.getRoombuilding(), roomBookingInfo.getRoomnumber(), student.toString());
				pushNotificationService.sendPushNotification("admin", userService.getDefaultAdminId(), message, "roomBooking", groupId, "faculty_approved");
				
				return JsonUtil.simpleMessageResponse("Approved.");
			}
			default: {
				return JsonUtil.simpleMessageResponse("Person type incorrect.");
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/decline", method = RequestMethod.POST)
	public String declineRoomBooking(String personType, String personId, String groupId, String declineReason) throws Exception {
		// Check parameters.
		if (personType == null) {
			return JsonUtil.parameterMissingResponse("personType");
		}
		if (personId == null) {
			return JsonUtil.parameterMissingResponse("personId");
		}
		if (groupId == null) {
			return JsonUtil.parameterMissingResponse("groupId");
		}
		if (declineReason == null) {
			return JsonUtil.parameterMissingResponse("declineReason");
		}
		
		// Check group id.
		RoomBookingInfo roomBookingInfo = roomBookingService.getRoomBookingInfo(groupId);
		if (roomBookingInfo == null) {
			return JsonUtil.simpleMessageResponse("Group not found.");
		}
		
		switch (personType) {
			case "admin": {
				roomBookingService.adminDecline(groupId, declineReason);

				// Send push notification.
				String message = String.format("您的教室申请已被管理员拒绝！申请编号: %s。", groupId);
				pushNotificationService.sendPushNotification(roomBookingInfo.getApplicanttype(),
						roomBookingInfo.getApplicantid(), message, "roomBooking", groupId, "admin_declined");

				return JsonUtil.simpleMessageResponse("Declined.");
			}
			case "faculty": {
				roomBookingService.supervisorDecline(groupId, declineReason);

				// Send push notification.
				String message = String.format("您的教室申请已被上级拒绝！申请编号: %s。", groupId);
				pushNotificationService.sendPushNotification(roomBookingInfo.getApplicanttype(),
						roomBookingInfo.getApplicantid(), message, "roomBooking", groupId, "faculty_declined");

				return JsonUtil.simpleMessageResponse("Declined.");
			}
			default: {
				return JsonUtil.simpleMessageResponse("Person type incorrect.");
			}
		}
	}
	
}
