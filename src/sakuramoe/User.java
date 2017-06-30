package sakuramoe;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class User {
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

		public static boolean checkEmailFormat(String skey) {
			return patternEmail.matcher(skey).matches();
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

		static String generateSkey(int userId) {
			byte[] skey = SkeyGenerator.generateSkey();

			try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();) {
				try (PreparedStatement ps = dbconn.prepareStatement(
						"INSERT INTO `userskey` (`userId`, `userSkeySkey`, `userSkeyExprie`) VALUES (?, ?, ?)");) {
					ps.setInt(1, userId);
					ps.setBytes(2, skey);
					ps.setTimestamp(3, new Timestamp(new Date().getTime() + SkeyGenerator.SkeyTimeSpan));
					ps.executeUpdate();
				}
			} catch (SQLException e) {
				throw new RuntimeException("An error occurred when logining.", e);
			}

			return Util.HexString.bytesToHexString(skey);
		}
	}

	private int userId;
	private LoginMethod loginMethod;
	private int skeyId;

	private enum LoginMethod {
		Username, Skey
	}

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
			ps.setBytes(2, Util.PasswordSHA.passwordSHA(password));
			try (ResultSet result = ps.executeQuery();) {
				if (result.first()) {
					userId = result.getInt(1);
					loginMethod = LoginMethod.Username;
					WriteUserOperation(userId, "Login", "Success", ip);
					return new OperationResult(null, true, null);
				}
				int id = UserInfo.getUserIdByName(userName);
				if (id != -1)
					WriteUserOperation(id, "Login", "Error", ip);
				return new OperationResult(null, false, "Invaild username or password");
			}
		} catch (SQLException e) {
			if (DebugSign.DEBUG_SIGN)
				throw new RuntimeException("An error occurred when logining.", e);
			else
				return new OperationResult(null, false, "Internal server error");
		}
	}

	public OperationResult loginSkey(int userId, String userSkey, String ip) {
		if (userId < 0 || !UserInfoPattern.checkSkeyFormat(userSkey))
			return new OperationResult(null, false, "Invaild format of userid or skey");

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `userSkeyId` FROM `userskey` WHERE `userId`=? AND `userSkeySkey` = ? AND `userSkeyExprie`>CURRENT_TIMESTAMP");) {
			ps.setInt(1, userId);
			ps.setBytes(2, Util.HexString.hexStringToBytes(userSkey));
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
				throw new RuntimeException("An error occurred when logining with skey", e);
			else
				return new OperationResult(null, false, "Internal server error");
		}
	}

	public String createLoginSkey(String ip) {
		if (userId == -1)
			return null;

		WriteUserOperation(userId, "Create skey", "Success", ip);

		return SkeyGenerator.generateSkey(userId);
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

	public static boolean changePassword(String userName, String oldPass, String newPass) {
		if (!UserInfoPattern.checkUserNameFormat(userName) || !UserInfoPattern.checkPasswordFormat(oldPass)
				|| !UserInfoPattern.checkPasswordFormat(newPass))
			return false;
		
		byte[] oldSHA = Util.PasswordSHA.passwordSHA(oldPass);
		byte[] newSHA = Util.PasswordSHA.passwordSHA(newPass);

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"UPDATE `user` SET `userPassword` = ? WHERE `userId` = ? AND `userPassword` = ?");) {
			ps.setBytes(1, oldSHA);
			ps.setString(2, userName);
			ps.setBytes(3, newSHA);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			throw new RuntimeException("An error occurred when changing password", e);
		}
	}

	public static OperationResult register(String userName, String userEmail, String userPassword, String ip) {
		if (!UserInfoPattern.checkUserNameFormat(userName) || !UserInfoPattern.checkEmailFormat(userEmail)
				|| !UserInfoPattern.checkPasswordFormat(userPassword))
			return new OperationResult(null, false, "Invaild format of username, password or email");

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps2 = dbconn.prepareStatement(
						"INSERT INTO `user` (`userName`, `userEmail`, `userPassword`) VALUES (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);) {
			ps2.setString(1, userName);
			ps2.setString(2, userEmail);
			ps2.setBytes(3, Util.PasswordSHA.passwordSHA(userPassword));
			ps2.executeUpdate();
			try (ResultSet key = ps2.getGeneratedKeys()) {
				key.first();
				int newkey = key.getInt(1);
				WriteUserOperation(newkey, "Register", "Success", ip);
			}
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			return new OperationResult(null, false, "User name already exists");
		} catch (SQLException e) {
			if (DebugSign.DEBUG_SIGN)
				throw new RuntimeException("An error occurred when registering", e);
			else
				return new OperationResult(null, false, "Internal server error");
		}

		return new OperationResult(null, true, null);
	}

	public boolean isLogin() {
		return userId != -1;
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
			throw new RuntimeException("An error occurred when writing operations", e);
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
			throw new RuntimeException("An error occurred when getting operations.", e);
		}

		if (result.size() == 0)
			return new OperationInfo[0];
		return result.toArray(new OperationInfo[0]);
	}
	
	public boolean deleteUser(String password) {
		if (!UserInfoPattern.checkPasswordFormat(password))
			return false;

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"DELETE FROM `user` WHERE `userId`=? AND `userPassword`=?");) {
			ps.setInt(1, userId);
			ps.setBytes(2, Util.PasswordSHA.passwordSHA(password));
			return 1 == ps.executeUpdate();
		} catch (SQLException e) {
			if (DebugSign.DEBUG_SIGN)
				throw new RuntimeException("An error occurred when logining.", e);
			else
				return false;
		}
	}
}
