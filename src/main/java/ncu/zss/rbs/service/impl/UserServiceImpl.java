package ncu.zss.rbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncu.zss.rbs.dao.AdminMapper;
import ncu.zss.rbs.dao.FacultyMapper;
import ncu.zss.rbs.dao.StudentMapper;
import ncu.zss.rbs.model.Faculty;
import ncu.zss.rbs.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	
	@Autowired
	AdminMapper adminMapper;
	
	@Autowired
	FacultyMapper facultyMapper;
	
	@Autowired
	StudentMapper studentMapper;
	
	@Override
	public Faculty getAdminById(String id) {
		return adminMapper.selectById(id);
	}

}
