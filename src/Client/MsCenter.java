package Client;

import java.net.Socket;
import java.util.ArrayList;

import Manager.Setting;

public class MsCenter {
	private String msg = null;
//	private Login login = null;
//	private Signup join = null;
	private Login login = Login.getInstance();
	private Signup join = Signup.getInstance();
	private static ClientChat ch = null;
	private static Socket withClient = null;
	private static Socket withClient2 = null;
	private static MsCenter ms;
	Setting sett = null;

	public MsCenter(ClientChat ch) {
		this.ch = ch;
		new Login(ch);
	}

	public static MsCenter getInstance() {
		if (ms == null) {
			ms = new MsCenter(ch);
		}
		return ms;
	}

	public void checkMsg(String msg) {
		if (msg.contains("member")) {
			join.membercheck(msg);
		} else if (msg.contains("login")) {
			login.loginresult(msg, withClient);
		} else if (msg.contains("check")) {
			join.idchk(msg);
		}

	}

	public String[] allMsg(String[] in) {
		for (int i = 0; i < in.length; i++) {
			System.out.println("리턴값 확인중" + in[i]);
		}
		ch.streamSet(in);
		return in;

	}

	public ArrayList<String[]> list(String msg, Socket withClient2) {
		MsCenter.withClient2 = withClient2;
		System.out.println(msg);
		ch = new ClientChat(withClient, withClient2);
		ch.send(msg, withClient2);
		return null;
	}

	public String[] back(Socket withClient2, String[] strings) {
		MsCenter.withClient2 = withClient2;
		System.out.println("mscenter왔다");
		Setting sett = new Setting(withClient2);
		sett.init(strings, withClient2);
		System.out.println("init으로 간다");
		return strings;

	}

}
