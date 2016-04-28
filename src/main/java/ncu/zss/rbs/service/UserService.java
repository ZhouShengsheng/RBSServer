package ncu.zss.rbs.service;

import org.springframework.stereotype.Service;

import ncu.zss.rbs.model.Faculty;
import ncu.zss.rbs.model.Student;

@Service
public interface UserService {
	
	/**
	 * Get admin by id.
	 * 
	 * @param id Admin id.
	 * @return Admin.
	 */
	Faculty getAdminById(String id);
	
	/**
	 * Get faculty by id.
	 * 
	 * @param id Faculty id.
	 * @return Faculty.
	 */
	Faculty getFacultyById(String id);
	
	/**
	 * Get student by id.
	 * 
	 * @param id Student id.
	 * @return Student.
	 */
	Student getStudentById(String id);
}
