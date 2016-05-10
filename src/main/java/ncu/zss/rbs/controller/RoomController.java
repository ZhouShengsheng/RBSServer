package ncu.zss.rbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ncu.zss.rbs.db.manager.RedisManager;
import ncu.zss.rbs.model.Room;
import ncu.zss.rbs.model.RoomBooking;
import ncu.zss.rbs.service.FavoriteRoomService;
import ncu.zss.rbs.service.RoomBookingService;
import ncu.zss.rbs.service.RoomService;
import ncu.zss.rbs.service.UserService;
import ncu.zss.rbs.util.JsonUtil;
import ncu.zss.rbs.util.TimeIntervalUtil;

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
	
	@Autowired
	@Qualifier("roomBookingServiceImpl")
	RoomBookingService roomBookingService;
	
	/**
	 * Get room list.
	 * 
	 * @param fromIndex -1 to retrieve all rooms. Otherwise, retrieve 20 rooms from fromIndex.
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String getRoomList(String building, Integer capacity, Integer hasMultiMedia, String timeIntervals,
			@RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) throws JsonParseException, JsonMappingException, IOException {
		
		// Check parameters.		
		if (fromIndex < -1) {
			return JsonUtil.simpleMessageResponse("Invalid parameter fromIndex.");
		}

		// Redis key.
		String roomListKey = String.format("screen:building=%s&capacity=%d&hasMultiMedia=%d&timeIntervals=%s", building, capacity,
				hasMultiMedia, timeIntervals);
		logger.info("roomListKey: " + roomListKey);
		// Get room list from redis.
		String roomListJsonString = RedisManager.getStringValueRedis(RedisManager.DB_ROOM_LIST, roomListKey);
		if (roomListJsonString != null) {
			logger.info("Get result in redis!");
			if (fromIndex == -1) {
				return roomListJsonString;
			}
			// Page.
			List<Object> roomList = JsonUtil.jsonStringToList(roomListJsonString);
			//logger.info("getting pageRoomList");
			int size = roomList.size();
			if (fromIndex >= size) {
				return JsonUtil.emptyArrayResponse();
			}
			int pageSize = 20;
			List<Object> pageRoomList;
			if (size - fromIndex >= pageSize) {
				pageRoomList = roomList.subList(fromIndex, fromIndex+pageSize);
			} else {
				pageRoomList = roomList.subList(fromIndex, size);
			}
			return JsonUtil.objectToJsonString(pageRoomList);
		}
		
		// Get room list from database.
		List<Room> roomList = roomService.getRoomList(building, capacity, hasMultiMedia);
		ArrayList<ArrayList<Date>> timeIntervalList = TimeIntervalUtil.getTimeIntervalListFromJsonString(timeIntervals);
		if (timeIntervalList != null) {
			int count = roomList.size();
			for (int i = 0; i < count; i++) {
				Room room = roomList.get(i);
				List<RoomBooking> roomBookingList = roomBookingService.getRoomBookingListForRoom(room.getBuilding(), room.getNumber());
				if (TimeIntervalUtil.checkTimeIntervalOverlaps(roomBookingList, timeIntervalList, false, null)) {
					roomList.remove(i);
					i--;
					count--;
				}
			}
		}
		
		if (roomList == null || roomList.isEmpty()) {
			return JsonUtil.emptyArrayResponse();
		}
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (Room room : roomList) {
			resultList.add(JsonUtil.objectToMap(room));
		}
		String resultListString = JsonUtil.objectToJsonString(resultList);

		// Store result list into redis.
		RedisManager.storeValueInRedis(RedisManager.DB_ROOM_LIST, roomListKey, resultListString, -1);
		
		if (fromIndex == -1) {
			return resultListString;
		}
		// Page.
		int size = roomList.size();
		if (fromIndex >= size) {
			return JsonUtil.emptyArrayResponse();
		}
		int pageSize = 20;
		List<Map<String, Object>> pageRoomList;
		if (size - fromIndex >= pageSize) {
			pageRoomList = resultList.subList(fromIndex, fromIndex+pageSize);
		} else {
			pageRoomList = resultList.subList(fromIndex, size);
		}
		return JsonUtil.objectToJsonString(pageRoomList);
	}
	
	/**
	 * Search room.
	 * 
	 * @param fromIndex -1 to retrieve all rooms. Otherwise, retrieve 20 rooms from fromIndex.
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchRoomList(@RequestParam(value = "condition", defaultValue = "none") String condition,
			@RequestParam(value = "fromIndex", defaultValue = "0") Integer fromIndex) throws JsonParseException, JsonMappingException, IOException {
		// Check parameters.		
		if (fromIndex < -1) {
			return JsonUtil.simpleMessageResponse("Invalid parameter fromIndex.");
		}

		// Redis key.
		String roomListKey = String.format("search:condition=%s", condition);
		logger.info("roomListKey: " + roomListKey);
		// Get room list from redis.
		String roomListJsonString = RedisManager.getStringValueRedis(RedisManager.DB_ROOM_LIST, roomListKey);
		if (roomListJsonString != null) {
			logger.info("Get result in redis!");
			if (fromIndex == -1) {
				return roomListJsonString;
			}
			// Page.
			List<Object> roomList = JsonUtil.jsonStringToList(roomListJsonString);
			//logger.info("getting pageRoomList");
			int size = roomList.size();
			if (fromIndex >= size) {
				return JsonUtil.emptyArrayResponse();
			}
			int pageSize = 20;
			List<Object> pageRoomList;
			if (size - fromIndex >= pageSize) {
				pageRoomList = roomList.subList(fromIndex, fromIndex+pageSize);
			} else {
				pageRoomList = roomList.subList(fromIndex, size);
			}
			return JsonUtil.objectToJsonString(pageRoomList);
		}
		
		// Get room list from database.
		List<Room> roomList = roomService.searchRoomList(condition);
		
		if (roomList == null || roomList.isEmpty()) {
			return JsonUtil.emptyArrayResponse();
		}
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (Room room : roomList) {
			resultList.add(JsonUtil.objectToMap(room));
		}
		String resultListString = JsonUtil.objectToJsonString(resultList);

		// Store result list into redis.
		RedisManager.storeValueInRedis(RedisManager.DB_ROOM_LIST, roomListKey, resultListString, -1);
		
		if (fromIndex == -1) {
			return resultListString;
		}
		// Page.
		int size = roomList.size();
		if (fromIndex >= size) {
			return JsonUtil.emptyArrayResponse();
		}
		int pageSize = 20;
		List<Map<String, Object>> pageRoomList;
		if (size - fromIndex >= pageSize) {
			pageRoomList = resultList.subList(fromIndex, fromIndex+pageSize);
		} else {
			pageRoomList = resultList.subList(fromIndex, size);
		}
		return JsonUtil.objectToJsonString(pageRoomList);
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
	 *  Get room info.
	 *  
	 * @param building
	 * @param number
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public String getRoomInfo(String type, String id, String building, String number) {
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
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("basicInfo", JsonUtil.objectToMap(room));
		
		// Favorite.
		boolean isFavorite = favoriteRoomService.isFavoriteRoom(type, id, building, number);
		resultMap.put("isFavorite", isFavorite);
		
		// Add booked intervals.
		List<RoomBooking> roomBookingList = roomBookingService.getRoomBookingListForRoom(building, number);
		ArrayList<ArrayList<Date>> timeIntervalList = new ArrayList<>();
		for (RoomBooking roomBooking : roomBookingList) {
			ArrayList<Date> timeInterval = new ArrayList<>();
			timeInterval.add(roomBooking.getFromtime());
			timeInterval.add(roomBooking.getTotime());
			timeIntervalList.add(timeInterval);
		}
		resultMap.put("timeIntervals", timeIntervalList);
		
		return JsonUtil.objectToJsonString(resultMap);
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
