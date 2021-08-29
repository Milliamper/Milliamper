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

public class PersonList extends JDialog {
	private JTable table;
	private PersonTM etm;

	public PersonList(JFrame f, PersonTM betm) {
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
		btnClose.setBounds(352, 227, 89, 23);
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
		TableRowSorter<PersonTM> trs = (TableRowSorter<PersonTM>) table.getRowSorter();
		trs.setSortable(0, false);

	}
}
