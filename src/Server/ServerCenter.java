package Server;

import java.net.Socket;
import java.util.ArrayList;

import DB.DAOCenter;

public class ServerCenter {
	private ArrayList<ServerChat> sList = new ArrayList<>();
	private DAOCenter dc = DAOCenter.getInstance();
	private String[] check = null;
	private ServerChat chat = null;
	private Socket withClient = null;
	private Socket withClient2 = null;

	public ServerCenter() {

	}


	public void addServerChat(ServerChat s) {
		this.sList.add(s);
	}

	public void select(Object objectMember, Socket withClient, Socket withClient2) {
		this.withClient = withClient;
		this.withClient2 = withClient2;
		dc.whichone(objectMember, withClient, withClient2);
	}

	public void select(String msg, Socket withClient, Socket withClient2) {
		this.withClient = withClient;
		this.withClient2 = withClient2;
		dc.list(msg,withClient2);
	}

	public void goSC(String msg, Socket withClient) {
		this.withClient = withClient;
		System.out.println("여기는 서버센터야 오바 : " + msg);
		chat = new ServerChat(withClient, withClient2, null);
		chat.send(msg, withClient);
	}

	public void goSC2(ArrayList<String[]> list, Socket withClient2) {
		this.withClient2=withClient2;
		System.out.println("여기는 서버센터야 오바 리스트는 넘어왔니?? : " + list);
		chat = new ServerChat(withClient, withClient2, null);
		chat.send2(list, withClient2);
	}

}
