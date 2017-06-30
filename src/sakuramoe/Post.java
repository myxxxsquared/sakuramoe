package sakuramoe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {

	public static class Comment {
		public int commentId;
		public int postId;
		public int userId;
		public Date commentTime;
		public String commentContent;
		public int commentParent;
	}

	public static class PostInfo {
		public int postID;
		public int userId;
		public String userDesc;
		public String userAvatar;
		public Date timePosted;
		public String content;
	}

	public static class CommentTreeNode {
		public int commentId;
		public int postId;
		public int userId;
		public String userDesc;
		public String userAvatar;
		public String parentDesc;
		public Date timePosted;
		public String content;
		public int depth;
	}

	private int postId;

	public int getPostId() {
		return postId;
	}

	public Post(int postId) {
		this.postId = postId;
	}

	public PostInfo getPostInfo() {
		PostInfo result = new PostInfo();
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `userId`, `postTime`, `postContent` FROM `post` WHERE `postId` = ?",
						Statement.RETURN_GENERATED_KEYS);) {
			ps.setInt(1, postId);
			try (ResultSet resultSet = ps.executeQuery()) {
				if (!resultSet.first())
					return null;
				result.userId = resultSet.getInt(1);
				result.timePosted = new Date(resultSet.getTimestamp(2).getTime());
				result.content = resultSet.getString(3);
				result.postID = postId;
				UserInfo userInfo = new UserInfo(result.userId);
				result.userAvatar = userInfo.getAvatar();
				result.userDesc = userInfo.getUserDesc();
			}
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
		return result;
	}

	public static Post createPost(User user, String content) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"INSERT INTO `post` (`userId`, `postContent`) VALUES (?, ?)",
						Statement.RETURN_GENERATED_KEYS);) {
			ps.setInt(1, user.getUserId());
			ps.setString(2, content);
			ps.executeUpdate();
			try (ResultSet gen = ps.getGeneratedKeys();) {
				gen.first();
				return new Post(gen.getInt(1));
			}
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}

	public void createComment(User user, int parent, String content) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"INSERT INTO `comment` (`postId`, `userId`, `commentContent`, `commentParent`) VALUES (?, ?, ?, ?)");) {
			ps.setInt(1, postId);
			ps.setInt(2, user.getUserId());
			ps.setString(3, content);
			if (parent == -1)
				ps.setNull(4, Types.INTEGER);
			else
				ps.setInt(4, parent);
			ps.executeUpdate();
			Cites.cite(getPostInfo().userId, user.getUserId(), postId);
			if (parent != -1) {
				Cites.cite(getCommentUserId(parent), user.getUserId(), postId);
			}
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}

	public static int getCommentUserId(int commentid) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps2 = dbconn.prepareStatement("SELECT `userId` FROM `comment` WHERE `commentId` = ?");) {
			ps2.setInt(1, commentid);
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

	public List<Comment> getComments() {
		List<Comment> result = new ArrayList<>();

		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `commentId`, `postId`, `userId`, `commentTime`, `commentContent`, `commentParent` FROM `comment` WHERE `postId` = ?",
						Statement.RETURN_GENERATED_KEYS);) {
			ps.setInt(1, postId);
			try (ResultSet r = ps.executeQuery();) {
				while (r.next()) {
					Comment comment = new Comment();
					comment.commentId = r.getInt(1);
					comment.postId = r.getInt(2);
					comment.userId = r.getInt(3);
					comment.commentTime = new Date(r.getTimestamp(4).getTime());
					comment.commentContent = r.getString(5);
					comment.commentParent = r.getInt(6);
					if (comment.commentParent == 0)
						comment.commentParent = -1;
					result.add(comment);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}

		return result;
	}

	public static List<CommentTreeNode> parseCommentTree(List<Comment> comments, String parentDesc) {
		List<CommentTreeNode> result = new ArrayList<CommentTreeNode>();
		parseCommentTree(comments, -1, parentDesc, 0, result);
		return result;
	}

	private static void parseCommentTree(List<Comment> comments, int parent, String parentDesc, int depth,
			List<CommentTreeNode> result) {
		for (Comment c : comments) {
			if (c.commentParent == parent) {
				CommentTreeNode node = new CommentTreeNode();
				node.commentId = c.commentId;
				node.postId = c.postId;
				node.userId = c.userId;
				UserInfo info = new UserInfo(c.userId);
				node.userDesc = info.getUserDesc();
				node.userAvatar = info.getAvatar();
				node.timePosted = c.commentTime;
				node.content = c.commentContent;
				node.depth = depth;
				node.parentDesc = parentDesc;

				result.add(node);
				parseCommentTree(comments, node.commentId, node.userDesc, depth + 1, result);
			}
		}
	}

	public static List<Post> getNews(int userId) {
		List<Post> result = new ArrayList<>();
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT `postId` FROM (SELECT `postId` FROM `post` WHERE `post`.`userId` = ? UNION SELECT `postId` FROM `post`, `friend` WHERE `post`.`userId` = `friend`.`userId` AND `friendId` = ?) AS `posts` ORDER BY `postId` DESC",
						Statement.RETURN_GENERATED_KEYS);) {
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			try (ResultSet resultSet = ps.executeQuery()) {
				while (resultSet.next())
					result.add(new Post(resultSet.getInt(1)));
			}
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
		return result;
	}

	public boolean like(User user) {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement(
						"SELECT * FROM `like` WHERE `postId` = ? AND `userId` = ?", Statement.RETURN_GENERATED_KEYS);) {
			ps.setInt(1, postId);
			ps.setInt(2, user.getUserId());
			try (ResultSet s = ps.executeQuery()) {
				if (s.first())
					return true;
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}

	public int getNumberLike() {
		try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
				PreparedStatement ps = dbconn.prepareStatement("SELECT COUNT(`userId`) FROM `like` WHERE `postId` = ?",
						Statement.RETURN_GENERATED_KEYS);) {
			ps.setInt(1, postId);
			try (ResultSet r = ps.executeQuery()) {
				r.first();
				return r.getInt(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}

	public void setLike(User user, boolean like) {
		if (like) {
			try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
					PreparedStatement ps = dbconn.prepareStatement(
							"INSERT INTO `like` (`postId`, `userId`) VALUES (?, ?)",
							Statement.RETURN_GENERATED_KEYS);) {
				ps.setInt(1, postId);
				ps.setInt(2, user.getUserId());
				ps.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException("", e);
			}
		} else {
			try (Connection dbconn = DatabaseConnector.GetDatabaseConnection();
					PreparedStatement ps = dbconn.prepareStatement(
							"DELETE FROM `like` WHERE `postId` = ? AND `userId` = ?",
							Statement.RETURN_GENERATED_KEYS);) {
				ps.setInt(1, postId);
				ps.setInt(2, user.getUserId());
				ps.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException("", e);
			}
		}

	}

}
