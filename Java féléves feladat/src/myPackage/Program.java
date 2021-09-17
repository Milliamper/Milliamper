package myPackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Program extends JFrame {

	private JPanel contentPane;
	private PersonTM etm;
	public static String selected = "Válassz!";
	public static String selectedSearch = "Válassz!";
	private JTextField textFieldAvgAge;
	private JTextField textFieldCountOK;
	private JTextField textFieldBeforeNine;
	private JTextField textFieldAfterFifteen;
	private JTextField textFieldSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Program frame = new Program();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Program() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnLista = new JButton("Lista");
		btnLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected.contentEquals(".csv")) {

					etm = FileManager.CsvReader();
					
					PersonList el = new PersonList(Program.this, etm);
					el.setVisible(true);
					textFieldAvgAge.setText(String.valueOf(FileManager.avgAge(FileManager.sumAge, FileManager.count)));
					textFieldCountOK.setText(String.valueOf(FileManager.countTeljesitett));
					textFieldBeforeNine.setText(String.valueOf(FileManager.countKesonErkezett));
					textFieldAfterFifteen.setText(String.valueOf(FileManager.countKoranTavozott));
				} else if (selected.contentEquals(".txt")) {
					
					etm = FileManager.TxtReader();
					PersonList el = new PersonList(Program.this, etm);
					el.setVisible(true);
					textFieldAvgAge.setText(String.valueOf(FileManager.avgAge(FileManager.sumAge, FileManager.count)));
					textFieldCountOK.setText(String.valueOf(FileManager.countTeljesitett));
					textFieldBeforeNine.setText(String.valueOf(FileManager.countKesonErkezett));
					textFieldAfterFifteen.setText(String.valueOf(FileManager.countKoranTavozott));
				} else if (selected.contentEquals("Mindkettõ")) {

					etm = FileManager.ReadTxtCsvAtTheSameTime();
					PersonList el = new PersonList(Program.this, etm);
					el.setVisible(true);
					textFieldAvgAge.setText(String.valueOf(FileManager.avgAge(FileManager.sumAge, FileManager.count)));
					textFieldCountOK.setText(String.valueOf(FileManager.countTeljesitett));
					textFieldBeforeNine.setText(String.valueOf(FileManager.countKesonErkezett));
					textFieldAfterFifteen.setText(String.valueOf(FileManager.countKoranTavozott));
				} else {
					Checker.SM("Válassz fájltípust!", 1);
				}

			}
		});
		btnLista.setBounds(10, 11, 120, 23);
		contentPane.add(btnLista);

		JButton btnUjAdat = new JButton("\u00DAj adatsor");
		btnUjAdat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewPerson np = new NewPerson(selected);
				np.setVisible(true);
			}
		});
		btnUjAdat.setBounds(10, 45, 120, 23);
		contentPane.add(btnUjAdat);

		JButton btnDelete = new JButton("T\u00F6rl\u00E9s");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (selected.contentEquals(".csv")) {
					etm = FileManager.CsvReader();
					PersonDelete pd = new PersonDelete(Program.this, etm);
					pd.setVisible(true);
				} else if (selected.contentEquals(".txt")) {
					etm = FileManager.TxtReader();
					PersonDelete pd = new PersonDelete(Program.this, etm);
					pd.setVisible(true);
				} else if (selected.contentEquals("Mindkettõ")) {
					etm = FileManager.ReadTxtCsvAtTheSameTime();
					PersonDelete pd = new PersonDelete(Program.this, etm);
					pd.setVisible(true);
				} else {
					Checker.SM("Válassz fájltípust!", 1);
				}
				
				
			}
		});
		btnDelete.setBounds(10, 79, 120, 23);
		contentPane.add(btnDelete);

		JButton btnNewButton = new JButton("M\u00F3dos\u00EDt\u00E1s");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected.contentEquals(".csv")) {
					etm = FileManager.CsvReader();
					PersonMod pm = new PersonMod(Program.this, etm);
					pm.setVisible(true);
				} else if (selected.contentEquals(".txt")) {
					etm = FileManager.TxtReader();
					PersonMod pm = new PersonMod(Program.this, etm);
					pm.setVisible(true);
				}else if (selected.contentEquals("Mindkettõ")) {
					etm = FileManager.ReadTxtCsvAtTheSameTime();
					PersonMod pm = new PersonMod(Program.this, etm);
					pm.setVisible(true);
				} else {
					Checker.SM("Válassz fájltípust!", 1);
				}

			}
		});
		btnNewButton.setBounds(10, 113, 120, 23);
		contentPane.add(btnNewButton);

		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Válassz!");
		comboBox.addItem(".csv");
		comboBox.addItem(".txt");
		comboBox.addItem("Mindkettõ");
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = comboBox.getSelectedItem().toString();
			}
		});
		comboBox.setBounds(332, 11, 142, 22);
		contentPane.add(comboBox);

		JLabel lblNewLabel = new JLabel("V\u00E1lassz f\u00E1jlt\u00EDpust a beolvas\u00E1shoz:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(151, 10, 206, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lbltlagletkor = new JLabel("\u00C1tlag\u00E9letkor: ");
		lbltlagletkor.setHorizontalAlignment(SwingConstants.RIGHT);
		lbltlagletkor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbltlagletkor.setBounds(151, 49, 206, 22);
		contentPane.add(lbltlagletkor);
		
		textFieldAvgAge = new JTextField();
		textFieldAvgAge.setEditable(false);
		textFieldAvgAge.setBounds(367, 48, 86, 20);
		contentPane.add(textFieldAvgAge);
		textFieldAvgAge.setColumns(10);
		
		JLabel lblTeljestettSttuszEmberek = new JLabel("Teljes\u00EDtett st\u00E1tusz\u00FA emberek sz\u00E1ma:");
		lblTeljestettSttuszEmberek.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTeljestettSttuszEmberek.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTeljestettSttuszEmberek.setBounds(151, 83, 206, 22);
		contentPane.add(lblTeljestettSttuszEmberek);
		
		textFieldCountOK = new JTextField();
		textFieldCountOK.setEditable(false);
		textFieldCountOK.setBounds(367, 85, 86, 20);
		contentPane.add(textFieldCountOK);
		textFieldCountOK.setColumns(10);
		
		JLabel lblTeljestettSttuszEmberek_1 = new JLabel("9 ut\u00E1n \u00E9rkezett emberek sz\u00E1ma:");
		lblTeljestettSttuszEmberek_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTeljestettSttuszEmberek_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTeljestettSttuszEmberek_1.setBounds(151, 117, 206, 22);
		contentPane.add(lblTeljestettSttuszEmberek_1);
		
		JLabel lblTeljestettSttuszEmberek_2 = new JLabel("15 el\u0151tt t\u00E1vozott emberek sz\u00E1ma:");
		lblTeljestettSttuszEmberek_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTeljestettSttuszEmberek_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTeljestettSttuszEmberek_2.setBounds(151, 150, 206, 22);
		contentPane.add(lblTeljestettSttuszEmberek_2);
		
		textFieldBeforeNine = new JTextField();
		textFieldBeforeNine.setEditable(false);
		textFieldBeforeNine.setBounds(367, 119, 86, 20);
		contentPane.add(textFieldBeforeNine);
		textFieldBeforeNine.setColumns(10);
		
		textFieldAfterFifteen = new JTextField();
		textFieldAfterFifteen.setEditable(false);
		textFieldAfterFifteen.setColumns(10);
		textFieldAfterFifteen.setBounds(367, 152, 86, 20);
		contentPane.add(textFieldAfterFifteen);
		
		JLabel lblTeljestettSttuszEmberek_2_1 = new JLabel("Keres\u00E9s: ");
		lblTeljestettSttuszEmberek_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeljestettSttuszEmberek_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTeljestettSttuszEmberek_2_1.setBounds(10, 196, 142, 22);
		contentPane.add(lblTeljestettSttuszEmberek_2_1);
		
		JComboBox comboBoxSearch = new JComboBox();
		comboBoxSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedSearch = comboBoxSearch.getSelectedItem().toString();
			}
		});
		comboBoxSearch.setBounds(237, 197, 120, 22);
		comboBoxSearch.addItem("Válassz");
		comboBoxSearch.addItem("Névre");
		comboBoxSearch.addItem("X évnél fiatalabb emberekre");
		
		contentPane.add(comboBoxSearch);
		
		JButton btnSearch = new JButton("List\u00E1z");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(selectedSearch.contentEquals("Névre")  && selected.contentEquals(".csv")) {
					etm = FileManager.CsvSearch(textFieldSearch.getText());
					PersonList el = new PersonList(Program.this, etm);
					el.setVisible(true);
				}
				
				if(selectedSearch.contentEquals("Névre")  && selected.contentEquals(".txt")) {
					etm = FileManager.TxtSearch(textFieldSearch.getText());
					PersonList el = new PersonList(Program.this, etm);
					el.setVisible(true);
				}
				
				if(selectedSearch.contentEquals("Névre")  && selected.contentEquals("Mindkettõ")) {
					etm = FileManager.SearchInTxtCsvAtTheSameTime(textFieldSearch.getText());
					PersonList el = new PersonList(Program.this, etm);
					el.setVisible(true);
				}
				
				if(selectedSearch.contentEquals("X évnél fiatalabb emberekre") && selected.contentEquals(".csv")) {
					etm = FileManager.CsvYounger(Checker.stringToInt(textFieldSearch.getText()));
					PersonList el = new PersonList(Program.this, etm);
					el.setVisible(true);
				}
				
				if(selectedSearch.contentEquals("X évnél fiatalabb emberekre") && selected.contentEquals(".txt")) {
					etm = FileManager.TxtYounger(Checker.stringToInt(textFieldSearch.getText()));
					PersonList el = new PersonList(Program.this, etm);
					el.setVisible(true);
				}
				
				if(selectedSearch.contentEquals("X évnél fiatalabb emberekre") && selected.contentEquals("Mindkettõ")) {
					etm = FileManager.YoungerTxtCsvAtTheSameTime(Checker.stringToInt(textFieldSearch.getText()));
					PersonList el = new PersonList(Program.this, etm);
					el.setVisible(true);
//					CHECKER.SM("EZ A FUNKCIÓ JELENLEG NEM ÉRHETÕ EL :(", 1);
				}
			}
		});
		btnSearch.setBounds(367, 197, 89, 23);
		contentPane.add(btnSearch);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(112, 198, 115, 20);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("V\u00E1logatott adatsor");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!selected.contentEquals("Mindkettõ")) {
					Checker.SM("A funkció eléréséhez válassza a fájltípusként a Mindkét opciót", 1);
				} else {
					etm = FileManager.ReadTxtCsvAtTheSameTime_Sorted();
					PersonList el = new PersonList(Program.this, etm);
					el.setVisible(true);
					textFieldAvgAge.setText(String.valueOf(FileManager.avgAge(FileManager.sumAge, FileManager.count)));
					textFieldCountOK.setText(String.valueOf(FileManager.countTeljesitett));
					textFieldBeforeNine.setText(String.valueOf(FileManager.countKesonErkezett));
					textFieldAfterFifteen.setText(String.valueOf(FileManager.countKoranTavozott));	
				}
			}
		});
		btnNewButton_1.setBounds(10, 151, 131, 23);
		contentPane.add(btnNewButton_1);

		Object emptmn[] = { "Jel", "Kód", "Név", "Szülidõ", "Kor", "Lakhely", "Törzsidõ", "Belépés ideje",
				"Kilépés ideje" };
		etm = new PersonTM(emptmn, 0);

	}

	public static String getSelectedString(String selected) {
		return selected;
	}
}
