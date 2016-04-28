package ncu.zss.rbs.controller;

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
					return JsonUtil.simpleMessageResponse("Admin not found.");
				}
				if (!admin.getPassword().equals(password)) {
					return JsonUtil.simpleMessageResponse("Password incorrect.");
				}
				RedisManager.storeValueInRedis(1, admin.getIdDigest(), admin.getId(), 7 * 24 * 3600);
				return JsonUtil.objectToJsonString(admin);
			}
			
			case "faculty": {
				Faculty faculty = userService.getFacultyById(id);
				if (faculty == null) {
					return JsonUtil.simpleMessageResponse("Faculty not found.");
				}
				if (!faculty.getPassword().equals(password)) {
					return JsonUtil.simpleMessageResponse("Password incorrect.");
				}
				RedisManager.storeValueInRedis(1, faculty.getIdDigest(), faculty.getId(), 7 * 24 * 3600);
				return JsonUtil.objectToJsonString(faculty);
			}
			
			case "student": {
				Student student = userService.getStudentById(id);
				if (student == null) {
					return JsonUtil.simpleMessageResponse("Student not found.");
				}
				if (!student.getPassword().equals(password)) {
					return JsonUtil.simpleMessageResponse("Password incorrect.");
				}
				RedisManager.storeValueInRedis(1, student.getIdDigest(), student.getId(), 7 * 24 * 3600);
				return JsonUtil.objectToJsonString(student);
			}
	
			default: {
				return JsonUtil.simpleMessageResponse("Unknown type.");
			}
		}
	}
}
