package Server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Manager.Setting;

public class SMain {

	public static void main(String[] args) throws Exception {
		ServerSocket serverS = null;
		ServerSocket serverS2 = null;
		Socket withClient = null;
		Socket withClient2 = null;
		serverS = new ServerSocket();
		serverS2 = new ServerSocket();
		serverS.bind(new InetSocketAddress("10.0.0.67", 7777));
		serverS2.bind(new InetSocketAddress("10.0.0.67", 8888));

		ArrayList<Socket> cList = new ArrayList<>();
		ServerCenter sc = new ServerCenter();
		while (true) {
			System.out.println("서버 대기중");
			withClient = serverS.accept();
			withClient2 = serverS2.accept();
			cList.add(withClient);
			cList.add(withClient2);
			System.out.println(cList);
			System.out.println(withClient.getInetAddress() + "님이 접속함.");
			ServerChat s = new ServerChat(withClient, withClient2, sc);
			s.start();

		}
	}

}
