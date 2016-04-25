package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(String id);

    int insert(Admin record);

    int insertSelective(Admin record);
}