import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorizationFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;

		String reqURI = httpReq.getRequestURI();
		if (reqURI.endsWith("/index.html") || reqURI.endsWith("/registration.html") || reqURI.endsWith("/login")
				|| reqURI.endsWith("/register")) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = httpReq.getSession(false);
		String userName;
		if (session != null && (userName = (String) session.getAttribute("user")) != null) {
			System.out.println("Giving Access to " + userName);
			chain.doFilter(request, response);
		} else {
			System.out.println("UnAuthorised access.......");
			HttpServletResponse httpRes = (HttpServletResponse) response;
			httpRes.sendRedirect("http://localhost:9090/LoginAndRegistration/index.html");
		}

	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}

}
