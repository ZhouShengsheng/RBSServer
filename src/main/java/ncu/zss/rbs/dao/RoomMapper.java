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
	List<Room> selectRoomList(String building);
	
	/**
	 * Select room of building from index.
	 * 
	 * @param building
	 * @param fromIndex
	 * @return
	 */
	List<Room> selectRoomListWithFromIndex(@Param("building") String building, @Param("fromIndex") Integer fromIndex);
	
}