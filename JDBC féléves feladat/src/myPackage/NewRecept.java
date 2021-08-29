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

public class NewRecept extends JDialog {
	private JTextField etelneve;
	private JTextField jelleg;
	private JTextField elkeszitesiido;
	private JTextField utolsomodositas;
	DbMethods dbm = new DbMethods();

	public NewRecept(JFrame f) {

		super(f, "Recept beszúrása", true);
		//középen megjelenés
		setLocationRelativeTo(null);
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		setBounds(100, 100, 268, 185);
		getContentPane().setLayout(null);
		dbm.Connect();
		JLabel lblNewLabel = new JLabel("Utols\u00F3 m\u00F3dos\u00EDt\u00E1s:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 89, 119, 15);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("\u00C9tel neve:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 11, 119, 15);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Jellege:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(10, 37, 119, 15);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Elk\u00E9sz\u00EDt\u00E9si id\u0151:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(10, 63, 119, 15);
		getContentPane().add(lblNewLabel_4);

		JButton btnBeszur = new JButton("Besz\u00FAr");
		btnBeszur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<Receptek> recept = new ArrayList<Receptek>();

				if (!filledTF(etelneve)) {
					dbm.SM("A név mezõ üres!");
				} else if (!filledTF(jelleg)) {
					dbm.SM("A jelleg mezõ üres!");
				} else if (!filledTF(elkeszitesiido)) {
					dbm.SM("Az elkészítési idõ mezõ üres!");
				} else if (!filledTF(utolsomodositas)) {
					dbm.SM("Az utolsó módosítás mezõ üres!");
				} else if (!goodInt(elkeszitesiido)) {
					dbm.SM("Csak számot adhatsz meg!");
				}
				else {
					try {
						LocalDate.parse(utolsomodositas.getText());
						recept.add(new Receptek(etelneve.getText(), jelleg.getText(), Integer.valueOf(elkeszitesiido.getText()),
								utolsomodositas.getText()));

					} catch (DateTimeParseException | ClassCastException e3) {
						dbm.SM("Hibás dátumformátum: " + e3.getMessage() + ". \nA helyes dátumformátum: YYYY-MM-DD");
					}
				}

				// ez létrehoz egy ett nevû tömböt és belemásolja az etterem lista adatait, és
				// akkora lesz mint amennyi elem van az etterem listában
				// így lesz lényegében egy olyan tömböm aminek dinamikusan változik a mérete
				Receptek[] rck = recept.toArray(new Receptek[recept.size()]);
				dbm.InsertWithPreparedStatementReceptek(rck);
				
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

		etelneve = new JTextField();
		etelneve.setColumns(10);
		etelneve.setBounds(122, 10, 119, 17);
		getContentPane().add(etelneve);

		jelleg = new JTextField();
		jelleg.setColumns(10);
		jelleg.setBounds(122, 35, 86, 20);
		getContentPane().add(jelleg);

		elkeszitesiido = new JTextField();
		elkeszitesiido.setColumns(10);
		elkeszitesiido.setBounds(122, 63, 30, 20);
		getContentPane().add(elkeszitesiido);

		utolsomodositas = new JTextField();
		utolsomodositas.setColumns(10);
		utolsomodositas.setBounds(122, 87, 86, 20);
		getContentPane().add(utolsomodositas);

		JLabel lblNewLabel_5 = new JLabel("perc");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(162, 65, 46, 14);
		getContentPane().add(lblNewLabel_5);

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

	public boolean goodInt(JTextField jtf) {
		String s = RTF(jtf);
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
