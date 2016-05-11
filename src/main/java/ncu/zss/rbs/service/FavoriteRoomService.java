package ncu.zss.rbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ncu.zss.rbs.model.Room;

@Service
public interface FavoriteRoomService {

	/**
	 * Is the room the favorite room if the user.
	 * 
	 * @param type User type.
	 * @param id User id.
	 * @param building
	 * @param number
	 * @return
	 */
	boolean isFavoriteRoom(String type, String id, String building, String number);
	
	/**
	 * Set favorite.
	 * 
	 * @param type
	 * @param id
	 * @param building
	 * @param number
	 */
	void setFavoriteRoom(String type, String id, String building, String number);
	
	/**
	 * Unset favorite.
	 * 
	 * @param type
	 * @param id
	 * @param building
	 * @param number
	 */
	void unsetFavoriteRoom(String type, String id, String building, String number);
	
	/**
	 * Clear favorite.
	 * 
	 * @param type
	 * @param id
	 */
	void clearFavoriteRoom(String type, String id);
	
	/**
	 * Get favorite room list.
	 * 
	 * @param type
	 * @param id
	 * @param fromIndex
	 * @return
	 */
	List<Room>getFavoriteList(String type, String id, Integer fromIndex);
	
}
