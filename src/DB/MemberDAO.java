package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberDAO {
	private static Connect c = Connect.getInstance();
	private Connection conn = c.connect();
	private Statement stmt;
	private ResultSet rs;
	private MemberDTO member = null;
	private static MemberDAO DAOobj;

	private MemberDAO() {
	}

	public static MemberDAO getInstance() {
		if (DAOobj == null) {
			DAOobj = new MemberDAO();
		}
		return DAOobj;
	}

	public boolean InsertMember(Object oj) {
		boolean result = false;
		String[] check = (String[]) oj;
		member = new MemberDTO();
		for (int i = 0; i < check.length; i++) {
			member.setId(check[0]);
			member.setName(check[1]);
			member.setPwd(check[2]);
			member.setAdr(check[3]);
			member.setCell(check[4]);
		}
		c.orclelode();
		try {
			String sql = "insert into member values(?,?,?,?,?,1)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getId());
			psmt.setString(2, member.getName());
			psmt.setString(3, member.getPwd());
			psmt.setString(4, member.getAdr());
			psmt.setString(5, member.getCell());
			int r = psmt.executeUpdate();

			if (r > 0) {
				result = true;
			}
			psmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int loginchk(String id, String pwd) {
		c.orclelode();
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * from member where id='" + id + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				MemberDTO member = new MemberDTO();
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setPwd(rs.getString("pwd"));
				member.setAdr(rs.getString("adr"));
				member.setCell(rs.getString("cell"));
				member.setLv(rs.getInt("lv"));
				int num = checkequal(member, id, pwd);
				System.out.println("여기는memdao : " + id + pwd);
				if (num == 1) {
					return 1;
				} else if (num == 5) {
					return 5;
				} else if (num == 10) {
					return 10;
				}
			} else {
				System.out.println("rs가 널인겨");
				return 10;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 55;

	}

	private int checkequal(MemberDTO member, String id, String pwd) {
		this.member = member;
		if (id.equals(member.getId()) && pwd.equals(member.getPwd())) {
			if (member.getLv() == 1) {
				System.out.println("쇼핑몰창 뜨게하기");
				return 1;
			} else if (member.getLv() == 5) {
				System.out.println("관리자창 뜨게 하기 관리자 객체를 관리자의 창으로 보내깅");
				return 5;
			}
		} else {
			System.out.println("존재하지 않는 아이디이거나 패스워드가 맞지 않습니다.");
			return 10;
		}
		return 55;
	}

	public int idchk(String id) {
		c.orclelode();
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * from member where id='" + id + "'";
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				return 1;
			} else {
				return 5;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 55;
	}

}