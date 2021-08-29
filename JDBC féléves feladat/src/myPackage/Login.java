package myPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JPasswordField;

public class Login extends JFrame {
	private JTextField userTF;
	static DbMethods dbm = new DbMethods();
	private JPasswordField pswdTF;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		dbm.Reg();

		setBounds(100, 100, 359, 201);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("Bejelentkez\u00E9s");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(120, 11, 103, 23);
		getContentPane().add(lblNewLabel);

		userTF = new JTextField();
		userTF.setBounds(169, 62, 111, 20);
		getContentPane().add(userTF);
		userTF.setColumns(10);

		JButton btnLogin = new JButton("Bejelentkez\u00E9s");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nev = userTF.getText();
				char[] password = pswdTF.getPassword();
				String pswd = new String(password);

				if (!filledTF(userTF)) {
					dbm.SM("A felhasználónév mezõ üres!");
				} else if (!filledTF(pswdTF)) {
					dbm.SM("A jelszó mezõ üres!");
				} else {
					int pc = dbm.Identification(nev, pswd);
					if (pc == 1) {
						dbm.SM("Sikeres belépés!");
						new JTPHomeScreen().setVisible(true);
						dispose();
					} else {
						dbm.SM("Hibás felhasználónév vagy jelszó!");
					}
				}
			}
		});
		btnLogin.setBounds(159, 115, 121, 23);
		getContentPane().add(btnLogin);

		JLabel lblNewLabel_1 = new JLabel("Felhaszn\u00E1l\u00F3n\u00E9v:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(52, 64, 97, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Jelsz\u00F3:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(79, 89, 70, 14);
		getContentPane().add(lblNewLabel_2);

		JButton btnRegister = new JButton("Regisztr\u00E1ci\u00F3");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				NewUser nu = new NewUser(Login.this);
				nu.setVisible(true);

			}
		});
		btnRegister.setBounds(44, 115, 105, 23);
		getContentPane().add(btnRegister);

		JButton btnLista = new JButton("");
		btnLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.ReadUserData();
			}
		});
		
		//így lesz láthatatlan a gomb
		btnLista.setOpaque(false);
		btnLista.setContentAreaFilled(false);
		btnLista.setBorderPainted(false);
		
		btnLista.setBounds(10, 7, 70, 23);
		getContentPane().add(btnLista);
		
		pswdTF = new JPasswordField();
		pswdTF.setBounds(169, 87, 111, 20);
		getContentPane().add(pswdTF);
	}

	public boolean filledTF(JTextField jtf) {
		String s = RTF(jtf);
		if (s.length() > 0)
			return true;
		return false;
	}

	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
}
