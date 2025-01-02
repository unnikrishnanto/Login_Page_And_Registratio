

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Authentication
 */

public class AuthenticationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserModel model;
	
	@Override
	public void init() {
		model = UserModel.getUserModel();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("uname");
		String password = request.getParameter("password");
		boolean status = model.authenticate(userName, password);
		
			try {
				if(status) {
					HttpSession session = request.getSession(true);
					session.setAttribute("user", userName);
					response.sendRedirect("/LoginAndRegistration/home.html");
				} else {
					response.sendRedirect("/LoginAndRegistration/index.html");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
	}

	@Override
	public void destroy() {
		if(model != null) {
			model.closeConnection();
		}
	}
}
