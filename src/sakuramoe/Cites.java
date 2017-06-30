package sakuramoe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Cites {
	
	public static Cites[] getCites(User user){
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn
						.prepareStatement("SELECT `citeId`, `citeTime`, `citePost`, `citeComment`, `citeWay` FROM `cite` WHERE `userId` = ?");) {
			ps.setInt(1, user.getUserId());
			try (ResultSet resultSet = ps.executeQuery()) {
				ArrayList<Cites> cites = new ArrayList<>();
				while (resultSet.next()) {
					Cites friends = new Cites();
					friends.userId = user.getUserId();
					friends.citeId = resultSet.getInt(1);
					friends.citeTime = new Date(resultSet.getTime(2).getTime());
					friends.citePost = resultSet.getInt(3);
					if(resultSet.wasNull())
						friends.citePost = -1;
					friends.citeComment = resultSet.getInt(4);
					if(resultSet.wasNull())
						friends.citeComment = -1;
					friends.citeWay = resultSet.getString(5);
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
	
	public int userId;
	public int citeId;
	public Date citeTime;
	public int citePost;
	public int citeComment;
	public String citeWay;
}
