package ncu.zss.rbs.dao;

import ncu.zss.rbs.model.StudentFacultyMapping;

public interface StudentFacultyMappingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentFacultyMapping record);

    int insertSelective(StudentFacultyMapping record);

    StudentFacultyMapping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentFacultyMapping record);

    int updateByPrimaryKey(StudentFacultyMapping record);
}