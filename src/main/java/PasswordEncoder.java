import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
	
	public static String encode(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public static boolean validate(String password, String hashedPassword) {
		return BCrypt.checkpw(password, hashedPassword);
	}
}
