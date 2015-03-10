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
	private JButton hinzufuegenButton = new JButton("Hinzufügen");
	private JButton loeschenButton = new JButton("Löschen");
	private JTextField inputTextfield = new JTextField();
	private static Binaerbaum baum = new Binaerbaum();
	
	public Main(Binaerbaum t) {
		loeschenButton.setName("loeschen");
		hinzufuegenButton.setName("hinzufuegen");
		this.userPanel.setLayout(new GridLayout(1,3));
		this.userPanel.add(inputTextfield);
		this.userPanel.add(hinzufuegenButton);
		this.userPanel.add(loeschenButton);
		this.getContentPane().setLayout(new BorderLayout());
		
		loeschenButton.addActionListener(this);
		hinzufuegenButton.addActionListener(this);
		
		this.getContentPane().add(userPanel, BorderLayout.SOUTH);
		
		this.panel = new DisplayPanel(t);
		this.panel.setPreferredSize(new Dimension(650, 650));
		this.scrollpane = new JScrollPane(panel);
		this.getContentPane().add(scrollpane, BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
	}

	public static void main(String[] args) {
		
		Main dt = new Main(baum);
		dt.setVisible(true);
		
//		baum.setRoot(baum.insert(baum.getRoot(), "JAN"));
//		baum.setRoot(baum.insert(baum.getRoot(), "FEB"));
//		baum.setRoot(baum.insert(baum.getRoot(), "MAE"));
//		baum.setRoot(baum.insert(baum.getRoot(), "APR"));
//		baum.setRoot(baum.insert(baum.getRoot(), "MAI"));
//		baum.setRoot(baum.insert(baum.getRoot(), "JUL"));
//		baum.setRoot(baum.insert(baum.getRoot(), "AUG"));
//		baum.setRoot(baum.insert(baum.getRoot(), "SEP"));
//		baum.setRoot(baum.insert(baum.getRoot(), "OKT"));
//		baum.setRoot(baum.insert(baum.getRoot(), "NOV"));
//		baum.setRoot(baum.insert(baum.getRoot(), "POV"));
//		baum.setRoot(baum.insert(baum.getRoot(), "DEZ"));
		//
		
		baum.setRoot(baum.insert(baum.getRoot(), "50"));
		baum.setRoot(baum.insert(baum.getRoot(), "30"));
		baum.setRoot(baum.insert(baum.getRoot(), "55"));
		baum.setRoot(baum.insert(baum.getRoot(), "25"));
		baum.setRoot(baum.insert(baum.getRoot(), "35"));
		baum.setRoot(baum.insert(baum.getRoot(), "53"));
		baum.setRoot(baum.insert(baum.getRoot(), "60"));
		baum.setRoot(baum.insert(baum.getRoot(), "10"));
		baum.setRoot(baum.insert(baum.getRoot(), "32"));
		baum.setRoot(baum.insert(baum.getRoot(), "37"));
		baum.setRoot(baum.insert(baum.getRoot(), "62"));
		baum.setRoot(baum.insert(baum.getRoot(), "15"));
		
		baum.berechneElementPositionen();
		
		baum.inorderTraversierung(baum.getRoot(), 0);
		
		// Tiefe des Baums für Y-Offset bestimmen
		baum.setMaxheight(baum.treeHeight(baum.getRoot()));
		}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JButton pressedButton = (JButton) arg0.getSource();
		
		if(pressedButton.equals(loeschenButton)) {
			baum.elementLoeschen(new Element(inputTextfield.getText().trim()));
			inputTextfield.setText("");
		} else if(pressedButton.equals(hinzufuegenButton)) {
			baum.insert(baum.getRoot(), inputTextfield.getText().trim());
			inputTextfield.setText("");
		}
		panel.revalidate();
		panel.repaint();
		baum.berechneElementPositionen();
	}
}