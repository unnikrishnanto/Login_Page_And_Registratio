

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class FetchResult
 */
public class FetchResult extends HttpServlet {
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String rollNumber = request.getParameter("rollnumber");
		String query = "SELECT * FROM result where rollnumber=?";
		ResultSet resSet = null;
		try(
				PreparedStatement pst = con.prepareStatement(query);
				PrintWriter writer = response.getWriter()
			){
			response.setContentType("text/html");
			int convertedRollNo = Integer.parseInt(rollNumber);
			pst.setInt(1, convertedRollNo);
			resSet  = pst.executeQuery();
			String html = "";
			if(resSet.next()) {
				html = """
						<!DOCTYPE html>
						<html lang="en">
						    <link rel="stylesheet" href="result.css">
						    <link rel="preconnect" href="https://fonts.googleapis.com">
						    <link rel="preconnect" href="https://fonts.gstatic.com" >
						    <link href="https://fonts.googleapis.com/css2?family=Abril+Fatface&family=Calistoga&display=swap" rel="stylesheet">
						    <title>Home</title>
						</head>
						<body>
						    <header>
						        <img id="logo" src="resourses/hogwarts_logo.jpg" alt="image not found">
						    </header>
						
						    
						    <div id="table-container">
						        <div id="heading-div">
						            <h1>RESULT</h1>
						        </div>
						        <div id="table-div">
						            <table border="1">
						                <tr>
						                    <th>Roll Number</th> 
						                    <th>Name</th> 
						                    <th>Subject 1</th>
						                    <th>Subject 2</th>
						                    <th>Subject 3</th>
						                </tr>
						                <tr>"""+
						                	"<td>"+resSet.getString(1) +"</td>" +
						                	"<td>"+resSet.getString("name") +"</td>" +
						                	"<td>"+resSet.getString(3) +"</td>" +
						                	"<td>"+resSet.getString(4) +"</td>" +
						                	"<td>"+resSet.getString(5) +"</td>" +
						                "</tr>"+
						            """
						            </table>
						        </div>
						    </div>
						    
						</body>
						</html>
				""";
			} else {
				html = """
						<!DOCTYPE html>
							<html>
								<head>
									<meta charset="UTF-8">
									<title>Results</title>
								</head>
								<body>
									<h1>Invalid Roll Number. Student not found</h1>
									<p>
									To try again..
									<a href = "http://localhost:9090/LoginAndRegistration/home.html">Click Here</a>
									</p>
								</body>
							</html>
						""";
			}
			
			writer.println(html);

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
