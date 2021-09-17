package myPackage;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReceptekList extends JDialog {
	private JTable table;
	private ReceptekTM rtm;
	DbMethods dbm = new DbMethods();
	private JButton btnTorles;

	public ReceptekList(JFrame f, ReceptekTM brtm) {
		super(f, "Receptek list�ja", true);
		rtm = brtm;
		setBounds(100, 100, 600, 300);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JButton btnBez�r = new JButton("Bez\u00E1r");
		btnBez�r.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // et�nik a k�perny�r�l a panel, de a referenci�i megmaradnak
			}
		});
		btnBez�r.setBounds(485, 228, 89, 23);
		getContentPane().add(btnBez�r);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 564, 206);
		getContentPane().add(scrollPane);

		table = new JTable(rtm);
		scrollPane.setViewportView(table);

		// oszlopok sz�less�g�t �ll�tja be
		TableColumn tc = null;
		for (int i = 0; i < 6; i++) {
			tc = table.getColumnModel().getColumn(i);
			if (i == 0) 
				tc.setPreferredWidth(30);//jel
			else if (i == 1) { 
				tc.setPreferredWidth(55); //sorsz�m
			} else if (i == 2)
				tc.setPreferredWidth(150); //�tel neve
			else if (i == 3)
				tc.setPreferredWidth(70); //jellege
			else if (i == 4)
				tc.setPreferredWidth(80); //elid�
			else {
				tc.setPreferredWidth(100);
			}
		}

		JButton btnMod = new JButton("M\u00F3dos\u00EDt\u00E1s");
		btnMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int db = 0, jel = 0, x = 0;
				for (x = 0; x < rtm.getRowCount(); x++) {
					if ((Boolean) rtm.getValueAt(x, 0)) {
						db++;
						jel = x;
					}
				}
				if (db == 0) {
					dbm.SM("Nincs kijel�lve m�dos�tand� rekord");
				}
				if (db > 1) {
					dbm.SM("T�bb rekord van kijel�lve, egyszerre csak egy rekord m�dos�that�");
				}
				if (db == 1) {
					ModRecept mr = new ModRecept(null, RTM(jel, 1), RTM(jel, 2), RTM(jel, 3), RTM(jel, 4), RTM(jel, 5));
					mr.setVisible(true);
				}
			}
		});
		btnMod.setBounds(10, 228, 100, 23);
		getContentPane().add(btnMod);

		btnTorles = new JButton("T\u00F6rl\u00E9s");
		btnTorles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int db = 0, jel = 0, x = 0;
				for (x = 0; x < rtm.getRowCount(); x++) {
					if ((Boolean) rtm.getValueAt(x, 0)) {
						db++;
						jel = x;
					}
				}
				if (db == 0) {
					dbm.SM("Nincs kijel�lve t�rlend� rekord");
				}
				if (db > 1) {
					dbm.SM("T�bb rekord van kijel�lve, egyszerre csak egy rekord t�r�lhet�");
				}
				if (db == 1) {
					dbm.Connect();
					dbm.DeleteReceptek(RTM(jel, 1));
					rtm.removeRow(jel);
					dbm.DisConnect();
				}

			}
		});
		btnTorles.setBounds(120, 228, 89, 23);
		getContentPane().add(btnTorles);

		table.setAutoCreateRowSorter(true);
		TableRowSorter<EtteremTM> trs = (TableRowSorter<EtteremTM>) table.getRowSorter();
		trs.setSortable(0, false);

	}

	public void SM(String msg) {
		JOptionPane.showMessageDialog(null, msg, "ABkezel� �zenet", 2);
	}

	public String RTM(int row, int col) {
		return rtm.getValueAt(row, col).toString();
	}
}
