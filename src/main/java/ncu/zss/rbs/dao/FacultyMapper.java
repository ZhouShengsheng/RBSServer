package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.Faculty;

public interface FacultyMapper {
    int deleteByPrimaryKey(String id);

    int insert(Faculty record);

    int insertSelective(Faculty record);

    Faculty selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Faculty record);

    int updateByPrimaryKey(Faculty record);
}