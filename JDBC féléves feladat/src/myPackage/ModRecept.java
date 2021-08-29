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

public class ModRecept extends JDialog {
	private JTextField etelneve;
	private JTextField jelleg;
	private JTextField elkeszitesiido;
	private JTextField utolsomodositas;
	DbMethods dbm = new DbMethods();
	private JTextField etelneve2;
	private JTextField jelleg2;
	private JTextField elkeszitesiido2;
	private JTextField utolsomodositas2;
	private JTextField sorszam;

	public ModRecept(JDialog d, String besorszam, String beetelneve, String bejelleg, String beelkeszitesiido,
			String beutolsomodositas) {

		super(d, "Recept módosítása", true);
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		setBounds(100, 100, 413, 235);
		getContentPane().setLayout(null);
		dbm.Connect();
		JLabel lblNewLabel = new JLabel("Utols\u00F3 m\u00F3dos\u00EDt\u00E1s:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 131, 119, 15);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("\u00C9tel neve:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 44, 119, 15);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Jellege:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(10, 71, 119, 15);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Elk\u00E9sz\u00EDt\u00E9si id\u0151:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(10, 100, 119, 15);
		getContentPane().add(lblNewLabel_4);

		JButton btnModosit = new JButton("M\u00F3dos\u00EDt");
		btnModosit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!utolsomodositas2.getText().isBlank()) {
						LocalDate.parse(utolsomodositas2.getText());
					}
					
					dbm.Connect();
					dbm.UpdateReceptek(RTF(sorszam), RTF2(etelneve2, etelneve), RTF2(jelleg2, jelleg),
							RTF2(elkeszitesiido2, elkeszitesiido), RTF2(utolsomodositas2, utolsomodositas));
				} catch (DateTimeParseException e2) {
					dbm.SM("Hibás dátumformátum: " + e2.getMessage() + ". \nA helyes dátumformátum: YYYY-MM-DD");
				}
			}
		});
		btnModosit.setBounds(154, 160, 89, 23);
		getContentPane().add(btnModosit);

		etelneve = new JTextField(beetelneve);
		etelneve.setEditable(false);
		etelneve.setColumns(10);
		etelneve.setBounds(122, 43, 119, 17);
		getContentPane().add(etelneve);

		jelleg = new JTextField(bejelleg);
		jelleg.setEditable(false);
		jelleg.setColumns(10);
		jelleg.setBounds(155, 69, 86, 20);
		getContentPane().add(jelleg);

		elkeszitesiido = new JTextField(beelkeszitesiido);
		elkeszitesiido.setEditable(false);
		elkeszitesiido.setColumns(10);
		elkeszitesiido.setBounds(155, 100, 30, 20);
		getContentPane().add(elkeszitesiido);

		utolsomodositas = new JTextField(beutolsomodositas);
		utolsomodositas.setEditable(false);
		utolsomodositas.setColumns(10);
		utolsomodositas.setBounds(155, 129, 86, 20);
		getContentPane().add(utolsomodositas);

		JLabel lblNewLabel_5 = new JLabel("perc");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(195, 102, 46, 14);
		getContentPane().add(lblNewLabel_5);

		etelneve2 = new JTextField();
		etelneve2.setColumns(10);
		etelneve2.setBounds(268, 43, 119, 17);
		getContentPane().add(etelneve2);

		jelleg2 = new JTextField();
		jelleg2.setColumns(10);
		jelleg2.setBounds(268, 69, 86, 20);
		getContentPane().add(jelleg2);

		elkeszitesiido2 = new JTextField();
		elkeszitesiido2.setColumns(10);
		elkeszitesiido2.setBounds(268, 100, 30, 20);
		getContentPane().add(elkeszitesiido2);

		JLabel lblNewLabel_5_1 = new JLabel("perc");
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5_1.setBounds(308, 102, 46, 14);
		getContentPane().add(lblNewLabel_5_1);

		utolsomodositas2 = new JTextField();
		utolsomodositas2.setColumns(10);
		utolsomodositas2.setBounds(268, 129, 86, 20);
		getContentPane().add(utolsomodositas2);

		sorszam = new JTextField(besorszam);
		sorszam.setEditable(false);
		sorszam.setBounds(180, 12, 61, 20);
		getContentPane().add(sorszam);
		sorszam.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("Sorsz\u00E1m:");
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
}
