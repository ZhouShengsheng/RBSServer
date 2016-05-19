package ncu.zss.rbs.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import ncu.zss.rbs.service.APNSService;

@Service("apnsServiceImpl")
public class APNSServiceImpl implements APNSService {
	
	Logger logger = Logger.getLogger(getClass());

	// APNS service
	private ApnsService apnsService =
		    APNS.newService()
		    .withCert(getClass().getClassLoader().getResource("push/RBSReleaseCertificate.p12").getFile(), "rbs_admin")
		    .withAppleDestination(true)
		    .build();
	
	@Override
	public void push(String apnToken, String payload) {
		apnsService.push(apnToken, payload);
	}

}
