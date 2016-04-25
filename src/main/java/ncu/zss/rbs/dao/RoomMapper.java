package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.Room;
import ncu.zss.rbs.model.RoomKey;

public interface RoomMapper {
    int deleteByPrimaryKey(RoomKey key);

    int insert(Room record);

    int insertSelective(Room record);

    Room selectByPrimaryKey(RoomKey key);

    int updateByPrimaryKeySelective(Room record);

    int updateByPrimaryKey(Room record);
}