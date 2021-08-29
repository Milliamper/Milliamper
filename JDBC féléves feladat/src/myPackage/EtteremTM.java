package myPackage;

import javax.swing.table.DefaultTableModel;

public class EtteremTM extends DefaultTableModel {

	//Konstruktor: megkapja a mez�k nev�t �s sorsz�m�t
	public EtteremTM(Object fildNames[], int rows) {
		super(fildNames, rows);
	}

	//0. oszlop minden sora szerkeszthet�, a t�bbi nem
	public boolean isCellEditable(int row, int col) {
		if (col == 0) {
			return true;
		}
		return false;
	}

	//Oszlopok t�pusa: 0. oszlop logikai, 1. �s 4. eg�sz, t�bbi sz�veges
	public Class<?> getColumnClass(int index) {
		if (index == 0)
			return (Boolean.class);
		else if (index == 1 || index == 4)
			return (Integer.class);
		return (String.class);
	}
}
