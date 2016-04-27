package ncu.zss.rbs.service;

import org.springframework.stereotype.Service;

import ncu.zss.rbs.model.Faculty;

@Service
public interface UserService {
	
	/**
	 * Get admin by id.
	 * @param id Admin id.
	 * @return Admin.
	 */
	Faculty getAdminById(String id);
}
