package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.RoomBooking;

public interface RoomBookingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoomBooking record);

    int insertSelective(RoomBooking record);

    RoomBooking selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoomBooking record);

    int updateByPrimaryKey(RoomBooking record);
}