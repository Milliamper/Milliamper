package myPackage;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewUser extends JDialog {
	private JTextField newuserTF;
	private JTextField newpswdTF;
	private DbMethods dbm = new DbMethods();

	
	public NewUser(JFrame f) {
		super(f, "Regisztráció", true);
		setBounds(100, 100, 383, 186);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Regisztr\u00E1ci\u00F3");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(139, 11, 89, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u00DAj felhaszn\u00E1l\u00F3n\u00E9v:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(62, 49, 105, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("\u00DAj jelsz\u00F3:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(86, 74, 81, 14);
		getContentPane().add(lblNewLabel_1_1);

		JButton btnReg = new JButton("Regisztr\u00E1ci\u00F3");
		btnReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!filledTF(newuserTF)) {
					dbm.SM("A felhasználónév mezõ üres!");
				} else if (!filledTF(newpswdTF)) {
					dbm.SM("A jelszó mezõ üres!");
				} else {
					dbm.userRegister(newuserTF.getText(), newpswdTF.getText());
					dispose();
				}
			}
		});
		btnReg.setBounds(123, 110, 120, 23);
		getContentPane().add(btnReg);

		newuserTF = new JTextField();
		newuserTF.setBounds(177, 47, 114, 20);
		getContentPane().add(newuserTF);
		newuserTF.setColumns(10);

		newpswdTF = new JTextField();
		newpswdTF.setBounds(177, 72, 114, 20);
		getContentPane().add(newpswdTF);
		newpswdTF.setColumns(10);

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
