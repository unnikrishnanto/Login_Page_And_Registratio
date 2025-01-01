

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class FetchResult
 */
public class ResultController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserModel model ;   
	 
	String url = "jdbc:mysql://localhost:3306/hogwartsdb";
	@Override
	public void init() {
		model = UserModel.getUserModel();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String rollNumber = request.getParameter("rollnumber");
		int convertedRollNo = Integer.parseInt(rollNumber);
		try(PrintWriter writer = response.getWriter()){
			response.setContentType("text/html");
			
			String html = "";
			Student student = model.getResult(convertedRollNo);
			if(student != null) {
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
						                	"<td>"+student.rollNumber +"</td>" +
						                	"<td>"+student.name +"</td>" +
						                	"<td>"+student.subject1 +"</td>" +
						                	"<td>"+student.subject2 +"</td>" +
						                	"<td>"+student.subject3+"</td>" +
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
		}
	}

	@Override
	public void destroy() {
		if(model != null) 
			model.closeConnection();
	}
	

}
