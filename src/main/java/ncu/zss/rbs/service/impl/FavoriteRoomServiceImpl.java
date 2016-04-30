package ncu.zss.rbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncu.zss.rbs.dao.FavoriteRoomMapper;
import ncu.zss.rbs.service.FavoriteRoomService;

@Service("favoriteRoomServiceImpl")
public class FavoriteRoomServiceImpl implements FavoriteRoomService {
	
	@Autowired
	FavoriteRoomMapper favoriteRoomMapper;

	@Override
	public boolean isFavoriteRoom(String type, String id, String building, String number) {
		return favoriteRoomMapper.selectFavoriteRoom(type, id, building, number) != null;
	}

	@Override
	public void setFavoriteRoom(String type, String id, String building, String number) {
		favoriteRoomMapper.insertFavoriteRoom(type, id, building, number);
	}

	@Override
	public void unsetFavoriteRoom(String type, String id, String building, String number) {
		favoriteRoomMapper.deleteFavoriteRoom(type, id, building, number);
	}

	@Override
	public void clearFavoriteRoom(String type, String id) {
		favoriteRoomMapper.clearFavoriteRoom(type, id);
	}

}
