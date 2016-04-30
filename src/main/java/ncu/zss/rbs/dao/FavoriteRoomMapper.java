package ncu.zss.rbs.dao;

import org.apache.ibatis.annotations.Param;

import ncu.zss.rbs.model.FavoriteRoom;

public interface FavoriteRoomMapper {
    
	/**
	 * Select a favorite room record.
	 * 
	 * @param type
	 * @param id
	 * @param building
	 * @param number
	 * @return
	 */
	FavoriteRoom selectFavoriteRoom(@Param("type") String type, @Param("id") String id, @Param("building") String building, @Param("number") String number);
	
	/**
	 * Insert a favorite room record.
	 * 
	 * @param type
	 * @param id
	 * @param building
	 * @param number
	 */
	void insertFavoriteRoom(@Param("type") String type, @Param("id") String id, @Param("building") String building, @Param("number") String number);
	
	/**
	 * Delete a favorite room record.
	 * 
	 * @param type
	 * @param id
	 * @param building
	 * @param number
	 */
	void deleteFavoriteRoom(@Param("type") String type, @Param("id") String id, @Param("building") String building, @Param("number") String number);
	
	/**
	 * Clear favorite room records.
	 * 
	 * @param type
	 * @param id
	 */
	void clearFavoriteRoom(@Param("type") String type, @Param("id") String id);
	
}