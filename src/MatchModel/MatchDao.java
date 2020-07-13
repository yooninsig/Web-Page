package MatchModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MatchDao {
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

	public MatchDao() {
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

	// ��ġ Ȯ���̵Ǹ� �����ִ� �޼ҵ�
	public void deleteRoom(int boardId) {
		String sql = "delete from matchtable where boardid = ?";

		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, boardId);

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

	// ���̵� �޾Ƽ� �߰� �濡 �ޱ�
	public void insertMatch(int boardId, String userId) {
		String sql = "insert into matchtable (boardId, userId) values(?,?)";

		try {
			pst = conn.prepareStatement(sql);

			pst.setInt(1, boardId);
			pst.setString(2, userId);

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

	// �� ���̵� �޾Ƽ� �濡�ִ� ���� ������ ���� ��ȯ
	public ArrayList<String> userList(int boardId) {
		ArrayList<String> user = new ArrayList<String>();
		String sql = "select userId from matchtable where boardId = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, boardId);

			rs = pst.executeQuery();
			while (rs.next()) {
				String temp = rs.getString("userId");
				user.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	// ���� ���̵� �޾Ƽ� ������ �������� �� ��ü�� ��ȯ
	public ArrayList<Integer> boardList(String userId) {
		ArrayList<Integer> board = new ArrayList<Integer>();
		String sql = "select boardId from matchtable where userId = ? order by boardId desc";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, userId);

			rs = pst.executeQuery();
			while (rs.next()) {
				int temp = rs.getInt("boardId");
				board.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return board;
	}

	public boolean isLast(String userId, int pageSeq) {
		String sql = "select boardId from matchTable where userId = ? order by boardId desc limit ?, 1";

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, userId);
			pst.setInt(2, pageSeq + 1);

			rs = pst.executeQuery();
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public int getBoard(String userId, int pageSeq) {
		String sql = "select boardId from matchTable where userId = ? order by boardId desc limit ?, 1";

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, userId);
			pst.setInt(2, pageSeq);

			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getInt("boardId");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public void outMatch(int boardId, String userId) {
		String sql = "delete from matchtable where boardId=? and userId=?";

		try {
			pst = conn.prepareStatement(sql);

			pst.setInt(1, boardId);
			pst.setString(2, userId);

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

	public int checkOverlap(int boardId, String userId) {
		String sql = "select userId from matchtable where boardId=? and userId=?";

		try {
			pst = conn.prepareStatement(sql);

			pst.setInt(1, boardId);
			pst.setString(2, userId);
			rs = pst.executeQuery();

			if (rs.next()) {
				return 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

}