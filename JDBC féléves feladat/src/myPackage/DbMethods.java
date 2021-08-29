package myPackage;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DbMethods {

	private Statement s = null;
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;

	public void Reg() {
		try {
			Class.forName("org.sqlite.JDBC");
			SM("Sikeres driver regisztráció!");

		} catch (ClassNotFoundException e) {
			SM("Hibás driver regisztáció" + e.getMessage());
		}
	}

	public void Connect() {
		try {
			String url = "jdbc:sqlite:D:\\Dokumentumok\\Miskolci Egyetem\\2020-21-2\\Adatbázisrendszerek II\\Eclipse workspace\\JDBC féléves feladat\\SQLite3\\JDBC.db";
			conn = DriverManager.getConnection(url);
//			SM("Connection OK!");
		} catch (SQLException e) {
			SM("JDBC Connect failure:" + e.getMessage());
		}
	}

	public void DisConnect() {
		try {
			conn.close();
//			SM("DisConnection OK!");
		} catch (SQLException e) {
			SM("DisConnection failure: " + e.getMessage());
		}
	}

	public void SM(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Program üzenet", 2);
	}

	// egyirányú parancsokat hajt végre (minden ami nem select)
	public void CommandExec(String command) {
		Connect();
		String sqlp = command;
		try {
			s = conn.createStatement();
			s.execute(sqlp);
			System.out.println("Command OK!");
		} catch (SQLException e) {
			System.out.println("CommandExec: " + e.getMessage());
		}
		DisConnect();
	}

	// táblát létrehozó parancs kiírása
	public String ReadSchema(String tableName) {
		Connect();
		String sql = "";
		String sqlp = "SELECT sql FROM sqlite_master WHERE name = '" + tableName + "';";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				sql = rs.getString("sql");
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect();
		return sql;
	}

	// tábla törlése
	public void DeleteTable(String tableName) {
		Connect();
		String sql = "";
		String sqlp = "DROP TABLE '" + tableName + "';";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				sql = rs.getString("sql");
				System.out.println(sql);
			}
			System.out.println("Delete OK!");
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect();
	}

	// tábla metaadatainak kiírása
	// az eltérõ karakterhosszok miatt muszáj kétfelé szedni a metaadatos metódust,
	// hogy szépen nézzen ki
	public String[] TableInfoEtterem() {
		Connect();
//		String[] result = new String[10];
		ArrayList<String> tempResult = new ArrayList<String>();
//		int i = 0;
		String sor = "", nam = "", tip = "", nul = "", def = "", pky = "", x = "\t";
		String sqlp = "PRAGMA table_info(Etterem);";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				sor = rs.getString(1); // oszlop sorszám
				nam = rs.getString(2); // mezõnév
				tip = rs.getString(3); // típus
				nul = rs.getString(4); // Not null elõírás
				def = rs.getString(5); // alapérték
				pky = rs.getString(6); // elsõdleges kulcs
				tempResult.add("\n" + sor + x + nam + x + tip + x + nul + x + def + x + pky);
//				i++;
			}
			rs.close();
		} catch (SQLException e) {
			SM(e.getMessage());
		}
		DisConnect();
		String[] result = tempResult.toArray(new String[tempResult.size()]);
		return result;
	}

	public String[] TableInfoReceptek() {
		Connect();
		ArrayList<String> tempResult = new ArrayList<String>();
//		String[] result = new String[10];
//		int i = 0;
		String sor = "", nam = "", tip = "", nul = "", def = "", pky = "", x = "\t";
		String sqlp = "PRAGMA table_info(Receptek);";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				sor = rs.getString(1); // oszlop sorszám
				nam = rs.getString(2); // mezõnév
				tip = rs.getString(3); // típus
				nul = rs.getString(4); // Not null elõírás
				def = rs.getString(5); // alapérték
				pky = rs.getString(6); // elsõdleges kulcs
				if (nam.length() <= 15) {
					nam = nam + "                          ";
				}
				tempResult.add("\n" + sor + x + nam + x + tip + x + nul + x + def + x + pky);
//				i++;
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect();
		String[] result = tempResult.toArray(new String[tempResult.size()]);
		return result;
	}

	// ezzel lehet megnézni, hogy egy név jelszó páros létezik-e
	public int Identification(String name, String pswd) {
		Connect();
		int pc = -1;
		String sqlp = "select count(*) pc from user where name='" + name + "' and password='" + pswd + "';";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				pc = rs.getInt("pc");
			}
			rs.close();
		} catch (SQLException e) {
			SM("Identification(): " + e.getMessage());
		}

		DisConnect();
		return pc;
	}

	// adat beszúrása a user táblába
	public void userRegister(String username, String password) {
		Connect();
		String sqlp = "insert into user values('" + username + "', '" + password + "')";
		try {
			s = conn.createStatement();
			s.execute(sqlp);
			SM("Sikeres regisztráció!");
		} catch (SQLException e) {
			SM("Sikertelen regiszráció: " + e.getMessage());
		}
		DisConnect();
	}

	// user tábla adatainak kiírása
	public void ReadUserData() {
		Connect();

		String name = "";
		String password = "";
		String x = "\t"; // tab hogy szépen nézzen ki

		String sqlp = "select name,password from user";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				name = rs.getString("name");
				password = rs.getString("password");

				SM("Username: " + name + " - Password: " + password);
			}
			rs.close();
		} catch (SQLException e) {
			SM(e.getMessage());
		}
		DisConnect();
	}

	// receptek tábla adatainak kiírása
	public ReceptekTM ReadReceptekData() {
		Object rtmn[] = { "Jel", "Sorszám", "Étel neve", "Jellege", "Elkészítési idõ", "Utolsó módosítás" };
		ReceptekTM rtm = new ReceptekTM(rtmn, 0);
		String etelneve = "";
		String jelleg = "";
		String utolsoModositasDatuma = "";
		int sorszam = 0, elkeszitesiido = 0;

		String sqlp = "select sorszam,nev,jelleg,elkeszitesi_ido,utolso_modositas from Receptek;";
		Connect();
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				sorszam = rs.getInt("sorszam");
				etelneve = rs.getString("nev");
				jelleg = rs.getString("jelleg");
				elkeszitesiido = rs.getInt("elkeszitesi_ido");
				utolsoModositasDatuma = rs.getString("utolso_modositas");

				LocalDate date = LocalDate.parse(utolsoModositasDatuma);
				rtm.addRow(new Object[] { false, sorszam, etelneve, jelleg, elkeszitesiido, date });

			}
			rs.close();
		} catch (SQLException | DateTimeParseException e) {
			SM(e.getMessage());
		}
		DisConnect();
		return rtm;
	}

	// Étterem tábla adatainak kiírása
	public EtteremTM ReadEtteremData() {
		Object etmn[] = { "Jel", "Azonosító", "Név", "Telefonszám", "Elsõ nyitás", "Specialitás" };
		EtteremTM etm = new EtteremTM(etmn, 0);
		String nev = "";
		String tel = "";
		String spec = "";
		String enyitas = "";
		int azon = 0;

		String sqlp = "select azonosito,nev,telefonszam,elso_nyitas,specialitas from Etterem;";
		Connect();
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				azon = rs.getInt("azonosito");
				nev = rs.getString("nev");
				tel = rs.getString("telefonszam");
				enyitas = rs.getString("elso_nyitas");
				spec = rs.getString("specialitas");
				try {
					LocalDate date2 = LocalDate.parse(enyitas);
					etm.addRow(new Object[] { false, azon, nev, tel, date2, spec });
				} catch (DateTimeParseException e) {
					SM("ReadEtteremData() hiba: " + e.getMessage());
				}
			}
			rs.close();
		} catch (SQLException e) {
			SM(e.getMessage());
		}
		DisConnect();
		return etm;
	}

	public void InsertRecept(String nev, String jelleg, String elkido, String utolsomodositas) {
		Connect();
		String sqlp = "insert into Receptek (nev, jelleg, elkeszitesi_ido, utolso_modositas) VALUES ('" + nev + "', '"
				+ jelleg + "', " + elkido + ", '" + utolsomodositas + "')";
		try {
			s = conn.createStatement();
			s.execute(sqlp);
			SM("Siker!");
		} catch (SQLException e) {
			SM("JDBC insert failure: " + e.getMessage());
		}
		DisConnect();

	}

	public void InsertEtterem(String nev, String tel, String enyitas, String spec) {
		Connect();
		String sqlp = "insert into Etterem (nev, telefonszam, elso_nyitas, specialitas) VALUES ('" + nev + "', '" + tel
				+ "', '" + enyitas + "', '" + spec + "')";
		try {
			s = conn.createStatement();
			s.execute(sqlp);
			SM("Siker!");
		} catch (SQLException e) {
			SM("JDBC insert failure: " + e.getMessage());
		}
		DisConnect();

	}

	public void InsertWithPreparedStatementEtterem(Etterem[] ett) {
		Connect();
		String sqlp = "insert into Etterem (nev, telefonszam, elso_nyitas, specialitas) " + "values(?,?,?,?)";
		try {
			ps = conn.prepareStatement(sqlp);
			for (int i = 0; i < ett.length; i++) {
				ps.setString(1, ett[i].getNev());
				ps.setString(2, ett[i].getTelefonszam());
				ps.setString(3, ett[i].getElso_nyitas());
				ps.setString(4, ett[i].getSpecialitas());
				ps.execute();
				SM("Siker!");
			}
		} catch (SQLException e) {
			SM("Insert problem: " + e.getMessage());
		}
		DisConnect();
	}

	public void InsertWithPreparedStatementReceptek(Receptek[] rck) {
		Connect();
		String sqlp = "insert into Receptek (nev, jelleg, elkeszitesi_ido, utolso_modositas) " + "values(?,?,?,?)";
		try {
			ps = conn.prepareStatement(sqlp);
			for (int i = 0; i < rck.length; i++) {
				ps.setString(1, rck[i].getEtelneve());
				ps.setString(2, rck[i].getJelleg());
				ps.setInt(3, rck[i].getElk_ido());
				ps.setString(4, rck[i].getUtolso_modositas());
				ps.execute();
				SM("Siker!");
			}
		} catch (SQLException e) {
			SM("Insert problem: " + e.getMessage());
		}
		DisConnect();
	}

	public void UpdateReceptek(String sorszam, String nev, String jelleg, String elkido, String utolsomodositas) {
		Connect();
		String sqlp = "update Receptek set nev='" + nev + "', jelleg='" + jelleg + "', elkeszitesi_ido=" + elkido
				+ ", utolso_modositas='" + utolsomodositas + "' where sorszam=" + sorszam;

		try {
			s = conn.createStatement();
			s.execute(sqlp);
			SM("Sikeres frissítés!");
		} catch (SQLException e) {
			SM("JDBC Update: " + e.getMessage());
		}
	}

	public void DeleteReceptek(String sorszam) {
		String sqlp = "delete from Receptek where sorszam=" + sorszam;
		try {
			s = conn.createStatement();
			s.execute(sqlp);
			SM("Sikeres törlés!");
		} catch (SQLException e) {
			SM("JDBC delete:" + e.getMessage());
		}
	}

	public void UpdateEtterem(String azon, String nev, String tel, String enyitas, String spec) {
		Connect();
		String sqlp = "update Etterem set nev='" + nev + "', telefonszam='" + tel + "', elso_nyitas='" + enyitas
				+ "', specialitas='" + spec + "' where azonosito=" + azon;

		try {
			s = conn.createStatement();
			s.execute(sqlp);
			SM("Sikeres frissítés!");
		} catch (SQLException e) {
			SM("JDBC Etterem update: " + e.getMessage());
		}
	}

	public void DeleteEtterem(String azon) {
		String sqlp = "delete from Etterem where azonosito=" + azon;
		try {
			s = conn.createStatement();
			s.execute(sqlp);
			SM("Sikeres törlés!");
		} catch (SQLException e) {
			SM("JDBC delete:" + e.getMessage());
		}
	}

	// tábla adatainak törlése
	public void TruncateTable(String tableName) {
		Connect();
		String sql = "";
		String sqlp = "DELETE FROM '" + tableName + "';";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				sql = rs.getString("sql");
				System.out.println(sql);
			}
			SM("A tábla üres!");
			rs.close();
		} catch (SQLException e) {
			// ha az üres táblának az adatait akarom törölni, akkor ezt a "hibaüzenetet"
			// adja vissza
			if (e.getMessage() == "query does not return ResultSet") {
				SM("A tábla üres!");
			} else {
				SM("Truncate table: " + e.getMessage());
			}
		}
		DisConnect();
	}

	// receptek adatainak kiírása fájlba
	public void WriteReceptekDataToFile(String file) {
		Connect();
		String etelnev = "", jelleg = "", elkido = "", utolsomodositas = "", x = ";";
		int sorszam = 0;
		String sqlp = "select sorszam,nev,jelleg,elkeszitesi_ido,utolso_modositas from Receptek;";
		try {
			PrintStream out = new PrintStream(new FileOutputStream(file));
			out.println("Sorszám;Étel_neve;Jellege;Elkészítési_idõ;Utolsó_módosítás");
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				sorszam = rs.getInt("sorszam");
				etelnev = rs.getString("nev");
				jelleg = rs.getString("jelleg");
				elkido = rs.getString("elkeszitesi_ido");
				utolsomodositas = rs.getString("utolso_modositas");
				out.println(sorszam + x + etelnev + x + jelleg + x + elkido + x + utolsomodositas);
			}
			out.close();
			rs.close();
			SM("Receptek tábla fájlba mentése sikeres!");
		} catch (IOException | SQLException e) {
			SM("WriteReceptekDataToFile: " + e.getMessage());
		}
		DisConnect();
	}

	// Receptek adatfeltöltés fájlból
	public void ReplaceReceptekData(String file) {
		String sqlp = "";
		Connect();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String sf = in.readLine();
			sf = in.readLine();
			while (sf != null) {
				String[] st = sf.split(";");

				// Mivel az Excel a YYYY-MM-DD alakban megadott dátumot YYYY.MM.DD formában
				// tárolja, így a visszaolvasás már nem mûködik, mert a Date.parse() metódusnak
				// nem jó, ezért a pontokat kötõjellé kell konvertálni a listába beillesztés
				// elõtt
				StringBuilder date = new StringBuilder(st[4]);
				date.setCharAt(4, '-');
				date.setCharAt(7, '-');

				sqlp = "replace into Receptek (sorszam ,nev, jelleg, elkeszitesi_ido, utolso_modositas) VALUES ("
						+ st[0] + ", '" + st[1] + "', '" + st[2] + "', " + st[3] + ", '" + date + "')";
				s = conn.createStatement();
				s.execute(sqlp);
				sf = in.readLine();
			}
			in.close();
			SM("Receptek tábla feltöltve fájlból");
		} catch (IOException | SQLException e) {
			SM("ReplaceReceptekData: " + e.getMessage());
		}
		DisConnect();
	}

	// Étterem adatainak kiírása fájlba
	public void WriteEtteremDataToFile(String file) {
		Connect();
		String nev = "", tel = "", enyitas = "", spec = "", x = ";";
		int azon = 0;
		String sqlp = "select azonosito,nev,telefonszam,elso_nyitas,specialitas from Etterem;";
		try {
			PrintStream out = new PrintStream(new FileOutputStream(file));
			out.println("Azonosító;Név;Telefonszám;Elsõ_nyitás;Specialitás");
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				azon = rs.getInt("azonosito");
				nev = rs.getString("nev");
				tel = rs.getString("telefonszam");
				enyitas = rs.getString("elso_nyitas");
				spec = rs.getString("specialitas");
				out.println(azon + x + nev + x + tel + x + enyitas + x + spec);
			}
			out.close();
			rs.close();
			SM("Étterem tábla fájlba mentése sikeres!");
		} catch (IOException | SQLException e) {
			SM("WriteReceptekDataToFile: " + e.getMessage());
		}
		DisConnect();
	}

	// Receptek adatfeltöltés fájlból
	// meglévõ táblát kiegészíti plusz rekordokkal, ha vannak -> összefésül
	public void ReplaceEtteremData(String file) {
		String sqlp = "";
		Connect();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String sf = in.readLine();
			sf = in.readLine();
			while (sf != null) {
				String[] st = sf.split(";");

				StringBuilder date = new StringBuilder(st[3]);
				date.setCharAt(4, '-');
				date.setCharAt(7, '-');

				sqlp = "replace into Etterem (azonosito,nev,telefonszam,elso_nyitas,specialitas) VALUES (" + st[0]
						+ ", '" + st[1] + "', '" + st[2] + "', '" + date + "', '" + st[4] + "')";
				s = conn.createStatement();
				s.execute(sqlp);
				sf = in.readLine();
			}
			in.close();
			SM("Étterem tábla feltöltve fájlból");
		} catch (IOException | SQLException e) {
			SM("ReplaceEtteremkData: " + e.getMessage());
		}
		DisConnect();
	}

	public String[] ElkeszitesiidoGivenIntervall(String from, String to) {
		Connect();
		String[] result = new String[20];
		String nev;
		int eido;
		int i = 0;
		String sqlp = "select nev, elkeszitesi_ido from Receptek where elkeszitesi_ido >= ? and elkeszitesi_ido <= ?";
		try {
			ps = conn.prepareStatement(sqlp);
			ps.setString(1, from);
			ps.setString(2, to);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				nev = rs.getString("nev");
				eido = rs.getInt("elkeszitesi_ido");
				if (nev.length() <= 10) {
					nev = nev + "    ";
				}
				result[i] = (nev + " - " + eido + " perc");
				i++;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect();
		return result;
	}

	public String[] UtolsomodositasGivenIntervall(String umod1, String umod2) {
		Connect();
		ArrayList<String> tempResult = new ArrayList<String>();
		String nev;
		String umod;
		String sqlp = "select nev, utolso_modositas from Receptek where utolso_modositas >= ? and utolso_modositas <= ?";
		try {
			ps = conn.prepareStatement(sqlp);
			ps.setString(1, umod1);
			ps.setString(2, umod2);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				nev = rs.getString("nev");
				umod = rs.getString("utolso_modositas");
				tempResult.add(nev + " - " + umod);
			}
		} catch (SQLException e) {
			SM(e.getMessage());
		}
		DisConnect();
		String[] result = tempResult.toArray(new String[tempResult.size()]);
		return result;
	}

	public String[] ElsoNyitasGivenIntervall(String enyitas1, String enyitas2) {
		Connect();
		ArrayList<String> tempResult = new ArrayList<String>();
		String nev;
		String enyitas;
		String sqlp = "select nev, elso_nyitas from Etterem where elso_nyitas >= ? and elso_nyitas <= ?";
		try {
			ps = conn.prepareStatement(sqlp);
			ps.setString(1, enyitas1);
			ps.setString(2, enyitas2);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				nev = rs.getString("nev");
				enyitas = rs.getString("elso_nyitas");
				tempResult.add(nev + " - " + enyitas);
			}
		} catch (SQLException e) {
			SM(e.getMessage());
		}
		DisConnect();
		String[] result = tempResult.toArray(new String[tempResult.size()]);
		return result;
	}

	public String[] SelectJelleg(String jelleg) {
		Connect();
		String[] result = new String[20];
		int i = 0;
		String nev = "";
		String jel = "";

		String sqlp = "select nev,jelleg from Receptek where jelleg='" + jelleg + "';";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				nev = rs.getString("nev");
				jel = rs.getString("jelleg");

				result[i] = (nev + " - " + jel + "\n");
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect();
		return result;
	}

	public String[] EtelEsTel() {
		Connect();
//		String[] result = new String[20];
		ArrayList<String> tempResult = new ArrayList<String>();
		String nev = "";
		String tel = "";

		String sqlp = "select Receptek.nev,telefonszam from Receptek join Etterem on Receptek.sorszam = Etterem.azonosito;";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				nev = rs.getString("nev");
				tel = rs.getString("telefonszam");

				tempResult.add(nev + " -> " + tel + "\n");
			}
			rs.close();
		} catch (SQLException e) {
			SM(e.getMessage());
		}
		DisConnect();
		String[] result = tempResult.toArray(new String[tempResult.size()]);
		return result;
	}

}
