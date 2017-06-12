package sakuramoe;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class User {

	private static class PasswordSHA {
		final static String SALT = ".30c8h*630_&3/3fbpwfd'";

		static byte[] passwordSHA(String password) {
			MessageDigest md;

			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("Cannot find SHA-256", e);
			}

			md.update((password + SALT).getBytes());
			byte[] savepassword = md.digest();

			return savepassword;
		}
	}

	private static class HexString {
		static String bytesToHexString(byte[] src) {
			StringBuilder stringBuilder = new StringBuilder("");
			if (src == null || src.length <= 0) {
				return null;
			}
			for (int i = 0; i < src.length; i++) {
				int v = src[i] & 0xFF;
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					stringBuilder.append(0);
				}
				stringBuilder.append(hv);
			}
			return stringBuilder.toString();
		}

		static byte[] hexStringToBytes(String hexString) {
			if (hexString == null || hexString.equals("")) {
				return null;
			}
			hexString = hexString.toUpperCase();
			int length = hexString.length() / 2;
			char[] hexChars = hexString.toCharArray();
			byte[] d = new byte[length];
			for (int i = 0; i < length; i++) {
				int pos = i * 2;
				d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
			}
			return d;
		}

		private static byte charToByte(char c) {
			return (byte) "0123456789ABCDEF".indexOf(c);
		}
	}

	public static class UserInfoPattern {
		public static final Pattern patternUserName = Pattern.compile("[a-zA-Z0-9_]{1,31}");
		public static final Pattern patternPassword = Pattern.compile(".{6,31}");
		public static final Pattern patternSkey = Pattern.compile("[a-zA-Z0-9]{64}");
		public static final Pattern patternEmail = Pattern.compile(".+@.+\\..+");

		public static boolean checkUserNameFormat(String userName) {
			return patternUserName.matcher(userName).matches();
		}

		public static boolean checkPasswordFormat(String password) {
			return patternPassword.matcher(password).matches();
		}

		public static boolean checkSkeyFormat(String skey) {
			return patternSkey.matcher(skey).matches();
		}

		public static boolean checkEmailFormat(String email) {
			return patternEmail.matcher(email).matches();
		}
	}

	private static class SkeyGenerator {
		static final long SkeyTimeSpan = 604800000l;
		static final SecureRandom secureRandom = new SecureRandom();
		static final int skeyLength = 32;

		static byte[] generateSkey() {
			byte[] skey = new byte[skeyLength];
			secureRandom.nextBytes(skey);
			return skey;
		}

		static String generateSkey(int userId, String purpose) {
			if (!purpose.equals("login") && !purpose.equals("emailcheck") && !purpose.equals("changepwd"))
				throw new RuntimeException("Skey type error.");

			byte[] skey = SkeyGenerator.generateSkey();

			try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();) {
				try (PreparedStatement ps = dbconn.prepareStatement(
						"INSERT INTO `userskey` (`userId`, `userSkeySkey`, `userSkeyExprie`, `userSkeyUseage`) VALUES (?, ?, ?, ?)");) {
					ps.setInt(1, userId);
					ps.setBytes(2, skey);
					ps.setTimestamp(3, new Timestamp(new Date().getTime() + SkeyGenerator.SkeyTimeSpan));
					ps.setString(4, purpose);
					ps.executeUpdate();
				}
			} catch (SQLException e) {
				throw new RuntimeException("Errors occurred when login", e);
			}

			return HexString.bytesToHexString(skey);
		}

		static void sendEmailSkey(int userId, String purpose) {
			if (!purpose.equals("emailcheck") && !purpose.equals("changepwd"))
				throw new RuntimeException("Skey type error.");

			generateSkey(userId, purpose);
		}
	}

	public static class OperationResult {
		public boolean success;
		public Object result;
		public String reason;

		OperationResult(Object result, boolean success, String reason) {
			this.success = success;
			this.result = result;
			this.reason = reason;
		}
	}

	private int userId;

	private enum LoginMethod {
		Username, Skey
	}

	private LoginMethod loginMethod;
	private int skeyId;

	public LoginMethod getLoginMethod() {
		return loginMethod;
	}

	public int getUserId() {
		return userId;
	}

	public User() {
		userId = -1;
	}

	public OperationResult login(String userName, String password, String ip) {
		if (!UserInfoPattern.checkUserNameFormat(userName) || !UserInfoPattern.checkPasswordFormat(password))
			return new OperationResult(null, false, "Invaild format of username or password");

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `userId` FROM `user` WHERE `userName`=? AND `userPassword`=? LIMIT 1");) {
			ps.setString(1, userName);
			ps.setBytes(2, PasswordSHA.passwordSHA(password));
			try (ResultSet result = ps.executeQuery();) {
				if (result.first()) {
					userId = result.getInt(1);
					loginMethod = LoginMethod.Username;
					WriteUserOperation(userId, "Login", "Success", ip);
					return new OperationResult(null, true, null);
				}
				int id = getUserIdByName(userName);
				if (id != -1)
					WriteUserOperation(id, "Login", "Error", ip);
				return new OperationResult(null, false, "Invaild username or password");
			}
		} catch (SQLException e) {
			if (DebugSign.DEBUG_SIGN)
				throw new RuntimeException("Errors occurred when login", e);
			else
				return new OperationResult(null, false, "Internal server error");
		}
	}

	public OperationResult loginSkey(int userId, String userSkey, String ip) {
		if (userId < 0 || !UserInfoPattern.checkSkeyFormat(userSkey))
			return new OperationResult(null, false, "Invaild format of userid or skey");

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `userSkeyId` FROM `userskey` WHERE `userId`=? AND `userSkeyUseage`='login' AND `userSkeySkey` = ? AND `userSkeyExprie`>CURRENT_TIMESTAMP");) {
			ps.setInt(1, userId);
			ps.setBytes(2, HexString.hexStringToBytes(userSkey));
			try (ResultSet result = ps.executeQuery();) {
				if (result.first()) {
					this.userId = userId;
					loginMethod = LoginMethod.Skey;
					skeyId = result.getInt(1);
					WriteUserOperation(userId, "Skey login", "Success", ip);
					return new OperationResult(null, true, null);
				}
				try {
					WriteUserOperation(userId, "Skey login", "Error", ip);
				} catch (Exception e) {
				}

				return new OperationResult(null, false, "Skey expired");
			}
		} catch (SQLException e) {
			if (DebugSign.DEBUG_SIGN)
				throw new RuntimeException("Errors occurred when login with skeys", e);
			else
				return new OperationResult(null, false, "Internal server error");
		}
	}

	public String createLoginSkey(String ip) {
		if (userId == -1)
			return null;

		WriteUserOperation(userId, "Create skey", "Success", ip);

		return SkeyGenerator.generateSkey(userId, "login");
	}

	public void logout(String ip) {
		if (userId == -1)
			return;

		WriteUserOperation(userId, "Logout", "Success", ip);

		if (loginMethod == LoginMethod.Skey) {
			try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
					PreparedStatement ps = dbconn.prepareStatement("DELETE FROM `userskey` WHERE `userSkeyId` = ?");) {
				ps.setInt(1, skeyId);
				ps.executeUpdate();
			} catch (SQLException e) {
				if (DebugSign.DEBUG_SIGN)
					e.printStackTrace();
			}
		}
		userId = -1;
	}

	public void changePassword(String email) {
		if (userId == -1 || !UserInfoPattern.checkEmailFormat(email))
			return;

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn
						.prepareStatement("SELECT `userId` FROM `user` WHERE `userId`=? AND `userEmail`=? LIMIT 1");) {
			ps.setInt(1, userId);
			ps.setString(2, email);

			try (ResultSet result = ps.executeQuery();) {
				if (result.first()) {
					SkeyGenerator.sendEmailSkey(userId, "changepwd");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Errors occurred when login", e);
		}
	}

	public static void changePassword(String userName, String email) {
		if (!UserInfoPattern.checkUserNameFormat(userName) || !UserInfoPattern.checkEmailFormat(email))
			return;

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `userId` FROM `user` WHERE `userName`=? AND `userEmail`=? LIMIT 1");) {
			ps.setString(1, userName);
			ps.setString(2, email);

			try (ResultSet result = ps.executeQuery();) {
				if (result.first()) {
					SkeyGenerator.sendEmailSkey(result.getInt(1), "changepwd");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Errors occurred when login", e);
		}
	}

	public static boolean emailVerify(int userId, String skey, String purpose, String password) {
		if (purpose.equals("emailcheck")) {
		} else if (purpose.equals("changepwd") && UserInfoPattern.checkPasswordFormat(password)) {
		} else
			return false;

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT FROM `userSkeyId` WHERE `userId`=? AND `userSkeyUseage`='login' AND `userSkeySkey` = ? AND `userSkeyExprie`>CURRENT_TIMESTAMP");) {
			ps.setInt(1, userId);
			ps.setBytes(2, HexString.hexStringToBytes(skey));
			try (ResultSet result = ps.executeQuery();) {
				if (result.first()) {
					if (purpose.equals("emailcheck")) {
						try (PreparedStatement ps2 = dbconn
								.prepareStatement("UPDATE `user` SET `userEmailCheck` = `yes` WHERE `userId` = ?");) {
							ps2.setInt(1, userId);
							ps2.executeUpdate();
						}

					} else if (purpose.equals("changepwd")) {
						try (PreparedStatement ps2 = dbconn
								.prepareStatement("UPDATE `user` SET `userPassword` = ? WHERE `userId` = ?");) {
							ps2.setBytes(1, PasswordSHA.passwordSHA(password));
							ps2.setInt(2, userId);
							ps2.executeUpdate();
						}
					}

					try (PreparedStatement ps2 = dbconn
							.prepareStatement("DELETE FROM `userskey` WHERE `userId` = ? AND `userSkeyUseage` = ?");) {
						ps2.setInt(1, userId);
						ps2.setString(2, purpose);
						ps2.executeUpdate();
					}
					return true;
				}
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Errors occurred when login with skeys", e);
		}
	}

	public static OperationResult register(String userName, String userEmail, String userPassword) {
		if (!UserInfoPattern.checkUserNameFormat(userName) || !UserInfoPattern.checkEmailFormat(userEmail)
				|| !UserInfoPattern.checkPasswordFormat(userPassword))
			return new OperationResult(null, false, "Invaild format of username, password or email");

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps2 = dbconn.prepareStatement(
						"INSERT INTO `user` (`userName`, `userEmail`, `userPassword`) VALUES (?, ?, ?)");) {
			ps2.setString(1, userName);
			ps2.setString(2, userEmail);
			ps2.setBytes(3, PasswordSHA.passwordSHA(userPassword));
			ps2.executeUpdate();
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			return new OperationResult(null, false, "User name already exists");
		} catch (SQLException e) {
			if (DebugSign.DEBUG_SIGN)
				throw new RuntimeException("Errors occurred when login with skeys", e);
			else
				return new OperationResult(null, false, "Internal server error");
		}

		return new OperationResult(null, true, null);
	}

	public UserInfo getUserInfo() {
		if (userId == -1)
			return null;

		return new UserInfo(userId);
	}

	public boolean isLogin() {
		return userId != -1;
	}

	public static Integer getUserIdByName(String userName) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps2 = dbconn.prepareStatement("SELECT `userId` FROM `user` WHERE `userName` = ?");) {
			ps2.setString(1, userName);
			try (ResultSet set = ps2.executeQuery()) {
				if (set.first())
					return set.getInt(1);
				else
					return -1;
			}
		} catch (SQLException e) {
			return -1;
		}
	}

	public static void WriteUserOperation(int userId, String type, String result, String ip) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps2 = dbconn.prepareStatement(
						"INSERT INTO `useroperation` (`userId`, `userOperationType`, `userOperationResult`, `userOperationIP`) VALUES (?,?,?,?)");) {
			ps2.setInt(1, userId);
			ps2.setString(2, type);
			ps2.setString(3, result);
			ps2.setString(4, ip);
			ps2.executeUpdate();
		} catch (SQLException e) {
		}
	}

	public OperationInfo[] getOperations() {
		ArrayList<OperationInfo> result = new ArrayList<>();

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `userOperationId`, `userOperationType`, `userOperationResult`,`userOperationIP`,`userOperationTime` FROM `useroperation` WHERE `userId` = ? ORDER BY `userOperationId` DESC LIMIT 30");) {
			ps.setInt(1, userId);
			try (ResultSet set = ps.executeQuery()) {
				while (set.next()) {
					OperationInfo info = new OperationInfo();
					info.id = set.getInt(1);
					info.userId = userId;
					info.operationType = set.getString(2);
					info.operationResult = set.getString(3);
					info.ip = set.getString(4);
					info.time = new Date(set.getTimestamp(5).getTime());
					result.add(info);
				}
			}
		} catch (SQLException e) {
		}

		if(result.size() == 0)
			return new OperationInfo[0];
		return result.toArray(new OperationInfo[0]);
	}
}
