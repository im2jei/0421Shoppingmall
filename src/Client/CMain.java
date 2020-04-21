package Client;

import java.net.Socket;

public class CMain {

	public static void main(String[] args) throws Exception {
		Socket withServer = null;
		Socket withServer2 = null;
		withServer = new Socket("10.0.0.67", 7777);
		withServer2 = new Socket("10.0.0.67",8888);
		System.out.println("아이피랑 포트넘버 생성");
//		new Login(withServer,withServer2);
		new ClientChat(withServer, withServer2);
	}

}
