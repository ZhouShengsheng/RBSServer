package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.Faculty;

public interface AdminMapper {

	/**
	 * Select admin by id.
	 * @param id Admin id.
	 * @return Admin.
	 */
	Faculty selectById(String id);
	
}