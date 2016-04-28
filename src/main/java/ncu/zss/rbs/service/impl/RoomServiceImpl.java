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
	
}
