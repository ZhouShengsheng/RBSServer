package ncu.zss.rbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ncu.zss.rbs.dao.SupervisorMapper;
import ncu.zss.rbs.model.Faculty;
import ncu.zss.rbs.service.SupervisorService;

@Service("supervisorServiceImpl")
public class SupervisorServiceImpl implements SupervisorService {
	
	@Autowired
	SupervisorMapper supervisorMapper;
	
	@Override
	public Faculty getClassSupervisor(String studentId) {
		return supervisorMapper.selectClassSupervisor(studentId);
	}

	@Override
	public List<Faculty> getSupervisorList(String studentId, Integer fromIndex) {
		return supervisorMapper.selectSupervisorList(studentId, fromIndex);
	}
	
	@Override
	public boolean isClassSupervisor(String studentId, String facultyId) {
		return getClassSupervisor(studentId).getId().equals(facultyId);
	}

	@Override
	public boolean isSupervisor(String studentId, String facultyId) {
		return supervisorMapper.selectSupervisor(studentId, facultyId) != null;
	}

	@Override
	public void addSupervisor(String studentId, String facultyId) {
		supervisorMapper.insertSupervisor(studentId, facultyId);
	}

	@Override
	public void deleteSupervisor(String studentId, String facultyId) {
		supervisorMapper.deleteSupervisor(studentId, facultyId);
	}

	@Override
	public List<Faculty> searchSupervisor(String condition, Integer fromIndex) {
		return supervisorMapper.selectSupervisorListWithCondition(String.format("%%%s%%", condition), fromIndex);
	}

}
