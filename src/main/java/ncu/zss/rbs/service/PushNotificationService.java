package ncu.zss.rbs.service;

import org.springframework.stereotype.Service;

@Service
public interface PushNotificationService {
	
	/**
	 * Get apn token of a user.
	 * 
	 * @param userType
	 * @param userId
	 * @return
	 */
	String getAPNToken(String userType, String userId);
	
	/**
	 * Update apn token.
	 * 
	 * @param userType
	 * @param userId
	 * @param apnToken
	 */
    void updateAPNToken(String userType, String userId, String apnToken);
    
    /**
	 * Delete apn token.
	 * 
	 * @param userType
	 * @param userId
	 * @param apnToken
	 */
    void deleteAPNToken(String userType, String userId);
    
    /**
     * Send push notification.
     * @param apnToken
     * @param message
     * @param type
     * @param groupId
     * @param status
     */
    void sendPushNotification(String apnToken, String message, String type, String groupId, String status) throws Exception;
	
}
