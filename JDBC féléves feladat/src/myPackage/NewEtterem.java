package myPackage;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class NewEtterem extends JDialog {
	private JTextField nev;
	private JTextField telefonszam;
	private JTextField elsonyitas;
	private JTextField specialitas;
	DbMethods dbm = new DbMethods();

	public NewEtterem(JFrame f) {

		super(f, "Étterem beszúrása", true);
		//középen megjelenés
		setLocationRelativeTo(null);
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		setBounds(100, 100, 268, 185);
		getContentPane().setLayout(null);
		dbm.Connect();
		JLabel lblNewLabel = new JLabel("Specialit\u00E1s: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 89, 119, 15);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("N\u00E9v:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 11, 119, 15);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Telefonsz\u00E1m:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(10, 37, 119, 15);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Els\u0151 nyit\u00E1s:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(10, 63, 119, 15);
		getContentPane().add(lblNewLabel_4);

		JButton btnBeszur = new JButton("Besz\u00FAr");
		btnBeszur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<Etterem> etterem = new ArrayList<Etterem>();

				if (!filledTF(nev)) {
					dbm.SM("A név mezõ üres!");
				} else if (!filledTF(telefonszam)) {
					dbm.SM("A telefonszám mezõ üres!");
				} else if (!filledTF(elsonyitas)) {
					dbm.SM("Az elkészítési idõ mezõ üres!");
				} else if (!filledTF(specialitas)) {
					dbm.SM("Az utolsó módosítás mezõ üres!");
				}else if(!isFormatCorrect(RTF(telefonszam))) {
					dbm.SM("Nem a megfelelõ formátumban adtad meg a telefonszámot!\nA megfelelõ formátum: XX-XXX-XX-XX vagy XX-XXX-XXX");
				} else {
					try {
						LocalDate.parse(elsonyitas.getText());
						etterem.add(new Etterem(nev.getText(), telefonszam.getText(), elsonyitas.getText(),
								specialitas.getText()));

					} catch (DateTimeParseException e3) {
						dbm.SM("Hibás dátumformátum: " + e3.getMessage() + ". \nA helyes dátumformátum: YYYY-MM-DD");
					}
				}

				// ez létrehoz egy ett nevû tömböt és belemásolja az etterem lista adatait, és
				// akkora lesz mint amennyi elem van az etterem listában
				// így lényegében lesz egy olyan tömböm aminek dinamikusan változik a mérete
				Etterem[] ett = etterem.toArray(new Etterem[etterem.size()]);
				dbm.InsertWithPreparedStatementEtterem(ett);
			}
		});
		btnBeszur.setBounds(10, 115, 89, 23);
		getContentPane().add(btnBeszur);

		JButton btnBezar = new JButton("Bez\u00E1r");
		btnBezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBezar.setBounds(152, 115, 89, 23);
		getContentPane().add(btnBezar);

		nev = new JTextField();
		nev.setColumns(10);
		nev.setBounds(122, 10, 119, 17);
		getContentPane().add(nev);

		telefonszam = new JTextField();
		telefonszam.setColumns(10);
		telefonszam.setBounds(122, 35, 119, 20);
		getContentPane().add(telefonszam);

		elsonyitas = new JTextField();
		elsonyitas.setColumns(10);
		elsonyitas.setBounds(122, 63, 119, 20);
		getContentPane().add(elsonyitas);

		specialitas = new JTextField();
		specialitas.setColumns(10);
		specialitas.setBounds(122, 87, 119, 20);
		getContentPane().add(specialitas);

	}

	public String RTF(JTextField jtf) {
		return jtf.getText();
	}

	public boolean filledTF(JTextField jtf) {
		String s = RTF(jtf);
		if (s.length() > 0)
			return true;
		return false;
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
