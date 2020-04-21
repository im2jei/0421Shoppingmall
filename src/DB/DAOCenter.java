package DB;

import java.net.Socket;
import java.util.ArrayList;

import Server.ServerCenter;
import Server.ServerChat;

public class DAOCenter implements DAOInterface {
	private String[] check;
	private static DAOCenter DAOcenter;
	private static MemberDAO mDao = MemberDAO.getInstance();
	private String notice = "";
	private ServerCenter sc = null;
	private String msg = "";
	private ServerChat chat = null;
	private int bl = 0;
	private boolean ox = true;
	private static ManagementDAO gDao = ManagementDAO.getInstance();
	private Socket withClient = null;
	private Socket withClient2 = null;

	private DAOCenter() {

	}

	public static DAOCenter getInstance() {
		if (DAOcenter == null) {
			DAOcenter = new DAOCenter();
		}
		return DAOcenter;
	}

	public void whichone(Object objectMember, Socket withClient, Socket withClient2) {
		 this.withClient = withClient;
		 this.withClient2 = withClient2;
		check = (String[]) objectMember;
		for (int i = 0; i < check.length; i++) {
			notice = check[check.length - 1];
			if (notice.equals("login")) { // 로그인 체크
				System.out.println("이건 로그인 : " + check[check.length - 1]);
				Select(check[0], check[1], notice, withClient);
				break;
			} else if (notice.equals("join")) { // 회원가입 체크
				System.out.println("이건회원가입 : " + check[check.length - 1]);
				Insert(objectMember, notice, withClient, withClient2);
				break;
			} else if (notice.equals("check")) { // 중복 체크
				System.out.println("이건중복체크지롱  : " + check[check.length - 1]);
				Select(check[0], check[1], notice, withClient);
				break;
			} else if (notice.equals("add")) {
				System.out.println("이건추가지롱  : " + check[check.length - 1]);
				Insert(objectMember, notice,withClient, withClient2);
				break;
			} else if (notice.equals("mod")) {
				System.out.println("이건수정이지롱  : " + check[check.length - 1]);
				Update(objectMember, notice, withClient2);
				break;
			} else if (notice.equals("del")) {
				System.out.println("이건 삭제이지롱  : " + check[check.length - 1]);
				Update(objectMember, notice, withClient2);
				break;
			}
		}
	}

	@Override
	public Boolean Insert(Object obj, String notice, Socket withClient, Socket withClient2) {
		this.withClient = withClient;
		 this.withClient2 = withClient2;
		switch (notice) {

		case "join":
			ox = mDao.InsertMember(obj);
			if (ox == true) {
				msg = "member/yes";
			} else {
				msg = "member/no";
			}
			System.out.println("DAO에서 메세지 확인 : " + msg);
			// chat.send(msg);
			sc.goSC(msg, withClient);
			break;
		case "add":
			ox = gDao.Insert(obj);
			if (ox == true) {
				msg = "goods/yes";
			} else {
				msg = "goods/no";
			}
			System.out.println("DAO에서 메세지 확인 : " + msg);
			// chat.send(msg);
			sc.goSC(msg, withClient2);
			break;
		case "cart":
			break;
		case "order":
			break;
		}
		return null;
	}

	@Override
	public int Select(String id, String pwd, String notice, Socket withClient) {
		this.withClient = withClient;
		sc = new ServerCenter();
		switch (notice) {

		case "login":
			bl = mDao.loginchk(id, pwd);
			if (bl == 1) {
				msg = "login/yes/1";
			} else if (bl == 5) {
				msg = "login/yes/5";
			} else if (bl == 10) {
				msg = "login/no";
			}
			System.out.println("DAO에서 메세지 확인 : " + msg);
			// chat.send(msg);
			sc.goSC(msg, withClient);
			break;
		case "check":
			bl = mDao.idchk(id);
			if (bl == 1) {
				msg = "check/no";
			} else if (bl == 5) {
				msg = "check/yes";
			}
			System.out.println("DAO에서 메세지 확인 : " + msg);
			// chat.send(msg,withClient);
			sc.goSC(msg, withClient);
			break;
		}
		return 300;
	}

	@Override
	public Boolean Edit(Object DTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean Delete(Object DTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean Update(Object obj, String notice, Socket withClient2) {
		this.withClient2 = withClient2;
		switch (notice) {

		case "mod":
			ox = gDao.Update(obj);
			if (ox == true) {
				msg = "mod/yes";
			} else {
				msg = "mod/no";
			}
			System.out.println("DAO에서 메세지 확인 : " + msg);
			sc.goSC(msg, withClient2);
			break;
		case "del":
			ox = gDao.delinfo(obj);
			if (ox == true) {
				msg = "del/yes";
			} else {
				msg = "del/no";
			}
			System.out.println("DAO에서 메세지 확인 : " + msg);
			sc.goSC(msg, withClient2);
			break;
		}
		return null;
	}

	public ArrayList<String[]> list(String msg, Socket withClient2) {
		this.withClient2=withClient2;
		ArrayList<String[]> gList = gDao.getList();
		sc.goSC2(gList, withClient2);
		return gList;

	}

}
