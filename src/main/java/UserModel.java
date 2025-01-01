import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserModel {
	private static UserModel instance;

	private Connection con;
	private ResultSet resSet = null;
	private String url = "jdbc:mysql://localhost:3306/hogwartsdb";

	private UserModel() {
		try {
			con = DriverManager.getConnection(url, "root", "11@22y@M0!.");
			System.out.println("DB connected successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB connection failed");
		}
	}

	public static UserModel getUserModel() {
		if (instance == null) {
			instance = new UserModel();
		}
		return instance;
	}

	public boolean authenticate(String userName, String password) {
		String query = "SELECT * FROM user where uname=? and password=?";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			resSet = null;
			pst.setString(1, userName);
			pst.setString(2, password);
			resSet = pst.executeQuery();
			if (resSet.next()) {
				return true;
			} else {
				System.out.println("Invalid user name or password");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Invalid input");
		}
		return false;
	}

	public int registerUser(String userName, String password) {
		String query = "INSERT INTO user values(?, ?)";
		int nora = 0;
		try (PreparedStatement pst = con.prepareStatement(query)) {

			pst.setString(1, userName);
			pst.setString(2, password);			
			nora = pst.executeUpdate();
			return nora;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Invalid input");
		}
		return nora;
	}
	
	public Student getResult(int rollNumber) {
		String query = "SELECT * FROM result where rollnumber=?";
		try(PreparedStatement pst = con.prepareStatement(query)){
			resSet = null;
			pst.setInt(1, rollNumber);
			resSet  = pst.executeQuery();
			if(resSet.next()) {
	            String name = resSet.getString(2) ;
	            String sub1 = resSet.getString(3); 
	            String sub2 = resSet.getString(4) ;
	            String sub3 = resSet.getString(5);
	            
	            Student student = new Student(rollNumber, name, sub1, sub2, sub3);
	            return student;
	        } else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void closeConnection() {
		if (resSet != null) {
			try {
				resSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
