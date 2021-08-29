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

public class PersonDelete extends JDialog {
	private JTable table;
	private PersonTM etm;
//	private Checker c = new Checker();

	public PersonDelete(JFrame f, PersonTM betm) {
		super(f, "Személyek listája", true);
		etm = betm;
		setBounds(100, 100, 810, 304);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 760, 204);
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
		
		JButton btnDeleteData = new JButton("Adatsor t\u00F6rl\u00E9se");
		btnDeleteData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int db = 0, jel = 0, x = 0;
				for (x = 0; x < etm.getRowCount(); x++) {
					if ((Boolean) etm.getValueAt(x, 0)) {
						db++;
						jel = x;
					}
				}
				if (db == 0) {
					FileManager.SM("Nincs kijelölve a törlendõ rekord", 0);
				}
				if (db > 1) {
					FileManager.SM("Több rekord van kijelölve, egyszerre csak egy rekord törölhetõ", 0);
				}
				if (db == 1) {
					
					if (Program.selected == "Mindkettõ") {
						if (FileManager.countCSV < jel) {
							etm.removeRow(jel);
//							FileManager.countTXT--;
							FileManager.InsertTXTMindketto(etm);
							Checker.SM("Txt adat törölve", 1);
							
						} else {
							etm.removeRow(jel);
//							FileManager.countCSV--;
							FileManager.InsertCSVMindketto(etm);
							Checker.SM("CSV adat törölve", 1);
						}
					} else {
						
						if(Program.selected.contentEquals(".csv")) {
							etm.removeRow(jel);
							FileManager.InsertCSV(etm);
							dispose();
							FileManager.SM("A rekord törölve!", 1);
						} else if (Program.selected.contentEquals(".txt")) {
							etm.removeRow(jel);
							FileManager.InsertTXT(etm);
							dispose();
							FileManager.SM("A rekord törölve!", 1);
						} else {
							Checker.SM("Válassz fájltípust", 1);
						}
					}
					
					
					
				}
				
			}
				
			
		});
		btnDeleteData.setBounds(10, 226, 120, 23);
		getContentPane().add(btnDeleteData);
		TableRowSorter<PersonTM> trs = (TableRowSorter<PersonTM>) table.getRowSorter();
		trs.setSortable(0, false);

	}
}
