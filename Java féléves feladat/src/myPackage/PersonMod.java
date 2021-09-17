package myPackage;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class PersonMod extends JDialog {
	private JTable table;
	private PersonTM etm;
	private JTextField kod;
	private JTextField nev;
	private JTextField szid;
	private JTextField lak;
	private JTextField begin;
	private JTextField end;
	private Checker c = new Checker();

	public PersonMod(JFrame f, PersonTM betm) {
		super(f, "Személyek listája", true);
		etm = betm;
		setBounds(100, 100, 810, 304);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 760, 173);
		getContentPane().add(scrollPane);

		table = new JTable(etm);
		scrollPane.setViewportView(table);

		JButton btnClose = new JButton("Bez\u00E1r");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(681, 226, 89, 23);
		getContentPane().add(btnClose);

		TableColumn tc = null;
		for (int i = 0; i < 9; i++) {
			tc = table.getColumnModel().getColumn(i);
			if (i == 0 || i == 1)
				tc.setPreferredWidth(120);
			else if (i == 4)
				tc.setPreferredWidth(120);
			else {
				tc.setPreferredWidth(120);
			}
		}

		table.setAutoCreateRowSorter(true);

		JButton btnMod = new JButton("M\u00F3dos\u00EDt\u00E1s");
		btnMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int db = 0, jel = 0, x = 0;
				for (x = 0; x < etm.getRowCount(); x++) {
					if ((Boolean) etm.getValueAt(x, 0)) {
						db++;
						jel = x;
					}
				}
				if (db == 0) {
					FileManager.SM("Nincs kijelölve a módosítandó rekord", 0);
				}
				if (db > 1) {
					FileManager.SM("Több rekord van kijelölve, egyszerre csak egy rekord módosítható", 0);
				}
				if (db == 1) {
					
					
					
					String file = "";
					System.out.println(FileManager.countCSV);
					System.out.println(FileManager.countTXT);
					System.out.println(jel);
					
					if (modDataPc() > 0) {
						boolean ok = true;
						if (ok && filled(kod)) {
							c.goodInt(kod, "Kód");
						}
						if (ok) {
							if (filled(kod)) {
								etm.setValueAt(c.stringToInt(c.RTF(kod)), jel, 1);
							}
							if (filled(nev)) {
								etm.setValueAt(c.RTF(nev), jel, 2);
							}
							if (filled(szid)) {
								etm.setValueAt(c.RTF(szid), jel, 3);
							}
							if (filled(lak)) {
								etm.setValueAt(c.RTF(lak), jel, 5);
							}
							if (filled(begin)) {
								etm.setValueAt(c.RTF(lak), jel, 7);
							}
							if (filled(end)) {
								etm.setValueAt(c.RTF(lak), jel, 8);
							}
							if(Program.selected == "Mindkettõ") {
								if (FileManager.countCSV < jel) {
									FileManager.InsertTXTMindketto(etm);
									c.SM("Txt adat módosítva", 1);
								} else {
									FileManager.InsertCSVMindketto(etm);
									c.SM("Csv adat módosítva", 1);
								}
							} else if (Program.selected == ".txt"){
								FileManager.InsertTXT(etm);
							} else {
								FileManager.InsertCSV(etm);
							}
							
						
							reset(jel);
						} else {
							c.SM("Nincs kijelölve egyetlen módosítandó adatmezõ sem!", 1);
						}

						
					}

				}

			}

		});
		btnMod.setBounds(20, 226, 120, 23);
		getContentPane().add(btnMod);

		kod = new JTextField();
		kod.setBounds(95, 195, 77, 20);
		getContentPane().add(kod);
		kod.setColumns(10);

		nev = new JTextField();
		nev.setColumns(10);
		nev.setBounds(182, 195, 77, 20);
		getContentPane().add(nev);

		szid = new JTextField();
		szid.setColumns(10);
		szid.setBounds(269, 195, 77, 20);
		getContentPane().add(szid);

		lak = new JTextField();
		lak.setColumns(10);
		lak.setBounds(433, 195, 86, 20);
		getContentPane().add(lak);

		begin = new JTextField();
		begin.setColumns(10);
		begin.setBounds(606, 195, 77, 20);
		getContentPane().add(begin);

		end = new JTextField();
		end.setColumns(10);
		end.setBounds(693, 195, 77, 20);
		getContentPane().add(end);
		TableRowSorter<PersonTM> trs = (TableRowSorter<PersonTM>) table.getRowSorter();
		trs.setSortable(0, false);

	}

	public boolean filled(JTextField a) {
		String s = RTF(a);
		if (s.length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public String RTF(JTextField jtf) {
		return jtf.getText();
	}

	public int modDataPc() {
		int pc = 0;
		if (filled(kod)) {
			pc++;
		}
		if (filled(nev)) {
			pc++;
		}
		if (filled(szid)) {
			pc++;
		}
		if (filled(lak)) {
			pc++;
		}
		if (filled(begin)) {
			pc++;
		}
		if (filled(end)) {
			pc++;
		}
		return pc;
	}
	
	public void reset (int i) {
		kod.setText("");
		nev.setText("");
		szid.setText("");
		lak.setText("");
		begin.setText("");
		end.setText("");
		etm.setValueAt(false, i, 0);
	}

}
