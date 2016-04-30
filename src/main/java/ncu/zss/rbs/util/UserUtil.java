package ncu.zss.rbs.util;

import ncu.zss.rbs.db.manager.RedisManager;

public final class UserUtil {

	/**
	 * Get user id by id digest.
	 * 
	 * @param idDigest
	 * @return
	 */
	public static String getUserIdByIdDigest(String idDigest) {
		return RedisManager.getStringValueRedis(RedisManager.DB_USER, idDigest);
	}
	
}
