package myPackage;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.swing.JOptionPane;

public class FileManager {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
	public static int count = 0;
	public static int sumAge = 0;
	public static int countTeljesitett = 0;
	public static int countKesonErkezett = 0;
	public static int countKoranTavozott = 0;
	public static int countCSV = 0;
	public static int countTXT = 0;
	public static HashSet<Integer> codes = new HashSet<Integer>();

	public static void reset() {
		count = 0;
		sumAge = 0;
		countTeljesitett = 0;
		countKesonErkezett = 0;
		countKoranTavozott = 0;
		countCSV = 0;
		countTXT = 0;
	}

	public static PersonTM CsvReader() {
		Object emptmn[] = { "Jel", "K�d", "N�v", "Sz�lid�", "Kor", "Lakhely", "T�rzsid�", "Bel�p�s ideje",
				"Kil�p�s ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {

			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine(); // mez�nevek az els� sorb�l
			while (s != null) { // 1. adatsor beolvas�sa
				String[] st = s.split(";"); // adatsor feldarabol�sa

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}
				} else {
					SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
					out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
				}

				s = in.readLine(); // tov�bbi adatsorok beolvas�sa
				count++;
				sumAge += calculateAge(st[2]);
			}
			in.close();
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			System.out.println("CsvReader: " + e.getMessage());
		}
		return etm;
	}

	public static PersonTM CsvYounger(int number) {
		Object emptmn[] = { "Jel", "K�d", "N�v", "Sz�lid�", "Kor", "Lakhely", "T�rzsid�", "Bel�p�s ideje",
				"Kil�p�s ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {

			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine(); // mez�nevek az els� sorb�l
			while (s != null) { // 1. adatsor beolvas�sa
				String[] st = s.split(";"); // adatsor feldarabol�sa

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (calculateAge(st[2]) < number) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}

					s = in.readLine(); // tov�bbi adatsorok beolvas�sa
					count++;
					sumAge += calculateAge(st[2]);
				}
			}
			in.close();
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			System.out.println("CsvReader: " + e.getMessage());
		}
		return etm;
	}

	public static PersonTM CsvSearch(String name) {
		Object emptmn[] = { "Jel", "K�d", "N�v", "Sz�lid�", "Kor", "Lakhely", "T�rzsid�", "Bel�p�s ideje",
				"Kil�p�s ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {

			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine(); // mez�nevek az els� sorb�l
			while (s != null) { // 1. adatsor beolvas�sa
				String[] st = s.split(";"); // adatsor feldarabol�sa

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (st[1].contains(name)) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}
				}

				s = in.readLine(); // tov�bbi adatsorok beolvas�sa
				count++;
				sumAge += calculateAge(st[2]);
			}
			in.close();
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			System.out.println("CsvReader: " + e.getMessage());
		}
		return etm;
	}

	public static PersonTM TxtSearch(String name) {
		Object emptmn[] = { "Jel", "K�d", "N�v", "Sz�lid�", "Kor", "Lakhely", "T�rzsid�", "Bel�p�s ideje",
				"Kil�p�s ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {

			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.txt"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine(); // mez�nevek az els� sorb�l
			while (s != null) { // 1. adatsor beolvas�sa
				String[] st = s.split(";"); // adatsor feldarabol�sa

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (st[1].contains(name)) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}
				}

				s = in.readLine(); // tov�bbi adatsorok beolvas�sa
				count++;
				sumAge += calculateAge(st[2]);
			}
			in.close();
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			System.out.println("CsvReader: " + e.getMessage());
		}
		return etm;
	}

	public static PersonTM ReadTxtCsvAtTheSameTime() {
		Object emptmn[] = { "Jel", "K�d", "N�v", "Sz�lid�", "Kor", "Lakhely", "T�rzsid�", "Bel�p�s ideje",
				"Kil�p�s ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {
			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine();
			while (s != null) {
				String[] st = s.split(";");

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					countCSV++;
					codes.add(Checker.stringToInt(st[0]));
					if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}

				} else {
					SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
					out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
				}

				s = in.readLine();
				count++;
				sumAge += calculateAge(st[2]);
			}
			in.close();
			in = new BufferedReader(new FileReader("adatok.txt"));
			s = in.readLine();
			while (s != null) {
				String[] st = s.split(";");

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					countTXT++;
					codes.add(Checker.stringToInt(st[0]));
					if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}
				} else {
					SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
					out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
				}

				s = in.readLine();
				count++;
				sumAge += calculateAge(st[2]);
			}

			in.close();
		} catch (IOException ioe) {
			System.out.println("CsvReader: " + ioe.getMessage());
		}
		return etm;
	}

