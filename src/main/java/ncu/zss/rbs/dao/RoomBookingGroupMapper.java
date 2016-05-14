package ncu.zss.rbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ncu.zss.rbs.model.RoomBookingGroup;

public interface RoomBookingGroupMapper {
    
	/**
	 * Select room booking processing list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @return
	 */
	List<RoomBookingGroup> selectProcessingList(@Param("applicantType") String applicantType,
			@Param("applicantId") String applicantId,
			@Param("fromIndex") Integer fromIndex);
	
	/**
	 * Select room booking approved list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @return
	 */
	List<RoomBookingGroup> selectApprovedList(@Param("applicantType") String applicantType,
			@Param("applicantId") String applicantId,
			@Param("fromIndex") Integer fromIndex);
	
	/**
	 * Select room booking declined list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @return
	 */
	List<RoomBookingGroup> selectDeclinedList(@Param("applicantType") String applicantType,
			@Param("applicantId") String applicantId,
			@Param("fromIndex") Integer fromIndex);
	
	/**
	 * Select room booking history list.
	 * 
	 * @param applicantType
	 * @param applicantId
	 * @return
	 */
	List<RoomBookingGroup> selectHistoryList(@Param("applicantType") String applicantType,
			@Param("applicantId") String applicantId,
			@Param("fromIndex") Integer fromIndex);
	
	/**
	 * Select admin processing list.
	 * 
	 * @param fromIndex
	 * @return
	 */
	List<RoomBookingGroup> selectAdminProcessingList(Integer fromIndex);
	
	/**
	 * Select admin processed list.
	 * 
	 * @param fromIndex
	 * @return
	 */
	List<RoomBookingGroup> selectAdminProcessedList(Integer fromIndex);
	
}