package ncu.zss.rbs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ncu.zss.rbs.model.Room;
import ncu.zss.rbs.service.FavoriteRoomService;
import ncu.zss.rbs.service.RoomService;
import ncu.zss.rbs.service.UserService;
import ncu.zss.rbs.util.JsonUtil;

/**
 * APIs related to room.
 *
 */
@Controller
@RequestMapping(value = "/room", produces = "text/plain;charset=UTF-8")
@Scope("prototype")
public class RoomController {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@Autowired
	@Qualifier("roomServiceImpl")
	RoomService roomService;
	
	@Autowired
	@Qualifier("favoriteRoomServiceImpl")
	FavoriteRoomService favoriteRoomService;
	
	/**
	 * Get room list.
	 * 
	 * @param fromIndex -1 to retrieve all rooms. Otherwise, retrieve 20 rooms from fromIndex.
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String getRoomList(String building,
			@RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) {
		
		// Check parameters.
		if (building == null) {
			return JsonUtil.parameterMissingResponse("building");
		}
		
		if (fromIndex < -1) {
			return JsonUtil.simpleMessageResponse("Invalid parameter fromIndex.");
		}
		
		// Get room list.
		List<Room> roomList;
		if (fromIndex == -1) {
			roomList = roomService.getRoomList(building);
		} else {
			roomList = roomService.getRoomList(building, fromIndex);
		}
		
		if (roomList == null || roomList.isEmpty()) {
			return JsonUtil.emptyArrayResponse();
		}
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (Room room : roomList) {
			resultList.add(JsonUtil.objectToMap(room));
		}
		
		return JsonUtil.objectToJsonString(resultList);
	}
	
	/**
	 * Add room.
	 * 
	 * @param idDigest The admin's id digest.
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addRoom(String idDigest, String building, String number, Integer capacity, Integer hasMultiMedia) {
		// Check parameters.
		if (idDigest == null) {
			return JsonUtil.parameterMissingResponse("idDigest");
		}
		if (building == null) {
			return JsonUtil.parameterMissingResponse("building");
		}
		if (number == null) {
			return JsonUtil.parameterMissingResponse("number");
		}
		if (capacity == null) {
			return JsonUtil.parameterMissingResponse("capacity");
		}
		if (hasMultiMedia == null) {
			return JsonUtil.parameterMissingResponse("hasMultiMedia");
		}
		
		// Check is admin.
		if (!userService.isAdmin(idDigest)) {
			return JsonUtil.simpleMessageResponse("You do not have the privileges.");
		}
		
		// Check if room exists.
		Room room = roomService.getRoom(building, number);
		if (room != null) {
			return JsonUtil.simpleMessageResponse("Room exists.");
		}
		
		// Add room.
		roomService.addRoom(building, number, capacity, hasMultiMedia);
		
		return JsonUtil.simpleMessageResponse("Room added.");
	}
	
	/**
	 * Get room info.
	 * 
	 * @param idDigest The admin's id digest.
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public String getRoomInfo(String building, String number) {
		// Check parameters.
		if (building == null) {
			return JsonUtil.parameterMissingResponse("building");
		}
		if (number == null) {
			return JsonUtil.parameterMissingResponse("number");
		}
		
		// Check if room exists.
		Room room = roomService.getRoom(building, number);
		if (room == null) {
			return JsonUtil.simpleMessageResponse("Room not found.");
		}
		
		return JsonUtil.objectToJsonString(room);
	}
	
	/**
	 * Update room info.
	 * 
	 * @param idDigest The admin's id digest.
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateRoomInfo(String idDigest, String building, String number, Integer capacity, Integer hasMultiMedia) {
		// Check parameters.
		if (idDigest == null) {
			return JsonUtil.parameterMissingResponse("idDigest");
		}
		if (building == null) {
			return JsonUtil.parameterMissingResponse("building");
		}
		if (number == null) {
			return JsonUtil.parameterMissingResponse("number");
		}
		if (capacity == null) {
			return JsonUtil.parameterMissingResponse("capacity");
		}
		if (hasMultiMedia == null) {
			return JsonUtil.parameterMissingResponse("hasMultiMedia");
		}
		
		// Check is admin.
		if (!userService.isAdmin(idDigest)) {
			return JsonUtil.simpleMessageResponse("You do not have the privileges.");
		}
		
		// Check if room exists.
		Room room = roomService.getRoom(building, number);
		if (room == null) {
			return JsonUtil.simpleMessageResponse("Room not found.");
		}

		// Add room.
		roomService.updateRoom(building, number, capacity, hasMultiMedia);

		return JsonUtil.simpleMessageResponse("Room updated.");
	}
	
	/**
	 * Delete room.
	 * 
	 * @param idDigest The admin's id digest.
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteRoom(String idDigest, String building, String number) {
		// Check parameters.
		if (building == null) {
			return JsonUtil.parameterMissingResponse("building");
		}
		if (number == null) {
			return JsonUtil.parameterMissingResponse("number");
		}
		
		// Check is admin.
		if (!userService.isAdmin(idDigest)) {
			return JsonUtil.simpleMessageResponse("You do not have the privileges.");
		}
		
		// Check if room exists.
		Room room = roomService.getRoom(building, number);
		if (room == null) {
			return JsonUtil.simpleMessageResponse("Room not found.");
		}

		// Add room.
		roomService.deleteRoom(building, number);

		return JsonUtil.simpleMessageResponse("Room deleted.");
	}
	
	/**
	 * Set favorite of a room.
	 * 
	 * @param idDigest
	 * @param type
	 * @param id
	 * @param building
	 * @param number
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/set_favorite", method = RequestMethod.POST)
	public String setFavorite(String idDigest, String type, String id, String building, String number) {
		// Check parameters.
		if (type == null) {
			return JsonUtil.parameterMissingResponse("type");
		}
		if (id == null) {
			return JsonUtil.parameterMissingResponse("id");
		}
		if (building == null) {
			return JsonUtil.parameterMissingResponse("building");
		}
		if (number == null) {
			return JsonUtil.parameterMissingResponse("number");
		}
		
		// Check if the user performing the action is the user logged in.
		if (!userService.isSameUserLoggedIn(id, idDigest)) {
			return JsonUtil.simpleMessageResponse("You do not have the privileges.");
		}
		
		// Check if the user has already set favorite for this room.
		if (favoriteRoomService.isFavoriteRoom(type, id, building, number)) {
			return JsonUtil.simpleMessageResponse("Already set favorite.");
		}
		
		// Set favorite.
		favoriteRoomService.setFavoriteRoom(type, id, building, number);
		
		return JsonUtil.simpleMessageResponse("Successfully set favorite.");
	}
	
	/**
	 * Unset favorite of a room.
	 * 
	 * @param idDigest
	 * @param type
	 * @param id
	 * @param building
	 * @param number
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/unset_favorite", method = RequestMethod.POST)
	public String unsetFavorite(String idDigest, String type, String id, String building, String number) {
		// Check parameters.
		if (type == null) {
			return JsonUtil.parameterMissingResponse("type");
		}
		if (id == null) {
			return JsonUtil.parameterMissingResponse("id");
		}
		if (building == null) {
			return JsonUtil.parameterMissingResponse("building");
		}
		if (number == null) {
			return JsonUtil.parameterMissingResponse("number");
		}
		
		// Check if the user performing the action is the user logged in.
		if (!userService.isSameUserLoggedIn(id, idDigest)) {
			return JsonUtil.simpleMessageResponse("You do not have the privileges.");
		}
		
		// Check if the user has already set favorite for this room.
		if (!favoriteRoomService.isFavoriteRoom(type, id, building, number)) {
			return JsonUtil.simpleMessageResponse("You haven't set favorite of this room yet.");
		}
		
		// Unset favorite.
		favoriteRoomService.unsetFavoriteRoom(type, id, building, number);
		
		return JsonUtil.simpleMessageResponse("Successfully unset favorite.");
	}
	
	/**
	 * Clear favorite.
	 * 
	 * @param idDigest
	 * @param type
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clear_favorite", method = RequestMethod.POST)
	public String clearFavorite(String idDigest, String type, String id) {
		// Check parameters.
		if (type == null) {
			return JsonUtil.parameterMissingResponse("type");
		}
		if (id == null) {
			return JsonUtil.parameterMissingResponse("id");
		}
		
		// Check if the user performing the action is the user logged in.
		if (!userService.isSameUserLoggedIn(id, idDigest)) {
			return JsonUtil.simpleMessageResponse("You do not have the privileges.");
		}
		
		// Clear favorite.
		favoriteRoomService.clearFavoriteRoom(type, id);
		
		return JsonUtil.simpleMessageResponse("Successfully cleared favorite.");
	}
}
