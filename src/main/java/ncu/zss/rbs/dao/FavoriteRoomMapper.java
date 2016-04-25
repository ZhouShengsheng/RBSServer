package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.FavoriteRoom;

public interface FavoriteRoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FavoriteRoom record);

    int insertSelective(FavoriteRoom record);

    FavoriteRoom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FavoriteRoom record);

    int updateByPrimaryKey(FavoriteRoom record);
}