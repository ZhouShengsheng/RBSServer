package ncu.zss.rbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ncu.zss.rbs.model.Room;

public interface RoomMapper {
    
	/**
	 * Select room of building.
	 * 
	 * @param building
	 * @return
	 */
	List<Room> selectRoomList(@Param("building") String building);
	
	/**
	 * Select room of building from index.
	 * 
	 * @param building
	 * @param fromIndex
	 * @return
	 */
	List<Room> selectRoomListWithFromIndex(@Param("building") String building, @Param("fromIndex") Integer fromIndex);

	/**
	 * Select room list by screen.
	 * 
	 * @param building
	 * @param capacity
	 * @param hasMultiMedia
	 * @param timeIntervals
	 * @return
	 */
	List<Room> selectRoomListWithScreenCondition(@Param("building") String building, @Param("capacity") Integer capacity,
			@Param("hasMultiMedia") Integer hasMultiMedia);

	/**
	 * Select room list by search.
	 * 
	 * @param condition
	 * @return
	 */
	List<Room> selectRoomListWithSearchCondition(@Param("condition") String condition);
	
	/**
	 * Insert room.
	 * 
	 * @param building
	 * @param number
	 * @param capacity
	 * @param hasMultiMedia
	 * @return
	 */
	void insertRoom(@Param("building") String building, @Param("number") String number,
			@Param("capacity")  Integer capacity, @Param("hasMultiMedia")  Integer hasMultiMedia);
	
	/**
	 * Select room.
	 * 
	 * @param building
	 * @param number
	 * @return
	 */
	Room selectRoom(@Param("building") String building, @Param("number") String number);
	
	/**
	 * Update room.
	 * 
	 * @param building
	 * @param number
	 * @param capacity
	 * @param hasMultiMedia
	 * @return
	 */
	void updateRoom(@Param("building") String building, @Param("number") String number,
			@Param("capacity")  Integer capacity, @Param("hasMultiMedia")  Integer hasMultiMedia);
	
	/**
	 * Delete room.
	 * 
	 * @param building
	 * @param number
	 * @return
	 */
	void deleteRoom(@Param("building") String building, @Param("number") String number);
	
}