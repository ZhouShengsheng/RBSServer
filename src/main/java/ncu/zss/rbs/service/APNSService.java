package ncu.zss.rbs.service;

import org.springframework.stereotype.Service;

@Service
public interface APNSService {

	void push(String apnToken, String payload);
	
}
