package myPackage;

import javax.swing.table.DefaultTableModel;

public class EtteremTM extends DefaultTableModel {

	//Konstruktor: megkapja a mezõk nevét és sorszámát
	public EtteremTM(Object fildNames[], int rows) {
		super(fildNames, rows);
	}

	//0. oszlop minden sora szerkeszthetõ, a többi nem
	public boolean isCellEditable(int row, int col) {
		if (col == 0) {
			return true;
		}
		return false;
	}

	//Oszlopok típusa: 0. oszlop logikai, 1. és 4. egész, többi szöveges
	public Class<?> getColumnClass(int index) {
		if (index == 0)
			return (Boolean.class);
		else if (index == 1 || index == 4)
			return (Integer.class);
		return (String.class);
	}
}
