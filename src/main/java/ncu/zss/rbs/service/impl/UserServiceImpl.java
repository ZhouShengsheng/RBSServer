package ncu.zss.rbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncu.zss.rbs.dao.AdminMapper;
import ncu.zss.rbs.dao.FacultyMapper;
import ncu.zss.rbs.dao.StudentMapper;
import ncu.zss.rbs.db.manager.RedisManager;
import ncu.zss.rbs.model.Faculty;
import ncu.zss.rbs.model.Student;
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

	@Override
	public Faculty getFacultyById(String id) {
		return facultyMapper.selectById(id);
	}

	@Override
	public Student getStudentById(String id) {
		return studentMapper.selectById(id);
	}
	
	@Override
	public Faculty getAdminByIdDigest(String idDigest) {
		return adminMapper.selectByIdDigest(idDigest);
	}

	@Override
	public boolean isAdmin(String idDigest) {
		// Get admin id from redis.
		String id = RedisManager.getStringValueRedis(RedisManager.DB_ADMIN, idDigest);
		
		// check id.
		if (id == null) {
			Faculty admin = getAdminByIdDigest(idDigest);
			if (admin == null) {
				return false;
			}
			RedisManager.storeValueInRedis(RedisManager.DB_ADMIN, idDigest, id, RedisManager.EXPIRE_TIME);
			return true;
		}
		
		return true;
	}

	@Override
	public void updateFaculty(Faculty faculty) {
		facultyMapper.updateInfo(faculty);
	}

	@Override
	public void updateStudent(Student student) {
		studentMapper.updateInfo(student);
	}

}
