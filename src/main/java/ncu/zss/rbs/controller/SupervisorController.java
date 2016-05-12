package ncu.zss.rbs.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ncu.zss.rbs.model.Faculty;
import ncu.zss.rbs.service.SupervisorService;
import ncu.zss.rbs.service.UserService;
import ncu.zss.rbs.util.JsonUtil;

/**
 * APIs related to user.
 *
 */
@Controller
@RequestMapping(value = "/supervisor", produces = "text/plain;charset=UTF-8")
@Scope("prototype")
public class SupervisorController {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@Autowired
	@Qualifier("supervisorServiceImpl")
	SupervisorService supervisorService;
	
	/**
	 * Get supervisor list.
	 * 
	 * @param id Student id.
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String getSupervisorList(String studentId, @RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) {
		if (studentId == null) {
			return JsonUtil.parameterMissingResponse("studentId");
		}
		
		List<Faculty> supervisorList = new ArrayList<>();
		
		// Get class supervisor.
		Faculty classSupervisor = supervisorService.getClassSupervisor(studentId);
		supervisorList.add(classSupervisor);
		
		// Get supervisor list.
		supervisorList.addAll(supervisorService.getSupervisorList(studentId, fromIndex));
		
		return JsonUtil.objectToJsonString(supervisorList);
	}
	
	/**
	 * Add supervisor.
	 * 
	 * @param id Student id.
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addSupervisor(String studentId, String facultyId) {
		if (studentId == null) {
			return JsonUtil.parameterMissingResponse("studentId");
		}
		
		if (facultyId == null) {
			return JsonUtil.parameterMissingResponse("facultyId");
		}
		
		// Check is supervisor.
		if (supervisorService.isClassSupervisor(studentId, facultyId)) {
			return JsonUtil.simpleMessageResponse("This faculty is already your supervisor.");
		}
		if (supervisorService.isSupervisor(studentId, facultyId)) {
			return JsonUtil.simpleMessageResponse("This faculty is already your supervisor.");
		}
		
		// Add supervisor.
		supervisorService.addSupervisor(studentId, facultyId);
		
		return JsonUtil.simpleMessageResponse("Successfully added supervisor.");
	}
	
	/**
	 * Delete supervisor.
	 * 
	 * @param studentId
	 * @param facultyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteSupervisor(String studentId, String facultyId) {
		if (studentId == null) {
			return JsonUtil.parameterMissingResponse("studentId");
		}
		
		if (facultyId == null) {
			return JsonUtil.parameterMissingResponse("facultyId");
		}
		
		// Check is class supervisor.
		if (supervisorService.isClassSupervisor(studentId, facultyId)) {
			return JsonUtil.simpleMessageResponse("Class supervisor cannot be deleted.");
		}
		
		// Check is supervisor.
		if (!supervisorService.isSupervisor(studentId, facultyId)) {
			return JsonUtil.simpleMessageResponse("This faculty is not your supervisor.");
		}
		
		// Delete supervisor.
		supervisorService.deleteSupervisor(studentId, facultyId);
		
		return JsonUtil.simpleMessageResponse("Successfully deleted supervisor.");
	}
	
	/**
	 * Check whether the supervisor is the student's supervisor.
	 * 
	 * @param studentId
	 * @param facultyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public String checkSupervisor(String studentId, String facultyId) {
		if (studentId == null) {
			return JsonUtil.parameterMissingResponse("studentId");
		}
		
		if (facultyId == null) {
			return JsonUtil.parameterMissingResponse("facultyId");
		}
		
		// Check is class supervisor.
		if (supervisorService.isClassSupervisor(studentId, facultyId)) {
			return JsonUtil.simpleMessageResponse("Yes.");
		}
		
		// Check is supervisor.
		if (supervisorService.isSupervisor(studentId, facultyId)) {
			return JsonUtil.simpleMessageResponse("Yes.");
		}
		
		return JsonUtil.simpleMessageResponse("No.");
	}
	
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchSupervisor(String condition, @RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) {
		if (condition == null) {
			return JsonUtil.parameterMissingResponse("condition");
		}
		
		List<Faculty> supervisorList = supervisorService.searchSupervisor(condition, fromIndex);
		if (supervisorList == null || supervisorList.isEmpty()) {
			return JsonUtil.emptyArrayResponse();
		}
		
		return JsonUtil.objectToJsonString(supervisorList);
	}
	
}
