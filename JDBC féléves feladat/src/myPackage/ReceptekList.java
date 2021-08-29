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
		super(f, "Receptek listája", true);
		rtm = brtm;
		setBounds(100, 100, 600, 300);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JButton btnBezár = new JButton("Bez\u00E1r");
		btnBezár.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // etûnik a képernyõrõl a panel, de a referenciái megmaradnak
			}
		});
		btnBezár.setBounds(485, 228, 89, 23);
		getContentPane().add(btnBezár);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 564, 206);
		getContentPane().add(scrollPane);

		table = new JTable(rtm);
		scrollPane.setViewportView(table);

		// oszlopok szélességét állítja be
		TableColumn tc = null;
		for (int i = 0; i < 6; i++) {
			tc = table.getColumnModel().getColumn(i);
			if (i == 0) 
				tc.setPreferredWidth(30);//jel
			else if (i == 1) { 
				tc.setPreferredWidth(55); //sorszám
			} else if (i == 2)
				tc.setPreferredWidth(150); //étel neve
			else if (i == 3)
				tc.setPreferredWidth(70); //jellege
			else if (i == 4)
				tc.setPreferredWidth(80); //elidõ
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
					dbm.SM("Nincs kijelölve módosítandó rekord");
				}
				if (db > 1) {
					dbm.SM("Több rekord van kijelölve, egyszerre csak egy rekord módosítható");
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
					dbm.SM("Nincs kijelölve törlendõ rekord");
				}
				if (db > 1) {
					dbm.SM("Több rekord van kijelölve, egyszerre csak egy rekord törölhetõ");
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
		JOptionPane.showMessageDialog(null, msg, "ABkezelõ üzenet", 2);
	}

	public String RTM(int row, int col) {
		return rtm.getValueAt(row, col).toString();
	}
}
