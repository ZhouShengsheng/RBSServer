package ncu.zss.rbs.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ncu.zss.rbs.model.Faculty;
import ncu.zss.rbs.service.UserService;
import ncu.zss.rbs.util.JsonUtil;

@Controller
@RequestMapping(value = "/user", produces = "text/plain;charset=UTF-8")
@Scope("prototype")
public class UserController {

	Logger logger = Logger.getLogger(TestController.class);
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String type, String id, String password) {
		
		if (type == null) {
			Map<String, Object> result = new HashMap<>();
			result.put("message", "Parameter type is required.");
			return JsonUtil.objectToJsonString(result);
		}
		
		if (id == null) {
			Map<String, Object> result = new HashMap<>();
			result.put("message", "Parameter id is required.");
			return JsonUtil.objectToJsonString(result);
		}
		
		if (password == null) {
			Map<String, Object> result = new HashMap<>();
			result.put("message", "Parameter password is required.");
			return JsonUtil.objectToJsonString(result);
		}
		
		switch (type) {
			case "admin": {
				Faculty admin = userService.getAdminById(id);
				if (admin == null) {
					Map<String, Object> result = new HashMap<>();
					result.put("message", "Admin not found.");
					return JsonUtil.objectToJsonString(result);
				}
				if (!admin.getPassword().equals(password)) {
					Map<String, Object> result = new HashMap<>();
					result.put("message", "Password incorrect.");
					return JsonUtil.objectToJsonString(result);
				}
				return JsonUtil.objectToJsonString(admin);
			}
	
			default: {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "Unknown type.");
				return JsonUtil.objectToJsonString(result);
			}
		}
	}
}
