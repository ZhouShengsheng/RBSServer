package ncu.zss.rbs.filter;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import ncu.zss.rbs.db.manager.RedisManager;
import ncu.zss.rbs.util.JsonUtil;
import ncu.zss.rbs.util.SignatureUtil;
/**
 * Check signature of request.
 *
 */
public class SignatureFilter extends OncePerRequestFilter {
	
	/**
	 * Request timeout.
	 */
	public static final long requestTimeout = 10000;

	Logger logger = Logger.getLogger(getClass());
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String idDigest = request.getParameter("idDigest");
		String nonceStr = request.getParameter("nonceStr");
		String timestamp = request.getParameter("timestamp");
		String signature = request.getParameter("signature");
		
		logger.info("idDigest: " + idDigest);
		logger.info("nonceStr: " + nonceStr);
		logger.info("timestamp: " + timestamp);
		logger.info("signature: " + signature);
		
		// Check request timestamp.
		long timeDiff = new Date().getTime()/1000 - Integer.parseInt(timestamp);
		if (timeDiff >= requestTimeout || timeDiff < 0) {
			logger.warn("Request timeout.");
			Writer writer = response.getWriter();
			writer.write(JsonUtil.simpleMessageResponse("Request timeout."));
			writer.flush();
			writer.close();
			return ;
		}
		
		// Check signature.
		if (checkSignature(request, idDigest, nonceStr, timestamp, signature)) {
			logger.info("SignatureFilter passed.");
			filterChain.doFilter(request, response);
		} else {
			logger.warn("SignatureFilter failed. Unauthorized.");
			Writer writer = response.getWriter();
			writer.write(JsonUtil.simpleMessageResponse("Unauthorized."));
			writer.flush();
			writer.close();
		}
	}
	
	/**
	 * Check signature.
	 * 
	 * @param idDigest
	 * @param nonceStr
	 * @return
	 */
	private boolean checkSignature(HttpServletRequest request, String idDigest, String nonceStr, String timestamp, String signature) {
		// Do not check some uris.
		String uri = request.getRequestURI();
		if(uri.contains("/user/login")) {
			return true;
		}
		
		// Get user id from redis.
		String id = RedisManager.getStringValueRedis(1, idDigest);
		logger.info("id: " + id);
		if (id == null) {
			return false;
		}
		
		// Check signature.
		String str = String.format("id=%s&nonceStr=%s&timestamp=%s", id, nonceStr, timestamp);
		String curSignature = SignatureUtil.sha1(str);
		return curSignature.equals(signature);
	}

}
