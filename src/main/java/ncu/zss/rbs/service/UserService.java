package ncu.zss.rbs.service;

import org.springframework.stereotype.Service;

import ncu.zss.rbs.model.Faculty;
import ncu.zss.rbs.model.Student;

@Service
public interface UserService {
	
	/**
	 * Check if the user performing the action is the user logged in.
	 * 
	 * @param id
	 * @param idDigest
	 * @return
	 */
	boolean isSameUserLoggedIn(String id, String idDigest);
	
	/**
	 * Get default admin id.
	 * 
	 * @return
	 */
	String getDefaultAdminId();
	
	/**
	 * Get admin by id.
	 * 
	 * @param
	 * @return
	 */
	Faculty getAdminById(String id);
	
	/**
	 * Get faculty by id.
	 * 
	 * @param
	 * @return
	 */
	Faculty getFacultyById(String id);
	
	/**
	 * Get student by id.
	 * 
	 * @param
	 * @return
	 */
	Student getStudentById(String id);
	
	/**
	 * Get admin by id digest.
	 * 
	 * @param idDigest
	 * @return
	 */
	Faculty getAdminByIdDigest(String idDigest);
	
	/**
	 * Check if the user id admin.
	 * 
	 * @param
	 * @return
	 */
	boolean isAdmin(String idDigest);
	
	/**
	 * Update faculty info.
	 * 
	 * @param faculty
	 */
	void updateFaculty(Faculty faculty);
	
	/**
	 * Update student info.
	 * 
	 * @param student
	 */
	void updateStudent(Student student);
	
}
