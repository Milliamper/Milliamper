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
		Object emptmn[] = { "Jel", "Kód", "Név", "Szülidõ", "Kor", "Lakhely", "Törzsidõ", "Belépés ideje",
				"Kilépés ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {

			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine(); // mezõnevek az elsõ sorból
			while (s != null) { // 1. adatsor beolvasása
				String[] st = s.split(";"); // adatsor feldarabolása

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}
				} else {
					SM("Hibás adatsor található a fájlban!", 1);
					out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
				}

				s = in.readLine(); // további adatsorok beolvasása
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
		Object emptmn[] = { "Jel", "Kód", "Név", "Szülidõ", "Kor", "Lakhely", "Törzsidõ", "Belépés ideje",
				"Kilépés ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {

			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine(); // mezõnevek az elsõ sorból
			while (s != null) { // 1. adatsor beolvasása
				String[] st = s.split(";"); // adatsor feldarabolása

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (calculateAge(st[2]) < number) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hibás adatsor található a fájlban!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}

					s = in.readLine(); // további adatsorok beolvasása
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
		Object emptmn[] = { "Jel", "Kód", "Név", "Szülidõ", "Kor", "Lakhely", "Törzsidõ", "Belépés ideje",
				"Kilépés ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {

			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine(); // mezõnevek az elsõ sorból
			while (s != null) { // 1. adatsor beolvasása
				String[] st = s.split(";"); // adatsor feldarabolása

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (st[1].contains(name)) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hibás adatsor található a fájlban!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}
				}

				s = in.readLine(); // további adatsorok beolvasása
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
		Object emptmn[] = { "Jel", "Kód", "Név", "Szülidõ", "Kor", "Lakhely", "Törzsidõ", "Belépés ideje",
				"Kilépés ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {

			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.txt"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine(); // mezõnevek az elsõ sorból
			while (s != null) { // 1. adatsor beolvasása
				String[] st = s.split(";"); // adatsor feldarabolása

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (st[1].contains(name)) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hibás adatsor található a fájlban!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}
				}

				s = in.readLine(); // további adatsorok beolvasása
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
		Object emptmn[] = { "Jel", "Kód", "Név", "Szülidõ", "Kor", "Lakhely", "Törzsidõ", "Belépés ideje",
				"Kilépés ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {
			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine();
			while (s != null) {
				String[] st = s.split(";");

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					countCSV++;
					codes.add(Checker.stringToInt(st[0]));
					if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}

				} else {
					SM("Hibás adatsor található a fájlban!", 1);
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

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					countTXT++;
					codes.add(Checker.stringToInt(st[0]));
					if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}
				} else {
					SM("Hibás adatsor található a fájlban!", 1);
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
//				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
//
//				if (isKodOk) {
//
//					codes.add(Checker.stringToInt(st[0]));
//
//				} else {
//					SM("Hibás adatsor található a fájlban!", 1);
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
		Object emptmn[] = { "Jel", "Kód", "Név", "Szülidõ", "Kor", "Lakhely", "Törzsidõ", "Belépés ideje",
				"Kilépés ideje" };
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
				
				

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, itr.next(), st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					countCSV++;
					

					if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}

				} else {
					SM("Hibás adatsor található a fájlban!", 1);
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

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				
				
				
				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, itr.next(), st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					countTXT++;
					if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}
				} else {
					SM("Hibás adatsor található a fájlban!", 1);
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
		Object emptmn[] = { "Jel", "Kód", "Név", "Szülidõ", "Kor", "Lakhely", "Törzsidõ", "Belépés ideje",
				"Kilépés ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {
			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine();
			while (s != null) {
				String[] st = s.split(";");

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (calculateAge(st[2]) < number) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hibás adatsor található a fájlban!", 1);
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

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (calculateAge(st[2]) < number) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hibás adatsor található a fájlban!", 1);
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
		Object emptmn[] = { "Jel", "Kód", "Név", "Szülidõ", "Kor", "Lakhely", "Törzsidõ", "Belépés ideje",
				"Kilépés ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {
			String x = ";";
			BufferedReader in = new BufferedReader(new FileReader("adatok.csv"));
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			String s = in.readLine();
			while (s != null) {
				String[] st = s.split(";");

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (st[1].contains(name)) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hibás adatsor található a fájlban!", 1);
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

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (st[1].contains(name)) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hibás adatsor található a fájlban!", 1);
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
		Object emptmn[] = { "Jel", "Kód", "Név", "Szülidõ", "Kor", "Lakhely", "Törzsidõ", "Belépés ideje",
				"Kilépés ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {
			String x = ";";
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			BufferedReader in = new BufferedReader(new FileReader("adatok.txt"));
			String s = in.readLine(); // mezõnevek az elsõ sorból
			while (s != null) { // 1. adatsor beolvasása
				String[] st = s.split(";"); // adatsor feldarabolása

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
					etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
							torzsidoDefine(st[4], st[5]), st[4], st[5] });
					if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
						countTeljesitett++;
					} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
						countKesonErkezett++;
					} else {
						countKoranTavozott++;
					}
				} else {
					SM("Hibás adatsor található a fájlban!\nA hibás rekord a Hibas_adat.txt fájlba kiírva!", 1);
					out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
				}

				s = in.readLine(); // további adatsorok beolvasása
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
		Object emptmn[] = { "Jel", "Kód", "Név", "Szülidõ", "Kor", "Lakhely", "Törzsidõ", "Belépés ideje",
				"Kilépés ideje" };
		PersonTM etm = new PersonTM(emptmn, 0);
		reset();
		try {
			String x = ";";
			PrintStream out = new PrintStream(new FileOutputStream("Hibas_adatok.txt", true));
			BufferedReader in = new BufferedReader(new FileReader("adatok.txt"));
			String s = in.readLine(); // mezõnevek az elsõ sorból
			while (s != null) { // 1. adatsor beolvasása
				String[] st = s.split(";"); // adatsor feldarabolása

				boolean isKodOk = Checker.goodIntBeforeRead(st[0], "Kód");
				boolean isDateOk = Checker.goodDateBeforeRead(st[2], "Születési idõ");
				boolean isTimeOk = Checker.goodTimeBeforeRead(st[4]);
				boolean isTime2Ok = Checker.goodTimeBeforeRead(st[5]);

				if (calculateAge(st[2]) < number) {
					if (isKodOk && isDateOk && isTimeOk && isTime2Ok) {
						etm.addRow(new Object[] { false, st[0], st[1], st[2], calculateAge(st[2]), st[3],
								torzsidoDefine(st[4], st[5]), st[4], st[5] });
						if (torzsidoDefine(st[4], st[5]) == "Teljesített") {
							countTeljesitett++;
						} else if (torzsidoDefine(st[4], st[5]) == "Késõn érkezett") {
							countKesonErkezett++;
						} else {
							countKoranTavozott++;
						}
					} else {
						SM("Hibás adatsor található a fájlban!\nA hibás rekord a Hibas_adat.txt fájlba kiírva!", 1);
						out.println(st[0] + x + st[1] + x + st[2] + x + st[3] + x + st[4] + x + st[5]);
					}

					s = in.readLine(); // további adatsorok beolvasása
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

	public static String torzsidoDefine(String belépés, String kilépés) {
		String result = "";

		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm:ss");

			LocalTime in = LocalTime.parse(belépés, dtf);
			LocalTime out = LocalTime.parse(kilépés, dtf);
			LocalTime workTimeBegin = LocalTime.parse("09:00:00", dtf);
			LocalTime workTimeEnd = LocalTime.parse("15:00:00", dtf);

			int compareToBegin = in.compareTo(workTimeBegin);
			int compareToEnd = out.compareTo(workTimeEnd);

			if (compareToBegin > 0) {
				result = "Kései érkezés";
			} else if (compareToEnd < 0) {
				result = "Korai távozás";
			} else {
				result = "Teljesített";
			}

		} catch (DateTimeParseException e) {
			SM("Az idõpont rosszul lett megadva! A helyes formátum: HH:mm:ss", 1);
			System.out.println("Törzsidõdefine:" + e.getMessage());
		}

		return result;
	}

	public static void SM(String msg, int tipus) {
		JOptionPane.showMessageDialog(null, msg, "Program üzenet", tipus);
	}

	// mindkét fájl mod
	public static void InsertCSVMindketto(PersonTM etm) {
		String x = ";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream("adatok.csv"));
//			out.print("Kód;Név;Születési_idõ;Lakhely;Kezdési_idõ;Befejezési_idõ");
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
//			out.print("Kód;Név;Születési_idõ;Lakhely;Kezdési_idõ;Befejezési_idõ");
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
			SM("File Manager Insert: Adatok kiírva!", 1);
		} catch (IOException e) {
			SM("File Manager Insert: " + e.getMessage(), 0);
		}
	}

	// mindket fajlt modosit
	public static void InsertTXTMindketto(PersonTM etm) {
		String x = ";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream("adatok.txt"));
//			out.print("Kód;Név;Születési_idõ;Lakhely;Kezdési_idõ;Befejezési_idõ");
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
//			out.print("Kód;Név;Születési_idõ;Lakhely;Kezdési_idõ;Befejezési_idõ");
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
			SM("File Manager Insert: Adatok kiírva!", 1);
		} catch (IOException e) {
			SM("File Manager Insert: " + e.getMessage(), 0);
		}
	}

	public static int avgAge(int sumAge, int count) {
		return sumAge / count;
	}
}
