package ncu.zss.rbs.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ncu.zss.rbs.util.JsonUtil;

@Controller
@RequestMapping(value = "/user", produces = "text/plain;charset=UTF-8")
@Scope("prototype")
public class UserController {

	Logger logger = Logger.getLogger(TestController.class);
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String id, String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("message", "hello " + id);
		return JsonUtil.objectToJsonString(result);
	}
}
