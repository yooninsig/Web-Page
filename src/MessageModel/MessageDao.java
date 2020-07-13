package MessageModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MessageDao {
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;

	// select - close
	public void Close() throws SQLException {
		if (conn != null)
			conn.close();
		if (pst != null)
			pst.close();
		if (rs != null)
			rs.close();
	}

	public MessageDao() {
		try {
			String url = "jdbc:mysql://localhost:3306/matchdb?serverTimezone=UTC";
			String uid = "root";
			String upw = "785200a";

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, uid, upw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ġ Ȯ�� or ������ ���� �߼�.
	public void sendMessage(String userId, int boardId, String content) {
		String sql = "insert into messagetable(userId, boardId, content) values(?,?,?)";

		try {
			pst = conn.prepareStatement(sql);

			pst.setString(1, userId);
			pst.setInt(2, boardId);
			pst.setString(3, content);

			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// ����� session���� id �����ͼ� ��� �޼��� ��ȯ.
	public ArrayList<String> allMessage(String userId) {
		ArrayList<String> message = new ArrayList<String>();

		String sql = "select * from messagetable where userid=? order by receivedate desc";

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, userId);

			rs = pst.executeQuery();

			while (rs.next()) {
				String boardid = rs.getString("boardId");
				String content = rs.getString("content");
				String mess = boardid + "�� �濡 " + content;

				message.add(mess);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return message;
	}

}