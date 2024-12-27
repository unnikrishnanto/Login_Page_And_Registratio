

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Authentication
 */

public class Authentication extends HttpServlet {
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
		String query = "SELECT * FROM user where uname=? and password=?";
		ResultSet resSet = null;
		try(PreparedStatement pst = con.prepareStatement(query)){
			pst.setString(1, userName);
			pst.setString(2, password);
			resSet = pst.executeQuery();
			if(resSet.next()) {
				response.sendRedirect("/LoginAndRegistration/home.html");
			}
			else {
				System.out.println("Invalid user name or password");
				response.sendRedirect("/LoginAndRegistration/index.html");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Invalid input");
		} finally {
			if(resSet != null) {
				try {
					resSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
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
