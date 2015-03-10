import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

class DisplayPanel extends JPanel {
	private Binaerbaum t;
	private int xOffset = 20, yOffset = 20;
	private int circleSize = 20;
	private int circleOffset = circleSize/2;
	
	public DisplayPanel(Binaerbaum t) {
		this.t = t;
		setBackground(Color.white);
		setForeground(Color.black);
	}

	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());
		
		Font MyFont = new Font("SansSerif", Font.PLAIN, 10);
		g.setFont(MyFont);
		
		MyFont = new Font("SansSerif", Font.BOLD, 14); 
		g.setFont(MyFont);
		
		this.zeichneBaum(g, t.getRoot());
	}
	
	public void rev() {
		revalidate();
	}
	
	public void zeichneBaum(Graphics g, Element root) {
		int dx, dy, dx2, dy2;
		int BREITE_PANEL = this.getParent().getWidth();
		int HOEHE_PANEL = this.getParent().getHeight();
		int X_GROESSE, Y_GROESSE;
		X_GROESSE = BREITE_PANEL / t.getTotalnodes(); 
		Y_GROESSE = (HOEHE_PANEL - 10) / (t.getMaxheight() + 1); 

		if (root != null) { 
			zeichneBaum(g, root.getLinkesKind());
			dx = root.getXpos() * X_GROESSE + xOffset;
			dy = root.getYpos() * Y_GROESSE + yOffset;
			String s = root.getData(); 
			
			g.setColor(Color.BLUE);
			// Knoten als Kreis anzeigen
			g.fillOval(dx - circleOffset, dy - circleOffset, circleSize+1, circleSize+1);
			
			// Knotenbeschreibung anzeigen
			g.setColor(Color.BLACK);
			g.drawString(s, dx + xOffset, dy + (yOffset / 2));
			
			// Linie zum Kind zeichnen
			if (root.getLinkesKind() != null) { // Linie zum linken Kind
				
				// Positionen definieren
				dx2 = root.getLinkesKind().getXpos() * X_GROESSE + xOffset;
				dy2 = root.getLinkesKind().getYpos() * Y_GROESSE + yOffset;
				g.drawLine(dx, dy, dx2, dy2);
			}
			if (root.getRechtesKind() != null) { // Linie zum rechten Kind
				
				// Positionen definieren
				dx2 = root.getRechtesKind().getXpos() * X_GROESSE + xOffset; 
				dy2 = root.getRechtesKind().getYpos() * Y_GROESSE + yOffset;
				g.drawLine(dx, dy, dx2, dy2);
			}
			zeichneBaum(g, root.getRechtesKind());
		}
	}
}