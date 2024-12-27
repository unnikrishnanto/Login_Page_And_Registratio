
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterUser
 */
public class RegisterUser extends HttpServlet {
private static final long serialVersionUID = 1L;
    
	Connection con;
	String url = "jdbc:mysql://localhost:3306/hogwartsdb";
	@Override
	public void init() {
		try {
			con = DriverManager.getConnection(url, "root", "11@22y@M0!.");
			System.out.println("DB connected successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB connection failed");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("uname");
		String password = request.getParameter("password");
		String query = "INSERT INTO user values(?, ?)";
		
		try(PreparedStatement pst = con.prepareStatement(query)){
			pst.setString(1, userName);
			pst.setString(2, password);
			int nora = pst.executeUpdate();
			if(nora == 1) {
				response.sendRedirect("/LoginAndRegistration/index.html");
			}
			else {
				System.out.println("Already exists...");
				response.sendRedirect("/LoginAndRegistration/registration.html");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Invalid input");
			try {
				response.sendRedirect("/LoginAndRegistration/registration.html");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void destroy() {
		try {
			if(con != null) 
				con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
