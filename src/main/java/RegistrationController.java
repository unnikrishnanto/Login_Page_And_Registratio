
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterUser
 */
public class RegistrationController extends HttpServlet {
private static final long serialVersionUID = 1L;
    
	UserModel model;
	@Override
	public void init() {
		model = UserModel.getUserModel();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("uname");
		String password = request.getParameter("password");
		int nora = 0;
		
		try {
			nora = model.registerUser(userName, password);
			if(nora == 1) {
				response.sendRedirect("/LoginAndRegistration/index.html");
			}
			else {
				System.out.println("Already exists...");
				response.sendRedirect("/LoginAndRegistration/registration.html");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("invalid input");
			try {
				response.sendRedirect("/LoginAndRegistration/registration.html");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void destroy() {
		if(model!=null) {
			model.closeConnection();
		}
	}
}
