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
