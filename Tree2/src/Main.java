// Code for popping up a window that displays a custom component
// in this case we are displaying a Binary Search tree  
// reference problem 4.38 of Weiss to compute tree node x,y positions

// input is a text file name that will form the Binary Search Tree

//     java DisplaySimpleTree textfile

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class Main extends JFrame implements ActionListener {
	private JScrollPane scrollpane;
	private DisplayPanel panel;
	private JPanel userPanel = new JPanel();
	private JPanel traversierungsPanel = new JPanel();
	private JButton hinzufuegenButton = new JButton("Hinzufügen");
	private JButton loeschenButton = new JButton("Löschen");
	private JButton inorderButton = new JButton("Inorder-Traversierung");
	private JButton preorderButton = new JButton("Preorder-Traversierung");
	private JButton postorderButton = new JButton("Postorder-Traversierung");
	private JTextField inputTextfield = new JTextField();
	private static Binaerbaum baum = new Binaerbaum();

	public Main(Binaerbaum t) {
		this.setTitle("Binärbaum - Justin & Wayne");
		loeschenButton.setName("loeschen");
		hinzufuegenButton.setName("hinzufuegen");

		
		this.inorderButton.addActionListener(this);
		this.preorderButton.addActionListener(this);
		this.postorderButton.addActionListener(this);
		
		this.traversierungsPanel.setLayout(new GridLayout(1,3));
		this.traversierungsPanel.add(inorderButton);
		this.traversierungsPanel.add(preorderButton);
		this.traversierungsPanel.add(postorderButton);
		
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
	}

	private void initialize(Binaerbaum baum) {
		this.panel = new DisplayPanel(baum);
		this.panel.setPreferredSize(new Dimension(1200, 800));
		this.scrollpane = new JScrollPane(panel);
		this.getContentPane().add(scrollpane, BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
	}
	
	public static void main(String[] args) {

		

		// baum.setRoot(baum.insert(baum.getRoot(), "JAN"));
		// baum.setRoot(baum.insert(baum.getRoot(), "FEB"));
		// baum.setRoot(baum.insert(baum.getRoot(), "MAE"));
		// baum.setRoot(baum.insert(baum.getRoot(), "APR"));
		// baum.setRoot(baum.insert(baum.getRoot(), "MAI"));
		// baum.setRoot(baum.insert(baum.getRoot(), "JUL"));
		// baum.setRoot(baum.insert(baum.getRoot(), "AUG"));
		// baum.setRoot(baum.insert(baum.getRoot(), "SEP"));
		// baum.setRoot(baum.insert(baum.getRoot(), "OKT"));
		// baum.setRoot(baum.insert(baum.getRoot(), "NOV"));
		// baum.setRoot(baum.insert(baum.getRoot(), "POV"));
		// baum.setRoot(baum.insert(baum.getRoot(), "DEZ"));
		//

		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "50"));
		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "30"));
		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "55"));
		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "25"));
		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "35"));
		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "53"));
		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "60"));
		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "10"));
		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "32"));
		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "37"));
		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "62"));
		baum.setRoot(baum.elementHinzufuegen(baum.getRoot(), "15"));


		baum.inorderTraversierung(baum.getRoot(), 0);

		// Tiefe des Baums für Y-Offset bestimmen
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
			erfolgreich = baum.elementLoeschen(new Element(inputTextfield.getText().trim()));
			
			if(!erfolgreich) {
				JOptionPane.showMessageDialog(this, "Zu löschendes Element ist nicht vorhanden!");
			}
			
			inputTextfield.setText("");
			this.initialize(baum);
		} else if (pressedButton.equals(hinzufuegenButton)) {
			baum.elementHinzufuegen(baum.getRoot(), inputTextfield.getText().trim());
			inputTextfield.setText("");
			this.initialize(baum);
		} else if(pressedButton.equals(inorderButton)) {
			travListe = baum.inorderTraversierung();
			JOptionPane.showMessageDialog(this, new JTextArea(this.concatList(travListe)));
		} else if(pressedButton.equals(preorderButton)) {
			travListe = baum.preorderTraversierung();
		} else if(pressedButton.equals(postorderButton)) {
			travListe = baum.postorderTraversierung();
		}
		baum.berechneElementPositionen();
	}
	
	private String concatList(ArrayList<String> liste) {
		String concattedString = "";
		
		for(int i = 0; i < liste.size(); i++) {
			concattedString += (i+1) + ":\t" + liste.get(i) + "\n";
		}
		
		return concattedString;
	}
}