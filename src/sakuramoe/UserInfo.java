package sakuramoe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserInfo {
	public final int userId;
	public final String userName;
	public final String userEmail;
	public final Date userRegtime;

	public UserInfo(int userId) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `userName`, `userEmail`, `userRegtime` FROM `user` WHERE `userId`=? LIMIT 1");) {
			ps.setInt(1, userId);
			try (ResultSet result = ps.executeQuery();) {
				result.first();
				this.userId = userId;
				this.userName = result.getString(1);
				this.userEmail = result.getString(2);
				this.userRegtime = new Date(result.getTimestamp(3).getTime());
			}
		} catch (SQLException e) {
			throw new RuntimeException("Errors occurred when login", e);
		}
	}

	public UserInfo(User user) {
		this(user.getUserId());
	}
	
	public UserInfo(String userName){
		this(getUserIdByName(userName));
	}

	public String getAttribute(String name) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `userMetaValue` FROM `usermeta` WHERE `userId`=? AND `userMetaItem`=? LIMIT 1");) {
			ps.setInt(1, userId);
			ps.setString(2, name);
			try (ResultSet resultSet = ps.executeQuery()) {
				if (resultSet.first())
					return resultSet.getString(1);
				else
					return null;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	public void setAttribute(String name, String value) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `userMetaValue` FROM `usermeta` WHERE `userId`=? AND `userMetaItem`=? LIMIT 1");) {
			ps.setInt(1, userId);
			ps.setString(2, name);
			try (ResultSet resultSet = ps.executeQuery()) {
				if (resultSet.first()) {
					try (PreparedStatement ps2 = dbconn.prepareStatement(
							"UPDATE `usermeta` SET `userMetaValue` = ? WHERE `userId`=? AND `userMetaItem`=?")) {
						ps2.setString(1, value);
						ps2.setInt(2, userId);
						ps2.setString(3, name);
						ps2.executeUpdate();
					}

				} else {
					try (PreparedStatement ps2 = dbconn.prepareStatement(
							"INSERT INTO `usermeta` (`userMetaValue`, `userId`, `userMetaItem`) VALUES (?, ?, ?)")) {
						ps2.setString(1, value);
						ps2.setInt(2, userId);
						ps2.setString(3, name);
						ps2.executeUpdate();
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Errors occurred when writting attribute", e);
		}
	}

	public String getUserDesc() {
		String nickname = getAttribute("nick");
		if (nickname != null)
			return nickname;
		else
			return userName;
	}

	public void setNickName(String nick) {
		setAttribute("nick", nick);
	}

	public String getAvatar() {
		String avatar = getAttribute("avatar");
		if (avatar != null)
			return "img/avatars/" + avatar + ".jpg";
		else
			return "img/avatars/0.jpg";
	}

	public void setGender(int gender) {
		setAttribute("gender", Integer.toString(gender));
	}

	public int getGender() {
		String gender = getAttribute("gender");
		if (gender == null)
			return 0;
		return Integer.parseInt(gender);
	}

	public String getBirthday() {
		return getAttribute("birthday");
	}

	public void setBirthday(String birthday) {
		setAttribute("birthday", birthday);
	}

	public String getIntroduction() {
		return getAttribute("intro");
	}

	public void setIntroduction(String intro) {
		setAttribute("intro", intro);
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
}