package ncu.zss.rbs.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ncu.zss.rbs.db.manager.RedisManager;
import ncu.zss.rbs.model.Faculty;
import ncu.zss.rbs.model.Student;
import ncu.zss.rbs.service.PushNotificationService;
import ncu.zss.rbs.service.SupervisorService;
import ncu.zss.rbs.service.UserService;
import ncu.zss.rbs.util.JsonUtil;

/**
 * APIs related to user.
 *
 */
@Controller
@RequestMapping(value = "/user", produces = "text/plain;charset=UTF-8")
@Scope("prototype")
public class UserController {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@Autowired
	@Qualifier("supervisorServiceImpl")
	SupervisorService supervisorService;
	
	@Autowired
	@Qualifier("pushNotificationServiceImpl")
	PushNotificationService pushNotificationService;
	
	/**
	 * User login.
	 * 
	 * @param type One of admin, faculty and student.
	 * @param id User id.
	 * @param password User password.
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String type, String id, String password) {
		if (type == null) {
			return JsonUtil.parameterMissingResponse("type");
		}
		
		if (id == null) {
			return JsonUtil.parameterMissingResponse("id");
		}
		
		if (password == null) {
			return JsonUtil.parameterMissingResponse("password");
		}
		
		switch (type) {
			case "admin": {
				Faculty admin = userService.getAdminById(id);
				if (admin == null) {
					return JsonUtil.simpleMessageResponse("User not found.");
				}
				if (!admin.getPassword().equals(password)) {
					return JsonUtil.simpleMessageResponse("Password incorrect.");
				}
				RedisManager.storeValueInRedis(RedisManager.DB_USER, admin.getIdDigest(), admin.getId(), RedisManager.EXPIRE_TIME);
				RedisManager.storeValueInRedis(RedisManager.DB_ADMIN, admin.getIdDigest(), admin.getId(), RedisManager.EXPIRE_TIME);
				admin.setPassword(null);
				return JsonUtil.objectToJsonString(admin);
			}
			
			case "faculty": {
				Faculty faculty = userService.getFacultyById(id);
				if (faculty == null) {
					return JsonUtil.simpleMessageResponse("User not found.");
				}
				if (!faculty.getPassword().equals(password)) {
					return JsonUtil.simpleMessageResponse("Password incorrect.");
				}
				RedisManager.storeValueInRedis(RedisManager.DB_USER, faculty.getIdDigest(), faculty.getId(), RedisManager.EXPIRE_TIME);
				faculty.setPassword(null);
				return JsonUtil.objectToJsonString(faculty);
			}
			
			case "student": {
				Student student = userService.getStudentById(id);
				if (student == null) {
					return JsonUtil.simpleMessageResponse("User not found.");
				}
				if (!student.getPassword().equals(password)) {
					return JsonUtil.simpleMessageResponse("Password incorrect.");
				}
				RedisManager.storeValueInRedis(RedisManager.DB_USER, student.getIdDigest(), student.getId(), RedisManager.EXPIRE_TIME);
				student.setPassword(null);
				
				// Get student supervisor.
				Faculty supervisor = supervisorService.getClassSupervisor(id);
				supervisor.setPassword(null);
				
				Map<String, Object> resultMap = JsonUtil.objectToMap(student);
				resultMap.put("supervisor", JsonUtil.objectToMap(supervisor));
				
				return JsonUtil.objectToJsonString(resultMap);
			}
	
			default: {
				return JsonUtil.simpleMessageResponse("Unknown type.");
			}
		}
	}
	
	/**
	 * User logout.
	 * 
	 * @param type One of admin, faculty and student.
	 * @param id User id.
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(String type, String id) {
		if (type == null) {
			return JsonUtil.parameterMissingResponse("type");
		}
		if (id == null) {
			return JsonUtil.parameterMissingResponse("id");
		}
		
		// Delete apn token to prevent from sending notifications.
		pushNotificationService.deleteAPNToken(type, id);
		
		return JsonUtil.simpleMessageResponse("Logged out.");
	}
	
	/**
	 * Get user info.
	 * 
	 * @param type One of admin, faculty and student.
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public String getUserInfo(String type, String id) {
		if (type == null) {
			return JsonUtil.parameterMissingResponse("type");
		}
		
		if (id == null) {
			return JsonUtil.parameterMissingResponse("id");
		}
		
		switch (type) {
			case "admin": {
				Faculty admin = userService.getAdminById(id);
				if (admin == null) {
					return JsonUtil.simpleMessageResponse("User not found.");
				}
				admin.setPassword(null);
				
				return JsonUtil.objectToJsonString(admin);
			}
			
			case "faculty": {
				Faculty faculty = userService.getFacultyById(id);
				if (faculty == null) {
					return JsonUtil.simpleMessageResponse("User not found.");
				}
				faculty.setPassword(null);

				return JsonUtil.objectToJsonString(faculty);
			}
			
			case "student": {
				Student student = userService.getStudentById(id);
				if (student == null) {
					return JsonUtil.simpleMessageResponse("User not found.");
				}
				student.setPassword(null);
				
				return JsonUtil.objectToJsonString(student);
			}
	
			default: {
				return JsonUtil.simpleMessageResponse("Unknown type.");
			}
		}
	}
	
	/**
	 * Update user info.
	 * 
	 * @param type One of admin, faculty and student.
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUserInfo(String idDigest, String type, String id,
			String designation, String office, String phone, String dormRoomNumber) {
		// Check parameters.
		if (type == null) {
			return JsonUtil.parameterMissingResponse("type");
		}
		if (id == null) {
			return JsonUtil.parameterMissingResponse("id");
		}

		// Check if the user performing the action is the user logged in.
		if (!userService.isSameUserLoggedIn(id, idDigest)) {
			return JsonUtil.simpleMessageResponse("You do not have the privileges.");
		}
		
		switch (type) {
			case "admin":
			case "faculty": {
				Faculty faculty = userService.getFacultyById(id);
				if (faculty == null) {
					return JsonUtil.simpleMessageResponse("User not found.");
				}
				
				// Update faculty info.
				faculty.setDesignation(designation);
				faculty.setOffice(office);
				faculty.setPhone(phone);
				userService.updateFaculty(faculty);

				return JsonUtil.simpleMessageResponse("User updated.");
			}
			
			case "student": {
				Student student = userService.getStudentById(id);
				if (student == null) {
					return JsonUtil.simpleMessageResponse("User not found.");
				}
				
				// Update student info.
				student.setDormroomnumber(dormRoomNumber);
				student.setPhone(phone);
				userService.updateStudent(student);

				return JsonUtil.simpleMessageResponse("User updated.");
			}
			
			default: {
				return JsonUtil.simpleMessageResponse("Unknown type.");
			}
		}
	}
	
	/**
	 * Change password.
	 * 
	 * @param type One of admin, faculty and student.
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/change_password", method = RequestMethod.POST)
	public String changePassword(String idDigest, String type, String id, String password, String newPassword) {
		// Check parameters.
		if (type == null) {
			return JsonUtil.parameterMissingResponse("type");
		}
		if (id == null) {
			return JsonUtil.parameterMissingResponse("id");
		}
		if (password == null) {
			return JsonUtil.parameterMissingResponse("password");
		}
		if (newPassword == null) {
			return JsonUtil.parameterMissingResponse("newPassword");
		}

		// Check if the user performing the action is the user logged in.
		if (!userService.isSameUserLoggedIn(id, idDigest)) {
			return JsonUtil.simpleMessageResponse("You do not have the privileges.");
		}
		
		switch (type) {
			case "admin":
			case "faculty": {
				Faculty faculty = userService.getFacultyById(id);
				if (faculty == null) {
					return JsonUtil.simpleMessageResponse("User not found.");
				}
				
				if (!faculty.getPassword().equals(password)) {
					return JsonUtil.simpleMessageResponse("Password incorrect.");
				}
				
				// Change password.
				faculty.setPassword(newPassword);
				userService.updateFaculty(faculty);
				
				return JsonUtil.simpleMessageResponse("Password changed.");
			}
			
			case "student": {
				Student student = userService.getStudentById(id);
				if (student == null) {
					return JsonUtil.simpleMessageResponse("User not found.");
				}
				
				if (!student.getPassword().equals(password)) {
					return JsonUtil.simpleMessageResponse("Password incorrect.");
				}
				
				// Change password.
				student.setPassword(newPassword);
				userService.updateStudent(student);

				return JsonUtil.simpleMessageResponse("Password changed.");
			}
			
			default: {
				return JsonUtil.simpleMessageResponse("Unknown type.");
			}
		}
	}
}
