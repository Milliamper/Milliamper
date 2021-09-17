package myPackage;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;

public class ModEtterem extends JDialog {
	private JTextField nev;
	private JTextField telefonszam;
	private JTextField elsonyitas;
	private JTextField specialitas;
	DbMethods dbm = new DbMethods();
	private JTextField nev2;
	private JTextField telefonszam2;
	private JTextField elsonyitas2;
	private JTextField specialitas2;
	private JTextField azonosito;

	public ModEtterem(JDialog d, String beazon, String benev, String betel, String beenyitas, String bespec) {

		super(d, "Étterem módosítása", true);
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		setBounds(100, 100, 397, 229);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		dbm.Connect();
		JLabel lblNewLabel = new JLabel("Specialit\u00E1s:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 131, 119, 15);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("N\u00E9v:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 44, 119, 15);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Telefonsz\u00E1m:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(10, 71, 119, 15);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Els\u0151 nyit\u00E1s:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(10, 100, 119, 15);
		getContentPane().add(lblNewLabel_4);

		JButton btnModosit = new JButton("M\u00F3dos\u00EDt");
		btnModosit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isFormatCorrect(RTF(telefonszam2)) && !telefonszam2.getText().isBlank()) {
					dbm.SM("Nem a megfelelõ formátumban adtad meg a telefonszámot!\nA megfelelõ formátum: XX-XXX-XX-XX vagy XX-XXX-XXX");
				} else {
					try {
						if (!elsonyitas2.getText().isBlank()) {
							LocalDate.parse(elsonyitas2.getText());
						}
						dbm.Connect();
						dbm.UpdateEtterem(RTF(azonosito), RTF2(nev2, nev), RTF2(telefonszam2, telefonszam),
								RTF2(elsonyitas2, elsonyitas), RTF2(specialitas2, specialitas));
					} catch (DateTimeParseException e2) {
						dbm.SM("Hibás dátumformátum: " + e2.getMessage() + ". \nA helyes dátumformátum: YYYY-MM-DD");

					}
				}
			}
		});
		btnModosit.setBounds(146, 156, 89, 23);
		getContentPane().add(btnModosit);

		nev = new JTextField(benev);
		nev.setEditable(false);
		nev.setColumns(10);
		nev.setBounds(122, 43, 119, 17);
		getContentPane().add(nev);

		telefonszam = new JTextField(betel);
		telefonszam.setEditable(false);
		telefonszam.setColumns(10);
		telefonszam.setBounds(122, 69, 119, 20);
		getContentPane().add(telefonszam);

		elsonyitas = new JTextField(beenyitas);
		elsonyitas.setEditable(false);
		elsonyitas.setColumns(10);
		elsonyitas.setBounds(122, 100, 119, 20);
		getContentPane().add(elsonyitas);

		specialitas = new JTextField(bespec);
		specialitas.setEditable(false);
		specialitas.setColumns(10);
		specialitas.setBounds(122, 129, 119, 20);
		getContentPane().add(specialitas);

		nev2 = new JTextField();
		nev2.setColumns(10);
		nev2.setBounds(251, 43, 119, 17);
		getContentPane().add(nev2);

		telefonszam2 = new JTextField();
		telefonszam2.setColumns(10);
		telefonszam2.setBounds(251, 69, 119, 20);
		getContentPane().add(telefonszam2);

		elsonyitas2 = new JTextField();
		elsonyitas2.setColumns(10);
		elsonyitas2.setBounds(251, 98, 119, 20);
		getContentPane().add(elsonyitas2);

		specialitas2 = new JTextField();
		specialitas2.setColumns(10);
		specialitas2.setBounds(251, 129, 119, 20);
		getContentPane().add(specialitas2);

		azonosito = new JTextField(beazon);
		azonosito.setEditable(false);
		azonosito.setBounds(122, 12, 119, 20);
		getContentPane().add(azonosito);
		azonosito.setColumns(10);

		JLabel lblNewLabel_2_1 = new JLabel("Azonosit\u00F3:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_1.setBounds(10, 18, 119, 15);
		getContentPane().add(lblNewLabel_2_1);

	}

	public String RTF2(JTextField jtf2, JTextField jtf) {
		if (jtf2.getText().length() == 0)
			return jtf.getText();
		else
			return jtf2.getText();
	}

	public String RTF(JTextField jtf) {
		return jtf.getText();
	}

	public void SM(String msg) {
		JOptionPane.showMessageDialog(null, msg, "ABkezelõ üzenet", 2);
	}

	public static boolean isFormatCorrect(String phoneNumber) {

		if (phoneNumber.matches("\\d{2}-\\d{3}-\\d{2}-\\d{2}")) {
			return true;
		} else if (phoneNumber.matches("\\d{2}-\\d{3}-\\d{3}")) {
			return true;
		} else {
			return false;
		}
	}
}
