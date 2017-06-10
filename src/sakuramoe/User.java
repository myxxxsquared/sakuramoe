package sakuramoe;

import java.security.SecureRandom;

public class User {
	private int userId;

	public int getUserId() {
		return userId;
	}

	public User() {
		userId = -1;
	}

	public boolean login(String userName, String password) {

	}

	public boolean loginSkey(String userId, String userSkey) {

	}

	public String createLoginSkey() {
		SecureRandom random;
		random.nextDouble();
	}

	public void invalidate() {
		userId = -1;
	}

	public void changePassword(String email) {

	}

	public static void changePassword(String userName, String email) {

	}

	public static boolean changePasswordSkey(String userId, String skey) {

	}

	public static boolean register() {

	}

	public static boolean sendVerifyEmail(String userName) {

	}
	
	public static boolean verifyEmail(String userId, String skey) {

	}
}
