package Server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ServerChat extends Thread {
	private Socket withClient = null;
	private Socket withClient2 = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private String id = null;
	private String[] check = null;
	private ServerCenter sc = null;
	private ServerChat ss = null;
	String msg = null;
	ArrayList<String[]> list = null;

	public ServerChat(Socket withClient, Socket withClient2, ServerCenter sc) {
		this.withClient = withClient;
		this.withClient2 = withClient2;
		this.sc = sc;
		ss = this;
	}

	@Override
	public void run() {
		streamSet();
		send(msg, withClient);
		recieve();
	}

	public void send(String msg, Socket withClient) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// while (true) {
					if (msg != null) {
						sendMsg = withClient.getOutputStream();
						System.out.println("여기는 서버챗인데 받은 메세지를 확인해볼것이야 : " + msg);
						sendMsg.write(msg.getBytes());
						System.out.println("서버에서 메세지를 보냈어요");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
	}

	public void send2(ArrayList<String[]> gList, Socket withClient2) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (gList != null) {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(baos);
						oos.writeObject(gList);

						byte[] bowl = baos.toByteArray();
						sendMsg = withClient2.getOutputStream();

						sendMsg.write(bowl);
						System.out.println("리스트 보내기 완료");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		}).start();

	}

	public void streamSet() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("메세지를 기다리는 중입니다");
					while (true) {
						reMsg = withClient.getInputStream();
						byte[] reBuffer = new byte[1024];
						reMsg.read(reBuffer);

						ByteArrayInputStream bais = new ByteArrayInputStream(reBuffer);

						ObjectInputStream ois = new ObjectInputStream(bais);

						Object o = ois.readObject();

						if (o != null) {
							check = (String[]) o;
							for (int i = 0; i < check.length; i++) {
								System.out.println(check[i]);
								id = check[0];
							}
							sc.select(check, withClient, withClient2);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
	}

	public void recieve() {
		this.withClient2 = withClient;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("메세지를 기다리는 중입니다");
					while (true) {
						reMsg = withClient2.getInputStream();
						byte[] reBuffer = new byte[100];
						reMsg.read(reBuffer);
						String msg = new String(reBuffer);
						msg = msg.trim();
						System.out.println("클라이언트에서 메세지를 받았어요." + msg);
						sc.select(msg, withClient, withClient2);

					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
	}
}
