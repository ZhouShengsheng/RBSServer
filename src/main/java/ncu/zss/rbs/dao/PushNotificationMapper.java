package ncu.zss.rbs.dao;

import org.apache.ibatis.annotations.Param;

public interface PushNotificationMapper {
	
	/**
	 * Select apn token by user.
	 * 
	 * @param userType
	 * @param userId
	 * @return
	 */
	String selectAPNToken(@Param("userType") String userType, @Param("userId") String userId);
	
	/**
	 * Insert or update.
	 * 
	 * @param userType
	 * @param userId
	 * @param apnToken
	 */
    void insertOrUpdate(@Param("userType") String userType, @Param("userId") String userId, @Param("apnToken") String apnToken);
    
    /**
     * Delete by user.
     * 
     * @param userType
     * @param userId
     */
    void delete(@Param("userType") String userType, @Param("userId") String userId);

}