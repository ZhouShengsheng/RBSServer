package ncu.zss.rbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ncu.zss.rbs.model.Faculty;

@Service
public interface SupervisorService {
	
	/**
	 * Get supervisor of the sutdent's class.
	 * 
	 * @param studentId
	 * @return
	 */
	Faculty getClassSupervisor(String studentId);

	/**
	 * Get supervisor list of a student specified by id.
	 * @param studentId
	 * @param fromIndex
	 * @return
	 */
	List<Faculty> getSupervisorList(String studentId, Integer fromIndex);
	
	/**
	 * Is the faculty the class supervisor of the student.
	 * 
	 * @param studentId
	 * @param facultyId
	 * @return
	 */
	boolean isClassSupervisor(String studentId, String facultyId);
	
	/**
	 * Is the faculty the supervisor of the student.
	 * 
	 * @param studentId
	 * @param facultyId
	 * @return
	 */
	boolean isSupervisor(String studentId, String facultyId);
	
	/**
	 * Add a supervisor to student.
	 * 
	 * @param studentId
	 * @param facultyId
	 */
	void addSupervisor(String studentId, String facultyId);
	
	/**
	 * Delete a supervisor of the student.
	 * 
	 * @param studentId
	 * @param facultyId
	 */
	void deleteSupervisor(String studentId, String facultyId);
	
}
