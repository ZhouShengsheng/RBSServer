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
	
	/**
	 * Get room list.
	 * 
	 * @param building
	 * @param capacity
	 * @param hasMultiMedia
	 * @param timeIntervals
	 * @return All the rooms matched the condition.
	 */
	List<Room> getRoomList(String building, Integer capacity, Integer hasMultiMedia);
	
	/**
	 * Search room list.
	 * 
	 * @param condition
	 * @return
	 */
	List<Room> searchRoomList(String condition);
	
	/**
	 * Add room.
	 * 
	 * @param building
	 * @param number
	 * @param capacity
	 * @param hasMultiMedia
	 * @return
	 */
	void addRoom(String building, String number, Integer capacity, Integer hasMultiMedia);
	
	/**
	 * Get room.
	 * 
	 * @param building
	 * @param number
	 * @return
	 */
	Room getRoom(String building, String number);
	
	/**
	 * Update room.
	 * 
	 * @param building
	 * @param number
	 * @param capacity
	 * @param hasMultiMedia
	 */
	void updateRoom(String building, String number, Integer capacity, Integer hasMultiMedia);
	
	/**
	 * Delete room.
	 * 
	 * @param building
	 * @param number
	 */
	void deleteRoom(String building, String number);
	
}
