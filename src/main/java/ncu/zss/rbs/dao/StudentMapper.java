package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.Student;

public interface StudentMapper {
    
	/**
	 * Select student by id.
	 * 
	 * @param id Student id.
	 * @return Student.
	 */
	Student selectById(String id);
	
}