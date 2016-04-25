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

/**
 * APIs related to test.
 *
 */
@Controller
@RequestMapping(value = "/test", produces = "text/plain;charset=UTF-8")
@Scope("prototype")
public class TestController {

	Logger logger = Logger.getLogger(TestController.class);
	
	@ResponseBody
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("message", "hello " + name);
		return JsonUtil.objectToJsonString(result);
	}
}
