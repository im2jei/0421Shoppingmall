package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Manager.Setting;
import Manager.Shoppingmall;

public class Login extends JFrame implements ActionListener, Serializable {
	private JPanel nP, cP, sP, eP;
	private JLabel idLabel, pwLabel, joinlabel;
	private JTextField idField, pwdField, loginField;
	private JButton loginBtn, joinBtn;
	private Socket client = null;
	private static MsCenter ms=MsCenter.getInstance();
	private static Socket withClient = null;
	private static Socket withClient2 = null;
	private String msg;
	private Setting sett = null;
	private Shoppingmall shop = null;
	private static Login login;
	private static ClientChat ch = null;

	Login(ClientChat ch) {
		super("�α���");
		this.ch = ch;
		createpanel();
		setClose();
	}

//	public Socket getSockt1() {
//		return withClient;
//	}
//	
//	public Socket getSockt2() {
//		return withClient2;
//	}

	public static Login getInstance() {
		if (login == null) {
			login = new Login(ch);
		}
		return login;
	}

	private void createpanel() {
		this.setLayout(new BorderLayout());
		nP = new JPanel();
		nP.setBorder(new EmptyBorder(10, 10, 0, 10));
		idLabel = new JLabel("ID");
		nP.add(idLabel);

		// �ؽ�Ʈ �ʵ� �����
		idField = new JTextField(15);
		nP.add(idField);

		// center �г� �����
		cP = new JPanel();
		pwLabel = new JLabel("�� ȣ");
		pwdField = new JPasswordField(15);

		cP.add(pwLabel);
		cP.add(pwdField);
		// south �г� �����
		sP = new JPanel();
		loginBtn = new JButton("�α���");
		sP.add(loginBtn);

		eP = new JPanel();
		joinBtn = new JButton("ȸ������");
		sP.add(joinBtn);
		loginBtn.addActionListener(this);
		joinBtn.addActionListener(this);

		// �г� �����ӿ� �ֱ� ��ġ�� ����"".
		this.add(nP, "North");
		this.add(cP, "Center");
		this.add(sP, "South");
		this.add(eP, "East");

		// �� ����
		// this.setBackground(Color,blue);

		// ������ �����ֱ�
		this.setBounds(400, 500, 300, 200);

	}

	public void setClose() {
		// this.setVisible(false);
		// �ڵ����� �����Ը���
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		// true�� ȭ�鿡�� ��Ÿ���� false�� ȭ�鿡�� �������
		this.setVisible(true);
	}

	private void loginchk(String msg) {
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] check = { idField.getText(), pwdField.getText(), "login" };
					ms.allMsg(check);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		loginchk(msg);
		gosignup();
	}

	private void gosignup() {
		joinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Signup(ch);
			}

		});
	}

	public int loginresult(String msg, Socket withServer) {
		if (msg.contains("login/yes/1")) {
			JOptionPane.showMessageDialog(null, "�α��� �Ϸ�");
			shop.init();
			System.out.println("���θ����");
			dispose();
			return 1;
		} else if (msg.contains("login/yes/5")) {
			JOptionPane.showMessageDialog(null, "�α��� �Ϸ�");
			sett = new Setting(withServer);
			sett.start();
			System.out.println("���ð��");
			dispose();
			return 5;
		} else if (msg.contains("login/no")) {
			JOptionPane.showMessageDialog(null, "�������� �ʴ� ���̵�ų� ��й�ȣ�� ���� �ʽ��ϴ�.");
			idField.setText("");
			pwdField.setText("");
			dispose();
			return 10;
		}
		return 0;
	}

}
