package ncu.zss.rbs.filter;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import ncu.zss.rbs.util.JsonUtil;
/**
 * Check requests' signatures.
 *
 */
public class SignatureFilter extends OncePerRequestFilter {

	private static boolean passed = false;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (checkSignature()) {
			System.out.println("SignatureFilter passed");
		} else {
			System.out.println("SignatureFilter refused");
			Map<String, String> map = new HashMap<>();
			map.put("message", "Unauthorized");
			Writer writer = response.getWriter();
			writer.write(JsonUtil.objectToJsonString(map));
			writer.flush();
			writer.close();
		}

		filterChain.doFilter(request, response);
	}
	
	private boolean checkSignature() {
		passed = !passed;
		return passed;
	}

}
