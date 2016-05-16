package ncu.zss.rbs.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ncu.zss.rbs.service.PushNotificationService;
import ncu.zss.rbs.service.UserService;
import ncu.zss.rbs.util.JsonUtil;

/**
 * APIs related to push notification.
 *
 */
@Controller
@RequestMapping(value = "/push_notification", produces = "text/plain;charset=UTF-8")
@Scope("prototype")
public class PushNotificationController {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@Autowired
	@Qualifier("pushNotificationServiceImpl")
	PushNotificationService pushNotificationService;
	
	/**
	 * Update device token.
	 * 
	 * @param idDigest
	 * @param type
	 * @param id
	 * @param deviceToken
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update_apn_token", method = RequestMethod.POST)
	public String updateUserInfo(String idDigest, String userType, String userId, String apnToken) {
		// Check parameters.
		if (userType == null) {
			return JsonUtil.parameterMissingResponse("userType");
		}
		if (userId == null) {
			return JsonUtil.parameterMissingResponse("userId");
		}
		if (apnToken == null) {
			return JsonUtil.parameterMissingResponse("apnToken");
		}

		// Check if the user performing the action is the user logged in.
		if (!userService.isSameUserLoggedIn(userId, idDigest)) {
			return JsonUtil.simpleMessageResponse("You do not have the privileges.");
		}
		
		// Update apn token.
		pushNotificationService.updateAPNToken(userType, userId, apnToken);
		
		return JsonUtil.simpleMessageResponse("APN token updated.");
	}
	
}
