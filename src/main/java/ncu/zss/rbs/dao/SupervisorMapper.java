package ncu.zss.rbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ncu.zss.rbs.model.Faculty;
import ncu.zss.rbs.model.Supervisor;

public interface SupervisorMapper {
	
	/**
	 * Select supervisor of the sutdent's class.
	 * 
	 * @param studentId
	 * @return
	 */
	Faculty selectClassSupervisor(@Param("studentId") String studentId);
    
	/**
	 * Select supervisor list of a student specified by id.
	 * 
	 * @param studentId
	 */
	List<Faculty> selectSupervisorList(@Param("studentId") String studentId, @Param("fromIndex") Integer fromIndex);
	
	/**
	 * Select supervisor of the student.
	 * 
	 * @param studentId
	 * @param facultyId
	 * @return
	 */
	Supervisor selectSupervisor(@Param("studentId") String studentId, @Param("facultyId") String facultyId);
	
	/**
	 * Insert a supervisor to student.
	 * 
	 * @param studentId
	 * @param facultyId
	 */
	void insertSupervisor(@Param("studentId") String studentId, @Param("facultyId") String facultyId);
	
	/**
	 * Delete a supervisor of the student.
	 * 
	 * @param studentId
	 * @param facultyId
	 */
	void deleteSupervisor(@Param("studentId") String studentId, @Param("facultyId") String facultyId);
	
	
	/**
	 * Search supervisor list.
	 * 
	 * @param condition
	 * @return
	 */
	List<Faculty> selectSupervisorListWithCondition(@Param("condition") String condition, @Param("fromIndex") Integer fromIndex);
}