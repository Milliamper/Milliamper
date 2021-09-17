package myPackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class JTPHomeScreen extends JFrame {

	private JPanel contentPane;
	DbMethods dbm = new DbMethods();
	private ReceptekTM rtm;
	private EtteremTM etm;
	private String selected = "V�lassz!";
	private String selectedRCK = "V�lassz!";
	private String selectedETT = "V�lassz!";
	private JTextField fromTF;
	private JTextField toTF;
	private JTextField fromTFett;
	private JTextField toTFett;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					JTPHomeScreen frame = new JTPHomeScreen();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public JTPHomeScreen() {
		dbm.Reg();
		dbm.Connect();

//		Term�kek t�bla l�trehoz�sa
		dbm.CommandExec("create table if not exists Receptek(sorszam Integer Not null Primary key Autoincrement, "
				+ "nev varchar(30), " + "jelleg varchar(30), " + "elkeszitesi_ido Integer, "
				+ "utolso_modositas DATE);");

//		�tterem t�bla l�trehoz�sa
		dbm.CommandExec("create table if not exists Etterem(azonosito Integer Not null Primary key Autoincrement, "
				+ "nev varchar(30), " + "telefonszam varchar(30), " + "elso_nyitas DATE, " + "specialitas varchar(50), "
				+ "Foreign key(azonosito) References Receptek(sorszam));");

//		//User t�bla l�trehoz�sa
		dbm.CommandExec("create table if not exists user(name varchar(30), password varchar(30));");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 315);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Receptek", null, panel, null);
		panel.setLayout(null);

		JButton btnLista = new JButton("Receptek list\u00E1z\u00E1sa");
		btnLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtm = dbm.ReadReceptekData();
				ReceptekList rl = new ReceptekList(JTPHomeScreen.this, rtm);
				rl.setVisible(true);
			}
		});
		btnLista.setBounds(10, 11, 200, 23);
		panel.add(btnLista);

		JButton btnBeszuras = new JButton("Recept besz\u00FAr\u00E1sa");
		btnBeszuras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewRecept nr = new NewRecept(JTPHomeScreen.this);
				nr.setVisible(true);
			}
		});
		btnBeszuras.setBounds(10, 45, 200, 23);
		panel.add(btnBeszuras);

		JButton btnTruncate = new JButton("\u00D6sszes recept t\u00F6rl\u00E9se");
		btnTruncate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.TruncateTable("Receptek");
			}
		});
		btnTruncate.setBounds(10, 79, 200, 23);
		panel.add(btnTruncate);

		JButton btnNewButton_1 = new JButton("Receptek ki\u00EDr\u00E1sa f\u00E1jlba");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.WriteReceptekDataToFile("receptek.csv");
			}
		});
		btnNewButton_1.setBounds(10, 113, 200, 23);
		panel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Receptek felt\u00F6lt\u00E9se f\u00E1jlb\u00F3l");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dbm.ReplaceReceptekData("receptek.csv");
				} catch (ArrayIndexOutOfBoundsException e2) {
					dbm.SM(e2.getMessage());
				}

			}
		});
		btnNewButton_2.setBounds(10, 147, 200, 23);
		panel.add(btnNewButton_2);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(220, 15, 299, 135);
		panel.add(scrollPane_1);

		JTextArea textAreaRCK = new JTextArea();
		scrollPane_1.setViewportView(textAreaRCK);

		JLabel lblNewLabel_1 = new JLabel("Lek\u00E9rdez\u00E9s:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(220, 156, 95, 14);
		panel.add(lblNewLabel_1);

		fromTF = new JTextField();
		fromTF.setBounds(289, 185, 86, 20);
		panel.add(fromTF);
		fromTF.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Kezd\u0151pont:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(220, 187, 77, 14);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("V\u00E9gpont:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_1.setBounds(230, 208, 77, 14);
		panel.add(lblNewLabel_2_1);

		toTF = new JTextField();
		toTF.setColumns(10);
		toTF.setBounds(289, 206, 86, 20);
		panel.add(toTF);

		JComboBox<String> comboBoxRCK = new JComboBox<String>();
		comboBoxRCK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedRCK = comboBoxRCK.getSelectedItem().toString();
			}
		});
		comboBoxRCK.setBounds(289, 153, 230, 22);
		panel.add(comboBoxRCK);
		comboBoxRCK.addItem("V�lassz!");
		comboBoxRCK.addItem("El��telek list�z�sa");
		comboBoxRCK.addItem("F��telek list�z�sa");
		comboBoxRCK.addItem("Desszertek list�z�sa");
		comboBoxRCK.addItem("Elk�sz�t�si id� intervallumon bel�l");
		comboBoxRCK.addItem("Receptek k�t d�tum k�z�tt");
		comboBoxRCK.addItem("�telekhez tartoz� telefonsz�mok");

		JButton btnRckExe = new JButton("V\u00E9grehajt");
		btnRckExe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedRCK.contentEquals("V�lassz!")) {
					dbm.SM("V�lassz egy listaelemet!");
				}
				if (selectedRCK.contentEquals("El��telek list�z�sa")) {
					String[] store = dbm.SelectJelleg("el��tel");
					for (int i = 0; i < store.length; i++) {
						textAreaRCK.append(store[i]);
					}
				}
				if (selectedRCK.contentEquals("F��telek list�z�sa")) {
					String[] store = dbm.SelectJelleg("f��tel");
					for (int i = 0; i < store.length; i++) {
						textAreaRCK.append(store[i]);
					}
				}
				if (selectedRCK.contentEquals("�telekhez tartoz� telefonsz�mok")) {
					String[] store = dbm.EtelEsTel();
					for (int i = 0; i < store.length; i++) {
						textAreaRCK.append(store[i]);
					}
				}
				if (selectedRCK.contentEquals("Desszertek list�z�sa")) {
					String[] store = dbm.SelectJelleg("desszert");
					for (int i = 0; i < store.length; i++) {
						textAreaRCK.append(store[i]);
					}
				}
				if (selectedRCK.contentEquals("Receptek k�t d�tum k�z�tt")) {
					if (!filledTF(fromTF)) {
						dbm.SM("A kezd�pont mez� �res!");
					} else if (!filledTF(toTF)) {
						dbm.SM("A v�gpont mez� �res!");
					} else {
						try {
							LocalDate.parse(RTF(fromTF));
							LocalDate.parse(RTF(toTF));
							String[] store = dbm.UtolsomodositasGivenIntervall(fromTF.getText(), toTF.getText());
							for (int i = 0; i < store.length; i++) {
								textAreaRCK.append("\n" + store[i]);
							}

						} catch (DateTimeParseException e2) {
							dbm.SM("Hib�s d�tumform�tum: " + e2.getMessage()
									+ ". \nA helyes d�tumform�tum: YYYY-MM-DD");
						}
					}
				}

				if (selectedRCK.contentEquals("Elk�sz�t�si id� intervallumon bel�l")) {
					if (!filledTF(fromTF)) {
						dbm.SM("A kezd�pont mez� �res!");
					} else if (!filledTF(toTF)) {
						dbm.SM("A v�gpont mez� �res!");
					} else if (!goodInt(fromTF)) {
						dbm.SM("Kezd�pont form�tuma nem megfelel�");
					} else if (!goodInt(toTF)) {
						dbm.SM("V�gpont form�tuma nem megfelel�");
					} else {
						textAreaRCK.append("\nReceptek " + fromTF.getText() + " �s " + toTF.getText()
								+ " k�zti elk�sz�t�si id�vel: ");
						textAreaRCK.append("\n======================================");
						String[] store = dbm.ElkeszitesiidoGivenIntervall(fromTF.getText(), toTF.getText());
						for (int i = 0; store[i] != null; i++) {
							textAreaRCK.append("\n" + store[i]);
						}
					}
				}

			}
		});
		btnRckExe.setBounds(404, 203, 115, 23);
		panel.add(btnRckExe);

		JButton btnClearRCKpanel = new JButton("Panel t\u00F6rl\u00E9se");
		btnClearRCKpanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaRCK.setText(null);
			}
		});
		btnClearRCKpanel.setBounds(10, 181, 200, 23);
		panel.add(btnClearRCKpanel);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("�ttermek", null, panel_1, null);
		panel_1.setLayout(null);

		JButton btnttermekListzsa = new JButton("\u00C9ttermek list\u00E1z\u00E1sa");
		btnttermekListzsa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etm = dbm.ReadEtteremData();
				EtteremList el = new EtteremList(JTPHomeScreen.this, etm);
				el.setVisible(true);
			}
		});
		btnttermekListzsa.setBounds(10, 11, 200, 23);
		panel_1.add(btnttermekListzsa);

		JButton btntteremBeszrsa = new JButton("\u00C9tterem besz\u00FAr\u00E1sa");
		btntteremBeszrsa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewEtterem ne = new NewEtterem(JTPHomeScreen.this);
				ne.setVisible(true);
			}
		});
		btntteremBeszrsa.setBounds(10, 45, 200, 23);
		panel_1.add(btntteremBeszrsa);

		JButton btnTruncateEtterem = new JButton("\u00D6sszes \u00E9tterem t\u00F6rl\u00E9se");
		btnTruncateEtterem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.TruncateTable("Etterem");
			}
		});
		btnTruncateEtterem.setBounds(10, 79, 200, 23);
		panel_1.add(btnTruncateEtterem);

		JButton btnNewButton = new JButton("\u00C9ttermek ki\u00EDr\u00E1sa f\u00E1jlba");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.WriteEtteremDataToFile("ettermek.csv");
			}
		});
		btnNewButton.setBounds(10, 113, 200, 23);
		panel_1.add(btnNewButton);

		JButton btnNewButton_3 = new JButton("\u00C9ttermek felt\u00F6lt\u00E9se f\u00E1jlb\u00F3l");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.ReplaceEtteremData("ettermek.csv");
			}
		});
		btnNewButton_3.setBounds(10, 147, 200, 23);
		panel_1.add(btnNewButton_3);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(220, 15, 297, 133);
		panel_1.add(scrollPane_2);

		JTextArea textAreaETT = new JTextArea();
		scrollPane_2.setViewportView(textAreaETT);

		JButton btnClearETTpanel = new JButton("Panel t\u00F6rl\u00E9se");
		btnClearETTpanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaETT.setText(null);
			}
		});
		btnClearETTpanel.setBounds(10, 181, 200, 23);
		panel_1.add(btnClearETTpanel);

		JLabel lblNewLabel_1_1 = new JLabel("Lek\u00E9rdez\u00E9s:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(218, 157, 95, 14);
		panel_1.add(lblNewLabel_1_1);

		JLabel lblNewLabel_2_2 = new JLabel("Kezd\u0151pont:");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2.setBounds(218, 188, 77, 14);
		panel_1.add(lblNewLabel_2_2);

		fromTFett = new JTextField();
		fromTFett.setColumns(10);
		fromTFett.setBounds(287, 186, 86, 20);
		panel_1.add(fromTFett);

		JLabel lblNewLabel_2_1_1 = new JLabel("V\u00E9gpont:");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_1_1.setBounds(228, 209, 77, 14);
		panel_1.add(lblNewLabel_2_1_1);

		toTFett = new JTextField();
		toTFett.setColumns(10);
		toTFett.setBounds(287, 207, 86, 20);
		panel_1.add(toTFett);

		JComboBox<String> comboBoxETT = new JComboBox<String>();
		comboBoxETT.addItem("V�lassz!");
		comboBoxETT.addItem("�telekhez tartoz� telefonsz�mok");
		comboBoxETT.addItem("Els� nyit�s k�t d�tum k�z�tt");
		comboBoxETT.setBounds(287, 154, 230, 22);
		panel_1.add(comboBoxETT);
		comboBoxETT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedETT = comboBoxETT.getSelectedItem().toString();
			}
		});

		JButton btnRckExeETT = new JButton("V\u00E9grehajt");
		btnRckExeETT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedETT.contentEquals("V�lassz!")) {
					dbm.SM("V�lassz egy listaelemet!");
				}
				if (selectedETT.contentEquals("�telekhez tartoz� telefonsz�mok")) {
					String[] store = dbm.EtelEsTel();
					for (int i = 0; i < store.length; i++) {
						textAreaETT.append(store[i]);
					}
				}
				if (selectedETT.contentEquals("Els� nyit�s k�t d�tum k�z�tt")) {
					if (!filledTF(fromTFett)) {
						dbm.SM("A kezd�pont mez� �res!");
					} else if (!filledTF(toTFett)) {
						dbm.SM("A v�gpont mez� �res!");
					} else {
						try {
							LocalDate.parse(RTF(fromTFett));
							LocalDate.parse(RTF(toTFett));
							String[] store = dbm.ElsoNyitasGivenIntervall(fromTFett.getText(), toTFett.getText());
							for (int i = 0; i < store.length; i++) {
								textAreaETT.append("\n" + store[i]);
							}

						} catch (DateTimeParseException e2) {
							dbm.SM("Hib�s d�tumform�tum: " + e2.getMessage()
									+ ". \nA helyes d�tumform�tum: YYYY-MM-DD");
						}
					}
				}
			}
		});
		btnRckExeETT.setBounds(402, 204, 115, 23);
		panel_1.add(btnRckExeETT);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Egyebek", null, panel_2, null);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("V\u00E1laszd ki a v\u00E9grehajtand\u00F3 parancsot:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 150, 242, 28);
		panel_2.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 509, 131);
		panel_2.add(scrollPane);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JButton btnExecute = new JButton("V\u00E9grehajt");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected.contentEquals("V�lassz!")) {
					dbm.SM("V�lassz egy listaelemet!");
				}
				if (selected.contentEquals("Receptek t�bl�t l�trehoz� parancs ki�r�sa")) {

					textArea.append("\n" + dbm.ReadSchema("Receptek"));
				}
				if (selected.contentEquals("�tterem t�bl�t l�trehoz� parancs ki�r�sa")) {

					textArea.append("\n" + dbm.ReadSchema("Etterem"));
				}
				if (selected.contentEquals("Receptek t�bla metaadatainak ki�r�sa")) {
					String[] store = dbm.TableInfoReceptek();
					String x = "\t";

					textArea.append(
							"CID" + x + "Name" + x + x + "Type" + x + "NotNull" + x + "Def_val" + x + "Primkey");
					for (int i = 0; i < store.length; i++) {
						textArea.append(store[i]);
					}
				}
				if (selected.contentEquals("�tterem t�bla metaadatainak ki�r�sa")) {
					String[] store = dbm.TableInfoEtterem();
					String x = "\t";

					textArea.append("CID" + x + "Name" + x + "Type" + x + "NotNull" + x + "Def_val" + x + "Primkey");
					for (int i = 0; i < store.length; i++) {
						textArea.append(store[i]);
					}
				}
			}
		});
		btnExecute.setBounds(430, 189, 89, 23);
		panel_2.add(btnExecute);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = comboBox.getSelectedItem().toString();
			}
		});
		comboBox.setBounds(229, 154, 290, 22);
		comboBox.addItem("V�lassz!");
		comboBox.addItem("Receptek t�bl�t l�trehoz� parancs ki�r�sa");
		comboBox.addItem("�tterem t�bl�t l�trehoz� parancs ki�r�sa");
		comboBox.addItem("Receptek t�bla metaadatainak ki�r�sa");
		comboBox.addItem("�tterem t�bla metaadatainak ki�r�sa");
		panel_2.add(comboBox);

		JButton btnCleanPanel = new JButton("Panel t\u00F6rl\u00E9se");
		btnCleanPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
			}
		});
		btnCleanPanel.setBounds(10, 189, 120, 23);
		panel_2.add(btnCleanPanel);

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
