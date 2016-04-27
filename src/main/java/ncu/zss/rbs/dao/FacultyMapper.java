package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.Faculty;

public interface FacultyMapper {
    
	/**
	 * Select faculty by id.
	 * @param id Faculty id.
	 * @return Faculty.
	 */
	Faculty selectById(String id);
	
}