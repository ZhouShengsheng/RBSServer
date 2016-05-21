package ncu.zss.rbs.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import ncu.zss.rbs.service.APNSService;

@Service("apnsServiceImpl")
@SuppressWarnings("unused")
public class APNSServiceImpl implements APNSService {
	
	// Certificates.
	private static final String pushNotificationCertificateDebug = "push/RBSDebugCertificate.p12";
	private static final String pushNotificationCertificateRelease = "push/RBSReleaseCertificate.p12";
	
	Logger logger = Logger.getLogger(getClass());

	// APNS service
	private ApnsService apnsService =
		    APNS.newService()
		    .withCert(getClass().getClassLoader().getResource(pushNotificationCertificateRelease).getFile(), "rbs_admin")
		    //.withSandboxDestination()
		    .withAppleDestination(true)
		    .build();
	
	@Override
	public void push(String apnToken, String payload) {
		apnsService.push(apnToken, payload);
	}

}
