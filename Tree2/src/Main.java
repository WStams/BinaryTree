// Code for popping up a window that displays a custom component
// in this case we are displaying a Binary Search tree  
// reference problem 4.38 of Weiss to compute tree node x,y positions

// input is a text file name that will form the Binary Search Tree

//     java DisplaySimpleTree textfile

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Main-Klasse zum erstellen eines Bin�rbaums
 * 
 * @author Justin Mertmann, Wayne Stams
 *
 */
public class Main extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollpane;
	private DisplayPanel panel;
	private JPanel userPanel = new JPanel();
	private JPanel traversierungsPanel = new JPanel();
	private JButton hinzufuegenButton = new JButton("Hinzuf�gen");
	private JButton loeschenButton = new JButton("L�schen");
	private JButton inorderButton = new JButton("Inorder-Traversierung");
	private JButton preorderButton = new JButton("Preorder-Traversierung");
	private JButton postorderButton = new JButton("Postorder-Traversierung");
	private JButton balanceButton = new JButton("Balancieren!");

	private JTextField inputTextfield = new JTextField();
	private static Binaerbaum baum = new Binaerbaum();

	/**
	 * Konstruktor
	 * 
	 * @param t
	 *            Bin�rbaum, der erstellt wird
	 */
	public Main(Binaerbaum t) {
		this.setTitle("Bin�rbaum - Justin & Wayne");
		loeschenButton.setName("loeschen");
		hinzufuegenButton.setName("hinzufuegen");

		this.inputTextfield.setBackground(new Color(200, 200, 200));

		this.inorderButton.addActionListener(this);
		this.preorderButton.addActionListener(this);
		this.postorderButton.addActionListener(this);
		this.balanceButton.addActionListener(this);

		this.traversierungsPanel.setLayout(new GridLayout(1, 4));
		this.traversierungsPanel.add(inorderButton);
		this.traversierungsPanel.add(preorderButton);
		this.traversierungsPanel.add(postorderButton);
		this.traversierungsPanel.add(balanceButton);

		this.userPanel.setLayout(new GridLayout(1, 3));
		this.userPanel.add(inputTextfield);
		this.userPanel.add(hinzufuegenButton);
		this.userPanel.add(loeschenButton);
		this.getContentPane().setLayout(new BorderLayout());

		loeschenButton.addActionListener(this);
		hinzufuegenButton.addActionListener(this);

		this.getContentPane().add(userPanel, BorderLayout.SOUTH);
		this.getContentPane().add(traversierungsPanel, BorderLayout.CENTER);
		this.initialize(t);
		this.setResizable(false);
	}

	/**
	 * Initialisiert das DisplayPanel
	 * 
	 * @param baum
	 *            zu zeichnender Baum
	 */
	private void initialize(Binaerbaum baum) {
		this.panel = new DisplayPanel(baum);
		this.panel.setPreferredSize(new Dimension(1200, 800));
		this.scrollpane = new JScrollPane(panel);
		this.getContentPane().add(scrollpane, BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
	}

	/**
	 * Main Methode
	 * 
	 * @param args
	 *            beim Start �bergebene Argumente
	 */
	public static void main(String[] args) {
		baum.elementHinzufuegen("JAN");
		baum.elementHinzufuegen("FEB");
		baum.elementHinzufuegen("MAE");
		baum.elementHinzufuegen("APR");
		baum.elementHinzufuegen("MAI");
		baum.elementHinzufuegen("JUN");
		baum.elementHinzufuegen("JUL");
		baum.elementHinzufuegen("AUG");
		baum.elementHinzufuegen("SEP");
		baum.elementHinzufuegen("OKT");
		baum.elementHinzufuegen("NOV");
		baum.elementHinzufuegen("DEZ");
		//
		baum.elementHinzufuegen("JEN");
		baum.elementHinzufuegen("FAB");
		baum.elementHinzufuegen("MBE");
		baum.elementHinzufuegen("ALR");
		baum.elementHinzufuegen("MSI");
		baum.elementHinzufuegen("J�N");
		baum.elementHinzufuegen("J�L");
		baum.elementHinzufuegen("A�G");
		baum.elementHinzufuegen("S�P");
		baum.elementHinzufuegen("POT");
		baum.elementHinzufuegen("NOT");
		baum.elementHinzufuegen("DER");
		baum.elementHinzufuegen("DIE");
		baum.elementHinzufuegen("DAS");
		baum.elementHinzufuegen("WIE");
		baum.elementHinzufuegen("ASW");
		baum.elementHinzufuegen("ASD");
		baum.elementHinzufuegen("ASF");
		baum.elementHinzufuegen("FAS");
		baum.elementHinzufuegen("WAE");
		baum.elementHinzufuegen("ERE");
		baum.elementHinzufuegen("KTI");
		baum.elementHinzufuegen("ONE");
		baum.elementHinzufuegen("NKA");
		baum.elementHinzufuegen("NON");
		baum.elementHinzufuegen("ENF");
		baum.elementHinzufuegen("FUT");
		baum.elementHinzufuegen("TER");
		baum.elementHinzufuegen("EJA");
		baum.elementHinzufuegen("KUL");
		baum.elementHinzufuegen("ATI");
		baum.elementHinzufuegen("ONE");
		baum.elementHinzufuegen("NBU");
		baum.elementHinzufuegen("KKA");
		baum.elementHinzufuegen("KEP");
		baum.elementHinzufuegen("ART");
		// baum.elementHinzufuegen("YHA");
		// baum.elementHinzufuegen("RDM");
		// baum.elementHinzufuegen("MEI");
		// baum.elementHinzufuegen("NFR");
		// baum.elementHinzufuegen("EUN");
		// baum.elementHinzufuegen("DDU");
		// baum.elementHinzufuegen("LAC");
		// baum.elementHinzufuegen("HSN");
		// baum.elementHinzufuegen("ACK");
		// baum.elementHinzufuegen("END");
		// baum.elementHinzufuegen("ESV");
		// baum.elementHinzufuegen("ERD");
		// baum.elementHinzufuegen("DVM");

		baum.inorderTraversierung(baum.getRoot(), 0);

		// Tiefe des Baums f�r Y-Offset bestimmen
		baum.setMaxheight(baum.berechneHoehe(baum.getRoot()));
		Main dt = new Main(baum);
		dt.setVisible(true);

		baum.berechneElementPositionen();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton pressedButton = (JButton) arg0.getSource();
		boolean erfolgreich;
		ArrayList<String> travListe;
		if (pressedButton.equals(loeschenButton)) {
			erfolgreich = baum.elementLoeschen(new Element(inputTextfield
					.getText().trim()));

			if (!erfolgreich) {
				JOptionPane.showMessageDialog(this,
						"Zu l�schendes Element ist nicht vorhanden!");
			}

			inputTextfield.setText("");
			this.initialize(baum);
		} else if (pressedButton.equals(hinzufuegenButton)) {
			baum.elementHinzufuegen(inputTextfield.getText().trim());
			inputTextfield.setText("");
			this.initialize(baum);
		} else if (pressedButton.equals(inorderButton)) {
			travListe = baum.inorderTraversierung();
			JTextArea textArea = new JTextArea(this.concatList(travListe));
			textArea.setBackground((this.getBackground()));

			JOptionPane.showMessageDialog(this, textArea);
		} else if (pressedButton.equals(preorderButton)) {
			travListe = baum.preorderTraversierung();
			JTextArea textArea = new JTextArea(this.concatList(travListe));
			textArea.setBackground((this.getBackground()));

			JOptionPane.showMessageDialog(this, textArea);
		} else if (pressedButton.equals(postorderButton)) {
			travListe = baum.postorderTraversierung();
			JTextArea textArea = new JTextArea(this.concatList(travListe));
			textArea.setBackground((this.getBackground()));

			JOptionPane.showMessageDialog(this, textArea);
		} else if (pressedButton.equals(balanceButton)) {
			baum.balanceTree();
		}
		baum.berechneElementPositionen();
		initialize(baum);
	}

	/**
	 * Konkatiniert eine StringListe zu einen String
	 * 
	 * @param liste
	 * @return concattedString
	 */
	private String concatList(ArrayList<String> liste) {
		String concattedString = "";

		for (int i = 0; i < liste.size(); i++) {
			concattedString += (i + 1) + ":\t" + liste.get(i) + "\n";
		}

		return concattedString;
	}
}