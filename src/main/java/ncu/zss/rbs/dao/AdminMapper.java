package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.Faculty;

public interface AdminMapper {

	/**
	 * Select admin by id.
	 * @param
	 * @return
	 */
	Faculty selectById(String id);
	
	/**
	 * Select admin by id digest.
	 * @param
	 * @return
	 */
	Faculty selectByIdDigest(String idDigest);
	
	/**
	 * Select default admin id.
	 * 
	 * @return
	 */
	String selectDefaultAdminId();
	
}