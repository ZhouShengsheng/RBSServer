package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.Class;

public interface ClassMapper {
    int deleteByPrimaryKey(String name);

    int insert(Class record);

    int insertSelective(Class record);

    Class selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(Class record);

    int updateByPrimaryKey(Class record);
}