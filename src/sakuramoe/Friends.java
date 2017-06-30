package sakuramoe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.sun.org.apache.bcel.internal.generic.I2F;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Friends {
	public static Friends[] getFriends(int userId) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn
						.prepareStatement("SELECT `friendId`, `friendTime` FROM `friend` WHERE `userId` = ?");) {
			ps.setInt(1, userId);
			try (ResultSet resultSet = ps.executeQuery()) {
				ArrayList<Friends> users = new ArrayList<>();
				while (resultSet.next()) {
					Friends friends = new Friends();
					friends.friendId = resultSet.getInt(1);
					friends.frinedTime = new Date(resultSet.getTime(2).getTime());
					users.add(friends);
				}
				Friends[] friends = new Friends[users.size()];
				for (int i = 0; i < friends.length; ++i)
					friends[i] = users.get(i);
				return friends;
			}
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}

	public static Friends getFriends(int userId1, int userId2) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `friendTime` FROM `friend` WHERE `userId` = ? AND `friendId` = ?");) {
			ps.setInt(1, userId1);
			ps.setInt(2, userId2);
			try (ResultSet resultSet = ps.executeQuery();) {
				if(!resultSet.first())
					return null;
				Friends result = new Friends();
				result.userId = userId1;
				result.friendId = userId2;
				result.frinedTime = new Date(resultSet.getTimestamp(1).getTime());
				return result;
			}

		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}

	public static void addFriend(User user, int friendId) {
		if(getFriends(user.getUserId(), friendId) != null)
			return;
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"INSERT INTO `cite` (`userId`, `citeId`, `citeWay`) VALUES (?, ?, 'addfriend')");) {
			ps.setInt(1, friendId);
			ps.setInt(2, user.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}

	public static void removeFriend(User user, int friendId) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"REMOVE FROM  `friend` WHERE `userId` = ? AND `friendId` = ?; REMOVE FROM  `friend` WHERE `userId` = ? AND `friendId` = ?;");) {
			ps.setInt(1, friendId);
			ps.setInt(2, user.getUserId());
			ps.setInt(4, friendId);
			ps.setInt(3, user.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}

	public static void acceptFriend(User user, int friendId) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"INSERT INTO `friend` (`userId`, `friendId`) VALUES (?, ?), (?, ?);");) {
			ps.setInt(1, friendId);
			ps.setInt(2, user.getUserId());
			ps.setInt(4, friendId);
			ps.setInt(3, user.getUserId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}

	public int userId;
	public int friendId;
	public Date frinedTime;
}
