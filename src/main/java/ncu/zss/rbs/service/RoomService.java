package ncu.zss.rbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ncu.zss.rbs.model.Room;

@Service
public interface RoomService {
	
	/**
	 * Get room list of building from index.
	 * 
	 * @param building
	 */
	List<Room> getRoomList(String building);
	
	/**
	 * Get room list of building from index.
	 * 
	 * @param building
	 * @param fromIndex
	 */
	List<Room> getRoomList(String building, Integer fromIndex);
	
}
