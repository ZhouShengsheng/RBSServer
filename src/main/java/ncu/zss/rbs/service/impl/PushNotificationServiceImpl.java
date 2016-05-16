package ncu.zss.rbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import ncu.zss.rbs.dao.PushNotificationMapper;
import ncu.zss.rbs.mq.MQService;
import ncu.zss.rbs.service.PushNotificationService;

@Service("pushNotificationServiceImpl")
public class PushNotificationServiceImpl implements PushNotificationService {

	@Autowired
	PushNotificationMapper pushNotificationMapper;
	
	@Autowired
	@Qualifier("rabbitMQService")
	MQService mqService;
	
	@Override
	public String getAPNToken(String userType, String userId) {
		return pushNotificationMapper.selectAPNToken(userType, userId);
	}

	@Override
	public void updateAPNToken(String userType, String userId, String apnToken) {
		pushNotificationMapper.insertOrUpdate(userType, userId, apnToken);
	}

	@Override
	public void deleteAPNToken(String userType, String userId) {
		pushNotificationMapper.delete(userType, userId);
	}

	@Override
	public void sendPushNotification(String apnToken, String message, String type, String groupId, String status) throws Exception {
		JSONObject pushData = new JSONObject();
		JSONObject aps = new JSONObject();
		aps.put("alert", message);
		aps.put("sound", "default");
		pushData.put("aps", aps);
		pushData.put("type", type);
		pushData.put("groupId", groupId);
		pushData.put("status", status);
		
		JSONObject mqData = new JSONObject();
		mqData.put("apnToken", apnToken);
		mqData.put("pushData", pushData);
		
		mqService.send("push_notification_exchange", "push.room_booking", mqData.toJSONString());
	}

}
