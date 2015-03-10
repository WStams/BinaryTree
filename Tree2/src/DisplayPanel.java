import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Eine von JPanel erbende Klasse, welche den Binärbaum zeichnet
 * 
 * @author Justin Mertmann, Wayne Stams
 *
 */
class DisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Binaerbaum t;
	private int xOffset = 20, yOffset = 20;
	private int circleSize = 20;
	private int circleOffset = circleSize / 2;

	/**
	 * Konstruktor
	 * 
	 * @param t
	 *            Zu erstellender Binärbaum
	 */
	public DisplayPanel(Binaerbaum t) {
		this.t = t;
		setBackground(Color.white);
		setForeground(Color.black);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
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

	/**
	 * Methode um den Binärbaum auf ein Panel zu zeichnen
	 * 
	 * @param g
	 *            Graphics Objekt, welches die Formen zeichnet
	 * @param root
	 *            zu zeichnendes Element
	 */
	public void zeichneBaum(Graphics g, Element root) {
		int dx, dy, dx2, dy2;
		int BREITE_PANEL = this.getParent().getWidth();
		int HOEHE_PANEL = this.getParent().getHeight();
		int X_GROESSE, Y_GROESSE;

		if (t.getTotalnodes() > 0)
			X_GROESSE = BREITE_PANEL / t.getTotalnodes();
		else
			X_GROESSE = BREITE_PANEL / 1;
		Y_GROESSE = (HOEHE_PANEL - 500) / (t.getMaxheight() + 1);

		if (root != null) {
			zeichneBaum(g, root.getLinkesKind());
			dx = root.getXpos() * X_GROESSE + xOffset;
			dy = root.getYpos() * Y_GROESSE + yOffset;

			String s = root.getNutzdaten();

			g.setColor(Color.BLUE);
			// Knoten als Kreis anzeigen
			g.fillOval(dx - circleOffset, dy - circleOffset, circleSize + 1,
					circleSize + 1);

			// Knotenbeschreibung anzeigen
			g.setColor(Color.BLACK);
			g.drawString(s, dx + xOffset, dy + (yOffset / 2));

			// Linie zum linken Kind zeichnen
			if (root.getLinkesKind() != null) {
				// Positionen definieren
				dx2 = root.getLinkesKind().getXpos() * X_GROESSE + xOffset;
				dy2 = root.getLinkesKind().getYpos() * Y_GROESSE + yOffset;
				g.drawLine(dx, dy, dx2, dy2);
			}
			// Linie zum rechten Kind-Element, ofern eines vorhanden ist
			if (root.getRechtesKind() != null) {
				// Positionen definieren
				dx2 = root.getRechtesKind().getXpos() * X_GROESSE + xOffset;
				dy2 = root.getRechtesKind().getYpos() * Y_GROESSE + yOffset;
				g.drawLine(dx, dy, dx2, dy2);
			}
			zeichneBaum(g, root.getRechtesKind());
		}
	}
}