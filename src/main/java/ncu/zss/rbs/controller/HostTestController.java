package ncu.zss.rbs.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/", produces = "text/plain;charset=UTF-8")
@Scope("prototype")
public class HostTestController {

	@ResponseBody
	@RequestMapping(value = "/hostname", method = RequestMethod.GET)
	public String getIP() throws UnknownHostException {
		InetAddress ip = InetAddress.getLocalHost();
		return ip.getHostName();
	}
	
}
