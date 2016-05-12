package ncu.zss.rbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ncu.zss.rbs.model.StudentBookingGroup;

public interface StudentBookingGroupMapper {
    
	/**
	 * Select student booking list.
	 * 
	 * @param facultyId
	 * @param fromIndex
	 * @return
	 */
	List<StudentBookingGroup> selectWithFacultyId(@Param("facultyId") String facultyId, @Param("fromIndex") Integer fromIndex);
	
}