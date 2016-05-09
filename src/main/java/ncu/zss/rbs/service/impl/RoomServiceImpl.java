package ncu.zss.rbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncu.zss.rbs.dao.RoomMapper;
import ncu.zss.rbs.model.Room;
import ncu.zss.rbs.service.RoomService;

@Service("roomServiceImpl")
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomMapper roomMapper;
	
	@Override
	public List<Room> getRoomList(String building) {
		return roomMapper.selectRoomList(building);
	}
	
	@Override
	public List<Room> getRoomList(String building, Integer fromIndex) {
		return roomMapper.selectRoomListWithFromIndex(building, fromIndex);
	}
	
	@Override
	public List<Room> getRoomList(String building, Integer capacity, Integer hasMultiMedia) {
		return roomMapper.selectRoomListWithScreenCondition(building, capacity, hasMultiMedia);
	}

	@Override
	public List<Room> searchRoomList(String condition) {
		return roomMapper.selectRoomListWithSearchCondition(String.format("%%%s%%", condition));
	}
	
	@Override
	public void addRoom(String building, String number, Integer capacity, Integer hasMultiMedia) {
		roomMapper.insertRoom(building, number, capacity, hasMultiMedia);
	}

	@Override
	public Room getRoom(String building, String number) {
		return roomMapper.selectRoom(building, number);
	}

	@Override
	public void updateRoom(String building, String number, Integer capacity, Integer hasMultiMedia) {
		roomMapper.updateRoom(building, number, capacity, hasMultiMedia);
	}

	@Override
	public void deleteRoom(String building, String number) {
		roomMapper.deleteRoom(building, number);
	}
	
}
