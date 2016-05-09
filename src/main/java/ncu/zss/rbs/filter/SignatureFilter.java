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

import ncu.zss.rbs.util.JsonUtil;
import ncu.zss.rbs.util.SignatureUtil;
import ncu.zss.rbs.util.UserUtil;

/**
 * Check signature of request.
 *
 */
public class SignatureFilter extends OncePerRequestFilter {
	
	/**
	 * Request timeout.
	 */
	public static final long requestTimeout = 100000000;

	Logger logger = Logger.getLogger(getClass());
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Check if it's special uri.
		if (isSpecialURI(request)) {
			filterChain.doFilter(request, response);
			return ;
		}
		
		// Check request timestamp.
		String timestamp = request.getParameter("timestamp");
		long timeDiff = new Date().getTime()/1000 - Integer.parseInt(timestamp);
		if (timeDiff >= requestTimeout) {
			logger.warn("Request timeout.");
			Writer writer = response.getWriter();
			writer.write(JsonUtil.simpleMessageResponse("Request timeout."));
			writer.flush();
			writer.close();
			return ;
		}
		
		// Check signature.
		if (checkSignature(request)) {
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
	 * Check if it's special uri.
	 * @param request
	 * @return
	 */
	private boolean isSpecialURI(HttpServletRequest request) {
		String uri = request.getRequestURI();
		logger.info("Request uri: " + uri);
		if (uri.contains("/user/login") ||
				uri.contains("/hostname")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check signature.
	 * 
	 * @param idDigest
	 * @param nonceStr
	 * @return
	 */
	private boolean checkSignature(HttpServletRequest request) {
		String idDigest = request.getParameter("idDigest");
		String id = UserUtil.getUserIdByIdDigest(idDigest);
		logger.info("id: " + id);
		if (id == null) {
			return false;
		}
		
		String nonceStr = request.getParameter("nonceStr");
		String timestamp = request.getParameter("timestamp");
		String signature = request.getParameter("signature");
		
		// Check signature.
		String str = String.format("id=%s&nonceStr=%s&timestamp=%s", id, nonceStr, timestamp);
		logger.info("str: " + str);
		String curSignature = SignatureUtil.sha1(str);
		return curSignature.equals(signature);
	}

}
