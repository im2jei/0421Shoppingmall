package Client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClientChat {
	private Socket withServer = null;
	private Socket withServer2 = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	String nnn = "";
	Scanner in = new Scanner(System.in);
	String msg = null;
	ArrayList<String[]> list;
	private static MsCenter ms=MsCenter.getInstance();

	
	
	public ClientChat(Socket withServer, Socket withServer2) {
		this.withServer = withServer;
		this.withServer2 = withServer2;
		
		receive();
		receive2();
		
	}
	public void start() {
		new MsCenter(this);
	}


	public void receive() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("receive start~~");

					while (true) {
						reMsg = withServer.getInputStream();
						byte[] reBuffer = new byte[1024];
						reMsg.read(reBuffer);
						msg = new String(reBuffer);
						if (msg != null) {
							msg = msg.trim();
							System.out.println("클라이언트에서 메세지를 받았어요." + msg);

							gotoCenter(msg);
						}
					}
				} catch (Exception e) {
					System.out.println("client send end !!!");
					return;
				}
			}
		}).start();
	}

	public void receive2() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("receive start~~");

					while (true) {
						System.out.println("working?");
						reMsg = withServer2.getInputStream();
						byte[] reBuffer = new byte[1024];
						reMsg.read(reBuffer);

						ByteArrayInputStream bais = new ByteArrayInputStream(reBuffer);

						ObjectInputStream ois = new ObjectInputStream(bais);
						System.out.println("mid");
						Object o = ois.readObject();

						if (o != null) {
							String[] check = (String[]) o;
							for (int i = 0; i < check.length; i++) {
								System.out.println(check[i]);
								gotoCenter2(check);

							}
						}
					}
				} catch (Exception e) {
					System.out.println("client send end !!!");
					return;
				}
			}
		}).start();
	}

	private void gotoCenter(String msg) {
		ms.checkMsg(msg);
	}

	private void gotoCenter2(String[] strings) {
		ms.back(withServer2, strings);
		System.out.println("mscenter로 이동");
	}

	public void streamSet(String[] check) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (check != null) {

						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(baos);
						oos.writeObject(check);

						byte[] bowl = baos.toByteArray();

						sendMsg = withServer.getOutputStream();

						sendMsg.write(bowl);
						System.out.println("보내기 완료");
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}

	public void send(String msg, Socket withServer2) {
		this.withServer2 = withServer2;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("여기는 클라이언트챗이다오바 : " + msg);
					sendMsg = withServer2.getOutputStream();
					sendMsg.write(msg.getBytes());
					System.out.println("리스트 달라는 메세지 보내기 완료");
				} catch (Exception e) {
				}
			}
		}).start();
	}

}