//	public static void ReadOnlyTheTXTIDsAtTheSameTime() {
//		try {
//			String x = ";";
//			BufferedReader in = new BufferedReader(new FileReader("adatok.txt"));
//			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
//			String s = in.readLine();
//			
//			while (s != null) {
//				String[] st = s.split(";");
//
//				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
//
//				if (isKodOk) {
//
//					codes.add(Checker.stringToInt(st[0]));
//
//				} else {
//					SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
//					out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
//				}
//
//				s = in.readLine();
//			}
//
//			in.close();
//		} catch (IOException ioe) {
//			System.out.println("CsvReader: " + ioe.getMessage());
//		}
//		System.out.println(codes);
//	}

	public static PersonTM ReadTxtCsvAtTheSameTime_Sorted() {
		ReadTxtCsvAtTheSameTime();
		Object emptmn[] = { "Jel", "K�d", "N�v", "Sz�lid�", "Kor", "Lakhely", "T�rzsid�", "Bel�p�s ideje",
				"Kil�p�s ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		Iterator<Integer> itr = codes.iterator();
		System.out.println(codes);
		reset();
		try {
			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine();
			while (s != null && itr.hasNext()) {
				String[] st = s.split(";");
				
				

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, itr.next(), st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					countCSV++;
					

					if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}

				} else {
					SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
					out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
				}

				s = in.readLine();
				count++;
				sumAge += calculateAge(st[2]);
			}
			in.close();
			in = new BufferedReader(new FileReader("adatok.txt"));
			s = in.readLine();

			
			
			while (s != null && itr.hasNext()) {
				String[] st = s.split(";");

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				
				
				
				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, itr.next(), st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					countTXT++;
					if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}
				} else {
					SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
					out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
				}

				s = in.readLine();
				count++;
				sumAge += calculateAge(st[2]);

			}

			in.close();

		} catch (IOException ioe) {
			System.out.println("CsvReader: " + ioe.getMessage());
		}
		return etm;
	}

	public static PersonTM YoungerTxtCsvAtTheSameTime(int number) {
		Object emptmn[] = { "Jel", "K�d", "N�v", "Sz�lid�", "Kor", "Lakhely", "T�rzsid�", "Bel�p�s ideje",
				"Kil�p�s ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {
			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine();
			while (s != null) {
				String[] st = s.split(";");

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (calculateAge(st[2]) < number) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}

					s = in.readLine();
					count++;
					sumAge += calculateAge(st[2]);
				}

			}
			in.close();
			in = new BufferedReader(new FileReader("adatok.txt"));
			s = in.readLine();
			while (s != null) {
				String[] st = s.split(";");

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (calculateAge(st[2]) < number) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}

					s = in.readLine();
					count++;
					sumAge += calculateAge(st[2]);
				}

			}
			in.close();
		} catch (IOException ioe) {
			System.out.println("CsvReader: " + ioe.getMessage());
		}
		return etm;
	}

	public static PersonTM SearchInTxtCsvAtTheSameTime(String name) {
		Object emptmn[] = { "Jel", "K�d", "N�v", "Sz�lid�", "Kor", "Lakhely", "T�rzsid�", "Bel�p�s ideje",
				"Kil�p�s ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {
			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine();
			while (s != null) {
				String[] st = s.split(";");

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (st[1].contains(name)) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}
				}

				s = in.readLine();
				count++;
				sumAge += calculateAge(st[2]);
			}
			in.close();
			in = new BufferedReader(new FileReader("adatok.txt"));
			s = in.readLine();
			while (s != null) {
				String[] st = s.split(";");

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (st[1].contains(name)) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hib�s adatsor tal�lhat� a f�jlban!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}
				}

				s = in.readLine();
				count++;
				sumAge += calculateAge(st[2]);
			}
			in.close();
		} catch (IOException ioe) {
			System.out.println("CsvReader: " + ioe.getMessage());
		}
		return etm;
	}

	public static PersonTM TxtReader() {
		Object emptmn[] = { "Jel", "K�d", "N�v", "Sz�lid�", "Kor", "Lakhely", "T�rzsid�", "Bel�p�s ideje",
				"Kil�p�s ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {
			String x = ";";
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			BufferedReader in = new BufferedReader(new FileReader("adatok.txt"));
			String s = in.readLine(); // mez�nevek az els� sorb�l
			while (s != null) { // 1. adatsor beolvas�sa
				String[] st = s.split(";"); // adatsor feldarabol�sa

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}
				} else {
					SM("Hib�s adatsor tal�lhat� a f�jlban!\nA hib�s rekord a Hibas_adat.txt f�jlba ki�rva!", 1);
					out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
				}

				s = in.readLine(); // tov�bbi adatsorok beolvas�sa
				count++;
				sumAge += calculateAge(st[2]);
			}
			in.close();
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			System.out.println("TxtReader: " + e.getMessage());
		}
		return etm;
	}

	public static PersonTM TxtYounger(int number) {
		Object emptmn[] = { "Jel", "K�d", "N�v", "Sz�lid�", "Kor", "Lakhely", "T�rzsid�", "Bel�p�s ideje",
				"Kil�p�s ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {
			String x = ";";
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			BufferedReader in = new BufferedReader(new FileReader("adatok.txt"));
			String s = in.readLine(); // mez�nevek az els� sorb�l
			while (s != null) { // 1. adatsor beolvas�sa
				String[] st = s.split(";"); // adatsor feldarabol�sa

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "K�d");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Sz�let�si id�");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (calculateAge(st[2]) < number) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljes�tett") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "K�s�n �rkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hib�s adatsor tal�lhat� a f�jlban!\nA hib�s rekord a Hibas_adat.txt f�jlba ki�rva!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}

					s = in.readLine(); // tov�bbi adatsorok beolvas�sa
					count++;
					sumAge += calculateAge(st[2]);
				}

			}
			in.close();
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			System.out.println("TxtReader: " + e.getMessage());
		}
		return etm;
	}

	public static int calculateAge(String date) {

		int age = 0;
		try {
			Date date1 = dateFormat.parse(date);
			Calendar now = Calendar.getInstance();
			Calendar dob = Calendar.getInstance();
			dob.setTime(date1);
			if (dob.after(now)) {
				throw new IllegalArgumentException("Can't be born in the future");
			}
			int year1 = now.get(Calendar.YEAR);
			int year2 = dob.get(Calendar.YEAR);
			age = year1 - year2;
			int month1 = now.get(Calendar.MONTH);
			int month2 = dob.get(Calendar.MONTH);
			if (month2 > month1) {
				age--;
			} else if (month1 == month2) {
				int day1 = now.get(Calendar.DAY_OF_MONTH);
				int day2 = dob.get(Calendar.DAY_OF_MONTH);
				if (day2 > day1) {
					age--;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return age;
	}

	public static String torzsidoDefine(String bel�p�s, String kil�p�s) {
		String result = "";

		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm:ss");

			LocalTime in = LocalTime.parse(bel�p�s, dtf);
			LocalTime out = LocalTime.parse(kil�p�s, dtf);
			LocalTime workTimeBegin = LocalTime.parse("09:00:00", dtf);
			LocalTime workTimeEnd = LocalTime.parse("15:00:00", dtf);

			int compareToBegin = in.compareTo(workTimeBegin);
			int compareToEnd = out.compareTo(workTimeEnd);

			if (compareToBegin > 0) {
				result = "K�sei �rkez�s";
			} else if (compareToEnd < 0) {
				result = "Korai t�voz�s";
			} else {
				result = "Teljes�tett";
			}

		} catch (DateTimeParseException e) {
			SM("Az id�pont rosszul lett megadva! A helyes form�tum: HH:mm:ss", 1);
			System.out.println("T�rzsid�define:" + e.getMessage());
		}

		return result;
	}

	public static void SM(String msg, int tipus) {
		JOptionPane.showMessageDialog(null, msg, "Program �zenet", tipus);
	}

	// mindk�t f�jl mod
	public static void InsertCSVMindketto(PersonTM etm) {
		String x = ";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream("adatok.csv"));
//			out.print("K�d;N�v;Sz�let�si_id�;Lakhely;Kezd�si_id�;Befejez�si_id�");
			for (int i = 0; i < (etm.getRowCount() - countTXT); i++) {
				String kod = etm.getValueAt(i, 1).toString();
				String nev = etm.getValueAt(i, 2).toString();
				String szid = etm.getValueAt(i, 3).toString();
				String lak = etm.getValueAt(i, 5).toString();

				String begin = etm.getValueAt(i, 7).toString();
				String end = etm.getValueAt(i, 8).toString();
				out.println(kod + x + nev + x + szid + x + lak + x + begin + x + end);
			}
			out.close();
		} catch (IOException e) {
			SM("File Manager Insert: " + e.getMessage(), 0);
		}
	}

	public static void InsertCSV(PersonTM etm) {
		String x = ";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream("adatok.csv"));
//			out.print("K�d;N�v;Sz�let�si_id�;Lakhely;Kezd�si_id�;Befejez�si_id�");
			for (int i = 0; i < etm.getRowCount(); i++) {
				String kod = etm.getValueAt(i, 1).toString();
				String nev = etm.getValueAt(i, 2).toString();
				String szid = etm.getValueAt(i, 3).toString();
				String lak = etm.getValueAt(i, 5).toString();

				String begin = etm.getValueAt(i, 7).toString();
				String end = etm.getValueAt(i, 8).toString();
				out.println(kod + x + nev + x + szid + x + lak + x + begin + x + end);
			}
			out.close();
		} catch (IOException e) {
			SM("File Manager Insert: " + e.getMessage(), 0);
		}
	}

	public static void Insert(String kod, String nev, String szid, String lak, String begin, String end) {
		String x = ";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream("adatok.csv", true));
			out.println(kod + x + nev + x + szid + x + lak + x + begin + x + end);
			out.close();
			SM("File Manager Insert: Adatok ki�rva!", 1);
		} catch (IOException e) {
			SM("File Manager Insert: " + e.getMessage(), 0);
		}
	}

	// mindket fajlt modosit
	public static void InsertTXTMindketto(PersonTM etm) {
		String x = ";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream("adatok.txt"));
//			out.print("K�d;N�v;Sz�let�si_id�;Lakhely;Kezd�si_id�;Befejez�si_id�");
			for (int i = countCSV; i < etm.getRowCount(); i++) {

				String kod = etm.getValueAt(i, 1).toString();
				String nev = etm.getValueAt(i, 2).toString();
				String szid = etm.getValueAt(i, 3).toString();
				String lak = etm.getValueAt(i, 5).toString();
				String begin = etm.getValueAt(i, 7).toString();
				String end = etm.getValueAt(i, 8).toString();

				out.println(kod + x + nev + x + szid + x + lak + x + begin + x + end);
			}
			out.close();
		} catch (IOException e) {
			SM("File Manager Insert: " + e.getMessage(), 0);
		}
	}

	public static void InsertTXT(PersonTM etm) {
		String x = ";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream("adatok.txt"));
//			out.print("K�d;N�v;Sz�let�si_id�;Lakhely;Kezd�si_id�;Befejez�si_id�");
			for (int i = 0; i < etm.getRowCount(); i++) {

				String kod = etm.getValueAt(i, 1).toString();
				String nev = etm.getValueAt(i, 2).toString();
				String szid = etm.getValueAt(i, 3).toString();
				String lak = etm.getValueAt(i, 5).toString();
				String begin = etm.getValueAt(i, 7).toString();
				String end = etm.getValueAt(i, 8).toString();

				out.println(kod + x + nev + x + szid + x + lak + x + begin + x + end);
			}
			out.close();
		} catch (IOException e) {
			SM("File Manager Insert: " + e.getMessage(), 0);
		}
	}

	public static void InsertTxt(String kod, String nev, String szid, String lak, String begin, String end) {
		String x = ";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream("adatok.txt", true));
			out.println(kod + x + nev + x + szid + x + lak + x + begin + x + end);
			out.close();
			SM("File Manager Insert: Adatok ki�rva!", 1);
		} catch (IOException e) {
			SM("File Manager Insert: " + e.getMessage(), 0);
		}
	}

	public static int avgAge(int sumAge, int count) {
		return sumAge / count;
	}
}
