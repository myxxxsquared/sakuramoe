package sakuramoe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

public class Cites {

	public static Cites[] getCites(User user) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `citeId`, `citeTime`, `citePost`, `citeWay` FROM `cite` WHERE `userId` = ? ORDER BY `citeTime` DESC");) {
			ps.setInt(1, user.getUserId());
			try (ResultSet resultSet = ps.executeQuery()) {
				ArrayList<Cites> cites = new ArrayList<>();
				while (resultSet.next()) {
					Cites friends = new Cites();
					friends.userId = user.getUserId();
					friends.citeId = resultSet.getInt(1);
					friends.citeTime = new Date(resultSet.getTime(2).getTime());
					friends.citePost = resultSet.getInt(3);
					if (resultSet.wasNull())
						friends.citePost = -1;
					friends.citeWay = resultSet.getString(4);
					cites.add(friends);
				}
				Cites[] cites2 = new Cites[cites.size()];
				for (int i = 0; i < cites2.length; ++i)
					cites2[i] = cites.get(i);
				return cites2;
			}
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}

	public static void cite(int userId, int citeId, int citePost) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"INSERT INTO `cite` (`userId`, `citeId`, `citePost`, `citeWay`) VALUES (?, ?, ?, 'reply')");) {
			ps.setInt(1, userId);
			ps.setInt(2, citeId);
			if (citePost == -1)
				ps.setNull(3, Types.INTEGER);
			else
				ps.setInt(3, citePost);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}

	public int userId;
	public int citeId;
	public Date citeTime;
	public int citePost;
	public String citeWay;
}
