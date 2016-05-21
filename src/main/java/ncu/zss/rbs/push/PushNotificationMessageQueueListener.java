package ncu.zss.rbs.push;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.fastjson.JSONObject;

import ncu.zss.rbs.mq.MQService;
import ncu.zss.rbs.mq.rabbitmq.RabbitMQMessageListener;
import ncu.zss.rbs.service.APNSService;
import ncu.zss.rbs.service.PushNotificationService;

public class PushNotificationMessageQueueListener implements InitializingBean, DisposableBean {

	Logger logger = Logger.getLogger(getClass());

	@Autowired
	@Qualifier("rabbitMQService")
	MQService mqService;

	@Autowired
	@Qualifier("apnsServiceImpl")
	APNSService apnsService;
	
	@Autowired
	@Qualifier("pushNotificationServiceImpl")
	PushNotificationService pushNotificationService;

	/**
	 * Listener.
	 */
	Object mqListener;

	@Override
	public void afterPropertiesSet() throws Exception {
		// Bind message listener.
		try {
			mqListener = mqService.bindListener(new RabbitMQMessageListener() {
				@Override
				public void onReceivedMessage(Object message) {
					String pushDataStr = new String((byte[]) message);

					JSONObject mqData = JSONObject.parseObject(pushDataStr);
					if (mqData == null || !mqData.containsKey("pushData") || !mqData.containsKey("userType") || !mqData.containsKey("userId")) {
						try {
							// Dirty data, reject.
							logger.warn("Dirty data, rejected: " + pushDataStr);
							reject(false);
							return;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					// Send push notification.
					String userType = mqData.getString("userType");
					String userId = mqData.getString("userId");
					String apnToken = pushNotificationService.getAPNToken(userType, userId);
					JSONObject pushData = mqData.getJSONObject("pushData");
					apnsService.push(apnToken, pushData.toJSONString());
					
					// Log.
					logger.info("New push notification.");
					logger.info("userType: " + userType);
					logger.info("userId: " + userId);
					logger.info("apnToken: " + apnToken);
					logger.info("pushData: " + pushData.toJSONString());

					try {
						// Ack.
						ack();
					} catch (IOException e) {
						logger.error("Failed to ack.");
						logger.error(e.getStackTrace());

						try {
							reject(true);
						} catch (IOException e1) {
							logger.error(e1.getStackTrace());
						}
					}
				}

			}, "push_notification_queue");
			logger.info("Binded message listener.");
		} catch (Exception e) {
			logger.error("Failed to bind listener.");
			logger.error(e.getStackTrace());
		} finally {
		}
	}

	@Override
	public void destroy() throws Exception {
		// Unlisten.
		mqService.unbindListener(mqListener);
	}

}
