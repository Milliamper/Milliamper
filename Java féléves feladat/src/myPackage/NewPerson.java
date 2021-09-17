package myPackage;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.awt.event.ActionEvent;

public class NewPerson extends JDialog {
	private JTextField kod;
	private JTextField nev;
	private JTextField szid;
	private JTextField lak;
	private JTextField begin;
	private JTextField end;
	private Checker c = new Checker();

	public NewPerson(String selected) {
		setBounds(100, 100, 251, 242);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("K\u00F3d:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 11, 117, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNv = new JLabel("N\u00E9v:");
		lblNv.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNv.setBounds(10, 36, 117, 14);
		getContentPane().add(lblNv);

		JLabel lblNewLabel_2 = new JLabel("Sz\u00FClet\u00E9si id\u0151:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 61, 117, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblLakhely = new JLabel("Lakhely:");
		lblLakhely.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLakhely.setBounds(10, 86, 117, 14);
		getContentPane().add(lblLakhely);

		JLabel lblKezdsiid = new JLabel("Kezd\u00E9si id\u0151:");
		lblKezdsiid.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblKezdsiid.setBounds(10, 111, 117, 14);
		getContentPane().add(lblKezdsiid);

		JLabel lblBefejezsiId = new JLabel("Befejez\u00E9si id\u0151:");
		lblBefejezsiId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBefejezsiId.setBounds(10, 136, 117, 14);
		getContentPane().add(lblBefejezsiId);

		kod = new JTextField();
		kod.setBounds(136, 9, 86, 20);
		getContentPane().add(kod);
		kod.setColumns(10);

		nev = new JTextField();
		nev.setColumns(10);
		nev.setBounds(136, 34, 86, 20);
		getContentPane().add(nev);

		szid = new JTextField();
		szid.setColumns(10);
		szid.setBounds(136, 59, 86, 20);
		getContentPane().add(szid);

		lak = new JTextField();
		lak.setColumns(10);
		lak.setBounds(136, 84, 86, 20);
		getContentPane().add(lak);

		begin = new JTextField();
		begin.setColumns(10);
		begin.setBounds(136, 109, 86, 20);
		getContentPane().add(begin);

		end = new JTextField();

		end.setColumns(10);
		end.setBounds(136, 134, 86, 20);
		getContentPane().add(end);

		JButton btnBeszur = new JButton("Besz\u00FAr");
		btnBeszur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					LocalTime kezdes = LocalTime.parse(begin.getText());
					LocalTime vegzes = LocalTime.parse(begin.getText());
					if (c.goodInt(kod, "Kód"))
						if (c.filled(nev, "Név"))
							if (c.goodDate(szid, "Születési idõ"))
								if (c.filled(lak, "Lakhely"))
									if (c.filled(begin, "Belépés idõpontja"))
										if (c.filled(end, "Kilépés idõpontja"))

											if (selected.equals(".csv")) {
												FileManager.Insert(RTF(kod), RTF(nev), RTF(szid), RTF(lak), RTF(begin),
														RTF(end));
											} else if (selected.equals(".txt")) {
												FileManager.InsertTxt(RTF(kod), RTF(nev), RTF(szid), RTF(lak),
														RTF(begin), RTF(end));
											} else if (selected.equals("Mindkettõ")) {
												FileManager.InsertTxt(RTF(kod), RTF(nev), RTF(szid), RTF(lak),
														RTF(begin), RTF(end));
												FileManager.Insert(RTF(kod), RTF(nev), RTF(szid), RTF(lak), RTF(begin),
														RTF(end));

											} else {
												c.SM("Válassz fájltípust", 1);
											}
				} catch (Exception e2) {
					c.SM(e2.getMessage(), 1);
				}

			}
		});
		btnBeszur.setBounds(73, 168, 89, 23);
		getContentPane().add(btnBeszur);

	}

	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
}
